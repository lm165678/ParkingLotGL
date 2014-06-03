/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author fernandogorodscy
 */
public class Power extends Object{
    
    private SceneObjects objs;
    private int id;
    
    public Power(float x, float y, float z, float angle, SceneObjects objs){
        super(x, y, z, angle, 2, 2, 2);
        
        this.objs = objs;
        id = getID();
    }
    
    private int getID(){
        return objs.addObj(this);
    }
    
    public void delete(){
        this.visible = false;
        objs.removeObj(id);
    }
    
    private boolean isValid(float x, float y, float z){
        
        if(y > 270 || y < 0)
            return false;
        if(z > 1200 || z < -1200)
            return false;
        if(x > 1200 || x < -1200)
            return false;
                    
        return objs.checkCollision(this) == null;
    }
    
    @Override
    public void draw(GLAutoDrawable drawable, boolean reverse){
        
        GL gl = drawable.getGL();
        GLUT glut = new GLUT();
        
        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));

        if(visible) {

        pos.setZ(pos.getZ() - 15.0f*cos);
        pos.setX(pos.getX() - 15.0f*sin);
            
        this.generateArea();
        
        if(!isValid(getX(), getY(), getZ())){
            this.delete();
        }

        gl.glPushMatrix();
            
            gl.glTranslatef(pos.getX(), pos.getY(), pos.getZ());
            gl.glRotatef(angle, 0, 1, 0);
            gl.glTranslatef(13, 66, -32);
            gl.glScalef(2, 2, 20);

            float diffuse[] = {0.7f, 0.75f, 0.25f, 1.0f}; 
            float specular[] = {0.5f, 0.5f, 0.5f, 1.0f}; 
            float shininess = 127.0f;
            gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, diffuse, 0); 
            gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specular, 0); 
            gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, shininess);
            
            glut.glutSolidSphere(1, 6, 6);
        gl.glPopMatrix();    
        }
    
    }
    
    @Override
    public void drawShadow(GLAutoDrawable drawable){
    }
    
}
