package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.ContentTopic;

@Repository
public interface ContentTopicRepository extends JpaRepository<ContentTopic, Integer>{

}
