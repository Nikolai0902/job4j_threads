package ru.job4j.pools;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Соритировака массива с применением ForkJoinPool.
 */
public class ParallelMergeSort extends RecursiveTask<int[]> {
    private final int[] array;
    private final int from;
    private final int to;

    public ParallelMergeSort(int[] array, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
    }

    /**
     * 1. создаем задачи для сортировки частей
     * 2. производим деление. Оно будет происходить, пока в частях не останется по одному элементу
     * 3. объединяем полученные результаты используя метод класса MergeSort.merge(left, right).
     * @return - обьедененный, отсортированный массив.
     */
    @Override
    protected int[] compute() {
        if (from == to) {
            return new int[] {array[from]};
        }
        int mid = (from + to) / 2;

        ParallelMergeSort leftSort = new ParallelMergeSort(array, from, mid);
        ParallelMergeSort rightSort = new ParallelMergeSort(array, mid + 1, to);

        leftSort.fork();
        rightSort.fork();

        int[] left = leftSort.join();
        int[] right = rightSort.join();
        return MergeSort.merge(left, right);
    }

    public static int[] sort(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelMergeSort(array, 0, array.length - 1));
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(ParallelMergeSort.sort(new  int[] {5, 4, 2, 1, 3, 6})));
    }
}
