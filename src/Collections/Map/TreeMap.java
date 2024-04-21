package Collections.Map;

import java.util.Comparator;

import Collections.List.Linked.DoublyLinkedList;

public class TreeMap<Key, Value>
{
    private final Comparator<Key> comparator;
    private BinaryKeyValueNode<Key, Value> head;
    private int size;

    public TreeMap(Comparator<Key> comparator)
    {
        this.comparator = comparator;
        this.size = 0;
    }

    public TreeMap(TreeMap<Key, Value> source)
    {
        this.head = source.head.copy();
        this.comparator = source.comparator;
        this.size = source.size;
    }

    public Value put(Key key, Value value)
    {
        size++;
        if (head != null)
            return put(key, value, head);

        head = new BinaryKeyValueNode<>(key, value);
        return null;
    }

    private Value put(Key key, Value value, BinaryKeyValueNode<Key, Value> current)
    {
        if (comparator.compare(key, current.getKey()) < 1)
        {
            if (current.getKey() == key)
            {
                Value old = current.getValue();
                current.setValue(value);
                return old;
            }

            if (current.getLeft() == null)
            {
                current.setLeft(new BinaryKeyValueNode<>(key, value));
                return null;
            }

            return put(key, value, current.getLeft());
        }
        else
        {
            if (current.getRight() == null)
            {
                current.setRight(new BinaryKeyValueNode<>(key, value));
                return null;
            }

            return put(key, value, current.getRight());
        }
    }

    public Value get(Key key)
    {
        return get(key, head);
    }

    private Value get(Key key, BinaryKeyValueNode<Key, Value> current)
    {
        if (current == null)
            return null;
        int comparison = comparator.compare(key, current.getKey());
        if (comparison == 0 && key == current.getKey())
            return current.getValue();
        else if (comparison <= 0)
            return get(key, current.getLeft());
        else
            return get(key, current.getRight());
    }

    public boolean containsKey(Key key)
    {
        return containsKey(key, head);
    }

    private boolean containsKey(Key key, BinaryKeyValueNode<Key, Value> current)
    {
        if (current == null)
            return false;
        int comparison = comparator.compare(key, current.getKey());
        if (comparison == 0 && key == current.getKey())
            return true;
        else if (comparison <= 0)
            return containsKey(key, current.getLeft());
        else
            return containsKey(key, current.getRight());
    }

    public String toString()
    {
        return head.toString();
    }

    public int getSize()
    {
        return size;
    }

    public DoublyLinkedList<Key> getKeys()
    {
        DoublyLinkedList<Key> keys = new DoublyLinkedList<>();
        addKey(keys, head);
        return keys;
    }

    private void addKey(DoublyLinkedList<Key> list, BinaryKeyValueNode<Key, Value> current) {
        if (current == null) return;
        addKey(list, current.getLeft());
        list.push(current.getKey());
        addKey(list, current.getRight());
    }

    public DoublyLinkedList<KeyValuePair<Key, Value>> getEntries() {
        DoublyLinkedList<KeyValuePair<Key, Value>> entries = new DoublyLinkedList<>();
        addEntry(entries, head);
        return entries;
    }

    private void addEntry(DoublyLinkedList<KeyValuePair<Key, Value>> list, BinaryKeyValueNode<Key, Value> current) {
        if (current == null) return;
        addEntry(list, current.getLeft());
        list.push(new KeyValuePair<>(current.getKey(), current.getValue()));
        addEntry(list, current.getRight());
    }

    public void putIfAbsent(Key key, Value value) {
        if (containsKey(key)) return;
        put(key, value);
    }

}
