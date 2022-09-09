package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> accountFId = getById(fromId);
        Optional<Account> accountTId = getById(toId);
        boolean result = accountFId.isPresent() && accountTId.isPresent()
                && accountFId.get().amount() >= amount;
        if (result) {
            update(new Account(fromId, accountFId.get().amount() - amount));
            update(new Account(toId, accountTId.get().amount() + amount));
        }
        return result;
    }
}
