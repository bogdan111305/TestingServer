package com.example.TestingServer.repository;


import com.example.TestingServer.entity.Result;
import com.example.TestingServer.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long>, JpaSpecificationExecutor<Result> {
    List<Result> findAllByUser(User user);

    Optional<Result>  findReportByIdAndUser(Long id, User user);

    Optional<Result>  findReportById(Long id);
}
