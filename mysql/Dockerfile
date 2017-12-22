FROM  mysql:8.0.3

ENV MYSQL_DATABASE=artdb \
    MYSQL_ROOT_PASSWORD=password

ADD artdb.sql /docker-entrypoint-initdb.d

EXPOSE 3306

