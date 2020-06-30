# SQL java client

Tiny SQL java client for non interactive shells and when other mysql tools are not available in the victim host.

## Description

This non interactive SQL client will allows an attacker with a shell inside a victim host to issue SQL queries inside the network when no other Mysql management tools are available to issue commands or other programming languages are installed, this kind of installations is very common inside Docker containers where only java app and dependencies are installed.

Output is not pretty but better ugly than nothing ;)

Haven't spend much time on this code (don't complain to much about it), just was a quick script for a HTB box which resulted useless since this wasn't the path LOL, but though it could be usefull for other people.

## Usage

Download java sql connector in victim host inside /dev/shm or other writable directory. <https://downloads.mariadb.org/connector-java/>

Configure environment variables pointing to the driver connector, mysql connection and authentication

```sh
export CLASSPATH=/dev/shm/mariadb-java-client-2.6.0.jar:$CLASSPATH
export MYSQL_CONNECTION="jdbc:mariadb://127.0.0.1:3306"
export MYSQL_USER="root"
export MYSQL_PASSWORD="secret"
```

### Java 11

Directly execute .java file with database as first parameter and SQL query as second

```sh
$ java SQLClient.java test_db "select username,role from users;"

Column : username
Value: admin,  
Column : role
Value: admin

Column : username
Value: user1,  
Column : role
Value: user
```

### Java 8

Compile .java file and execute classs name with database as first parameter and SQL query as second

```sh
$ /usr/lib/jvm/java-8-openjdk-amd64/bin/javac SQLClient.java

$ java SQLClient test_db "select username,role from users;"

Column : username
Value: admin,  
Column : role
Value: admin

Column : username
Value: user1,  
Column : role
Value: user
```

## Support

Tested in:

- java 8
- java 11
- mariadb driver connector

Other java versions and connectors may work or may require small changes to load the driver.

## Local setup quick start

Create mariadb docker container

```sh
docker run --name mariadb -e MYSQL_ROOT_PASSWORD=secret --net host -d mariadb:latest
```

Connect to database

```sh
mysql -uroot -psecret -h 127.0.0.1
```

Create demo content

```sql
create database Testing;
use Testing;
create table users ( id int, username varchar(255), email varchar(255), password varchar(255), role varchar(255));
insert into users values (0, 'admin', 'admin@admin.com', '1234', 'admin');
insert into users values (1, 'user1', 'user1@user1.com', '123456', 'user');
```
