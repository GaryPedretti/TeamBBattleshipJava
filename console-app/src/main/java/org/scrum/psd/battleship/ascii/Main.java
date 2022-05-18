package org.scrum.psd.battleship.ascii;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import com.diogonunes.jcdp.color.api.Ansi.BColor;

import org.scrum.psd.battleship.controller.GameController;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
// test

public class Main {
    private static List<Ship> myFleet;
    private static List<Ship> enemyFleet;
    private static ColoredPrinter console;

    public static void main(String[] args) {
        console = new ColoredPrinter.Builder(1, false).background(Ansi.BColor.BLACK).foreground(Ansi.FColor.WHITE).build();

        console.setForegroundColor(Ansi.FColor.MAGENTA);
        console.println("                                     |__");
        console.println("                                     |\\/");
        console.println("                                     ---");
        console.println("                                     / | [");
        console.println("                              !      | |||");
        console.println("                            _/|     _/|-++'");
        console.println("                        +  +--|    |--|--|_ |-");
        console.println("                     { /|__|  |/\\__|  |--- |||__/");
        console.println("                    +---------------___[}-_===_.'____                 /\\");
        console.println("                ____`-' ||___-{]_| _[}-  |     |_[___\\==--            \\/   _");
        console.println(" __..._____--==/___]_|__|_____________________________[___\\==--____,------' .7");
        console.println("|                        Welcome to Battleship                         BB-61/");
        console.println(" \\_________________________________________________________________________|");
        console.println("");
        console.setForegroundColor(Ansi.FColor.WHITE);

        InitializeGame();

        StartGame();
    }

    private static void StartGame() {
        console.setBackgroundColor(BColor.BLACK);

        Scanner scanner = new Scanner(System.in);

        console.println("                  __");
        console.println("                 /  \\");
        console.println("           .-.  |    |");
        console.println("   *    _.-'  \\  \\__/");
        console.println("    \\.-'       \\");
        console.println("   /          _/");
        console.println("  |      _  /\" \"");
        console.println("  |     /_\'");
        console.println("   \\    \\_/");
        console.println("    \" \"\" \"\" \"\" \"");

        do {
            console.println("");
            console.setForegroundColor(Ansi.FColor.CYAN);
            console.println("Player, it's your turn");
            console.println("");
            console.println("Enter coordinates for your shot :");
            console.println("");
            Boolean validCoordinates = false;
            String enteredCoordinates;
            do {
                console.print("Firing shot to coordinates: ");
                enteredCoordinates = scanner.next();
                validCoordinates = checkCoordinates(enteredCoordinates);
            } while (validCoordinates == false);
            Position position = parsePosition(enteredCoordinates);
            console.println("Firing shot to coordinates: " + position.getColumn() + position.getRow());
            boolean isHit = GameController.checkIsHit(enemyFleet, position);
            console.println("\t");
            if (isHit) {
                beep();
                console.println("                \\         .  ./");
                console.println("              \\      .:\" \";'.:..\" \"   /");
                console.println("                  (M^^.^~~:.'\" \").");
                console.println("            -   (/  .    . . \\ \\)  -");
                console.println("               ((| :. ~ ^  :. .|))");
                console.println("            -   (\\- |  \\ /  |  /)  -");
                console.println("                 -\\  \\     /  /-");
                console.println("                   \\  \\   /  /");
                console.println("");
                console.setForegroundColor(Ansi.FColor.GREEN);
                console.println("Yeah ! Nice hit !");
                console.println("");
            }
            else {
                console.setForegroundColor(Ansi.FColor.RED);
                console.println("Miss !");
                console.println("");
            }

            console.println("\t");
            console.println("\t");

            position = getRandomPosition();
            isHit = GameController.checkIsHit(myFleet, position);
            console.println("\t");
            console.setForegroundColor(Ansi.FColor.MAGENTA);
            console.println(String.format("Computer shoot in %s%s and %s", position.getColumn(), position.getRow(), isHit ? "hit your ship !" : "miss"));
            console.println("");
            if (isHit) {
                beep();

                console.println("                \\         .  ./");
                console.println("              \\      .:\" \";'.:..\" \"   /");
                console.println("                  (M^^.^~~:.'\" \").");
                console.println("            -   (/  .    . . \\ \\)  -");
                console.println("               ((| :. ~ ^  :. .|))");
                console.println("            -   (\\- |  \\ /  |  /)  -");
                console.println("                 -\\  \\     /  /-");
                console.println("                   \\  \\   /  /");

            }
            console.println("\t");
            console.println("\t");
            console.println("\t");
        } while (true);
    }

    private static void beep() {
        console.print("\007");
    }

    protected static Boolean checkCoordinates(String enteredCoordinates){
        return true;
    }

    protected static Position parsePosition(String input) {
        Position position = new Position();
        // check to see if letter is in bound
        String inputLetter = new String(input.toUpperCase().substring(0, 1));
        if (letterContains(inputLetter) == false) {
           return null;
        }
        // check to see if number is in bound
        int number = Integer.parseInt(input.substring(1));
        if (number > 8 ||  number < 1) {
            return null;
        }
        position.setColumn(Letter.valueOf(inputLetter));
        position.setRow(number);
        return position;
    }

    protected static boolean letterContains(String input) {
        for (Letter l : Letter.values()) {
            if (l.name().equals(input)) {
                return true;
            }
        }
        return false;
    }

    private static Position getRandomPosition() {
        int rows = 8;
        int lines = 8;
        Random random = new Random();
        Letter letter = Letter.values()[random.nextInt(lines)];
        int number = random.nextInt(rows);
        Position position = new Position(letter, number);
        return position;
    }

    private static void InitializeGame() {
        // InitializeMyFleet();
        DebugInitializeMyFleet();

        InitializeEnemyFleet();
    }

    private static void InitializeMyFleet() {
        Scanner scanner = new Scanner(System.in);
        myFleet = GameController.initializeShips();

        console.print("Please position your fleet (Game board has size from A to H and 1 to 8) :");

        for (Ship ship : myFleet) {
            console.println("\t");
            console.println(String.format("Please enter the positions for the %s (size: %s)", ship.getName(), ship.getSize()));
            for (int i = 1; i <= ship.getSize(); i++) {
                console.print(String.format("Enter position %s of %s (i.e A3) : ", i, ship.getSize()));
                String positionInput = scanner.next();
                ship.addPosition(positionInput);
            }
        }
    }

    private static void DebugInitializeMyFleet() {
        myFleet = GameController.initializeShips();

        myFleet.get(0).getPositions().add(new Position(Letter.H, 1));
        myFleet.get(0).getPositions().add(new Position(Letter.H, 2));
        myFleet.get(0).getPositions().add(new Position(Letter.H, 3));
        myFleet.get(0).getPositions().add(new Position(Letter.H, 4));
        myFleet.get(0).getPositions().add(new Position(Letter.H, 5));

        myFleet.get(1).getPositions().add(new Position(Letter.C, 2));
        myFleet.get(1).getPositions().add(new Position(Letter.C, 3));
        myFleet.get(1).getPositions().add(new Position(Letter.C, 4));
        myFleet.get(1).getPositions().add(new Position(Letter.C, 5));

        myFleet.get(2).getPositions().add(new Position(Letter.F, 3));
        myFleet.get(2).getPositions().add(new Position(Letter.G, 3));
        myFleet.get(2).getPositions().add(new Position(Letter.H, 3));

        myFleet.get(3).getPositions().add(new Position(Letter.B, 4));
        myFleet.get(3).getPositions().add(new Position(Letter.C, 4));
        myFleet.get(3).getPositions().add(new Position(Letter.D, 4));

        myFleet.get(4).getPositions().add(new Position(Letter.A, 5));
        myFleet.get(4).getPositions().add(new Position(Letter.A, 6));
    }



    private static void InitializeEnemyFleet() {
        enemyFleet = GameController.initializeShips();

        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 4));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 5));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 6));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 7));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 8));

        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 6));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 7));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 8));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 9));

        enemyFleet.get(2).getPositions().add(new Position(Letter.A, 3));
        enemyFleet.get(2).getPositions().add(new Position(Letter.B, 3));
        enemyFleet.get(2).getPositions().add(new Position(Letter.C, 3));

        enemyFleet.get(3).getPositions().add(new Position(Letter.F, 8));
        enemyFleet.get(3).getPositions().add(new Position(Letter.G, 8));
        enemyFleet.get(3).getPositions().add(new Position(Letter.H, 8));

        enemyFleet.get(4).getPositions().add(new Position(Letter.C, 5));
        enemyFleet.get(4).getPositions().add(new Position(Letter.C, 6));
    }
}
