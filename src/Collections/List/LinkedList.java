package Collections.List;

public interface LinkedList<T> extends CommonList<T>
{
    void insert(int index, T element);
    T replace(int index, T element);
    int lastIndexOf(T element);
}
