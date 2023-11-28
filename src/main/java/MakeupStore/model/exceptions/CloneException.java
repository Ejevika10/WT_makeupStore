package MakeupStore.model.exceptions;

/**
 * @author ejevika
 * @version 1.0
 */
public class CloneException extends RuntimeException {
    /**
     * Place message of exception
     * @param message message of exception
     */
    public CloneException(String message) {
        super(message);
    }
}
