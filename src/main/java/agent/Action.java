package agent;

public class Action {

    public static final Action moveHigh = new Action(1);

    public static final Action moveDown = new Action(1);

    public static final Action moveLeft = new Action(1);

    public static final Action moveRight = new Action(1);

    public static final Action gather = new Action(1);

    public static final Action clean = new Action(1);

    public static final Action planning = new Action(1);

    public static final Action learning = new Action(1);

    public static final Action updateState = new Action(1);

    private int coef;

    public Action(int coef) {
        this.coef = coef;
    }

    public int getCoef() {
        return coef;
    }

    public void setCoef(int coef) {
        this.coef = coef;
    }
}
