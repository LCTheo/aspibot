package agent;

import environnement.environment;
import environnement.Room;
import environnement.Position;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @theo
 */
public abstract class Agent implements Runnable {

    protected Sensor sensor;
    protected Effector effector;
    protected Room[][] map;
    protected Position position;

    protected Queue<Action> plan;

    protected Agent(environment environment){
        sensor = new Sensor(environment);
        effector = new Effector(environment);
        updateState();
        position = new Position();
        position.x = (int) (Math.random()*4);
        position.y = (int) (Math.random()*4);
        plan = new LinkedList<>();
    }

    @Override
    public void run() {
        while (true){
            if (!plan.isEmpty()){
                Action nextAction = plan.poll();
                if(nextAction == Action.gather){
                    effector.gather(position);
                    map[position.x][position.y].setJewel(false);
                }
                else if(nextAction == Action.clean){
                    effector.clean(position);
                    map[position.x][position.y].setDust(false);
                    map[position.x][position.y].setJewel(false);
                }
                else if(nextAction == Action.moveDown){
                    position.y = position.y + 1;
                    effector.move(position);
                }
                else if(nextAction == Action.moveLeft){
                    position.x = position.x - 1;
                    effector.move(position);
                }
                else if(nextAction == Action.moveRight){
                    position.y = position.y + 1;
                    effector.move(position);
                }
                else if(nextAction == Action.moveHigh){
                    position.y = position.y - 1;
                    effector.move(position);
                }
            }
            else {
                planning();
            }
        }
    }

    private void updateState(){
        map = sensor.scanEnvironment();
    }

    private void learning(){

    }

    protected abstract void planning();
}
