package environnement;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

/**
 * @henri
 * classe permettant l'affichage de l'environement et de l'agent pour suivre les actions réalisé.
 * des indicateurs comme les scores de poussiere et bijoux seront aussi affichés
 * seul l'environement fait appel a display
 * d'autres methodes peuvent etre créées si besoin
 * pour tester le déplacement de l'agent tu peux le faire bouger arbitraiaremnt en appelant agentMove dans agent.run
 *  pour une représentation photoréaliste cf src/main/ressources/ui.png
 */
public class Display {

    private static Display display = new Display();
    private JPanel panel;
    private JPanel pan_score;
    private JPanel pan_events;
    private JLabel[][] tab_labels = new JLabel[5][5];

    //Images pour le render
    private ImageIcon dust = new ImageIcon("src/main/resources/dust.png");
    private ImageIcon jewel = new ImageIcon("src/main/resources/jewel.png");
    private ImageIcon vacuum = new ImageIcon("src/main/resources/aspibot.png");

    private Display() {
        JFrame frame = new JFrame();
        this.panel = new JPanel();
        Border border = BorderFactory.createLineBorder(Color.black,2);

        //Création du tableau de cases
        int i = 0;
        int j = 0;
        while(i<5){
            while(j<5){
                JLabel label = new JLabel();
                this.tab_labels[i][j] = label;
                label.setSize(50, 50);
                label.setBackground(Color.gray);
                label.setBorder(border);
                this.panel.add(label);
                j++;
            }
            j=0;
            i++;
        }
        //Affichage des cases selon une grille de 5 par 5
        this.panel.setLayout(new GridLayout(5,5));
        frame.add(this.panel);

        //Ajout de la case "Score"
        this.pan_score = new JPanel();
        JLabel score = new JLabel("score");
        score.setPreferredSize(new Dimension(100,50));
        score.setBorder(border);
        this.pan_score.add(score);
        frame.add(this.pan_score, BorderLayout.SOUTH);

        //Ajout de la case "Events"
        this.pan_events = new JPanel();
        JLabel event = new JLabel("events");
        event.setPreferredSize(new Dimension(200,650));
        event.setBorder(border);
        this.pan_events.add(event);
        frame.add(this.pan_events, BorderLayout.EAST);

        //Caractéristiques de la fenêtre
        frame.setPreferredSize(new Dimension(1200, 800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Aspibot");
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * initialise l'affichage et lance la fenetre
     */
    public static void init() {
        //display = new Display();

        //Génération de l'aspibot sur une case aléatoire
        JLabel label = display.tab_labels[(int) (Math.random()*(4-0+1)+0)][(int) (Math.random()*(4-0+1)+0)];
        label.setIcon(display.vacuum);

    }

    /**
     * met a jour les éléments de la fenetre
     * @param event
     * @param position
     */
    public static void render(Event event, Position position){

        if(event.equals(Event.addDust)){
            JLabel label = display.tab_labels[position.getX()][position.getY()];
            label.setIcon(display.dust);
        } else if (event.equals(Event.addJewel)){
            JLabel label = display.tab_labels[position.getX()][position.getY()];
            label.setIcon(display.jewel);
        } else if (event.equals(Event.gather)){
            JLabel label = display.tab_labels[position.getX()][position.getY()];
            label.setIcon(null);
        } else if (event.equals(Event.clean)){
            JLabel label = display.tab_labels[position.getX()][position.getY()];
            label.setIcon(null);
        } else if (event.equals(Event.move)){
            JLabel label = display.tab_labels[position.getX()][position.getY()];
            label.setIcon(display.vacuum);
        }

    }
}
