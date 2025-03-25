/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gambells;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author benkruseski
 */
public class GUIFrame extends JFrame {
    private JTextField betField;
    private JLabel resultLabel;
    
    public GUIFrame() {
        setTitle("Plinko Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        
        add(new JLabel("Enter your bet: $"));
        betField = new JTextField(10);
        add(betField);

        JButton playButton = new JButton("Bet");
        add(playButton);

        resultLabel = new JLabel("Place your bet and press Bet to play.");
        add(resultLabel);

        playButton.addActionListener(new PlayButtonListener());
    }

    private class PlayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double bet = Double.parseDouble(betField.getText());
                Game game = new Game();
                double payout = game.playGame(bet);
                resultLabel.setText("You won: $" + String.format("%.2f", payout));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid bet amount.");
            }
        }
    }
}
