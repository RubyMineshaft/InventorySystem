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

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        loadMainMenu(event);
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        int id = Integer.parseInt(partIDTxt.getText());
        String name = partNameTxt.getText();
        int stock = Integer.parseInt(partInvTxt.getText());
        double price = Double.parseDouble(partPriceTxt.getText());
        int max = Integer.parseInt(partMaxTxt.getText());
        int min = Integer.parseInt(partMinTxt.getText());

        if (inHouseRBtn.isSelected()) {
            int machineId = Integer.parseInt(machCompTxt.getText());
            Inventory.updatePart(listIndex, new InHouse(id, name, price, stock, min, max, machineId));
        } else {
            String companyName = machCompTxt.getText();
            Inventory.updatePart(listIndex, new Outsourced(id, name, price, stock, min, max, companyName));
        }

        loadMainMenu(event);

    }

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

    public void setInHouse(ActionEvent event) {
        machCompLabel.setText("Machine ID");
    }

    public void setOutsourced(){
        machCompLabel.setText("Company Name");
    }
}
