/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import world.Object;

/**
 *
 * @author fernandogorodscy
 */
public interface Controllable{
    
    public boolean isActive();
    public void setActive(boolean active);
    
    public boolean gainControl(Object from);
    
    public void moveFoward();
    public void moveBackward();
    public void moveRight();
    public void moveLeft();
    public void action();
    public void action2();
}
