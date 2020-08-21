package com.home.pratice.bootstrap.http.interfaces;

import org.springframework.stereotype.Repository;

@Repository
public interface BaseDao<T> {
    int add(T clazz);
    int delete(Long id);
    int update(T clazz);
    <T> T query(Long id);
}
