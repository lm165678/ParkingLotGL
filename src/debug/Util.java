/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package debug;

import javax.media.opengl.GL;

/**
 *
 * @author paulovich
 */
public class Util {

    public static void printModelViewMatrix(GL gl) {
        //printf("Mwc,vc=\n");
        System.out.println("Model View =");
        float[] modelview = new float[16];
        gl.glGetFloatv(gl.GL_MODELVIEW_MATRIX, modelview, 0);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(modelview[i + j * 4] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printProjectionMatrix(GL gl) {
        //printf("Mwc,vc=\n");
        System.out.println("Model View =");
        float[] modelview = new float[16];
        gl.glGetFloatv(gl.GL_PROJECTION_MATRIX, modelview, 0);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(modelview[i + j * 4] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
