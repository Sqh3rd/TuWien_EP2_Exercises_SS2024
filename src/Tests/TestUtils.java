package Tests;

import AB2.Collections.List.List;
import Collections.Set.Set;

import java.util.Objects;

public class TestUtils {
    public static <T> Asserter<T> assertThat(T obj) {
        return new Asserter<>(obj);
    }

    public static <T> ArrayAsserter<T> assertThat(T[] array) {
        return new ArrayAsserter<>(array);
    }

    public static <T> ListAsserter<T> assertThat(List<T> unsortedList) {
        return new ListAsserter<>(unsortedList);
    }

    public static <T> SetAsserter<T> assertThat(Set<T> set) { return new SetAsserter<>(set); }

    public static RunnableAsserter assertThatRunnable(Runnable func) {
        return new RunnableAsserter(func);
    }

    private static <T> void assertEqual(T actual, T expected, String fieldName) {
        if (!Objects.equals(actual, expected))
            throw new AssertionError(STR."Expected \{fieldName} to equal \{expected} but was \{actual} instead");
    }

    public static class Asserter<T> {
        private final T actual;

        Asserter(T actual) {
            this.actual = actual;
        }

        public void isEqualTo(T expected) {
            assertEqual(actual, expected, actual == null ? "null" : actual.getClass().getName());
        }

        public void isNotEqualTo(T unexpected) {
            if (Objects.equals(actual, unexpected))
                throw new AssertionError(STR."Expected \{actual} to not equal \{unexpected}");
        }
    }

    public static class ArrayAsserter<T> {
        private final T[] actual;

        ArrayAsserter(T[] actual) {
            this.actual = actual;
        }

        public void containsExactly(T[] expected) {
            assertEqual(actual.length, expected.length, "length");
            for (int i = 0; i < actual.length; i++) {
                assertEqual(actual[i], expected[i], STR."array element at index \{i}");
            }
        }
    }

    public static class ListAsserter<T> {
        private final List<T> actual;

        ListAsserter(List<T> actual) {
            this.actual = actual;
        }

        public void containsExactlyElementsOf(List<T> expected) {
            for (int i = 0; i < actual.count(); i++) {
                assertEqual(actual.get(i), expected.get(i), STR."list entry at index \{i}");
            }
        }

        public void hasSameDimensionsAs(List<T> expected) {
            assertEqual(actual.count(), expected.count(), "count");
            assertEqual(actual.size(), expected.size(), "size");
        }
    }

    public static class SetAsserter<T> {
        private final Set<T> set;

        SetAsserter(Set<T> set) {
            this.set = set;
        }

        public void isEqualTo(Set<T> other) {
            if (!Set.equals(set, other))
                throw new AssertionError(STR."Expected \{set.getClass().getName()} to equal \{other} but was \{set} instead");
        }
    }

    public static class RunnableAsserter {
        private final Runnable func;

        RunnableAsserter(Runnable func) {
            this.func = func;
        }

        public void throwsException(Exception expected) {
            try {
                func.run();
            } catch (Exception actual) {
                assertEqual(actual.getClass(), expected.getClass(), "exception type");
                if (expected.getMessage() != null)
                    assertEqual(actual.getMessage(), expected.getMessage(), "message");
            }
        }
    }
}
