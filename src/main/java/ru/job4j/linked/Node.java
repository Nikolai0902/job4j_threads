package ru.job4j.linked;

public final class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        Node<T> node = next;
        this.next = node;
        this.value = value;
    }

    public Node<T> getNext() {
        Node<T> node = next;
        return node;
    }

    public T getValue() {
        return value;
    }
}
