package agent;

import environnement.Position;
import environnement.environment;

public class Effector {

    private environment environment;

    public Effector(environment environment) {
        this.environment = environment;
    }


    public void gather(Position position) {
        environment.gather(position);
    }

    public void clean(Position position){
        environment.clean(position);
    }

    public void move(Position position){
        environment.agentMove(position);
    }
}
