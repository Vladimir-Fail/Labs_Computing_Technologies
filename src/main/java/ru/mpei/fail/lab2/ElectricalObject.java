package ru.mpei.fail.lab2;

import java.util.List;

public interface ElectricalObject {
    String getName();
    boolean isCondition();
    void setCondition(boolean condition);
    boolean isWorking();
    void setIsWorking(boolean isWorking);
    boolean[] getFullState();
    int getVoltage();
    Protection getProtection();
    List<String> getConnectedWith();
}
