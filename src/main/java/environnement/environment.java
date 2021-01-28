package environnement;

public class environment implements Runnable{



    /**
     * carte des piece avec les éléments
     */
    private Room[][] map;

    /**
     * score de recolte des bijou. +1 si ramassé, -1 si aspiré.
     */
    private int Jewelscore;

    /**
     * score de ramassage, = poussiere_ramassé/poussire_total
     */
    private float DustScore;

    private int electricityCost;

    public environment() {
    }

    @Override
    public void run() {


       // Display.render();
    }


    private void addJewel(){}

    private void addDust(){}

    public Room[][] getMap() {
        return map;
    }

    public void ramassage(){}

    public void nettoyage(){}


    public int getJewelscore() {
        return Jewelscore;
    }

    public float getDustScore() {
        return DustScore;
    }

    public int getElectricityCost() {
        return electricityCost;
    }
}
