package ru.mpei.fail.lab2;

import java.util.List;

public class ShortCircuit {
    //модификаторы тока КЗ в зависимости от типа КЗ
    private static final double SINGLE_PHASE_MODIFIER = 0.5;
    private static final double TWO_PHASE_MODIFIER = 0.75;
    private static final double THREE_PHASE_MODIFIER = 1;
    private static final double TURN_TO_TURN_MODIFIER = 0.3;
    // T: [1,26; 9), El110: [2,1; 9), El35: [3,5; 15) возможные токи КЗ
    
    private String type;
    private ElectricalObject target;
    private double current;
    
    public ElectricalObject getTarget() {
        return target;
    }
    
    public double getCurrent() {
        return current;
    }
    
    public ShortCircuit(List<ElectricalObject> electricalObjects) {
        int indexOfTarget = (int) (Math.random() * electricalObjects.size());
        target = electricalObjects.get(indexOfTarget);
        
        double currentVoltageModifier; // Имитирует разный уровень токов кз для разных классов напряжения
        if (target.getVoltage() == 110) {
            currentVoltageModifier = 0.6;
        } else {
            currentVoltageModifier = 1;
        }
        
        // Определяем тип и ток КЗ
        double randomNumber = Math.random();
        double scCurrent = Math.random() * 8 + 7; // [7, 15) кА
        if (target instanceof Transformer) { // для трансформатора и для остальных элементов разные виды КЗ
            if (randomNumber < 0.25) {
                type = "single-phase";
                current = scCurrent * currentVoltageModifier * SINGLE_PHASE_MODIFIER;
            } else if (randomNumber >= 0.25 && randomNumber < 0.5) {
                type = "two-phase";
                current = scCurrent * currentVoltageModifier * TWO_PHASE_MODIFIER;
            } else if (randomNumber >= 0.5 && randomNumber < 0.75) {
                type = "three-phase";
                current = scCurrent * currentVoltageModifier * THREE_PHASE_MODIFIER;
            } else {
                type = "turn-to-turn";
                current = scCurrent * currentVoltageModifier * TURN_TO_TURN_MODIFIER;
            }
            
        } else {
            if (randomNumber < 0.33) {
                type = "single-phase";
                current = scCurrent * currentVoltageModifier * SINGLE_PHASE_MODIFIER;
            } else if (randomNumber >= 0.33 && randomNumber < 0.66) {
                type = "two-phase";
                current = scCurrent * currentVoltageModifier * TWO_PHASE_MODIFIER;
            } else {
                type = "three-phase";
                current = scCurrent * currentVoltageModifier * THREE_PHASE_MODIFIER;
            }
        }
    }
    
    @Override
    public String toString() {
        return "Произошло КЗ на " + target.getName() +
                ". Тип замыкания: '" + type + '\'' +
                ", I = " + current +
                " кА";
    }
}
