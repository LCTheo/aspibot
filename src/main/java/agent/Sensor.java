package agent;

import environnement.environment;
import environnement.Room;

public class Sensor {

    environment environment;

    public Sensor(environment environment) {
        this.environment = environment;
    }

    public Room[][] scan_environement(){
        return environment.getMap();
    }

    public int getJewelScore(){
        return 0;
    }

    public float getDustScore(){
        return 0;
    }
}
