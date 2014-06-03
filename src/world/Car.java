/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import Engine.Controllable;
import Geometry.Position;
import Geometry.Area;
import Som.Sounds;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author fernandogorodscy
 */
public class Car extends Object implements Controllable{
    
    private static int DONT_MOVE = 0;
    private static int FORWARD_MOVE = 1;
    private static int BACKWARD_MOVE = 2;
    
    public static int A6_RED = 0;
    public static int A6_GREEN = 1;
    
    private float topSpeed;
    private float handling;
    private float acceleration;
    
    
    private float speed;
    private float frontWheelAngle;
    private boolean active;
    private int moving;
    private Robot driver;
    
    private final SceneObjects otherObjects;
    
    private Object wheel;
    private Object body;
    
    
    public Car(float x, float y, float z, float angle, SceneObjects otherObjects) {
        super(x, y, z, 0, 87, 70, 260);
        
        this.body = new Object(0, 0, 0, 0, true, "./data/fefo/car/Model/car.obj");
        this.wheel = new Object(-37.5f, 30.5f, 68.8f, 0, false, "./data/fefo/car/Model/roda.obj");
        
        this.frontWheelAngle = 0;
        this.angle = angle;
        this.speed = 0;
        this.topSpeed = 4;
        this.handling = 4;
        this.acceleration = 1.1f;
        this.active = false;
        this.compiled = false;
        
        this.otherObjects = otherObjects;
        
        this.generateArea();
    }
    public Car(float x, float y, float z, float angle, SceneObjects otherObjects, int type) {
        this(x, y, z, angle, otherObjects);
        
        switch(type){
            case 0:
                this.body = new Object(0, 0, 0, 0, true, "./data/fefo/car/Model/redcar.obj");
                this.topSpeed = 6;
                this.handling = 3;
                this.acceleration = 1.07f;
                break;
            case 1:
                this.body = new Object(0, 0, 0, 0, true, "./data/fefo/car/Model/car.obj");
                this.topSpeed = 4;
                this.handling = 4;
                this.acceleration = 1.1f;
                break;
            default:
                this.body = new Object(0, 0, 0, 0, true, "./data/fefo/car/Model/car.obj");
                break;
        }
        
    }

    @Override
    public boolean isActive() {
        return active;
    }
    
     @Override
    public void setActive(boolean active) {
        this.active = active;
        if (active)
            Sounds.startEngine();
    }
    
    
    public boolean drive(Robot driver){
        Position posPorta = new Position(
                this.getX()-(width/2*(float)(Math.cos(Math.toRadians(angle)))),
                this.getY(), 
                this.getZ()+(width/2*(float)(Math.sin(Math.toRadians(angle)))));
        
        Area porta = new Area(posPorta, 25, height);
        
        
        for(int i=0; i<driver.getArea().size(); i++){
            if(driver.getArea().get(i).isOver(porta)){
                this.driver = driver;
                otherObjects.getController().setPlayer(this);
                
                return true;
            }
        }
        
        return false;
        
    }
    private void getOut(){
        this.driver.setX(this.getCenterX()-(width*2/3*(float)(Math.cos(Math.toRadians(angle)))));
        this.driver.setZ(this.getCenterZ()+(width*2/3*(float)(Math.sin(Math.toRadians(angle)))));
        this.driver.generateArea();
        
        if(otherObjects.checkCollision(driver) == null){
            this.driver.gainControl(this);
            this.driver.angle = this.angle + 90;
            this.driver.moveFoward();
            Sounds.stopEffects();
            
        }
        else{
            this.driver.pos.setX(this.getCenterX());
            this.driver.pos.setZ(this.getCenterZ());
            this.driver.clearArea();
        }
    }
    
    public void move() {
        
        this.generateArea();
        
        if(otherObjects.checkCollision(this.simulateMove()) != null){
            this.speed = 0;
            return;
        }
        
        double cos = Math.cos(Math.toRadians(angle));
        double sin = Math.sin(Math.toRadians(angle));
        
        // Determina a direcao do movimento
        pos.setZ((float)(pos.getZ() - speed * cos));
        pos.setX((float)(pos.getX() - speed * sin));
        // Seta a posicao do motorista a mesma do carModel
        driver.pos.setX(pos.getX());
        driver.pos.setZ(pos.getZ());
        
        // C‡lculo da rotacao da roda
        this.wheel.angle += -speed*0.5f*Math.PI;
        
        // Retorno da roda fronta a posicao em frente
        this.frontWheelAngle = this.frontWheelAngle/(1.01f + 0.005f*Math.abs(speed));
        
        // C‡lculo da rotacao do carModel
        this.angle += speed*frontWheelAngle/100;
        
        // Atrito do solo
        speed = speed/1.01f;
    }
    
    public Car simulateMove(){
        
        float add = speed;
        float addAngle = speed*frontWheelAngle/100 + 1;
        
        if(speed == 0){
            addAngle = frontWheelAngle/4 + 1;
            add = topSpeed*2;
        }
        
        double cos = Math.cos(Math.toRadians(angle+addAngle));
        double sin = Math.sin(Math.toRadians(angle+addAngle));
        
        float dx = (float)(addAngle*(-cos));
        float dz = (float)(addAngle*sin);
        
        Position simPos;
        
        
        if(moving == FORWARD_MOVE)
            simPos = new Position((float)(pos.getX() - (10+add*2)*sin), 0, (float)(pos.getZ() - (10+add*2)*cos));
        else if(moving == BACKWARD_MOVE)
            simPos = new Position((float)(pos.getX()+dx + (10+add*2)*sin), 0, (float)(pos.getZ()+dz + (10+add*2)*cos));
        else
            return this;
        
        
        Car simCar = new Car(simPos.getX(), simPos.getY(), simPos.getZ(), angle+addAngle, otherObjects);
        simCar.generateArea();
        
        return simCar;
    }
    
    public void accel() {
        
        Sounds.acceleration();
        Sounds.acceleration2();
        
        if(speed < topSpeed) {
            if(speed > 0)
                speed = speed*acceleration + 0.02f;
            else
                speed = speed + 0.2f;
        }
        if(speed > 0)
            this.moving = FORWARD_MOVE;
        else if(speed < 0)
            this.moving = BACKWARD_MOVE;
        else
            this.moving = DONT_MOVE;
    }
    
    public void accelBack() {
        
        Sounds.playRear();
        
        if(speed > -topSpeed/4) {
            speed += -0.2f;
        }
        if(speed > 0)
            this.moving = FORWARD_MOVE;
        else if(speed < 0)
            this.moving = BACKWARD_MOVE;
        else
            this.moving = DONT_MOVE;
    }
    
    public void brake() {
        speed = speed/1.08f;
            
            
        if(speed > 0)
            this.moving = FORWARD_MOVE;
        else if(speed < 0)
            this.moving = BACKWARD_MOVE;
        else
            this.moving = DONT_MOVE;
    }
    
    public void turnLeft() {
        if(frontWheelAngle < 45)
            frontWheelAngle += handling;
    }
    
    public void turnRight() {
        if(frontWheelAngle > -45)
            frontWheelAngle -= handling;
    }
    
    @Override
    public float getX() {
        return this.getCenterX();
    }
    
    @Override
    public float getZ() {
        return this.getCenterZ();
    }
    
    private float getCenterX(){
        
        if(topSpeed == 0){
            return pos.getX();
        }
        
        float ZRotAxis = 63.7f-Math.abs(speed*0.2f*frontWheelAngle/topSpeed);
        float sinAngle = (float)(Math.sin(Math.toRadians(angle)));
        
        return pos.getX() - (ZRotAxis*sinAngle);
    }
    
    private float getCenterZ(){
        
        if(topSpeed == 0){
            return pos.getZ();
        }
        
        float ZRotAxis = 63.7f-Math.abs(speed*0.2f*frontWheelAngle/topSpeed);
        float cosAngle = (float)(Math.cos(Math.toRadians(angle)));
        
        return pos.getZ() + (ZRotAxis + ZRotAxis*(-cosAngle));
    }
    
    @Override
    public void compile(GLAutoDrawable drawable){
        this.body.compile(drawable, false);
        this.wheel.compile(drawable, false);
    }
    
    @Override
    public void draw(GLAutoDrawable drawable, boolean reverse) {
        GL gl = drawable.getGL();
        
        if(this.isVisible()) {
            /// Eixo de rotacao do carModel entre as duas rodas traseiras.
            //  OBS: o eixo de rotacao sobe conforme a velocidade do carModel
            //  para dar efeito de derrape nas rodas traseiras caso a curva
            /// seja muito fechada e a velocidade muito alta.
            float ZRotAxis = 63.7f-Math.abs(speed*0.2f*frontWheelAngle/topSpeed);
            
            if(moving != DONT_MOVE)
                move();
            
            // Carro
            gl.glPushMatrix();
                gl.glTranslatef(pos.getX(), pos.getY(), pos.getZ());

                gl.glTranslatef(0, 0, ZRotAxis);
                gl.glRotatef(angle, 0, 1, 0);
                gl.glTranslatef(0, 0, -ZRotAxis);

                // Carcaca
                gl.glPushMatrix();
                    // Ajusta a posicao da carcaca de modo que o carro
                    // fique completamente assima do ch‹o.
                    gl.glTranslatef(0, 35, 0);
                    
                    body.draw(drawable, false);
                gl.glPopMatrix();

                // Rodas da frente
                gl.glPushMatrix();
                    // Desloca as rodas para a frente.
                    gl.glTranslatef(0, 17.15f, -88.0f);

                    // Roda esquerda
                    gl.glPushMatrix();
                        // Encaixa a roda no seu devido lugar
                        gl.glTranslatef(37.5f, 0, 0);
                    
                        gl.glRotatef(frontWheelAngle, 0, 1, 0);
                        gl.glRotatef(wheel.angle, 1, 0, 0);
                        
                        wheel.draw(drawable, false);
                    gl.glPopMatrix();

                    // Roda direita
                    gl.glPushMatrix();
                        // Encaixa a roda no seu devido lugar
                        gl.glTranslatef(-37.5f, 0, 0);
                        
                        gl.glRotatef(frontWheelAngle+180, 0, 1, 0);
                        gl.glRotatef(-wheel.angle, 1, 0, 0);
                        
                        wheel.draw(drawable, false);
                    gl.glPopMatrix();


                gl.glPopMatrix();

                // Rodas de tr‡s
                gl.glPushMatrix();
                    gl.glTranslatef(0, 17.15f, 44.4f);

                    // Roda esquerda
                    gl.glPushMatrix();
                        gl.glTranslatef(37.5f, 0, 0);
                        gl.glRotatef(wheel.angle, 1, 0, 0);
                        
                        wheel.draw(drawable, false);
                    gl.glPopMatrix();

                    // Roda direita
                    gl.glPushMatrix();
                        gl.glTranslatef(-37.5f, 0, 0);
                        gl.glRotatef(180, 0, 1, 0);
                        gl.glRotatef(-wheel.angle, 1, 0, 0);
                        
                        wheel.draw(drawable, false);
                    gl.glPopMatrix();

                gl.glPopMatrix();

            gl.glPopMatrix();
        
        }
    }

    @Override
    public void moveFoward() {
        this.accel();
    }

    @Override
    public void moveBackward() {
        this.accelBack();
    }

    @Override
    public void moveRight() {
        this.turnRight();
    }

    @Override
    public void moveLeft() {
        this.turnLeft();
    }

    @Override
    public void action() {
        
        if(driver == null || this.speed > 0.1) {
            return;
        }
        
        this.getOut();
    }

    @Override
    public void action2() {
        
        
    }

    @Override
    public boolean gainControl(Object from) {
        if(from instanceof Robot) {
            return this.drive((Robot)from);
        }
        return false;
    }
}
