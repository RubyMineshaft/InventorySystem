package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the main menu.
 */
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Label searchFailTxt;

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
    private TextField productSearchTxt;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInvCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    /** Handler for the add part button. Navigates to the add part form.
     * @param event click event
     */
    @FXML
    private void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Handler for the add product button. Displays the add product form.
     * @param event click event
     */
    @FXML
    private void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Handler for the delete part button. Displays a confirmation dialogue and deletes selected part.
     * @param event click event
     */
    @FXML
    private void onActionDeletePart(ActionEvent event) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Part Deletion");
        alert.setContentText("Are you sure you want to delete " + selectedPart.getName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            Inventory.deletePart(selectedPart);
    }

    /** Handler for the delete product button. Displays a confirmation dialogue and deletes selected product if there are no associated parts.
     * @param event click event
     */
    @FXML
    private void onActionDeleteProduct(ActionEvent event) {

        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Product Deletion");
        alert.setContentText("Are you sure you want to delete " + selectedProduct.getName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            if (selectedProduct.getAllAssociatedParts().isEmpty())
                Inventory.deleteProduct(selectedProduct);
            else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Product Deletion Error");
                error.setContentText("Products with associated parts cannot be deleted. Please remove all associated parts first.");
                error.showAndWait();
            }


    }

    /** Exits the application.
     * @param event click event
     */
    @FXML
    private void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /** Handler for the Modify part button. Navigates to the Modify part form.
     * @param event the click event
     */
    @FXML
    private void onActionModifyPart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPartForm.fxml"));
        loader.load();

        ModifyPartFormController controller = loader.getController();

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Modify Part");
            error.setHeaderText("Selection Error");
            error.setContentText("You must choose a part to modify before clicking the modify button.");

            error.showAndWait();
        } else {
            controller.modifyPart(selectedPart);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Handler for the modify product button. Navigates to the modify product form.
     * @param event the click event
     */
    @FXML
    private void onActionModifyProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProductForm.fxml"));
        loader.load();

        ModifyProductFormController controller = loader.getController();

        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Modify Product");
            error.setHeaderText("Selection Error");
            error.setContentText("You must choose a product to modify before clicking the modify button.");

            error.showAndWait();
        } else {

            controller.modifyProduct(productTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /** Populates Part and Product table views when the view is loaded.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    /** Handler for part searches. Filters or selects parts in list depending on search query.
     * @param event the search submit event
     */
    public void partSearch(ActionEvent event) {
        searchFailTxt.setText("");
        String query = partSearchTxt.getText();

        try {
            Part match = Inventory.lookupPart(Integer.parseInt(query));
            if (match == null) {
                searchFailTxt.setText("Search returned no results.");
                partSearchTxt.setText("");
            }
            partTableView.getSelectionModel().select(match);
        } catch (NumberFormatException e) {
            ObservableList<Part> matches = Inventory.lookupPart(query);
            partTableView.setItems(matches);
            if (matches.isEmpty()){
                searchFailTxt.setText("Search returned no results.");
                partSearchTxt.clear();
                partTableView.setItems(Inventory.getAllParts());
            }
        }

        partTableView.requestFocus();
    }

    /** Handler for product searches. Filters or selects products in list depending on search query.
     * @param event the search submit event
     */
    public void productSearch(ActionEvent event) {
        searchFailTxt.setText("");
        String query = productSearchTxt.getText();

        try {
            Product match = Inventory.lookupProduct(Integer.parseInt(query));
            if (match == null) {
                searchFailTxt.setText("Search returned no results.");
                productSearchTxt.setText("");
            }
            productTableView.getSelectionModel().select(match);
        } catch (NumberFormatException e) {
            ObservableList<Product> matches = Inventory.lookupProduct(query);
            productTableView.setItems(matches);
            if (matches.isEmpty()){
                searchFailTxt.setText("Search returned no results.");
                productSearchTxt.clear();
                productTableView.setItems(Inventory.getAllProducts());
            }
        }

        productTableView.requestFocus();
    }


}

