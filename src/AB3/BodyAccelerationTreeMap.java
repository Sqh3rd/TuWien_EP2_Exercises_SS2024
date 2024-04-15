package AB3;

import AB3.Collections.Map.TreeMap;

/**
 * This data structure maps bodies to vectors ('Vector3' objects). It is implemented
 * as a binary search tree where bodies are sorted based on their masses.
 * The map allows multiple bodies with the same mass as long as they are not identical.
 * There is no limit on the number of key-value pairs stored in the map.
 */
//
// TODO: define further classes and methods for the implementation of the binary search tree, if
//  needed.
//
public class BodyAccelerationTreeMap extends TreeMap<Body, Vector3>
{
    BodyAccelerationTreeMap() {
        super(Body::compareByMass);
    }

    /**
     * Adds a new key-value association to this map. If the key already exists in this map,
     * the value is replaced and the old value is returned. Otherwise, 'null' is returned.
     * @param key a body != null.
     * @param value the vector to be associated with the key (can also be 'null').
     * @return the old value if the key already existed in this map, or 'null' otherwise.
     */
    public Vector3 put(Body key, Vector3 value) {
        return super.put(key, value);
    }

    /**
     * Returns the value associated with the specified key, i.e. the vector
     * associated with the specified body. Returns 'null' if the key is not contained in this map.
     * @param key a body != null.
     * @return the value associated with the specified key (or 'null' if the key is not contained in
     * this map).
     */
    public Vector3 get(Body key) {
        return super.get(key);
    }

    /**
     * Returns 'true' if this map contains a mapping for the specified key.
     * @param key a body != null.
     * @return 'true' if this map contains a mapping for the specified key, or 'false' otherwise.
     */
    public boolean containsKey(Body key) {
        return super.containsKey(key);
    }

    /**
     *  Returns a readable representation of this map, in which key-value pairs are listed in
     *  descending order according to the mass of the bodies.
     */
    public String toString() {
        return super.toString();
    }
}

// TODO: define further classes, if needed (either here or in a separate file).
