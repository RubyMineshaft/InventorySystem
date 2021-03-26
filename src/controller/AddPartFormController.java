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

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Part will not be saved. Are you sure you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) loadMainMenu(event);
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
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

    private void loadMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public static int generateId(){
        currentId++;
        return currentId;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseRBtn.setSelected(true);
        partIDTxt.setText("Auto Gen - Disabled");
        id = generateId();
    }

    public void setInHouse(ActionEvent event) {
        machCompLabel.setText("Machine ID");
    }

    public void setOutsourced(){
        machCompLabel.setText("Company Name");
    }

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
