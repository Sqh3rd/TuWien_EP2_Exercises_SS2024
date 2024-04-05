package AB2;

import AB1.Vector3;
import AB2.Collections.Map.SortedMap;

/**
 * A map that associates a body with an acceleration vector. The number of
 * key-value pairs is not limited.
 */
public class BodyAccelerationMap extends SortedMap<Body, Vector3>
{
    /**
     * Initializes this map with an initial capacity.
     *
     * @param initialCapacity specifies the size of the internal array. initialCapacity > 0.
     */
    public BodyAccelerationMap(int initialCapacity)
    {
        super(initialCapacity, Body::compareByMass);
    }

    /**
     * Adds a new key-value association to this map. If the key already exists in this map,
     * the value is replaced and the old value is returned. Otherwise, 'null' is returned.
     *
     * @param key          a body != null.
     * @param acceleration the acceleration vector to be associated with the key.
     * @return the old value if the key already existed in this map, or 'null' otherwise.
     */
    @Override
    public Vector3 put(Body key, Vector3 acceleration)
    {
        return super.put(key, acceleration);
    }

    /**
     * Returns the value associated with the specified key, i.e. the acceleration vector
     * associated with the specified body. Returns 'null' if the key is not contained in this map.
     *
     * @param key a body != null.
     * @return the value associated with the specified key (or 'null' if the key is not contained in
     * this map).
     */
    public Vector3 get(Body key)
    {
        return super.get(key);
    }
}
