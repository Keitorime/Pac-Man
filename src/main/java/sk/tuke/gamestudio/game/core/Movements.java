package sk.tuke.gamestudio.game.core;

public class Movements extends gamefield {
    private int x;
    private int y;

    public Movements(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        this.x = 0;
        this.y = 0;
    }
public void removePlayerCoin(Movements movements) {
    coins[movements.getY()][movements.getX()] = 0;
}

    @Override
    public void removeCoin(int x, int y) {
        super.removeCoin(x, y);
        removePlayerCoin(new Movements(x,y));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveUp() {
        y = Math.max(0, y - 1);
    }

    public void moveDown() {
        y = Math.min(height - 1, y + 1);
    }

    public void moveLeft() {
        x = Math.max(0, x - 1);
    }

    public void moveRight() {
        x = Math.min(width - 1, x + 1);
        
    }
}
