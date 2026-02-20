package sk.tuke.gamestudio.game.core;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;


public class gamefield {
    public int height;
    public int width;
    public int[][] coins;
    private Mob[] mobs;
    private Hero hero;

    public gamefield(int height, int width) {
        this.height = height;
        this.width = width;
        coins = new int[height][width];
        generateCoins();
        generateMonsters();
        generateHero();
    }

    public gamefield() {

    }


    public void generateMonsters() {
        Random random = new Random();
        mobs = new Mob[3];
        for (int i = 0; i < mobs.length; i++) {
            int x, y;
            do {
                x = random.nextInt(width);
                y = random.nextInt(height);
            } while (isOccupied(x, y));
            mobs[i] = new Mob(x, y, 2);
        }
    }
    public void removeMonster(int x, int y) {
        List<Mob> monstersCopy = new ArrayList<>();
        for (Mob monster : mobs) {
            if (monster.getX() != x || monster.getY() != y) {
                monstersCopy.add(monster); // Додаємо моба до списку, якщо його координати не співпадають з x та y
            }
        }
        mobs = monstersCopy.toArray(new Mob[0]); // Оновлюємо посилання на список мобів
    }

    public void generateHero() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(width);
            y = random.nextInt(height);
        } while (isOccupied(x, y)); // Перевіряємо чи позиція зайнята
        hero = new Hero("Keito"); // Припустимо, герой має 3 HP
        hero.setX(x);
        hero.setY(y);
    }

    public void generateCoins() {
        Random random = new Random();
        coins = new int[height][width];
        boolean[][] positionsOccupied = new boolean[height][width];
        int totalPositions = height * width;
        int coinsToGenerate = totalPositions / 2;
        for (int xfor = 0; xfor < coinsToGenerate; xfor++) {
            int x, y;
            do {
                x = random.nextInt(width);
                y = random.nextInt(height);
            } while (positionsOccupied[y][x]);

            coins[y][x] = 1;
            positionsOccupied[y][x] = true;
        }
    }

    public boolean isMonster(int x, int y) {
        if (mobs != null) {
            for (Mob mob : mobs) {
                if (mob != null && mob.getX() == x && mob.getY() == y) {
                    return true;
                }
            }
        }
        return false;
    }


    private boolean isOccupied(int x, int y) {
        if (hero != null && x == hero.getX() && y == hero.getY()) {
            return true;
        }
        return isMonster(x, y);
    }

    public List<Mob> getAllMonsters() {
        List<Mob> allMonsters = new ArrayList<>();
        for (Mob mob : mobs) {
            allMonsters.add(mob);
        }
        return allMonsters;
    }


    public boolean isCoin(int x, int y) {
        return coins[y][x] == 1;
    }

    public void removeCoin(int x, int y) {
        coins[y][x] = 0;

    }

    public int getTotalCoins() {
        int totalCoins = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isCoin(x, y)) {
                    totalCoins++;
                }
            }
        }
        return totalCoins;
    }

}
