package com.example.TestingServer.repository;

import com.example.TestingServer.entity.Answer;
import com.example.TestingServer.entity.Group;
import com.example.TestingServer.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long>, JpaSpecificationExecutor<Topic> {
    Optional<Topic> findTopicById(Long topicId);
}