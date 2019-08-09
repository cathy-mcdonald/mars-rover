package org.mars;

/**
 * Represents a robotic rover on Mars.
 */ 
public class Rover {
  private int xCoord;
  private int yCoord;
  private Direction direction;

  /**
   * Rover constructor.
   * @param x           'x' coordinate of rover's location
   * @param y           'y' coordinate of rover's location
   * @param direction   Direction in which the rover is facing
   */
  public Rover(int x, int y, Direction direction) {
    xCoord = x;
    yCoord = y;
    this.direction = direction;
  }
  
  public int getXCoord() {
    return xCoord;
  }

  public int getYCoord() {
    return yCoord;
  }
  
  /**
   * Move forward one grid point and maintain the same heading.
   * @param plateau Plateau on which the rover is moving
   * @throws RoverCommandException if rover cannot move that way
   */
  public void move(Plateau plateau) throws RoverCommandException {
    int newXCoord = xCoord;
    int newYCoord = yCoord;
    
    switch (direction) {
      case NORTH:
        newYCoord++;
        break;
      case EAST:
        newXCoord++;
        break;
      case SOUTH:
        newYCoord--;
        break;
      case WEST:
        newXCoord--;
        break;
      default:
        throw new RoverCommandException("Unknown direction");
    }
    
    if (plateau.isNavigable(newXCoord, newYCoord)) {
      xCoord = newXCoord;
      yCoord = newYCoord;
    } else {
      throw new RoverCommandException("Cannot move");
    }  
  }
  
  /**
   * Spin 90 degrees left without moving from current location.
   */
  public void turnLeft() {
    direction = direction.left();
  }
  
  /**
   * Spin 90 degrees right without moving from current location.
   */
  public void turnRight() {
    direction = direction.right();
  }
  
}

