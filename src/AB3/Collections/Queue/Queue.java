package AB3.Collections.Queue;

import AB2.ArrayUtils;
import AB3.Collections.List.Standard.UnsortedList;

public class Queue<T>
{
    private final UnsortedList<T> internalUnsortedList;
    private int head = 0;

    public Queue(int initialCapacity)
    {
        if (initialCapacity <= 0)
            throw new IllegalArgumentException("initial capacity needs to be > 0!");
        internalUnsortedList = UnsortedList.<T>builder()
            .withInitialArray(ArrayUtils.General.newArray(initialCapacity))
            .build();
    }

    public Queue(Queue<T> q)
    {
        if (q == null)
            throw new IllegalArgumentException("Body Queue must not be null!");
        internalUnsortedList = q.internalUnsortedList.toBuilder().build();
        head = q.head;
    }

    public void add(T element)
    {
        if (internalUnsortedList.count() == internalUnsortedList.size())
        {
            internalUnsortedList.lshift(head);
            head = 0;
        }
        internalUnsortedList.push(element);
    }

    public T poll()
    {
        if (head >= internalUnsortedList.count())
            return null;
        T element = internalUnsortedList.get(head);
        internalUnsortedList.replace(null, head);
        head++;
        return element;
    }

    public int size()
    {
        return internalUnsortedList.count() - head;
    }
}
