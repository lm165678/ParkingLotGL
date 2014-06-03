/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Geometry;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author fernandogorodscy
 */
public class Area {
    
    private Position pos;
    private float radius;
    private float height;
    
    public Area(Position pos, float radius, float height){
        this.pos = pos;
        this.radius = radius;
        this.height = height;
        
    }
    
    public boolean isOver(Area area){
        if(area.getRadius() < 0 || this.radius < 0 || area.getHeight() < 0 || this.height < 0) {
            return false;
        }
        return pos.distance2D(area.getPos()) < radius + area.getRadius() &&
                pos.getY() <= area.getHeight() &&
                height >= area.getPos().getY();
        
    }
    public boolean isOver(Line line){
        
        return pos.distance2D(line) < radius;
        
    }

    public Position getPos() {
        return pos;
    }

    public float getHeight() {
        return height;
    }
    
    

    public float getRadius() {
        return radius;
    }
    
    public void draw(GLAutoDrawable drawable){
        GL gl = drawable.getGL();
        GLUT glut = new GLUT();
        
        if(radius < 0) return;
        
        gl.glPushMatrix();
            
            float diffuse[] = {0.01f, 0.01f, 0.01f, 1.0f}; 
            float specular[] = {0.0f, 0.0f, 0.0f, 1.0f}; 
            float shininess = 65.0f;
            gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, diffuse, 0); 
            gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specular, 0); 
            gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, shininess);
            
            gl.glTranslatef(pos.getX(), pos.getY(), pos.getZ());
            gl.glScalef(radius, 1, radius);
            glut.glutSolidSphere(1, 8, 8);
        
        gl.glPopMatrix();
        
        
    }
    
    
    
}
