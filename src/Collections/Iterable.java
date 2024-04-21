package Collections;

import java.util.function.Consumer;

public interface Iterable<T>
{
    void forEach(Consumer<T> consumer);
}
