package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Models.DomainModel;
import com.example.sinamekihunter.Models.TargetModel;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class AddSubdomainTabController implements ControllersParent{
    public TabPane tabPane;
    @FXML
    private TextField newDomainTextField;
    @FXML
    private Button addDomainButton;
    @Override
    public void InitController() {

    }
    @FXML
    public void onAddButtonClick(){
        String url = newDomainTextField.getText();
        DomainModel domainModel = new DomainModel(url);
        tabPane.getTabs().get(tabPane.getTabs().size() - 1).setText(domainModel.getDomainUrl());
        MainDashboardController mainDashboardController = (MainDashboardController) ControllersManager.getInstance().getController(StringValues.SceneNames.MAIN_DASHBOARD_SCENE);
        mainDashboardController.createAddSubdomainTab();
        tabPane.getSelectionModel().getSelectedItem().setContent(new Pane());
        tabPane.getSelectionModel().selectLast();
    }
}
