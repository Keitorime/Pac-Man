package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.*;

import java.util.List;

public interface RatingService {
    void setRating(Rating rating) throws GameStudioException;
    int getAverageRating(String game) throws GameStudioException;
    int getRating(String game, String player) throws GameStudioException;
    void reset() throws GameStudioException;
}
