package simulation;

import gui.BallStateObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A MovementSimulator has got a Box with a Ball and calculates the speed,
 * collisions and new positions of the Ball for a given refresh-Time.
 * The MovementSimulator acts as an Observable Object, which notifies its
 * registered Observers when the Ball gets inactive or active.
 * A MovementSimulator also provides all information of the Ball.
 *
 * @see Simulation
 */
public class MovementSimulator implements Simulation {

    /**
     * The Box with the Ball.
     */
    private Box box;

    /**
     * The Thread for physical calculations.
     */
    private Thread physicalCalculation;

    /**
     * The number of seconds, the Thread for physical calculations will wait
     * until a calculation has been done.
     */
    private int refreshTime;

    /**
     * Stops a running physicalCalculation-Thread, when set to false.
     */
    private boolean simulationIsActive;

    /**
     * The List of interested Observers.
     */
    private List<BallStateObserver> observers;

    /**
     * Instantiates a new MovementSimulator with a new Box and Ball
     * The refresh-Time is set to 10 milliseconds.
     *
     * @param length     the length of the new instantiated Box
     * @param height     the height of the new instantiated Box
     * @param ballRadius the radius of the instantiated Ball in the new
     *                   instantiated Box.
     */
    public MovementSimulator(int length, int height, int ballRadius) {
        box = new Box(length, height, ballRadius);
        observers = new ArrayList<>();
        refreshTime = 10;
    }

    /**
     * Registers a new BallStateObserver that is interested in the interactive
     * changing ball-state. The BallSateObserver will get a notification
     * when the ball-state changes.
     *
     * @param observer the new interested BallStateObserver
     * @see BallStateObserver
     */
    public void registerObserver(BallStateObserver observer) {
        observers.add(observer);
    }

    /**
     * Unregisters a BallStateObserver. The BallStateObserver will not get any
     * notification anymore when the Ball-state changes.
     *
     * @param observer the BallStateObserver that is not interested in the
     *                 Ball-state any longer.
     */
    public void unregisterObserver(BallStateObserver observer) {
        observers.remove(observer);
    }

    /**
     * Returns the x-Coordinate of the current Ball-position in the Box.
     *
     * @return the x-Coordinate of the Ball
     * @see Box
     * @see Ball
     */
    public double getBallX() {
        return box.getBallX();
    }

    /**
     * Returns the y-Coordinate of the current Ball-position in the Box.
     *
     * @return the y-Coordinate of the Ball
     */
    public double getBallY() {
        return box.getBallY();
    }

    /**
     * Returns the Ball-radius of the Ball in the box.
     *
     * @return the Ball-radius
     * @see Box
     * @see Ball
     */
    public int getBallRadius() {
        return box.getBallRadius();
    }

    /**
     * Gives the Ball a new random speed.
     * The magnitudes of the speed in x-direction and the speed in y-direction
     * are smaller than 1.
     */
    private void setRandomBallSpeed() {
        final Random random = new Random();
        boolean positiveX = random.nextBoolean();
        boolean positiveY = random.nextBoolean();

        double newSpeedX = Math.random();
        if (!positiveX) {
            newSpeedX = newSpeedX * (-1);
        }
        double newSpeedY = Math.random();
        if (!positiveY) {
            newSpeedY = newSpeedY * (-1);
        }
        box.setBallSpeed(newSpeedX, newSpeedY);
    }

    /**
     * Notifies all registered BallStateObserver that the Ball has started
     * to move.
     */
    private void notifyBallIsActive() {
        for (BallStateObserver observer : observers) {
            observer.notifyBallIsActive();
        }
    }

    /**
     * Notifies all registered BallStateObserver that the Ball has stopped
     * moving.
     */
    private void notifyBallIsInactive() {
        for (BallStateObserver observer : observers) {
            observer.notifyBallIsInactive();
        }
    }

    /**
     * Inverts the speed of the Ball in x-direction. The speed in x-direction
     * is also slowed.
     */
    private void invertAndSlowSpeedInXDirection() {
        double invertedSlowedSpeedX = (-1)
                * box.getBallSpeedInXDirection() * 0.95;
        box.setBallSpeed(invertedSlowedSpeedX, box.getBallSpeedInYDirection());
    }

    /**
     * Inverts the speed of the Ball in y-direction. The speed in y-direction
     * is also slowed.
     */
    private void invertAndSlowSpeedInYDirection() {
        double invertedSlowedSpeedY = (-1)
                * box.getBallSpeedInYDirection() * 0.95;
        box.setBallSpeed(box.getBallSpeedInXDirection(), invertedSlowedSpeedY);
    }

    /**
     * Calculates the new position of the Ball after the refreshTime.
     * Proofs if a collision has occurred. Slows the Ball-speed caused by
     * rolling friction. If this method calculates that the Ball will hit the
     * wall of the Box, the refresh-time for the physicalCalculation-Thread is
     * set to the time, the collision will occur and the calculation will stop.
     * As a result of this the Ball is set next to the wall and the
     * speed-directions of the Ball are inverted. So the Ball moves physically
     * correct away from the wall that was hit.
     *
     * @return the Tuple that contains the new x-Coordinate and the
     * new y-Coordinate of the Balls position.
     */
    private Tuple<Double> calculatePhysicalData() {
        double newX = box.getBallX();
        double newY = box.getBallY();
        boolean collisionOccurred = false;

        for (int i = 1; i <= refreshTime; i++) {
            double proofX = newX + box.getBallSpeedInXDirection();

            if (proofX < (box.getBallRadius())
                    && box.ballMovesInNegativeXDirection()) {
                invertAndSlowSpeedInXDirection();
                collisionOccurred = true;
                proofX = box.getBallRadius();
            } else if (proofX > (box.getLength() - box.getBallRadius())
                    && box.ballMovesInPositiveXDirection()) {
                invertAndSlowSpeedInXDirection();
                collisionOccurred = true;
                proofX = box.getLength() - box.getBallRadius();
            }

            double proofY = newY + box.getBallSpeedInYDirection();
            if (proofY < (box.getBallRadius())
                    && box.ballMovesInNegativeYDirection()) {
                invertAndSlowSpeedInYDirection();
                collisionOccurred = true;
                proofY = box.getBallRadius();
            } else if (proofY > (box.getHeight() - box.getBallRadius())
                    && box.ballMovesInPositiveYDirection()) {
                invertAndSlowSpeedInYDirection();
                collisionOccurred = true;
                proofY = box.getHeight() - box.getBallRadius();
            }
            newX = proofX;
            newY = proofY;

            if (collisionOccurred && !box.isBallInactive()) {
                refreshTime = i;
                return new Tuple<>(newX, newY);
            } else {
                rollingFriction();
            }
        }
        return new Tuple<>(newX, newY);
    }

    /**
     * While the Ball the Box is moving, the Ball gets slower and the speed
     * of the Ball is decreased. The new calculated speed is smaller than the
     * old one and as a result of this, the new speed of the Ball in x-Direction
     * and y-Direction is also less than the old one.
     */
    private void rollingFriction() {
        double newSpeedInXDirection = box.getBallSpeedInXDirection();
        double newSpeedInYDirection = box.getBallSpeedInYDirection();

        double oldVectorMagnitude = Math.sqrt(newSpeedInXDirection
                * newSpeedInXDirection + newSpeedInYDirection
                * newSpeedInYDirection);

        double newVectorMagnitude = oldVectorMagnitude - 0.0001;
        newSpeedInXDirection = (newSpeedInXDirection / oldVectorMagnitude)
                * newVectorMagnitude;
        newSpeedInYDirection = (newSpeedInYDirection / oldVectorMagnitude)
                * newVectorMagnitude;
        box.setBallSpeed(newSpeedInXDirection, newSpeedInYDirection);
    }

    /**
     * Starts a Thread that calculates the new position of the Ball.
     * The Thread is active until it is stopped manually.
     * The calculation is done after the Thread has waited for the given
     * refresh-time. When there is already a running Thread, this method
     * will not instantiate a second one. Before the Thread is started,
     * the Ball gets a random Ball-speed. If the Ball gets marked as inactive,
     * the interested Observers are notified, This also happens, when the Ball
     * gets marked as active. In addition the Thread will wait for
     * 1 - 10 seconds, when the Ball gets marked as inactive, until the Ball
     * gets a new random speed.
     *
     * @see Simulation
     */
    public void startMovement() {
        if (physicalCalculation == null) {
            simulationIsActive = true;
            int oldRefreshTime = refreshTime;
            setRandomBallSpeed();
            notifyBallIsActive();

            physicalCalculation = new Thread() {
                public void run() {
                    while (simulationIsActive) {
                        Tuple<Double> newLocation = calculatePhysicalData();
                        try {
                            sleep(refreshTime);
                            if (refreshTime != oldRefreshTime) {
                                refreshTime = oldRefreshTime;
                            }
                        } catch (InterruptedException interrupted) {
                            simulationIsActive = false;
                        }
                        box.placeBall(newLocation.getX(), newLocation.getY());
                        if (box.isBallInactive()) {
                            notifyBallIsInactive();
                            final Random random = new Random();
                            try {
                                sleep((random.nextInt(10) + 1) * 1000);
                                setRandomBallSpeed();
                                notifyBallIsActive();
                            } catch (InterruptedException exception) {
                                simulationIsActive = false;
                            }
                        }
                    }
                }
            };
            physicalCalculation.start();
        }
    }
}