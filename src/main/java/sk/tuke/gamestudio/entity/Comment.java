package sk.tuke.gamestudio.entity;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.io.Serializable;
@Entity
public class Comment implements Serializable{
    @Id
    @GeneratedValue
    private int ident;
    private String game;
    private String playerName;
    private String commentText;
    private Date commentedOn;

    public Comment() {
    }

    public Comment(String game, String playerName, String commentText, Date commentedOn) {
        this.game = game;
        this.playerName = playerName;
        this.commentText = commentText;
        this.commentedOn = commentedOn;
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCommentedOn() {
        return commentedOn;
    }

    public void setCommentedOn(Date commentedOn) {
        this.commentedOn = commentedOn;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "game='" + game + '\'' +
                ", playerName='" + playerName + '\'' +
                ", commentText='" + commentText + '\'' +
                ", commentedOn=" + commentedOn +
                '}';
    }
}