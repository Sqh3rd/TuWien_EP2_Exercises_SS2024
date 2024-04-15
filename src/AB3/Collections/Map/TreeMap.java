package AB3.Collections.Map;

import java.util.Comparator;

public class TreeMap<Key, Value>
{
    private final Comparator<Key> comparator;
    private KeyValuePair<Key, Value> head;

    public TreeMap(Comparator<Key> comparator)
    {
        this.comparator = comparator;
    }

    public Value put(Key key, Value value)
    {
        if (head != null) return put(key, value, head);

        head = new KeyValuePair<>(key, value);
        return null;
    }

    private Value put(Key key, Value value, KeyValuePair<Key, Value> current)
    {
        if (comparator.compare(key, current.key) < 1)
        {
            if (current.key == key) {
                Value old = current.value;
                current.value = value;
                return old;
            }

            if (current.left == null)
            {
                current.left = new KeyValuePair<>(key, value);
                return null;
            }

            return put(key, value, current.left);
        }
        else {
            if (current.right == null)
            {
                current.right = new KeyValuePair<>(key, value);
                return null;
            }

            return put(key, value, current.right);
        }
    }

    public Value get(Key key)
    {
        return get(key, head);
    }

    private Value get(Key key, KeyValuePair<Key, Value> current)
    {
        if (current == null) return null;
        int comparison = comparator.compare(key, current.key);
        if (comparison == 0 && key == current.key) return current.value;
        else if (comparison <= 0) return get(key, current.left);
        else return get(key, current.right);
    }

    public boolean containsKey(Key key)
    {
        return containsKey(key, head);
    }

    private boolean containsKey(Key key, KeyValuePair<Key, Value> current) {
        if (current == null) return false;
        int comparison = comparator.compare(key, current.key);
        if (comparison == 0 && key == current.key) return true;
        else if (comparison <= 0) return containsKey(key, current.left);
        else return containsKey(key, current.right);
    }

    public String toString()
    {
        return head.toString();
    }

    private static class KeyValuePair<Key, Value>
    {
        private final Key key;
        private Value value;

        private KeyValuePair<Key, Value> left;
        private KeyValuePair<Key, Value> right;

        KeyValuePair(Key key, Value value)
        {
            this.key = key;
            this.value = value;
        }

        public String toString() {
            String ownRepresentation = String.format("%s: %s", key.toString(), value.toString());
            String leftRepresentation = left == null ? "" : left.toString() + ", ";
            String rightRepresentation = right == null? "" : ", " + right.toString();

            return String.format("{ %s%s%s }", leftRepresentation, ownRepresentation, rightRepresentation);
        }
    }
}
