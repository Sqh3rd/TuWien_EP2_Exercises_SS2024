package AB7;

import java.util.Iterator;

public class BodyIteratorImpl implements BodyIterator {
    private final Iterator<Body> iter;

    BodyIteratorImpl(Iterator<Body> iter) {
        this.iter = iter;
    }

    public static BodyIteratorImpl of(Iterator<Body> iter) {
        return new BodyIteratorImpl(iter);
    }

    @Override
    public boolean hasNext() {
        return iter.hasNext();
    }

    @Override
    public Body next() {
        return iter.next();
    }
}
