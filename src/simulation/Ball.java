package simulation;

/**
 * A Ball has got a constant radius and a changeable position. The Ball can
 * move and got its own speed in direction, which is interpretable as a
 * two-dimensional vector.
 *
 * @see Tuple
 */
public class Ball {

    /**
     * The not-changeable radius of the Ball.
     */
    private final int radius;

    /**
     * The current speed of the Ball in the X-direction.
     * The magnitude of the speed has to be smaller than 1.
     */
    private double speedX;

    /**
     * The current speed of the Ball in the Y-direction.
     * The magnitude of the speed has to be smaller than 1.
     */
    private double speedY;

    /**
     * The tuple with the current position of the Ball.
     * The position is changeable.
     */
    private Tuple<Double> position;

    /**
     * Creates a Ball with a specified position and a constant radius.
     * The radius can not be changed.
     *
     * @param radius    the constant radius of the Ball
     * @param xPosition the X-position of the Ball
     * @param yPosition the Y-position of the Ball
     */
    public Ball(int radius, double xPosition, double yPosition) {
        this.radius = radius;
        position = new Tuple<>(xPosition, yPosition);
    }

    /**
     * Sets the speed of the Ball to the given speed in y-direction
     * and x-direction. Does nothing if the magnitude of one of the
     * speed-components is smaller than 1.
     *
     * @param x The speed in X-direction, which has to be smaller or equal to 1
     * @param y The speed in Y-direction, which has to be smaller or equal to 1
     */
    public void setSpeed(double x, double y) {
        if (Math.abs(x) <= 1 && Math.abs(y) <= 1) {
            speedX = x;
            speedY = y;
        }
    }

    /**
     * Proofs if the the magnitude of the speed is greater than 1.
     * If it is smaller or equal to 1, the Ball-state is interpreted as
     * inactive /not moving, otherwise it is interpreted as active /moving.
     *
     * @return if the Ball is currently inactive
     */
    public boolean isInactive() {
        double speedXMagnitude = Math.abs(speedX);
        double speedYMagnitude = Math.abs(speedY);
        return 0.0001 > Math.sqrt(speedXMagnitude * speedXMagnitude
                + speedYMagnitude * speedYMagnitude);
    }

    /**
     * Proofs if the Ball is currently moving in the positive X-direction.
     * This means that the X-direction of the speed is currently positive.
     *
     * @return if the Ball moves in positive X-direction
     */
    public boolean movesInPositiveXDirection() {
        return 0.0001 < speedX;
    }

    /**
     * Proofs if the Ball is currently moving in the positive Y-direction.
     * This means that the Y-direction of the speed is currently positive.
     *
     * @return if the Ball moves in positive Y-direction
     */
    public boolean movesInPositiveYDirection() {
        return 0.0001 < speedY;
    }

    /**
     * Proofs if the Ball is currently moving in the negative X-direction.
     * This means that the X-direction of the speed is currently negative.
     *
     * @return if the Ball moves in negative X-direction
     */
    public boolean movesInNegativeXDirection() {
        return speedX < 0 && speedX < -0.0001;
    }

    /**
     * Proofs if the Ball is currently moving in the negative Y-direction.
     * This means that the Y-direction of the speed is currently negative.
     *
     * @return if the Ball moves in negative Y-direction
     */
    public boolean movesInNegativeYDirection() {
        return speedY < 0 && speedY < -0.0001;
    }

    /**
     * Returns the radius of the Ball. The radius is an I
     * nteger-value.
     *
     * @return the radius of the Ball
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Returns the current speed in X-direction.
     *
     * @return the speed in X-direction
     */
    public double getSpeedInXDirection() {
        return speedX;
    }


    /**
     * Returns the current speed in Y-direction
     *
     * @return the speed in Y-direction
     */
    public double getSpeedInYDirection() {
        return speedY;
    }


    /**
     * Sets the position of the Ball. The position is saved as a tuple.
     *
     * @param x the new X-position of the Ball
     * @param y the new Y-position of the Ball
     */
    public synchronized void setPosition(double x, double y) {
        position = new Tuple<>(x, y);
    }

    /**
     * Returns the current X-position of the Ball.
     *
     * @return the X-position of the Ball
     */
    public synchronized double getXPosition() {
        return position.getX();
    }

    /**
     * Returns the current Y-position of the Ball.
     *
     * @return the Y-Position of the Ball
     */
    public synchronized double getYPosition() {
        return position.getY();
    }
}
