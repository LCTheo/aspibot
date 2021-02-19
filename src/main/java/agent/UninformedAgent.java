package agent;

import environnement.Environment;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * Classe implémentant la version non informé de l'agent. l'algorithme utilisé est iterative deepning search.
 * La structure utilisée est un tree search.
 */
public class UninformedAgent extends Agent{

    /**
     * Constructeur de la classe UninformedAgent
     * @param environment environnement dans lequel évolue l'agent
     */
    public UninformedAgent(Environment environment) {
        super(environment);
    }

    @Override
    protected Deque<Action> planning() {
        Deque<Action> path = new LinkedList<>();
        Node solution = null;
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
        System.out.println("Solution trouvé !");
        Node currentNode = solution;

        while (currentNode != null){
            path.push(currentNode.getAction());
            currentNode = currentNode.getParent();
        }
        return path;
    }

    /**
     * methode de recherche en profondeur utilisée de maniere récursive.
     * @param node noeud initial de la recherche
     * @param limit limite de profondeur pour cette itération
     * @return le noeud solution si trouvé, null si pas de solution trouvée
     * @throws Exception limite de la recherche atteinte
     */
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

    /**
     * methode permettant d'étendre la frontiere d'exploration
     * @param parent noeud initial de l'expansion
     * @return liste de noeud
     */
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

    /**
     * calcul du coup d'un noeud donnée à partir de son parent et de l'action associée
     * @param parent parent du noeud
     * @param action action associé au noeud
     * @param node noeud dont le coût est à déterminer
     * @return valeur du coût
     */
    protected int stepCost(Node parent, Action action, Node node) {
        return 1;
    }



}
