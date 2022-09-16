package ru.job4j.pools;

import java.util.Objects;
/**
 * В данный класс сведены результаты подсчета сумм:
 * - sums[i].rowSum - сумма элементов по i строке.
 * - sums[i].colSum  - сумма элементов по i столбцу.
 */
public class Sums {
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
