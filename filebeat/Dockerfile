FROM docker.elastic.co/beats/filebeat:7.2.0
COPY ./filebeat.docker.yml /usr/share/filebeat/filebeat.yml
USER root
RUN chmod go-w /usr/share/filebeat/filebeat.yml