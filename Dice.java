// Dice and turn
// turn.num represents a color turn with value - 0 to 3 for G R B Y
package ProjectLudo;

import java.util.Random;

public class Dice {

    int value;

    void roll() {
        Random ran = new Random();
        value = ran.nextInt(6) + 1;
    }
}

class turn {

    int num;

    turn(int n) {
        num = n;
    }

    void next() {
        num = (num + 1) % 4;
    }
}
