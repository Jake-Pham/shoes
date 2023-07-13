package com.kiva.application.service;

import org.springframework.stereotype.Service;

import com.kiva.application.entity.Comment;
import com.kiva.application.model.request.CreateCommentPostRequest;
import com.kiva.application.model.request.CreateCommentProductRequest;

@Service
public interface CommentService {
    Comment createCommentPost(CreateCommentPostRequest createCommentPostRequest,long userId);
    Comment createCommentProduct(CreateCommentProductRequest createCommentProductRequest, long userId);
}
