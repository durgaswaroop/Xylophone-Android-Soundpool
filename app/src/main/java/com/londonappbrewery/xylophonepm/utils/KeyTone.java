package com.londonappbrewery.xylophonepm.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class KeyTone {
    private int buttonId;
    private int toneId;
    private long startTime;
    private long endTime;

    public KeyTone(int buttonId, long startTime, long endTime) {
        this.buttonId = buttonId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getButtonId() {
        return buttonId;
    }

    public void setButtonId(int buttonId) {
        this.buttonId = buttonId;
    }

    public int getToneId() {
        return toneId;
    }

    public void setToneId(int toneId) {
        this.toneId = toneId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "KeyTone{" +
                "buttonId=" + buttonId +
                ", toneId=" + toneId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}