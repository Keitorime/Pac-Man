package sk.tuke.gamestudio.game.core;

public class Mob {
    private int HP;
    private int x;
    private int y;


    public Mob(int x, int y, int HP) {
        this.HP = HP;
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHP() {
        return HP;
    }
    public void reduce_Mob_HP() {
        HP--;
    }

}
