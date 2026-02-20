package sk.tuke.gamestudio.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreServiceRest {
    private final ScoreService scoreService;

    public ScoreServiceRest(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    //GET -> http://localhost:8080/api/score/Pac-Man
    @GetMapping("/{game}")
    public List<Score> getTopScores(@PathVariable String game) {
        return scoreService.getTopScores(game);
    }

    //POST -> http://localhost:8080/api/score
    @PostMapping
    public void addScore(@RequestBody Score score) {
        scoreService.addScore(score);
    }
}

