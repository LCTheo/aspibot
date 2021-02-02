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
    protected Position goal;
    protected Queue<Action> plan;

    protected Agent(environment environment){
        sensor = new Sensor(environment);
        effector = new Effector(environment);
        updateState();

        int x = (int) (Math.random()*4);
        int y = (int) (Math.random()*4);
        position = new Position(x, y);
        effector.move(position);

        plan = new LinkedList<>();
    }

    @Override
    public void run() {
        while (true){
            if (!plan.isEmpty()){
                Action nextAction = plan.poll();
                if(nextAction == Action.gather){
                    effector.gather(position);
                    map[position.getX()][position.getY()].setJewel(false);
                }
                else if(nextAction == Action.clean){
                    effector.clean(position);
                    map[position.getX()][position.getY()].setDust(false);
                    map[position.getX()][position.getY()].setJewel(false);
                }
                else if(nextAction == Action.moveDown){

                    position.setX(position.getX() + 1);
                    effector.move(position);
                }
                else if(nextAction == Action.moveLeft){
                    position.setX(position.getX() - 1);
                    effector.move(position);
                }
                else if(nextAction == Action.moveRight){
                    position.setY(position.getY() + 1);
                    effector.move(position);
                }
                else if(nextAction == Action.moveHigh){
                    position.setY(position.getY() - 1);
                    effector.move(position);
                }
            }
            else {
                planning();
            }
            if ((position.getX() == goal.getX() && position.getY() == goal.getY()) && !map[goal.getX()][goal.getY()].isDust()){
                goal = selectGoal();
            }
        }
    }

    private void updateState(){
        map = sensor.scanEnvironment();
    }

    private void learning(){

    }

    protected Position selectGoal(){
        return new Position(1, 3);
    }

    protected abstract void planning();
}
