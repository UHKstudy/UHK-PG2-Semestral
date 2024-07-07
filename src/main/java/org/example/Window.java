package org.example;

import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;

public class Window {
    private long windowHandle;
    public void swapBuffers() {
        glfwSwapBuffers(windowHandle);
    }
    public void drawQuad(Vector2f position, Vector2f scale, float rotation) {

    }
}