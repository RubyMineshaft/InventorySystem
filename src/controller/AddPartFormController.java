package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class AddPartFormController {

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
    void onActionCancel(ActionEvent event) {

    }

    @FXML
    void onActionSave(ActionEvent event) {

    }

}
