package org.mars;

public enum Direction {
  EAST, 
  SOUTH, 
  WEST, 
  NORTH;
  
  public Direction left() {
    if (this == EAST) return NORTH;
    return values()[ordinal() - 1];
  }
  
  public Direction right() {
    if (this == NORTH) return EAST;
    return values()[ordinal() + 1];
  }

}
