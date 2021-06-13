package polyygon;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * the graphical of the settings screens
 */
public class App {

    public static Pane root;
    private static Game game;
    private static int width, height;
    private Settings settings;
    private int selectedField;
    private int selectedRuby;
    private int selectedPearl;
    private int selectedMod;

    /**
     * constructor
     * @param t (int) choose menu item:
     *          0 -> New Game
     *          1 -> Settings
     *          2 -> Continue
     *          3 -> How to play
     */
    public App(Pane main, int t) throws IOException, ClassNotFoundException {
        root = new Pane();
        width = 1280; //px <--->
        height = 720; //px
        File tmpDir = new File("settings.txt");

        if (!tmpDir.exists()) { // check if the user already saved settings
            settings = new Settings(); //if not init the settings object
        } else {
            settings = new Settings();
            settings = SaveLoad.deserializeSettings(); // else get the settings saved
            selectedField = settings.field; // 0, 1, 2...or extra 3 (4th map)
            selectedPearl = settings.pearl; // 0, 1
            selectedRuby = settings.ruby; // 0, 1
            selectedMod = settings.mod; //0, 1, 2
        }
        Res.init(); // initialze the assets
        root.setPrefSize(width, height);
        main.getChildren().add(root);
        if (t == 1) { // get the selected screen by the user
            showSettings(); // settings screen
        } else if (t == 2) {
            SavedGame savedGame = SaveLoad.deserializeBoard(); //continue game
            startGame(1, savedGame); // p == 1 => human vs. human
        } else if (t == 3) { //how to play screen
            showHowToPlay();
        } else {
            gameModeSelection(); // new game screen
        }

    }

    /**
     * it will show "How to play" menu item (screen)
     */
    public void showHowToPlay() {

        root.getChildren().clear();
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        StackPane stackPane = new StackPane();
        HBox rules = new HBox();

        ImageView rule1 = new ImageView(Res.rules1); //first image from 2
        rule1.setFitHeight(690);
        rule1.setFitWidth(588);
        ImageView rule2 = new ImageView(Res.rules2); //second image from 2
        rule2.setFitHeight(690);
        rule2.setFitWidth(588);
        rules.setAlignment(Pos.CENTER);
        rules.getChildren().addAll(rule1, rule2);
        VBox vBox=new VBox();

        MenuItem returnB = new MenuItem("Return", mouseEvent -> {
            returnToMenu();
        });
        vBox.setAlignment(Pos.CENTER);

        stackPane.getChildren().add(rules);
        stackPane.setPrefWidth(1280); // <--->
        stackPane.setAlignment(Pos.BASELINE_CENTER);
        vBox.getChildren().addAll(stackPane,returnB);

        root.getChildren().add(vBox);


    }

    /**
     * set buttons the user can choose what mode he wants to play
     */
    public void gameModeSelection() {
        MainMenu.showBg(root);
        MenuBox vbox = new MenuBox();

        if (selectedMod == 2) {
            vbox = new MenuBox(
                    new MenuItem("Vs human", e -> {
                        startGame(1, null); // p == 1 => human vs. human
                    }),
                    new MenuItem("Return", mouseEvent -> returnToMenu()));
        } else {
            vbox = new MenuBox(
                    new MenuItem("Vs human", e -> {
                        startGame(1, null); // p == 1 => human vs. human
                    }),
                    new MenuItem("Vs AI", mouseEvent -> startGame(0, null)), // p == 0 => AI vs. human
                    new MenuItem("Return", mouseEvent -> returnToMenu()));
        }

        vbox.setTranslateX(100);
        vbox.setTranslateY(350);

        root.getChildren().addAll(vbox);
    }

    /**
     * return to (main) menu method
     */
    public void returnToMenu() {
        root.getChildren().clear();
        root.getChildren().add(MainMenu.createContent());
    }

    /**
     * inialize the graphical of the settings screen
     */
    public void showSettings() {
        StackPane container = new StackPane();
        container.setPrefSize(width, height);
        root.getChildren().clear();
        MainMenu.showBg(root);
        VBox setting = new VBox();
        setting.setAlignment(Pos.CENTER);
        setting.setSpacing(50);
        setting.setBackground(new Background(new BackgroundFill(Color.rgb(127, 127, 127), CornerRadii.EMPTY, Insets.EMPTY)));

        HBox buttonContainer = new HBox();
        MenuItem apply = new MenuItem("Apply", mouseEvent -> {
            settings.field = selectedField;
            settings.ruby = selectedRuby;
            settings.pearl = selectedPearl;
            settings.mod = selectedMod;
            try {                             //mouselisteners of the apply and return button
                SaveLoad.serializeSettings(settings);
            } catch (IOException e) {
                e.printStackTrace();
            }
            returnToMenu();
        });
        MenuItem returnB = new MenuItem("Return", mouseEvent -> {
            returnToMenu();
        });
        buttonContainer.setSpacing(50);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(returnB, apply);

        HBox mapContainer = new HBox();
        Text text = new Text("MAP      : ");
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 25));

        HBox map = new HBox();
        ImageView field0 = new ImageView(Res.field(0));
        ImageView field1 = new ImageView(Res.field(1));
        ImageView field2 = new ImageView(Res.field(2));
        ImageView field3 = new ImageView(Res.field(3));
        map.getChildren().addAll(field0, field1, field2, field3);
        map.setSpacing(50);
        map.setAlignment(Pos.CENTER);
        map.getChildren().forEach(node -> {
            ((ImageView) node).setFitWidth(100);
            ((ImageView) node).setFitHeight(100);
            ((ImageView) node).setOpacity(1.0);
        });
        mapContainer.getChildren().addAll(text, map);
        mapContainer.setAlignment(Pos.CENTER);
        mapContainer.setSpacing(100);

        HBox pearlContainer = new HBox();
        Text textPearl = new Text("PEARL     : ");
        textPearl.setFill(Color.WHITE);
        textPearl.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 25));
        HBox pearl = new HBox();
        ImageView pearl0 = new ImageView(Res.pearl(0));
        ImageView pearl1 = new ImageView(Res.pearl(1));
        pearl.getChildren().addAll(pearl0, pearl1);
        pearl.setSpacing(50);
        pearl.setAlignment(Pos.CENTER);
        pearl.getChildren().forEach(node -> {
            ((ImageView) node).setFitWidth(64);
            ((ImageView) node).setFitHeight(64);
        });
        pearlContainer.getChildren().addAll(textPearl, pearl);
        pearlContainer.setAlignment(Pos.CENTER);
        pearlContainer.setSpacing(100);


        HBox rubyContainer = new HBox();
        Text textruby = new Text("RUBY     : ");
        textruby.setFill(Color.WHITE);
        textruby.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 25));
        HBox ruby = new HBox();
        ImageView ruby0 = new ImageView(Res.ruby(0));
        ImageView ruby1 = new ImageView(Res.ruby(1));
        ruby.getChildren().addAll(ruby0, ruby1);
        ruby.setSpacing(50);
        ruby.setAlignment(Pos.CENTER);
        ruby.getChildren().forEach(node -> {
            ((ImageView) node).setFitWidth(64);
            ((ImageView) node).setFitHeight(64);
        });
        rubyContainer.getChildren().addAll(textruby, ruby);
        rubyContainer.setAlignment(Pos.CENTER);
        rubyContainer.setSpacing(100);

        HBox modContainer = new HBox();
        Text textMod = new Text("MOD     : ");
        textMod.setFill(Color.WHITE);
        textMod.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 25));
        HBox mod = new HBox();
        ImageView mod0 = new ImageView(Res.mod(0));
        ImageView mod1 = new ImageView(Res.mod(1));
        ImageView mod2 = new ImageView(Res.mod(2));
        mod.getChildren().addAll(mod0, mod1, mod2);
        mod.setSpacing(50);
        mod.setAlignment(Pos.CENTER);
        mod.getChildren().forEach(node -> {
            ((ImageView) node).setFitWidth(64);
            ((ImageView) node).setFitHeight(64);
        });
        modContainer.getChildren().addAll(textMod, mod);
        modContainer.setAlignment(Pos.CENTER);
        modContainer.setSpacing(100);

        setupMouseListeners(map.getChildren(), 0);
        setupMouseListeners(pearl.getChildren(), 1);
        setupMouseListeners(ruby.getChildren(), 2);
        setupMouseListeners(mod.getChildren(), 3);
        setSelected(map.getChildren(), ruby.getChildren(), pearl.getChildren(), mod.getChildren());

        container.setAlignment(Pos.BASELINE_CENTER);
        setting.getChildren().addAll(mapContainer, pearlContainer, rubyContainer, modContainer, buttonContainer);
        container.getChildren().addAll(setting);
        root.getChildren().addAll(container);
    }

    /**
     * setSelected => set green glow on the selected/active settings
     */
    public void setSelected(List<Node> maps, List<Node> rubies, List<Node> pearls, List<Node> mods) {
        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.GREEN);
        borderGlow.setWidth(30);
        borderGlow.setHeight(30);
        maps.get(selectedField).setEffect(borderGlow);
        rubies.get(selectedRuby).setEffect(borderGlow);
        pearls.get(selectedPearl).setEffect(borderGlow);
        mods.get(selectedMod).setEffect(borderGlow);
    }

    /**
     * select => set green glow on the clicked setting
     */
    public void select(Node selected, List<Node> list) {
        list.forEach(node -> node.setEffect(null));
        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.GREEN);
        borderGlow.setWidth(30);
        borderGlow.setHeight(30);
        selected.setEffect(borderGlow);
    }

    /**
     * setupMouseListeners => set setting by clicking
     */
    public void setupMouseListeners(List<Node> nodes, int type) {
        nodes.forEach(node -> node.setOnMouseClicked(mouseEvent -> {
            switch (type) {
                case 0 -> selectedField = nodes.indexOf(mouseEvent.getSource());
                case 1 -> selectedPearl = nodes.indexOf(mouseEvent.getSource());
                case 2 -> selectedRuby = nodes.indexOf(mouseEvent.getSource());
                case 3 -> selectedMod = nodes.indexOf(mouseEvent.getSource());
            }
            select(node, nodes);
        }));

    }

    /**
     * initialize game object
     * @param p p = 0 -> ai vs human / p = 1 -> human vs human
     * @param savedGame is object of savegame
     */
    public void startGame(int p, SavedGame savedGame) {
        game = new Game((double) width, (double) height, p);

        root.getChildren().add(game);
        game.setOnMouseClicked(mouseEvent -> {
                game.getBoard().selectCell(mouseEvent.getX(), mouseEvent.getY()); // mouse listener on the game screen
        });

        if (savedGame == null) {
            game.init(selectedField, selectedPearl, selectedRuby, selectedMod); // if the game is not saved init a new game

        } else game.loadGame(savedGame, savedGame.selectedField, selectedPearl, selectedRuby, savedGame.mod); // else load the game
        game.start(); // start the game thread
    }


}
