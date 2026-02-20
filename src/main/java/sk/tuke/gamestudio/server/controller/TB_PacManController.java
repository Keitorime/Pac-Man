package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import sk.tuke.gamestudio.game.core.gamefield;


@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class TB_PacManController {
    @RequestMapping("/Pac-Man")
    public String minePage() {
        return "index.html";
    }
    @RequestMapping("level")
    public String gamePage() {
        return "level.html";
    }
    gamefield gameField = new gamefield();
    @PostMapping("/generateCoins")
    public ResponseEntity<String> generateCoins() {
        // Виклик функції для генерації монстрів
        gameField.generateCoins(); //зроби паблік, поміняти, якщо знайду інше рішення
        return ResponseEntity.ok("Coins generated successfully");
    }
    @PostMapping("/generateHero")
    public ResponseEntity<String> generateHero() {
        // Виклик функції для генерації монстрів
        gameField.generateHero(); //зроби паблік, поміняти, якщо знайду інше рішення
        return ResponseEntity.ok("Hero generated successfully");
    }



}
