// Other nodes - roll button, rolled num text
//
package ProjectLudo;

import static javafx.application.Application.STYLESHEET_CASPIAN;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class OtherNodes {
    
    Group nodes;
    Text rolledNum, turnColor;
    Button rollB;
    
    OtherNodes(Board lb) {
        
        double length = lb.length;
        double X = lb.xcor;
        double Y = lb.ycor;
        
        // For showing rolled Num
        rolledNum = new Text(X + length*1.16, Y + length*0.6, "0");
        rolledNum.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.EXTRA_BOLD, length*0.3));
        rolledNum.setFill(Color.GOLDENROD);

        turnColor = new Text(X + length*1.16, Y + length*0.85, "GREEN");
        turnColor.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.EXTRA_BOLD, 30));
        turnColor.setFill(lb.colors[0]);
        
        // Button to roll
        rollB = new Button("Roll Here");
        rollB.setTranslateX(X + length*1.16);
        rollB.setTranslateY(Y + length*0.7);
        
        nodes = new Group(rolledNum, rollB, turnColor);
    }
    
    
}