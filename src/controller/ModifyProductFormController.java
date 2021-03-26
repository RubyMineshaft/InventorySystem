package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.Optional;

/**
 * Controller for the Modify product form.
 */
public class ModifyProductFormController {

    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int listIndex;

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private String errorText = "";

    @FXML
    private Label errorTxt;

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

    /** Adds the selected part to the products associated parts list.
     * @param event the click event
     */
    @FXML
    private void onActionAddPart(ActionEvent event) {
        associatedParts.add(partTableView.getSelectionModel().getSelectedItem());
    }

    /** Handler for the cancel button. Displays confirmation dialogue and returns user to main menu.
     * @param event the click event
     */
    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Changes will not be saved. Are you sure you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) loadMainMenu(event);
    }

    /** Handler for the remove associated part button. Displays confirmation dialogue and removes selected associated part.
     * @param event the click event.
     */
    @FXML
    private void onActionRemovePart(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Associated Parts");
        alert.setHeaderText("Associated part removal");
        alert.setContentText("Are you sure you want to remove this associated part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            associatedParts.remove(associatedPartsTableView.getSelectionModel().getSelectedItem());
    }

    /** Validates form data. Checks for input errors and displays error messages.
     * @return true if no fields contain errors
     */
    private boolean validate(){
        boolean hasErrors = false;

        name = productNameTxt.getText();
        if (name == "") {
            errorText += "Name cannot be empty. \n";
            hasErrors = true;
        }

        try { stock = Integer.parseInt(invTxt.getText()); }
        catch(NumberFormatException e) {
            errorText += "Inv must be an integer. \n";
            hasErrors = true;
        }

        try {price = Double.parseDouble(priceTxt.getText());}
        catch(NumberFormatException e){
            errorText += "Price must be a double. \n";
            hasErrors = true;
        }

        try {max = Integer.parseInt(maxTxt.getText());}
        catch(NumberFormatException e) {
            errorText += "Max must be a number. \n";
            hasErrors = true;
        }

        try { min = Integer.parseInt(minTxt.getText()); }
        catch(NumberFormatException e) {
            errorText += "Min must be a number. \n";
            hasErrors = true;
        }

        if (min > max) {
            errorText += "Min must be less than Max. \n";
            hasErrors = true;
        }
        if (!(min < stock && stock < max)) {
            errorText += "Inv must be between Min and Max. \n";
            hasErrors = true;
        }

        errorTxt.setText(errorText);

        return !hasErrors;
    }

    /** Handler for the save button. Updates product if validations pass.
     * @param event the click event
     */
    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        errorText = "";

        if (validate()) {

            Product updatedProduct = new Product(id, name, price, stock, min, max);
            for (Part part : associatedParts) {
                updatedProduct.addAssociatedPart(part);
            }

            Inventory.updateProduct(listIndex, updatedProduct);
            loadMainMenu(event);
        }


    }

    /** Accepts product information from the main menu view. Populates form fields with object's data.
     * @param product the product to be modified.
     */
    public void modifyProduct(Product product) {

        listIndex = Inventory.getAllProducts().indexOf(product);
        id = product.getId();
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

    /** Displays the main menu.
     * @param event the click event
     */
    private void loadMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Handler for part searches. Searches for a part and filters the table view.
     * @param event the search submit event
     */
    public void partSearch(ActionEvent event) {
        errorTxt.setText("");
        String query = partSearchTxt.getText();

        try {
            Part match = Inventory.lookupPart(Integer.parseInt(query));
            if (match == null) {
                errorTxt.setText("Search returned no results.");
                partSearchTxt.setText("");
            }
            partTableView.getSelectionModel().select(match);
        } catch (NumberFormatException e) {
            ObservableList<Part> matches = Inventory.lookupPart(query);
            partTableView.setItems(matches);
            if (matches.isEmpty()){
                errorTxt.setText("Search returned no results.");
                partSearchTxt.clear();
                partTableView.setItems(Inventory.getAllParts());
            }
        }

        partTableView.requestFocus();
    }


}
