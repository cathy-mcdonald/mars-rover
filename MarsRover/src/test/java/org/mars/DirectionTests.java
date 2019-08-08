package org.mars;

import static org.junit.Assert.*;

import org.junit.Test;

public class DirectionTests {

  @Test
  public void testLeft() {
    assertEquals(Direction.NORTH, Direction.EAST.left());
    assertEquals(Direction.WEST, Direction.NORTH.left());
    assertEquals(Direction.SOUTH, Direction.WEST.left());
    assertEquals(Direction.EAST, Direction.SOUTH.left());
  }
  
  @Test
  public void testRight() {
    assertEquals(Direction.NORTH, Direction.WEST.right());
    assertEquals(Direction.WEST, Direction.SOUTH.right());
    assertEquals(Direction.SOUTH, Direction.EAST.right());
    assertEquals(Direction.EAST, Direction.NORTH.right());    
  }

}
