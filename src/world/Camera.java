/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import javax.media.opengl.glu.GLU;


/**
 *
 * @author fernandogorodscy
 */
public class Camera extends Object{
    
    public static final int FIRST_VIEW = 1;
    public static final int THIRD_VIEW = 3;
    
    private double alef;
    private double bet;
    private double radius;
    
    private double lookAtX;
    private double lookAtY;
    private double lookAtZ;
    
    private double viewUpX;
    private double viewUpY;
    private double viewUpZ;
    
    private int viewMode;
    private boolean lockCam;
    private Object target;
    
    private GLU glu = new GLU();
    private SceneObjects objs;
    
    public Camera(SceneObjects objs){
        super(0, 0, 500, 0, 10, 10, 10);
        
        this.alef = 0;
        this.bet = 0;
        this.radius = 500;
        
        this.viewUpX = 0;
        this.viewUpY = 1;
        this.viewUpZ = 0;
        
        this.lookAtX = 0;
        this.lookAtY = 50;
        this.lookAtZ = 0;
        
        this.viewMode = THIRD_VIEW;
        this.lockCam = false;
        this.target = null;
        
        this.glu = new GLU();
        this.objs = objs;
    }
    
    public void moveUp(){
        if(alef < 80 && getY() < 270) {
            alef = alef + 2;
        }
    }
    public void moveDown(){
        if(alef > -80 && getY() > 10) {
            alef = alef - 2;
        }
    }
    public void moveRight(){
        if(bet > 360) {
            bet = 0;
        }
        if(getX() < 1200 && getX() > -1200 && getZ() < 1190 && getZ() > -1190)
            bet = bet + 2;
    }
    public void moveLeft(){
        if(bet < 0) {
            bet = 360;
        }
        if(getX() < 1200 && getX() > -1190 && getZ() < 1200 && getZ() > -1190)
            bet = bet - 2;
    }
    public void moveNear(){
        if(radius > 20) {
            radius = radius * 0.95;
        }
    }
    public void moveFar(){
        if(radius < 1500 && 
                getX() < 1190 && getX() > -1190 &&
                getY() <  270 && getY() >    10 &&
                getZ() < 1190 && getZ() > -1190) {
            radius = radius * 1.05;
        }
    }
    
    private boolean isValid(float x, float y, float z){
        
        if(y > 270 || y < 10)
            return false;
        if(z > 1200 || z < -1200)
            return false;
        if(x > 1200 || x < -1200)
            return false;
                    
        return true;
    }
    
    private boolean isValid(double alef, double bet, double radius){
        double senAlef = Math.sin(Math.toRadians(alef));
        double cosAlef = Math.cos(Math.toRadians(alef));
        double senBet = Math.sin(Math.toRadians(bet));
        double cosBet = Math.cos(Math.toRadians(bet));
        
        float x = (float) (radius*senBet*cosAlef + lookAtX);
        float y = (float) (radius*senAlef        + lookAtY);
        float z = (float) (radius*cosAlef*cosBet + lookAtZ);
        
        return isValid(x, y, z);
    }
    
    private void validatePosition(){
        
        while(!isValid(alef, bet, radius)){
            if(isValid(alef, bet+5, radius)){
                bet += 5;
            }
            else if(isValid(alef, bet-5, radius)){
                bet -= 5;
            }
            else if(isValid(alef+5, bet, radius)){
                alef += 5;
            }
            else if(isValid(alef+5, bet, radius)){
                alef -= 5;
            }
            else {
                radius = radius*0.9999;
            }
        }
    }
    
    private void moveCam(double alef, double bet, double radius){
        
        validatePosition();
        
        double senAlef = Math.sin(Math.toRadians(alef));
        double cosAlef = Math.cos(Math.toRadians(alef));
        double senBet = Math.sin(Math.toRadians(bet));
        double cosBet = Math.cos(Math.toRadians(bet));
        
        pos.setX((float) (radius*senBet*cosAlef + lookAtX));
        pos.setY((float) (radius*senAlef        + lookAtY));
        pos.setZ((float) (radius*cosAlef*cosBet + lookAtZ));
    }
    
    private void moveCam(Object target){
        bet = target.getAngle();
        
        double senBet = Math.sin(Math.toRadians(bet));
        double cosBet = Math.cos(Math.toRadians(bet));
        
        pos.setX(target.getX());
        pos.setY(target.getY());
        pos.setZ(target.getZ());
        
        if(target instanceof Robot) {
            Robot robo = (Robot) target;
        
            float senMove = (float) Math.sin(Math.toRadians(robo.getCount()*360.0/64.0));
            pos.setY(pos.getY() + target.height*0.92f + senMove);
            pos.setX(pos.getX() - target.depth/2*(float)senBet);
            pos.setZ(pos.getZ() - target.depth/2*(float)cosBet);
        }
        else if(target instanceof Car) {
            
            pos.setY(pos.getY() + target.height*0.7f);
            pos.setX(pos.getX() - target.depth*0.4f*(float)senBet);
            pos.setZ(pos.getZ() - target.depth*0.4f*(float)cosBet);
        }
    }
    
    public boolean isLock(){
        return this.lockCam;
    }
    
    public void lock(){
        this.lockCam = true;
    }
    
    public void unlock(){
        this.lockCam = false;
    }
    
    public void turnOn(Object player){
        
        this.target = player;
        
        if(this.viewMode == FIRST_VIEW) {
            this.look1stView();
        }
        else if(this.viewMode == THIRD_VIEW) {
            this.look3rdView();
        }
    }
    
    private void look1stView(){
        
        double senBet = Math.sin(Math.toRadians(bet));
        double cosBet = Math.cos(Math.toRadians(bet));
        
        moveCam(this.target);
        
        this.lookAtX = pos.getX() - 50*senBet;
        this.lookAtY = pos.getY() + alef/2;
        this.lookAtZ = pos.getZ() - 50*cosBet;
        
        this.viewUpX = 0;
        this.viewUpY = 1;
        this.viewUpZ = 0;
        
        glu.gluLookAt(pos.getX(), pos.getY(), pos.getZ(),
                        lookAtX, lookAtY, lookAtZ, 
                        viewUpX, viewUpY, viewUpZ);
    }
    
    private void look3rdView(){
        
        if(lockCam){
            bet = target.getAngle();
        }
        
        moveCam(alef, bet, radius);
        
        this.lookAtX = target.getX();
        this.lookAtY = target.getY() + target.height*0.92f;
        this.lookAtZ = target.getZ();
        
        this.viewUpX = 0;
        this.viewUpY = 1;
        this.viewUpZ = 0;
        
        glu.gluLookAt(pos.getX(), pos.getY(), pos.getZ(),
                        lookAtX, lookAtY, lookAtZ, 
                        viewUpX, viewUpY, viewUpZ);
        
        Object obj = objs.checkCollision(this);
        if(obj != null){
            obj.setVisible(true);
        }
        
        this.generateArea();
        
        obj = objs.checkCollision(this);
        if(obj != null && !(obj instanceof Parking)){
            obj.setVisible(false);
        }
    }
    
    
    public void setViewMode(int mode){
        this.viewMode = mode;
    }
    public void setViewMode(int mode, Object obj){
        this.viewMode = mode;
        
        if(mode == THIRD_VIEW) {
            this.alef = 0;
            this.bet = 0;
            this.radius = 500;
        }
        else if(mode == FIRST_VIEW) {
            this.alef = 0;
            this.bet = 0;
            this.radius = 500;
        }
    }
    
    public int getViewMode(){
        return this.viewMode;
    }
    
}
