package org.mars;

/**
 * Cardinal compass directions.
 */
public enum Direction {
  EAST, 
  SOUTH, 
  WEST, 
  NORTH;
  
  /**
   * Direction on right/clockwise.
   * @return direction on right
   */
  public Direction right() {
    return values()[(ordinal() + 1) % values().length];
  }

  /**
   * Direction on left/counterclockwise.
   * @return direction on left
   */
  public Direction left() {
    return values()[(ordinal() + values().length - 1) % values().length];
  }
  
}
