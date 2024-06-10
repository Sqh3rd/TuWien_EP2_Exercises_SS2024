package AB8;

import java.util.NoSuchElementException;

public class SEBodyIterator implements BodyIterator {
    private final Body body;
    private boolean wasUsed = false;

    private SEBodyIterator(Body body) {
        this.body = body;
    }

    public static SEBodyIterator of(Body body) {
        return new SEBodyIterator(body);
    }

    @Override
    public boolean hasNext() {
        return !wasUsed;
    }

    @Override
    public Body next() {
        if (!hasNext())
            throw new NoSuchElementException();
        wasUsed = true;
        return body;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
