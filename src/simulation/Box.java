package simulation;

import java.util.Random;

/**
 * A Box has got a Ball, which is not visible outside the class and two
 * Integer values for its height and length. The height and length are not
 * changeable after they have been set. A Box provides several information
 * of its Ball and methods for changing the values of the Ball.
 *
 * @see Ball
 */
public class Box {

    /**
     * The not changeable length of the Box,
     */
    private final int length;

    /**
     * The not changeable height of the Box.
     */
    private final int height;

    /**
     * The Ball that is alterable by the Box. The Ball is not visible outside
     * the class.
     *
     * @see Ball
     */
    private Ball ball;

    /**
     * Creates a new Box with the given length, height and the radius of the
     * Ball. At the beginning, the Ball is set on a random-position inside the
     * Box. The position can be changed manually.
     *
     * @param length the new fixed length of the Box
     * @param height the new fixed height of the Box
     */
    public Box(int length, int height, int ballRadius) {
        this.length = length;
        this.height = height;

        final Random random = new Random();
        ball = new Ball(ballRadius, random.nextInt(length - 2 * ballRadius)
               + ballRadius, random.nextInt(height - 2 * ballRadius)
               + ballRadius);
    }

    /**
     * Sets the speed of the Ball to the given parameters.
     *
     * @param x the new speed in X-direction
     * @param y the new speed in Y-direction
     * @see Ball
     */
    public void setBallSpeed(double x, double y) {
        ball.setSpeed(x, y);
    }

    /**
     * Returns if the Ball is moving in positive X-direction
     *
     * @return if the Ball currently moves in positive X-direction
     * @see Ball
     */
    public boolean ballMovesInPositiveXDirection() {
        return ball.movesInPositiveXDirection();
    }

    /**
     * Returns if the Ball is moving in positive Y-direction .
     *
     * @return if the Ball currently moves in positive Y-direction
     * @see Ball
     */
    public boolean ballMovesInPositiveYDirection() {
        return ball.movesInPositiveYDirection();
    }

    /**
     * Returns if the Ball is moving in negative X-direction.
     *
     * @return if the Ball currently moves in negative X-direction
     * @see Ball
     */
    public boolean ballMovesInNegativeXDirection() {
        return ball.movesInNegativeXDirection();
    }

    /**
     * Returns if the Ball is moving in negative Y-direction.
     *
     * @return if the Ball currently moves in negative Y-direction
     * @see Ball
     */
    public boolean ballMovesInNegativeYDirection() {
        return ball.movesInNegativeYDirection();
    }

    /**
     * Returns the current speed of the Ball in X-direction
     *
     * @return the speed of the Ball in X-direction
     * @see Ball
     */
    public double getBallSpeedInXDirection() {
        return ball.getSpeedInXDirection();
    }


    /**
     * Returns the current speed of the Ball in Y-direction.
     *
     * @return the speed of the Ball in Y-direction
     * @see Ball
     */
    public double getBallSpeedInYDirection() {
        return ball.getSpeedInYDirection();
    }

    /**
     * Returns the fixed length of the Box as an Integer-value.
     *
     * @return the length of the Box
     * @see Ball
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns the fixed height of the Box as an Integer-value.
     *
     * @return the height of the Box
     * @see Ball
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the X-position of the Ball.
     *
     * @return the X-position of the Ball
     * @see Ball
     */
    public double getBallX() {
        return ball.getXPosition();
    }

    /**
     * Returns if the Ball is inactive or not.
     *
     * @return if the Ball is inactive
     * @see Ball
     */
    public boolean isBallInactive() {
        return ball.isInactive();
    }

    /**
     * Returns the current Y-coordinate of the Ball.
     *
     * @return the Y-coordinate of the Ball
     * @see Ball
     */
    public double getBallY() {
        return ball.getYPosition();
    }

    /**
     * Places the Ball at a specified position in the Box.
     * If the given parameters for the new position are not inside the Box or
     * the Ball, the Ball will not be placed at that position.
     *
     * @param x the new X-coordinate of the Ball.
     * @param y the new Y-coordinate of the Ball.
     * @see Ball
     */
    public void placeBall(double x, double y) {
        if (x >= ball.getRadius() && x <= (length - ball.getRadius())
                && y >= ball.getRadius()
                && y <= (height - ball.getRadius())) {
            ball.setPosition(x, y);
        }
    }

    /**
     * Returns the radius of the Ball.
     *
     * @return the radius of the Ball
     * @see Ball
     */
    public int getBallRadius() {
        return ball.getRadius();
    }
}
