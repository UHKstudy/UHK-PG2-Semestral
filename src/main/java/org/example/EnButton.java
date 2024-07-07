package org.example;

import org.lwjgl.glfw.GLFW;

public class EnButton {
    private boolean isPressed = false;

    public void handleMouseClick(int button, int action) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            if (action == GLFW.GLFW_PRESS) {
                isPressed = true;
                System.out.println("");
            } else if (action == GLFW.GLFW_RELEASE) {
                isPressed = false;
                System.out.println("");
            }
        }
    }

    public boolean isButtonPressed() {
        return isPressed;
    }
}
