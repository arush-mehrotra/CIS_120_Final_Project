package org.cis120.snake;

/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * 
 * @version 2.1, Apr 2017
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
    private SnakeComponent snakePart = new SnakeComponent(390, 10);
    private Snake snake = new Snake(new SnakeComponent(390, 10));
    private Apple apple = new Apple(200, 200);
    private Mine mine = new Mine(-100, -100);
    private GoldenApple goldenapple = new GoldenApple(-100, -100);

    private boolean playing = false; // whether the game is running
    private JLabel status; // Current status text, i.e. "Running..."

    // Game constants
    public static final int COURT_WIDTH = 400;
    public static final int COURT_HEIGHT = 400;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;

    private int counter = 0; // tracks the score of the game

    public static final String HIGHSCORES = "files/highscore.txt"; // txt file where scores are
                                                                   // written and read

    // txt files that control the game state
    public static final String GAMESTATE_SNAKE = "files/GameState_Snake.txt";
    public static final String GAMESTATE_APPLE = "files/GameState_Apple.txt";
    public static final String GAMESTATE_GOLDENAPPLE = "files/GameState_GoldenApple.txt";
    public static final String GAMESTATE_MINE = "files/GameState_Mine.txt";
    public static final String GAMESTATE_COUNTER = "files/GameState_Counter.txt";

    private static ArrayList<Object> objects = new ArrayList<>();

    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the
        // given INTERVAL. We register an ActionListener with this timer, whose
        // actionPerformed() method is called each time the timer triggers. We
        // define a helper method called tick() that actually does everything
        // that should be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    tick();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key
        // is pressed, by changing the square's velocity accordingly. (The tick
        // method below actually moves the square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && snake.direction() != 1) {
                    snake.changeDirection(0);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && snake.direction() != 0) {
                    snake.changeDirection(1);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && snake.direction() != 3) {
                    snake.changeDirection(2);
                } else if (e.getKeyCode() == KeyEvent.VK_UP && snake.direction() != 2) {
                    snake.changeDirection(3);
                }
            }
        });

        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        snakePart = new SnakeComponent(390, 10);
        snake = new Snake(snakePart);
        playing = true;
        status.setText("Running...");
        counter = 0;
        RunSnake.returnCounter().setText("Counter: " + counter);
        mine.resetDirection();
        apple.resetDirection();
        goldenapple.resetDirection();
        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() throws IOException {
        if (playing) {
            // advance the snake
            snake.move();
            // if snake collides with an apple
            if (snake.collides(apple)) {
                addFruit();
                snake.addComponent();
                counter++;
                RunSnake.returnCounter().setText("Counter:" + " " + counter);
            }
            // if snake collides with a golden apple
            if (snake.collides(goldenapple)) {
                goldenapple.resetDirection();
                snake.addComponent();
                snake.addComponent();
                counter++;
                counter++;
                RunSnake.returnCounter().setText("Counter:" + " " + counter);
            }
            // if snake goes out of bounds or collides with a mine
            if (snake.outOfBounds() || snake.collides(mine)) {
                playing = false;
                status.setText("You Lost!");
                highscoreTrack();
                counter = 0;
                RunSnake.returnCounter().setText("Counter: " + counter);
                RunSnake.returnHighscore().setText("Highscore: " + highscoreRead());
            }
            repaint();
        }
    }

    // controls the addition of an apple, golden apple, or mine onto the GameCourt

    public void addFruit() {
        apple.setDirection();
        mine.setDirection();
        goldenapple.setDirection();
    }

    // paints the components onto the GUI

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        snake.draw(g);
        apple.draw(g);
        mine.draw(g);
        goldenapple.draw(g);
    }

    // writes the scores onto the highscore txt file

    public void highscoreTrack() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(HIGHSCORES, true));
        writer.write((counter + "\n"));
        writer.close();
    }

    // reads the scores from the highscore txt file and returns the highscore

    public int highscoreRead() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(HIGHSCORES));
        int counter = 0;
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            if (Integer.parseInt(line) > counter) {
                counter = Integer.parseInt(line);
            }
        }
        return counter;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }

    // saves the entire game state to a series of text files

    public void saveGameState() {
        snake.changeDirection(4);
        playing = false;
        try {
            FileOutputStream gameStateSnake = new FileOutputStream(GAMESTATE_SNAKE);
            ObjectOutputStream saveSnake = new ObjectOutputStream(gameStateSnake);
            saveSnake.writeObject(new Point(snake.getHead().getPx(), snake.getHead().getPy()));
            saveSnake.close();

            FileOutputStream gameStateApple = new FileOutputStream(GAMESTATE_APPLE);
            ObjectOutputStream saveApple = new ObjectOutputStream(gameStateApple);
            saveApple.writeObject(new Point(apple.getPx(), apple.getPy()));
            saveApple.close();

            FileOutputStream gameStateMine = new FileOutputStream(GAMESTATE_MINE);
            ObjectOutputStream saveMine = new ObjectOutputStream(gameStateMine);
            saveMine.writeObject(new Point(mine.getPx(), mine.getPy()));
            saveMine.close();

            FileOutputStream gameStateGoldenApple = new FileOutputStream(GAMESTATE_GOLDENAPPLE);
            ObjectOutputStream saveGoldenApple = new ObjectOutputStream(gameStateGoldenApple);
            saveGoldenApple.writeObject(new Point(goldenapple.getPx(), goldenapple.getPy()));
            saveGoldenApple.close();

            FileOutputStream gameStateCounter = new FileOutputStream(GAMESTATE_COUNTER);
            ObjectOutputStream saveCounter = new ObjectOutputStream(gameStateCounter);
            saveCounter.writeObject(counter);
            saveCounter.close();
        } catch (FileNotFoundException e) {
            RunSnake.errorMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // reads the previously saved game state and displays it

    public void readGameState() {
        try {
            FileInputStream gameStateSnake = new FileInputStream(GAMESTATE_SNAKE);
            ObjectInputStream saveSnake = new ObjectInputStream(gameStateSnake);
            Point newPointS = (Point) saveSnake.readObject();

            FileInputStream gameStateApple = new FileInputStream(GAMESTATE_APPLE);
            ObjectInputStream saveApple = new ObjectInputStream(gameStateApple);
            Point newPointA = (Point) saveApple.readObject();

            FileInputStream gameStateMine = new FileInputStream(GAMESTATE_MINE);
            ObjectInputStream saveMine = new ObjectInputStream(gameStateMine);
            Point newPointM = (Point) saveMine.readObject();
            mine.setPx(newPointM.x);
            mine.setPy(newPointM.y);

            FileInputStream gameStateGoldenApple = new FileInputStream(GAMESTATE_GOLDENAPPLE);
            ObjectInputStream saveGoldenApple = new ObjectInputStream(gameStateGoldenApple);
            Point newPointG = (Point) saveGoldenApple.readObject();
            goldenapple.setPx(newPointG.x);
            goldenapple.setPy(newPointG.y);

            FileInputStream gameStateCounter = new FileInputStream(GAMESTATE_COUNTER);
            ObjectInputStream saveCounter = new ObjectInputStream(gameStateCounter);
            int newCounter = (Integer) saveCounter.readObject();

            snakePart.setPx(newPointS.x);
            snakePart.setPy(newPointS.y);
            snake = new Snake(snakePart);
            playing = true;
            status.setText("Running...");
            counter = newCounter;
            for (int i = 0; i < counter; i++) {
                snake.addComponent();
            }
            RunSnake.returnCounter().setText("Counter: " + counter);
            apple.setPx(newPointA.x);
            apple.setPy(newPointA.y);
            mine.setPx(newPointM.x);
            mine.setPy(newPointM.y);
            goldenapple.setPx(newPointG.x);
            goldenapple.setPy(newPointG.y);
            requestFocusInWindow();
        } catch (FileNotFoundException e) {
            RunSnake.errorMessage();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlaying() {
        return playing;
    }

}