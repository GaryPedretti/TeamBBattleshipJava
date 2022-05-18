package org.scrum.psd.battleship.ascii;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;

import org.scrum.psd.battleship.controller.GameController;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;
import org.scrum.psd.battleship.controller.dto.ShotResult;

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
            printFleetStatus(myFleet, "My   ");
            printFleetStatus(enemyFleet, "Enemy");
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
                if (validCoordinates == false) {
                    console.println("Error: Coordinates entered are not valid, please try again.");
                }
            } while (validCoordinates == false);
            Position position = parsePosition(enteredCoordinates);
            console.println("Firing shot to coordinates: " + position.getColumn() + position.getRow());
            ShotResult myShot = GameController.fireShot(enemyFleet, position);
            console.println("\t");
            if (myShot.isHit()) {
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
                if (myShot.sunkShip()) {
                    console.setForegroundColor(Ansi.FColor.RED);
                    console.println("You sunk an enemy ship!");
                }
            }
            else {
                console.setForegroundColor(Ansi.FColor.RED);
                console.println("Miss !");
                console.println("");
            }

            console.println("\t");
            console.println("\t");

            position = getRandomPosition();
            ShotResult enemyShot = GameController.fireShot(myFleet, position);
            console.println("\t");
            console.setForegroundColor(Ansi.FColor.MAGENTA);
            console.println(String.format("Computer shoot in %s%s and %s", position.getColumn(), position.getRow(), enemyShot.isHit() ? "hit your ship !" : "miss"));
            console.println("");
            if (enemyShot.isHit()) {
                beep();

                console.println("                \\         .  ./");
                console.println("              \\      .:\" \";'.:..\" \"   /");
                console.println("                  (M^^.^~~:.'\" \").");
                console.println("            -   (/  .    . . \\ \\)  -");
                console.println("               ((| :. ~ ^  :. .|))");
                console.println("            -   (\\- |  \\ /  |  /)  -");
                console.println("                 -\\  \\     /  /-");
                console.println("                   \\  \\   /  /");

                if (enemyShot.sunkShip()) {
                    console.setForegroundColor(Ansi.FColor.RED);
                    console.println("The enemy sunk your ship!");
                }

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
        String strLetter = enteredCoordinates.toUpperCase().substring(0, 1);
        if (letterContains(strLetter) == false) { return false; }
        try {
            Integer.parseInt(enteredCoordinates.substring(1));
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        int number = Integer.parseInt(enteredCoordinates.substring(1));
        if (number < 1 || number > 8) { return false; }
        return true;
    }
    protected static Position parsePosition(String input) {
        Letter letter = Letter.valueOf(input.toUpperCase().substring(0, 1));
        int number = Integer.parseInt(input.substring(1));
        return new Position(letter, number);
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
        InitializeMyFleet();
        //DebugInitializeMyFleet();
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

        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 5));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 6));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 7));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 8));

        enemyFleet.get(2).getPositions().add(new Position(Letter.A, 3));
        enemyFleet.get(2).getPositions().add(new Position(Letter.B, 3));
        enemyFleet.get(2).getPositions().add(new Position(Letter.C, 3));

        enemyFleet.get(3).getPositions().add(new Position(Letter.F, 8));
        enemyFleet.get(3).getPositions().add(new Position(Letter.G, 8));
        enemyFleet.get(3).getPositions().add(new Position(Letter.H, 8));

        enemyFleet.get(4).getPositions().add(new Position(Letter.C, 5));
        enemyFleet.get(4).getPositions().add(new Position(Letter.C, 6));
    }

    private static void printFleetStatus(List<Ship> fleet, String player) {
        console.setForegroundColor(FColor.YELLOW);
        console.print(player + " Fleet: ");
        for (int i=0; i < fleet.size(); i++) {
            Ship ship = fleet.get(i);

            console.print(ship.getStatus() + " - ");
        }
        console.println("\t");
    }
}
