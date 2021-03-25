package InventorySystem;

import controller.AddPartFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

/** One error I was getting while working on this was a NullPointerException when trying to save Products.
 * This was because I forgot to set the associatedParts ObservableList to an FXCollections.observableArrayList().
 * Changing this fixed the error. */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Inventory.addPart(new InHouse(AddPartFormController.generateId(), "First part", 34.99, 3,1,5, 1234));
        Inventory.addPart(new InHouse(AddPartFormController.generateId(), "Second part", 3.99, 2,1,50, 1234));
        Inventory.addPart(new InHouse(AddPartFormController.generateId(), "Third part", 4.99, 30,1,15, 1234));
        Inventory.addPart(new InHouse(AddPartFormController.generateId(), "Brakes", 0.99, 11,1,5, 1234));
        Inventory.addPart(new Outsourced(AddPartFormController.generateId(), "outsourced part", 34.99, 3,1,5, "Apple"));



        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
