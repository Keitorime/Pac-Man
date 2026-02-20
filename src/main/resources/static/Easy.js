document.addEventListener("DOMContentLoaded", function() {
    var gameField = document.getElementById("gameField");
    addCoinsToPage(gameField.clientWidth, gameField.clientHeight);
    // addHeroToPage();

    // Додавання обробника подій для натискання клавіш
    document.addEventListener("keydown", function(event) {
        moveHero(event.key);
    });
    document.addEventListener("DOMContentLoaded", function() {
        var riddleManager = new RiddleManager("easy");
        var randomRiddle = riddleManager.getRandomRiddle();
        showRiddle(randomRiddle, mob);
    });
    document.addEventListener("DOMContentLoaded", function() {
        showFight(mob);
    });
});
function generateCoins() {
    axios.post('/generateCoins')
        .then(response => {
            console.log(response.data); // Вивід успішного повідомлення в консоль
            addCoinsToPage(800, 600); // Додавання монеток на сторінку
            checkWinCondition(); // Перевірка умови перемоги після генерації монеток
        })
        .catch(error => {
            console.error('Error generating coins:', error); // Вивід помилки в консоль
        });
}

var coinCount = 0; // Змінна для лічильника монеток
var defeatedCount = 0; // Змінна для лічильника збитих мобів

function addCoin() {
    coinCount++; // Збільшуємо лічильник монеток
    document.getElementById("coinCount").textContent = coinCount; // Оновлюємо відображення лічильника монеток
    checkWinCondition(); // Перевірка умови перемоги після додавання монетки
}

function addDefeatedMob() {
    defeatedCount++; // Збільшуємо лічильник збитих мобів
    document.getElementById("defeatedCount").textContent = defeatedCount; // Оновлюємо відображення лічильника збитих мобів
    checkWinCondition(); // Перевірка умови перемоги після додавання монетки
}

function checkWinCondition() {
    var mobCount = document.getElementsByClassName("mob").length; // Кількість мобів на полі
    var numCoins = document.getElementsByClassName("coin").length; // Кількість монеток на полі
    if (defeatedCount === 3 && coinCount === numCoins) {
        showWinMessage();
    }
}
function addCoinsToPage(fieldWidth, fieldHeight) {
    var coinSize = 20; // Розмір монетки
    var minCoins = 5; // Мінімальна кількість монеток
    var maxCoins = 15; // Максимальна кількість монеток
    var numCoins = getRandomNumber(minCoins, maxCoins); // Генеруємо рандомну кількість монеток

    // Очищення поля від старих монеток
    var gameField = document.getElementById("gameField");
    gameField.innerHTML = '';

    for (var i = 0; i < numCoins; i++) {
        var coin = document.createElement("div");
        coin.classList.add("coin");
        coin.setAttribute("data-coin", ""); // Додаємо атрибут для позначення монетки
        coin.style.left = getRandomPosition(coinSize, fieldWidth - coinSize) + "px";
        coin.style.top = getRandomPosition(coinSize, fieldHeight - coinSize) + "px";
        gameField.appendChild(coin);
    }
}

function getRandomNumber(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function getRandomPosition(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function generateMobs() {
    var mobCount = 3; // Кількість мобів, які потрібно згенерувати
    var gameField = document.getElementById("gameField");

    for (var i = 0; i < mobCount; i++) {
        var mob = document.createElement("div");
        mob.classList.add("mob");
        mob.style.left = getRandomPosition(20, gameField.clientWidth - 20) + "px";
        mob.style.top = getRandomPosition(20, gameField.clientHeight - 20) + "px";
        gameField.appendChild(mob);
    }

    checkWinCondition();
}
function moveHero(key) {
    var hero = document.getElementById("hero");
    var step = 10; // Крок руху героя

    // Отримуємо поточне значення трансформації
    var currentTransform = hero.style.transform || "translate(0px, 0px)";

    // Розбиваємо значення трансформації на компоненти (переміщення по осі X та Y)
    var matches = currentTransform.match(/(-?\d+)px,\s*(-?\d+)px/);
    var currentTranslateX = parseInt(matches[1]);
    var currentTranslateY = parseInt(matches[2]);

    // Обчислюємо нові значення трансформації відповідно до натисканої клавіші
    var newTranslateX = currentTranslateX;
    var newTranslateY = currentTranslateY;

    if (key === "ArrowUp") {
        newTranslateY -= step;
    } else if (key === "ArrowDown") {
        newTranslateY += step;
    } else if (key === "ArrowLeft") {
        newTranslateX -= step;
    } else if (key === "ArrowRight") {
        newTranslateX += step;
    }

    // Формуємо нове значення трансформації та застосовуємо його
    var newTransform = "translate(" + newTranslateX + "px, " + newTranslateY + "px)";
    hero.style.transform = newTransform;

    checkCoinCollision(hero);
    checkMobCollision(hero);
}

function checkCoinCollision(hero) {
    var heroPosition = hero.getBoundingClientRect();
    var coins = document.getElementsByClassName("coin");

    for (var i = 0; i < coins.length; i++) {
        var coin = coins[i];
        var coinPosition = coin.getBoundingClientRect();

        if (
            heroPosition.left < coinPosition.right &&
            heroPosition.right > coinPosition.left &&
            heroPosition.top < coinPosition.bottom &&
            heroPosition.bottom > coinPosition.top
        ) {
            coin.style.display = "none"; // Приховуємо монетку
            addCoin(); // Додаємо до лічильника монеток
        }
    }
}
function checkMobCollision(hero) {
    var heroPosition = hero.getBoundingClientRect();
    var mobs = document.getElementsByClassName("mob");

    for (var i = 0; i < mobs.length; i++) {
        var mob = mobs[i];
        var mobPosition = mob.getBoundingClientRect();

        if (
            heroPosition.left < mobPosition.right &&
            heroPosition.right > mobPosition.left &&
            heroPosition.top < mobPosition.bottom &&
            heroPosition.bottom > mobPosition.top
        ) {
            showNotification("Fight with unknown creature", 1000, 20, mob);

            // Передаємо моба як аргумент, щоб можна було його видалити при бої
        }
    }
}

function showNotification(message, x, y, mob) {
    var riddleManager = new RiddleManager("easy");
    var randomRiddle = riddleManager.getRandomRiddle(); // Оновлення загадки

    var notification = document.createElement("div");
    notification.classList.add("notification");
    notification.textContent = message;
    notification.style.position = "absolute";
    notification.style.right = "20px";
    notification.style.top = "20px";
    notification.style.color = "white";
    notification.style.fontSize = "20px";
    notification.style.borderRadius = "20px 20px 20px 20px";
    document.body.appendChild(notification);

    var fightButton = document.createElement("button");
    fightButton.textContent = "Fight";
    fightButton.addEventListener("click", function() {
        document.body.removeChild(notification);
        document.body.removeChild(fightButton);
        document.body.removeChild(SolveRiddleButton);
        if (mob) {
            mob.remove();
        }
        showFight(mob);
    });

    var SolveRiddleButton = document.createElement("button");
    SolveRiddleButton.textContent = "Solve the Riddle";
    SolveRiddleButton.addEventListener("click", function() {
        document.body.removeChild(notification);
        document.body.removeChild(fightButton);
        document.body.removeChild(SolveRiddleButton);

        // Перевіряємо, чи існує mob, і видаляємо його, якщо так
        if (mob) {
            mob.remove();
        }

        var riddleManager = new RiddleManager("easy");
        var randomRiddle = riddleManager.getRandomRiddle();
        showRiddle(randomRiddle, mob);
    });

    fightButton.style.position = "absolute";
    fightButton.style.top = "50px";
    fightButton.style.right = "20px";

    SolveRiddleButton.style.position = "absolute";
    SolveRiddleButton.style.top = "80px";
    SolveRiddleButton.style.right = "20px";

    document.body.appendChild(fightButton);
    document.body.appendChild(SolveRiddleButton);

    setTimeout(function() {
        document.body.removeChild(notification);
        document.body.removeChild(fightButton);
        document.body.removeChild(SolveRiddleButton);
    }, 3000);
}
function showWinMessage() {
    var winMessageContainer = document.createElement("div");
    winMessageContainer.style.width = "400px";
    winMessageContainer.style.height = "300px";
    winMessageContainer.style.position = "absolute";
    winMessageContainer.style.top = "50%";
    winMessageContainer.style.left = "50%";
    winMessageContainer.style.transform = "translate(-50%, -50%)";
    winMessageContainer.style.backgroundColor = "rgba(255, 255, 255)";
    winMessageContainer.style.border = "2px solid #000";
    winMessageContainer.style.textAlign = "center";
    winMessageContainer.style.display = "flex";
    winMessageContainer.style.flexDirection = "column";
    winMessageContainer.style.justifyContent = "center";
    winMessageContainer.style.alignItems = "center";
    winMessageContainer.style.borderRadius = "20px 20px 20px 20px";

    var winMessage = document.createElement("div");
    winMessage.textContent = "YOU WIN!";
    winMessage.style.fontSize = "24px";
    winMessage.style.marginBottom = "20px";

    var playAgainButton = document.createElement("button");
    playAgainButton.textContent = "Play again?";
    playAgainButton.addEventListener("click", function() {
        location.reload(); // Перезавантаження сторінки для рестарту гри
    });

    winMessageContainer.appendChild(winMessage);
    winMessageContainer.appendChild(playAgainButton);

    document.body.appendChild(winMessageContainer);
}

class Riddle {
    constructor(question, answer) {
        this.question = question;
        this.answer = answer;
        this.solved = false;
    }

    checkAnswer(userAnswer) {
        if (userAnswer && userAnswer.trim().toLowerCase() === this.answer.toLowerCase()) {
            this.solved = true;
            return true;
        }
        return false;
    }
}

class RiddleManager {
    constructor(difficulty) {
        this.easyRiddles = [];
        this.middleRiddles = [];
        this.hardRiddles = [];
        this.initializeRiddles(difficulty);
        this.resetColor = "\x1b[0m"; // Повернення до стандартного кольору консолі
        this.blueColor = "\x1b[94m"; // Синій колір для відображення загадок
    }

    initializeRiddles(difficulty) {
        switch (difficulty.toLowerCase()) {
            case "easy":
                    this.initializeEasyRiddles();
                    break;
                case "middle":
                    this.initializeMiddleRiddles();
                    break;
                case "hard":
                    this.initializeHardRiddles();
                    break;
                default:
                    throw new Error("Invalid difficulty level: " + difficulty);
            }
        }

        initializeEasyRiddles() {
            this.easyRiddles.push(new Riddle("What gets bigger the more you take away?", "hole"));
            this.easyRiddles.push(new Riddle("What has four fingers and a thumb, but isn’t alive?", "glove"));
            this.easyRiddles.push(new Riddle("If you have me, you will want to share me. If you share me, you will no longer have me. What am I?", "secret"));
            this.easyRiddles.push(new Riddle("What has a head and a tail, but no body?", "coin"));
            this.easyRiddles.push(new Riddle("What comes once in a minute, twice in a moment, but never in a thousand years?", "m"));
            this.easyRiddles.push(new Riddle("I shave every day, but my beard stays the same. Who am I?", "barber"));
        }

        initializeMiddleRiddles() {
            this.middleRiddles.push(new Riddle("My first is in blood and also in battle. My second is in acorn, oak, and apple. My third and fourth are both the same. In the center of sorrow and twice in refrain. My fifth starts eternity ending here. My last is the first of last, Oh Dear!", "barrel"));
            this.middleRiddles.push(new Riddle("If you were standing directly on Antarctica's South Pole facing north, which direction would you travel if you took one step backward?", "north"));
            // Додайте ще загадок середнього рівня складності за потреби
        }

        initializeHardRiddles() {
            this.hardRiddles.push(new Riddle("You will find me with four legs, but no hair. People ride me for hours, but I don't go anywhere without needing to be tugged. Jerked or turned on, I always manage to be ready for work.", "desk"));
            this.hardRiddles.push(new Riddle("What has roots as nobody sees. Is taller then trees. Up, up it goes. And yet never grows.", "mountain"));
            // Додайте ще важких загадок за потреби
        }

        getRandomRiddle() {
            let riddles;
            const totalRiddles = this.easyRiddles.length + this.middleRiddles.length + this.hardRiddles.length;
            const randomIndex = Math.floor(Math.random() * totalRiddles);

            if (randomIndex < this.easyRiddles.length) {
                riddles = this.easyRiddles;
            } else if (randomIndex < this.easyRiddles.length + this.middleRiddles.length) {
                riddles = this.middleRiddles;
            } else {
                riddles = this.hardRiddles;
            }

            const selectedRiddle = riddles[randomIndex % riddles.length];
            riddles.splice(randomIndex % riddles.length, 1);

            if (riddles.length === 0) {
                this.initializeRiddles(); // Повторно ініціалізуємо масив, якщо всі загадки були вибрані
            }

            return selectedRiddle;
        }
    }

    function showRiddle(riddle, mob) {
        // Створення контейнера для загадки
        var riddleContainer = document.createElement("div");
        riddleContainer.classList.add("riddle-container");

        // Створення елемента для тексту загадки
        var riddleQuestion = document.createElement("p");
        riddleQuestion.textContent = riddle.question;

            // Створення поле введення для відповіді
            var answerInput = document.createElement("input");
            answerInput.type = "text";
            answerInput.placeholder = "Enter your answer...";

            // Створення кнопки для відправки відповіді
            var submitButton = document.createElement("button");
            submitButton.textContent = "Submit";
            submitButton.addEventListener("click", function() {
                var userAnswer = answerInput.value;
                if (riddle.checkAnswer(userAnswer)) {
                    // Відображення повідомлення про правильну відповідь
                    var successMessage = document.createElement("p");
                    // successMessage.textContent = "Correct answer!";
                    document.body.appendChild(successMessage);

                    // Додавання моба до списку повержених
                    addDefeatedMob();

                    // Перевірка умови перемоги
                    checkWinCondition();

                    // Видалення контейнера з загадкою
                    riddleContainer.remove();

                    // Видалення моба з екрану
                    mob.remove();
                } else {
                    // Відображення повідомлення про неправильну відповідь
                    var errorMessage = document.createElement("p");
                    errorMessage.textContent = "Incorrect answer, please try again.";
                    riddleContainer.appendChild(errorMessage);
                }
            });

            // Додавання елементів до контейнера з загадкою
            riddleContainer.appendChild(riddleQuestion);
            riddleContainer.appendChild(answerInput);
            riddleContainer.appendChild(submitButton);

            // Додавання контейнера з загадкою до сторінки
            document.body.appendChild(riddleContainer);
        }

        function showFight(mob) {
            var notification = document.createElement("div");
            notification.classList.add("notification");
            notification.textContent = "Fight with unknown creature";
            notification.style.position = "absolute";
            notification.style.right = "20px";
            notification.style.top = "20px";
            notification.style.color = "white";
            notification.style.fontSize = "20px";
            document.body.appendChild(notification);

            var rollDiceButton = document.createElement("button");
            rollDiceButton.textContent = "Roll Dice";
            rollDiceButton.addEventListener("click", function() {
                document.body.removeChild(notification);
                document.body.removeChild(rollDiceButton);

                var win = rollDice();

                if (win) {
                    // Додати до лічильника переможених мобів
                    addDefeatedMob();
                } else {
                    // Повідомлення про програш
                    var loseMessage = document.createElement("p");
                    loseMessage.textContent = "YOU LOSE\ntry again?";
                    loseMessage.style.position = "absolute";
                    loseMessage.style.top = "50%";
                    loseMessage.style.left = "50%";
                    loseMessage.style.transform = "translate(-50%, -50%)";
                    loseMessage.style.fontSize = "24px";
                    loseMessage.style.color = "red";
                    document.body.appendChild(loseMessage);

                    // Кнопка для повторної спроби
                    var tryAgainButton = document.createElement("button");
                    tryAgainButton.textContent = "Try again?";
                    tryAgainButton.style.position = "absolute";
                    tryAgainButton.style.top = "60%";
                    tryAgainButton.style.left = "50%";
                    tryAgainButton.style.transform = "translate(-50%, -50%)";
                    tryAgainButton.addEventListener("click", function() {
                        location.reload();
                    });
                    document.body.appendChild(tryAgainButton);
                }
            });

            rollDiceButton.style.position = "absolute";
            rollDiceButton.style.top = "50px";
            rollDiceButton.style.right = "20px";

            document.body.appendChild(rollDiceButton);
        }

        // Функція для кидання кубика та повернення результату (true - парне, false - непарне)
        function rollDice() {
            return Math.floor(Math.random() * 6) % 2 === 0;
        }