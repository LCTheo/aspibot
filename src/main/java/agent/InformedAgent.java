package agent;

import environnement.Environment;
import org.apache.commons.lang3.tuple.Pair;
import java.util.*;

import static java.lang.Math.abs;

/**
 * Classe implémentant la version informé de l'agent. l'algorithme utilisé est A* dans sa forme simple.
 * de plus la structure utilisé est un graph search, ceci afin d'évité les boucles lié a l'heuristique.
 * l'heuristique représenté par la méthode heuristic(State state) est basé sur la distance de manhattan.
 */
public class InformedAgent extends Agent{

    /**
     * Constructeur de la classe InformedAgent
     * @param environment environnement dans lequel évolue l'agent
     */
    public InformedAgent(Environment environment) {
        super(environment);
    }

    @Override
    protected Deque<Action> planning() {
        Set<State> closed = new HashSet<>();
        Deque<Action> path = new LinkedList<>();
        List<InformedNode> fringe = new ArrayList<>();
        //initialisation de la frontiere
        InformedNode initialState = new InformedNode(this.state, null, 0, 0, heuristic(this.state));
        fringe.add(initialState);

        while (!fringe.isEmpty()){
            InformedNode node = fringe.remove(0);
            //test de présence de l'état dans la liste des états déjà explorés
            if (!closed.contains(node.getState())){
                closed.add(node.getState());
                // imprésion de la position théorique de l'agent pour vérification
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

    /**
     * methode permetant d'étendre la frontier d'exploration
     * @param parent node initial de l'extention
     * @return liste de node
     */
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

    /**
     * calcul du coup d'un node donnée à partir de son parent et de l'action associée
     * @param parent parent du node
     * @param action action associé au node
     * @param node node dont le coût est à déterminer
     * @return valeur du coût
     */
    protected int stepCost(Node parent, Action action, Node node) {
        return 1;
    }

    /**
     * methode d'insertion des nouveaux node dans la frontière
     * @param expend liste des nodes à ajouter
     * @param fringe frontière d'exploration actuel
     * @return la frontière actualisé
     */
    private List<InformedNode> insertAll(List<InformedNode> expend, List<InformedNode> fringe) {
        InformedNode node;
        while (!expend.isEmpty()){
            node = expend.remove(0);
            int i = 0;
            if (fringe.isEmpty()){
                fringe.add(node);
            }else {
                //triage des nodes du coût le plus bas au plus haut
                while ((i < fringe.size()) && (fringe.get(i).getTotalCost() <= node.getTotalCost()) ){
                    i++;
                }
                fringe.add(i, node);
            }
        }
        return fringe;
    }

    /**
     * methode permetant de determiner le coût théorique restant pour arriver au but à partir d'un état donnée
     * @param state état de départ
     * @return coût théorique calculé
     */
    private int heuristic(State state){
        int i = 0;
        int j = 0;
        int score = 0;
        // on parcourt le tableau de piece et pour chaque piece non vide on calcule la distance absolue entre cette case et la position de l'agent
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
