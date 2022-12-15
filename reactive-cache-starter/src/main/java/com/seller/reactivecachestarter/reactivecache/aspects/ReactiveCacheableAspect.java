package com.seller.reactivecachestarter.reactivecache.aspects;

import com.seller.reactivecachestarter.exceptions.IncorrectMethodDefinitionException;
import com.seller.reactivecachestarter.reactivecache.annotations.ReactiveMonoCacheable;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import reactor.core.CorePublisher;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class ReactiveCacheableAspect {

    private final CacheManager cacheManager;

    @Around("@annotation(com.seller.reactivecachestarter.reactivecache.annotations.ReactiveMonoCacheable) && args(..)")
    public Mono<?> cacheResult(ProceedingJoinPoint joinPoint) {
        var cache = getCache(joinPoint);
        var cacheKey = getCacheKey(joinPoint);

        return Optional.ofNullable(cache.get(cacheKey))
                .map(Cache.ValueWrapper::get)
                .map(Mono::just)
                .orElseGet(() -> proceed(joinPoint, Mono.class)
                        .doOnNext(value -> cache.put(cacheKey, value))
                );
    }

    private <T extends CorePublisher<?>> T proceed(ProceedingJoinPoint joinPoint, Class<T> returnType) {
        try {
            return returnType.cast(joinPoint.proceed());
        } catch (ClassCastException e) {
            throw new IncorrectMethodDefinitionException(
                    String.format("Method should return %s", returnType.getName())
            );
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private Cache getCache(JoinPoint joinPoint) {
        var methodSignature = (MethodSignature) joinPoint.getSignature();
        var annotation = methodSignature.getMethod().getAnnotation(ReactiveMonoCacheable.class);

        var cacheName = annotation.cacheName();
        return Optional.ofNullable(cacheManager.getCache(cacheName))
                .orElseThrow(() -> new IncorrectMethodDefinitionException("Cache name is incorrect or doesn't exist"));
    }

    private Object getCacheKey(ProceedingJoinPoint joinPoint) {
        var methodSignature = (MethodSignature) joinPoint.getSignature();
        var annotation = methodSignature.getMethod().getAnnotation(ReactiveMonoCacheable.class);

        var args = joinPoint.getArgs();

        var keyName = annotation.key().trim().toLowerCase();
        Object keyValue = null;

        if (!keyName.isBlank()) {
            var parameterNames = Arrays.stream(methodSignature.getParameterNames())
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
            var index = parameterNames.indexOf(keyName);
            if (index != -1) {
                keyValue = parameterNames.get(index);
            }
        } else if (args.length == 1) {
            keyValue = args[0];
        }

        if (keyValue == null) {
            throw new IncorrectMethodDefinitionException("Invalid or missing caching key");
        }

        return keyValue;
    }
}
