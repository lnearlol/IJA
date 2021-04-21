
package ija.ija2021.projekt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import ija.ija2021.projekt.classes.Goods;
import ija.ija2021.projekt.classes.GoodsItem;
import ija.ija2021.projekt.classes.Shelf;
import ija.ija2021.projekt.classes.Testovaci_trida;

import ija.ija2021.projekt.settings.Drawable;
import ija.ija2021.projekt.settings.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main extends Application {

    @SuppressWarnings("unchecked")
	@Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiLayout.fxml"));
        
        BorderPane root_element = loader.load(); 
        Scene scene = new Scene(root_element);
        primaryStage.setScene(scene); 
        primaryStage.show();
        

        MainController controller = loader.getController();
        List<Drawable> elements = new ArrayList<>();

        YAMLFactory map_base = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        ObjectMapper mapper = new ObjectMapper(map_base);
        
        new Testovaci_trida();
        
    }
}

