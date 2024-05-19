package AB5;

/**
 * Single Element iterator
 * @param <T> needs to extend IntVar since I'm bound by the IntVarIterator Interface :c
 */
public class SEIterator<T extends IntVar> implements IntVarIterator {
    private boolean hasNext = true;
    private final T value;

    SEIterator(T value) {
        this.value = value;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public IntVar next() {
        if (!hasNext)
            return null;
        hasNext = false;
        return value;
    }
}
