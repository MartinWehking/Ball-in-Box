package simulation;

/**
 * A Tuple contains a pair of two different classes.
 * The Tuple is not changeable after it has been initialized.
 */
public class Tuple<T> {

    /**
     * The first Object.
     */
    private T x;

    /**
     * The second Object.
     */
    private T y;

    /**
     * Creates a Tuple with two elements.
     *
     * @param x the first Object
     * @param y the second Object
     */
    public Tuple(T x, T y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the first Object.
     *
     * @return the first Object
     */
    public T getX() {
        return x;
    }

    /**
     * Returns the second Object.
     *
     * @return the second object
     */
    public T getY() {
        return y;
    }
}
