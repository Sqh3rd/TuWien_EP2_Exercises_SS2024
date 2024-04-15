package AB3.tests;

import static AB2.tests.TestUtils.assertThat;

import org.junit.jupiter.api.Test;

import AB3.Collections.Map.TreeMap;

public class TreeMapTest
{
    @Test
    void canPutAndGetValues()
    {
        TreeMap<Integer, String> map = new TreeMap<>(Integer::compare);
        map.put(1, "1");
        map.put(10, "10");

        assertThat(map.get(1)).isEqualTo("1");
        assertThat(map.get(10)).isEqualTo("10");
    }

    @Test
    void toStringIsCorrect()
    {
        TreeMap<Integer, String> map = new TreeMap<>(Integer::compare);
        map.put(1, "1");
        map.put(10, "10");
        map.put(20, "20");
        map.put(-1, "-1");
        map.put(15, "15");

        assertThat(map.toString()).isEqualTo("{ { -1: -1 }, 1: 1, { 10: 10, { { 15: 15 }, 20: 20 } } }");
    }

    @Test
    void toStringRepresentationIsIndependentOfOrder()
    {
        TreeMap<Integer, String> map1 = new TreeMap<>(Integer::compare);
        map1.put(1, "1");
        map1.put(10, "10");
        map1.put(20, "20");
        map1.put(-1, "-1");
        map1.put(15, "15");
        TreeMap<Integer, String> map2 = new TreeMap<>(Integer::compare);
        map2.put(15, "15");
        map2.put(-1, "-1");
        map2.put(20, "20");
        map2.put(1, "1");
        map2.put(10, "10");

        assertThat(map1.toString()).isNotEqualTo(map2.toString());
        String map1WithoutBraces = map1.toString().replaceAll("[/{} ]", "");
        String map2WithoutBraces = map2.toString().replaceAll("[/{} ]", "");
        assertThat(map1WithoutBraces).isEqualTo(map2WithoutBraces);
    }
}
