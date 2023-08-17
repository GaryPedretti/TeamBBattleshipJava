package org.scrum.psd.battleship.controller;

import org.junit.Assert;
import org.junit.Test;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class GameControllerTest {
    @Test
    public void testCheckIsHitTrue() {
        List<Ship> ships = GameController.initializeShips();
        int counter = 0;

        for (Ship ship : ships) {
            Letter letter = Letter.values()[counter];

            for (int i = 0; i < ship.getSize(); i++) {
                ship.getPositions().add(new Position(letter, i));
            }

            counter++;
        }

        Position position = new Position(Letter.A, 1);
        boolean result = GameController.checkIsHit(ships, position);

        Assert.assertTrue(result);
    }

    @Test
    public void testShipKnowsWhenItsHit() {
        Position position = new Position(Letter.A, 1);
        List<Position> positions = new ArrayList();
        positions.add(position);
        Ship myShip = new Ship("Testing Boat", 1, positions);
        List<Ship> ships = new ArrayList();
        ships.add(myShip);

        boolean result = GameController.checkIsHit(ships, new Position(Letter.A, 1));

        Assert.assertTrue(result);
        Assert.assertTrue(position.isHit());
    }

    @Test
    public void testShipKnowsWhenItsAMiss() {
        Position position = new Position(Letter.A, 1);
        List<Position> positions = new ArrayList();
        positions.add(position);
        Ship myShip = new Ship("Testing Boat", 1, positions);
        List<Ship> ships = new ArrayList();
        ships.add(myShip);

        boolean result = GameController.checkIsHit(ships, new Position(Letter.B, 1));

        Assert.assertFalse(result);
        Assert.assertFalse(position.isHit());
    }

    @Test
    public void testShipKnowsWhenItsSunk() {
        Position position1 = new Position(Letter.A, 1);
        Position position2 = new Position(Letter.B, 1);
        
        List<Position> positions = new ArrayList();
        positions.add(position1);
        positions.add(position2);

        Ship myShip = new Ship("Testing Boat", 2, positions);
        List<Ship> ships = new ArrayList();
        ships.add(myShip);

        GameController.checkIsHit(ships, new Position(Letter.B, 1));
        GameController.checkIsHit(ships, new Position(Letter.A, 1));

        boolean isShipSunk = GameController.isShipSunk(ships, "Testing Boat");
        Assert.assertTrue(isShipSunk);
    }
    @Test
    public void testShipKnowsWhenItsNotSunk() {
        Position position1 = new Position(Letter.A, 1);
        Position position2 = new Position(Letter.B, 1);
        
        List<Position> positions = new ArrayList();
        positions.add(position1);
        positions.add(position2);

        Ship myShip = new Ship("Testing Boat", 2, positions);
        List<Ship> ships = new ArrayList();
        ships.add(myShip);


        GameController.checkIsHit(ships, new Position(Letter.A, 1));

        boolean isShipSunk = GameController.isShipSunk(ships, "Testing Boat");
        Assert.assertFalse(isShipSunk);
    }

    @Test
    public void testShipKnowsWhenItIsSunkWithOtherShips() {
        List<Ship> ships = GameController.initializeShips();
        int counter = 0;
    
        GameController.checkIsHit(ships, new Position(Letter.C, 5));
        GameController.checkIsHit(ships, new Position(Letter.C, 6));

        Assert.assertTrue(GameController.isShipSunk(ships, "Patrol Boat"));
    }

    @Test
    public void testCheckIsHitFalse() {
        List<Ship> ships = GameController.initializeShips();
        int counter = 0;

        for (Ship ship : ships) {
            Letter letter = Letter.values()[counter];

            for (int i = 0; i < ship.getSize(); i++) {
                ship.getPositions().add(new Position(letter, i));
            }

            counter++;
        }

        boolean result = GameController.checkIsHit(ships, new Position(Letter.H, 1));

        Assert.assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckIsHitPositstionIsNull() {
        GameController.checkIsHit(GameController.initializeShips(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckIsHitShipIsNull() {
        GameController.checkIsHit(null, new Position(Letter.H, 1));
    }

    @Test
    public void testIsShipValidFalse() {
        Ship ship = new Ship("TestShip", 3);
        boolean result = GameController.isShipValid(ship);

        Assert.assertFalse(result);
    }

    @Test
    public void testIsShipValidTrue() {
        List<Position> positions = Arrays.asList(new Position(Letter.A, 1), new Position(Letter.A, 1), new Position(Letter.A, 1));
        Ship ship = new Ship("TestShip", 3, positions);

        boolean result = GameController.isShipValid(ship);

        Assert.assertTrue(result);
    }

}
