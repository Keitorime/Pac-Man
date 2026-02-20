        package sk.tuke.gamestudio.game.console;

        import org.springframework.beans.factory.annotation.Autowired;
        import java.util.Scanner;
        import java.util.Random;
        import java.util.List;

        import java.util.Date;
        import sk.tuke.gamestudio.game.core.*;
        import sk.tuke.gamestudio.service.*;
        import sk.tuke.gamestudio.entity.*;
        public class Console {
            private Scanner scanner;

            private ScoreServiceJDBC scoreServiceJDBC;

            @Autowired
            private ScoreServiceJPA scoreServiceJPA;


            private RatingServiceJDBS ratingServiceJDBS;

            @Autowired
            private RatingServiceJPA ratingServiceJPA;


            private CommentServiceJDBS commentServiceJDBS;

            @Autowired
            private CommentServiceJPA commentServiceJPA;

            private int coinsCollected;
            public Console() {
                this.scanner = new Scanner(System.in);
                this.commentServiceJDBS = new CommentServiceJDBS();
                this.scoreServiceJDBC = new ScoreServiceJDBC();
                this.ratingServiceJDBS = new RatingServiceJDBS();
                coinsCollected = 0;
            }



            public void showMainMenu() {
                String purple = "\033[95m";
                String green = "\033[92m";
                String yellow = "\033[93m";
                String red = "\033[91m";
                String reset = "\033[0m";

                System.out.println("\n" + purple + "ＷＥＬＣＯＭＥ" + reset + " " +
                        green + "ＴＯ ＴＵＲＮ-ＢＡＳＥＤ" + reset + " " +
                        yellow + "ＰＡＣ-ＭＡＮ" + reset + " " +
                        green + "ＧＡＭＥ" + reset);
                System.out.println(green + "\n----------------------" + reset);
                printInstructions();
                System.out.println(green + "----------------------" + reset);
                System.out.println(green + "\n1. START GAME" + reset);
                System.out.println(green + "2. SERVICE" +reset);
                System.out.println(green + "3. EXIT GAME" + reset);
                System.out.print(green + "\nSelect an option: " + reset);
                int option = getUserOption();


                switch (option) {
                    case 1:
                        printMessage(purple + "Starting the game..." + reset);
                        level();
                        break;
                    case 2:
                        service();
                        break;
                    case 3:
                        printMessage(red + "Exiting the game..." + reset);
                        System.exit(0);
                        break;
                    default:
                        if (option == -1) {
                            printMessage(purple + "Invalid input. Please enter a number." + reset);
                        } else {
                            printMessage(purple + "Invalid option. Exiting the game..." + reset);
                        }
                }
            }
            public void level(){
                String purple = "\033[95m";
                String green = "\033[92m";
                String yellow = "\033[93m";
                String red = "\033[91m";
                String reset = "\033[0m";
                String blue = "\033[94m";
                System.out.println(green + "CHOOSE THE DIFFICULTY OG THE GAME" + reset);
                System.out.println(yellow + "1. EASY" + reset);
                System.out.println(blue + "2. MIDDLE" + reset);
                System.out.println(purple + "3. HARD" + reset);
                System.out.println(red + "\n4. BACK" + reset);
                System.out.print(green + "Select an option: " + reset);

                int option = getUserOption();

                switch (option) {
                    case 1:
                        printMessage(green +"Starting easy game..." + reset);
                        startGame(4, 4, "easy");
                        break;
                    case 2:
                        printMessage(green +"Starting middle game..." + reset);
                        startGame(4, 7, "middle");
                        break;
                    case 3:
                        printMessage(green +"Starting hard game..." + reset);
                        startGame(6, 10, "hard");
                        break;
                    case 4:
                        showMainMenu();

                }
            }

            public void startGame(int height, int width, String difficulty) {

                gamefield gameField = new gamefield(height, width);
                Movements movements = new Movements(width, height);
                Condition condition = Condition.PLAYING;
                int coinsCollected = 0;
                RiddleManager riddleManager = new RiddleManager(difficulty);
                int totalCoins = gameField.getTotalCoins();
                String purple = "\033[95m";
                String green = "\033[92m";
                String red = "\033[91m";
                String reset = "\033[0m";
                while (condition == Condition.PLAYING) {
                    printGameField(height, width, movements, gameField, coinsCollected);
                    if (isNearMonster(movements.getX(), movements.getY(), gameField)) {
                        handleNearMonster();
                        int choice = getUserOption();
                        switch (choice) {
                            case 1:
                                solveRiddle(riddleManager, gameField, movements, coinsCollected, height, width);
                                break;
                            case 2:
                                startBattle();
                                gameField.removeMonster(movements.getX(), movements.getY());
                                printGameField(height, width, movements, gameField, coinsCollected);
                                break;
                            default:
                                printMessage(red  + "Invalid option. Try again" + reset);
                        }
                    }
                    char input = getUserInput();
                    switch (input) {
                        case 'W', 'w':
                            movements.moveUp();
                            break;
                        case 'S', 's':
                            movements.moveDown();
                            break;
                        case 'A', 'a':
                            movements.moveLeft();
                            break;
                        case 'D', 'd':
                            movements.moveRight();
                            break;
                        case 'X', 'x':
                            condition = Condition.GAME_OVER;
                            break;
                    }

                    if (gameField.isCoin(movements.getX(), movements.getY())) {
                        condition = handleCoinCollection(gameField, movements, condition);
                        coinsCollected++;
                    }

                    if (coinsCollected == totalCoins) {
                        condition = Condition.WIN;
                        System.out.println(purple + "You collected all coins: "+ coinsCollected + reset);
                        System.out.println(green + "W   W III N   N" + reset);
                        System.out.println(green + "W   W  I  NN  N" + reset);
                        System.out.println(green + "W W W  I  N N N" + reset);
                        System.out.println(green + "WW WW  I  N  NN" + reset);
                        System.out.println(green + "W   W III N   N" + reset);
                        System.exit(0);

                    }
                }
                handleGameResult(condition);
            }

        //
        //    private void exitGame(){
        //        System.out.println("Exit");
        //        System.exit(0);
        //    }



            public void service(){
                String green = "\033[92m";
                String red = "\033[91m";
                String reset = "\033[0m";
                Scanner scanner = new Scanner(System.in);
                Condition condition = Condition.PLAYING;
                while (condition == Condition.PLAYING){
                    System.out.println(green + "\n1. PRINT SCORES" + reset);
                    System.out.println(green + "2. PRINT AVG RATING" + reset);
                    System.out.println(green + "3. PRINT COMMENTS" + reset);
                    System.out.println(green + "4. ADD SCORE" + reset);
                    System.out.println(green + "5. ADD RATING" + reset);
                    System.out.println(green + "6. ADD COMMENT" + reset);
                    System.out.println(red + "7. EXIT" + reset);
                    System.out.print(green + "\nSelect an option: " + reset);
                    int option;
                    try {
                        option = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Error");
                        continue;
                    }

                    switch (option) {

                        case 1:
                            printScore();
                            service();

                        case 2:
                            avgRating();
                            service();
                        case 3:
                            printComment();
                            service();
                        case 4:
                            addScore();
                            service();
                        case 5:
                            addRating();
                            service();
                        case 6:
                            addComment();
                            service();
                        case 7:
                            showMainMenu();
                    }
                }
            }



        private void printInstructions() {
                String purple = "\033[95m";
                String green = "\033[92m";
                String yellow = "\033[93m";
                String red = "\033[91m";
                String reset = "\033[0m";

                System.out.println(purple + "👾" + reset + " - " + purple + "this is you" + reset);
                System.out.println(green + "Your task is to collect all coins" + reset);
                System.out.println(green + "But be careful they can be everywhere!~~~" + reset);
                System.out.println(yellow + "0" + reset + " - " + yellow + "is your coin" + reset);
                System.out.println(red + "M" + reset + " - " + red + "this is your enemy, what you must defeat" + reset);
            }

            private void printGameField(int height, int width, Movements movements, gamefield gameField, int coinsCollected) {

                String white = "\033[97m";
                String yellow = "\033[93m";
                String red = "\033[91m";
                String reset = "\033[0m";
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        if (movements.getX() == j && movements.getY() == i) {
                            System.out.print("\uD83D\uDC7E");
                        } else if (gameField.isMonster(j, i)) {
                            System.out.print(red + " M " + reset);
                        } else if (gameField.isCoin(j, i)) {
                            System.out.print(yellow + " 0 " + reset);
                        } else {
                            System.out.print(white + " _ "+ reset);
                        }
                    }
                    System.out.println();
                }
                System.out.println( white + "Coins collected: " + coinsCollected + reset);
            }

            private void handleNearMonster() {
                String purple = "\033[95m";
                String green = "\033[92m";
                String yellow = "\033[93m";
                String red = "\033[91m";
                String reset = "\033[0m";
                printMessage( green + "You are near the monster." + reset);
                printMessage(green + "What do you want to do?" + reset);
                printMessage(purple + "1. Solve a riddle" + reset);
                printMessage(red + "2. Fight" + reset);

            }

            private char getUserInput() {
                String green = "\033[92m";
                String reset = "\033[0m";
                System.out.print(green + "\nPress: <W> UP, <S> DOWN, <A> LEFT, <D> RIGHT, <X> END_GAME: " + reset);
                return scanner.next().charAt(0);
            }

            private Condition handleCoinCollection(gamefield gameField, Movements movements, Condition condition) {
                String green = "\033[92m";
                String reset = "\033[0m";
                if (gameField.isCoin(movements.getX(), movements.getY())) {
                    gameField.removeCoin(movements.getX(), movements.getY());
                    coinsCollected++;
                    System.out.println( green + "You collected a coin!" + reset);

                    if (coinsCollected == gameField.getTotalCoins()) {

                        if (gameField.getAllMonsters().isEmpty()) {
                            condition = Condition.WIN;
                        }
                    }
                    gameField.removeCoin(movements.getX(), movements.getY());
                }
                return condition;
            }
            private void handleGameResult(Condition condition) {
                if (condition == Condition.WIN) {
                    System.out.println("You win!");
                } else {
                    System.out.println("GAME OVER");
                }
            }
            public int getUserOption() {
                String red = "\033[91m";
                String reset = "\033[0m";
                int option;
                try {
                    option = scanner.nextInt();
                } catch (java.util.InputMismatchException e) {
                    printMessage(red + "Invalid input. Please enter a number." + reset);
                    scanner.nextLine();
                    option = getUserOption();
                }
                return option;
            }

            public void printMessage(String message) {
                System.out.println(message);
            }

                private void startBattle() {
                    String white = "\033[97m";
                    String purple = "\033[95m";
                    String green = "\033[92m";
                    String red = "\033[91m";
                    String reset = "\033[0m";
                    String blue = "\033[94m";
                    Hero hero = new Hero("YourHeroName");
                    Mob mob = new Mob(0, 0, 2);

                    Condition condition = Condition.PLAYING;
                    System.out.println(green + "Starting a battle..." + reset);

                    Scanner scanner = new Scanner(System.in);
                    Random random = new Random();

                    while (hero.getHP() > 0 && mob.getHP() > 0) {
                        System.out.println(purple + "Press any key to roll the dice:" + reset);
                        scanner.nextLine();

                        int randomNum = random.nextInt(10);
                        System.out.println(white + "Random number: " + randomNum + reset);

        //                Battle.fight(hero,mob);
                        if (randomNum % 2 == 0) {
                            mob.reduce_Mob_HP();
                            System.out.println( red + "Mob HP reduced. Current mob HP: " + mob.getHP() + reset);
                            System.out.println( green + "Current hero HP: " + hero.getHP() + reset);
                        } else {
                            hero.reduce_Hero_HP();
                            System.out.println( red + "Hero HP reduced. Current hero HP: " + hero.getHP() + reset);
                            System.out.println(green + "Current mob HP: " + mob.getHP() + reset);
                        }

                    }

                    if (hero.getHP() <= 0) {
                        System.out.println( red + "Hero has been defeated! Mob wins!" + reset);
                        condition = Condition.GAME_OVER;
                        System.exit(0);

                    } else {
                        System.out.println(blue + "Mob has been defeated! Hero wins!" + reset);
                        condition = Condition.PLAYING;

                    }
                }

            private boolean isNearMonster(int heroX, int heroY, gamefield field) {
                for (Mob monster : field.getAllMonsters()) {
                    int monsterX = monster.getX();
                    int monsterY = monster.getY();
                    int distanceX = Math.abs(heroX - monsterX);
                    int distanceY = Math.abs(heroY - monsterY);
                    if (distanceX < 1 && distanceY < 1) {
                        return true;
                    }
                }
                return false;
            }

            public boolean answerRiddle(Riddle riddle, String userAnswer) {
                String reset = "\033[0m";
                String purple = "\033[95m";
                if (riddle.checkAnswer(userAnswer)) {
                    System.out.println(purple + "Correct answer! You solved the riddle!" + reset);
                    return true;
                } else {
                    return false;
                }
            }
            private void solveRiddle(RiddleManager riddleManager, gamefield gameField, Movements movements, int coinsCollected, int height, int width) {
                String green = "\033[92m";
                String red = "\033[91m";
                String reset = "\033[0m";
                Riddle riddle = riddleManager.getRandomRiddle();
                System.out.println(green + "Here is the riddle:" + reset);
                System.out.println(riddle.getQuestion());
                System.out.print(green + "Enter your answer: " + reset);
                scanner.nextLine();
                String userAnswer = scanner.nextLine().toLowerCase();

                if (!answerRiddle(riddle, userAnswer)) {

                    System.out.println( red + "Sorry, wrong answer. You lost." + reset);
                    System.exit(0);
                }

                gameField.removeMonster(movements.getX(), movements.getY());
                printGameField(height, width, movements, gameField, coinsCollected);
            }
            public void addComment() {
                String green = "\033[92m";
                String reset = "\033[0m";
                Scanner scanner = new Scanner(System.in);
                System.out.print(green + "Your name: " + reset);
                String playerName = scanner.nextLine();
                System.out.print(green + "Game: " + reset);
                String game = scanner.nextLine();
                System.out.print(green + "Enter your comment: " + reset);
                String commentText = scanner.nextLine();
                Date commentedOn = new Date();
                Comment comment = new Comment(game, playerName, commentText, commentedOn);
//                commentServiceJDBS.addComment(comment);
                commentServiceJPA.addComment(comment);
                System.out.println(green + "Comment added successfully!" + reset);
            }


            private void addRating() {
                String green = "\033[92m";
                String reset = "\033[0m";
                Scanner scanner = new Scanner(System.in);
                System.out.print(green + "Enter your name: " + reset);
                String playerName = scanner.nextLine();
                System.out.print(green + "Enter the game name: " + reset);
                String gameName = scanner.nextLine();
                System.out.print(green + "Enter your rating: " + reset);
                int ratingValue = scanner.nextInt();
                Rating rating = new Rating(gameName, playerName, ratingValue);
//                ratingServiceJDBS.addRating(rating);
                ratingServiceJPA.setRating(rating);
                System.out.println(green + "Rating added successfully!" + reset);
            }

            public void printComment() {
                String green = "\033[92m";
                String reset = "\033[0m";
                Scanner scanner = new Scanner(System.in);
                System.out.print(green + "Game name: " + reset);
                String gameName = scanner.nextLine();
//                List<Comment> comments = commentServiceJDBS.getComments(playerName);
                List<Comment> comments = commentServiceJPA.getComments(gameName);
                System.out.println(green + "Comments for game " + gameName + ":" + reset);
                for (Comment comment : comments) {
                    System.out.println(comment.getCommentText() + " - " + comment.getCommentedOn());
                }
            }
            private void avgRating() {
                String green = "\033[92m";
                String reset = "\033[0m";
                System.out.println(green + "Printing avg rating" + reset);
                Scanner scanner = new Scanner(System.in);
                System.out.print(green + "Enter game name: " + reset);
                String playerName = scanner.nextLine();
//                RatingServiceJDBS ratingServiceJDBS = new RatingServiceJDBS();
                int avgRating = ratingServiceJPA.getAverageRating(playerName);
                System.out.println(green + "Average rating for game " + playerName + ": " + avgRating + reset);
            }
            public void addScore() {
                String green = "\033[92m";
                String reset = "\033[0m";
                Scanner scanner = new Scanner(System.in);
                System.out.print(green + "Your name: " + reset);
                String player = scanner.nextLine();
                System.out.print(green + "Game: ");
                String game = scanner.nextLine();
                System.out.print(green + "Enter your score: " + reset);
                int points = scanner.nextInt();
                Date playedOn = new Date();
                Score score = new Score(game, player, points, playedOn);
//                scoreServiceJDBC.addScore(score);
                scoreServiceJPA.addScore(score);
                System.out.println(green + "Score added successfully!" + reset);
            }
            private void printScore() {
                String green = "\033[92m";
                String reset = "\033[0m";
                System.out.println(green + "Printing scores" + reset);
                Scanner scanner = new Scanner(System.in);
                System.out.print(green + "Enter game name: " + reset);
                String gameName = scanner.nextLine();
                List<Score> topScores = scoreServiceJPA.getTopScores(gameName);

                System.out.println(green + "Top scores for game " + gameName + ":" + reset);
                for (Score score : topScores) {
                    if (score != null) {
                        System.out.println(score.getPlayer() + " - " + score.getPoints());
                    } else {
                        System.out.println("Null score object encountered.");
                    }
                }
            }

        }