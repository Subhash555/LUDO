// Generates general paths to be used by class Tokens to create token specific path
// Member array safeSpots is used in Class Tokens to determine if token is on a safe spot
//
package ProjectLudo;

import javafx.scene.paint.Color;

public class Paths {

    final Color[] colors;
    final double[][] commonPath;
    final double[][][] winColumn;
    final int[] beginIndex = {1, 14, 27, 40};
    final int[] entryIndex = {51, 12, 25, 38};
    final int[] safeSpots;

    Paths(Board lb) {

        final double X = lb.xcor;
        final double Y = lb.ycor;
        colors = lb.colors;

        final double unit = lb.unit;
        final double[] u = lb.units;

        commonPath = new double[52][2];
        {
            double x = X + unit * 0.5;
            double y = Y + unit * 6.5;
            int index = 0, sign = 1;
            byte[] shiftx = {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0};
            byte[] shifty = {0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1};

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 26; j++) {
                    commonPath[index][0] = x;
                    commonPath[index][1] = y;
                    x += sign * shiftx[j] * unit;
                    y += sign * shifty[j] * unit;
                    index++;
                }
                sign = -1;
            }
        }

        winColumn = new double[4][6][2];
        {
            double[][] begin = {{X + unit, Y + u[7]}, {X + u[7], Y + unit},
            {X + u[13], Y + u[7]}, {X + u[7], Y + u[13]}};

            int horiz = 1, sign;
            for (int i = 0; i < 4; i++) {
                sign = (i >= 2) ? -1 : 1;
                double x = begin[i][0] + unit / 2;
                double y = begin[i][1] + unit / 2;
                for (int j = 0; j < 6; j++) {
                    winColumn[i][j][0] = x;
                    winColumn[i][j][1] = y;
                    x += horiz * sign * unit;
                    y += (1 - horiz) * sign * unit;
                }
                horiz = (horiz == 1) ? 0 : 1;     //toggle
            }
        }
        safeSpots = new int[52];
        {
            int[] safeIndex = {1, 9, 14, 22, 27, 35, 40, 48};
            // on assumption default value is false
            for (int idx : safeIndex) {
                safeSpots[idx] = idx;
            }
            int num = 0;
            for (int i = 0; i < safeSpots.length; i++) {
                if (safeSpots[i] == 0) {
                    safeSpots[i] = -1;
                } else if (safeSpots[i] == i) {
                    safeSpots[i] = num++;
                }
            }
        }
    }
}
