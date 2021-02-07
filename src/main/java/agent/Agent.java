package agent;

import environnement.environment;
import environnement.Room;
import environnement.Position;

import java.util.Deque;
import java.util.LinkedList;

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

    private void learning(){

    }

    protected abstract Deque<Action> planning();
}
