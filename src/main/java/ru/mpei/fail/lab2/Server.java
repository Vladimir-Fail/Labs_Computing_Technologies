package ru.mpei.fail.lab2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Server {
    public void whatIsHappeningOnSubstation(Substation substation) {
        if (substation.getSevereDamage() > 0) { // Если на предыдущих итерациях случилась серьезная авария, то считаем
            // что подстанция не дееспособна
            substation.decreaseSevereDamage();
            if (substation.getSevereDamage() != 0) {
                MyLogger.makeLog("error", "ПС в данный момент находится в нерабочем состоянии и не может снабжать потребителей!"); // изменить
            } else {
                MyLogger.makeLog("info", "ПС восстановлена!");
            }
            
        } else {
            // собираем все работающие элементы
            List<ElectricalObject> electricalObjects = substation.getEnabledElectricalObjects();
            
            if (Math.random() <= 0.7) { // КЗ в конкретной итерации происходит с вероятностью примерно 70%
                ShortCircuit shortCircuit = new ShortCircuit(electricalObjects); // генерируется новое КЗ
                ElectricalObject target = shortCircuit.getTarget();
                List<ElectricalObject> connectedWithTarget = substation.electricalObjectsFromString(target.getConnectedWith());
                // список элементов, которые нужно отключить при КЗ на объекте target
                
                MyLogger.makeLog("warning", shortCircuit.toString()); // информация о КЗ
                
                if (target.getProtection().protectFrom(shortCircuit)) {    // Проверяем, сработала ли защита
                    // Выполняем необходимые переключения
                    target.setCondition(false);
                    target.setIsWorking(false);
                    
                    if (target.getName().equals("Q9") || target.getName().equals("Q10")) {
                        substation.setSevereDamage(4); // В случае, если КЗ произошло на секционном выключателе (а он в работе только если
                        // уже что-то отключено!), то считаем это серьезной аварией.
                        MyLogger.makeLog("error", "Произошло КЗ на секционном выключателе! ПС выведена из работы.");
                    }
                    
                    // меняем на нерабочее состояние все связанные элементы
                    // если секционный выключатель переключается второй раз подряд, то считаем это серьезной аварией
                    for (ElectricalObject electricalObject : connectedWithTarget) {
                        if ((electricalObject.getName().equals("Q9") || electricalObject.getName().equals("Q10")) && electricalObject.isWorking()) {
                            substation.setSevereDamage(4);
                            MyLogger.makeLog("error", "ПС выведена из работы из-за невозможности снабжать потребителей!");
                        } else if (electricalObject.getName().equals("Q9") || electricalObject.getName().equals("Q10")) {
                            electricalObject.setIsWorking(!electricalObject.isWorking());
                        }
                        else {
                            electricalObject.setIsWorking(false);
                        }
                    }
                    
                } else { // защита не сработало, считаем это серьезной аварией
                    substation.setSevereDamage(5);
                    MyLogger.makeLog("error", "ПС выведена из работы из-за серьезной аварии!");
                }
                
            } else { // КЗ на этой итерации не произошло
                MyLogger.makeLog("info", "КЗ сегодня не произошло.");
            }
        }
    }
    
    // С вероятностью в 70% элемент восстановит после КЗ свое состояние
    public void checkAndRepair(Substation substation, Map<String, boolean[]> defaultState) {
        List<ElectricalObject> notWorkingElectricalObjects = // получим список объектов которые надо починить
                substation.getAllElectricalObjects().stream()
                        .filter((x) -> !x.isCondition())
                        .toList();
        
        
        if (!notWorkingElectricalObjects.isEmpty()) {
            for (ElectricalObject NotWorkingEelectricalObject : notWorkingElectricalObjects) {
                if (Math.random() <= 0.7) { // вероятность того что элемент починится до следующей итерации
                    NotWorkingEelectricalObject.setIsWorking(true);
                    NotWorkingEelectricalObject.setCondition(true);
                    
                    List<ElectricalObject> connectedWithNotWorkingElectricalObject =
                            substation.electricalObjectsFromString(NotWorkingEelectricalObject.getConnectedWith());
                    
                    connectedWithNotWorkingElectricalObject.stream()
                            .filter((x) -> !Arrays.equals(x.getFullState(), defaultState.get(x.getName()))) // фильтр нужен в случае секционного выключателя
                            .forEach((x) -> {
                                boolean[] temp = defaultState.get(x.getName());
                                x.setCondition(temp[0]);
                                x.setIsWorking(temp[1]);
                            });
                    
                    if (substation.getSevereDamage() == 0) {
                        MyLogger.makeLog("info", "Сегодня починили: " + NotWorkingEelectricalObject.getName());
                    }
                    
                } else {
                    if (substation.getSevereDamage() == 0) {
                        MyLogger.makeLog("warning", "Не успели сегодня починить: " + NotWorkingEelectricalObject.getName());
                    }
                }
            }
        } else { // в случае если нет поврежденных элементов
            if (substation.getSevereDamage() == 0) {
                MyLogger.makeLog("info", "Сегодня нечего ремонтировать.");
                // пишем что ничего ремонтировать не нужно
            }
        }
    }
    
    // логирует текущее состояние подстанции (сколько шин и трансформаторов находятся в работе)
    public void logActualState(Substation substation) {
        List<Integer> actualState = substation.getActualState();
        
        StringBuilder notEnabledElectricalObjects = new StringBuilder();
        List<ElectricalObject> allforString = substation.getAllElectricalObjects();
        for (ElectricalObject electricalObject : allforString) {
            if (!electricalObject.isWorking()) {
                notEnabledElectricalObjects.append("\n");
                notEnabledElectricalObjects.append(electricalObject.getName());
            }
        }
        
        checkBuses(substation);
        
        if (substation.getSevereDamage() == 0) {
            MyLogger.makeLog("info", "Текущее состояние подстанции:\n В работе шин 110 кВ: " + actualState.getFirst() +
                    " , В работе шин 35 кВ: " +
                    actualState.get(1) + " , В работе трансформаторов: " + actualState.get(2) + ".");
            
            MyLogger.makeLog("info", "Не находятся в работе: " + notEnabledElectricalObjects);
        }
    }
    
    // если выведено из работы больше одной секции шин, то считаем это серьезной аварией
    public void checkBuses(Substation substation) {
        List<Integer> actualState = substation.getActualState();
        if (substation.getSevereDamage() == 0 && (((actualState.get(0) + actualState.get(1)) < 3) || actualState.get(2) == 0)) {
            substation.setSevereDamage(4);
            MyLogger.makeLog("error", "ПС выведена из работы из-за невозможности снабжать потребителей!");
        }
    }
    
}
