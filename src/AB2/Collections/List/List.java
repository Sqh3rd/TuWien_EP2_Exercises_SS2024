package AB2.Collections.List;

public interface List<T>
{
    void push(T element);
    T pop();
    T remove(int index);
    void lshift(int numberOfElements);
    int size();
    int count();
    int indexOf(T element);
    boolean contains(T element);
    T get(int index);
}
