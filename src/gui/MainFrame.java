package gui;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * A MainFrame is the main visible JFrame that contains a Field.
 * The title of the MainFrame is "Ball in Box". When the MainFrame is
 * instantiated, the position of the MainFrame is set to the middle of the
 * screen, it can not be resized.
 *
 * @see Field
 */
public class MainFrame extends JFrame {

    /**
     * The Field which draws the Simulation on itself.
     */
    private Field field;

    /**
     * Creates a new MainFrame.
     * The Field is automatically instantiated with the given parameters.
     *
     * @param length       the length of the Box
     * @param height       the height of the Box
     * @param ballRadius   the radius of the Ball
     * @param repaintDelay the number of seconds for the Field until it
     *                     refreshes.
     */
    public MainFrame(int length, int height, int ballRadius, int repaintDelay) {
        super("Ball in Box");

        field = new Field(length, height, ballRadius, repaintDelay);
        setContentPane(field);
        setResizable(false);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Creates a new MainFrame with fixed length and height of the box, fixed
     * radius of the ball and fixed delay-time for the repainting-Thread of
     * A Field.
     *
     * @param args unused parameters
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame g = new MainFrame(640, 480, 20, 25);
                g.setVisible(true);
        }
        });
    }
}
