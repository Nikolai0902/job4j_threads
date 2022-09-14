package ru.job4j.pools;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Параллельный поиск индекса в массиве объектов с применением ForkJoinPool.
 * В целях оптимизации, если размер массива не больше 10, используется обычный линейный поиск.
 */
public class ParallelFind extends RecursiveTask<Integer> {

    private final static int SIZE_LINE_SEARCH = 10;
    private final int value;
    private final Integer[] array;
    private final int from;
    private final int to;

    public ParallelFind(int value, Integer[] array, int from, int to) {
        this.value = value;
        this.array = array;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (from == to) {
            return array[to] == value ? to : -1;
        }
        int mid = (from + to) / 2;
        ParallelFind left = new ParallelFind(value, array, from, mid);
        ParallelFind right = new ParallelFind(value, array, mid + 1, to);
        left.fork();
        right.fork();
        int leftIndex = left.join();
        int rightIndex = right.join();
        return leftIndex == -1 ? rightIndex : leftIndex;
    }

    private static Integer lineSearch(int value, Integer[] array) {
        return List.of(array).indexOf(value);
    }

    public static int startSearch(int value, Integer[] array) {
        if (array.length <= SIZE_LINE_SEARCH) {
            return lineSearch(value, array);
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelFind(value, array, 0, array.length - 1));
    }

    public static void main(String[] args) {
        Integer[] values = new Integer[]{1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1};
        System.out.println(ParallelFind.startSearch(0, values));
    }
}
