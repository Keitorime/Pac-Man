package sk.tuke.gamestudio.game.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RiddleManager {

    private final List<Riddle> easyRiddles;
    private final List<Riddle> middleRiddles;
    private final List<Riddle> hardRiddles;
    private final Random random;
    String reset = "\033[0m";
    String blue = "\033[94m";
    public RiddleManager(String difficulty) {
        easyRiddles = new ArrayList<>();
        middleRiddles = new ArrayList<>();
        hardRiddles = new ArrayList<>();
        random = new Random();
        initializeRiddles(difficulty);
    }

    // Method to initialize riddles based on difficulty
    private void initializeRiddles(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "easy":
                initializeEasyRiddles();
                break;
            case "middle":
                initializeMiddleRiddles();
                break;
            case "hard":
                initializeHardRiddles();
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty level: " + difficulty);
        }
    }

    // Method to initialize easy riddles
    private void initializeEasyRiddles() {
        easyRiddles.add(new Riddle(blue +"What gets bigger the more you take away?" + reset, "hole"));
        easyRiddles.add(new Riddle(blue +"What has four fingers and a thumb, but isn’t alive?" + reset, "glove"));
        easyRiddles.add(new Riddle(blue +"If you have me, you will want to share me. If you share me, you will no longer have me. What am I?" + reset, "secret"));
        easyRiddles.add(new Riddle(blue +"What has a head and a tail, but no body?" + reset, "coin"));
        easyRiddles.add(new Riddle(blue +"What comes once in a minute, twice in a moment, but never in a thousand years?" + reset, "m"));
        easyRiddles.add(new Riddle(blue +"I shave every day, but my beard stays the same. Who am I?" + reset, "barber"));
        // Add more easy riddles if needed
    }

    // Method to initialize middle riddles
    private void initializeMiddleRiddles() {
        middleRiddles.add(new Riddle(blue +"My first is in blood and also in battle. My second is in acorn, oak, and apple. My third and fourth are both the same. In the center of sorrow and twice in refrain. My fifth starts eternity ending here. My last is the first of last, Oh Dear!" + reset, "barrel"));
        middleRiddles.add(new Riddle(blue +"If you were standing directly on Antarctica's South Pole facing north, which direction would you travel if you took one step backward?" + reset, "north"));
        middleRiddles.add(new Riddle(blue +"Feed me and I live, yet give me a drink and I die. What am I?" + reset, "fire"));
        middleRiddles.add(new Riddle(blue +"What is easy to get into, and hard to get out of?" + reset, "trouble"));
        middleRiddles.add(new Riddle(blue +"Alive without breathe,\n" + blue +
                "As cold as death,\n" +
                "Never thirsty, ever drinking,\n" +
                "All in mail never clinking.’\n", "fish" + reset));
        middleRiddles.add(new Riddle(blue + "I stink up your breath and fights evil vampires. What am I?"  + reset, "garlic"));
        // Add more middle riddles if needed
    }

    // Method to initialize hard riddles
    private void initializeHardRiddles() {
        hardRiddles.add(new Riddle(blue +"You will find me with four legs, but no hair. People ride me for hours, but I don't go anywhere without needing to be tugged. Jerked or turned on, I always manage to be ready for work." + reset, "desk"));
        hardRiddles.add(new Riddle(blue +"What has roots as nobody sees. Is taller then trees. Up, up it goes. And yet never grows." + reset, "mountain"));
        hardRiddles.add(new Riddle(blue +"Thirty white horses on a red hill,\n" +
                "First they champ,\n" +
                "Then they stamp,\n" +
                "Then they stand still.’\n" + reset, "teeth"));
        hardRiddles.add(new Riddle(blue +"‘A box without hinges, key or lid,\n" +
                "Yet golden treasure inside is hid.’\n " + reset, "eggs"));
        hardRiddles.add(new Riddle(blue +"This thing all things devours:\n" +
                "Birds, beasts, trees, flowers;\n" +
                "Gnaws iron, bites steel;\n" +
                "Grinds hard stones to meal;\n" +
                "Slays kings, ruins towns,\n" +
                "And beats high mountain down.\n" + reset, "time"));
        hardRiddles.add(new Riddle(blue +"‘It cannot be seen, cannot be felt,\n" +
                "Cannot be heard, cannot be smelt.\n" +
                "It lies behind stars and under hills,\n" +
                "And empty holes it fills.\n" +
                "It comes first and follows after,\n" +
                "Ends life, kills laughter.’\n" + reset, "dark"));
        // Add more hard riddles if needed
    }

    // Method to get a random riddle based on difficulty
    public Riddle getRandomRiddle() {
        List<Riddle> riddles;
        int totalRiddles = easyRiddles.size() + middleRiddles.size() + hardRiddles.size();
        int randomIndex = random.nextInt(totalRiddles);
        if (randomIndex < easyRiddles.size()) {
            riddles = easyRiddles;
        } else if (randomIndex < easyRiddles.size() + middleRiddles.size()) {
            riddles = middleRiddles;
            randomIndex -= easyRiddles.size();
        } else {
            riddles = hardRiddles;
            randomIndex -= easyRiddles.size() + middleRiddles.size();
        }
        return riddles.remove(randomIndex);
    }

}