/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.Effects;

import Engine.Vector2D;

/**
 *
 * @author ali.allahverdi
 */
public class AttractorParticleBehaviour implements ParticleBehaviour{

    private Vector2D attraction;
    private final float maxSpeed = 50;
    public AttractorParticleBehaviour(Vector2D attraction)
    {
        this.attraction = attraction;
    }
    @Override
    public void update(float delta, Particle p) {
        p.isAlive=true;
        Vector2D attract = attraction.subtract(p.position);        
        p.velocity.thisAdd(attract.normalize().scale(3));
        p.velocity.thisScale((float)Math.pow(0.90f, delta));
        if(p.velocity.length2()>maxSpeed*maxSpeed){p.velocity = p.velocity.normalize().scale(maxSpeed);}
        if(attract.length2()<20*20){p.isAlive=false;}     
    }
    
}
