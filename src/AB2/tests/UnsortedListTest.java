package AB2.tests;

import static AB2.tests.ArrayFixture.intArray;
import static AB2.tests.TestUtils.assertThat;
import static AB2.tests.TestUtils.assertThatRunnable;

import org.junit.jupiter.api.Test;

import AB2.Collections.List.UnsortedList;

public class UnsortedListTest
{
    @Test
    void hasCorrectInitialValues()
    {
        UnsortedList<Integer> ints = UnsortedList.<Integer>builder()
            .withInitialArray(intArray())
            .build();

        assertThat(ints.count()).isEqualTo(5);
        assertThat(ints.get(0)).isEqualTo(0);
        assertThat(ints.get(-1)).isEqualTo(4);
        assertThat(ints.contains(3)).isEqualTo(true);
    }

    @Test
    void isIndependentOfInitialValues()
    {
        Integer[] initialArray = intArray();
        UnsortedList<Integer> ints = UnsortedList.<Integer>builder()
            .withInitialArray(initialArray)
            .build();

        assertThat(ints.get(0)).isEqualTo(0);

        initialArray[0] = -1;

        assertThat(ints.get(0)).isEqualTo(0);
    }

    @Test
    void canBeManipulated()
    {
        UnsortedList<Integer> ints = UnsortedList.<Integer>builder()
            .withInitialArray(intArray())
            .withAdditiveScaling(10)
            .withMultiplicativeScaling(0)
            .build();
        ints.push(5);
        assertThat(ints.get(5)).isEqualTo(5);
        assertThat(ints.get(14)).isEqualTo(null);
        assertThatRunnable(() -> ints.get(15)).throwsException(new ArrayIndexOutOfBoundsException());
    }

    @Test
    void canLshift()
    {
        UnsortedList<Integer> ints = UnsortedList.<Integer>builder()
            .withInitialArray(intArray())
            .build();
        ints.lshift(2);

        Integer[] expectedArray = new Integer[3];
        for (int i = 0; i < expectedArray.length; i++)
        {
            expectedArray[i] = i + 2;
        }

        UnsortedList<Integer> expected = UnsortedList.<Integer>builder()
            .withInitialArray(expectedArray)
            .build();

        assertThat(ints).containsExactlyElementsOf(expected);
    }
}
