package agent;

public class Action {
    // Liste des action possible pour l'agent
    public static final Action gather = new Action("gather");

    public static final Action clean = new Action("clean");

    public static final Action moveHigh = new Action("moveHigh");

    public static final Action moveDown = new Action("moveDown");

    public static final Action moveLeft = new Action("moveLeft");

    public static final Action moveRight = new Action("moveRight");

    //nom de l'action
    private final String name;

    /**
     * constructeur interne d'action
     * @param name nom de l'action
     */
    private Action(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
