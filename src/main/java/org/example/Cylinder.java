package org.example;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Cylinder {

    private final float radius;
    private final float length;
    private final int segments;
    private final float posX;
    private final float posY;
    private final float posZ;
    private int textureID;
    private float rotationAngle = 0.0f;
    private Vector3f rotationAxis;

    public Cylinder(float radius, float length, int segments, float posX, float posY, float posZ, String texturePath, Vector3f rotationAxis) {
        this.radius = radius;
        this.length = length;
        this.segments = segments;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.textureID = loadTexture(texturePath);
        this.rotationAxis = rotationAxis;
    }
    private int loadTexture(String path) {
        ByteBuffer image;
        int width, height;
        Path filePath = Paths.get(path).toAbsolutePath();

        try (MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            image = STBImage.stbi_load(filePath.toString(), w, h, comp, 4);
            if (image == null) {
                throw new RuntimeException("Failed to load a texture file!\n" + STBImage.stbi_failure_reason());
            }

            width = w.get();
            height = h.get();
        }

        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        STBImage.stbi_image_free(image);

        return textureID;
    }

    public void render() {
        glPushMatrix();
        glTranslatef(posX, posY, posZ);
        glRotatef(rotationAngle, rotationAxis.x, rotationAxis.y, rotationAxis.z);

        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, textureID);

        glBegin(GL_QUAD_STRIP);
        for (int i = 0; i <= segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;
            float x = (float) Math.cos(angle) * radius;
            float y = (float) Math.sin(angle) * radius;
            float texCoord = (float) i / segments;

            glNormal3f(x, y, 0);
            glTexCoord2f(texCoord, 0);
            glVertex3f(x, y, 0);
            glTexCoord2f(texCoord, 1);
            glVertex3f(x, y, length);
        }
        glEnd();

        glBegin(GL_POLYGON);
        for (int i = 0; i < segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;
            float x = (float) Math.cos(angle) * radius;
            float y = (float) Math.sin(angle) * radius;

            glNormal3f(0, 0, -1);
            glTexCoord2f((float) Math.cos(angle) * 0.5f + 0.5f, (float) Math.sin(angle) * 0.5f + 0.5f);
            glVertex3f(x, y, 0);
        }
        glEnd();

        glBegin(GL_POLYGON);
        for (int i = 0; i < segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;
            float x = (float) Math.cos(angle) * radius;
            float y = (float) Math.sin(angle) * radius;

            glNormal3f(0, 0, 1);
            glTexCoord2f((float) Math.cos(angle) * 0.5f + 0.5f, (float) Math.sin(angle) * 0.5f + 0.5f);
            glVertex3f(x, y, length);
        }
        glEnd();

        glDisable(GL_TEXTURE_2D);
        glPopMatrix();
    }

    public void setRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public float getRotationAngle() {
        return rotationAngle;
    }

    public void setTexture(String texturePath) {
        this.textureID = loadTexture(texturePath);
    }
}
