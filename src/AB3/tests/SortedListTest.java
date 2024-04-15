package AB3.tests;

import static AB2.tests.ArrayFixture.intArray;
import static AB2.tests.ArrayFixture.scrambledIntArray;
import static AB2.tests.TestUtils.assertThat;

import org.junit.jupiter.api.Test;

import AB2.Collections.List.SortedList;
import AB2.Collections.List.UnsortedList;

public class SortedListTest
{
    @Test
    void sortsInternalArrayOnCreation()
    {
        SortedList<Integer> actual = SortedList.<Integer>builder()
            .withInitialArray(scrambledIntArray())
            .withComparator(Integer::compare)
            .build();
        UnsortedList<Integer> expected = UnsortedList.<Integer>builder()
            .withInitialArray(intArray())
            .build();

        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    void sortsOnPush()
    {
        SortedList<Integer> actual = SortedList.<Integer>builder()
            .withInitialArray(scrambledIntArray())
            .withComparator(Integer::compare)
            .build();
        actual.push(-1);
        actual.push(-3);
        actual.push(-2);
        actual.push(5);

        Integer[] expectedArray = new Integer[9];
        for (int i = 0; i < expectedArray.length; i++)
        {
            expectedArray[i] = i - 3;
        }

        UnsortedList<Integer> expected = UnsortedList.<Integer>builder()
            .withInitialArray(expectedArray)
            .build();

        assertThat(actual).containsExactlyElementsOf(expected);
    }
}
