package org.cis120.snake;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class GoldenApple extends GameObj implements Powerups, Serializable {
    public static final String IMG_FILE = "files/goldenapple.png";
    public static final int SIZE = 15;

    private static BufferedImage img;

    // constructor

    public GoldenApple(int px, int py) {
        super(px, py, SIZE, SIZE);

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    // draws the GoldenApple onto the GameCourt

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }

    // internal timer to control how long the GoldenApple is visible

    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }

    // sets the location of the GoldenApple and includes a probabilistic component
    // so it is not spawned every time

    public void setDirection() {
        int probability = (int) ((Math.random() * 15) + 1);
        if (probability < 3) {
            this.setPx((int) (Math.random() * 360) + 20);
            this.setPy((int) (Math.random() * 360) + 20);
            setTimeout(() -> resetDirection(), 2000);
        }
    }

    // resets the location of the GoldenApple off the screen

    public void resetDirection() {
        this.setPx(-100);
        this.setPy(-100);
    }

}
