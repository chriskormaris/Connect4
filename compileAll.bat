:: This file is for Windows only.

if not exist bin mkdir bin
javac src\connect4\*.java src\gui\*.java -d bin
pause
