package org.mars;

public class Rover {
  private int xCoord;
  private int yCoord;
  private Direction direction;

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

  public void processCommand(char command, Plateau plateau) throws RoverCommandException {
    switch (command) {
      case 'M':
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
        }
        // TODO test if the new position is valid in the plateau
        xCoord = newXCoord;
        yCoord = newYCoord;
        break;
        
      case 'R':
        direction = direction.right();
        break;
        
      case 'L':
        direction = direction.left();
        break;
        
      default:
        throw new RoverCommandException("Unknown command");
    }
  }
  
}

