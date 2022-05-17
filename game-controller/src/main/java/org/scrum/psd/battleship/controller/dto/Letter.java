package org.scrum.psd.battleship.controller.dto;

public enum Letter {
    A, B, C, D, E, F, G, H;
}

public static boolean letterContains(String input) {
    for (Letter l : Letter.values()) {
        if (l.name().equals(input)) {
            return true;
        }
    }
    return false;
}
