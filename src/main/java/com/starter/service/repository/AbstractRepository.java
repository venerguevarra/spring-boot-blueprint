package com.starter.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starter.service.model.AbstractEntity;

public interface AbstractRepository<T extends AbstractEntity> extends JpaRepository<Long, T> {

}
