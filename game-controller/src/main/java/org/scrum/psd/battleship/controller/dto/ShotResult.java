package org.scrum.psd.battleship.controller.dto;

public class ShotResult {
    private Ship ship;
    boolean directHit = false;

    public ShotResult(Ship ship) {
        this.ship = ship;
        this.directHit = true;
    }
    public ShotResult(boolean directHit) {
        this.ship = null;
        this.directHit = directHit;
    }
    
    public boolean isHit() {
        return this.directHit;
    }

    public boolean sunkShip() {
        if (ship != null) {
            return ship.isSunk();
        }
        else {
            return false;
        }
    }
}
