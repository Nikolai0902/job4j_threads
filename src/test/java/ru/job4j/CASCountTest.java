package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {
    @Test
    public void when2ThreadsResult10()
            throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread1 = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        casCount.increment();
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(casCount.get()).isEqualTo(10);
    }
}