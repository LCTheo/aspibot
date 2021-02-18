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
 * @theo
 */
public abstract class Agent implements Runnable {

    protected Sensor sensor;
    protected Effector effector;
    protected State state;
    protected Deque<Action> plan;
    protected int updateDecompte;
    protected int baseDecompte;

    protected Agent(Environment environment){
        sensor = new Sensor(environment);
        effector = new Effector(environment);

        int x = (int) (Math.random()*4);
        int y = (int) (Math.random()*4);
        state = new State(sensor.scanEnvironment(), new Position(x, y));
        //environment.setAgentPosition(new Position(x,y));
        state.printMap();
        System.out.println("position :" + state.getAgentPosition().getX() +", "+state.getAgentPosition().getY());
        effector.move(state.getAgentPosition(), 0);

        plan = new LinkedList<>();
        updateDecompte = 20;
        baseDecompte = 20;
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
            }
        }
    }

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

    private void learning(){

    }

    protected abstract Deque<Action> planning() throws Exception;
}
