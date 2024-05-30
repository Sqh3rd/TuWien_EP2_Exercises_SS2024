package AB7;

import java.util.Arrays;
import java.util.Deque;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Represents a hierarchical system consisting of more than one subsystem (i.e., implying more
 * than one body).
 */
public class MultiBody implements HierarchicalSystem {
    private final Set<HierarchicalSystem> subSystems;
    private final double mass;

    /**
     * Initializes this system with more than one subsystem.
     *
     * @param subsystems an array of components of this system (bodies or subsystems),
     *                   subsystems != null && subsystems.length > 1.
     *                   Refer to Java Varargs documentation for more details:
     *                   https://docs.oracle.com/javase/8/docs/technotes/guides/language/varargs.html
     */
    public MultiBody(HierarchicalSystem... subsystems) {
        this.subSystems = Set.of(subsystems);
        this.mass = subSystems.stream()
            .map(HierarchicalSystem::getMass)
            .reduce((double) 0, Double::sum);
    }

    @Override
    public String getName() {
        DistanceComparator comp = new DistanceComparator(getCenter());
        return subSystems.stream()
            .min(comp)
            .orElseThrow()
            .getName();
    }

    @Override
    public double getMass() {
        return mass;
    }

    @Override
    public Vector3 getCenter() {
        return subSystems.stream()
            .map(system -> system.getCenter().times(system.getMass()))
            .reduce(Vector3.ZERO, Vector3::plus).times(1/mass);
    }

    @Override
    public double getRadius() {
        Vector3 currentCenter = getCenter();
        DistanceComparator comp = new DistanceComparator(currentCenter);
        HierarchicalSystem furthestSystem = subSystems.stream()
            .max(comp)
            .orElseThrow();
        return furthestSystem
            .getCenter()
            .distanceTo(currentCenter) + furthestSystem.getRadius();
    }

    @Override
    public int getNumberOfBodies() {
        return subSystems.stream()
            .map(HierarchicalSystem::getNumberOfBodies)
            .reduce(0, Integer::sum);
    }

    @Override
    public Deque<Body> asOrderedList(SystemComparator comp) {
        return subSystems.stream()
            .map(system -> system.asOrderedList(comp))
            .flatMap(Deque::stream)
            .sorted(comp)
            .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
    }

    @Override
    public BodyIterator iterator() {
        return BodyIteratorImpl.of(asOrderedList(new MassComparator()).iterator());
    }

    private String subsystemToString(HierarchicalSystem subsystem, AtomicBoolean first) {
        if (first.get()) {
            first.set(false);
            return subsystem.toString();
        }
        String systemString = subsystem.toString();
        if (systemString.contains("\n")) {
            systemString = Arrays.stream(systemString.split("\n"))
                .map(it -> String.format("   %s", it))
                .collect(Collectors.joining("\n"));
        } else {
            systemString = String.format("   %s", systemString);
        }
        return systemString;
    }

    @Override
    public String toString() {
        AtomicBoolean first = new AtomicBoolean(true);
        return subSystems.stream()
            .sorted(new DistanceComparator(getCenter()))
            .map(system -> subsystemToString(system, first))
            .collect(Collectors.joining("\n"));
    }
}
