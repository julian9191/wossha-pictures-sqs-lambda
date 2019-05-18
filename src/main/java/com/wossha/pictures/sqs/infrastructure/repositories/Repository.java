package com.wossha.pictures.sqs.infrastructure.repositories;

public interface Repository<T> {

    void add(T entity);

    void remove(T entity);

    void update(T entity);

}