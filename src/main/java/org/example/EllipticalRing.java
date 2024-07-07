package org.example;

import static org.lwjgl.opengl.GL11.*;

public class EllipticalRing {

    private final float innerRadiusX, innerRadiusY;
    private final float outerRadiusX, outerRadiusY;
    private final float posX, posY, posZ;

    public EllipticalRing(float innerRadiusX, float innerRadiusY, float outerRadiusX, float outerRadiusY, float posX, float posY, float posZ) {
        this.innerRadiusX = innerRadiusX;
        this.innerRadiusY = innerRadiusY;
        this.outerRadiusX = outerRadiusX;
        this.outerRadiusY = outerRadiusY;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public void render() {
        glPushMatrix();
        glTranslatef(posX, posY, posZ);
        glBegin(GL_TRIANGLE_STRIP);
        glColor3f(0.678f, 0.847f, 0.902f);
        for(int i = 0; i <= 360; i++) {
            double degInRad = Math.toRadians(i);
            glVertex2f((float)Math.cos(degInRad)*outerRadiusX, (float)Math.sin(degInRad)*outerRadiusY);
            glVertex2f((float)Math.cos(degInRad)*innerRadiusX, (float)Math.sin(degInRad)*innerRadiusY);
        }
        glEnd();
        glPopMatrix();
    }

}
