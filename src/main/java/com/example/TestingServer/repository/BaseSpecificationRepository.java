package com.example.TestingServer.repository;

import com.example.TestingServer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseSpecificationRepository<T, D> extends JpaRepository<T, D>, JpaSpecificationExecutor<T> {
}
