package ru.mpei.fail.lab2;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Substation {
    @SerializedName("name")
    private String name;
    @SerializedName("Transformer")
    private List<Transformer> transformers;
    @SerializedName("Line")
    private List<Line> lines;
    @SerializedName("Bus")
    private List<Bus> buses;
    @SerializedName("CircuitBreaker")
    private List<CircuitBreaker> circuitBreakers;
    private int severeDamage = 0;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Transformer> getTransformers() {
        return transformers;
    }
    
    public void setTransformers(List<Transformer> transformers) {
        this.transformers = transformers;
    }
    
    public List<Line> getLines() {
        return lines;
    }
    
    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
    
    public List<Bus> getBuses() {
        return buses;
    }
    
    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }
    
    public List<CircuitBreaker> getCircuitBreakers() {
        return circuitBreakers;
    }
    
    public void setCircuitBreakers(List<CircuitBreaker> circuitBreakers) {
        this.circuitBreakers = circuitBreakers;
    }
    
    public int getSevereDamage() {
        return severeDamage;
    }
    
    public void setSevereDamage(int severeDamage) {
        this.severeDamage = severeDamage;
    }
    
    public void decreaseSevereDamage() {
        this.severeDamage--;
    }
    
    // из списка имен элементов получаем список самих элементов
    public List<ElectricalObject> electricalObjectsFromString(List<String> connectedWithTargetString) {
        
        List<ElectricalObject> electricalObjects = getAllElectricalObjects();
        
        return electricalObjects.stream()
                .filter((x) -> connectedWithTargetString.contains(x.getName()))
                .toList();
    }
    
    public List<ElectricalObject> getEnabledElectricalObjects() {
        List<ElectricalObject> electricalObjects = getAllElectricalObjects();
        return electricalObjects.stream()
                .filter((x) -> x.isWorking())
                .toList();
    }
    
    public List<ElectricalObject> getAllElectricalObjects() {
        List<ElectricalObject> electricalObjects = new ArrayList<>(transformers);
        electricalObjects.addAll(lines);
        electricalObjects.addAll(buses);
        electricalObjects.addAll(circuitBreakers);
        return electricalObjects;
    }
    
    //нужен для логера, показывает сколько секций шин и трансформаторов в работе
    public List<Integer> getActualState() {
        List<Integer> actualState = new ArrayList<>();
        List<ElectricalObject> electricalObjects = getAllElectricalObjects();
        int buses110 = 0;
        int buses35 = 0;
        int transformers = 0;
        for (ElectricalObject electricalObject : electricalObjects) {
            if (electricalObject instanceof Bus) {
                if (electricalObject.isWorking() && (electricalObject.getName().equals("B12") || electricalObject.getName().equals("B11"))) {
                    buses110++;
                } else if (electricalObject.isWorking() && (electricalObject.getName().equals("B22") || electricalObject.getName().equals("B21"))) {
                    buses35++;
                }
            }
            
            if (electricalObject instanceof Transformer) {
                if (electricalObject.isWorking()) {
                    transformers++;
                }
            }
        }
        
        actualState.add(buses110);
        actualState.add(buses35);
        actualState.add(transformers);
        return actualState;
    }
}
