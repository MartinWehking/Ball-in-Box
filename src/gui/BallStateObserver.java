package gui;

/**
 * A BallStateObserver gets a notification when the state of a Ball has been
 * changed.
 *
 * @see simulation.Ball
 */
public interface BallStateObserver {

    /**
     * Notifies the Observer that a Ball is currently not moving.
     */
    void notifyBallIsInactive();

    /**
     * Notifies the Observer that a Ball is currently moving.
     */
    void notifyBallIsActive();
}
