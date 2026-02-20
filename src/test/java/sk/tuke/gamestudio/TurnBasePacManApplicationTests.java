package sk.tuke.gamestudio;
import sk.tuke.gamestudio.game.core.gamefield;
import sk.tuke.gamestudio.game.core.Mob;
import sk.tuke.gamestudio.game.core.Hero;
import sk.tuke.gamestudio.game.core.Riddle;
import sk.tuke.gamestudio.game.core.Movements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class TurnBasePacManApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    public void testGenerateMonsters() {
        gamefield field = new gamefield(5, 5);
        Mob[] mobs = field.getAllMonsters().toArray(new Mob[0]);
        assertEquals(3, mobs.length); // Перевіряємо, чи згенеровано 3 мобів
    }
    @Test
    public void testGenerateHero() {
        Hero hero = new Hero("Keito");
        assertNotNull(hero); // Перевіряємо, чи герой згенерований і не є null
    }

    @Test
    public void testGenerateCoins() {
        gamefield field = new gamefield(5, 5);
        int totalCoins = field.getTotalCoins();
        assertEquals(12, totalCoins); // Перевіряємо, чи згенеровано 12 монет
    }

    //forriddle
    @Test
    public void testCheckAnswer_CorrectAnswer() {
        Riddle riddle = new Riddle("What has to be broken before you can use it?", "an egg");
        assertTrue(riddle.checkAnswer("an egg"));
        assertTrue(riddle.checkAnswer("An Egg")); // Перевіряємо, чи регістр не має значення
        assertTrue(riddle.checkAnswer("AN EGG")); // Перевіряємо, чи регістр не має значення
        assertTrue(riddle.checkAnswer("an EGG")); // Перевіряємо, чи регістр не має значення
        assertTrue(riddle.checkAnswer("An egg ")); // Перевіряємо, чи регістр не має значення та зайві пробіли
    }

    @Test
    public void testCheckAnswer_IncorrectAnswer() {
        Riddle riddle = new Riddle("What has to be broken before you can use it?", "an egg");
        assertFalse(riddle.checkAnswer("a rock"));
        assertFalse(riddle.checkAnswer("An apple")); // Перевіряємо, чи регістр не має значення
        assertFalse(riddle.checkAnswer("AN orange")); // Перевіряємо, чи регістр не має значення
        assertFalse(riddle.checkAnswer("a book")); // Перевіряємо, чи регістр не має значення
    }

    @Test
    public void testMoveUp() {
        Movements movements = new Movements(5, 5);
        movements.moveUp();
        assertEquals(0, movements.getX()); // Перевіряємо, чи координата X залишилась та ж сама
        assertEquals(0, movements.getY()); // Перевіряємо, чи координата Y зменшилась на 1
    }

    @Test
    public void testMoveDown() {
        Movements movements = new Movements(5, 5);
        movements.moveDown();
        assertEquals(0, movements.getX()); // Перевіряємо, чи координата X залишилась та ж сама
        assertEquals(1, movements.getY()); // Перевіряємо, чи координата Y збільшилась на 1
    }
    @Test
    public void testMoveLeft() {
        Movements movements = new Movements(5, 5);
        movements.moveLeft();
        assertEquals(0, movements.getX()); // Перевіряємо, чи координата X зменшилась на 1
        assertEquals(0, movements.getY()); // Перевіряємо, чи координата Y залишилась та ж сама
    }
    @Test
    public void testMoveRight() {
        Movements movements = new Movements(5, 5);
        movements.moveRight();
        assertEquals(1, movements.getX()); // Перевіряємо, чи координата X збільшилась на 1
        assertEquals(0, movements.getY()); // Перевіряємо, чи координата Y залишилась та ж сама
    }
}
