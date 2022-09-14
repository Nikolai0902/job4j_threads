package ru.job4j.pools;

import java.util.Arrays;

/**
 * Объединение двух отсортированных массива массивов.
 */
public class MergeSort {
    public static int[] merge(int[] left, int[] right) {
        int li = 0;
        int ri = 0;
        int resI = 0;
        int[] result = new int[left.length + right.length];
        while (resI != result.length) {
            if (li == left.length) {
                result[resI++] = right[ri++];
            } else if (ri == right.length) {
                result[resI++] = left[li++];
            } else if (left[li] <= right[ri]) {
                result[resI++] = left[li++];
            } else {
                result[resI++] = right[ri++];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] one = new int[]{2, 1};
        int[] two = new int[]{4, 3};
        System.out.println(Arrays.toString(MergeSort.merge(one, two)));
    }
}
