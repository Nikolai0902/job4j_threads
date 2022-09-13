package ru.job4j.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        BiFunction<Integer, Base, Base> biFunc = (keyCache, modelCache) -> {
            if (modelCache.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base newModelCache = new Base(modelCache.getId(), modelCache.getVersion() + 1);
            newModelCache.setName(model.getName());
            return newModelCache;
        };
        return memory.computeIfPresent(model.getId(), biFunc) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public ConcurrentHashMap<Integer, Base> all() {
        return new ConcurrentHashMap<>(memory);
    }
}
