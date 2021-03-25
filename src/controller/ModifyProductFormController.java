package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class ModifyProductFormController {

    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int listIndex;


    @FXML
    private TextField productIdTxt;

    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField invTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField partSearchTxt;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Part> associatedPartsTableView;

    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;

    @FXML
    private TableColumn<Part, String> associatedPartNameCol;

    @FXML
    private TableColumn<Part, Integer> associatedPartInvCol;

    @FXML
    private TableColumn<Part, Double> associatedPartPriceCol;

    @FXML
    void onActionAddPart(ActionEvent event) {
        associatedParts.add(partTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        loadMainMenu(event);
    }

    @FXML
    void onActionRemovePart(ActionEvent event) {
        associatedParts.remove(associatedPartsTableView.getSelectionModel().getSelectedItem());
    }



    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        int id = Integer.parseInt(productIdTxt.getText());
        String name = productNameTxt.getText();
        int inv = Integer.parseInt(invTxt.getText());
        double price = Double.parseDouble(priceTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());
        int min = Integer.parseInt(minTxt.getText());

        Product updatedProduct = new Product(id, name, price, inv, min, max);
        for (Part part : associatedParts) {
            updatedProduct.addAssociatedPart(part);
        }

        Inventory.updateProduct(listIndex, updatedProduct);
        loadMainMenu(event);


    }

    public void modifyProduct(Product product) {

        listIndex = Inventory.getAllProducts().indexOf(product);
        associatedParts.addAll(product.getAllAssociatedParts());

        productIdTxt.setText(String.valueOf(product.getId()));
        productNameTxt.setText(product.getName());
        invTxt.setText(String.valueOf(product.getStock()));
        priceTxt.setText(String.valueOf(product.getPrice()));
        maxTxt.setText(String.valueOf(product.getMax()));
        minTxt.setText(String.valueOf(product.getMin()));

        partTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTableView.setItems(associatedParts);

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void loadMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void partSearch(ActionEvent event) {
        String query = partSearchTxt.getText();

        try {
            Part match = Inventory.lookupPart(Integer.parseInt(query));
            partTableView.getSelectionModel().select(match);
        } catch (NumberFormatException e) {
            ObservableList<Part> matches = Inventory.lookupPart(query);
            partTableView.setItems(matches);
        }

        partSearchTxt.clear();
        partTableView.requestFocus();
    }


}
