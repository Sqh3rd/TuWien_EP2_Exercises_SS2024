package AB8;

import java.io.IOException;

/**
 * Thrown to indicate that a file does not meet the expected format.
 */
public class StateFileFormatException extends IOException {
    StateFileFormatException() {
        super("Invalid format for State File!");
    }

    StateFileFormatException(String s) {
        super(s);
    }

    static StateFileFormatException fromIncorrectColumnCount(int lineNo, int actualColumnCount) {
        return new StateFileFormatException(
            String.format("Expected line %s to contain 8 columns, but was %s instead",
                lineNo,
                actualColumnCount));
    }
}
