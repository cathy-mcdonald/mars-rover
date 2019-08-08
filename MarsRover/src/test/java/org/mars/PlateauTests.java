package org.mars;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlateauTests {

  private File plateauFile;
  
  @Before
  public void setUp() throws Exception {
    plateauFile = new File("src/test/resources/plateau.txt");
  }

  @After
  public void tearDown() throws Exception {
  }
  
  @Test
  public void testRoverAtStart() {
    try {
      Plateau plateau = new Plateau(plateauFile);
      String expectedOutput = "XoooooRRRR\n"
          + "ooRooooooo\n"
          + "ooooooRRoo\n"
          + "ooRooooooo\n"
          + "oooooRoooo\n"
          + "oooooRRRoo\n";
      assertEquals(expectedOutput, plateau.mapWithRover(0, 5));
    } catch (IOException | PlateauCreationException e) {
      fail("Unexpected Exception" + e.getMessage());
    }
  }

  @Test
  public void testRoverAt_0_0() {
    try {
      Plateau plateau = new Plateau(plateauFile);
      String expectedOutput = "ooooooRRRR\n"
          + "ooRooooooo\n"
          + "ooooooRRoo\n"
          + "ooRooooooo\n"
          + "oooooRoooo\n"
          + "XooooRRRoo\n";
      assertEquals(expectedOutput, plateau.mapWithRover(0, 0));
    } catch (IOException | PlateauCreationException e) {
      fail("Unexpected Exception" + e.getMessage());
    }
  }

  @Test
  public void testRoverAt_3_4() {
    try {
      Plateau plateau = new Plateau(plateauFile);
      String expectedOutput = "ooooooRRRR\n"
          + "ooRXoooooo\n"
          + "ooooooRRoo\n"
          + "ooRooooooo\n"
          + "oooooRoooo\n"
          + "oooooRRRoo\n";
      assertEquals(expectedOutput, plateau.mapWithRover(3, 4));
    } catch (IOException | PlateauCreationException e) {
      fail("Unexpected Exception" + e.getMessage());
    }
  }

  @Test
  public void testEmptyFile() {
    try {
      File emptyFile = new File("src/test/resources/empty.txt");
      Plateau plateau = new Plateau(emptyFile);
      fail("Expected PlateauCreationException");
    } catch (IOException e) {
      fail("Unexpected Exception" + e.getMessage());
    } catch (PlateauCreationException e) {
      // Exception as expected
    }
  }

  @Test
  public void testBadFileFormat() {
    try {
      File emptyFile = new File("src/test/resources/bad_format.txt");
      Plateau plateau = new Plateau(emptyFile);
      fail("Expected PlateauCreationException");
    } catch (IOException e) {
      fail("Unexpected Exception" + e.getMessage());
    } catch (PlateauCreationException e) {
      // Exception as expected
    }
  }

}
