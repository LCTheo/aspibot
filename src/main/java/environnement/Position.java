package environnement;

/**
* Classe Position permettant de connaitre la position en abscisse et ordonnée de chaque case du tableau de l'environnement
* Les positions x et y peuvent être récupérées et éditées par l'environnement pour mettre à jour l'affichage graphique
*/
public class Position {
    
    //Position x de la case
    private int x;
    
    //Position y de la case
    private int y;

    /**
    * Constructeur de la classe Position, qui prend deux entiers, x et y et les associe à ses attributs de position x et y 
    *
    */
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
  
    /**
     * Setter pour la position x
     * @param x : position à éditer 
     */
    public void setX(int x) {
        this.x = x;
    }
    
     /**
     * Setter pour la position y
     * @param y : position à éditer 
     */
    public void setY(int y) {
        this.y = y;
    }
    
     /**
     * Getter pour la position x
     * return x : position à récupérer 
     */
    public int getX() {
        return x;
    }
    
    /**
     * Getter pour la position y
     * return y : position à récupérer 
     */
    public int getY() {
        return y;
    }
}
