/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import Engine.Controllable;
import Geometry.Position;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author fernandogorodscy
 */
public class Robot extends Object implements Controllable{
    
    private static int DONT_MOVE = 0;
    private static int FORWARD_MOVE = 1;
    private static int BACKWARD_MOVE = 2;
    private static int RIGHT_MOVE = 3;
    private static int LEFT_MOVE = 4;
    
    //Animation
    private int moveCount;
    private int shootCount;
    
    // Vari‡veis de controle de acao
    private boolean shooting;
    private boolean moveArm;
    private boolean active;
    private boolean animating;
    private int walking;
    private int moving;
    
    private Object head;
    private Object body;
    private Object leftArm;
    private Object rightArm;
    private Object leftLeg;
    private Object rightLeg;
    private Object leftShin;
    private Object rightShin;
    private Object rightHand;
    
    private final SceneObjects otherObjects;
    

    
    public Robot(float x, float y, float z, float angle, SceneObjects otherObjects){
        super(x, y, z, angle, 30, 84, 15);
        
        this.head = new Object(0, 0, 0, 0, false, "./data/fefo/Iron_Man/model/Head.obj");
        this.body = new Object(0, 0, 0, 0, false, "./data/fefo/Iron_Man/model/Body.obj");
        this.leftArm = new Object(0, 0, 0, 0, false, "./data/fefo/Iron_Man/model/LeftArm.obj");
        this.rightArm = new Object(0, 0, 0, 0, false, "./data/fefo/Iron_Man/model/RightArm.obj");
        this.rightHand = new Object(0, 0, 0, 0, false, "./data/fefo/Iron_Man/model/RightHand.obj");
        this.leftLeg = new Object(0, 0, 0, 0, false, "./data/fefo/Iron_Man/model/LeftLeg.obj");
        this.rightLeg = new Object(0, 0, 0, 0, false, "./data/fefo/Iron_Man/model/RightLeg.obj");
        this.leftShin = new Object(0, 0, 0, 0, false, "./data/fefo/Iron_Man/model/LeftShin.obj");
        this.rightShin = new Object(0, 0, 0, 0, false, "./data/fefo/Iron_Man/model/RightShin.obj");
        
        this.otherObjects = otherObjects;
        
        this.moveCount = 0;
        this.shootCount = 1;
        this.shooting = false;
        this.shooting = true;
        this.active = true;
        this.moving = DONT_MOVE;
        this.animating = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public void compile(GLAutoDrawable drawable){
        this.head.compile(drawable, false);
        this.body.compile(drawable, false);
        this.leftArm.compile(drawable, false);
        this.rightArm.compile(drawable, true);
        this.leftLeg.compile(drawable, false);
        this.rightLeg.compile(drawable, true);
        this.leftShin.compile(drawable, false);
        this.rightShin.compile(drawable, true);
    }

    public int getCount() {
        return moveCount;
    }

    private void move() {
        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));
        
        this.generateArea();
        
        if(otherObjects.checkCollision(this.simulateMove(FORWARD_MOVE)) == null){
            pos.setZ(pos.getZ() - 1.1f * cos);
            pos.setX(pos.getX() - 1.1f * sin);
        }
    }
    private void moonWalk() {
        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));
        
        this.generateArea();
        
        if(otherObjects.checkCollision(this.simulateMove(BACKWARD_MOVE)) == null){
            pos.setZ(pos.getZ() + 1.1f * cos);
            pos.setX(pos.getX() + 1.1f * sin);
        }
        
    }
    
    public Robot simulateMove(int mode) {
        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));
        
        Position simPos = this.pos;
        float simAngle = this.angle;
        
        if(mode == FORWARD_MOVE)
            simPos = new Position(pos.getX() - 1.1f * sin, 0, pos.getZ() - 1.1f * cos);
        
        else if(mode == BACKWARD_MOVE)
            simPos = new Position(pos.getX() + 1.1f * sin, 0, pos.getZ() + 1.1f * cos);
        
        if(mode == RIGHT_MOVE)
            simAngle = this.angle - 4;
        
        else if(mode == LEFT_MOVE)
            simAngle = this.angle + 4;
        
        
        
        Robot simRobot = new Robot(simPos.getX(), simPos.getY(), simPos.getZ(), simAngle, otherObjects);
        simRobot.generateArea();
        
        return simRobot;
    }

    private void walk() {
        
        if(moving == FORWARD_MOVE && isActive())
                move();
        if(moving == BACKWARD_MOVE && isActive())
                moonWalk();
        if (moveCount < 8) {
            
            if(moveCount == 1){
                moveArm = shootCount == 1;
                if(this.walking != DONT_MOVE)
                    this.animating = true;
                else
                    this.animating = false;
            }
            
            rightLeg.angle = rightLeg.angle + 2;
            rightShin.angle = rightShin.angle - 4;
            
            leftLeg.angle = leftLeg.angle - 1;

            if(moveArm)
                rightArm.angle = rightArm.angle - 1;
            leftArm.angle = leftArm.angle + 1;
            
            body.angle = body.angle + 0.5f;
            
        } // Perna Direita na frente
        else if (moveCount < 16) {
            rightLeg.angle = rightLeg.angle + 2;
            
            leftLeg.angle = leftLeg.angle - 1;
            leftShin.angle = leftShin.angle - 2;
            
            if(moveArm)
                rightArm.angle = rightArm.angle - 1;
            leftArm.angle = leftArm.angle + 1;
            
            body.angle = body.angle + 0.5f;
            
            pos.setY(pos.getY() - 0.1f);
        }
        else if (moveCount < 24) {
            rightLeg.angle = rightLeg.angle - 2;
            
            leftLeg.angle = leftLeg.angle + 1;
            leftShin.angle = leftShin.angle + 2;
            
            if(moveArm)
                rightArm.angle = rightArm.angle + 1;
            leftArm.angle = leftArm.angle - 1;
            
            body.angle = body.angle - 0.5f;
            
        }
        else if (moveCount < 32) {
            rightLeg.angle = rightLeg.angle - 2;
            rightShin.angle = rightShin.angle + 4;
            
            leftLeg.angle = leftLeg.angle + 1;
            
            if(moveArm)
                rightArm.angle = rightArm.angle + 1;
            leftArm.angle = leftArm.angle - 1;
            
            body.angle = body.angle - 0.5f;
            
            pos.setY(pos.getY() + 0.1f);
        } // Pernas e Bracos alinhados
        else if (moveCount < 40) {
            
            if(moveCount == 32){
                moveArm = shootCount == 1;
                if(this.walking != DONT_MOVE)
                    this.animating = true;
                else
                    this.animating = false;
            }
            
            leftLeg.angle = leftLeg.angle + 2;
            leftShin.angle = leftShin.angle - 4;
            
            rightLeg.angle = rightLeg.angle - 1;

            leftArm.angle = leftArm.angle - 1;
            if(moveArm)
                rightArm.angle = rightArm.angle + 1;
            
            body.angle = body.angle - 0.5f;
            
        }
        else if (moveCount < 48) {
            leftLeg.angle = leftLeg.angle + 2;
            
            rightLeg.angle = rightLeg.angle - 1;
            rightShin.angle = rightShin.angle - 2;
            
            leftArm.angle = leftArm.angle - 1;
            if(moveArm)
                rightArm.angle = rightArm.angle + 1;
            
            body.angle = body.angle - 0.5f;
            
            pos.setY(pos.getY() - 0.1f);
        }
        else if (moveCount < 56) {
            leftLeg.angle = leftLeg.angle - 2;
            
            rightLeg.angle = rightLeg.angle + 1;
            rightShin.angle = rightShin.angle + 2;
            
            leftArm.angle = leftArm.angle + 1;
            if(moveArm)
                rightArm.angle = rightArm.angle - 1;
            
            body.angle = body.angle + 0.5f;
            
        }
        else if (moveCount < 64) {
            leftLeg.angle = leftLeg.angle - 2;
            leftShin.angle = leftShin.angle + 4;
            
            rightLeg.angle = rightLeg.angle + 1;
            
            leftArm.angle = leftArm.angle + 1;
            if(moveArm)
                rightArm.angle = rightArm.angle - 1;
            
            body.angle = body.angle + 0.5f;
            
            pos.setY(pos.getY() + 0.1f);
        }
        else {
            moveCount = -1;
        }

        moveCount++;

    }
    
    private void shootMove(boolean shoot) {
        
        if(shoot){
            if(shootCount < 10){
                rightHand.angle = rightHand.angle + 10;
                rightArm.angle = rightArm.angle + 10;
                
                
                shootCount++;
            } else {
                shooting = false;
            }
        }
        else {
            if(shootCount > 1){
                rightHand.angle = rightHand.angle - 10;
                rightArm.angle = rightArm.angle - 10;
                
                
                shootCount--;
            }
            else {
                shooting = false;
            }
        }


    }
    
    private void shoot(){
        this.shooting = true;
        
        if(shootCount > 8)
            new Power(this.getX(), this.getY(), this.getZ(), this.angle, this.otherObjects);
        
    }

    public void turn(int angle) {
        
        this.generateArea();
        
        if(angle < 0)
            if(otherObjects.checkCollision(this.simulateMove(RIGHT_MOVE)) == null){
                this.angle += angle;
                
                this.animating = true;
                this.moving = this.walking;
            }
                
        if(angle > 0)
            if(otherObjects.checkCollision(this.simulateMove(LEFT_MOVE)) == null){
                this.angle += angle;
            
            this.animating = true;
            this.moving = this.walking;
        }
    }

    @Override
    public void draw(GLAutoDrawable drawable, boolean reverse) {
        GL gl = drawable.getGL();
        
        if(this.isVisible() && this.isActive()) {
            //Robo
            
            if(animating) {
                this.walk();
                walking = DONT_MOVE;
            }
            this.shootMove(shooting);
            
            gl.glPushMatrix();
                
                gl.glTranslatef(pos.getX(), pos.getY()+63, pos.getZ());
                gl.glRotatef(angle, 0, 1, 0);
                
                //Cabeca
                gl.glPushMatrix();
                    head.draw(drawable, false);
                gl.glPopMatrix();

                //Peito
                gl.glPushMatrix();
                    
                    gl.glRotatef(body.angle, 0, 1, 0);
                
                    //Braco esquerdo
                    gl.glPushMatrix();

                        gl.glTranslatef(-2.2f, 4, 0);
                        gl.glRotatef(leftArm.angle, 1, 0, 0);
                        gl.glTranslatef(2.2f, -4, 0);

                        leftArm.draw(drawable, false);
                    gl.glPopMatrix();

                    //Braco direito
                    gl.glPushMatrix();

                        gl.glTranslatef(-2.2f, 4, 0);
                        gl.glRotatef(rightArm.angle, 1, 0, 0);
                        gl.glTranslatef(2.2f, -4, 0);
                        
                        gl.glPushMatrix();
                            
                            gl.glTranslatef(12.2f, -24, 2.2f);
                            gl.glRotatef(rightHand.angle, 0, 0, 1);
                            gl.glRotatef(rightHand.angle, 1, 0, 0);
                            gl.glTranslatef(-12.2f, 24, -2.2f);
                            
                            rightHand.draw(drawable, true);
                        gl.glPopMatrix();
                        
                        rightArm.draw(drawable, true);
                    gl.glPopMatrix();
                
                    body.draw(drawable, false);
                gl.glPopMatrix();
                //Perna esquerda
                gl.glPushMatrix();

                    gl.glTranslatef(0, -20, 1);
                    gl.glRotatef(leftLeg.angle, 1, 0, 0);
                    gl.glTranslatef(0, 20, -1);

                    // Canela Esquerda
                    gl.glPushMatrix();

                        gl.glTranslatef(0, -35, 2);
                        gl.glRotatef(leftShin.angle, 1, 0, 0);
                        gl.glTranslatef(0, 35, -2);

                        leftShin.draw(drawable, false);
                    gl.glPopMatrix();

                    leftLeg.draw(drawable, false);

                gl.glPopMatrix();

                //Perna direita
                gl.glPushMatrix();

                    gl.glTranslatef(0, -20, 1);
                    gl.glRotatef(rightLeg.angle, 1, 0, 0);
                    gl.glTranslatef(0, 20, -1);

                    // Canela direita
                    gl.glPushMatrix();

                        gl.glTranslatef(0, -35, 2);
                        gl.glRotatef(rightShin.angle, 1, 0, 0);
                        gl.glTranslatef(0, 35, -2);

                        rightShin.draw(drawable, true);
                    gl.glPopMatrix();

                    rightLeg.draw(drawable, true);
                gl.glPopMatrix();
        
            gl.glPopMatrix();
            // Fim Robo
        }
    }

    @Override
    public void moveFoward() {
        
        this.moving = FORWARD_MOVE;
        this.walking = FORWARD_MOVE;
        this.animating = true;
        
    }

    @Override
    public void moveBackward() {
        
        this.moving = BACKWARD_MOVE;
        this.walking = BACKWARD_MOVE;
        this.animating = true;
    }

    @Override
    public void moveRight() {
        this.turn(-4);
    }

    @Override
    public void moveLeft() {
        this.turn(4);
    }

    @Override
    public void action() {
        
        Object obj = otherObjects.checkCollision(this.simulateMove(FORWARD_MOVE));
        
        // Se estiver na frente do carro
        if(obj instanceof Controllable){
            
            if(((Controllable)obj).gainControl(this)){
                this.setVisible(false);
                this.clearArea();
            }
        }
    }
    @Override
    public void action2() {
        
        this.shoot();
    }

    @Override
    public boolean gainControl(Object from) {
        
        this.setVisible(true);
        this.setActive(true);
        this.generateArea();
        otherObjects.getController().setPlayer(this);
        
        return true;
    }
}
