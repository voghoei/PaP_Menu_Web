package PaP;


public class PaPException
        extends Exception {

    private static final long serialVersionUID = 1L;

    public PaPException(String message) {
        super(message);
    }

    public PaPException(Throwable cause) {
        super(cause);
    }
}
