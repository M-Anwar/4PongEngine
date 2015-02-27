/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine.Effects;

import Engine.Graphics;
import Engine.ImageLoader;
import Engine.Color;

/**
 *
 * @author muhammed.anwar
 */
public class DefaultParticleRenderer implements ParticleRenderer{

    public int color;
    @Override
    public void draw(Particle p, Graphics g) {      
        g.setColor(color);        
       // g.drawImage(p.position.x-p.size/2,p.position.y-p.size/2,ImageLoader.SMOKE1,(int)p.size,(int)p.size);
        g.fillOval(p.position.x-p.size/2, p.position.y-p.size/2, p.size, p.size);
        Color c = new Color(g.getColor());
        g.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),50).getRGB());
        g.fillOval(p.position.x-p.size, p.position.y-p.size, p.size*2, p.size*2);
    }              
    
}
