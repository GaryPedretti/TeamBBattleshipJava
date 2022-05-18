package org.scrum.psd.battleship.ascii;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.util.NoSuchElementException;

import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class MainEndToEndTest {
    @ClassRule
    public static final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @ClassRule
    public static final TextFromStandardInputStream gameInput = emptyStandardInputStream();

    @Test
    public void testPlayGameShotHits() {
        try {
            systemOutRule.clearLog();
            gameInput.provideLines("a1", "a2", "a3", "a4", "a5", "b1", "b2", "b3", "b4", "c1", "c2", "c3", "d1", "d2", "d3", "e1", "e2",  //player ship placement
                "b4");  //player shots

            Main.main(new String[]{});
        } catch(NoSuchElementException e) {
            Assert.assertTrue(systemOutRule.getLog().contains("Welcome to Battleship"));
            Assert.assertTrue(systemOutRule.getLog().contains("Yeah ! Nice hit !"));
        }
    }

    @Test
    public void testPlayGameShotMisses() {
        try {
            systemOutRule.clearLog();
            gameInput.provideLines("a1", "a2", "a3", "a4", "a5", "b1", "b2", "b3", "b4", "c1", "c2", "c3", "d1", "d2", "d3", "e1", "e2",  //player ship placement
                "e4"); //player shots

            Main.main(new String[]{});
        } catch(NoSuchElementException e) {
            Assert.assertTrue(systemOutRule.getLog().contains("Welcome to Battleship"));
            Assert.assertTrue(systemOutRule.getLog().contains("Miss !"));
        }
    }

    @Test
    public void testPlayGameShotOutOfBoundsX() {
        try {
            systemOutRule.clearLog();
            gameInput.provideLines("a1", "a2", "a3", "a4", "a5", "b1", "b2", "b3", "b4", "c1", "c2", "c3", "d1", "d2", "d3", "e1", "e2",  //player ship placement
                "m4"); //player shots

            Main.main(new String[]{});
        } catch(NoSuchElementException e) {
            Assert.assertTrue(systemOutRule.getLog().contains("Welcome to Battleship"));
            Assert.assertTrue(systemOutRule.getLog().contains("Error"));
        }
    }

    @Test
    public void testPlayGameShotOutOfBoundsY() {
        try {
            systemOutRule.clearLog();
            gameInput.provideLines("a1", "a2", "a3", "a4", "a5", "b1", "b2", "b3", "b4", "c1", "c2", "c3", "d1", "d2", "d3", "e1", "e2",  //player ship placement
                "b14"); //player shots

            Main.main(new String[]{});
        } catch(NoSuchElementException e) {
            Assert.assertTrue(systemOutRule.getLog().contains("Welcome to Battleship"));
            Assert.assertTrue(systemOutRule.getLog().contains("Error"));
        }
    }

    @Test
    public void testPlayGameShotInvalid() {
        try {
            systemOutRule.clearLog();
            gameInput.provideLines("a1", "a2", "a3", "a4", "a5", "b1", "b2", "b3", "b4", "c1", "c2", "c3", "d1", "d2", "d3", "e1", "e2",  //player ship placement
                "abc"); //player shots

            Main.main(new String[]{});
        } catch(NoSuchElementException e) {
            Assert.assertTrue(systemOutRule.getLog().contains("Welcome to Battleship"));
            Assert.assertTrue(systemOutRule.getLog().contains("Error"));
        }
    }

    @Test
    public void testPlayGameShotSinkShip1() {
        try {
            systemOutRule.clearLog();
            gameInput.provideLines("b2", "b3", "b4", "b5", "b6", "d2", "d3", "d4", "d5", "f2", "f3", "f4", "b7", "c7", "d7", "f7", "g7",  //player ship placement
                "b4", "b5", "b6", "b7", "b8" ); //player shots

            Main.main(new String[]{});
        } catch(NoSuchElementException e) {
            Assert.assertTrue(systemOutRule.getLog().contains("Welcome to Battleship"));
            Assert.assertFalse(systemOutRule.getLog().contains("Miss !"));
            Assert.assertTrue(systemOutRule.getLog().contains("Yeah ! Nice hit !"));
            Assert.assertTrue(systemOutRule.getLog().contains("You sunk an enemy ship!"));
        }
    }

    @Test
    public void testPlayGameShotSinkShip2() {
        try {
            systemOutRule.clearLog();
            gameInput.provideLines("b2", "b3", "b4", "b5", "b6", "d2", "d3", "d4", "d5", "f2", "f3", "f4", "b7", "c7", "d7", "f7", "g7",  //player ship placement
                "e6", "e7", "e8", "e9" ); //player shots

            Main.main(new String[]{});
        } catch(NoSuchElementException e) {
            Assert.assertTrue(systemOutRule.getLog().contains("Welcome to Battleship"));
            Assert.assertFalse(systemOutRule.getLog().contains("Miss !"));
            Assert.assertTrue(systemOutRule.getLog().contains("Yeah ! Nice hit !"));
            Assert.assertTrue(systemOutRule.getLog().contains("You sunk an enamy ship!"));
        }
    }

    @Test
    public void testPlayGameShotSinkShip3() {
        try {
            systemOutRule.clearLog();
            gameInput.provideLines("b2", "b3", "b4", "b5", "b6", "d2", "d3", "d4", "d5", "f2", "f3", "f4", "b7", "c7", "d7", "f7", "g7",  //player ship placement
                "a3", "b3", "c3" ); //player shots

            Main.main(new String[]{});
        } catch(NoSuchElementException e) {
            Assert.assertTrue(systemOutRule.getLog().contains("Welcome to Battleship"));
            Assert.assertFalse(systemOutRule.getLog().contains("Miss !"));
            Assert.assertTrue(systemOutRule.getLog().contains("Yeah ! Nice hit !"));
            Assert.assertTrue(systemOutRule.getLog().contains("You sunk an enemy ship!"));
        }
    }

    @Test
    public void testPlayGameShotSinkShip4() {
        try {
            systemOutRule.clearLog();
            gameInput.provideLines("b2", "b3", "b4", "b5", "b6", "d2", "d3", "d4", "d5", "f2", "f3", "f4", "b7", "c7", "d7", "f7", "g7",  //player ship placement
                "f8", "g8", "h8" ); //player shots

            Main.main(new String[]{});
        } catch(NoSuchElementException e) {
            Assert.assertTrue(systemOutRule.getLog().contains("Welcome to Battleship"));
            Assert.assertFalse(systemOutRule.getLog().contains("Miss !"));
            Assert.assertTrue(systemOutRule.getLog().contains("Yeah ! Nice hit !"));
            Assert.assertTrue(systemOutRule.getLog().contains("You sunk an enemy ship!"));
        }
    }

    @Test
    public void testPlayGameShotSinkShip5() {
        try {
            systemOutRule.clearLog();
            gameInput.provideLines("b2", "b3", "b4", "b5", "b6", "d2", "d3", "d4", "d5", "f2", "f3", "f4", "b7", "c7", "d7", "f7", "g7",  //player ship placement
                "c5", "c6" ); //player shots

            Main.main(new String[]{});
        } catch(NoSuchElementException e) {
            Assert.assertTrue(systemOutRule.getLog().contains("Welcome to Battleship"));
            Assert.assertFalse(systemOutRule.getLog().contains("Miss !"));
            Assert.assertTrue(systemOutRule.getLog().contains("Yeah ! Nice hit !"));
            Assert.assertTrue(systemOutRule.getLog().contains("You sunk an enemy ship!"));
        }
    }

    @Test
    public void testPlayGameShotAllWater() {
        try {
            systemOutRule.clearLog();
            gameInput.provideLines("b2", "b3", "b4", "b5", "b6", "d2", "d3", "d4", "d5", "f2", "f3", "f4", "b7", "c7", "d7", "f7", "g7",  //player ship placement
                "a1", "a2",       "a4", "a5", "a6", "a7", "a8",  //player shots
                "b1", "b2",  
                "c1", "c2",       "c4",             "c7", "c8", 
                "d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8", 
                "e1", "e2", "e3", "e4", "e5",  
                "f1", "f2", "f3", "f4", "f5", "f6", "f7",  
                "g1", "g2", "g3", "g4", "g5", "g6", "g7",  
                "h1", "h2", "h3", "h4", "h5", "h6", "h7"
                 );

            Main.main(new String[]{});
        } catch(NoSuchElementException e) {
            Assert.assertTrue(systemOutRule.getLog().contains("Welcome to Battleship"));
            Assert.assertTrue(systemOutRule.getLog().contains("Miss !"));
            Assert.assertFalse(systemOutRule.getLog().contains("Yeah ! Nice hit !"));
        }
    }


}
