package org.mars;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a plateau on Mars.
 * The plateau is created by reading a file containing a high view.
 * 'o' means the land is plain and the rover can navigate through it, while 'R' means the high
 * view images showed there's a rock and the rover must go around it.
 */
public class Plateau {
  
  private static char NAVIGABLE_TERRAIN = 'o';
  private ArrayList<String> plateauStringArray;

  /**
   * Plateau constructor.
   * @param plateauFile File containing plateau high view configuration
   * @throws IOException if problem occurs reading plateauFile
   * @throws PlateauCreationException if plateauFile is empty or contains unexpected first character
   */
  public Plateau(File plateauFile) throws IOException, PlateauCreationException {
    plateauStringArray = new ArrayList<String>();
    
    try (BufferedReader reader = new BufferedReader(new FileReader(plateauFile))) {
      while (reader.ready()) {
        plateauStringArray.add(reader.readLine());
      }
      
      if (plateauStringArray.isEmpty()) {
        throw new PlateauCreationException("Plateau file is empty");
      } else if (plateauStringArray.get(0).charAt(0) != NAVIGABLE_TERRAIN) {
        throw new PlateauCreationException("First character of plateau file should be " 
              + NAVIGABLE_TERRAIN);
      }
    }
  }
  
  /**
   * Generate high view of plateau with rover position marked at (x, y) with an X.
   * @param x 'x' coordinate of Rover
   * @param y 'y' coordinate of Rover
   * @return high view of plateau
   */
  public String mapWithRover(int x, int y) {
    StringBuffer outputString = new StringBuffer("");
    for (int i = 0; i < plateauStringArray.size(); i++) {
      if (i == plateauStringArray.size() - 1 - y) {
        // Found line with Rover in it, now insert X into position
        String line = plateauStringArray.get(i);
        outputString.append(line.substring(0, x) + "X" + line.substring(x + 1, line.length()));
      } else {
        outputString.append(plateauStringArray.get(i));
      }
      outputString.append("\n");
    }
    return outputString.toString();
  }
  
  /**
   * Test whether a position in the plateau is navigable.
   * @param x 'x' coordinate to test
   * @param y 'y' coordinate to test
   * @return true if position is navigable by the Rover
   */
  public boolean isNavigable(int x, int y) {
    // First check if position is inside plateau
    if (y < 0 || y >= plateauStringArray.size()) {
      return false;
    }
    int arrayIndex = plateauStringArray.size() - 1 - y;
    if (x < 0 || x >= plateauStringArray.get(arrayIndex).length()) {
      return false;
    }
    
    // Now check type of terrain
    return (plateauStringArray.get(arrayIndex).charAt(x) == NAVIGABLE_TERRAIN);
  }
  
  /**
   * Get north most 'y' coordinate in plateau.
   * @return north most 'y' coordinate
   */
  public int getTopY() {
    return plateauStringArray.size() - 1;
  }

}
