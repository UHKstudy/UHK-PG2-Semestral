package org.example;

import static org.lwjgl.opengl.GL11.*;

public class EllipticalCylinder {

    private final float radiusX;
    private final float radiusY;
    private final float length;
    private final int segments;
    private final float posX;
    private final float posY;
    private final float posZ;
    private float rotationAngle = 0.0f;

    public EllipticalCylinder(float radiusX, float radiusY, float length, int segments, float posX, float posY, float posZ) {
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.length = length;
        this.segments = segments;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }



    public void render(boolean isRotating) {
        glPushMatrix();
        glTranslatef(posX, posY, posZ);

        glColor3f(0.0f, 0.0f, 1.0f);
        glBegin(GL_QUAD_STRIP);
        for (int i = 0; i <= segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;
            float x = (float) Math.cos(angle) * radiusX;
            float y = (float) Math.sin(angle) * radiusY;
            glVertex3f(x, y, 0);
            glVertex3f(x, y, length);
        }
        glEnd();

        if (isRotating) {
            glPointSize(3.0f);
            glBegin(GL_POINTS);
            for (int i = 0; i <= segments; i++) {
                double angle = 2.0 * Math.PI * (i + rotationAngle) / segments;
                float x = (float) Math.cos(angle) * radiusX;
                float y = (float) Math.sin(angle) * radiusY;
                glColor3f((float)Math.random(), (float)Math.random(), (float)Math.random());
                glVertex3f(x, y, 0);
                glVertex3f(x, y, length);
            }
            glEnd();
        }

        glPopMatrix();
    }



    public float getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
    }
}
