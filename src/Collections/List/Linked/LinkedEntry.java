package Collections.List.Linked;

public interface LinkedEntry<T, E extends LinkedEntry<T, E>>
{
    T getValue();
    void setValue(T value);
    E getNext();
    E copy();
}
