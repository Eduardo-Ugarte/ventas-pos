@echo off
echo Iniciando Sistema de Ventas...
cd /d %~dp0
call mvnw.cmd spring-boot:run
pause