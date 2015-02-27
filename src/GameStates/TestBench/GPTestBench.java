/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameStates.TestBench;

import Engine.GameState;
import Engine.GameStateManager;
import Engine.Graphics;
import Engine.Color;
/**
 *
 * @author Muhammed Anwar
 */
public class GPTestBench extends GameState{
    

    @Override
    public void init() {
        
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE.getRGB());
        g.drawString("Hello World", 10, 40);
    }

    @Override
    public void handleInput() {
    }
    
}
