package AB5;

import Collections.List.Linked.SinglyLinkedList;
import Collections.Map.KeyValuePair;

/**
 * This data structure maps variables ('IntVar' objects) to constants ('IntConst' objects).
 * It is implemented as hash map. The map allows multiple variables with the
 * same name as long as they are not identical. There is no limit on the number of key-value
 * mappings stored in the map. This class implements 'IntVarConstMap'.
 */
//
// TODO: define further classes and methods for the implementation of this class, if
//  needed.
//
public class IntVarConstHashMap implements IntVarConstMap
{
    private final int maxSize = 97;
    private final SinglyLinkedList<KeyValuePair<IntVar, IntConst>>[] arr;
    private int usedSize = 0;

    /**
     * Initializes this map as an empty map.
     */
    public IntVarConstHashMap() {
        arr = new SinglyLinkedList[maxSize];
    }

    /**
     * Initializes this map as an independent clone of the specified map. Later changes of 'this'
     * will not affect 'map' and vice versa.
     */
    public IntVarConstHashMap(IntVarConstHashMap map) {
        usedSize = map.usedSize;
        arr = new SinglyLinkedList[maxSize];
        for (int i = 0; i < maxSize; i++) {
            if (map.arr[i] == null)
                continue;
            arr[i] = map.arr[i].clone();
        }
    }

    private int hashKey(IntVar key) {
        return key.hashCode() % maxSize;
    }

    @Override
    public IntConst get(IntVar key) {
        int pos = hashKey(key);
        if (arr[pos] == null)
            return null;
        for (var iterator = arr[pos].iterator(); iterator.hasNext();) {
            var next = iterator.next();
            if (next.getKey() == key)
                return next.getValue();
        }
        return null;
    }

    @Override
    public IntConst put(IntVar key, IntConst value) {
        int pos = hashKey(key);
        usedSize++;
        if (arr[pos] == null) {
            arr[pos] = new SinglyLinkedList<>();
            arr[pos].push(new KeyValuePair<>(key, value));
            return null;
        }

        for (var iterator = arr[pos].iterator(); iterator.hasNext();) {
            var next = iterator.next();
            if (next.getKey() != key)
                continue;
            IntConst oldValue = next.getValue();
            next.setValue(value);
            return oldValue;
        }

        arr[pos].push(new KeyValuePair<>(key, value));
        return null;
    }

    @Override
    public IntConst remove(IntVar key) {
        int pos = hashKey(key);
        if (arr[pos] == null)
            return null;

        var iterator = arr[pos].iterator();
        for (int i = 0; i < arr[pos].size(); i++) {
            var next = iterator.next();
            if (next.getKey() != key)
                continue;
            usedSize--;
            return arr[pos].remove(i).getValue();
        }
        return null;
    }

    @Override
    public boolean containsKey(IntVar key) {
        int pos = hashKey(key);
        if (arr[pos] == null)
            return false;

        for (var iterator = arr[pos].iterator(); iterator.hasNext();) {
            var next = iterator.next();
            if (next.getKey() == key)
                return true;
        }

        return false;
    }

    @Override
    public int size() {
        return usedSize;
    }

    @Override
    public IntVarQueue keyQueue() {
        IntVarQueue result = new IntVarQueue();
        for (var entry : arr) {
            if (entry == null)
                continue;
            for (var iterator = entry.iterator(); iterator.hasNext();) {
               result.add(iterator.next().getKey());
            }
        }
        return result;
    }

    @Override
    public IntVarConstMap clone() {
        return new IntVarConstHashMap(this);
    }
}
