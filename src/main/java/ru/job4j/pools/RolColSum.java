package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Производит расчет суммы по строкам и столбцам квадратной матрицы.
 */
public class RolColSum {
    /**
     * В данный класс сведены результаты посчета сумм:
     * - sums[i].rowSum - сумма элементов по i строке.
     * - sums[i].colSum  - сумма элементов по i столбцу.
     */
    public static class Sums {
        private final int rowSum;
        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] matrixSum = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int sumRow = 0;
            int sumCol = 0;
            int j = 0;
            while (j < matrix.length) {
                sumCol += matrix[j][i];
                sumRow += matrix[i][j];
                j++;
            }
            matrixSum[i] = new Sums(sumRow, sumCol);
        }
        return matrixSum;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] matrixSum = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            matrixSum[i] = getTask(matrix, i).get();
        }
        return matrixSum;
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> {
            int sumRow = 0;
            int sumCol = 0;
            int j = 0;
            while (j < matrix.length) {
                sumCol += matrix[j][i];
                sumRow += matrix[i][j];
                j++;
            }
            return new Sums(sumRow, sumCol);
        });
    }
}
