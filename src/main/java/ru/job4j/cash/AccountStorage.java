package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        Optional<Account> accountOptional = getById(account.id());
        boolean result = accountOptional.isEmpty();
        if (result) {
            accounts.put(account.id(), account);
        }
        return result;
    }

    public synchronized boolean update(Account account) {
        Optional<Account> accountOptional = getById(account.id());
        boolean result = accountOptional.isPresent();
        if (result) {
            accounts.replace(account.id(), account);
        }
        return result;
    }

    public synchronized boolean delete(int id) {
        Optional<Account> accountOptional = getById(id);
        boolean result = accountOptional.isPresent();
        if (result) {
            accounts.remove(id, accountOptional.get());
        }
        return result;
    }

    public synchronized Optional<Account> getById(int id) {
        Optional<Account> account = Optional.empty();
        for (Integer key : accounts.keySet()) {
            if (key == id) {
                account = Optional.of(accounts.get(id));
            }
        }
        return account;
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
