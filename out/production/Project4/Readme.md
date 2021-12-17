## About/Overview

<i>The problem requires us to design and implement a graphical adventure game controller for our
dungeon adventure game. While the console based approach is supported, we upgrade ourselves to
playing the game using an interactive graphical user interface. The dungeon starts of with a blank
screen except the location where the player resides. Each location in the dungeon will be visible
when the player explores that area. The user has the ability to move the player using mouse click or
key press. The player can pick up items by pressing 'P' from the keyboard and specifying the type of
item they want. The player can shoot an arrow through an 'S' key press. A prompt opens requiring the
user to specify the distance and direction to shoot. The presence of an Otyugh nearby a player is
indicated through a stench shown at the player's location. The game settings, description and
actions taken by the user are all displayed at the bottom of the screen. When the game ends due to
the player getting eaten by an Otyugh or reaching the end location, the user can restart the game
with a new dungeon or the previous dungeon with mostly similar characteristics with the help of a
menu bar provided at the top of the game window. Moreover, the user can read about the game
information provided in the menu bar and also quit the game if they want.</i>

## List of features

* Creation of both non-wrapping and wrapping dungeons with different degrees of interconnectivity.

* Support for at least three types of treasure: Diamonds, Rubies, and Sapphires.

* Treasure to be added to a specified percentage of caves.

* A player to enter the dungeon at the start.

* Provide a description of the player that includes their name and treasure collected.

* Provide a description of the player's location that includes the description of cave, the tunnels
  connected to that cave, the treasures present in that cave and set of possible moves from that
  cave.

* A player to move from their current location to another location.

* A player to pick up treasure that is located in their same location.

* Demonstrate the player navigating the dungeon, collecting treasure, and reaching the end location.

* Display the state of the dungeon at each time step.

* Assign a specified number of Otyughs to the dungeon as given by the user.

* Arrows to be added in the same frequency as treasures to both tunnels and caves.

* Enable a player to begin with 3 arrows.

* A player to shoot an arrow to a specified distance and direction as given by user.

* Allow a player to kill an Otyugh by shooting arrows.

* A player to pick up arrows from their current location or when passing through a tunnel.

* Display of Game settings which included the size of the dungeon, the interconnectivity, whether it
  is non-wrapping or not, the percentage of caves that have treasure, and the number of Otyughs
  through one or more items on a JMenu.

* Provided an option for quitting the game, restarting the game as a new game with a new dungeon or
  reusing the same dungeon through one or more items on a JMenu.

* Displays the dungeon to the screen using graphical representation. The view begins with a mostly
  blank screen and displays only the pieces of the maze that have been revealed by the user's
  exploration of the caves and tunnels.

* Provided the ability to scroll for Dungeons bigger than the area allocated to the screen.

* Allows the player to move through the dungeon using a mouse click on the screen in addition to the
  keyboard arrow keys. A click on an invalid space in the game would not advance the player.

* Displays the details of a dungeon location to the screen such as presence of treasures, arrows,
  smell of monsters.

* Displays the player's description at each step.

* Provides an option for the player to pick up a treasure or an arrow through pressing a key on the
  keyboard.

* Provides an option for the player to shoot an arrow by pressing a key on the keyboard.

* Provides a clear indication of the results of each action a player takes at each step.

## How To Run

#### Running the JAR File:

    1. Hover over to the "Select Run/Debug Configuration" present on the topmost-right corner of Intellij IDEA.
	
	2. Select "Project5" from the list of options.
	
	3. Click on "Run" option beside it or press "Shift+F10".

## How to Use the Program

    1. Run the JAR file as given in the "Running the Jar File" section above.

    2. Initially, the game starts with a default GUI based dungeon.

    3. User can navigate by pressing the arrow keys or mouse click to the desired cell.

    4. User can pickup items by pressing 'P'. A prompt opens up asking for which item to pick.
       |_ Type 'A' - pickup arrow
       |_ Type 'D' - pickup diamond
       |_ Type 'S' - pickup sapphire
       |_ Type 'R' - pickup ruby

    5. User can shoot an arrow by pressing 'S'. A prompt opens asking for the distance and direction.
       |_ Example: distance - 2, direction - W
       |_ Example: distance - 5, direction - N

    6. User can restart the game with a new dungeon by hovering to 'Settings' located in the top left
       corner of the screen, clicking on 'Restart' and then 'New Dungeon'. Default values will be present, the user can change the values at their
       will. Press 'Ok' to start the game or press 'Cancel' to do nothing.

    7. User can restart the game with the same dungeon by hovering to 'Settings' location in the top
       left corner of the screen, clicking on 'Restart' and then 'Reuse Dungeon'.
    
    8. User can quit the game by hovering to 'Settings' located in the top left corner of the screen and clicking on 'Quit Game'
  
    9. For understanding the game, the user can hover over to 'Settings' located in the top left
       corner of the screen and clicking on 'Game Info'.

    10. User can still use the console based version by clicking on 'Run' in Intellij. Go to 'Edit Configurations'.
       On the top left side, select 'Applications' and then select 'Driver'. Under 'Build and Run', enter the 
       Command line argument as 'console'. Click on 'Apply' and then 'Ok'. This will change the configuration of the 
       game to a console based one.

## Description of Examples

#### Run 1 -- ExampleRun.PNG:
1. Player starts at a cave.
2. User presses left arrow key and the player moves left to reach a dead end cave containing a ruby.
3. User presses right arrow key and the player moves right to reach a cave containing an arrow and a ruby.
4. User presses down arrow key and the player moves down to reach a cave containing an arrow and a diamond.
5. User presses the right arrow key and the player moves right to reach a dead end cave.
6. User presses left arrow key, up and up arrow key to move the player to a cave containing an arrow and a sapphire.
7. User presses left arrow key to move the player to a cave containing an arrow and a sapphire.
8. User presses the down arrow key to reach a cave containing a ruby.
9. User presses the up, right and right arrow key to move to a cave containing stench.
10. User presses the left arrow key and stays there.

## Design/Model Changes


* 
* A new graphical controller interface has been included to handle the view related operations.

* An Otyugh interface has been included which is implemented by the OtyughImpl class. This allows us
  to create an otyugh, and also keep track of the otyugh states i.e. whether its alive or dead based
  on the number of hits taken.

* A Controller interface has been included for the dungeon with a class named '
  DungeonConsoleController' implementing the interface. This controller separates the model from the
  user inputs. It validates the text based console input entered by the user and passes them to the
  model to do a specific action.

## Assumptions

* The number of rows and columns in dungeon cannot be less than 5. Either one of row or column value
  must be greater than equal to 5. The reason being, manhattan distance between the start and end
  state is expected to be at least 5.

## Limitations

* A location is defined as a cave with tunnels inside it connecting to other caves. Therefore, a
  crooked arrow cannot bend through the tunnels and can only travel in a straight line.

## Citations

* generating a matrix with 0’s and 1’s in java, while selecting the probability. (n.d.). Stack
  Overflow. Retrieved November 3, 2021,
  from https://stackoverflow.com/questions/9142636/generating-a-matrix-with-0s-and-1s-in-java-while-selecting-the-probability


* Kruskal’s Algorithm – Minimum Spanning Tree (MST) - Complete Java Implementation |
  TutorialHorizon. (n.d.). Retrieved November 3, 2021,
  from https://algorithms.tutorialhorizon.com/kruskals-algorithm-minimum-spanning-tree-mst-complete-java-implementation/#:~:text=Kruskal%E2%80%99s%20Algorithm%20%E2%80%93%20Minimum%20Spanning%20Tree%20%28MST%29%20%E2%80%93


* How to use Java Collections Queue in Java. (n.d.). Retrieved November 3, 2021,
  from https://www.javainterviewpoint.com/use-java-collections-queue-java/#:~:text=%20How%20to%20use%20Java%20Collections%20Queue%20in


* Remove all elements from the ArrayList in Java. (2018, October 30).
  GeeksforGeeks. https://www.geeksforgeeks.org/remove-all-elements-from-the-arraylist-in-java/

* javax.swing (Java Platform SE 7 ). (2020, June 24). Package Javax.Swing. Retrieved December 5,
  2021, from https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html
