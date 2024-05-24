package AB6;

import Collections.List.Linked.SinglyLinkedList;
import Collections.Map.KeyValuePair;

/**
 * This data structure maps variables ('IntVar' objects) to constants ('IntConst' objects).
 * It is implemented as hash map. There is no limit on the number of key-value
 * mappings stored in the map. This class implements 'IntVarConstMap'.
 */
public class IntVarConstHashMap implements IntVarConstMap
{
    private int hashedPlaces = 97;
    private SinglyLinkedList<KeyValuePair<IntVar, IntConst>>[] arr;
    private int usedSize = 0;

    /**
     * Initializes this map as an empty map.
     */
    public IntVarConstHashMap() {
        arr = new SinglyLinkedList[hashedPlaces];
    }

    /**
     * Initializes this map as an independent copy of the specified map. Later changes of 'this'
     * will not affect 'map' and vice versa.
     */
    public IntVarConstHashMap(IntVarConstHashMap map) {
        usedSize = map.usedSize;
        arr = new SinglyLinkedList[hashedPlaces];
        for (int i = 0; i < hashedPlaces; i++) {
            if (map.arr[i] == null)
                continue;
            arr[i] = map.arr[i].deepCopy();
        }
    }

    private int hashKey(IntVar key) {
        return key.hashCode() % hashedPlaces;
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
        if (arr[pos] == null) {
            arr[pos] = new SinglyLinkedList<>();
            arr[pos].push(new KeyValuePair<>(key, value));
            usedSize++;
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
        usedSize++;
        if (usedSize / hashedPlaces == 1) rehash();
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
    public IntVarHashSet keySet() {
        IntVarHashSet result = new IntVarHashSet();
        for (var entry : arr) {
            if (entry == null)
                continue;
            for (KeyValuePair<IntVar, IntConst> keyValuePair : entry) {
                result.add(keyValuePair.getKey());
            }
        }
        return result;
    }

    private void rehash() {
        this.hashedPlaces = Math.min(hashedPlaces*2, Integer.MAX_VALUE);
        SinglyLinkedList<KeyValuePair<IntVar, IntConst>>[] oldEntries = arr;
        arr = new SinglyLinkedList[hashedPlaces];
        for (SinglyLinkedList<KeyValuePair<IntVar, IntConst>> list : oldEntries)
            for (KeyValuePair<IntVar, IntConst> kvp : list) put(kvp.getKey(), kvp.getValue());
    }

    public IntVarConstHashMap copy() {
        return new IntVarConstHashMap(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) sb.append(arr[i].toString());
            else if (!sb.isEmpty() && i + 1 < arr.length && arr[i] != null) sb.append(", ");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) return false;
        IntVarConstHashMap map = (IntVarConstHashMap) obj;
        if (map.usedSize != usedSize) return false;
        for (IntVar var : keySet())
            if (!get(var).equals(map.get(var))) return false;
        return true;
    }
}
