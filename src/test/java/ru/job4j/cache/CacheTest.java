package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    void whenAdd() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        base.setName("Nik");
        assertThat(cache.add(base)).isTrue();
    }

    @Test
    void whenDelete() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        base.setName("Nik");
        cache.add(base);
        cache.delete(base);
        assertThat(cache.all()).isEmpty();
    }

    @Test
    void whenUpdate() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        Base baseNew = new Base(1, 1);
        cache.update(baseNew);

        Base actual = new Base(1, 2);
        Base expected = cache.all().get(base.getId());
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void whenUpdateException() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        Base baseNew = new Base(1, 2);
        assertThatThrownBy(() -> cache.update(baseNew)).isInstanceOf(OptimisticException.class);
    }
}