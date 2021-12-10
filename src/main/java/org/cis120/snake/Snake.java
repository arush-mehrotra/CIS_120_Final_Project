package org.cis120.snake;

/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * 
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * A basic game object starting in the upper left corner of the game court. It
 * is displayed as a square of a specified color.
 */
public class Snake implements Serializable {
    private LinkedList<SnakeComponent> snake = new LinkedList<>();
    private int direction = 4;

    /**
     * Note that, because we don't need to do anything special when constructing
     * a Square, we simply use the superclass constructor called with the
     * correct parameters.
     */
    public Snake(SnakeComponent snakeComponent) {
        snake.addLast(snakeComponent);
    }

    // adds another SnakeComponent to the tail of the Snake

    public void addComponent() {
        SnakeComponent end = snake.peekLast();
        SnakeComponent tail = new SnakeComponent(end.getPx(), end.getPy());
        snake.addLast(tail);
    }

    // returns the total length of the Snake

    public int length() {
        return snake.size();
    }

    // returns the current direction that the Snake is moving in

    public int direction() {
        return direction;
    }

    // changes the direction that the Snake is moving in

    public void changeDirection(int direction) {
        this.direction = direction;
    }

    // determines whether the head of the Snake intersects/collides with another
    // GameObject

    public boolean collides(GameObj obj) {
        return snake.peekFirst().intersects(obj);
    }

    // draws the Snake onto the GameCourt

    public void draw(Graphics g) {
        for (SnakeComponent x : snake) {
            x.draw(g);
        }
    }

    // controls the movement of the Snake based on the key that is pressed (0 =
    // left, 1 = right, 2 = up, 3 = down)

    public void move() {
        SnakeComponent head = snake.peekFirst();
        SnakeComponent newhead = null;
        switch (this.direction) {
            case 0:
                newhead = new SnakeComponent(head.getPx() - 8, head.getPy());
                break;
            case 1:
                newhead = new SnakeComponent(head.getPx() + 8, head.getPy());
                break;
            case 2:
                newhead = new SnakeComponent(head.getPx(), head.getPy() + 8);
                break;
            case 3:
                newhead = new SnakeComponent(head.getPx(), head.getPy() - 8);
                break;
            case 4:
                newhead = new SnakeComponent(head.getPx(), head.getPy());
                break;
            default:
                break;
        }
        snake.addFirst(newhead);
        snake.removeLast();
    }

    // determines whether the head of the snake is out of the bounds of the
    // GameCourt

    public boolean outOfBounds() {
        SnakeComponent head = snake.peekFirst();
        if (head.getPx() >= 400 || head.getPy() >= 400 || head.getPx() <= 0 || head.getPy() <= 0) {
            return true;
        }
        return false;
    }

    // returns the head of the Snake (used for testing purposes)

    public SnakeComponent getHead() {
        return snake.peekFirst();
    }

    public boolean selfIntersect() {
        SnakeComponent head = snake.peekFirst();
        for (SnakeComponent x : snake) {
            if (!(head.equals(x))) {
                if (head.intersects(x)) {
                    return true;
                }
            }
        }
        return false;
    }

}