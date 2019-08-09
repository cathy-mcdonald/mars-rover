package org.mars;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class RoverCommandInterpreter {
  
  private static final String PLATEAU_FILE_PATH = "plateau.txt";
  private static Plateau plateau;
  private static Rover rover;
  private static int commandCount;
  private static int failedCount;

  public static void main(String[] args) {
    try {
      setPlateau(PLATEAU_FILE_PATH);
      processInput(new InputStreamReader(System.in));
    } catch (IOException | PlateauCreationException e) {
      System.err.println(e.toString());      
    }
  }
  
  public static void setPlateau(String fileName) throws IOException, PlateauCreationException {
    plateau = new Plateau(new File(fileName));
  }
  
  public static void processInput(Reader reader) throws IOException {

    int data = 0;
    char dataChar = '\n';
    commandCount = 0;
    failedCount = 0;
    rover = new Rover(0, plateau.getTopY(), Direction.EAST);
   
    System.out.println("Mars Rover v1.0 running, plateau configuration is:");
    
    while ((data != -1) && (Character.toUpperCase(dataChar) != 'X')) {
      if (dataChar == '\n') {
        System.out.println("\n" + plateau.mapWithRover(rover.getXCoord(), rover.getYCoord()));
        System.out.println("Waiting for commands.");
        System.out.print(">");
      } else {
        processCommand(dataChar);
      }
      
      data = reader.read();
      dataChar = (char) data;
    }
    
    System.out.println("\nSent " + commandCount + " command(s) / " + failedCount + " failed.\n");
    System.out.println("Mars Rover v1.0 closed.");
  }
  
  public static void processCommand(char command) {
    commandCount++;
    switch (command) {
      case 'M':
        try {
          rover.move(plateau);
        } catch (RoverCommandException e) {
          failedCount++;
          System.err.println(e.toString());
        }       
        break;
        
      case 'R':
        rover.turnRight();
        break;
        
      case 'L':
        rover.turnLeft();
        break;
        
      default:
        System.err.println("Unknown command");
    }
  }

}
