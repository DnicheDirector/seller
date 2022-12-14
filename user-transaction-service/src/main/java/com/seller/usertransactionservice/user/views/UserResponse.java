package com.seller.usertransactionservice.user.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private UUID id;
    private ZonedDateTime created;
    private ZonedDateTime updated;
    private String username;
    private String email;
    private String name;
    private String role;
    private Long companyId;
}