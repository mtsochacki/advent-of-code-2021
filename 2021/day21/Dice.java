public class Dice {
    int number = -1;
    int rolls = 0;

    int roll() {
        number += 1;
        rolls++;
        return number % 100 + 1;
    }
}
