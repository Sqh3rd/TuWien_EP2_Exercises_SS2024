package AB6;

import Collections.Queue.Queue;

public class GenericIterator implements IntVarIterator {
    private final Queue<IntVar> queue;

    public GenericIterator(IntVar ...elements) {
        this.queue = Queue.of(elements);
    }

    public GenericIterator(Queue<IntVar> other) {
        this.queue = other.clone();
    }

    @Override
    public boolean hasNext() {
        return queue.size() > 0;
    }

    @Override
    public IntVar next() {
        return queue.poll();
    }
}
