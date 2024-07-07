package org.example;

import static org.lwjgl.glfw.GLFW.*;

public class KeyControllers {

    private final Camera camera;
    private float lastMouseX, lastMouseY;
    private boolean firstMouse = true;
    private final long window;

    public KeyControllers(Camera camera, long window) {
        this.camera = camera;
        this.window = window;
    }

    public void handleKey(int key, int action) {
        if (action == GLFW_PRESS || action == GLFW_REPEAT) {
            switch (key) {
                case GLFW_KEY_W:
                    camera.move(0, 0.5f);
                    break;
                case GLFW_KEY_S:
                    camera.move(0, -0.5f);
                    break;
                case GLFW_KEY_A:
                    camera.move(-0.5f, 0);
                    break;
                case GLFW_KEY_D:
                    camera.move(0.5f, 0);
                    break;
                case GLFW_KEY_H:
                    camera.resetPosition();
                    break;
                case GLFW_KEY_ESCAPE:
                    glfwSetWindowShouldClose(window, true);
                    break;
            }
        }
    }

    public void handleMouse(float xpos, float ypos, boolean mousePressed) {
        if (mousePressed) {
            if (firstMouse) {
                lastMouseX = xpos;
                lastMouseY = ypos;
                firstMouse = false;
            } else {
                float dx = xpos - lastMouseX;
                float dy = ypos - lastMouseY;
                camera.rotate(dy * 0.1f, dx * 0.1f);
            }

            lastMouseX = xpos;
            lastMouseY = ypos;
        } else {
            firstMouse = true;
        }
    }


    public void handleScroll(float yoffset) {
        camera.zoom(yoffset * -0.5f);
    }
}
