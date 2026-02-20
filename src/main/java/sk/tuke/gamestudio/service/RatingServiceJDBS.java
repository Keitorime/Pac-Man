package sk.tuke.gamestudio.service;
import sk.tuke.gamestudio.entity.Rating;

import java.sql.*;
import java.util.List;
public class    RatingServiceJDBS implements RatingService{
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String INSERT_OR_UPDATE = "INSERT INTO rating (player, game, rating) VALUES (?, ?, ?)";
    public static final String SELECT_AVERAGE = "SELECT AVG(points) FROM score WHERE player = ?";
    public static final String SELECT_RATING = "SELECT rating FROM rating WHERE player = ? AND game = ? ORDER BY rating DESC";
    public static final String DELETE = "DELETE FROM rating";
    private int rating;

    @Override
    public void setRating(Rating rating) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT_OR_UPDATE)
        ) {
            statement.setString(1, rating.getPlayer());
            statement.setString(2, rating.getGame());
            statement.setInt(3, rating.getRating());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException("Problem setting rating", e);
        }
    }


    @Override
    public int getAverageRating(String player) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_AVERAGE)
        ) {
            statement.setString(1, player);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new GameStudioException("Problem getting average rating", e);
        }
        return 0;
    }


    @Override
    public int getRating(String player, String game) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_RATING)
        ) {
            statement.setString(1, player);
            statement.setString(2, game);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new GameStudioException("Problem get  ting rating", e);
        }
        return 0; // Return default value if no rating found
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new GameStudioException("Problem deleting ratings", e);
        }
    }

}
