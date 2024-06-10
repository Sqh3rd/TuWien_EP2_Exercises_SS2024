package AB8;

import java.util.Deque;
import java.util.NoSuchElementException;

public class MultiBodyIterator implements BodyIterator {
    private final MultiBody mb;
    private final Deque<Body> bodies;
    private Body lastEmitted = null;

    private MultiBodyIterator(MultiBody mb) {
        this.mb = mb;
        this.bodies = this.mb.asOrderedList(new DistanceComparator(this.mb.getCenter()));
    }

    public static MultiBodyIterator of(MultiBody mb) {
        return new MultiBodyIterator(mb);
    }

    @Override
    public boolean hasNext() {
        return !bodies.isEmpty();
    }

    @Override
    public Body next() {
        if (!hasNext())
            throw new NoSuchElementException();
        lastEmitted = bodies.pollFirst();
        return lastEmitted;
    }

    @Override
    public void remove() {
        if (lastEmitted == null)
            throw new IllegalStateException();
        mb.remove(lastEmitted);
        lastEmitted = null;
    }
}
