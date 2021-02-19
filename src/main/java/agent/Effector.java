package agent;

import environnement.Position;
import environnement.Environment;

/**
 * classe simulant les actionneurs de l'agent
 */
public class Effector {

    // environnement dans lequel l'agent évolue
    private Environment environment;

    /**
     * Constructeur de la classe Effector
     * @param environment environnement dans lequel évolue l'agent
     */
    public Effector(Environment environment) {
        this.environment = environment;
    }

    /**
     * action de rammasser un bijou
     * @param position position où l'agent ce trouve
     */
    public void gather(Position position) {
        environment.gather(position);
    }

    /**
     * action de nettoyer la piece
     * @param position position où l'agent ce trouve
     */
    public void clean(Position position){
        environment.clean(position);
    }

    /**
     * action de déplacement de l'agetn
     * @param positionToGoTO position où l'agent va
     * @param id id du type de déplacement
     */
    public void move(Position positionToGoTO, int id){
        environment.agentMove(positionToGoTO, id);
    }
}
