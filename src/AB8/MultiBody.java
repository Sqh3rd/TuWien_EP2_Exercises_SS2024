package AB8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Represents a hierarchical system consisting of more than one subsystem (i.e., implying more
 * than one body).
 */
public class MultiBody implements HierarchicalSystem {
    private final List<HierarchicalSystem> subsystems = new ArrayList<>();
    private double mass;

    /**
     * Initializes this system with more than one subsystem.
     *
     * @param subsystems an array of components of this system (bodies or subsystems),
     *                   subsystems != null && subsystems.length > 1.
     *                   Refer to Java Varargs documentation for more details:
     *                   https://docs.oracle.com/javase/8/docs/technotes/guides/language/varargs.html
     */
    public MultiBody(HierarchicalSystem... subsystems) {
        this.subsystems.addAll(List.of(subsystems));
        mass = calculateMass();
    }

    private double calculateMass() {
        return this.subsystems.stream()
            .map(HierarchicalSystem::getMass)
            .reduce((double) 0, Double::sum);
    }

    @Override
    public String getName() {
        DistanceComparator comp = new DistanceComparator(getCenter());
        return subsystems.stream()
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
        return subsystems.stream()
            .map(system -> system.getCenter().times(system.getMass()))
            .reduce(new Vector3(0, 0, 0), Vector3::plus)
            .times(1 / mass);
    }

    @Override
    public double getRadius() {
        Vector3 center = getCenter();
        DistanceComparator comp = new DistanceComparator(center);
        return subsystems.stream()
            .max(comp)
            .map(it -> center.distanceTo(it.getCenter()) + it.getRadius())
            .orElseThrow();
    }

    @Override
    public int getNumberOfBodies() {
        return subsystems.stream()
            .map(HierarchicalSystem::getNumberOfBodies)
            .reduce(0, Integer::sum);
    }

    @Override
    public Deque<Body> asOrderedList(SystemComparator comp) {
        return subsystems.stream()
            .map(system -> system.asOrderedList(comp))
            .flatMap(Deque::stream)
            .sorted(comp)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public BodyIterator iterator() {
        return MultiBodyIterator.of(this);
    }

    private Function<HierarchicalSystem, String> indentSubsystemString(AtomicBoolean isFirst) {
        return (HierarchicalSystem system) -> {
            String systemString = system.toString();
            if (isFirst.get()) {
                isFirst.set(false);
                return systemString;
            }
            if (!systemString.contains("\n")) {
                return "   " + systemString;
            }
            return Arrays.stream(systemString.split("\n"))
                .map(str -> "   " + str)
                .collect(Collectors.joining("\n"));
        };
    }

    @Override
    public String toString() {
        AtomicBoolean isFirst = new AtomicBoolean();
        isFirst.set(true);
        SystemComparator comp = new DistanceComparator(getCenter())
            .thenComparing(new MassComparator())
            .thenComparing(new NameComparator());
        return subsystems.stream()
            .sorted(comp)
            .map(indentSubsystemString(isFirst))
            .collect(Collectors.joining("\n"));
    }

    public boolean remove(Body body) {
        if (subsystems.remove(body)) {
            mass = calculateMass();
            return true;
        }

        return subsystems.stream()
            .filter(system -> system instanceof MultiBody)
            .map(MultiBody.class::cast)
            .anyMatch(system -> system.remove(body));
    }
}
