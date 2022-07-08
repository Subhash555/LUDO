// Token movement logic - moving, killing, adjusting, bonus roll on kill/6/win
//
package ProjectLudo;

import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TokenMotion {

    Tokens lt;
    Animations anim;
    turn turn;
    ArrayList<token>[] safeList;
    Button rollB;
    Text turnColor;
    Color[] colors;
    String[] colorText = {"GREEN", "RED", "BLUE", "YELLOW"};
    final double unit;
    int diceNo;

    TokenMotion(LudoGUI gui) {
        unit = gui.lb.unit;
        turn = gui.turn;
        lt = gui.lt;
        rollB = gui.other.rollB;
        turnColor = gui.other.turnColor;
        colors = gui.lb.colors;
        anim = new Animations(this);
        safeList = new ArrayList[8];
        for (int i = 0; i < 8; i++) {
            safeList[i] = new ArrayList();
        }
    }
    
    void moveToken(token tkn, int num) {

        diceNo = num;
        num = (tkn.pos == 0) ? 1 : num;
        preMovement(tkn);
        anim.translate(tkn, num);
    }

    void preMovement(token tkn) {
        int safeNum = tkn.safeSpotNum();
        if (safeNum != -1) {
            ArrayList traffic = safeList[safeNum];
            adjustTokensRemove(traffic, tkn);
        }
    }

    void postMovement(token tkn) {

        int safeNum = tkn.safeSpotNum();
        if (safeNum != -1) {
            ArrayList traffic = safeList[safeNum];
            adjustTokensAdd(traffic, tkn);
            checkSixOrWin(tkn);
        } else {
            boolean someoneKilled = false;
            for (int i = 0; i < 16; i++) {
                token other = lt.tokens[i];
                if (other.uniPos() == tkn.uniPos() && other.index != tkn.index) {
                    anim.killTranslation(other);
                    someoneKilled = true;
                }
            }
            if (!someoneKilled) {
                checkSixOrWin(tkn);
            }
        }
    }

    private void checkSixOrWin(token tkn) {
        
        rollB.setMouseTransparent(false);
        if (diceNo != 6 && tkn.pos != 57) {
            turn.next();
        }
        showTurn();
    }

    void postKill() {
        rollB.setMouseTransparent(false);
        showTurn();
    }
    
    void showTurn() {
        turnColor.setText(colorText[turn.num]);
        turnColor.setFill(colors[turn.num]);
    }

    token[] movableTokens(int num) {

        token ittr;
        token[] tokens = new token[4];
        int[] isvalid = new int[4];
        int valid = 0;
        for (int i = 0; i < 4; i++) {
            ittr = lt.tokens[turn.num * 4 + i];
            tokens[i] = ittr;
            if (isValidMove(ittr, num)) {
                isvalid[i] = 1;
                valid++;
            } else {
                isvalid[i] = 0;
            }
        }
        token[] validTokens = new token[valid];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            if (isvalid[i] == 1) {
                validTokens[index++] = tokens[i];
            }
        }
        return validTokens;
    }

    private boolean isValidMove(token tkn, int num) {
        
        if (tkn.pos == 0) {             // Start condition
            num = (num == 6) ? 1 : 0;   // Original condition
            //num = 1;                  // To speed up testing
        }
        num = (tkn.pos + num <= 57) ? num : 0;      // bound check
        return (num != 0);
    }

    private void adjustTokensAdd(ArrayList<token> tokens, token tkn) {

        int count = tokens.size();
        if (count == 0) {
            tokens.add(tkn);
        } else {
            int idx = 0;
            for (int i = 0; i < count; i++) {
                token other = tokens.get(i);
                if (tkn.index <= other.index) {
                    tokens.add(idx, tkn);
                    break;
                } else if (idx == count - 1) {
                    tokens.add(tkn);
                }
                idx++;
            }
            adjustTokens(tokens);
        }
    }

    private void adjustTokensRemove(ArrayList<token> tokens, token tkn) {

        int count = tokens.size();
        tkn.node.setCenterX(tkn.path[tkn.pos][0]);
        tkn.node.setRadius(unit / 3);
        tkn.node.setStrokeWidth(unit / 12);
        tokens.remove(tkn);
        if (count > 1) {
            adjustTokens(tokens);
        }

    }

    private void adjustTokens(ArrayList<token> tokens) {
        
        int count = tokens.size();
        double len = (count == 1) ? unit * 2 / 3 : unit * 0.9;
        double radius = len / (count + 1);
        double stroke = radius / 4;
        token tk = tokens.get(0);
        double x = tk.path[tk.pos][0] - len / 2 + radius;

        for (int i = 0; i < count; i++) {
            token t = tokens.get(i);
            t.node.setRadius(radius);
            t.node.setStrokeWidth(stroke);
            t.node.setCenterX(x);
            t.node.toFront();
            x += radius;
        }
    }
}
