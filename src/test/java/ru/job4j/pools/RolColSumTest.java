package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import ru.job4j.pools.RolColSum.Sums;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    void testSumMatrix() {
        int[][] matrix = {{1, 1, 1}, {1, 1, 1}, {2, 2, 2}};
        Sums[] expected = {new Sums(3, 4), new Sums(3, 4), new Sums(6, 4)};
        Sums[] result = RolColSum.sum(matrix);
        assertThat(expected).isEqualTo(result);
    }

    @Test
    void testAsyncSumMatrix() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 1, 1}, {1, 1, 1}, {2, 2, 2}};
        Sums[] expected = {new Sums(3, 4), new Sums(3, 4), new Sums(6, 4)};
        Sums[] result = RolColSum.asyncSum(matrix);
        assertThat(expected).isEqualTo(result);
    }

}