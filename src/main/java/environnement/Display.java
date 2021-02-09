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
 * seul l'environnement fait appel a display
 * d'autres methodes peuvent etre créées si besoin
 * pour tester le déplacement de l'agent tu peux le faire bouger arbitraiaremnt en appelant agentMove dans agent.run
 *  pour une représentation photoréaliste cf src/main/ressources/ui.png
 */
public class Display {

    private static Display display = new Display();
    private JPanel panel;
    private JPanel pan_score;
    private JScrollPane pan_events;
    private JPanel[][] tab_panels = new JPanel[5][5];
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
                JPanel panel = new JPanel();
                JLabel label1 = new JLabel("", JLabel.CENTER);
                JLabel label2 = new JLabel("", JLabel.CENTER);
                JLabel label3 = new JLabel("", JLabel.CENTER);
                panel.add(label1);
                panel.add(label2);
                panel.add(label3);
                this.tab_panels[i][j] = panel;
                panel.setSize(50, 50);
                panel.setBackground(Color.gray);
                panel.setBorder(border);
                this.panel.add(panel);
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
    public static void init(environment environment) {
       display.render(Event.initpos,environment.getAgentPosition());

    }

    /**
     * met a jour les éléments de la fenetre
     * @param event
     * @param position
     */
    public static void render(Event event, Position position){
        if(event.equals(Event.initpos)){
            JPanel panel = display.tab_panels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Initialisation de l'agent en: \t"+position.getX()+", "+position.getY()+"\n");
            //Mise à jour de l'affichage
            JLabel label = (JLabel) panel.getComponent(2);
            label.setIcon(display.vacuum);
        } else if(event.equals(Event.addDust)){
            JPanel panel = display.tab_panels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Ajout d'une poussière en : \t"+position.getX()+", "+position.getY()+"\n");
            //Mise à jour de l'affichage
            JLabel label = (JLabel) panel.getComponent(0);
            label.setIcon(display.dust);
        } else if (event.equals(Event.addJewel)){
            JPanel panel = display.tab_panels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Ajout d'un diamant en: \t"+position.getX()+", "+position.getY()+"\n");
            //Mise à jour de l'affichage
            JLabel label = (JLabel) panel.getComponent(1);
            label.setIcon(display.jewel);
        } else if (event.equals(Event.gather)){
            JPanel panel = display.tab_panels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Récupération d'un diamant en: \t"+position.getX()+", "+position.getY()+"\n");
            //Mise à jour du score
            display.score++;
            display.scorelabel.setText(Integer.toString(display.score));
            //Mise à jour de l'affichage
            JLabel label = (JLabel) panel.getComponent(1);
            label.setIcon(null);
        } else if (event.equals(Event.clean)){
            JPanel panel = display.tab_panels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Nettoyage d'une poussière en: \t"+position.getX()+", "+position.getY()+"\n");
            //Mise à jour du score
            display.score++;
            display.scorelabel.setText(Integer.toString(display.score));
            //Mise à jour de l'affichage
            JLabel label = (JLabel) panel.getComponent(0);
            label.setIcon(null);
        } else if (event.equals(Event.move)){
            JPanel panel = display.tab_panels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Déplacement de l'agent en: \t"+position.getX()+", "+position.getY()+"\n");
            //Mise à jour de l'affichage
            JLabel label = (JLabel) panel.getComponent(2);
            label.setIcon(display.vacuum);
        }

    }
}
