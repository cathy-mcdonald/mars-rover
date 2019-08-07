package org.mars;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommandLineTests {
  private static final String EOL = System.getProperty("line.separator");
  private static final String ROVER_CLOSED = "Mars Rover v1.0 closed." + EOL;
  private PrintStream console;
  private ByteArrayOutputStream bytes;

  @Before
  public void setUp() throws Exception {
    bytes = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(bytes));
  }

  @After
  public void tearDown() throws Exception {
    System.setOut(console);
  }

  @Test
  public void test() {
    String[] args = new String[0];
    RoverCommandInterpreter.main(args);
    assertEquals(ROVER_CLOSED, bytes.toString());
  }

}
