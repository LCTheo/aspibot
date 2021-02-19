package agent;

import environnement.Environment;
import environnement.Room;

/**
 * classe simulant les capteurs de l'agent
 */
public class Sensor {

    // environnement dans lequel l'agent évolue
    Environment environment;

    /**
     * Constructeur de la classe sensor
     * @param environment environnement dans lequel évolue l'agent
     */
    public Sensor(Environment environment) {
        this.environment = environment;
    }

    /**
     * permet de donner l'état de l'environnement à l'agent
     * @return un tableau représentant les pieces de l'environnement
     */
    public Room[][] scanEnvironment(){
        return environment.getMap();
    }

    /**
     * donne le score "bijou"
     * @return
     */
    public int getJewelScore(){
        return environment.getJewelscore();
    }

    /**
     * donne le score "poussiere"
     * @return
     */
    public int getDustScore(){
        return environment.getDustScore();
    }

    /**
     * donne le cout en éléctricité
     * @return
     */
    public int getElectricityCost(){
        return environment.getElectricityCost();
    }
}
