package ru.mpei.fail.lab2;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class Lab2 {
    public static void main() {
        
        Gson gson = new Gson();
        String path = "config/config.json";
        try (Reader reader = new FileReader(path)) {
            Substation substation = gson.fromJson(reader, Substation.class); // получение подстанции из файла конфигурации
            
            MyLogger.makeLog("info", "Построена подстанция: " + substation.getName() + "\n");
            
            Map<String, boolean[]> defaultState = new HashMap<>(); // создание словаря исходного состояния подстанции
            substation.getAllElectricalObjects()
                    .forEach((x) -> defaultState.put(x.getName(), x.getFullState()));
            
            Server server = new Server();
            
            /// --------------------  ЗАПУСК МОДЕЛИРОВАНИЯ -----------------------
            for (int i = 0; i < 10; i++) {
                MyLogger.makeLog("info", "Новый день на " + substation.getName());
                
                server.whatIsHappeningOnSubstation(substation);
                server.checkBuses(substation);
                server.checkAndRepair(substation, defaultState);
                server.logActualState(substation);
                
                MyLogger.makeLog("info", "День закончился!\n");
                Thread.sleep(1500);
            }
            
        } catch (Exception e) {
            MyLogger.makeLog("error", e.getMessage());
        }
        
        
    }
    
}
