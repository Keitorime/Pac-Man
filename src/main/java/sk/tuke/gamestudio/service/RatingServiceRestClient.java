package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;

@Service
public class RatingServiceRestClient implements RatingService {
    // See value of remote.server.api property in application.properties file
    @Value("${remote.server.api}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) throws GameStudioException {
        restTemplate.postForEntity(url + "/rating", rating, Rating.class);
    }


    @Override
    public int getAverageRating(String game) throws GameStudioException {
        return restTemplate.getForObject(url + "/rating/average/" + game, Integer.class);
    }

    @Override
    public int getRating(String game, String player) throws GameStudioException {
        return restTemplate.getForObject(url + "/rating/" + game + "/" + player, Integer.class);
    }

    @Override
    public void reset() throws GameStudioException {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}