/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gambells;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author benkruseski
 */
public class GUIFrame extends JFrame {
    private JTextField betField;
    private JTextField initialBetField;
    private JTextField nameField;
    private JLabel resultLabel;
    private JLabel balanceLabel;
    private JButton cashoutButton;
    private double balance;
    private String playerName;
    
    public GUIFrame() {
        setTitle("Plinko Game");
        setSize(250, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        

        add(new JLabel("Enter your name: "));
        nameField = new JTextField(10);
        add(nameField);

        add(new JLabel("Enter your initial bet: $"));
        initialBetField = new JTextField(10);
        add(initialBetField);

        JButton startButton = new JButton("Start");
        add(startButton);

        add(new JLabel("Enter your bet: $"));
        betField = new JTextField(10);
        add(betField);

        JButton playButton = new JButton("Bet");
        add(playButton);

        cashoutButton = new JButton("Cash Out");
        add(cashoutButton);

        resultLabel = new JLabel("Enter your name and place your bet and press Bet to play.");
        add(resultLabel);

        balanceLabel = new JLabel("Current balance: $" + String.format("%.2f", balance));
        add(balanceLabel);

        startButton.addActionListener(new StartButtonListener());
        playButton.addActionListener(new PlayButtonListener());
        cashoutButton.addActionListener(new CashoutButtonListener());
    }

    private class PlayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double bet = Double.parseDouble(betField.getText());
                if (bet <= 0 || bet > balance) {
                    resultLabel.setText("Invalid bet amount.");
                    return;
                }

                Game game = new Game();
                double winnings = game.playGame(bet);
                balance += winnings - bet;

                if (balance <= 0) {
                    balance = 0;
                    resultLabel.setText("You are out of money. Game over.");
                } else {
                    resultLabel.setText("You won $" + String.format("%.2f", winnings - bet) + "!");
                }

                balanceLabel.setText("Current balance: $" + String.format("%.2f", balance));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid bet amount.");
            }
        }
    }

    private class CashoutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                saveData();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            int choice = JOptionPane.showConfirmDialog(null, "You cashed out: $" + String.format("%.2f", balance) + "\nWould you like to play again?", "Cash Out", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                initialBetField.setText(String.format("%.2f", balance));
                balanceLabel.setText("Current balance: $" + String.format("%.2f", balance));
                resultLabel.setText("New game started. Place bet!");
            } else {
                System.exit(0);
            }
        }
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                playerName = nameField.getText();
                if (playerName.isEmpty()) {
                    resultLabel.setText("Invalid name.");
                    return;
                }

                balance = Double.parseDouble(initialBetField.getText());
                if (balance <= 0) {
                    resultLabel.setText("Invalid initial balance");
                    return;
                }
                balanceLabel.setText("Current balance: $" + String.format("%.2f", balance));
                resultLabel.setText("Place your bet and press Bet to play.");
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid bet amount.");
            }
        }
    }

    private void saveData() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Game Data");
        int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try (FileWriter writer = new FileWriter(fileToSave)) {
                    writer.write("Player: " + playerName + "\n");
                    writer.write("Balance: $" + String.format("%.2f", balance) + "\n");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving game data: ", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
