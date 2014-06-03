package world;

import Engine.Controller;
import Som.Sounds;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import debug.Axis;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

public class Scene implements GLEventListener {
    
    private int height;
    private int width;
    private SceneObjects sobj;
    
    public Scene() {
        this.sobj = new SceneObjects();
    }

    public static void main(String[] args) {
        
        GLCapabilities caps = new GLCapabilities();
        caps.setHardwareAccelerated(true);
        caps.setDoubleBuffered(true);
        
        GLCanvas canvas = new GLCanvas(caps);
        Scene scene = new Scene();
        canvas.addGLEventListener(scene);
        
        Animator a = new FPSAnimator(canvas, 30);
        
        
        //cria uma janela e adiciona o painel
        JFrame frame = new JFrame("GTA V - Stark Industries Parking Lot");
        canvas.addKeyListener(scene.sobj.getController().getKb());
        frame.getContentPane().add(canvas);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        a.start();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        
        this.height = height;
        this.width = width;
        
        if(height < 100)
            this.height = 100;
        
        if(width < 100)
            this.width = 100;
        
            
        
        GL gl = drawable.getGL();
        GLU glu = new GLU();
        
        gl.glClearColor(0.1f, 0.1f, 0.1f, 0); //define a cor de fundo
        gl.glEnable(GL.GL_DEPTH_TEST); //remocao de superfície oculta
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glCullFace(GL.GL_BACK);
        gl.glShadeModel(GL.GL_SMOOTH);
        

        gl.glMatrixMode(GL.GL_PROJECTION); //define que a matrix Ž a de projecao
        gl.glLoadIdentity(); //carrega a matrix de identidade
        
        if(width >= height){
            glu.gluPerspective(60, (double)width/height, 0.1, 5000);
        }
        else {
            if((double)height/width < 2)
                glu.gluPerspective((60.0f*height)/width, (double)width/height, 0.1, 5000);
            else
                glu.gluPerspective(120, (double)width/height, 0.1, 5000);
        }
        
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        //limpa o buffer
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        //define que a matrix Ž a de modelo
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity(); //carrega matrix identidade
        
        // Tela de Loading:
        gl.glPushMatrix();
            Controller c = this.sobj.getController();
            
            glu.gluLookAt(0, 1000, 0, 
                        0, 0, 0, 
                        0, 0, 1);

            if(c.getScreen() == Controller.EMPTY){
                Sounds.playPreIntro();
                this.sobj.getLoader().draw(drawable, false);
                gl.glFlush();
                c.nextScreen();
                return;
            }
            else if(c.getScreen() == Controller.LOADING){
                this.sobj.getLoader().draw(drawable, false);
                this.sobj.compileAll(drawable);
                c.nextScreen();
                return;
            }
            else if(c.getScreen() == Controller.COMPLETE){
                this.sobj.getLoader().draw(drawable, false);
                c.action();
                return;
            }
        gl.glPopMatrix();
        
        gl.glPushMatrix(); 
            
            // Liga a camera principal
            this.sobj.getController().turnOn();
        
            gl.glViewport(0, 0, width, height);
            
            this.lighting(drawable);
            
            this.drawScene(drawable);
        gl.glPopMatrix();
        
        gl.glPushMatrix(); // Mini-map
            
            //Posicionamento da Camera do mini-map
            glu.gluLookAt(this.sobj.getController().getObjPlayer().pos.getX(), 1000+this.sobj.getController().getObjPlayer().pos.getY(), this.sobj.getController().getObjPlayer().pos.getZ(), 
                        this.sobj.getController().getObjPlayer().pos.getX(), this.sobj.getController().getObjPlayer().pos.getY(), this.sobj.getController().getObjPlayer().pos.getZ(), 
                        0, 0, -1);
            
            gl.glViewport(0, height*80/100, width*20/100-20, height*20/100-20);
            
            this.lighting(drawable);
            
            this.drawScene(drawable);
        gl.glPopMatrix();
        
        this.sobj.getController().action();
        
        
        
        //forma o desenho das primitivas
        gl.glFlush();
    }
    
    public void drawScene(GLAutoDrawable drawable) {
//        Axis axis = new Axis();
//        axis.draw(drawable);
        this.sobj.drawAll(drawable);
    }
    
    private void lighting(GLAutoDrawable drawable) {

        GL gl = drawable.getGL();

        float[] black = {0.0f, 0.0f, 0.0f, 1.0f};
        float[] diffuse = new float[]{0.7f, 0.7f, 0.7f, 1.0f};
        float[] gray = new float[]{0.2f, 0.2f, 0.2f, 1.0f};
        float[] specular = new float[]{0.7f, 0.7f, 0.7f, 1.0f};
        float[] position0 = new float[]{300, 240, 300, 1.0f};
        float[] position1 = new float[]{-300, 240, 300, 1.0f};
        float[] position2 = new float[]{300, 0, -300, 1.0f};
        float[] position3 = new float[]{-300, 0, -300, 1.0f};




        // Define os parametros da luz de numero 0
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, gray, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position0, 0);
        gl.glLightf(GL.GL_LIGHT0, GL.GL_CONSTANT_ATTENUATION, 1.0f);
        gl.glLightf(GL.GL_LIGHT0, GL.GL_LINEAR_ATTENUATION, 0.0001f);

        // Define os parametros da luz de numero 1
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, gray, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, specular, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, position1, 0);
        gl.glLightf(GL.GL_LIGHT1, GL.GL_CONSTANT_ATTENUATION, 1.0f);
        gl.glLightf(GL.GL_LIGHT1, GL.GL_LINEAR_ATTENUATION, 0.0001f);

        // Define os parametros da luz de numero 2
        gl.glLightfv(GL.GL_LIGHT2, GL.GL_AMBIENT, gray, 0);
        gl.glLightfv(GL.GL_LIGHT2, GL.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL.GL_LIGHT2, GL.GL_SPECULAR, specular, 0);
        gl.glLightfv(GL.GL_LIGHT2, GL.GL_POSITION, position2, 0);
        gl.glLightf(GL.GL_LIGHT2, GL.GL_CONSTANT_ATTENUATION, 1.0f);
        gl.glLightf(GL.GL_LIGHT2, GL.GL_LINEAR_ATTENUATION, 0.0001f);

        // Define os parametros da luz de numero 3
        gl.glLightfv(GL.GL_LIGHT3, GL.GL_AMBIENT, gray, 0);
        gl.glLightfv(GL.GL_LIGHT3, GL.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL.GL_LIGHT3, GL.GL_SPECULAR, specular, 0);
        gl.glLightfv(GL.GL_LIGHT3, GL.GL_POSITION, position3, 0);
        gl.glLightf(GL.GL_LIGHT3, GL.GL_CONSTANT_ATTENUATION, 1.0f);
        gl.glLightf(GL.GL_LIGHT3, GL.GL_LINEAR_ATTENUATION, 0.0001f);

        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);
        gl.glEnable(GL.GL_LIGHT1);
        gl.glEnable(GL.GL_LIGHT2);
        gl.glEnable(GL.GL_LIGHT3);
        
        gl.glLightModeli(GL.GL_LIGHT_MODEL_LOCAL_VIEWER, GL.GL_FALSE);
    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
