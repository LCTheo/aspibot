package environnement;

public class Room {

    private boolean dust;
    private boolean Jewel;

    public Room() {
        dust = false;
        Jewel = false;
    }


    public boolean isDust() {
        return dust;
    }

    public void setDust(boolean dust) {
        this.dust = dust;
    }

    public boolean isJewel() {
        return Jewel;
    }

    public void setJewel(boolean bijou) {
        this.Jewel = bijou;
    }
}
