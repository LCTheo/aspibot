package environnement;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

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
    private JScrollPane pan_events;
    private JLabel[][] tab_labels = new JLabel[5][5];
    private int score;
    private String events;
    private JLabel scorelabel;
    private JTextArea eventlabel;
    //private Date date =java.util.Calendar.getInstance().getTime();
    //private LocalDateTime date = LocalDateTime.now();
    //private long date = System.currentTimeMillis() / 1000;

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
                JLabel label = new JLabel("",SwingConstants.CENTER);
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
        this.scorelabel = new JLabel("", SwingConstants.CENTER);
        this.scorelabel.setPreferredSize(new Dimension(100,50));
        this.scorelabel.setBorder(border);
        this.pan_score.add(scorelabel);
        frame.add(this.pan_score, BorderLayout.SOUTH);

        //Ajout de la case "Events"
        this.eventlabel = new JTextArea();
        this.eventlabel.setEditable(false);

        this.eventlabel.setBorder(border);
        this.pan_events = new JScrollPane(this.eventlabel);
        this.pan_events.setPreferredSize(new Dimension(360,650));
        //Autoscroll pour les events
        DefaultCaret caret = (DefaultCaret)this.eventlabel.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        //this.pan_events.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.pan_events.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
     * initialise l'affichage de l'agent
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
            //Affichage de l'évènement
            display.eventlabel.append("Ajout d'une poussière\t"+LocalDateTime.now()+"\n");
            //Mise à jour de l'affichage
            label.setIcon(display.dust);
        } else if (event.equals(Event.addJewel)){
            JLabel label = display.tab_labels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Ajout d'un diamant\t"+LocalDateTime.now()+"\n");
            //Mise à jour de l'affichage
            label.setIcon(display.jewel);
        } else if (event.equals(Event.gather)){
            JLabel label = display.tab_labels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Récupération d'un diamant\t"+LocalDateTime.now()+"\n");
            //Mise à jour du score
            display.score++;
            display.scorelabel.setText(Integer.toString(display.score));
            //Mise à jour de l'affichage
            label.setIcon(null);
        } else if (event.equals(Event.clean)){
            JLabel label = display.tab_labels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Nettoyage d'une poussière\t"+LocalDateTime.now()+"\n");
            //Mise à jour du score
            display.score++;
            display.scorelabel.setText(Integer.toString(display.score));
            //Mise à jour de l'affichage
            label.setIcon(null);
        } else if (event.equals(Event.move)){
            JLabel label = display.tab_labels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Déplacement de l'agent\t"+LocalDateTime.now()+"\n");
            //Mise à jour de l'affichage
            label.setIcon(display.vacuum);
        }

    }
}
