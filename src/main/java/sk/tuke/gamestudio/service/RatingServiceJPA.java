package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
        List<Integer> existRating = entityManager.createQuery("select r.rating from Rating r where r.player = :player", Integer.class)
                .setParameter("player", rating.getPlayer())
                .getResultList();
        if (existRating.isEmpty()) {
            entityManager.persist(rating);
        }
        else {
            entityManager.createQuery("update Rating set rating = :rating where game = :game and player = :player")
                    .setParameter("rating", rating.getRating())
                    .setParameter("game", rating.getGame())
                    .setParameter("player", rating.getPlayer())
                    .executeUpdate();
        }
    }

    @Override
    public int getAverageRating(String game) {
        List<Integer> ratings = entityManager.createQuery("select r.rating from Rating r where r.game = :game", Integer.class)
                .setParameter("game", game)
                .getResultList();

        if (ratings.isEmpty()) {
            return 0;
        }

        int totalRating = 0;
        for (int rating : ratings) {
            totalRating += rating;
        }

        // Calculate and return the average rating
        return totalRating / ratings.size();
    }

    @Override
    public int getRating(String game, String player) throws GameStudioException {
        Integer rating = entityManager.createQuery("select r.rating from Rating r where r.game = :game and r.player = :player", Integer.class)
                .setParameter("game", game)
                .setParameter("player", player)
                .getSingleResult();

        if (rating == null) {
            throw new GameStudioException("Rating not found for game: " + game + " and player: " + player);
        }

        return rating;
    }
    @Override
    public void reset() {
        entityManager.createNativeQuery("delete from rating").executeUpdate();
    }
}