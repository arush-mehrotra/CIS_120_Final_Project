=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: arushm
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. File I/O - I used File I/O to track the high scores while playing the game by writing it to the txt file
  (highscore.txt). Given that simply writing the high scores was not enough, I also used a BufferedReader to read and
  determine the highest score and then display it on the GUI. I also caught and handled IOExceptions to ensure that the
  game does not crash. I also added a feature so that the entire game state is saved when you press the save button
  and it can be reloaded when you press the reload button.

  2. JUnit Testable Component - I implemented JUnit Tests in the GameTest file to ensure that the different methods
  that I wrote are accurately working. For example, I had tests that ensured that all getters/setters, edge cases,
  and overall game logic had correct behavior. I also made sure that the test cases did not overlap with each other.

  3. File Inheritance and Subtyping - I used inheritance and subtyping to represent the different objects that the
  snack is going to interact with. For example, I used the Powerups interfance and implemented it for the Apple, Mine,
  and GoldenApple objects which all have specific, distinct behaviors but share a common functionality in that they
  interact with the snake itself.

  4. Collections - I used collections to represent the Snake itself. I specifically used a LinkedList because of the
  features and methods that it allows me to use. For example, it is much easier to track the head and tail of a
  LinkedList using the peekFirst() and peekLast() method. Also, LinkedLists are mutable, which is necessary given that
  everytime the snake interacts with an apple or golden apple, an additional component is added.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  The Apple class represents the Apple object. It extends the GameObj class and implements the Powerups interface.
  The Direction class is used in GameObj.java to indicate the direction an object should move after it collides with
  an object. The GameCourt class holds the primary game logic for how different objects interact with one another.
  The GameObj class includes the getters and setters to control the location of where the different game objects are
  and also determine if any two of these objects' bounding boxes intersect with one another to implement a collision
  feature. The GoldenApple class represents the GoldenApple object. It extends the GameObj class and implements the
  Powerups interface. Unlike an Apple, it is not spawned every single time and it gives the user two points instead
  of one. The Mine class represents the mine (bomb) object. It extends the GameObj class and implements the Powerups
  interface. If a snake collides with a mine, the game ends. The RunSnake class specifies the frame and widgets of the
  GUI. The Snake class implements the Snake object. It is a LinkedList of SnakeComponents and includes methods
  controlling how the snake moves and adds components to itself. The SnakeComponent class is the fundamental building
  block of the Snake itself. The Powerups interface includes the method definitions for the methods that are then
  uniquely defined in Mine.java, Apple.java, and GoldenApple.java.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  The primary hurdle that I faced while implementing the game was controlling how the Snake itself moves. It took me
  a while before I came up with the implementation defined in Snake.java (the move() method). Another issue that I faced
  was then when the snake head intersected with an apple, the counter would increase more than once because
  it would take too long for the next fruit to spawn. To solve this, I had to optimize my code to make it more efficient
  and putting the specific methods within the object classes themselves rather than putting all the logic in the
  GameCourt class.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I believe that there is a good separation of functionality. I believe that my private state is well encapsulated
  and I ensured that there were specific accessor methods for certain data structures to preserve the encapsulation.
  For example, in RunSnake, I made sure that the Label for the Counter and Highscore were encapsulated and could
  only be accessed by specific methods. I also think I did a good job of separating functionality in order to preserve
  the Model-View-Controller relationship. If given the chance, however, one thing I would consider refactoring is
  reading and writing the highscores to the txt file. I think the implementation right now, while viable, could
  definitely be improved upon.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  I got the images (apple.png, bomb.png, goldenapple.png, snakecomponent.png) off of the internet. Additionally, I
  searched up how to implement the setTimeout() method in GoldenApple to create an internal timer so that the GoldenApple
  would disappear after a certain amount of time (in milliseconds). Besides that, all of the code (besides the initial
  starter code is original).
