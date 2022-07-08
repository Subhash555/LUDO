// Ludo Board's graphic component nodes are created and added to member - board
//
package ProjectLudo;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Board {

    final Group board;
    final Color[] colors = {Color.FORESTGREEN, Color.ORANGERED, Color.DODGERBLUE, Color.GOLD};
    final double length, xcor, ycor;
    final double unit, units[];

    // Board length, top corner co-ordinates
    Board(final double LEN, final double X, final double Y) {
        
        length = LEN; xcor = X; ycor = Y;
        unit = LEN / 15;
        double[] u = new double[16];
        for (int i = 0; i < 16; i++) {
            u[i] = unit * i;
        }
        units = u;

        Group grid = new Group();
        
        for (int i = 0; i < 16; i++) {
            double x = X + i * u[1], y1 = Y, y2 = Y + LEN;
            Line line = new Line(x, y1, x, y2);
            line.setStrokeWidth(LEN / 1000);
            grid.getChildren().add(line);
        }
        
        for (int i = 0; i < 16; i++) {
            double y = X + i * u[1], x1 = X, x2 = X + LEN;
            Line line = new Line(x1, y, x2, y);
            line.setStrokeWidth(LEN / 1000);
            grid.getChildren().add(line);
        }

        Group fourboxes = new Group();
        double trns = u[9];
        double[][] pos = {{X, Y}, {X + trns, Y}, {X + trns, Y + trns}, {X, Y + trns}};
        double dx = unit * 2 / 3;
        double[][] pos2 = {{-dx, -dx}, {+dx, -dx}, {+dx, +dx}, {-dx, +dx,}};

        for (int i = 0; i < 4; i++) {

            double w1 = u[6], x = pos[i][0], y = pos[i][1];
            Rectangle box = new Rectangle(x, y, w1, w1);
            box.setFill(colors[i]);
            
            double w2 = w1 * 2 / 3;
            double d = (w1 - w2) / 2;
            Rectangle box2 = new Rectangle(x + d, y + d, w2, w2);
            box2.setFill(Color.WHITE);
            box2.setArcWidth(w2 / 2); box2.setArcHeight(w2 / 2);
            
            fourboxes.getChildren().addAll(box, box2);

            for (int j = 0; j < 4; j++) {
                
                double centx = x + w1 / 2, centy = y + w1 / 2;
                double cirx = centx + pos2[j][0], ciry = centy + pos2[j][1], r = unit / 2;
                Circle cir = new Circle(cirx, ciry, r, Color.TRANSPARENT);
                cir.setStroke(colors[i]);
                cir.setStrokeWidth(LEN / 100);
                fourboxes.getChildren().add(cir);
            }
        }

        Group winbox = new Group();
        {
            double x = X + u[6], y = Y + u[6], w = u[3];
            double[][] pos3 = {{x, y + w}, {x, y}, {x + w, y}, {x + w, y + w}};
            double cx = LEN / 2 + X, cy = LEN / 2 + Y;
            
            for (int i = 0; i < 4; i++) {
                double x1 = pos3[i][0], y1 = pos3[i][1];
                double x2 = pos3[(i + 1) % 4][0], y2 = pos3[(i + 1) % 4][1];
                Polygon tri = new Polygon(cx, cy, x1, y1, x2, y2);
                tri.setFill(colors[i]);
                winbox.getChildren().add(tri);
            }
        }

        Group homecolumn = new Group();
        double[][] pos4 = {{X + u[3], Y + u[5]}, {X + u[7], Y + u[1]},
        {X + u[11], Y + u[5]}, {X + u[7], Y + u[9]}};

        for (int i = 0; i < 4; i++) {
            double x = pos4[i][0], y = pos4[i][1], l = unit, w = u[5];
            Rectangle bar = new Rectangle(x, y, l, w);
            bar.setRotate(90 * (i + 1));
            bar.setFill(colors[i]);
            homecolumn.getChildren().add(bar);
        }

        Group begbox = new Group();
        double[][] pos5 = {{X + u[1], Y + u[6]}, {X + u[8], Y + u[1]},
        {X + u[13], Y + u[8]}, {X + u[6], Y + u[13]}};

        for (int i = 0; i < 4; i++) {
            double x = pos5[i][0], y = pos5[i][1], w = unit;
            Rectangle box = new Rectangle(x, y, w, w);
            box.setFill(colors[i]);
            begbox.getChildren().add(box);
        }

        Group safespots = new Group();
        double[][] pos6 = {{X + u[2], Y + u[8]}, {X + u[6], Y + u[2]},
        {X + u[12], Y + u[6]}, {X + u[8], Y + u[12]}};

        for (int i = 0; i < 4; i++) {
            double x = pos6[i][0], y = pos6[i][1],
                    w = unit * 3 / 4, d = (unit - w) / 2;
            Rectangle box = new Rectangle(x + d, y + d, w, w);
            box.setFill(Color.TRANSPARENT);
            box.setStroke(colors[i]);
            box.setStrokeType(StrokeType.INSIDE);
            box.setStrokeWidth(LEN / 100);
            safespots.getChildren().add(box);
        }

        Group arrows = new Group();
        double[][] pos7 = {{X, Y + u[7]}, {X + u[7], Y},
        {X + u[14], Y + u[7]}, {X + u[7], Y + u[14]}};

        for (int i = 0; i < 4; i++) {
            double w = unit / 2, cx = pos7[i][0] + unit / 2, cy = pos7[i][1] + unit / 2;
            MoveTo beg = new MoveTo(cx - w / 2, cy);
            LineTo line1 = new LineTo(cx + w / 2, cy);
            MoveTo arm1 = new MoveTo(cx + w / 6, cy - w / 3);
            MoveTo arm2 = new MoveTo(cx + w / 6, cy + w / 3);
            Path arrow = new Path(beg, line1, arm1, line1, arm2, line1);
            arrow.setStroke(colors[i]);
            arrow.setStrokeWidth(LEN / 166);
            arrow.setRotate(i * 90);
            arrows.getChildren().add(arrow);
        }

        board = new Group(grid, fourboxes, winbox, homecolumn, begbox, safespots, arrows);
    }
}