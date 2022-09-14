package ru.job4j.pools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Класс вычисляет сумму элементов по диагоналям матрицы.
 * Релизация вычисления идет асинхронно с помощью CompletableFuture.
 */
public class SumDig {
    public static int[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        int[] sums = new int[2 * n];
        Map<Integer, CompletableFuture<Integer>> futures = new HashMap<>();
        // считаем сумму по главной диагонали
        futures.put(0, getTask(matrix, 0, n - 1, n - 1));
        // считаем суммы по побочным диагоналям
        for (int k = 1; k <= n; k++) {
            futures.put(k, getTask(matrix, 0, k - 1, k - 1));
            if (k < n) {
                futures.put(2 * n - k, getTask(matrix, n - k, n - 1, n - 1));
            }
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    /**
     * Осной метод подсчета суммы в одной из указанных диагоналей матрицы.
     * Метода запускают асинхронную задачу.
     * @param data - матрица.
     * @param startRow - номер начальной строки.
     * @param endRow - номер конечной строки.
     * @param startCol - номер колонки.
     * @return - объект CompletableFuture. Результат асинхронной задачи.
     */
    public static CompletableFuture<Integer> getTask(int[][] data, int startRow, int endRow, int startCol) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            int col = startCol;
            for (int i = startRow; i <= endRow; i++) {
                sum += data[i][col];
                col--;
            }
            return sum;
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(Arrays.toString(asyncSum(matrix)));
    }
}
