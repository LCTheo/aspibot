package agent;

import environnement.environment;
import environnement.Room;

public class Sensor {

    environment environment;

    public Sensor(environment environment) {
        this.environment = environment;
    }

    public Room[][] scanEnvironment(){
        return environment.getMap();
    }

    public int getJewelScore(){
        return environment.getJewelscore();
    }

    public float getDustScore(){
        return environment.getDustScore();
    }

    public int getElectricityCost(){
        return environment.getElectricityCost();
    }
}
