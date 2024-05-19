package Collections.List.Linked;

import Collections.Iterator;

public class LinkedEntryIterator<T> implements Iterator<T>
{
    private LinkedEntry<T> current;

    LinkedEntryIterator(LinkedEntry<T> current) {
        this.current = current;
    }

    @Override
    public T next() {
        T value = current.getValue();
        current = current.getNext();
        return value;
    }

    @Override
    public boolean hasNext()
    {
        return current != null;
    }
}
