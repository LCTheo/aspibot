package agent;

import environnement.Environment;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * @theo
 */
public class UninformedAgent extends Agent{

    private Set<State> closed;
    public UninformedAgent(Environment environment) {
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



}
