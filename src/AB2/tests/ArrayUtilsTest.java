package AB2.tests;

import static AB2.tests.TestUtils.assertThat;

import org.junit.jupiter.api.Test;

import AB2.ArrayUtils;

public class ArrayUtilsTest
{
    @Test
    void binarySearch() {
        Integer[] testArray = new Integer[10];
        for (int i = 0; i < testArray.length; i++) {
            testArray[i] = i;
        }

        assertThat(ArrayUtils.Search.binarySearch(testArray, Integer::compare, 5, testArray.length - 1, false))
            .isEqualTo(5);
        assertThat(ArrayUtils.Search.binarySearch(testArray, Integer::compare, 10, testArray.length - 1, false))
            .isEqualTo(-1);
        assertThat(ArrayUtils.Search.binarySearch(testArray, Integer::compare, 10, testArray.length - 1, true))
            .isEqualTo(10);
    }

    @Test
    void quickSort() {
        Integer[] testArray = new Integer[]{ 5, 0, 2, 1, 6, 7, 3, 8, 4, 9};
        Integer[] expected = new Integer[10];
        for (int i = 0; i < expected.length; i++) {
            expected[i] = i;
        }

        assertThat(ArrayUtils.Sort.quicksort(testArray, Integer::compare))
            .containsExactly(expected);
    }
}
