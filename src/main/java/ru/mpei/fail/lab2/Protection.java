package ru.mpei.fail.lab2;

import com.google.gson.annotations.SerializedName;

public class Protection {
    @SerializedName("vendor")
    private String vendor;
    @SerializedName("MainProtection")
    private MainProtection mainProtection;
    @SerializedName("BackupProtection")
    private BackupProtection backupProtection;
    
    public String getVendor() {
        return vendor;
    }
    
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    
    public MainProtection getMainProtection() {
        return mainProtection;
    }
    
    public void setMainProtection(MainProtection mainProtection) {
        this.mainProtection = mainProtection;
    }
    
    public BackupProtection getBackupProtection() {
        return backupProtection;
    }
    
    public void setBackupProtection(BackupProtection backupProtection) {
        this.backupProtection = backupProtection;
    }
    
    // проверка работы защиты
    public boolean protectFrom(ShortCircuit shortCircuit) {
        int resultMain = mainProtection.checkMainProtection(shortCircuit.getCurrent());
        
        if (resultMain > 0 ) {
            MyLogger.makeLog("info", "Сработала основная защита на " + shortCircuit.getTarget().getName());
            return true;
        }
        
        int resultBackup = backupProtection.checkBackupProtection(shortCircuit.getCurrent());
        
        if (resultMain == 0 && resultBackup > 0) {
            MyLogger.makeLog("warning", "Основная защита не увидела КЗ, но сработала резервная на " + shortCircuit.getTarget().getName());
            return true;
            
        } else if (resultMain < 0 && resultBackup > 0) {
            MyLogger.makeLog("warning", "Отказ основной защиты, но сработала резервная на " + shortCircuit.getTarget().getName());
            return true;
            
        } else if (resultMain == 0 && resultBackup == 0 ) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            MyLogger.makeLog("error", "Основная и резервная защиты не увидели КЗ");
            return false;
            
        } else if (resultMain < 0 && resultBackup == 0 ) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            MyLogger.makeLog("error", "Отказ основной защиты, резервная защита не увидела КЗ");
            return false;
            
        } else if (resultMain < 0 && resultBackup < 0 ) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            MyLogger.makeLog("error", "Отказ основной и резервной защит");
            return false;
            
        } else if (resultMain == 0 && resultBackup < 0 ) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            MyLogger.makeLog("error", "Основная защита не увидела КЗ, отказ резервной защиты");
            return false;
            
        } else {
            MyLogger.makeLog("error", "Непредвиденная ошибка в работе защиты!!!");
            return false;
        }
    }
    
    public class MainProtection {
        @SerializedName("type")
        private String type;
        @SerializedName("chanceOfFailure")
        private double chanceOfFailure;
        @SerializedName("setting")
        private double setting;
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public double getChanceOfFailure() {
            return chanceOfFailure;
        }
        
        public void setChanceOfFailure(double chanceOfFailure) {
            this.chanceOfFailure = chanceOfFailure;
        }
        
        public double getSetting() {
            return setting;
        }
        
        public void setSetting(int setting) {
            this.setting = setting;
        }
        
        private int checkMainProtection(double current) {
            if (Math.random() <= chanceOfFailure) {
                return -1; // защита отказала
            } else if (current < setting) {
                return 0; // защита не увидела КЗ
            } else {
                return 1; // защита сработала
            }
        }
    }
    
    public class BackupProtection {
        @SerializedName("type")
        private String type;
        @SerializedName("chanceOfFailure")
        private double chanceOfFailure;
        @SerializedName("setting")
        private double setting;
        @SerializedName("timeSetting")
        private double timeSetting;
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public double getChanceOfFailure() {
            return chanceOfFailure;
        }
        
        public void setChanceOfFailure(double chanceOfFailure) {
            this.chanceOfFailure = chanceOfFailure;
        }
        
        public double getSetting() {
            return setting;
        }
        
        public void setSetting(int setting) {
            this.setting = setting;
        }
        
        public double getTimeSetting() {
            return timeSetting;
        }
        
        public void setTimeSetting(double timeSetting) {
            this.timeSetting = timeSetting;
        }
        
        private int checkBackupProtection(double current) {
            try {
                Thread.sleep((long) (timeSetting * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            
            if (Math.random() <= chanceOfFailure) {
                return -1; // защита отказала
            } else if (current < setting) {
                return 0; // защита не увидела КЗ
            } else {
                return 1;
            }
        }
    }
    
}
