package org.cis120.snake;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Mine extends GameObj implements Powerups, Serializable {
    public static final String IMG_FILE = "files/bomb.png";
    public static final int SIZE = 30;

    private static BufferedImage img;

    // constructor

    public Mine(int px, int py) {
        super(px, py, SIZE, SIZE);

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    // draws the Mine onto the GameCourt

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }

    // sets the location of the Mine

    public void setDirection() {
        int probability = (int) ((Math.random() * 9) + 1);
        if (probability < 5) {
            this.setPx((int) (Math.random() * 360) + 20);
            this.setPy((int) (Math.random() * 360) + 20);
        }
    }

    // resets the location of the Mine off the GameCourt

    public void resetDirection() {
        this.setPx(-100);
        this.setPy(-100);
    }

}
