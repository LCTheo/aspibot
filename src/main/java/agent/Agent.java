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
    protected Deque<Action> plan;
    protected int updateDecompte;
    protected int baseDecompte;

    protected Agent(environment environment){
        sensor = new Sensor(environment);
        effector = new Effector(environment);

        int x = (int) (Math.random()*4);
        int y = (int) (Math.random()*4);
        state = new State(sensor.scanEnvironment(), new Position(x, y));
        state.printMap();
        System.out.println("position :" + state.getAgentPosition().getX() +", "+state.getAgentPosition().getY());
        effector.move(state.getAgentPosition());

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
                    state.setAgentPosition(state.getAgentPosition().getX(),state.getAgentPosition().getY()+1);
                    effector.move(state.getAgentPosition());
                }
                else if(nextAction == Action.moveLeft){
                    state.setAgentPosition(state.getAgentPosition().getX()-1,state.getAgentPosition().getY());
                    effector.move(state.getAgentPosition());
                }
                else if(nextAction == Action.moveRight){
                    state.setAgentPosition(state.getAgentPosition().getX()+1,state.getAgentPosition().getY());
                    effector.move(state.getAgentPosition());
                }
                else if(nextAction == Action.moveHigh){
                    state.setAgentPosition(state.getAgentPosition().getX(),state.getAgentPosition().getY()-1);
                    effector.move(state.getAgentPosition());
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                plan = planning();
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


    private void learning(){

    }
    protected abstract List<Node> expend(Node parent);

    protected abstract int stepCost(Node parent, Action action, Node node);

    protected abstract List<Pair<Action, State>> successorFn(State lastState);

    protected abstract Deque<Action> planning();
}
