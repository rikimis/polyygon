package polyygon;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * @author Richard Mištík
 */
public class MainMenu extends Application {

    public static Parent createContent() {

        Pane root = new Pane();
        root.setPrefSize(1280, 720); //in pixels
        //root.getChildren().add(mediaView);
        showBg(root);

        Title title = new Title("P O L Y Y G O N"); // title shown at the main (menu) screen
        // new Title = rectangle
        title.setTranslateX(50); //sets the value of the property translateX
        title.setTranslateY(200); //sets the value of the property translateY


        // create a menu box with buttons
        MenuBox vbox = new MenuBox(
                new MenuItem("NEW GAME", mouseEvent -> { // new game button with mouselistener
                    root.getChildren().clear(); // clear the screen
                    try {
                        new App(root, 0); // and start the program
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }),
                new MenuItem("Settings", mouseEvent -> {
                    root.getChildren().clear();
                    try {
                        new App(root, 1);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }),
                new MenuItem("How to Play", mouseEvent -> {
                    try {
                        new App(root, 3);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }),
                new MenuItem("Exit", mouseEvent -> ((Stage) root.getScene().getWindow()).close()));

        File tmpDir = new File("game.txt");
        if (tmpDir.exists()) { //shows continue button if game is already saved
            MenuItem i = new MenuItem("CONTINUE", mouseEvent -> {
                root.getChildren().clear();
                try {
                    new App(root, 2);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            vbox.addIndex(i, 0);
        }
        vbox.setTranslateX(100);
        vbox.setTranslateY(350);

        root.getChildren().addAll(title, vbox); //add the title and menu items

        return root;

    }

    /**
     * method to set the background image
     * @param root of class Pane
     */
    public static void showBg(Pane root) {
        try (InputStream is = Files.newInputStream(Paths.get("images/bg2.jpg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(1280); //px <--->
            img.setFitHeight(720); //px
            root.getChildren().add(img);
        } catch (IOException e) {
            System.out.println("Couldn't load image");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(createContent()); //scene
        primaryStage.setTitle("Polyygon");//title of the window
        primaryStage.setScene(scene); // scene -> stage
        //primaryStage.setFullScreen(true); //Full Screen

        //icon
        Image icon = new Image(new File("images/h1.png").toURI().toString());
        primaryStage.getIcons().add(icon);
        primaryStage.show();

        //infinite music loop
        final Task task = new Task() {

            @Override
            protected Object call() throws Exception {
                int s = Integer.MAX_VALUE;
                AudioClip audio = new AudioClip(new File("music/music.mp3").toURI().toString());
                audio.setVolume(0.5f);
                audio.setCycleCount(s);
                audio.play();
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
