package com.kiva.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiva.application.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

}
