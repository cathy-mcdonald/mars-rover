# Mars Rover

Command line program which simulates moving a robotic rover around a plateau on the surface of Mars.

## Running the program

Clone the git repository.

### Configuration

The program requires a file 'plateau.txt' in the working directory containing the plateau high view configuration.

In the file 'o' represents plain which the rover can navigate through and 'R" represents rock.

Example file content:
```
ooooooRRRR
ooRooooooo
ooooooRRoo
ooRooooooo
oooooRoooo
oooooRRRoo
```

### Running

The program can be run in the MarsRover directory with:

`./gradlew run --console=plain`

The plateau will be shown with the rover marked with an 'X'.

```
XoooooRRRR
ooRooooooo
ooooooRRoo
ooRooooooo
oooooRoooo
oooooRRRoo
```

North is taken to be at the top of the plain as shown and the rover will start facing East (i.e. to the right).

## Running the JUnit tests

Unit tests exist for each of the Direction, Plateau and Rover classes as well as some tests of the command line processor.

Gradle can run the entire test suite.

`.\gradlew test`

The commands are:

'M' - move forward one grid point and maintain the same heading

'L' - spin 90 degrees to the left

'R' - spin 90 degrees to the right

'X' - exit
