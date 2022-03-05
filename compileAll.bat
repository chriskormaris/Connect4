:: This file is for Windows only.

if not exist bin mkdir bin
javac src\com\chriskormaris\connect4\api\ai\*.java src\com\chriskormaris\connect4\api\connect4\*.java src\com\chriskormaris\connect4\api\enumeration\*.java src\com\chriskormaris\connect4\api\util\*.java src\com\chriskormaris\connect4\console\*.java src\com\chriskormaris\connect4\gui\*.java src\com\chriskormaris\connect4\gui\enumeration\*.java src\com\chriskormaris\connect4\gui\util\*.java -d bin

:: Copy the folder "res\images" to "bin".
if not exist bin\images mkdir bin\images
copy res\images bin\images

pause
