package agent;

import environnement.Environnement;
import environnement.Piece;

public class Capteur {

    Environnement environnement;

    public Capteur(Environnement environnement) {
        this.environnement = environnement;
    }

    public Piece[][] scan_environement(){
        return environnement.getCarte();
    }
}
