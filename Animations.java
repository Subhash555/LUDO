// Contains methods related to specific token movement animations
//
package ProjectLudo;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Animations {

    TranslateTransition trans, halt;
    int num;
    boolean finished;
    token tkn;
    TokenMotion logic;

    Animations(TokenMotion tm) {
        logic = tm;
        trans = new TranslateTransition();
        halt = new TranslateTransition(Duration.millis(200));
        halt.setOnFinished((ActionEvent event) -> {
            trans.play();
        });
    }

    void translate(token t, int n) {
        num = n;
        tkn = t;
        trans.setDuration(Duration.millis(10));
        if (num > 0) {
            num = setUpTransition();
            trans.play();
            trans.setOnFinished((ActionEvent event) -> {
                if (num > 0) {
                    num = setUpTransition();
                    halt.setNode(tkn.node);
                    halt.play();
                } else {
                    changeCoord();
                    logic.postMovement(tkn);
                }
            });
        }
    }
    
    private int setUpTransition() {
        double x = tkn.path[tkn.pos + 1][0] - tkn.path[tkn.pos][0];
        double y = tkn.path[tkn.pos + 1][1] - tkn.path[tkn.pos][1];
        tkn.pos++;
        trans.setByX(x);
        trans.setByY(y);
        trans.setNode(tkn.node);
        return --num;
    }

    void killTranslation(token t) {
        tkn = t;
        trans.setDuration(Duration.millis(20));
        finished = setUpKill();
        trans.play();
        trans.setOnFinished((ActionEvent event) -> {
            if (!finished) {
                finished = setUpKill();
                trans.play();
            } else {
                changeCoord();
                logic.postKill();
            }
        });

    }

    private boolean setUpKill() {
        double x = tkn.path[tkn.pos - 1][0] - tkn.path[tkn.pos][0];
        double y = tkn.path[tkn.pos - 1][1] - tkn.path[tkn.pos][1];
        tkn.pos--;
        trans.setByX(x);
        trans.setByY(y);
        trans.setNode(tkn.node);
        return (tkn.pos == 0);
    }
    
    private void changeCoord() {
        Circle cir = tkn.node;
        cir.setTranslateX(0);
        cir.setTranslateY(0);
        tkn.node.setCenterX(tkn.path[tkn.pos][0]);
        tkn.node.setCenterY(tkn.path[tkn.pos][1]);
    }
    
}
