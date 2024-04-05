package AB2;

import AB2.Collections.Queue.Queue;

/**
 * A queue of bodies. A collection designed for holding bodies prior to processing.
 * The bodies of the queue can be accessed in a FIFO (first-in-first-out) manner,
 * i.e., the body that was first inserted by 'add' is retrieved first by 'poll'.
 * The number of elements of the queue is not limited.
 */
public class BodyQueue extends Queue<Body>
{
    /**
     * Initializes this queue with an initial capacity.
     *
     * @param initialCapacity the length of the internal array in the beginning,
     *                        initialCapacity > 0.
     */
    public BodyQueue(int initialCapacity)
    {
        super(initialCapacity);
    }

    /**
     * Initializes this queue as an independent copy of the specified queue.
     * Calling methods of this queue will not affect the specified queue
     * and vice versa.
     *
     * @param q the queue from which elements are copied to the new queue, q != null.
     */
    public BodyQueue(BodyQueue q)
    {
        super(q);
    }

    /**
     * Adds the specified body 'b' to this queue.
     *
     * @param b the element that is added to the queue.
     */
    @Override
    public void add(Body b)
    {
        super.add(b);
    }

    /**
     * Retrieves and removes the head of this queue,
     * or returns 'null' if this queue is empty.
     *
     * @return the head of this queue (or 'null' if this queue is empty).
     */
    @Override
    public Body poll()
    {
        return super.poll();
    }

    /**
     * Returns the number of bodies in this queue.
     *
     * @return the number of bodies in this queue.
     */
    public int size()
    {
        return super.size();
    }
}
