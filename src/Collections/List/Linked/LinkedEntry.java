package Collections.List.Linked;

public interface LinkedEntry<T>
{
    T getValue();
    void setValue(T value);
    LinkedEntry<T> getNext();
}
