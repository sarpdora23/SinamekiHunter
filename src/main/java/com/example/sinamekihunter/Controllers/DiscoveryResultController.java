package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Utils.DiscoverThread;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;

public class DiscoveryResultController implements ControllersParent {
    private DiscoverThread discoverThread;
    @FXML
    private Label discovery_type_label;
    @FXML
    private Label discovery_progress_label;
    @FXML
    private Button discovery_start_button;
    @FXML
    private Button discovery_stop_button;
    @FXML
    private ListView discovery_result_listview;
    @FXML
    private ProgressBar discovery_progressbar;


    @Override
    public void InitController() {

    }
    public void setDiscoverThread(DiscoverThread discoverThread){
        this.discoverThread = discoverThread;
    }

}

