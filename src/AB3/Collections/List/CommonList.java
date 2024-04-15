package AB3.Collections.List;

public interface CommonList<T>
{
    void push(T element);
    T pop();
    T remove(int index);
    int size();
    int indexOf(T element);
    boolean contains(T element);
    T get(int index);
}
