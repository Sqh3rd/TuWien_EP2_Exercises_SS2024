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
    private final int maxCollisionSize = 6;
    private double currentSize;
    private SinglyLinkedList<KeyValuePair<IntVar, IntConst>>[] arr;
    private int usedSize = 0;

    /**
     * Initializes this map as an empty map.
     */
    public IntVarConstHashMap() {
        currentSize = Math.pow(2, 6);
        arr = new SinglyLinkedList[(int) currentSize];
    }


    /**
     * Initializes this map as an independent copy of the specified map. Later changes of 'this'
     * will not affect 'map' and vice versa.
     */
    public IntVarConstHashMap(IntVarConstHashMap map) {
        currentSize = map.currentSize;
        arr = new SinglyLinkedList[(int) currentSize];
        for (var list : map.arr) {
            if (list == null) continue;
            for (var iterator = list.iterator(); iterator.hasNext();) {
                var entry = iterator.next();
                put(entry.getKey(), entry.getValue());
            }
        }
    }


    private void rehash() {
        int oldSize = (int) currentSize;
        currentSize = Math.min(currentSize *2, Integer.MAX_VALUE);
        SinglyLinkedList<KeyValuePair<IntVar, IntConst>>[] newArr = new SinglyLinkedList[(int) currentSize];
        for (int i = 0; i < oldSize; i++) {
            if (arr[i] == null)
                continue;
            for (var iterator = arr[i].iterator(); iterator.hasNext();)
                put(iterator.next(), newArr);
        }
        arr = newArr;
    }

    private int hashKey(IntVar key) {
        return hashKey(key, (int) currentSize);
    }

    private int hashKey(IntVar key, int size) {
        return key.hashCode() % size;
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
        usedSize++;
        return put(new KeyValuePair<>(key, value), arr);
    }

    private IntConst put(KeyValuePair<IntVar, IntConst> value,
                         SinglyLinkedList<KeyValuePair<IntVar, IntConst>>[] array) {
        int pos = hashKey(value.getKey(), array.length);
        if (array[pos] == null) {
            array[pos] = new SinglyLinkedList<>();
            array[pos].push(value);
            return null;
        }

        for (var iterator = array[pos].iterator(); iterator.hasNext();) {
            var next = iterator.next();
            if (next.getKey() != value.getKey())
                continue;
            IntConst oldValue = next.getValue();
            next.setValue(value.getValue());
            return oldValue;
        }

        array[pos].push(value);
        if (array[pos].size() > maxCollisionSize && currentSize < Integer.MAX_VALUE) rehash();
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
