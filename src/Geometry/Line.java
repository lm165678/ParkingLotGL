/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Geometry;

/**
 *
 * @author fernandogorodscy
 */
public class Line {
    
    private float a;
    private float b;
    private float c;
    
    public Line(Position begin, Position end){
        
        // Obtendo a equacao da reta a partir de 2 pontos
        a = begin.getZ() - end.getZ();
        b = end.getX() - begin.getX();
        c = begin.getX()*end.getZ() - end.getX()*begin.getZ();
        // Eq: ax + by + c = 0;
    }
    public Line(float xa, float za, float xb, float zb){
        
        this(new Position(xa, 0, za), new Position(xb, 0, zb));
    }
    
    public float distance2D(Position P){
        return (float) (Math.abs(a*P.getX() + b*P.getZ() + c)/Math.sqrt(a*a + b*b));
    }
    
    
}
