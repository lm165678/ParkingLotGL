/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package debug;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author fernandogorodscy
 */
public class Axis {
    
    public Axis(){
        
    }
    
    public void draw(GLAutoDrawable drawable) {
        
        GL gl = drawable.getGL();
        GLUT glut = new GLUT();
        

        gl.glPushMatrix();
            gl.glScalef(200, 0.3f, 0.3f);
            gl.glColor3f(1, 0, 0);
            glut.glutSolidCube(1.0f);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
            gl.glScalef(0.3f, 200, 0.3f);
            gl.glColor3f(0, 1, 0);
            glut.glutSolidCube(1.0f);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
            gl.glScalef(0.3f, 0.3f, 200);
            gl.glColor3f(0, 0, 1);
            glut.glutSolidCube(1.0f);
        gl.glPopMatrix();
        
    }
    
}
