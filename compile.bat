@echo off
javac -d out src\lab\kasir\*.java
if errorlevel 1 (echo COMPILE GAGAL & pause & exit /b 1)
echo COMPILE BERHASIL
pause
