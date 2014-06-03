/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import Som.Sounds;
import java.util.ArrayList;
import world.Camera;
import world.Car;
import world.Object;
import world.SceneObjects;

/**
 *
 * @author fernandogorodscy
 */
public class Controller{
    
    //Screen:
    public static final int EMPTY = 0;
    public static final int LOADING = 1;
    public static final int COMPLETE = 2;
    public static final int GAME = 3;
    
    // Objetos da cena
    private SceneObjects objs;
    // Objeto controlado no momento
    private int player;
    // Teclado respons‡vel por ler as teclas
    private Keyboard kb;
    // Objetos que podem ser controlados:
    private ArrayList<Controllable> controllables;
    private Camera camera;
    // Flag que armazena a tela atual
    private int screen;
    
    
    public Controller(SceneObjects objs){
        
        this.objs = objs;
        
        this.controllables = new ArrayList<Controllable>();
        this.camera = new Camera(objs);
        
        this.player = 0;
        
        this.kb = new Keyboard();
        
        this.screen = EMPTY;
    }
    
    public void addControllables(){
        
        for(int i=0; i<objs.getObjects().size(); i++){
            
            if(objs.getObjects().get(i) instanceof Controllable){
                
                this.controllables.add((Controllable)objs.getObjects().get(i));
                
            }
        }
    }

    public Keyboard getKb() {
        return kb;
    }
    
    public int getScreen(){
        return this.screen;
    }
    public void setScreen(int screen){
        this.screen = screen;
    }
    public void nextScreen(){
        this.screen = this.screen + 1;
    }
    
    public Object getObjPlayer() {
        return (Object)this.controllables.get(player);
    }
    
    public Controllable getCtrlPlayer() {
        return this.controllables.get(player);
    }
    
    public void turnOn() {
        this.camera.turnOn((Object)this.getObjPlayer());
    }
    
    public void setPlayer(Controllable player) {
        for(int i=0; i<controllables.size(); i++){
            if(controllables.get(i).equals(player)){
                setPlayer(i);
                return;
            }
        }
    }
    public void setPlayer(int i) {
        deactivePlayer();
        this.player = i;
        activePlayer();
    }
    
    public void activePlayer(){
        this.getCtrlPlayer().setActive(true);
    }
    public void deactivePlayer(){
        this.getCtrlPlayer().setActive(false);
    }
    
    public void action(){
        
        

        if(kb.getW()) {
            this.getCtrlPlayer().moveFoward();
        }
        if(kb.getA()) {
            this.getCtrlPlayer().moveLeft();
        }
        if(kb.getS()) {
            this.getCtrlPlayer().moveBackward();
        }
        if(kb.getD()) {
            this.getCtrlPlayer().moveRight();
        }
        
        
        
        // Move a camera pra cima
        if(kb.getUP()) {
            camera.moveUp();
        }
        // Move a camera pra esquerda
        if(kb.getLEFT()) {
            camera.moveLeft();
        }
        // Move a camera pra baixo
        if(kb.getDOWN()) {
            camera.moveDown();
        }
        // Move a camera pra direita
        if(kb.getRIGHT()) {
            camera.moveRight();
        }
        // Zoom Out
        if(kb.getO()) {
            camera.moveFar();
        }
        // Zoom In
        if(kb.getI()) {
            camera.moveNear();
        }
        // Freia
        if(kb.getB()) {
            if(this.getObjPlayer() instanceof Car){
                ((Car)this.getObjPlayer()).brake();
            }
        }
        if(kb.getL()) {
            if(camera.getViewMode() == Camera.THIRD_VIEW) {
                if(camera.isLock()){
                    camera.unlock();
                }
                else {
                    camera.lock();
                }
            }
        }
        
        if(kb.getSpace()) {
            if(screen == COMPLETE){
                screen = GAME;
                Sounds.stopPreIntro();
                Sounds.playIntro();
            }
            else if(screen == GAME){
                kb.clear();
                this.getCtrlPlayer().action();
            }
        }
        if(kb.getE()){
            this.getCtrlPlayer().action2();
        }
        if(kb.get1()) {
            camera.setViewMode(Camera.FIRST_VIEW);
        }
        if(kb.get3()) {
            camera.setViewMode(Camera.THIRD_VIEW);
        }
    }
    
}
