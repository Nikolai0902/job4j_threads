package ru.job4j.synch;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.*;

class SingleLockListTest {

    @Test
    public void add() throws InterruptedException {
        SingleLockList<Integer> list = new SingleLockList<>(new ArrayList<>());
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        Thread thread = new Thread(() -> list.add(3));
        first.start();
        second.start();
        thread.start();
        first.join();
        second.join();
        thread.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl).contains(1, 2, 3);
    }
}