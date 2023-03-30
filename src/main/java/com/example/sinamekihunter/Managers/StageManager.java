package com.example.sinamekihunter.Managers;

import com.example.sinamekihunter.Controllers.ControllersParent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class StageManager {
    private static StageManager instance;
    private Stage mainStage;
    private HashMap<String,Stage> active_stages;
    private StageManager(Stage mainStage,String firstStage_name){
        active_stages = new HashMap<>();
        this.mainStage = mainStage;
        active_stages.put(firstStage_name,mainStage);
    }
    public static StageManager getInstance(){
        return instance;
    }
    public static void createInstance(Stage firstStage,String firstStage_name){
        instance = new StageManager(firstStage,firstStage_name);
    }
    public Stage getMainStage(){
        return this.mainStage;
    }
    public Stage getStage(String stage_name){
        return active_stages.get(stage_name);
    }
    public void createStage(String stage_name, Stage new_stage,String sceneName,ControllersParent controller){
        active_stages.put(stage_name,new_stage);
        ControllersManager.getInstance().addController(new_stage.getScene(),controller,sceneName);
        new_stage.show();
    }
    public void closeStage(String old_stage_name){
        Stage old_stage = active_stages.get(old_stage_name);
        active_stages.remove(old_stage_name);
        ControllersManager.getInstance().closeScene(old_stage.getScene());
        old_stage.close();
    }
}
