/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import Geometry.Area;
import Geometry.Position;
import Reader.JWavefrontModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author fernandogorodscy
 */
public class Object {
    
    protected Position pos;
    protected float angle;
    
    protected float width;
    protected float height;
    protected float depth;
    
    protected ArrayList<Area> area;
    
    protected boolean visible;
    
    private String modelFile;
    private boolean texture;
    
    protected boolean compiled;
    protected JWavefrontModel model;

    public Object(float x, float y, float z, float angle){
        this.pos = new Position(x, y, z);
        this.angle = angle;
        this.texture = false;
        this.visible = true;
        this.modelFile = null;
        
        this.area = new ArrayList<Area>();
        this.area.add(new Area(pos, -1, -1));
        
        this.compiled = false;
    }
    public Object(float x, float y, float z, float angle, boolean texture, String filepath){
        this(x, y, z, angle);
        
        this.texture = texture;
        this.modelFile = filepath;
    }
    public Object(float x, float y, float z, float angle, float width, float height, float depth){
        this(x, y, z, angle);
        
        this.setDimension(width, height, depth);
    }
    public Object(float x, float y, float z, float angle, boolean texture, String filepath,
                                                    float width, float height, float depth){
        this(x, y, z, angle, texture, filepath);
        
        this.setDimension(width, height, depth);
    }
    
    
    
    public void compile(GLAutoDrawable drawable){
        if(!compiled && modelFile != null){
            try {
                model = new JWavefrontModel(new File(modelFile));
                //compiling the model
                if(texture)
                    model.compile(drawable, JWavefrontModel.WF_MATERIAL | JWavefrontModel.WF_TEXTURE | JWavefrontModel.WF_SMOOTH);
                else
                    model.compile(drawable, JWavefrontModel.WF_MATERIAL | JWavefrontModel.WF_SMOOTH);                    
                model.unitize();
                model.facetNormals();
                model.vertexNormals(90);
                
                compiled = true;
            } catch (IOException ex) {
                Logger.getLogger(ex.getMessage() + "\nError while compiling the object");
            }
        }
    }
    public void compile(GLAutoDrawable drawable, boolean reverse){
        if(!compiled && modelFile != null){
            try {
                model = new JWavefrontModel(new File(modelFile));
                //compiling the model
                if(reverse){
                    model.reverseWinding();
                }
                if(texture)
                    model.compile(drawable, JWavefrontModel.WF_MATERIAL | JWavefrontModel.WF_TEXTURE | JWavefrontModel.WF_SMOOTH);
                else
                    model.compile(drawable, JWavefrontModel.WF_MATERIAL | JWavefrontModel.WF_SMOOTH);   
                model.unitize();
                model.facetNormals();
                model.vertexNormals(90);
                
                compiled = true;
            } catch (IOException ex) {
                Logger.getLogger(ex.getMessage() + "\nError while compiling the object");
            }
        }
    }
    private void setDimension(float width, float height, float depth){
        
        this.width = width;
        this.height = height;
        this.depth = depth;
        
        generateArea();
    }
    
    public void generateArea(){
        this.area.clear();
        int nDiv; // Nœmero de divis›es a serem feitas
        Position newPos;
        float radius;
        float x, y, z, d;
        float cos = (float)Math.cos(Math.toRadians(angle));
        float sin = (float)Math.sin(Math.toRadians(angle));
        
        if(width > depth){
            nDiv = (int) (width*2/depth)-1;
            for(int i=0; i<nDiv; i++){
                
                radius = depth/2;
                
                if(i==nDiv-1)
                    d = (radius*nDiv) - width/2;
                else
                    d = -width/2 + radius*(i+1);
                
                x = getX() + d*cos;
                y = getY();
                z = getZ() - d*sin;
                
                newPos = new Position(x, y, z);
                
                this.area.add(new Area(newPos, radius, height));
            }
        }
        else {
            nDiv = (int) (depth*2/width)-1;
            for(int i=0; i<nDiv; i++){
                
                radius = width/2;
                
                if(i==nDiv-1)
                    d = (radius*nDiv) - depth/2;
                else
                    d = -depth/2 + radius*(i+1);
                
                x = getX() + d*sin;
                y = getY();
                z = getZ() + d*cos;
                
                newPos = new Position(x, y, z);

                this.area.add(new Area(newPos, radius, height));
            }
        }
        
        // height not used yet.
    }
    
    public void clearArea(){
        this.area.clear();
        this.area.add(new Area(pos, -1, -1));
    }
    
    public ArrayList<Area> getArea(){
        return area;
    }
    
    public boolean isOver(Object obj){
        
        for(int i=0; i<area.size(); i++){
            for(int j=0; j<obj.getArea().size(); j++){
                if(area.get(i).isOver(obj.getArea().get(j))){
                    return true;
                }
            }
        }
        return false;
    }
    
    public Position leftSide(){
        
        float cos = (float)Math.cos(Math.toRadians(angle));
        float sin = (float)Math.sin(Math.toRadians(angle));
        
        float x, z;
        
        x = this.getX() + (this.width/2 * -cos);
        z = this.getZ() + (this.width/2 * sin);
        
        return new Position(x, this.getY(), z);
        
    }
    public Position rightSide(){
        
        float cos = (float)Math.cos(Math.toRadians(angle));
        float sin = (float)Math.sin(Math.toRadians(angle));
        
        float x, z;
        
        x = this.getX() + (this.width/2 * cos);
        z = this.getZ() + (this.width/2 * -sin);
        
        return new Position(x, this.getY(), z);
        
    }
    public Position frontSide(){
        
        float cos = (float)Math.cos(Math.toRadians(angle));
        float sin = (float)Math.sin(Math.toRadians(angle));
        
        float x, z;
        
        x = this.getX() + (this.depth/2 * -sin);
        z = this.getZ() + (this.depth/2 * -cos);
        
        return new Position(x, this.getY(), z);
        
    }
    public Position backSide(){
        
        float cos = (float)Math.cos(Math.toRadians(angle));
        float sin = (float)Math.sin(Math.toRadians(angle));
        
        float x, z;
        
        x = this.getX() + (this.depth/2 * sin);
        z = this.getZ() + (this.depth/2 * cos);
        
        return new Position(x, this.getY(), z);
        
    }
    
    
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }
    
    public float getX(){
        return this.pos.getX();
    }
    public float getY(){
        return this.pos.getY();
    }
    public float getZ(){
        return this.pos.getZ();
    }

    public void setX(float X) {
        this.pos.setX(X);
    }

    public void setY(float Y) {
        this.pos.setY(Y);
    }

    public void setZ(float Z) {
        this.pos.setZ(Z);
    }
    
    public float getAngle(){
        return this.angle;
    }
    
    public void draw(GLAutoDrawable drawable, boolean reverse){
        if(!compiled){
                this.compile(drawable, reverse);
        }
        GL gl = drawable.getGL();
        
        if(visible) {
        gl.glPushMatrix();
            gl.glTranslatef(pos.getX(), pos.getY(), pos.getZ());
        
            this.model.draw(drawable);
        gl.glPopMatrix();    
        }
    }
    
    public void drawShadow(GLAutoDrawable drawable){
        for(int i=0; i<this.getArea().size(); i++){
            this.getArea().get(i).draw(drawable);
        }
    }
    
}
