package Collections.List;

public interface List<T> extends CommonList<T>
{
    void lshift(int numberOfElements);
    int count();
}
