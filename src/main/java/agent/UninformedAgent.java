package agent;

import environnement.Position;
import environnement.Room;
import environnement.environment;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * @theo
 */
public class UninformedAgent extends Agent{

    private Set<State> closed;
    public UninformedAgent(environment environment) {
        super(environment);
    }

    @Override
    protected Deque<Action> planning() {
        Deque<Action> path = new LinkedList<>();
        Node solution = null;
        closed = new HashSet<>();
        int depth = 0;
        Node initialState= new Node(this.state, null, 0, 0 );
        while(solution == null){

            try {
               solution = recursive_DLS(initialState, depth);
            } catch (Exception e) {
                System.out.println("profondeur : "+ depth);
                depth = depth + 1;
            }
        }

        Node currentNode = solution;

        while (currentNode != null){
            path.push(currentNode.getAction());
            currentNode = currentNode.getParent();
        }
        return path;
    }

    private Node recursive_DLS(Node node, int limit) throws Exception{
        boolean cutoff = false;
        if (goalTest(node.getState())){
            return node;
        }
        else if(node.getDepth() == limit){
            throw new Exception();

        }
        else {
            for (Node successor: expend(node)) {
                //if (!closed.contains(successor.getState())){
                  //  closed.add(successor.getState());
                    try {
                        Node result = recursive_DLS(successor, limit);
                        if (result != null){
                            return result;
                        }
                    }catch (Exception e){
                        cutoff = true;
                    }
                //}
            }
            if (cutoff){
                throw new Exception();
            }
            else {
                return null;
            }
        }
    }

    protected List<Node> expend(Node parent){
        List<Node> successors = new ArrayList<>();

        for (Pair<Action, State> nextState: successorFn(parent.getState())){
            Node s = new Node();
            s.setParent(parent);
            s.setAction(nextState.getLeft());
            s.setState(nextState.getRight());
            s.setPathCost(parent.getPathCost() + stepCost(parent, nextState.getLeft(), s));
            s.setDepth(parent.getDepth() + 1);
            successors.add(s);
        }
        return successors;
    }

    protected int stepCost(Node parent, Action action, Node node) {
        return 1;
    }

    protected List<Pair<Action, State>> successorFn(State lastState){
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

}
