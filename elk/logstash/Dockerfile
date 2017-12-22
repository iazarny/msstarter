FROM docker.elastic.co/logstash/logstash-oss:6.1.1

RUN /usr/share/logstash/bin/logstash-plugin install logstash-input-log4j
COPY logstash.conf /config-dir/
EXPOSE 5001 5002
CMD logstash -f /config-dir/logstash.conf

