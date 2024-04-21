package Collections.List.Linked;

public class SinglyLinkedEntry<T> implements LinkedEntry<T>
{
    private T value;
    private SinglyLinkedEntry<T> next;

    SinglyLinkedEntry(T value) {
        this(value, null);
    }

    SinglyLinkedEntry(T value, SinglyLinkedEntry<T> next)
    {
        this.value = value;
        this.next = next;
    }

    public void setValue(T value) { this.value = value; }

    public T getValue()
    {
        return value;
    }

    public SinglyLinkedEntry<T> getNext()
    {
        return next;
    }

    public void setNext(SinglyLinkedEntry<T> singlyLinkedEntry)
    {
        this.next = singlyLinkedEntry;
    }

    public SinglyLinkedEntry<T> clone()
    {
        return new SinglyLinkedEntry<>(value, next == null ? null : next.clone());
    }
}
