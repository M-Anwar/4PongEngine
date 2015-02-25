/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.Effects;

/**
 *
 * @author ali.allahverdi
 */
public class GravityParticleBehaviour implements ParticleBehaviour {

    @Override
    public void update(float delta, Particle p) {
        p.velocity.thisScale((float)Math.pow(0.90f, delta));
        p.velocity.thisAdd(0,2);
    }
    
}
