package org.mars;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Plateau {
  
  private static char NAVIGABLE_TERRAIN = 'o';
  private ArrayList<String> plateauStringArray;

  public Plateau(File plateauFile) throws IOException, PlateauCreationException {
    plateauStringArray = new ArrayList<String>();
    
    BufferedReader reader = new BufferedReader(new FileReader(plateauFile));
    while (reader.ready()) {
      plateauStringArray.add(reader.readLine());
    }
    
    // Do basic validation on first character of file
    if (plateauStringArray.isEmpty()) {
      throw new PlateauCreationException("Plateau file is empty");
    } else if (plateauStringArray.get(0).charAt(0) != NAVIGABLE_TERRAIN) {
      throw new PlateauCreationException("First character of plateau file should be " 
            + NAVIGABLE_TERRAIN);
    }
  }
  
  // Output map of plateau with rover position marked at (x, y) with an X
  public String mapWithRover(int x, int y) {
    StringBuffer outputString = new StringBuffer("");
    for (int i = 0; i < plateauStringArray.size(); i++) {
      if (i == plateauStringArray.size() - 1 - y) {
        String line = plateauStringArray.get(i);
        outputString.append(line.substring(0, x) + "X" + line.substring(x + 1, line.length()));
      } else {
        outputString.append(plateauStringArray.get(i));
      }
      outputString.append("\n");
    }
    return outputString.toString();
  }
  
  // Test if a position in the plateau is navigable
  public boolean isNavigable(int x, int y) {
    // First check if position is inside plateau
    if (y < 0 || y >= plateauStringArray.size()) return false;
    int arrayIndex = plateauStringArray.size() - 1 - y;
    if (x < 0 || x >= plateauStringArray.get(arrayIndex).length()) return false;
    
    return (plateauStringArray.get(arrayIndex).charAt(x) == NAVIGABLE_TERRAIN);
  }
  
  public int getTopLeftY() {
    return plateauStringArray.size() -1;
  }

}
