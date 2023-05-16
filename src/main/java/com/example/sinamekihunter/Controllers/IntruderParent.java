package com.example.sinamekihunter.Controllers;

import java.io.File;
import java.io.IOException;

public abstract class IntruderParent implements IntruderType {

    protected int speed = 1;
    protected boolean isStopped;
    protected boolean isRunning;
    protected int totalWordCount = 0;
    @Override
    public void fuzz(String requestText) {

    }

    @Override
    public File getWordlist() {
        return null;
    }

    @Override
    public boolean getIsRunning() {
        return this.isRunning;
    }

    @Override
    public boolean getIsStopped() {
        return this.isStopped;
    }
    @Override
    public void setRunning(boolean newValue) {
        this.isRunning = newValue;
    }

    @Override
    public void setStopped(boolean newValue) {
        this.isStopped = newValue;
    }
    @Override
    public void setSpeed(int newValue) {
        this.speed = newValue;
    }

    @Override
    public int getTotalWordCount() {
        return 0;
    }

    @Override
    public void initIntruder() throws IOException {

    }

}
