package org.mars;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommandLineTests {
  private static final String EOL = System.getProperty("line.separator");
  private static final String ROVER_RUNNING = "Mars Rover v1.0 running, plateau configuration is:" + EOL;
  private static final String WAITING = "Waiting for commands." + EOL + ">";
  private static final String ROVER_CLOSED = EOL + "Mars Rover v1.0 closed." + EOL;
  private PrintStream console;
  private ByteArrayOutputStream outputBytes;
  private String expectedPlateau;

  @Before
  public void setUp() throws Exception {
    outputBytes = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(outputBytes));
    RoverCommandInterpreter.setPlateau("src/test/resources/plateau.txt");
    expectedPlateau = EOL + "XoooooRRRR" + EOL
        + "ooRooooooo" + EOL
        + "ooooooRRoo" + EOL
        + "ooRooooooo" + EOL
        + "oooooRoooo" + EOL
        + "oooooRRRoo" + EOL + EOL;
  }

  @After
  public void tearDown() throws Exception {
    System.setOut(console);
  }

  @Test
  public void testDoNothing() {
    String inputString = "x";
    StringReader input = new StringReader(inputString);
    String expectedResult = EOL + "Sent 0 command(s) / 0 failed." + EOL;
    
    try {
      RoverCommandInterpreter.processInput(input);
    } catch (IOException e) {
      fail("Unexpected IOException " + e.toString());
    }
    
    int outputCursor = 0;
    String outputString = outputBytes.toString();
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_RUNNING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedResult);
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_CLOSED);
  }
  
  @Test
  public void testMoveOnce() {
    String inputString = "M\nX";
    StringReader input = new StringReader(inputString);
    String expectedNextPlateau = EOL + "oXooooRRRR" + EOL
        + "ooRooooooo" + EOL
        + "ooooooRRoo" + EOL
        + "ooRooooooo" + EOL
        + "oooooRoooo" + EOL
        + "oooooRRRoo" + EOL + EOL;
    String expectedResult = EOL + "Sent 1 command(s) / 0 failed." + EOL;
    
    try {
      RoverCommandInterpreter.processInput(input);
    } catch (IOException e) {
      fail("Unexpected IOException " + e.toString());
    }
    

    int outputCursor = 0;
    String outputString = outputBytes.toString();
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_RUNNING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedNextPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedResult);
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_CLOSED);
  }

  @Test
  public void testMultipleCommands() {
    String inputString = "M\nM\nM\nR\nM\nX";
    StringReader input = new StringReader(inputString);
    String expectedPlateau2 = EOL + "oXooooRRRR" + EOL
        + "ooRooooooo" + EOL
        + "ooooooRRoo" + EOL
        + "ooRooooooo" + EOL
        + "oooooRoooo" + EOL
        + "oooooRRRoo" + EOL + EOL;
    String expectedPlateau3 = EOL + "ooXoooRRRR" + EOL
        + "ooRooooooo" + EOL
        + "ooooooRRoo" + EOL
        + "ooRooooooo" + EOL
        + "oooooRoooo" + EOL
        + "oooooRRRoo" + EOL + EOL;
    String expectedPlateau4 = EOL + "oooXooRRRR" + EOL
        + "ooRooooooo" + EOL
        + "ooooooRRoo" + EOL
        + "ooRooooooo" + EOL
        + "oooooRoooo" + EOL
        + "oooooRRRoo" + EOL + EOL;
    String expectedPlateau5 = EOL + "ooooooRRRR" + EOL
        + "ooRXoooooo" + EOL
        + "ooooooRRoo" + EOL
        + "ooRooooooo" + EOL
        + "oooooRoooo" + EOL
        + "oooooRRRoo" + EOL + EOL;
    String expectedResult = EOL + "Sent 5 command(s) / 0 failed." + EOL;
    
    try {
      RoverCommandInterpreter.processInput(input);
    } catch (IOException e) {
      fail("Unexpected IOException " + e.toString());
    }
    
    int outputCursor = 0;
    String outputString = outputBytes.toString();
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_RUNNING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau2);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau3);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau4);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau4);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau5);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedResult);
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_CLOSED);
  }

  @Test
  public void testMultipleCommandsOneLine() {
    String inputString = "MMMRM\nX";
    StringReader input = new StringReader(inputString);
    String expectedNextPlateau = EOL + "ooooooRRRR" + EOL
        + "ooRXoooooo" + EOL
        + "ooooooRRoo" + EOL
        + "ooRooooooo" + EOL
        + "oooooRoooo" + EOL
        + "oooooRRRoo" + EOL + EOL;
    String expectedResult = EOL + "Sent 5 command(s) / 0 failed." + EOL;
    
    try {
      RoverCommandInterpreter.processInput(input);
    } catch (IOException e) {
      fail("Unexpected IOException " + e.toString());
    }
    

    int outputCursor = 0;
    String outputString = outputBytes.toString();
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_RUNNING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedNextPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedResult);
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_CLOSED);
  }
  
  @Test
  public void testMultipleCommandsLowercase() {
    String inputString = "mmmrm\nx";
    StringReader input = new StringReader(inputString);
    String expectedNextPlateau = EOL + "ooooooRRRR" + EOL
        + "ooRXoooooo" + EOL
        + "ooooooRRoo" + EOL
        + "ooRooooooo" + EOL
        + "oooooRoooo" + EOL
        + "oooooRRRoo" + EOL + EOL;
    String expectedResult = EOL + "Sent 5 command(s) / 0 failed." + EOL;
    
    try {
      RoverCommandInterpreter.processInput(input);
    } catch (IOException e) {
      fail("Unexpected IOException " + e.toString());
    }
    

    int outputCursor = 0;
    String outputString = outputBytes.toString();
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_RUNNING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedNextPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedResult);
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_CLOSED);
  }
  
  @Test
  public void testLeftTurn() {
    String inputString = "MRMMLM\nX";
    StringReader input = new StringReader(inputString);
    String expectedNextPlateau = EOL + "ooooooRRRR" + EOL
        + "ooRooooooo" + EOL
        + "ooXoooRRoo" + EOL
        + "ooRooooooo" + EOL
        + "oooooRoooo" + EOL
        + "oooooRRRoo" + EOL + EOL;
    String expectedResult = EOL + "Sent 6 command(s) / 0 failed." + EOL;
    
    try {
      RoverCommandInterpreter.processInput(input);
    } catch (IOException e) {
      fail("Unexpected IOException " + e.toString());
    }
    
    int outputCursor = 0;
    String outputString = outputBytes.toString();
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_RUNNING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedNextPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedResult);
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_CLOSED);
  }
  
  @Test
  public void testTooFarNorthFailure() {
    String inputString = "LM\nX";
    StringReader input = new StringReader(inputString);
    String expectedNextPlateau = EOL + "XoooooRRRR" + EOL
        + "ooRooooooo" + EOL
        + "ooooooRRoo" + EOL
        + "ooRooooooo" + EOL
        + "oooooRoooo" + EOL
        + "oooooRRRoo" + EOL + EOL;
    String expectedResult = EOL + "Sent 2 command(s) / 1 failed." + EOL;
    
    try {
      RoverCommandInterpreter.processInput(input);
    } catch (IOException e) {
      fail("Unexpected IOException " + e.toString());
    }
    
    int outputCursor = 0;
    String outputString = outputBytes.toString();
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_RUNNING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedNextPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedResult);
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_CLOSED);
  }

  @Test
  public void testTooFarSouthFailure() {
    String inputString = "RMMMMMM\nX";
    StringReader input = new StringReader(inputString);
    String expectedNextPlateau = EOL + "ooooooRRRR" + EOL
        + "ooRooooooo" + EOL
        + "ooooooRRoo" + EOL
        + "ooRooooooo" + EOL
        + "oooooRoooo" + EOL
        + "XooooRRRoo" + EOL + EOL;
    String expectedResult = EOL + "Sent 7 command(s) / 1 failed." + EOL;
    
    try {
      RoverCommandInterpreter.processInput(input);
    } catch (IOException e) {
      fail("Unexpected IOException " + e.toString());
    }
    
    int outputCursor = 0;
    String outputString = outputBytes.toString();
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_RUNNING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedNextPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedResult);
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_CLOSED);
  }
  
  @Test
  public void testTooFarWestFailure() {
    String inputString = "RRM\nX";
    StringReader input = new StringReader(inputString);
    String expectedNextPlateau = EOL + "XoooooRRRR" + EOL
        + "ooRooooooo" + EOL
        + "ooooooRRoo" + EOL
        + "ooRooooooo" + EOL
        + "oooooRoooo" + EOL
        + "oooooRRRoo" + EOL + EOL;
    String expectedResult = EOL + "Sent 3 command(s) / 1 failed." + EOL;
    
    try {
      RoverCommandInterpreter.processInput(input);
    } catch (IOException e) {
      fail("Unexpected IOException " + e.toString());
    }
    
    int outputCursor = 0;
    String outputString = outputBytes.toString();
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_RUNNING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedNextPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedResult);
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_CLOSED);
  }
  
  @Test
  public void testTooFarEastFailure() {
    String inputString = "MMMMMRMLMMMMM\nX";
    StringReader input = new StringReader(inputString);
    String expectedNextPlateau = EOL + "ooooooRRRR" + EOL
        + "ooRooooooX" + EOL
        + "ooooooRRoo" + EOL
        + "ooRooooooo" + EOL
        + "oooooRoooo" + EOL
        + "oooooRRRoo" + EOL + EOL;
    String expectedResult = EOL + "Sent 13 command(s) / 1 failed." + EOL;
    
    try {
      RoverCommandInterpreter.processInput(input);
    } catch (IOException e) {
      fail("Unexpected IOException " + e.toString());
    }
    
    int outputCursor = 0;
    String outputString = outputBytes.toString();
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_RUNNING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedNextPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedResult);
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_CLOSED);
  }
  
  @Test
  public void testRockFailure() {
    String inputString = "MMRM\nX";
    StringReader input = new StringReader(inputString);
    String expectedNextPlateau = EOL + "ooXoooRRRR" + EOL
        + "ooRooooooo" + EOL
        + "ooooooRRoo" + EOL
        + "ooRooooooo" + EOL
        + "oooooRoooo" + EOL
        + "oooooRRRoo" + EOL + EOL;
    String expectedResult = EOL + "Sent 4 command(s) / 1 failed." + EOL;
    
    try {
      RoverCommandInterpreter.processInput(input);
    } catch (IOException e) {
      fail("Unexpected IOException " + e.toString());
    }
    
    int outputCursor = 0;
    String outputString = outputBytes.toString();
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_RUNNING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedNextPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedResult);
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_CLOSED);
  }

  @Test
  public void testThreeFailures() {
    String inputString = "LMRMMRMLMMMM\nX";
    StringReader input = new StringReader(inputString);
    String expectedNextPlateau = EOL + "oooooXRRRR" + EOL
        + "ooRooooooo" + EOL
        + "ooooooRRoo" + EOL
        + "ooRooooooo" + EOL
        + "oooooRoooo" + EOL
        + "oooooRRRoo" + EOL + EOL;
    String expectedResult = EOL + "Sent 12 command(s) / 3 failed." + EOL;
    
    try {
      RoverCommandInterpreter.processInput(input);
    } catch (IOException e) {
      fail("Unexpected IOException " + e.toString());
    }
    
    int outputCursor = 0;
    String outputString = outputBytes.toString();
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_RUNNING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedNextPlateau);
    outputCursor = validatePartialOutput(outputString, outputCursor, WAITING);
    outputCursor = validatePartialOutput(outputString, outputCursor, expectedResult);
    outputCursor = validatePartialOutput(outputString, outputCursor, ROVER_CLOSED);
  }
  
  @Test
  public void testFileNotFound() {
    try {
      RoverCommandInterpreter.setPlateau("no_file.txt");
      fail("Expected FileNotFoundException");
    } catch (IOException e) {
      assertEquals(e.getClass(), FileNotFoundException.class);
    } catch (PlateauCreationException e) {
      fail("Expected FileNotFoundException");      
    }
  }

  private int validatePartialOutput(String outputString, int outputCursor, String expectedString) {
    assertEquals(expectedString, outputString.substring(outputCursor, outputCursor + expectedString.length()));
    return outputCursor + expectedString.length();
  }
}
