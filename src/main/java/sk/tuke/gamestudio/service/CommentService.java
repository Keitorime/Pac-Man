package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.*;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment) throws GameStudioException;
    List<Comment> getComments(String game) throws GameStudioException;
    void reset() throws GameStudioException;
}
