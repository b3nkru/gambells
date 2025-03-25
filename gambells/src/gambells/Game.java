/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gambells;

import java.util.Random;

/**
 *
 * @author benkruseski
 */
public class Game {
    private static final int ROWS = 12;
    private static final double[] PAYOUT = {0.2, 0.5, 1, 2, 5, 10, 5, 2, 1, 0.5, 0.2};
    private Random random = new Random();
    
    public double playGame(double bet) {
        int position = ROWS / 2;
        
        for (int i = 0; i < ROWS; i++) {
            position += random.nextBoolean() ? 1 : -1;
            position = Math.max(0, Math.min(PAYOUT.length - 1, position));
        }
        
        return bet * PAYOUT[position];
    }
}
