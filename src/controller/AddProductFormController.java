package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductFormController implements Initializable {

    private static int currentId;
    private ObservableList<Part> selectedParts = FXCollections.observableArrayList();


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

    public static int generateId(){
        currentId++;
        return currentId;
    }

    @FXML
    void onActionAddPart(ActionEvent event) {
        selectedParts.add(partTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        loadMainMenu(event);
    }

    @FXML
    void onActionRemovePart(ActionEvent event) {
        selectedParts.remove(associatedPartsTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        int id = generateId();
        String name = productNameTxt.getText();
        double price = Double.parseDouble(priceTxt.getText());
        int stock = Integer.parseInt(invTxt.getText());
        int min = Integer.parseInt(minTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());

        Product product = new Product(id, name, price, stock, min, max);
        for (Part part : selectedParts) {
            product.addAssociatedPart(part);
        }

        Inventory.addProduct(product);

        loadMainMenu(event);
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productIdTxt.setText("Auto Gen - Disabled");

        partTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTableView.setItems(selectedParts);

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
}
