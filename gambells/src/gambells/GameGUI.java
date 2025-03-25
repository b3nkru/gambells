package gambells;

import javax.swing.SwingUtilities;

public class GameGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIFrame().setVisible(true));
    }
}
