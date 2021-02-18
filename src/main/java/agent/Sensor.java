package agent;

import environnement.Environment;
import environnement.Room;

public class Sensor {

    Environment environment;

    public Sensor(Environment environment) {
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
