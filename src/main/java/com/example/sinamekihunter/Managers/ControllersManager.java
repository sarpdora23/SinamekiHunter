package com.example.sinamekihunter.Managers;

import com.example.sinamekihunter.Controllers.ControllersParent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class ControllersManager {

    private static ControllersManager instance;
    private ControllersParent mainController;
    private HashMap<Scene,ControllersParent> active_controllers;
    private HashMap<String,Scene> active_scenes;

    private ControllersManager(ControllersParent firstController,Scene firstScene,String scene_name){
        active_controllers = new HashMap<>();
        active_controllers.put(firstScene,firstController);
        active_scenes = new HashMap<>();
        active_scenes.put(scene_name,firstScene);
    };
    public static void createInstance(ControllersParent firstController,Scene firstScene,String scene_name){
        instance = new ControllersManager(firstController,firstScene,scene_name);
    }
    public static ControllersManager getInstance(){
        return instance;
    }
    public void addController(Scene scene,ControllersParent controller){
        active_controllers.put(scene,controller);
    }
    public ControllersParent getMainController(){
        return this.mainController;
    }
    public ControllersParent getController(String name){
        Scene scene = active_scenes.get(name);
        return active_controllers.get(scene);
    }
    public ControllersParent getController(Scene scene){
        return active_controllers.get(scene);
    }
    public static void changeScene(Stage stage,ControllersParent controller ,Scene newScene,String scene_name){
        ControllersManager.getInstance().closeScene(stage.getScene());
        stage.hide();
        stage.setScene(newScene);
        instance.active_scenes.put(scene_name,newScene);
        instance.active_controllers.put(newScene,controller);
        stage.show();
    }
    public void closeScene(Scene old_scene){
        active_controllers.remove(old_scene);
        String old_scene_name = "";
        for (String scene_name:active_scenes.keySet()) {
            if (active_scenes.get(scene_name) == old_scene){
                old_scene_name = scene_name;
                break;
            }
        }
        active_scenes.remove(old_scene_name);
    }

    public void setMainScene(ControllersParent mainController){
        this.mainController = mainController;
    }
}
