package AB8;

import java.io.FileNotFoundException;

/**
 * Signals that an attempt to open the state file denoted by a specified pathname has failed.
 */
public class StateFileNotFoundException extends FileNotFoundException {
    StateFileNotFoundException() {
        super("State File not found");
    }

    StateFileNotFoundException(String path) {
        super(String.format("State File %s not found", path));
    }
}
