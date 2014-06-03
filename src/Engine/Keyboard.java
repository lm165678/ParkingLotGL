/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author fernandogorodscy
 */
public class Keyboard extends KeyAdapter {
    private static int key_w     = 0;
    private static int key_a     = 1;
    private static int key_s     = 2;
    private static int key_d     = 3;
    
    private static int key_up    = 4;
    private static int key_left  = 5;
    private static int key_down  = 6;
    private static int key_right = 7;
    
    private static int key_i     = 8;
    private static int key_o     = 9;
    
    private static int key_b     = 10;
    private static int key_l     = 11;
    
    private static int key_e     = 12;
    
    private static int key_space = 13;
    
    private static int key_1     = 14;
    private static int key_3     = 15;
    
    private ArrayList<Boolean> keys;
    
    private boolean locked;
    
    public Keyboard() {
        this.keys = new ArrayList<Boolean>(16);
        this.locked = false;
        
        for(int i=0; i<16; i++){
            keys.add(false);
        }
        
    }
    
    public void clear(){
        keys.clear();
        for(int i=0; i<16; i++){
            keys.add(false);
        }
    }
    
    
    public boolean getW(){
        return this.keys.get(key_w);
    }
    
    public boolean getA(){
        return this.keys.get(key_a);
    }
    
    public boolean getS(){
        return this.keys.get(key_s);
    }
    
    public boolean getD(){
        return this.keys.get(key_d);
    }
    
    
    public boolean getUP(){
        return this.keys.get(key_up);
    }
    
    public boolean getLEFT(){
        return this.keys.get(key_left);
    }
    
    public boolean getDOWN(){
        return this.keys.get(key_down);
    }
    
    public boolean getRIGHT(){
        return this.keys.get(key_right);
    }
    
    
    public boolean getI(){
        return this.keys.get(key_i);
    }
    
    public boolean getO(){
        return this.keys.get(key_o);
    }
    
    public boolean getB(){
        return this.keys.get(key_b);
    }
    public boolean getL(){
        return this.keys.get(key_l);
    }
    public boolean getE(){
        return this.keys.get(key_e);
    }
    
    public boolean getSpace(){
        return this.keys.get(key_space);
    }
    
    public boolean get1() {
        return this.keys.get(key_1);
    }

    public boolean get3() {
        return this.keys.get(key_3);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_I://faz zoom-in
                this.keys.set(key_i, true);
                
                break;
            case KeyEvent.VK_O://faz zoom-out
                this.keys.set(key_o, true);
                
                break;
            case KeyEvent.VK_UP://gira sobre o eixo-x
                this.keys.set(key_up, true);
                
                break;
            case KeyEvent.VK_DOWN://gira sobre o eixo-x
                this.keys.set(key_down, true);
                
                break;
            case KeyEvent.VK_LEFT://gira sobre o eixo-y
                this.keys.set(key_left, true);
                
                break;
            case KeyEvent.VK_RIGHT://gira sobre o eixo-y
                this.keys.set(key_right, true);
                
                break;
            case KeyEvent.VK_D:
                this.keys.set(key_d, true);
                
                break;
            case KeyEvent.VK_A:
                this.keys.set(key_a, true);
                
                break;
            case KeyEvent.VK_S:
                this.keys.set(key_s, true);
                
                break;
            case KeyEvent.VK_W:
                this.keys.set(key_w, true);
                
                break;
                
            case KeyEvent.VK_B:
                this.keys.set(key_b, true);
                
                break;
                
            case KeyEvent.VK_L:
                this.keys.set(key_l, true);
                
                break;
                
            case KeyEvent.VK_E:
                this.keys.set(key_e, true);
                
                break;
                
            case KeyEvent.VK_SPACE:
                if(locked) return;
                this.keys.set(key_space, true);
                
                break;
                
            case KeyEvent.VK_1:
                this.keys.set(key_1, true);
                
                break;
                
            case KeyEvent.VK_3:
                this.keys.set(key_3, true);
                
                break;
        }
        
        this.locked = true;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        
        this.locked = false;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_I://faz zoom-in
                this.keys.set(key_i, false);
                
                break;
            case KeyEvent.VK_O://faz zoom-out
                this.keys.set(key_o, false);
                
                break;
            case KeyEvent.VK_UP://gira sobre o eixo-x
                this.keys.set(key_up, false);
                 
               break;
            case KeyEvent.VK_DOWN://gira sobre o eixo-x
                this.keys.set(key_down, false);
                
                break;
            case KeyEvent.VK_LEFT://gira sobre o eixo-y
                this.keys.set(key_left, false);
                
                break;
            case KeyEvent.VK_RIGHT://gira sobre o eixo-y
                this.keys.set(key_right, false);
                
                break;
            case KeyEvent.VK_D:
                this.keys.set(key_d, false);
                
                break;
            case KeyEvent.VK_A:
                this.keys.set(key_a, false);
                
                break;
            case KeyEvent.VK_S:
                this.keys.set(key_s, false);
                
                break;
            case KeyEvent.VK_W:
                this.keys.set(key_w, false);
                
                break;
                
            case KeyEvent.VK_B:
                this.keys.set(key_b, false);
                
                break;
                
            case KeyEvent.VK_L:
                this.keys.set(key_l, false);
                
                break;
                
            case KeyEvent.VK_E:
                this.keys.set(key_e, false);
                
                break;
                
            case KeyEvent.VK_SPACE:
                this.keys.set(key_space, false);
                
                break;
                
            case KeyEvent.VK_1:
                this.keys.set(key_1, false);
                
                break;
                    
            case KeyEvent.VK_3:
                this.keys.set(key_3, false);
                
                break;
        }
    }
}
