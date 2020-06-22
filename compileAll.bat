:: This file is for Windows only.

if not exist bin mkdir bin
javac src\connect4\*.java src\gui\*.java -d bin

:: Copy the folder "res\images" to "bin".
if not exist bin\images mkdir bin\images
copy res\images bin\images

pause
