package com.utcn.dao;

import org.springframework.data.repository.CrudRepository;
import com.utcn.model.Comment;

// No need to implement this. Spring does this automatically
public interface CommentRepository extends CrudRepository<Comment, Long> {

}
