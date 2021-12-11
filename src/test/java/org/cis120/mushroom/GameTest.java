package org.cis120.mushroom;

import org.cis120.snake.*;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {

    private SnakeComponent snakePart = new SnakeComponent(390, 10);
    private Snake snake = new Snake(new SnakeComponent(390, 10));
    private Apple apple = new Apple(200, 200);
    private Mine mine = new Mine(-100, -100);
    private GoldenApple goldenapple = new GoldenApple(-100, -100);
    public static final String HIGHSCORES = "files/highscoreTest.txt";
    public static final String HIGHSCORES_EMPTY = "files/highscoreTestEmpty.txt";

    @Test
    public void getPx() {
        assertEquals(200, apple.getPx());
    }

    @Test
    public void getPy() {
        assertEquals(-100, mine.getPy());
    }

    @Test
    public void setPx() {
        mine.setPx(50);
        assertEquals(50, mine.getPx());
    }

    @Test
    public void setPy() {
        goldenapple.setPy(350);
        assertEquals(350, goldenapple.getPy());
    }

    @Test
    public void intersects() {
        goldenapple.setPx(100);
        goldenapple.setPy(100);
        snakePart.setPx(100);
        snakePart.setPy(100);
        assertTrue(goldenapple.intersects(snakePart));
    }

    @Test
    public void snakeAddComponent() {
        snake.addComponent();
        snake.addComponent();
        assertEquals(3, snake.length());
    }

    @Test
    public void snakeChangeDirection() {
        snake.changeDirection(3);
        assertEquals(3, snake.direction());
    }

    @Test
    public void snakeCollides() {
        snake.getHead().setPx(100);
        snake.getHead().setPy(100);
        goldenapple.setPx(100);
        goldenapple.setPy(100);
        assertTrue(snake.collides(goldenapple));
    }

    @Test
    public void snakeGetHead() {
        snake.addComponent();
        assertEquals(390, snake.getHead().getPx());
        assertEquals(10, snake.getHead().getPy());
    }

    @Test
    public void snakeOutOfBoundsX() {
        snake.getHead().setPx(410);
        assertTrue(snake.outOfBounds());
    }

    @Test
    public void snakeOutOfBoundsY() {
        snake.getHead().setPy(410);
        assertTrue(snake.outOfBounds());
    }

    @Test
    public void snakeOutOfBoundsNegX() {
        snake.getHead().setPx(-10);
        assertTrue(snake.outOfBounds());
    }

    @Test
    public void snakeOutOfBoundsNegY() {
        snake.getHead().setPy(-10);
        assertTrue(snake.outOfBounds());
    }

    @Test
    public void snakeMoveLeft() {
        snake.getHead().setPx(200);
        snake.getHead().setPy(200);
        snake.changeDirection(0);
        snake.move();
        assertEquals(192, snake.getHead().getPx());
        assertEquals(200, snake.getHead().getPy());
    }

    @Test
    public void snakeMoveRight() {
        snake.getHead().setPx(200);
        snake.getHead().setPy(200);
        snake.changeDirection(1);
        snake.move();
        assertEquals(208, snake.getHead().getPx());
        assertEquals(200, snake.getHead().getPy());
    }

    @Test
    public void snakeMoveUp() {
        snake.getHead().setPx(200);
        snake.getHead().setPy(200);
        snake.changeDirection(2);
        snake.move();
        assertEquals(200, snake.getHead().getPx());
        assertEquals(208, snake.getHead().getPy());
    }

    @Test
    public void snakeMoveDown() {
        snake.getHead().setPx(200);
        snake.getHead().setPy(200);
        snake.changeDirection(3);
        snake.move();
        assertEquals(200, snake.getHead().getPx());
        assertEquals(192, snake.getHead().getPy());
    }

    @Test
    public void appleResetDirection() {
        apple.setPx(250);
        apple.setPy(100);
        apple.resetDirection();
        assertEquals(200, apple.getPx());
        assertEquals(200, apple.getPy());
    }

    @Test
    public void appleSetDirection1() {
        apple.setPx(100);
        apple.setPy(100);
        apple.setDirection();
        assertTrue(apple.getPx() >= 210 && apple.getPx() <= 380);
        assertTrue(apple.getPy() >= 210 && apple.getPy() <= 380);
    }

    @Test
    public void appleSetDirection2() {
        apple.setPx(100);
        apple.setPy(300);
        apple.setDirection();
        assertTrue(apple.getPx() >= 210 && apple.getPx() <= 380);
        assertTrue(apple.getPy() >= 20 && apple.getPy() <= 190);
    }

    @Test
    public void appleSetDirection3() {
        apple.setPx(300);
        apple.setPy(300);
        apple.setDirection();
        assertTrue(apple.getPx() >= 20 && apple.getPx() <= 190);
        assertTrue(apple.getPy() >= 20 && apple.getPy() <= 190);
    }

    @Test
    public void appleSetDirection4() {
        apple.setPx(300);
        apple.setPy(100);
        apple.setDirection();
        assertTrue(apple.getPx() >= 20 && apple.getPx() <= 190);
        assertTrue(apple.getPy() >= 210 && apple.getPy() <= 380);
    }

    public int highscoreReadTest(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int counter = 0;
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            if (Integer.parseInt(line) > counter) {
                counter = Integer.parseInt(line);
            }
        }
        return counter;
    }

    @Test
    public void highscoreRead() throws IOException {
        assertEquals(9, highscoreReadTest(HIGHSCORES));
    }

    @Test
    public void highscoreReadNoFile() {
        assertThrows(IOException.class, () -> highscoreReadTest("asdasd"));
    }

    @Test
    public void highscoreReadEmpty() throws IOException {
        assertEquals(0, highscoreReadTest(HIGHSCORES_EMPTY));
    }


}
