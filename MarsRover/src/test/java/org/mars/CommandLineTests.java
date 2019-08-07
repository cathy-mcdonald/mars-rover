package org.mars;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommandLineTests {
  private static final String EOL = System.getProperty("line.separator");
  private static final String WAITING = "Waiting for commands." + EOL + ">";
  private static final String ROVER_CLOSED = "Mars Rover v1.0 closed." + EOL;
  private PrintStream console;
  private ByteArrayOutputStream bytes;

  @Before
  public void setUp() throws Exception {
    bytes = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(bytes));
    RoverCommandInterpreter.setPlateau("src/test/resources/plateau.txt");
  }

  @After
  public void tearDown() throws Exception {
    System.setOut(console);
  }

  @Test
  public void testDoNothing() {
    String inputString = "x";
    StringReader input = new StringReader(inputString);
    RoverCommandInterpreter.processInput(input);
    String expectedPlateau = "XoooooRRRR\n"
        + "ooRooooooo\n"
        + "ooooooRRoo\n"
        + "ooRooooooo\n"
        + "oooooRoooo\n"
        + "oooooRRRoo\n\n";
    assertEquals(expectedPlateau + WAITING + ROVER_CLOSED, bytes.toString());
  }
  
  @Test
  public void testMoveOnce() {
    String inputString = "M\nX";
    StringReader input = new StringReader(inputString);
    String expectedPlateau = "XoooooRRRR\n"
        + "ooRooooooo\n"
        + "ooooooRRoo\n"
        + "ooRooooooo\n"
        + "oooooRoooo\n"
        + "oooooRRRoo\n\n";
    String expectedNextPlateau = "oXooooRRRR\n"
        + "ooRooooooo\n"
        + "ooooooRRoo\n"
        + "ooRooooooo\n"
        + "oooooRoooo\n"
        + "oooooRRRoo\n\n";
    RoverCommandInterpreter.processInput(input);
    assertEquals(expectedPlateau + WAITING + expectedNextPlateau + WAITING + ROVER_CLOSED, bytes.toString());
  }

}
