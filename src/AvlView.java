import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
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
 @author Jan Andreas Sletta og Sindre Haavaldsen (Gruppe 7)
  *
  * Programmet lager et AVLTre av Integer verdier
  * som blir tegna på et pane
  *
  * Klassen står for det grafiske og legger opp komponenter
  *
  */

public class AvlView extends Application {

    private final static int skjermHøyde = 800;
    private final static int skjermBredde = 1400;
    private final double radius = 15;           // Radius på tegnet node
    private final double høydeAvstand = 45;     // Høyde avstand mellom hver node
    private Pane tegneBrett;                    // Panet AVLTreet blir tegnet på

    private TextField nyeTall;                  // Textfield brukeren legger tall inn i
    private Button settInnTall;                 // Knapp som legger inn tall fra nyeTall Textfielf
    private Button leggInnTilfeldigeTall;       // Knapp som legger inn 10 tilfeldige tall
    private Button slettTre;                    // Sletter treet
    private ColorPicker nodeFarge;              // Velger farge på node

    private AVLTre tre = new AVLTre();          // Instans av klassen AVLTre som inneholder metoder for logikken
    private AVLTre.AVLNode root;                // Instans av node klasse (root noden)

    @Override
    public void start(Stage primaryStage){
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

    /** En public versjon av tegnTre som kaller private-versjonen*/
    public void tegnTre() {
        tegneBrett.getChildren().clear();
        if (root != null) {
            tegnTre(root, tegneBrett.getWidth() / 2, høydeAvstand,
                    tegneBrett.getWidth() / 4);
        }
    }


    /**
     *
     * Metoden tar inn root noden og tegner ut treet fullstendig
     * som et rekursivt kall
     *
     * @param      root             root noden
     * @param      x                x posisjon til node
     * @param      y                y posisjon til node
     * @param      breddeAvstand    Bredde-avstanden mellom hver node
     *
     */
    private void tegnTre(AVLTre.AVLNode root,
                         double x, double y, double breddeAvstand) {
        if (root.venstreNode != null) {
            // Tegn en linje til noden til venstre
            tegneBrett.getChildren().add(new Line(x - breddeAvstand, y + høydeAvstand, x, y));
            // Tegn subtreet til venstre rekursivt
            tegnTre(root.venstreNode, x - breddeAvstand, y + høydeAvstand, breddeAvstand / 2);
        }

        if (root.hoyreNode != null) {
            // Tegn en linje til noden til høyre
            tegneBrett.getChildren().add(new Line(x + breddeAvstand, y + høydeAvstand, x, y));
            // Tegn subtreet til høyre rekursivt
            tegnTre(root.hoyreNode, x + breddeAvstand, y + høydeAvstand, breddeAvstand / 2);
        }

        // Tegn en node som en farget sirkel med tall
        Circle circle = new Circle(x, y, radius);
        circle.setFill(nodeFarge.getValue());
        circle.setStroke(Color.BLACK);
        tegneBrett.getChildren().addAll(circle,
                new Text(x-10, y + 4, root.verdi + ""));
    }

    /**
     *  Metoden legger opp et GridPane som inneholder knapper og tekstfelt
     *
     * @return Gridpane der knappene ligger
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
        nodeFarge = new ColorPicker();
        nodeFarge.setValue(Color.LIGHTGREEN);

        grid.add(nyeTall, 0,0);
        grid.add(settInnTall, 1, 0);
        grid.add(leggInnTilfeldigeTall, 2, 0);
        grid.add(slettTre, 3, 0);
        grid.add(nodeFarge, 4, 0);

        return grid;
    }

}
