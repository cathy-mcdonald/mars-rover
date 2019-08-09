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
  public void testMoveEast() {
    Rover rover = new Rover(0, 0, Direction.EAST);
    try {
      assertEquals(0, rover.getXCoord());
      assertEquals(0, rover.getYCoord());
      rover.move(plateau);
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
      assertEquals(3, rover.getXCoord());
      assertEquals(0, rover.getYCoord());
      rover.move(plateau);
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
      assertEquals(1, rover.getXCoord());
      assertEquals(2, rover.getYCoord());
      rover.move(plateau);
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
      assertEquals(1, rover.getXCoord());
      assertEquals(2, rover.getYCoord());
      rover.move(plateau);
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
      assertEquals(1, rover.getXCoord());
      assertEquals(2, rover.getYCoord());
      rover.turnRight();
      rover.move(plateau);
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
      assertEquals(1, rover.getXCoord());
      assertEquals(2, rover.getYCoord());
      rover.turnLeft();
      rover.move(plateau);
      assertEquals(1, rover.getXCoord());
      assertEquals(3, rover.getYCoord());     
    } catch (RoverCommandException e) {
      fail("Unexpected RoverCommandException" + e.getMessage());
    }   
  }
  
  @Test
  public void testMoveTooFarNorth() {
    Rover rover = new Rover(1, 5, Direction.NORTH);
    try {
      assertEquals(1, rover.getXCoord());
      assertEquals(5, rover.getYCoord());
      rover.move(plateau);
      fail("Expected RoverCommandException to be thrown");
    } catch (RoverCommandException e) {
      assertEquals("Cannot move", e.getMessage());    
    }
  }

  @Test
  public void testMoveTooFarEast() {
    Rover rover = new Rover(9, 3, Direction.EAST);
    try {
      assertEquals(9, rover.getXCoord());
      assertEquals(3, rover.getYCoord());
      rover.move(plateau);
      fail("Expected RoverCommandException to be thrown");
    } catch (RoverCommandException e) {
      assertEquals("Cannot move", e.getMessage());    
    }
  }

  @Test
  public void testMoveTooFarSouth() {
    Rover rover = new Rover(1, 0, Direction.SOUTH);
    try {
      assertEquals(1, rover.getXCoord());
      assertEquals(0, rover.getYCoord());
      rover.move(plateau);
      fail("Expected RoverCommandException to be thrown");
    } catch (RoverCommandException e) {
      assertEquals("Cannot move", e.getMessage());    
    }
  }

  @Test
  public void testMoveTooFarWest() {
    Rover rover = new Rover(0, 2, Direction.WEST);
    try {
      assertEquals(0, rover.getXCoord());
      assertEquals(2, rover.getYCoord());
      rover.move(plateau);
      fail("Expected RoverCommandException to be thrown");
    } catch (RoverCommandException e) {
      assertEquals("Cannot move", e.getMessage());    
    }
  }

  @Test
  public void testMoveIntoRock() {
    Rover rover = new Rover(2, 5, Direction.SOUTH);
    try {
      assertEquals(2, rover.getXCoord());
      assertEquals(5, rover.getYCoord());
      rover.move(plateau);
      fail("Expected RoverCommandException to be thrown");
    } catch (RoverCommandException e) {
      assertEquals("Cannot move", e.getMessage());    
    }
  }

  @Test
  public void testMoveNotQuiteTooFarNorth() {
    Rover rover = new Rover(1, 4, Direction.NORTH);
    try {
      assertEquals(1, rover.getXCoord());
      assertEquals(4, rover.getYCoord());
      rover.move(plateau);
      assertEquals(1, rover.getXCoord());
      assertEquals(5, rover.getYCoord());      
    } catch (RoverCommandException e) {
      fail("Unexpected RoverCommandException" + e.getMessage());
    }   
  }

  @Test
  public void testNotQuiteTooFarEast() {
    Rover rover = new Rover(8, 3, Direction.EAST);
    try {
      assertEquals(8, rover.getXCoord());
      assertEquals(3, rover.getYCoord());
      rover.move(plateau);
      assertEquals(9, rover.getXCoord());      
      assertEquals(3, rover.getYCoord());
    } catch (RoverCommandException e) {
      fail("Unexpected RoverCommandException" + e.getMessage());
    }   
  }

  @Test
  public void testNotQuiteTooFarSouth() {
    Rover rover = new Rover(1, 1, Direction.SOUTH);
    try {
      assertEquals(1, rover.getXCoord());
      assertEquals(1, rover.getYCoord());
      rover.move(plateau);
      assertEquals(1, rover.getXCoord());
      assertEquals(0, rover.getYCoord());      
    } catch (RoverCommandException e) {
      fail("Unexpected RoverCommandException" + e.getMessage());
    }   
  }

  @Test
  public void testNotQuiteTooFarWest() {
    Rover rover = new Rover(1, 2, Direction.WEST);
    try {
      assertEquals(1, rover.getXCoord());
      assertEquals(2, rover.getYCoord());
      rover.move(plateau);
      assertEquals(0, rover.getXCoord());      
      assertEquals(2, rover.getYCoord());
    } catch (RoverCommandException e) {
      fail("Unexpected RoverCommandException" + e.getMessage());
    }   
  }

}
