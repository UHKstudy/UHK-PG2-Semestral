package org.example;

import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.opengl.GL11.*;

    public class AxesDrawer {

        public void drawAxes() {
            glBegin(GL_LINES);



            glColor3f(1.0f, 0.0f, 0.0f);
            glVertex3f(-20.0f, 0.0f, 0.0f);
            glVertex3f(20.0f, 0.0f, 0.0f);


            glColor3f(0.0f, 1.0f, 0.0f);
            glVertex3f(0.0f, -20.0f, 0.0f);
            glVertex3f(0.0f, 20.0f, 0.0f);


            glColor3f(1.0f, 1.0f, 0.0f);
            glVertex3f(0.0f, 0.0f, -20.0f);
            glVertex3f(0.0f, 0.0f, 20.0f);

            glEnd();


            drawArrowhead(20.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
            drawArrowhead(0.0f, 20.0f, 0.0f, 0.0f, 1.0f, 0.0f);
            drawArrowhead(0.0f, 0.0f, 20.0f, 1.0f, 1.0f, 0.0f);
        }

        public void drawArrowhead(float x, float y, float z, float r, float g, float b) {
            glBegin(GL_TRIANGLES);
            glColor3f(r, g, b);

            if (x != 0) {
                glVertex3f(x, y, z);
                glVertex3f(x - 1.0f, y + 0.5f, z);
                glVertex3f(x - 1.0f, y - 0.5f, z);
            } else if (y != 0) {
                glVertex3f(x, y, z);
                glVertex3f(x + 0.5f, y - 1.0f, z);
                glVertex3f(x - 0.5f, y - 1.0f, z);
            } else if (z != 0) {
                glVertex3f(x, y, z);
                glVertex3f(x, y + 0.5f, z - 1.0f);
                glVertex3f(x, y - 0.5f, z - 1.0f);
            }

            glEnd();
        }

    }

