package agent;

import environnement.Position;
import environnement.Room;
import environnement.environment;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;

public class InformedAgent extends Agent{

    public InformedAgent(environment environment) {
        super(environment);
    }

    protected List<InformedNode> expend(InformedNode parent) {
        List<InformedNode> successors = new ArrayList<>();

        for (Pair<Action, State> nextState: successorFn(parent.getState())){
            InformedNode s = new InformedNode();
            s.setParent(parent);
            s.setAction(nextState.getLeft());
            s.setState(nextState.getRight());
            s.setPathCost(parent.getPathCost() + stepCost(parent, nextState.getLeft(), s));
            s.setHeuristicCost(heuristic(nextState.getRight()));
            s.setDepth(parent.getDepth() + 1);
            successors.add(s);
        }
        return successors;
    }

    protected int stepCost(Node parent, Action action, Node node) {
        return 1;
    }

    protected List<Pair<Action, State>> successorFn(State lastState) {
        List<Pair<Action, State>> successors = new ArrayList<>();
        State nextState;
        if(lastState.getRoom(lastState.getAgentPosition().getX(), lastState.getAgentPosition().getY()).isDust()){
            nextState = new State(lastState.getMap(), lastState.getAgentPosition());
            nextState.getRoom(nextState.getAgentPosition().getX(), nextState.getAgentPosition().getY()).setDust(false);
            nextState.getRoom(nextState.getAgentPosition().getX(), nextState.getAgentPosition().getY()).setJewel(false);
            successors.add(new ImmutablePair<>(Action.clean, nextState));
        }

        if(lastState.getRoom(lastState.getAgentPosition().getX(), lastState.getAgentPosition().getY()).isJewel()){
            nextState = new State(lastState.getMap(), lastState.getAgentPosition());
            nextState.getRoom(nextState.getAgentPosition().getX(), nextState.getAgentPosition().getY()).setJewel(false);
            successors.add(new ImmutablePair<>(Action.gather, nextState));
        }

        if(lastState.getAgentPosition().getX()<4){
            Position newPos = new Position(lastState.getAgentPosition().getX()+1, lastState.getAgentPosition().getY());
            nextState = new State(lastState.getMap(), newPos);
            successors.add(new ImmutablePair<>(Action.moveRight, nextState));
        }
        if(lastState.getAgentPosition().getX()>0){
            Position newPos = new Position(lastState.getAgentPosition().getX()-1, lastState.getAgentPosition().getY());
            nextState = new State(lastState.getMap(), newPos);
            successors.add(new ImmutablePair<>(Action.moveLeft, nextState));
        }
        if(lastState.getAgentPosition().getY()<4){
            Position newPos = new Position(lastState.getAgentPosition().getX(), lastState.getAgentPosition().getY()+1);
            nextState = new State(lastState.getMap(), newPos);
            successors.add(new ImmutablePair<>(Action.moveDown, nextState));
        }
        if(lastState.getAgentPosition().getY()>0){
            Position newPos = new Position(lastState.getAgentPosition().getX(), lastState.getAgentPosition().getY()-1);
            nextState = new State(lastState.getMap(), newPos);
            successors.add(new ImmutablePair<>(Action.moveHigh, nextState));
        }

        return successors;
    }

    @Override
    protected Deque<Action> planning() {
        Deque<Action> path = new LinkedList<>();
        List<InformedNode> fringe = new ArrayList<>();
        InformedNode initialState = new InformedNode(this.state, null, 0, 0, heuristic(this.state));
        fringe.add(initialState);
        while (!fringe.isEmpty()){
            InformedNode node = fringe.remove(0);
            node.getState().printMap();
            System.out.println("position :" + node.getState().getAgentPosition().getX() +", "+node.getState().getAgentPosition().getY());
            if (goalTest(node.getState())){
                while (node != null){
                    path.push(node.getAction());
                    node = node.getParent();
                }
                return path;
            }
            fringe = insertAll(expend(node), fringe);
        }
        System.out.println("Aucune solution trouvé.");
        return path;
    }

    private List<InformedNode> insertAll(List<InformedNode> expend, List<InformedNode> fringe) {
        InformedNode node;
        while (!expend.isEmpty()){
            node = expend.remove(0);
            int i = 0;
            if (fringe.isEmpty()){
                fringe.add(node);
            }else {
                while ((i < fringe.size()) && (fringe.get(i).getTotalCost() <= node.getTotalCost()) ){
                    i++;
                }
                fringe.add(i, node);
            }
        }
        return fringe;
    }

    private int heuristic(State state){
        int i = 0;
        int j = 0;
        int score = 0;
        while (i<4){
            while (j<4){
                if (state.getRoom(i, j).isDust() || state.getRoom(i, j).isJewel()){
                    if (state.getRoom(i, j).isDust()){
                        score++;
                    }
                    if (state.getRoom(i, j).isJewel()){
                        score++;
                    }
                    int distance = abs(state.getAgentPosition().getX() - i) + abs(state.getAgentPosition().getY() - j);
                    score = score + distance;
                }
                j++;
            }
            j = 0;
            i++;
        }
        return score;
    }
}
