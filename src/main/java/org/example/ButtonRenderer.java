package org.example;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

public class ButtonRenderer {
    private int buttonTexture;

    public ButtonRenderer() {

        buttonTexture = loadTexture("org/example/resources/1.png");
    }

    public void renderButton(Vector2f position, Vector2f size) {
        Matrix4f transformationMatrix = new Matrix4f()
                .translate(position.x, position.y, 0.0f)
                .scale(size.x, size.y, 1.0f) ;

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, buttonTexture);

    }

    private int loadTexture(String path) {

        return 0;
    }
}
