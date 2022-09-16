package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Производит подсчет суммы по строкам и столбцам квадратной матрицы.
 */
public class RolColSum {
    /**
     * Последовательная версия программы
     * @param matrix - матрица значений.
     * @return - результаты подсчета.
     */
    public static Sums[] sum(int[][] matrix) {
        Sums[] matrixSum = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            matrixSum[i] = getTask(matrix, i);
        }
        return matrixSum;
    }

    /**
     * Асинхронная версия программы. i - я задача считает сумму по i столбцу и i строке
     * @param matrix - матрица значений.
     * @return - результаты подсчета суммы элементов.
     * @throws ExecutionException - исключение исполнения.
     * @throws InterruptedException - исклбчение прерывания.
     */
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] matrixSum = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int finalI = i;
            matrixSum[i] = CompletableFuture.supplyAsync(() -> getTask(matrix, finalI)).get();
        }
        return matrixSum;
    }

    private static Sums getTask(int[][] matrix, int i) {
        int sumRow = 0;
        int sumCol = 0;
        for (int j = 0; j < matrix.length; j++) {
            sumCol += matrix[j][i];
            sumRow += matrix[i][j];
        }
        return new Sums(sumRow, sumCol);
    }
}
