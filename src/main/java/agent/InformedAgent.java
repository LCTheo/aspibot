package agent;

import environnement.Environment;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static java.lang.Math.abs;

public class InformedAgent extends Agent{

    public InformedAgent(Environment environment) {
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

    @Override
    protected Deque<Action> planning() {
        Set<State> closed = new HashSet<>();
        Deque<Action> path = new LinkedList<>();
        List<InformedNode> fringe = new ArrayList<>();
        InformedNode initialState = new InformedNode(this.state, null, 0, 0, heuristic(this.state));
        fringe.add(initialState);
        while (!fringe.isEmpty()){
            InformedNode node = fringe.remove(0);
            if (!closed.contains(node.getState())){
                closed.add(node.getState());
                System.out.println("position :" + node.getState().getAgentPosition().getX() +", "+node.getState().getAgentPosition().getY());

                if (goalTest(node.getState())){
                    System.out.println("Solution trouvé !");
                    while (node != null){
                        path.push(node.getAction());
                        node = node.getParent();
                    }
                    return path;
                }
                fringe = insertAll(expend(node), fringe);
            }
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
