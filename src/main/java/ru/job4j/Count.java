package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Использование библиотеки - jcip-annotations.
 * Позволяет с помощью анотаций информировать программиста,
 * о том что у нас есть общие ресурсы и нам нужно аккуратно с ними работать.
 */
@ThreadSafe
public class Count {
    @GuardedBy("this")
    private int value;

    public synchronized void increment() {
        this.value++;
    }

    public synchronized int get() {
        return this.value;
    }
}
