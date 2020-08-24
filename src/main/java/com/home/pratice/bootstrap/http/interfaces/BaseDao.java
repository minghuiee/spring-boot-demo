package com.home.pratice.bootstrap.http.interfaces;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseDao<T, T2> {
    int add(T clazz);

    int delete(String id);

    int update(T clazz);

    T2 query(String id);

    List<T2> queryList(T clazz);
}
