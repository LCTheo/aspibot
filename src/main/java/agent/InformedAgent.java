package agent;

import environnement.Room;
import environnement.environment;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Deque;
import java.util.List;

public class InformedAgent extends Agent{

    protected InformedAgent(environment environment) {
        super(environment);
    }

    @Override
    protected List<Node> expend(Node parent) {
        return null;
    }

    @Override
    protected int stepCost(Node parent, Action action, Node node) {
        return 0;
    }

    @Override
    protected List<Pair<Action, State>> successorFn(State lastState) {
        return null;
    }

    @Override
    protected Deque<Action> planning() {

        return null;
    }


}
