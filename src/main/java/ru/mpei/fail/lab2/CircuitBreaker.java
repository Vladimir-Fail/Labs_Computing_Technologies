package ru.mpei.fail.lab2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CircuitBreaker implements ElectricalObject {
    @SerializedName("name")
    private String name;
    @SerializedName("condition")
    private boolean condition;
    @SerializedName("isWorking")
    private boolean isWorking;
    @SerializedName("voltage")
    private int voltage;
    @SerializedName("protection")
    private Protection protection;
    @SerializedName("connectedWith")
    private List<String> connectedWith;
    
    public List<String> getConnectedWith() {
        return connectedWith;
    }
    
    public void setConnectedWith(List<String> connectedWith) {
        this.connectedWith = connectedWith;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isCondition() {
        return condition;
    }
    
    public void setCondition(boolean condition) {
        this.condition = condition;
    }
    
    public boolean isWorking() {
        return isWorking;
    }
    
    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }
    
    public boolean[] getFullState() {
        return new boolean[]{isCondition(), isWorking()};
    }
    
    public int getVoltage() {
        return voltage;
    }
    
    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }
    
    public Protection getProtection() {
        return protection;
    }
    
    public void setProtection(Protection protection) {
        this.protection = protection;
    }
}
