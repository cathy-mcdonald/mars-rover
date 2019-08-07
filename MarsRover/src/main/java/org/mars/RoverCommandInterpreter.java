package org.mars;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class RoverCommandInterpreter {
  
  private static Plateau plateau;

  public static void main(String[] args) {
    setPlateau("plateau.txt");
    System.out.println("Mars Rover v1.0 running, plateau configuration is:");
    processInput(new InputStreamReader(System.in));
  }
  
  public static void setPlateau(String fileName) {
    plateau = new Plateau(new File(fileName));
  }
  
  public static void processInput(Reader reader) {

    int data = 0;
    char dataChar = '\n';
    
    while ((data != -1) && (Character.toUpperCase(dataChar) != 'X')) {
      if (dataChar == '\n') {
        System.out.println(plateau.mapWithRover(0, 5));
        System.out.println("Waiting for commands.");
        System.out.print(">");
      } else {
        processCommand(dataChar);
      }
      try {
        data = reader.read();
      } catch (IOException e) {
        e.printStackTrace();
      }
      dataChar = (char) data;
    }
 
    System.out.println("Mars Rover v1.0 closed.");
    
  }
  
  public static void processCommand(char command) {
    //TODO Make the rover do stuff
  }

}
