@echo off
set path = '/d %~dp0'
cd path
call mvn clean install
cd path
cd target
java -jar -Denv=pro netty.jar
cmd