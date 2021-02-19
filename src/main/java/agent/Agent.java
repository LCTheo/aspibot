package agent;

import environnement.Environment;
import environnement.Room;
import environnement.Position;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe générale représentant un agent. reprend les éléments comme le BDI et la methode run() pour l'agent.
 * L'implémentation des algorithmes de recherche correspond aux méthodes planning() dans InformedAgent et UninformedAgent.
 */
public abstract class Agent implements Runnable {

    //instance de la classe sensor
    protected Sensor sensor;

    //instance de la classe effector
    protected Effector effector;

    //croyance de l'agent sur l'environnement
    protected State state;

    //pile d'action représentant les intentions de l'agent
    protected Deque<Action> plan;

    //nombre de cycle avant la mise a jour des croyances
    protected int updateDecompte;

    //nombre de cycle entre chaque mise a jour des croyances
    protected int baseDecompte;

    private Pair<Integer, Integer> lastIteration;

    /**
     * Constructeur de la classe Agent
     * @param environment environnement dans lequel évolue l'agent
     */
    protected Agent(Environment environment){
        sensor = new Sensor(environment);
        effector = new Effector(environment);

        int x = (int) (Math.random()*4);
        int y = (int) (Math.random()*4);
        state = new State(sensor.scanEnvironment(), new Position(x, y));
        //state.printMap();
        System.out.println("position :" + state.getAgentPosition().getX() +", "+state.getAgentPosition().getY());
        effector.move(state.getAgentPosition(), 0);
        plan = new LinkedList<>();
        updateDecompte = 20;
        baseDecompte = 20;
        lastIteration = null;
    }

    @Override
    public void run() {
        while (true){
            updateDecompte = updateDecompte -1;
            if (!plan.isEmpty()){
                Action nextAction = plan.pop();
                if(nextAction == Action.gather){
                    Room room = new Room();
                    room.setDust(state.getRoom(state.getAgentPosition().getX(),state.getAgentPosition().getY()).isDust());
                    state.updateRoom(state.getAgentPosition().getX(),state.getAgentPosition().getY(),room);
                    effector.gather(state.getAgentPosition());
                }
                else if(nextAction == Action.clean){
                    Room room = new Room();
                    state.updateRoom(state.getAgentPosition().getX(),state.getAgentPosition().getY(),room);
                    effector.clean(state.getAgentPosition());
                }
                else if(nextAction == Action.moveDown){
                    int id = 1;
                    state.setAgentPosition(state.getAgentPosition().getX(),state.getAgentPosition().getY()+1);
                    effector.move(state.getAgentPosition(), id);

                    }
                    else if(nextAction == Action.moveLeft){
                        int id = 2;
                        state.setAgentPosition(state.getAgentPosition().getX()-1,state.getAgentPosition().getY());
                        effector.move(state.getAgentPosition(), id);

                    }
                    else if(nextAction == Action.moveRight){
                        int id = 3;
                        state.setAgentPosition(state.getAgentPosition().getX()+1,state.getAgentPosition().getY());
                        effector.move(state.getAgentPosition(), id);
                    }
                    else if(nextAction == Action.moveHigh){
                        int id = 4;
                        state.setAgentPosition(state.getAgentPosition().getX(),state.getAgentPosition().getY()-1);
                        effector.move(state.getAgentPosition(), id);
                    }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    plan = planning();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (updateDecompte == 0){
                updateDecompte = baseDecompte;
                updateState();
                learning();
            }
        }
    }

    /**
     * mise a jour des croyances de l'agent
     */
    private void updateState(){
         Room[][] updatedMap = sensor.scanEnvironment();
        int i = 0;
        int j = 0;
        while(i<5){
            while(j<5){
                state.updateRoom(i, j, updatedMap[i][j]);
                j++;
            }
            j=0;
            i++;
        }
    }

    /**
     * test de but pour le porbleme. Correspond au désir de l'agent
     * @param state
     * @return
     */
    protected boolean goalTest(State state){
        int i = 0;
        int j = 0;
        while (i< 5){
            while (j< 5){
                if (state.getMap()[i][j].isDust() || state.getMap()[i][j].isJewel()){
                    return false;
                }
                j++;
            }
            j = 0;
            i++;
        }
        return true;
    }

    /**
     * methode permetant de retourner les actions possible pour l'agent
     * @param lastState état dans lequel ce trouve l'agent
     * @return liste de couple action, état
     */
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

    /**
     * méthode permetant à l'agent d'apprendre la meilleur periode de mise a jour des croyances
     */
    private void learning(){
        if (lastIteration == null){
            lastIteration = new ImmutablePair<>(sensor.getDustScore(), baseDecompte);
        }else {
            Pair<Integer, Integer> iteration = new ImmutablePair<>(sensor.getDustScore(), baseDecompte);
            int dustScore = sensor.getDustScore();
            if (dustScore == 0){
                lastIteration = new ImmutablePair<>(sensor.getDustScore(), baseDecompte);
            }else {
                if(dustScore < lastIteration.getLeft()){
                    if (baseDecompte < lastIteration.getRight()){
                        baseDecompte--;
                    }else {
                        baseDecompte++;
                    }
                }else {
                    if (baseDecompte < lastIteration.getRight()){
                        baseDecompte++;
                    }else {
                        baseDecompte--;
                    }
                }
                lastIteration = iteration;
            }
        }
    }

    /**
     * methode de plannification du plan d'action de l'agent. Retourne une pile d'action assimilable aux intentions de l'agent
     * Son implémentation correspond au algorithme informé et noninformé dans les classe InformedAgent et UNinformedAgent respectivement
     * @return pile d'action
     * @throws Exception
     */
    protected abstract Deque<Action> planning() throws Exception;
}
