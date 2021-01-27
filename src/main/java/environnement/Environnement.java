package environnement;

public class Environnement implements Runnable{



    /**
     * carte des piece avec les éléments
     */
    private Piece[][] carte;

    /**
     * score de recolte des bijou. +1 si ramassé, -1 si aspiré.
     */
    private int score_bijou;

    /**
     * score de ramassage, = poussiere_ramassé/poussire_total
     */
    private float score_poussiere;

    private int cout_electricite;

    public Environnement() {
    }

    @Override
    public void run() {


        Display.render();
    }


    private void ajouter_bijou(){}

    private void ajouter_poussier(){}

    public Piece[][] getCarte() {
        return carte;
    }

    public void ramassage(){}

    public void nettoyage(){}


}
