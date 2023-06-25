package org.salon.dao;

import java.util.List;

public interface DAO <T> {
    T get(long id);

    List<T> getAll();

    T create(T t);

    void update(T t);

    void delete(long id);
}
