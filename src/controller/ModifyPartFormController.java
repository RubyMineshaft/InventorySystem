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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartFormController implements Initializable {

    private int listIndex;
    private String errorText = "";

    @FXML
    private Label errorTxt;

    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private ToggleGroup partSourceToggle;

    @FXML
    private RadioButton outsourcedRBtn;

    @FXML
    private Label machCompLabel;

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

    int id, stock, max, min, machineId;
    String name, companyName;
    double price;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        loadMainMenu(event);
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        errorText = "";
        try {
            if (validate()) {
                if (inHouseRBtn.isSelected()) {
                    Inventory.updatePart(listIndex, new InHouse(id, name, price, stock, min, max, machineId));
                    loadMainMenu(event);
                } else {
                    Inventory.updatePart(listIndex, new Outsourced(id, name, price, stock, min, max, companyName));
                    loadMainMenu(event);
                }
            }
        }

        catch(Exception e) {
            errorText += "Some fields contain invalid input. \n";
            errorTxt.setText(errorText);
        }    }

    private void loadMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void modifyPart(Part part) {

        listIndex = Inventory.getAllParts().indexOf(part);
        partIDTxt.setText(String.valueOf(part.getId()));
        id = part.getId();
        partNameTxt.setText(part.getName());
        partInvTxt.setText(String.valueOf(part.getStock()));
        partPriceTxt.setText(String.valueOf(part.getPrice()));
        partMaxTxt.setText(String.valueOf(part.getMax()));
        partMinTxt.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse){
            machCompLabel.setText("Machine ID");
            machCompTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
            inHouseRBtn.setSelected(true);
        } else {
            machCompLabel.setText("Company Name");
            machCompTxt.setText(((Outsourced) part).getCompanyName());
            outsourcedRBtn.setSelected(true);
        }
    }

    private boolean validate(){
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

    public void setInHouse(ActionEvent event) {
        machCompLabel.setText("Machine ID");
    }

    public void setOutsourced(){
        machCompLabel.setText("Company Name");
    }
}
