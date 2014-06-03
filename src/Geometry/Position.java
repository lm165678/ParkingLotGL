/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Geometry;

/**
 *
 * @author fernandogorodscy
 */
public class Position {
    private float X;
    private float Y;
    private float Z;
    
    public Position(float x, float y, float z){
        this.X = x;
        this.Y = y;
        this.Z = z;
        
    }
    
    public boolean equal(Position pos){
        return (X==pos.X && Y==pos.Y && Z==pos.Z);
    }

    public float getX() {
        return X;
    }

    public float getY() {
        return Y;
    }

    public float getZ() {
        return Z;
    }

    public void setX(float X) {
        this.X = X;
    }

    public void setY(float Y) {
        this.Y = Y;
    }

    public void setZ(float Z) {
        this.Z = Z;
    }
    
    
    
    public float distance(Position p){
        float a, b, c;
        
        a = Math.abs(this.X - p.getX());
        b = Math.abs(this.Y - p.getY());
        c = Math.abs(this.Z - p.getZ());
        
        
        return (float) Math.sqrt( a*a + b*b + c*c );
        
    }
    public float distance2D(Position p){
        float a, b;
        
        a = Math.abs(this.X - p.getX());
        b = Math.abs(this.Z - p.getZ());
        
        return (float) Math.sqrt( a*a + b*b );
        
    }
    
    public float distance2D(Position begin, Position end){
        return (new Line(begin, end).distance2D(this));
    }
    public float distance2D(Line line){
        return line.distance2D(this);
    }
}
