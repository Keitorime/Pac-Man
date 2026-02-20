package sk.tuke.gamestudio.entity;
import java.util.Date;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.io.Serializable;
@Entity
public class Score implements Serializable{
    @Id
    @GeneratedValue
    private int ident;

    private String game;

    private String player;

    private int points;

    private Date playedon;

    public Score() {
    }

    public Score(String game, String player, int points, Date playedon) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.playedon = playedon;
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }


    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedon() {
        return playedon;
    }

    public void setPlayedOn(Date playedOn) {
        this.playedon = playedon;
    }

    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", points=" + points +
                ", playedOn=" + playedon +
                '}';
    }

}
