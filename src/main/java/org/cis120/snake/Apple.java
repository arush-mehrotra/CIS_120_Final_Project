package org.cis120.snake;

/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * 
 * @version 2.1, Apr 2017
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * A game object displayed using an image.
 *
 * Note that the image is read from the file when the object is constructed, and
 * that all objects created by this constructor share the same image data (i.e.
 * img is static). This is important for efficiency: your program will go very
 * slowly if you try to create a new BufferedImage every time the draw method is
 * invoked.
 */
public class Apple extends GameObj implements Powerups, Serializable {
    public static final String IMG_FILE = "files/apple.png";
    public static final int SIZE = 15;

    private static BufferedImage img;

    // constructor for Apple

    public Apple(int px, int py) {
        super(px, py, SIZE, SIZE);

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    // sets the location of the Apple object

    public void setDirection() {
        if (this.getPx() <= 200 && this.getPy() <= 200) {
            this.setPx((int) (Math.random() * 170) + 210);
            this.setPy((int) (Math.random() * 170) + 210);
        } else if (this.getPx() <= 200 && this.getPy() > 200) {
            this.setPx((int) (Math.random() * 170) + 210);
            this.setPy((int) (Math.random() * 170) + 20);
        } else if (this.getPx() > 200 && this.getPy() > 200) {
            this.setPx((int) (Math.random() * 170) + 20);
            this.setPy((int) (Math.random() * 170) + 20);
        } else {
            this.setPx((int) (Math.random() * 170) + 20);
            this.setPy((int) (Math.random() * 170) + 210);
        }

    }

    // resets the location of the Apple object to its original location at the start
    // of the game

    public void resetDirection() {
        this.setPx(200);
        this.setPy(200);
    }

    // draws the Apple onto the GameCourt

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }

}
