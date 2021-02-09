package agent;

public class Action {

    public static final Action moveHigh = new Action(1);

    public static final Action moveDown = new Action(1);

    public static final Action moveLeft = new Action(1);

    public static final Action moveRight = new Action(1);

    public static final Action gather = new Action(1);

    public static final Action clean = new Action(1);

    private int priority;

    public Action(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean morePriorityThan(Action action){
        return this.priority > action.getPriority();
    }
}
