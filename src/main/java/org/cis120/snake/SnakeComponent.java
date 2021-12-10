package org.cis120.snake;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class SnakeComponent extends GameObj implements Serializable {
    public static final int SIZE = 15;
    private static BufferedImage img;
    public static final String IMG_FILE = "files/snakecomponent.png";

    // constructor

    public SnakeComponent(int px, int py) {
        super(px, py, SIZE, SIZE);

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    // draws the individual SnakeComponent onto the GameCourt

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }
}
