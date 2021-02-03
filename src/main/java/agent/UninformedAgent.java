package agent;

import environnement.environment;

import java.util.Deque;

/**
 * @theo
 */
public class UninformedAgent extends Agent{


    public UninformedAgent(environment environment) {
        super(environment);
    }

    @Override
    protected Deque<Action> planning() {

        return null;
    }

}
