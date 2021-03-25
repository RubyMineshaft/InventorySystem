package InventorySystem;

import controller.AddPartFormController;
import controller.AddProductFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/** One error I was getting while working on this was a NullPointerException when trying to save Products.
 * This was because I forgot to set the associatedParts ObservableList to an FXCollections.observableArrayList().
 * Changing this fixed the error. */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Inventory.addPart(new InHouse(AddPartFormController.generateId(), "Belt", 8.99, 15,5,25, 1234));
        Inventory.addPart(new InHouse(AddPartFormController.generateId(), "Chain", 13.88, 2,1,50, 1234));
        Inventory.addPart(new InHouse(AddPartFormController.generateId(), "Drum", 19.50, 30,1,30, 1234));
        Inventory.addPart(new InHouse(AddPartFormController.generateId(), "Brakes", 24.95, 11,1,5, 1234));
        Inventory.addPart(new Outsourced(AddPartFormController.generateId(), "Padding", 4.97, 3,1,5, "XYZ"));

        Inventory.addProduct(new Product(AddProductFormController.generateId(), "Amazing Product", 149.99, 4, 1, 8));
        Inventory.addProduct(new Product(AddProductFormController.generateId(), "Another Product", 79.99, 7, 1, 10));
        Inventory.addProduct(new Product(AddProductFormController.generateId(), "Dog House", 49.50, 3, 1, 8));

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
