// Methods return Events to be added on desired tokens in class LudoGUI
// 
package ProjectLudo;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Events {

    token[] movableTokens;
    TokenMotion logic;
    Tokens lt;
    turn turn;
    Dice dice;
    Text roll;

    Events(LudoGUI gui) {
        logic = gui.logic;
        lt = gui.lt;
        turn = gui.turn;
        dice = gui.dice;
        roll = gui.other.rolledNum;
    }

    EventHandler<MouseEvent> displayRoll() {
        EventHandler<MouseEvent> displayRoll = (MouseEvent e) -> {
            // add condition to roll only if not won yet
            dice.roll();
            String Snum = String.valueOf(dice.value);
            roll.setText(Snum);
            movableTokens = logic.movableTokens(dice.value);
            switch (movableTokens.length) {
                case 0:
                    turn.next();
                    logic.showTurn();
                    break;
                case 1:
                    ((Node) e.getSource()).setMouseTransparent(true);
                    logic.moveToken(movableTokens[0], dice.value);
                    //turn.next();
                    break;
                default:
                    ((Node) e.getSource()).setMouseTransparent(true);

                    /*
                    ## This comment block allows faster testing by moving the first token without user input 
                    ## when all movable tokens are in home (for this uncomment this block and comment out below for loop)
                    boolean AllInHome = true;
                    for (token tkn : movableTokens) {
                        if (tkn.pos != 0) {
                            AllInHome = false;
                        }
                        tkn.node.setMouseTransparent(false);
                    }
                    if (AllInHome) {
                        logic.moveToken(movableTokens[0], dice.value);
                    }
                     */
                    for (token tkn : movableTokens) {
                        tkn.node.setMouseTransparent(false);
                    }
                    break;
            }
        };
        return displayRoll;
    }

    EventHandler<MouseEvent> tokenBubbleUp() {
        EventHandler<MouseEvent> tokenEntry = (MouseEvent e) -> {
            Circle cir = (Circle) e.getSource();
            cir.setRadius(cir.getRadius() * 1.1);
        };
        return tokenEntry;
    }

    EventHandler<MouseEvent> tokenBubbleDown() {
        EventHandler<MouseEvent> tokenExit = (MouseEvent e) -> {
            Circle cir = (Circle) e.getSource();
            cir.setRadius(cir.getRadius() / 1.1);
        };
        return tokenExit;
    }

    EventHandler<MouseEvent> tokenMove() {
        EventHandler<MouseEvent> tokenMove = (MouseEvent e) -> {
            Circle cir = (Circle) e.getSource();
            token target = null;
            for (token tkn : movableTokens) {
                if (tkn.node == cir) {
                    target = tkn;
                }
                tkn.node.setMouseTransparent(true);
            }
            logic.moveToken(target, dice.value);
        };
        return tokenMove;
    }

}
