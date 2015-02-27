/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

import G4Pong.GamePanel;
import GameStates.TestBench.GPTestBench;
import GameStates.TestBench.MTestBench;

/**
 * A manager class which delegates the act of drawing and updating the screen.
 * The manager controls the current state of the program and can switch between
 * different states.
 * 
 * TODO: Refactoring to allow users to register their own GameState classes
 * instead of having to change it manually in the actual Engine package.
 * @author muhammed.anwar
 */
public class GameStateManager 
{
    private GameState gameState;      
   
    public GameStateManager(GameState initState){
       setState(initState);
    }
    public GameStateManager() {
        this(null);
    }
    
    public void setState(GameState state) {         
            if(gameState!=null)
            {
                gameState.dispose();    
            }            
            if(state!=null){
                state.setGSM(this);
                state.init();              
            }            
            gameState = state;           
            
    }

    public void update(float delta) {
            if(gameState != null) gameState.internalUpdate(delta);
    }

    public void draw(Graphics g) {
            if(gameState != null) gameState.render(g);
            else {
                    g.setColor(Color.BLACK.getRGB());
                    g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
            }
    }
    public GameState getCurrentState(){return gameState;}
}
