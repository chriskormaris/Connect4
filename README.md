# Connect-4
A Project made for educational purposes while studying in Athens University of Economics & Business.

Video for this project:
- https://www.youtube.com/watch?v=SxstLdf9LkE

Programming Language: `Java`

The game known as `Connect-4`, `Score-4`, `4-in-a-line` or `4-in-a-row`.
The AI uses the `Minimax` algorithm, with `alpha-beta pruning`.
A graphical interface is included.
The user can click on the buttons or press the keys 1-7 to place a checker.
To go back a move click on `Undo` option or press `Ctrl+Z`.
To redo a move click on `Redo` option or press `Ctrl+Y`.

### HOT TO RUN IN ECLIPSE

1. Create a new Eclipse Java project.
2. Copy all `.java` files located in `src` folder of the repository to the src folder of the Eclipse project.
3. Copy `res` folder to the Eclipse project root.
4. Right-click the project folder on `Project Explorer`.
   Go to `Properties->Java Build Path->Source`.
   Click `Add Folder...`. Tick the `res` folder.
5. Run the class `GUI`.
You can also run the application just from the console, with the classes `Connect4` and `Connect5`.

A `.jar` executable is included as well.
Java 8 and above is required to run. Have fun!!


### Screenshots

#### Connect-4
![screenshot](/screenshots/connect-4_system-style.png)

#### Connect-5
![screenshot](/screenshots/connect-5_system-style.png)

### Citation
The GUI of the Java Swing application was based on this GitHub repository:
- https://github.com/0xadada/smart-connect-four
