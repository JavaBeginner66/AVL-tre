import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 @author Jan Andreas Sletta og Sindre Haavaldsen
  *
  *
  *
  */

public class AvlView extends Application {

    private final static int skjermHøyde = 800;
    private final static int skjermBredde = 1400;
    private final double radius = 15;
    private final double høydeAvstand = 45;
    private Pane tegneBrett;

    private TextField nyeTall;
    private Button settInnTall;
    private Button leggInnTilfeldigeTall;
    private Button slettTre;

    private AVLTre tre = new AVLTre();
    private AVLTre.AVLNode root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane pane = new BorderPane();
        tegneBrett = new Pane();
        pane.setCenter(tegneBrett);
        pane.setBottom(setInfoVindu());

        primaryStage.setTitle("Algoritmer oblig 2: Avl tre");
        primaryStage.setScene(new Scene(pane, skjermBredde, skjermHøyde));
        primaryStage.show();

        settInnTall.setOnMouseClicked((e) -> {
            root = tre.insert(root, Integer.parseInt(nyeTall.getText()));
            tegnTre();
            nyeTall.setText("");
        });

        leggInnTilfeldigeTall.setOnMouseClicked((e) -> {
            for(int i = 0; i<10; i++){
                root = tre.insert(root,(int)(Math.random() * 99 + 1));
            }
            tegnTre();
        });

        slettTre.setOnMouseClicked((e) -> {
            root = null;
            tre = new AVLTre();
            tegneBrett.getChildren().clear();
        });
    }

    /** En public versjon av tegnTre som kaller privat-versjonen*/
    public void tegnTre() {
        tegneBrett.getChildren().clear();
        if (root != null) {
            tegnTre(root, tegneBrett.getWidth() / 2, høydeAvstand,
                    tegneBrett.getWidth() / 4);
        }
    }


    private void tegnTre(AVLTre.AVLNode root,
                         double x, double y, double breddeAvstand) {
        if (root.left != null) {
            // Tegn en linje til noden til venstre
            tegneBrett.getChildren().add(new Line(x - breddeAvstand, y + høydeAvstand, x, y));
            // Tegn subtreet til venstre rekursivt
            tegnTre(root.left, x - breddeAvstand, y + høydeAvstand, breddeAvstand / 2);
        }

        if (root.right != null) {
            // Tegn en linje til noden til høyre
            tegneBrett.getChildren().add(new Line(x + breddeAvstand, y + høydeAvstand, x, y));
            // Tegn subtreet til høyre rekursivt
            tegnTre(root.right, x + breddeAvstand, y + høydeAvstand, breddeAvstand / 2);
        }

        // Tegn en node som en farget sirkel med tall
        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.LIGHTGREEN);
        circle.setStroke(Color.BLACK);
        tegneBrett.getChildren().addAll(circle,
                new Text(x-10, y + 4, root.value + ""));
    }

    /**
     *  Metoden legger opp en GridPane som inneholder knapper og tekstfelt
     */
    private GridPane setInfoVindu(){
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(13, 13, 13,13));
        grid.setHgap(15);
        grid.setVgap(5);

        nyeTall = new TextField();
        settInnTall = new Button("Sett inn");
        leggInnTilfeldigeTall = new Button("Legg inn 10 tilfeldige tall");
        slettTre = new Button("Slett tre");

        grid.add(nyeTall, 0,0);
        grid.add(settInnTall, 1, 0);
        grid.add(leggInnTilfeldigeTall, 2, 0);
        grid.add(slettTre, 3, 0);

        return grid;
    }

}
