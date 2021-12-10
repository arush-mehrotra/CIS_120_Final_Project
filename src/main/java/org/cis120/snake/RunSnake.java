package org.cis120.snake;
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * 
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunSnake implements Runnable {
    private static Label counter;
    private static Label highscore;
    public static final String HIGHSCORES = "files/highscore.txt";
    private static JFrame frame = new JFrame("Snake");

    public static Label returnCounter() {
        return counter;
    }

    public static Label returnHighscore() {
        return highscore;
    }

    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for
        // local variables.

        // Top-level frame in which game components live.
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        // final JFrame frame = new JFrame("Snake");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Main playing area
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        control_panel.add(reset);

        // instructions button

        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        frame.getComponent(0), "The purpose of this game is to get "
                                + "the highest amount of points as possible." + "\n"
                                + "Eating a red apple will give you " +
                                "one point and " + "eating a golden apple will give you two!" + "\n"
                                +
                                "But be careful, watch out for the bombs - if you run into "
                                + "them, you will die."
                );
            }
        });
        control_panel.add(instructions);

        final JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (court.isPlaying()) {
                    court.saveGameState();
                } else {
                    errorMessage();
                }
            }
        });
        status_panel.add(save);

        final JButton reload = new JButton("Reload");
        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                court.readGameState();
            }
        });
        status_panel.add(reload);

        // displays the score of the game

        counter = new Label("Counter: 0");
        counter.setPreferredSize(new Dimension(75, 25));
        control_panel.add(counter);

        // displays the highscore of the game

        try {
            highscore = new Label("Highscore: " + highscoreRead());
        } catch (IOException e) {
            e.printStackTrace();
        }
        status_panel.add(highscore);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }

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

    public static void errorMessage() {
        JOptionPane.showMessageDialog(frame, "Function can not be performed!");
    }
}