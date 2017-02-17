package gui;

import simulation.Simulation;
import simulation.MovementSimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A Field is a JPanel that draws the Ball on itself. The JPanel has got
 * the length and height of the Box. The walls of the Box are the borders
 * of the Field. A Field observes a Ball and draws a ball after a given
 * delay-time, except the Ball is not moving. It only starts to draw it again
 * after a Field gets notified again.
 */
public class Field extends JPanel implements BallStateObserver {

    /**
     * The Thread, which is responsible for refreshing the painting
     * on the Field.
     */
    private Timer repaintTimer;

    /**
     * The Simulation, which is used for painting.
     */
    private Simulation simulation;

    /**
     * The current Ball-state. Changes, when the Ball gets inactive or active-
     */
    private JLabel ballState;

    /**
     * The delay until the repaintTimer refreshes again.
     */
    private final int delay;

    /**
     * Creates a new Field its own instantiated Simulation. The preferred size
     * of this Field depends on the given length and height for the box.
     * The repaint-Timer is directly started and the ball-state directly
     * visible.
     *
     * @param boxLength  the length of the instantiated Box
     * @param boxHeight  the height of the instantiated Box
     * @param ballRadius the radius of the new instantiated Ball
     */
    public Field(int boxLength, int boxHeight, int ballRadius, int delay) {
        simulation = new MovementSimulator(boxLength, boxHeight, ballRadius);
        simulation.registerObserver(this);
        setPreferredSize(new Dimension(boxLength, boxHeight));
        this.delay = delay;

        ballState = new JLabel();
        add(ballState);
        startRepaintTimer();
        simulation.startMovement();
    }

    /**
     * Notifies this JPanel to start its repaint-Thread and change the
     * current for the user visible ball-state to active on this JPanel.
     * Does nothing if the repaint-Timer has already been started.
     *
     * @see BallStateObserver
     */
    public void notifyBallIsActive() {
        if (repaintTimer != null) {
            repaintTimer.start();
            ballState.setText("Ball rolls");
        }
    }

    /**
     * Notifies this Field to stop its repainting-Thread and change the current
     * for the user visible ball-state to inactive on this JPanel. Does nothing
     * if the repaint-Timer has not been started.
     *
     * @see BallStateObserver
     */
    public void notifyBallIsInactive() {
        if (repaintTimer != null) {
            repaintTimer.stop();
            ballState.setText("Ball rests");
        }
    }

    /**
     * Starts the repainting-Thread. The repainting-Thread will not be started
     * if it has already been started.
     * Furthermore the repainting-Thread is unique and there is only one of it.
     * When the repainting-Thread has been started, it will always
     * repaint this JPanel after it has waited for the given delay-time.
     *
     * @see Timer
     */
    private void startRepaintTimer() {
        if (repaintTimer == null) {
            repaintTimer = new Timer(delay, event -> {
                SwingUtilities.invokeLater(new Thread() {
                    public void run() {
                        repaint();
                        revalidate();
                    }
                });
            });
            repaintTimer.start();
        }
    }

    /**
     * Draws the Ball of the given Simulation on this JPanel.
     * The location of the drawn Ball depends on its x-Coordinate and
     * its Y-Coordinate, which are interpreted as Integer-values.
     * The color of the Ball is black and it is represented as a simple circle,
     * The size of the Ball depends on its radius.
     *
     * @param g the Graphics.
     * @see Graphics
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int ballX = (int) simulation.getBallX();
        int ballY = (int) simulation.getBallY();
        int ballRadius = simulation.getBallRadius();
        g.fillOval(ballX - ballRadius, ballY - ballRadius, ballRadius * 2,
                ballRadius * 2);
    }
}