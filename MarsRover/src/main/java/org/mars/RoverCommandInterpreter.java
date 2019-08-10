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
  
  /**
   * Process command input for the robotic rover.
   * Print out the initial plateau configuration.
   * Process each one character command.
   * After each line of input print out the updated plateau configuration.
   * Exit when the input 'X' or 'x' is encountered.
   * Print summary of total number of commands and failed commands.
   * 
   * @param reader Reader for input stream
   * @throws IOException if a problem is encountered reading the input
   */
  public static void processInput(Reader reader) throws IOException {

    commandCount = 0;
    failedCount = 0;
    
    // Initialise rover in top-left (i.e. Northwest) corner of plateau facing east
    rover = new Rover(0, plateau.getTopY(), Direction.EAST);
   
    System.out.println("Mars Rover v1.0 running, plateau configuration is:");
    
    int data = 0;
    char dataChar = '\n';
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
  
  /**
   * Interpret one letter command and invoke it on the robotic rover.
   * Maintain a count of total number of commands and failed commands.
   * @param command One letter command
   */
  public static void processCommand(char command) {
    commandCount++;
    switch (Character.toUpperCase(command)) {
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
        System.err.println("Unknown command. Accepted commands are 'L', 'R' and 'M'. 'X' to exit.");
    }
  }

}
