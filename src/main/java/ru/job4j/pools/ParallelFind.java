package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Параллельный поиск индекса в массиве объектов с применением ForkJoinPool.
 * В целях оптимизации, если размер массива не больше 10, используется обычный линейный поиск.
 */
public class ParallelFind<T> extends RecursiveTask<Integer> {

    private final static int SIZE_LINE_SEARCH = 10;
    private final T value;
    private final T[] array;
    private final int from;
    private final int to;

    public ParallelFind(T value, T[] array, int from, int to) {
        this.value = value;
        this.array = array;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= SIZE_LINE_SEARCH) {
            for (int i = from; i <= to; i++) {
               if (value.equals(array[i])) {
                   return i;
               }
            }
            return -1;
        }
        int mid = (from + to) / 2;
        ParallelFind<T> left = new ParallelFind<T>(value, array, from, mid);
        ParallelFind<T> right = new ParallelFind<T>(value, array, mid + 1, to);
        left.fork();
        right.fork();
        Integer leftIndex = left.join();
        Integer rightIndex = right.join();
        return Math.max(leftIndex, rightIndex);
    }

    public static <T> Integer startSearch(T value, T[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelFind<>(value, array, 0, array.length - 1));
    }
}

