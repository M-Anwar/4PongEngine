/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

import Engine.GUI.Component;
import java.util.ArrayList;

/**
 * Every unique game screen should extend this class. Game states should be 
 * switched using only the GameStateManager instance.
 * @author muhammed.anwar
 */
public abstract class GameState 
{
    private GameStateManager gsm;
    private ArrayList<Component> components;
    
    //A temp list to store the components that need to be removed/added in the next update
    //this measure prevents concurrent access issues when trying to remove an object
    //from a list while it is still being iterated over in the update/draw loop
    private ArrayList<Component> removeComponents; 
    private ArrayList<Component> addComponents; 
    
    public GameState() {          
        components = new ArrayList<>();
        removeComponents = new ArrayList<>();
        addComponents = new ArrayList<>();               
    }
    
    /** Called at construction of the GameState, all initialization should go here **/
    public abstract void init();
    /** Called every frame to update any logic
     * @param delta the time passed between the last frame and now**/    
    public abstract void update(float delta);
    /** Called every frame to draw to the screen 
     * @param g the graphics context to draw to*/
    public abstract void draw(Graphics g);
    /** A utility method that can be used to handle input. The user calls this
     method on their own if they want to. Or they can simply leave it empty**/
    public abstract void handleInput();
    
    public void internalUpdate(float delta){
        update(delta);
        for(Component c: components)
            c.update(delta);
        for(Component c: removeComponents){
            components.remove(c);
        }
        for(Component c: addComponents){
            components.add(c);
        }
        addComponents.clear();
        removeComponents.clear();
    }
    public void render(Graphics g){
        draw(g);
        for(Component c: components)
            c.draw(g);
    }
    
    /**
     * Dispose method where you clean up any resources that might be used 
     * by the current game state. The dispose method is called when switching
     * states by the GameStateManager. If overriding, one MUST call super.dispose()
     * or else GUI components will not be disposed of properly.
     */
    public void dispose(){
        for(Component c: components)
            c.dispose();
    }
    /**
     * Switch the current gameState.
     * Will immediately switch the current game state to another one.
     * The parameter should be taken from the list of static values in 
     * GameStateManager. (POSSIBLE CHANGE IN FUTURE)
     * @param state GameStateManager.INTRO etc, the int representing the state.
     */
    public void setState(GameState state)
    {        
        if(this.gsm!=null)
            this.gsm.setState(state);
    }   
    
    /**
     * Set the GameStateManager of the current gamestate.
     * This method should not be called by the user and is called internally
     * to set the manager after state construction.
     * @param g 
     */
    public void setGSM(GameStateManager g){this.gsm =g ;}
    
    /**
     * Add a specific component to the game state
     * @param p the component to add
     */
    public void addComponent(Component p){
        //Add the component to the add list, to be added at the next update.
        addComponents.add(p);}
    
    /**
     * Remove a specific component from the game state
     * @param p the component to remove
     */
    public void removeComponent(Component p){
        //Add the component to the remove list, to be removed at the next update.
        removeComponents.add(p);}
    /**
     * Returns an arraylist of current components in the gameState
     * @return an ArrayList of components
     */
    public ArrayList<Component> getComponents(){return this.components;}
}
