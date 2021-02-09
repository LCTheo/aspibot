package agent;

import environnement.environment;
import environnement.Room;
import environnement.Position;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @theo
 */
public abstract class Agent implements Runnable {

    protected Sensor sensor;
    protected Effector effector;
    protected State state;
    protected Room[][] goal;
    protected Deque<Action> plan;

    protected Agent(environment environment){
        sensor = new Sensor(environment);
        effector = new Effector(environment);
        updateState();

        int x = (int) (Math.random()*4);
        int y = (int) (Math.random()*4);
        state = new State(updateState(), new Position(x, y));
        effector.move(state.getAgentPosition());

        plan = new LinkedList<>();
        goal = new Room[5][5];
        int i = 0;
        int j = 0;
        while (i < 5){
            while (j < 5){
                goal[i][j] = new Room();
                j++;
            }
            j = 0;
            i++;
        }

    }

    @Override
    public void run() {
        while (true){
            if (!plan.isEmpty()){
                Action nextAction = plan.poll();
                if(nextAction == Action.gather){
                    effector.gather(state.getAgentPosition());
                }
                else if(nextAction == Action.clean){
                    effector.clean(state.getAgentPosition());
                }
                else if(nextAction == Action.moveDown){
                    effector.move(state.getAgentPosition());
                }
                else if(nextAction == Action.moveLeft){
                    effector.move(state.getAgentPosition());
                }
                else if(nextAction == Action.moveRight){
                    effector.move(state.getAgentPosition());
                }
                else if(nextAction == Action.moveHigh){
                    effector.move(state.getAgentPosition());
                }
            }
            else {
                updateState();
                plan = planning();
            }
        }
    }

    private Room[][] updateState(){
        return sensor.scanEnvironment();
    }

    protected boolean goalTest(State state){
        int i = 0;
        int j = 0;
        while (i< 5){
            while (j< 5){
                if (state.getMap()[i][j].isDust()){
                    return false;
                }
                j++;
            }
            j = 0;
            i++;
        }
        return true;
    }


    private void learning(){

    }
    protected abstract List<Node> expend(Node parent, Room[][] goal);

    protected abstract int stepCost(Node parent, Action action, Node node);

    protected abstract List<Pair<Action, State>> successorFn(Room[][] goal, State lastState);

    protected abstract Deque<Action> planning();
}
