package org.mars;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Plateau {
  
  private static char NAVIGABLE_TERRAIN = 'o';
  private ArrayList<String> plateauStringArray;

  public Plateau(File plateauFile) {
    plateauStringArray = new ArrayList<String>();
    
    try (BufferedReader reader = new BufferedReader(new FileReader(plateauFile))) {
      while (reader.ready()) {
        plateauStringArray.add(reader.readLine());
      }
    } catch (IOException e) {
      System.err.println(e.toString());
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

}
