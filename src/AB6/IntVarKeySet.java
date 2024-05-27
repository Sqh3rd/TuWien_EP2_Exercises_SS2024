package AB6;

import Collections.Queue.Queue;

public class IntVarKeySet implements IntVarSet {
    private final IntVarConstMap map;

    IntVarKeySet(IntVarConstMap map) {
        this.map = map;
    }

    @Override
    public void add(IntVar v) {
        if (map.containsKey(v)) return;
        map.put(v, LinearExpression.ZERO);
    }

    @Override
    public boolean contains(IntVar v) {
        return map.containsKey(v);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public IntVarIterator iterator() {
        return new GenericIterator(Queue.of(map.keys()));
    }
}
