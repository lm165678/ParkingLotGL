/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import Geometry.Area;
import Geometry.Line;
import Geometry.Position;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author fernandogorodscy
 */
public class Parking extends Object{
    
    private final Object floor;
    private final Object stripe;
    private final Object wall;
    private final Object pillar;
    private final Object light;
    private final Object roof;
    private final Object extincteur;
    
    private final ArrayList<Line> borders;
    
    public Parking(float x, float y, float z, float angle){
        super(x, y, z, angle);
        
        this.floor = new Object(0, 0, 0, 0, true, "./data/fefo/Parking/model/floor.obj");
        this.stripe = new Object(0, 1, 0, 0, false, "./data/fefo/Parking/model/faixas.obj");
        this.wall = new Object(0, 0, 0, 0, true, "./data/fefo/Parking/model/muro.obj");
        this.pillar = new Object(0, 0, 0, 0, true, "./data/fefo/Parking/model/collum.obj");
        this.light = new Object(0, 0, 0, 0, true, "./data/fefo/Parking/model/light.obj");
        this.roof = new Object(0, 0, 0, 0, true, "./data/fefo/Parking/model/roof.obj");
        this.extincteur = new Object(0, 0, 0, 0, false, "./data/fefo/Parking/model/extintor.obj", 13, 50, 13);
        
        borders = new ArrayList<Line>(4);
        makeBorders();
        
    }
    
    private void makeBorders(){
        
        Position a, b, c, d, p;
        float cX, cZ;
        
        /*
         * a---------b
         * |         |
         * |         |
         * |         |
         * |         |
         * |         |
         * c---------d
         */
        
        a = new Position(-1200, 0, -1200);
        b = new Position(1200, 0, -1200);
        c = new Position(-1200, 0, 1200);
        d = new Position(1200, 0, 1200);
        
        
        // LEFT BORDER
        borders.add(new Line(a, c));
        // UP BORDER
        borders.add(new Line(a, b));
        // RIGHT BORDER
        borders.add(new Line(b, d));
        // DOWN BORDER
        borders.add(new Line(c, d));
        
        // Pilares
        /*
         *  ----------
         * |          |
         * | 1  2  3  |
         * |          |
         * | 4  5  6  |
         * |          |
         * | 7  8  9  |
         * |          |
         *  ----------
         */
        
        // Pilares
        
        for(int i=-1; i<2; i++)
            for(int j=-1; j<2; j++){
                
                cX = 600*i;
                cZ = 600*j;

                p = new Position(cX, 0, cZ);

                this.pillar.area.add(new Area(p, 30, 280));
            }
        
        
        // Extintores
        /*
         *  ----------
         * |          |
         * 1          2
         * |          |
         * |          |
         * |          |
         * 3          4
         * |          |
         *  ----------
         */
        
        // Extintores
        
        for(int i=0; i<2; i++)
            for(int j=0; j<2; j++){
                
                cX = 1175 - (2350*i);
                cZ = 600 - (1200*j);

                p = new Position(cX, 50, cZ);

                this.pillar.area.add(new Area(p, 10, 280));
            }
        
    }
    
    @Override
    public boolean isOver(Object obj){
        
        for(int i=0; i<borders.size(); i++){
            for(int j=0; j<obj.getArea().size(); j++){
                
                if(obj.getArea().get(j).isOver(borders.get(i))){
                    return true;
                }
                
            }
        }
        
        return this.pillar.isOver(obj) || this.extincteur.isOver(obj);
    }
    
    @Override
    public void drawShadow(GLAutoDrawable drawable){
        this.extincteur.drawShadow(drawable);
    }
    
    @Override
    public void compile(GLAutoDrawable drawable){
        this.floor.compile(drawable);
        this.stripe.compile(drawable);
        this.wall.compile(drawable);
        this.pillar.compile(drawable);
        this.light.compile(drawable);
        this.roof.compile(drawable);
        this.extincteur.compile(drawable);
    }
    
    @Override
    public void draw(GLAutoDrawable drawable, boolean reverse){
        if(!compiled){
                this.compile(drawable, reverse);
        }
        GL gl = drawable.getGL();
        
        if(visible) {
        gl.glPushMatrix();
            gl.glTranslatef(pos.getX(), pos.getY(), pos.getZ());
        
            this.floor.draw(drawable, reverse);
            this.stripe.draw(drawable, reverse);
            this.wall.draw(drawable, reverse);
            this.pillar.draw(drawable, reverse);
            this.light.draw(drawable, reverse);
            this.roof.draw(drawable, reverse);
            this.extincteur.draw(drawable, reverse);
        gl.glPopMatrix();    
        
        
        
        }
    }
    
}
