package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddProductFormController implements Initializable {

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
    private TableView<?> partTableView;

    @FXML
    private TableColumn<?, ?> partIdCol;

    @FXML
    private TableColumn<?, ?> partNameCol;

    @FXML
    private TableColumn<?, ?> partInvCol;

    @FXML
    private TableColumn<?, ?> partPriceCol;

    @FXML
    private TableView<?> associatedPartsTableView;

    @FXML
    private TableColumn<?, ?> associatedPartIdCol;

    @FXML
    private TableColumn<?, ?> associatedPartNameCol;

    @FXML
    private TableColumn<?, ?> associatedPartInvCol;

    @FXML
    private TableColumn<?, ?> associatedPartPriceCol;

    @FXML
    void onActionAddPart(ActionEvent event) {

    }

    @FXML
    void onActionCancel(ActionEvent event) {

    }

    @FXML
    void onActionRemovePart(ActionEvent event) {

    }

    @FXML
    void onActionSave(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
