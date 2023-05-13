package com.example.sinamekihunter.Controllers;

import java.io.File;

public interface IntruderType {
    public void fuzz(String requestText);
    public File getWordlist();
    public boolean getIsRunning();
    public boolean getIsStopped();
    public void setRunning(boolean newValue);
    public void setStopped(boolean newValue);
    public int getTotalWordCount();
    public void initIntruder();
    public void setSpeed(int newValue);
}
