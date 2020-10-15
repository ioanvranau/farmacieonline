package com.utcn.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.common.collect.Lists;
import com.utcn.dao.CommentRepository;
import com.utcn.model.Comment;


@Component
public class CommentService {

    @Autowired
    private CommentRepository commentRepository; // DAO

    public List<Comment> getAllComments() {
        return Lists.newArrayList(commentRepository.findAll());
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    public void delete(Long id) {
        commentRepository.delete(id);
    }

    public Comment findById(Long id) {
        return commentRepository.findOne(id);
    }
}
