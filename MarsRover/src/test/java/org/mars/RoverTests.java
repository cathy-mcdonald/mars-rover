package org.mars;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class RoverTests {

  private File plateauFile;
  private Plateau plateau;
  
  @Before
  public void setUp() throws Exception {
    plateauFile = new File("src/test/resources/plateau.txt");
    plateau = new Plateau(plateauFile);
  }

  @Test
  public void testDoNothing() {
    Rover rover = new Rover(0, 2, Direction.EAST);
    assertEquals(0, rover.getXCoord());
    assertEquals(2, rover.getYCoord());
  }
  
  @Test
  public void testMoveEast() {
    Rover rover = new Rover(0, 0, Direction.EAST);
    try {
      rover.processCommand('M', plateau);
      assertEquals(1, rover.getXCoord());
      assertEquals(0, rover.getYCoord());
      
    } catch (RoverCommandException e) {
      fail("Unexpected RoverCommandException" + e.getMessage());
    }   
  }

  @Test
  public void testMoveWest() {
    Rover rover = new Rover(3, 0, Direction.WEST);
    try {
      rover.processCommand('M', plateau);
      assertEquals(2, rover.getXCoord());
      assertEquals(0, rover.getYCoord());
      
    } catch (RoverCommandException e) {
      fail("Unexpected RoverCommandException" + e.getMessage());
    }   
  }

  @Test
  public void testMoveNorth() {
    Rover rover = new Rover(1, 2, Direction.NORTH);
    try {
      rover.processCommand('M', plateau);
      assertEquals(1, rover.getXCoord());
      assertEquals(3, rover.getYCoord());
      
    } catch (RoverCommandException e) {
      fail("Unexpected RoverCommandException" + e.getMessage());
    }   
  }

  @Test
  public void testMoveSouth() {
    Rover rover = new Rover(1, 2, Direction.SOUTH);
    try {
      rover.processCommand('M', plateau);
      assertEquals(1, rover.getXCoord());
      assertEquals(1, rover.getYCoord());
      
    } catch (RoverCommandException e) {
      fail("Unexpected RoverCommandException" + e.getMessage());
    }   
  }

  @Test
  public void testTurnRight() {
    Rover rover = new Rover(1, 2, Direction.EAST);
    try {
      rover.processCommand('R', plateau);
      rover.processCommand('M', plateau);
      assertEquals(1, rover.getXCoord());
      assertEquals(1, rover.getYCoord());
      
    } catch (RoverCommandException e) {
      fail("Unexpected RoverCommandException" + e.getMessage());
    }   
  }

  @Test
  public void testTurnLeft() {
    Rover rover = new Rover(1, 2, Direction.EAST);
    try {
      rover.processCommand('L', plateau);
      rover.processCommand('M', plateau);
      assertEquals(1, rover.getXCoord());
      assertEquals(3, rover.getYCoord());
      
    } catch (RoverCommandException e) {
      fail("Unexpected RoverCommandException" + e.getMessage());
    }   
  }
  
  @Test
  public void testUnknownCommand() {
    Rover rover = new Rover(1, 2, Direction.EAST);
    try {
      rover.processCommand('F', plateau);
      fail("Expected RoverCommandException to be thrown");
    } catch (RoverCommandException e) {
      assertEquals("Unknown command", e.getMessage());    
    }
  }

  @Test
  public void testMoveTooFarNorth() {
    Rover rover = new Rover(1, 5, Direction.NORTH);
    try {
      rover.processCommand('M', plateau);
      fail("Expected RoverCommandException to be thrown");
    } catch (RoverCommandException e) {
      assertEquals("Cannot move", e.getMessage());    
    }
  }

  @Test
  public void testMoveTooFarEast() {
    Rover rover = new Rover(8, 3, Direction.EAST);
    try {
      rover.processCommand('M', plateau);
      fail("Expected RoverCommandException to be thrown");
    } catch (RoverCommandException e) {
      assertEquals("Cannot move", e.getMessage());    
    }
  }

  @Test
  public void testMoveTooFarSouth() {
    Rover rover = new Rover(1, 0, Direction.SOUTH);
    try {
      rover.processCommand('M', plateau);
      fail("Expected RoverCommandException to be thrown");
    } catch (RoverCommandException e) {
      assertEquals("Cannot move", e.getMessage());    
    }
  }

  @Test
  public void testMoveTooFarWest() {
    Rover rover = new Rover(0, 2, Direction.WEST);
    try {
      rover.processCommand('M', plateau);
      fail("Expected RoverCommandException to be thrown");
    } catch (RoverCommandException e) {
      assertEquals("Cannot move", e.getMessage());    
    }
  }

  @Test
  public void testMoveIntoRock() {
    Rover rover = new Rover(2, 0, Direction.SOUTH);
    try {
      rover.processCommand('M', plateau);
      fail("Expected RoverCommandException to be thrown");
    } catch (RoverCommandException e) {
      assertEquals("Cannot move", e.getMessage());    
    }
  }


}
