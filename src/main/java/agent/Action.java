package agent;

public class Action {

    public static final Action gather = new Action("gather",5);

    public static final Action clean = new Action("clean",4);

    public static final Action moveHigh = new Action("moveHigh", 3);

    public static final Action moveDown = new Action("moveDown", 2);

    public static final Action moveLeft = new Action("moveLeft", 1);

    public static final Action moveRight = new Action("moveRight",0);

    private final int priority;

    private final String name;

    public Action(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public boolean morePriorityThan(Action action){
        return this.priority > action.getPriority();
    }

    public String getName(){
        return this.name;
    }
}
