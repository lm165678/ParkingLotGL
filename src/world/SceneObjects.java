/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import Engine.Controller;
import java.util.ArrayList;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author fernandogorodscy
 */
public class SceneObjects {
    
    private ArrayList<Object> objects;
    private Controller controller;
    
    private boolean compiled;
    private Object loader;
    
    public SceneObjects(){
        this.controller = new Controller(this);
        this.objects = new ArrayList<Object>();
        
        this.objects.add(new Robot(0, 2, 40, 180, this));
        this.objects.add(new Parking(0, 0, 0, 0));
        this.objects.add(new Car(0, 0, -780, 0, this, Car.A6_RED));
        this.objects.add(new Car(-120, 2, -50, 180, this, Car.A6_GREEN));
        
        this.controller.addControllables();
        
        this.compiled = false;
        
        this.loader = new Object(0, 0, 0, 0, true, "./data/fefo/Others/model/loading.obj");
        
    }
    
    public Controller getController(){
        return this.controller;
    }
    
    public Object getLoader(){
        return this.loader;
    }
    
    public int addObj(Object obj){
        this.objects.add(obj);
        
        return this.objects.size();
    }
    
    public void removeObj(int id){
        if(id >=0 && id < this.objects.size())
            this.objects.remove(id);
    }
    public void removeObj(Object obj){
        this.objects.remove(obj);
    }
    
    public void compileAll(GLAutoDrawable drawable){
        if(!compiled){
            System.out.println("Loading...");
            
            for(int i=0; i<objects.size(); i++){
                this.objects.get(i).compile(drawable);
            }
            
            System.out.println("Complete.");
            compiled = true;
        }
    }
    
    public void drawAll(GLAutoDrawable drawable){
        for(int i=0; i<objects.size(); i++){
            this.objects.get(i).draw(drawable, false);
            this.objects.get(i).drawShadow(drawable);
        }
    }
    
    public Object checkCollision(Object obj){
        
        for(int i=0; i<objects.size(); i++){
            if(!objects.get(i).equals(controller.getObjPlayer()) && 
                    !objects.get(i).equals(obj) && 
                    (obj.isOver(objects.get(i)) || 
                    objects.get(i).isOver(obj))){
                return objects.get(i);
            }
        }
        
        return null;
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }
    
    
    
}
