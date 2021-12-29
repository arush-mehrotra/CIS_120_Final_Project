# Snake
I recreated Snake, inspired by the classic arcade game Snake, with additional features for my CIS 120 Final Project.

## Directory Layout

```bash
├── src/main/java/org/cis120/snake/
│   ├── Apple.java          # Represents the Apple object and controls its location on the GameCourt
│   ├── Direction.java      # Indicates the direction an object should move after it collides with an object
│   ├── GameCourt.java      # Holds the primary game logic for how different objects interact with one another
│   ├── GameObj.java        # Includes the getters and setters to control the location of where the different game objects are and also determine if any two of these objects' bounding boxes intersect with one another to implement a collision feature 
│   ├── GoldenApple.java    # Represents the GoldenApple object and controls its location on the GameCourt
│   └── Mine.java           # Represents the Mine object and controls its location on the GameCourt
│   ├── Powerups.java       # Includes the method definitions for the methods that are then uniquely defined in Mine.java, Apple.java, and GoldenApple.java
│   └── RunSnake.java       # Specifies the frame and widgets of the GUI
│   ├── Snake.java          # Defines how a Snake is composed as a LinkedList of SnakeComponents and includes methods to determine how the snake moves/interacts with game objects
│   └── SnakeComponent.java # Fundamental building block of the Snake itself

```

## Running The Game

After compiling, run the following command inside the src folder

```bash
java Game
```
