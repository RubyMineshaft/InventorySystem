package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the controller for the add part form.
 */
public class AddPartFormController implements Initializable {

    private static int currentId;
    private String errorText = "";

    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private ToggleGroup partSourceToggle;

    @FXML
    private RadioButton outsourcedRBtn;

    @FXML
    private Label machCompLabel;

    @FXML
    private Label errorTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partPriceTxt;

    @FXML
    private TextField partIDTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField machCompTxt;

    @FXML
    private TextField partMinTxt;

    private int id, stock, max, min, machineId;
    private String name, companyName;
    private double price;

    /** Event hadler for the cancel button. Displays a confirmation dialogue and goes back to the main menu if the user clicks "OK".
     * @param event the click event
     */
    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Part will not be saved. Are you sure you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) loadMainMenu(event);
    }

    /** The event handler for the save button. Validates form data and saves if no errors were found.
     * @param event the click event
     */
    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        errorText = "";
        try {
            if (validate()) {
                if (inHouseRBtn.isSelected()) {
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                    loadMainMenu(event);
                } else {
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                    loadMainMenu(event);
                }
            }
        }

        catch(Exception e) {
            errorText += "Some fields contain invalid input. \n";
            errorTxt.setText(errorText);
        }

    }

    /** This method navigates back to the main menu.
     * @param event the click event
     */
    private void loadMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method generates a unique id.
     * @return a unique ID
     */
    public static int generateId(){
        currentId++;
        return currentId;
    }

    /** This is the first method called when opening the Add part form. It selects the In House radio button and generates an ID.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseRBtn.setSelected(true);
        partIDTxt.setText("Auto Gen - Disabled");
        id = generateId();
    }

    /** Changes the machComp label to Machine ID when the in house radio button is selected.
     */
    public void setInHouse() {
        machCompLabel.setText("Machine ID");
    }

    /** Changes the machComp label to Company Name when the Outsourced radio button is selected.
     */
    public void setOutsourced(){
        machCompLabel.setText("Company Name");
    }

    /** Validates form data. Checks data types and displays error messages if invalid inputs are present.
     * @return True if all form fields are valid
     */
    private boolean validate() {
        boolean hasErrors = false;

        name = partNameTxt.getText();
        if (name == "") {
            errorText += "Name cannot be empty. \n";
            hasErrors = true;
        }

        try { stock = Integer.parseInt(partInvTxt.getText()); }
        catch(NumberFormatException e) {
            errorText += "Inv must be an integer. \n";
            hasErrors = true;
        }

        try {price = Double.parseDouble(partPriceTxt.getText());}
        catch(NumberFormatException e){
            errorText += "Price must be a double. \n";
            hasErrors = true;
        }

        try {max = Integer.parseInt(partMaxTxt.getText());}
        catch(NumberFormatException e) {
            errorText += "Max must be a number. \n";
            hasErrors = true;
        }

        try { min = Integer.parseInt(partMinTxt.getText()); }
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

        if (inHouseRBtn.isSelected()) {
            try { machineId = Integer.parseInt(machCompTxt.getText()); }
            catch(NumberFormatException e) {
                errorText += "Machine ID must be a number. \n";
                hasErrors = true;
            }
        } else {
            companyName = machCompTxt.getText();
            if (companyName == "") {
                errorText += "Company name cannot be blank. \n";
                hasErrors = true;
            }
        }

        errorTxt.setText(errorText);

        return !hasErrors;
    }
}
