package simulation;

import gui.BallStateObserver;

/**
 * A Simulation provides Methods for returning values of a BallInBox-Simulation.
 * In addition the movement-calculations for a Simulation can be started.
 */
public interface Simulation {

    /**
     * Returns the X-coordinate of the Ball.
     *
     * @return the X-coordinate of the Ball
     * @see Ball
     */
    double getBallX();

    /**
     * Returns the Y-coordinate of the Ball.
     *
     * @return the Y-coordinate of the Ball
     * @see Ball
     */
    double getBallY();

    /**
     * Returns the radius of the Ball as Integer-value.
     *
     * @return the radius of the Ball
     */
    int getBallRadius();

    /**
     * Starts the movement calculations for the Balls movement.
     */
    void startMovement();

    /**
     * Registers a new Observer.
     *
     * @param observer the Observer that is interested in the changing
     *                 Ball-state
     */
    void registerObserver(BallStateObserver observer);

    /**
     * Unregisters an Observer. Does nothing, if the Observer was
     * not registered.
     *
     * @param observer the Observer that is not longer interested in the
     *                 changing Ball-state
     */
    void unregisterObserver(BallStateObserver observer);
}
