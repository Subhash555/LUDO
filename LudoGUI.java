// Ludo GUI - the driver class
//
package ProjectLudo;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;

public class LudoGUI extends Application {

    //Group root;
    turn turn = new turn(0);
    Board lb = new Board(510, 50, 50);
    OtherNodes other = new OtherNodes(lb);
    Tokens lt = new Tokens(lb);
    Dice dice = new Dice();
    TokenMotion logic = new TokenMotion(this);
    Events events = new Events(this);

    public static void main(String args[]) {
        // Launch GUI
        launch(args);
    }
    
    Group setup() {
        // Adding nodes to a group
        Group root = new Group(lb.board, lt.tokenNodes, other.nodes);
        //logic = new TokenMotion(this);
        
        // Registering event for roll button
        other.rollB.addEventFilter(MouseEvent.MOUSE_CLICKED, events.displayRoll());

        // Registering events for tokens
        lt.tokenNodes.getChildren().forEach((Node token) -> {
            token.addEventFilter(MouseEvent.MOUSE_ENTERED, events.tokenBubbleUp());
            token.addEventFilter(MouseEvent.MOUSE_EXITED, events.tokenBubbleDown());
            token.addEventFilter(MouseEvent.MOUSE_CLICKED, events.tokenMove());
            token.setMouseTransparent(true);
        });
        
        return root;
    }

    // Method to initiate GUI
    @Override
    public void start(Stage ps) {
        
        Group root = setup();

        // Setup scene with the group node
        Scene scene = new Scene(root, lb.length * 1.5 + lb.xcor * 2, lb.length + lb.ycor * 2);

        // ps- primaryStage; Setup stage with the scene and show
        ps.setTitle("PROJECT LUDO");
        //ps.setAlwaysOnTop(true);
        ps.setScene(scene);
        ps.show();
    }
}

/*
Notes:
- No bonus roll awarded yet- kill, win, 6 --- now conditions added
- path is bounded to finish line but no win message shown
- tokens are adjusted only in safe spots
- and double triples quads all can be killed with one token
- same colored token may stack on each other with only one token visible at non safespot
- turn color is not indicated currently, although moving other's tokens will not be possible
- tokens which can be moved bubbles up when mouse enters
- because it is dice values are directly from random generator some may get 6's in sequence
  and some may not be able to take out a single token also
*/