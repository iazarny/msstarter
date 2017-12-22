DROP DATABASE IF EXISTS artdb;

CREATE DATABASE artdb CHARACTER SET utf8 COLLATE utf8_general_ci;

SET character_set_client = utf8;
SET character_set_results = utf8;
SET character_set_connection = utf8;

-- grant statement creates the user if the user does not exist (as long as the no_auto_create_user is not set).
GRANT ALL ON artdb.* TO 'root'@'%' IDENTIFIED BY 'password' WITH GRANT OPTION;
GRANT ALL ON artdb.* TO 'root'@'localhost' IDENTIFIED BY 'password' WITH GRANT OPTION;

USE artdb;



CREATE TABLE `article` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `title` varchar(256) DEFAULT '',
  `content` varchar(10000) DEFAULT '',
  `link` varchar(512) DEFAULT '',
  PRIMARY KEY (`id`)
);

INSERT INTO `article` (`title`, `content`, `link`) VALUE (
  'Mixing Keys in Tomcat',
  'Tomcat 8.5+ can go one step further and supports multiple certificate types for each host. This is most useful for supporting RSA with older browsers while supporting ECDSA with compatible browsers.',
  'https://dzone.com/articles/mixing-keys-in-tomcat');

INSERT INTO `article` (`title`, `content`, `link`) VALUE (
  'CI/CD for Containerized Microservices',
  'The benefits of microservices, such as shorter development time, small and independent releases, and decentralized governance introduce a set of challenges like versioning, testing, deployment, and configuration control...',
  'https://dzone.com/articles/cicd-for-containerised-microservices');

INSERT INTO `article` (`title`, `content`, `link`) VALUE (
  'JAX-RS List Generic Type Erasure',
  'Want to maintain a List''s generic type? The problem is that when you want to return a List from a JAX-RS resource method, the generic type is lost....',
  'https://dzone.com/articles/jax-rs-list-generic-type-erasure');

CREATE TABLE `hibernate_sequence` ( next_val bigint );

INSERT INTO hibernate_sequence VALUES ( 100 );
