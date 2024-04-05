package AB2.Collections.Map;

public class KeyValuePair<Key, Value>
{
    private final Key key;
    private Value value;

    KeyValuePair(Key key, Value value)
    {
        this.key = key;
        this.value = value;
    }

    public Key getKey()
    {
        return key;
    }

    public Value getValue()
    {
        return value;
    }

    public void setValue(Value value)
    {
        this.value = value;
    }
}
