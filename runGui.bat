:: This file is for Windows only.

:: copy the "images" folder from "res\images" inside the "bin" directory
if not exist bin\images mkdir bin\images
copy res\images\* bin\images
java -cp bin gui.Gui
pause
