package agent;

import environnement.Position;
import environnement.Environment;

public class Effector {

    private Environment environment;

    public Effector(Environment environment) {
        this.environment = environment;
    }


    public void gather(Position position) {
        environment.gather(position);
    }

    public void clean(Position position){
        environment.clean(position);
    }

    public void move(Position positionToGoTO, int id){
        environment.agentMove(positionToGoTO, id);
    }
}
