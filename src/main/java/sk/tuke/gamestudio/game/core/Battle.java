package sk.tuke.gamestudio.game.core;

import java.util.Random;

public class Battle {
    public static void fight(Hero hero, Mob mob) {
        Random random = new Random();
        int randomNum = random.nextInt(10); // Генеруємо випадкове число від 0 до 9

        if (randomNum % 2 == 0) {
            // Парне число, тому зменшуємо HP у моба
            mob.reduce_Mob_HP();
        } else {
            // Непарне число, тому зменшуємо HP у гравця
            hero.reduce_Hero_HP();
        }
    }
}