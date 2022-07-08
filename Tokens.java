// class Tokens - calculates tokens home position and initializes tokens
// class token - blueprint for tokens 
package ProjectLudo;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class Tokens {

    final token[] tokens;
    final Group tokenNodes;

    Tokens(Board lb) {

        Paths paths = new Paths(lb);
        final double X = lb.xcor;
        final double Y = lb.ycor;
        final double[] u = lb.units;
        final double unit = u[1];

        tokens = new token[16];
        tokenNodes = new Group();
        {
            double trns = u[9];
            double[][] pos = {{X, Y}, {X + trns, Y}, {X + trns, Y + trns}, {X, Y + trns}};
            double dx = unit * 2 / 3;
            double[][] pos2 = {{-dx, -dx}, {+dx, -dx}, {+dx, +dx}, {-dx, +dx,}};

            for (int i = 0; i < 4; i++) {

                double w1 = u[6], x = pos[i][0], y = pos[i][1];
                for (int j = 0; j < 4; j++) {

                    double centx = x + w1 / 2, centy = y + w1 / 2;
                    double cirx = centx + pos2[j][0], ciry = centy + pos2[j][1], r = unit / 3;
                    token tkn = new token(paths, i, cirx, ciry, r);
                    tokens[i * 4 + j] = tkn;
                    tokenNodes.getChildren().add(tkn.node);
                }
            }
        }
    }
}

class token {

    Color color;
    int pos, index;
    Circle node;
    double[][] path;
    static Paths paths;

    token(Paths paths, int indx, double x, double y, double r) {

        token.paths = paths;
        color = paths.colors[indx];
        pos = 0;
        index = indx;

        node = new Circle(x, y, r);
        {
            node.setFill(color);
            node.setStrokeType(StrokeType.INSIDE);
            node.setStroke(Color.BLACK);
            node.setStrokeWidth(r / 4);
        }

        path = new double[58][2];
        {
            path[0][0] = x;
            path[0][1] = y;
            int boxnum = paths.beginIndex[index] - 1;
            for (int i = 0; i < 51; i++) {
                boxnum = (boxnum + 1) % 52;
                path[i + 1][0] = paths.commonPath[boxnum][0];
                path[i + 1][1] = paths.commonPath[boxnum][1];
            }
            for (int i = 0; i < 6; i++) {
                path[i + 52][0] = paths.winColumn[index][i][0];
                path[i + 52][1] = paths.winColumn[index][i][1];
            }
        }
    }

    int uniPos() {
        if (pos == 0)
            return -1;
        int unipos = (pos + paths.beginIndex[index] - 1) % 52;
        return unipos;
    }
    

    int safeSpotNum() {
        if (this.uniPos() == -1)
            return -1;
        return paths.safeSpots[uniPos()];
            
    }
}