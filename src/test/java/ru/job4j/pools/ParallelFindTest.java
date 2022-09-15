package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import ru.job4j.cache.OptimisticException;

import static org.assertj.core.api.Assertions.*;

class ParallelFindTest {

    @Test
    void testSearchSizeArrayLess10() {
        Integer[] values = new Integer[]{0, 1, 100, 3, 4};
        var result = ParallelFind.startSearch(100, values);
        assertThat(result).isEqualTo(2);
    }

    @Test
    void testSearchSizeArrayMore10() {
        Integer[] values = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 100, 12, 13};
        var result = ParallelFind.startSearch(100, values);
        assertThat(result).isEqualTo(11);
    }

    @Test
    void testSearchSize0() {
        Integer[] values = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 100, 12, 13};
        var result = ParallelFind.startSearch(1000, values);
        assertThat(result).isEqualTo(-1);
    }
}