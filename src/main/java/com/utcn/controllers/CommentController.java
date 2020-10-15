package com.utcn.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.utcn.model.Comment;
import com.utcn.model.Medicament;
import com.utcn.services.CommentService;
import com.utcn.services.MedicamentService;

@RestController
public class CommentController {


    @Autowired
    private CommentService commentService;

    @Autowired
    private MedicamentService medicamentService;

    @RequestMapping("/comment")
    public
    @ResponseBody
    List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<Comment> addComment(
            @RequestBody Comment comment) {
        if (comment != null) {
            if (comment.getDislikes() == null) {
                comment.setDislikes(0);
            }

            if (comment.getLikes() == null) {
                comment.setLikes(0);
            }
            if (comment.getMedicamentId() != null) {
                final Comment savedComment = commentService.save(comment);
                final Medicament medicament = medicamentService.findById(comment.getMedicamentId());
                medicament.getComments().add(savedComment);
                medicamentService.save(medicament);
                return new ResponseEntity<Comment>(comment, HttpStatus.OK);
            }
        }
        return new ResponseEntity<Comment>(new Comment(),
                HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/increaseLikes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<Comment> increaseLikes(
            @RequestBody Comment comment) {

        if(comment.getMedicamentId() != null) {
            final Medicament byId = medicamentService.findById(comment.getMedicamentId());
            if(comment.getId() != null) {
                final Comment commentById = commentService.findById(comment.getId());
                if(byId.getComments().contains(commentById)) {
                    final Integer likes = commentById.getLikes();
                    if(likes != null) {
                        commentById.setLikes(likes + 1);
                    } else {
                        commentById.setLikes(1);
                    }
                    commentService.save(commentById);
                    return new ResponseEntity<Comment>(new Comment(), HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<Comment>(new Comment(),
                HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/increaseDislikes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<Comment> increaseDislikes(
            @RequestBody Comment comment) {

        if(comment.getMedicamentId() != null) {
            final Medicament byId = medicamentService.findById(comment.getMedicamentId());
            if(comment.getId() != null) {
                final Comment commentById = commentService.findById(comment.getId());
                if(byId.getComments().contains(commentById)) {
                    final Integer dislikes = commentById.getDislikes();
                    if(dislikes != null) {
                        commentById.setDislikes(dislikes + 1);
                    } else {
                        commentById.setDislikes(1);
                    }
                    commentService.save(commentById);
                    return new ResponseEntity<Comment>(new Comment(), HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<Comment>(new Comment(),
                HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/comment", method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseEntity<Comment> deleteComment(@RequestBody Comment comment) {
        if(comment.getMedicamentId() != null) {
            final Medicament byId = medicamentService.findById(comment.getMedicamentId());
            if(comment.getId() != null) {
                final Comment commentById = commentService.findById(comment.getId());
                if(byId.getComments().contains(commentById)) {
                    byId.getComments().remove(commentById);
                    medicamentService.save(byId);
                    commentService.delete(commentById);
                }
            }
        }
        return new ResponseEntity<Comment>(new Comment(), HttpStatus.OK);
    }
}
