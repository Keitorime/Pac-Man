package sk.tuke.gamestudio.game.core;

public class Hero {
    private String name;
    private int HP;
    private int x;
    private int y;

    public Hero(String name) {
        this.name = name;
        this.HP = 3;
    }

    public String getName() {
        return name;
    }

    public int getHP() {
        return HP;
    }
    public void reduce_Hero_HP() {
        HP--;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
