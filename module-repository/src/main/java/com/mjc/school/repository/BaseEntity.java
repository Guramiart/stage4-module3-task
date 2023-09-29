package com.mjc.school.repository;

public interface BaseEntity<K> {

    K getId();
    void setId(K id);

}
