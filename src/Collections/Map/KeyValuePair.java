package Collections.Map;

import misc.Clone;

public class KeyValuePair<Key, Value> implements Clone
{
    private final Key key;
    private Value value;

    public KeyValuePair(Key key, Value value)
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

    @Override
    public KeyValuePair<Key, Value> clone() {
        return new KeyValuePair<>(key, value);
    }
}
