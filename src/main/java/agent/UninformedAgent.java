package agent;

import environnement.Room;
import environnement.environment;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @theo
 */
public class UninformedAgent extends Agent{


    public UninformedAgent(environment environment) {
        super(environment);
    }

    @Override
    protected Deque<Action> planning() {
        Node solution = null;
        int depth = 0;
        Node initialState= new Node(this.state, null, 0, 0 );
        while(solution == null){

            try {
               solution = recursive_DLS(initialState, this.goal, depth);
            } catch (Exception e) {
                depth = depth + 1;
            }
        }
        Deque<Action> path = new LinkedList<>();

        return null;
    }

    private Node recursive_DLS(Node state, Room[][] goal, int limit) throws Exception{
        boolean cutoff = false;
        if (goalTest(state.getState())){
            return state;
        }
        else if(state.getDepth() == limit){
            throw new Exception();
        }
        else {
            for (Node successor: expend(state, goal)) {
                try {
                    Node result = recursive_DLS(successor, goal, limit);
                    if (result != null){
                        return result;
                    }
                }catch (Exception e){
                    cutoff = true;
                }

            }
            if (cutoff){
                throw new Exception();
            }
            else {
                return null;
            }
        }
    }

    protected List<Node> expend(Node parent, Room[][] goal){
        List<Node> successors = new ArrayList<>();

        for (Pair<Action, State> nextState: successorFn(goal, parent.getState())){
            Node s = new Node();
            s.setParent(parent);
            s.setAction(nextState.getLeft());
            s.setState(nextState.getRight());
            s.setPathCost(parent.getPathCost() + stepCost(parent, nextState.getLeft(), s));
            s.setDepth(parent.getDepth() + 1);
            int i = 0;
            if (successors.isEmpty()){
                successors.add(s);
            }
            else {
                while (successors.get(i).getAction().morePriorityThan(s.getAction()) && i < successors.size()){
                    i++;
                }
                successors.add(i, s);
            }
        }
        return successors;
    }

    protected int stepCost(Node parent, Action action, Node node) {
        return 1;
    }

    protected List<Pair<Action, State>> successorFn(Room[][] goal, State lastState){
        List<Pair<Action, State>> successors = new ArrayList<>();

        if(lastState.getAgentPosition().getX()<4){
            State nextState = lastState.copy();
            nextState.getAgentPosition().setX(nextState.getAgentPosition().getX()+1);
            successors.add(new ImmutablePair<>(Action.moveRight, nextState));
        }
        if(lastState.getAgentPosition().getX()>1){
            State nextState = lastState.copy();
            nextState.getAgentPosition().setX(nextState.getAgentPosition().getX()-1);
            successors.add(new ImmutablePair<>(Action.moveLeft, nextState));
        }
        if(lastState.getAgentPosition().getY()<4){
            State nextState = lastState.copy();
            nextState.getAgentPosition().setY(nextState.getAgentPosition().getY()+1);
            successors.add(new ImmutablePair<>(Action.moveDown, nextState));
        }
        if(lastState.getAgentPosition().getY()>1){
            State nextState = lastState.copy();
            nextState.getAgentPosition().setY(nextState.getAgentPosition().getY()-1);
            successors.add(new ImmutablePair<>(Action.moveHigh, nextState));
        }

        if(lastState.getRoom(lastState.getAgentPosition().getX(), lastState.getAgentPosition().getY()).isDust()){
            State nextState = lastState.copy();
            nextState.getRoom(nextState.getAgentPosition().getX(), nextState.getAgentPosition().getY()).setDust(false);
            nextState.getRoom(nextState.getAgentPosition().getX(), nextState.getAgentPosition().getY()).setJewel(false);
            successors.add(new ImmutablePair<>(Action.clean, nextState));
        }

        if(lastState.getRoom(lastState.getAgentPosition().getX(), lastState.getAgentPosition().getY()).isJewel()){
            State nextState = lastState.copy();
            nextState.getRoom(nextState.getAgentPosition().getX(), nextState.getAgentPosition().getY()).setJewel(false);
            successors.add(new ImmutablePair<>(Action.gather, nextState));
        }
        return successors;
    }

}
