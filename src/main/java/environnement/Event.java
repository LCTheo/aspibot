package environnement;

public class Event {
    private int id;

    public static final Event move = new Event();

    public static final Event gather = new Event();

    public static final Event clean = new Event();

    public static final Event addJewel = new Event();

    public static final Event addDust = new Event();

    private Event() {

    }
}
