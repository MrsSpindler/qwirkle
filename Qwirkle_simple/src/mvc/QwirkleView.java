package mvc;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class QwirkleView {
    public static final double GAP = 2;
    public static final double H_SPACING = 15;
    public static final double V_SPACING = 5;
    private static final double FONT_SIZE = 20;

    private QwirkleModel model;

    private BorderPane view;
    private GridPane tileArea;
    private HBox tileInfo;
    private HBox playerArea;
    private Button btnSwitchPlayer;
    private Text txtTilesRemaining;


    public QwirkleView(QwirkleModel _model) {
        model = _model;

        createAndConfigurePanes();

        createAndLayoutControls();

        updateControllerFromListeners();

        observeModelAndUpdateControls();
    }

    private void observeModelAndUpdateControls() {
        // TODO Auto-generated method stub
        

    }

    private void updateControllerFromListeners() {
        // TODO Auto-generated method stub

    }

    private void createAndLayoutControls() {
        
        txtTilesRemaining = new Text("Tiles remaining " + model.getTilesRemaining());
        txtTilesRemaining.setFont(Font.font(FONT_SIZE));
        
        btnSwitchPlayer = new Button(model.isOnePlayerMode() ? "End Turn" : "Next Player");

        tileInfo.getChildren().add(txtTilesRemaining);
        tileInfo.getChildren().add(btnSwitchPlayer);
        
        
        for (Player player : model.getPlayers()) {
            TilePane playerPane = new TilePane();
            playerPane.setStyle("-fx-border-color: black");
            playerPane.setAlignment(Pos.CENTER);
            playerPane.setPrefWidth((Player.NUM_TILES + 2) * (Tile.TILE_SIZE + GAP));
            
            Text txtName = new Text(player.getName());
            txtName.setFont(Font.font(FONT_SIZE));
            playerPane.getChildren().add(txtName);  
            
            
            for (Tile tile : player.getTilesInPlay()) {
                ImageView tilePic = new ImageView(tile.getImageName());
                tilePic.setFitHeight(Tile.TILE_SIZE);
                tilePic.setFitWidth(Tile.TILE_SIZE);
                
                playerPane.getChildren().add(tilePic);
            }
            
            Text txtScore = new Text("0");
            txtScore.setFont(Font.font(FONT_SIZE));
            playerPane.getChildren().add(txtScore);  
            
            playerArea.getChildren().add(playerPane);

          
        }

        
    }

    private void createAndConfigurePanes() {
        view = new BorderPane();

        VBox controls = new VBox();
        controls.setAlignment(Pos.CENTER);
        controls.setSpacing(V_SPACING);

        tileInfo = new HBox();
        tileInfo.setAlignment(Pos.CENTER);
        tileInfo.setSpacing(H_SPACING);

        playerArea = new HBox();
        playerArea.setAlignment(Pos.CENTER);
        playerArea.setSpacing(H_SPACING);

        controls.getChildren().addAll(tileInfo, playerArea);
        view.setTop(controls);
        
        tileArea = new GridPane();
        tileArea.setAlignment(Pos.CENTER);
        tileArea.setHgap(GAP);
        tileArea.setVgap(GAP);

        view.setCenter(tileArea);

    }

    public Parent asParent() {
        return view;
    }

}
