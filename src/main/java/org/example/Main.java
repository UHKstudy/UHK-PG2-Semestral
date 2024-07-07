package org.example;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {

    private long window;
    private Camera camera;
    private float lastMouseX, lastMouseY;
    private boolean firstMouse = true;
    private boolean mousePressed = false;
    private Cylinder cylinder1;
    private Cylinder cylinder2;
    private KeyControllers keyControllers;
    private Cylinder cylinder3;
    private Cylinder cylinder4;
    private EllipticalCylinder cylinder5;
    private CylinderRotator cylinderRotator1;
    private CylinderRotator cylinderRotator2;
    private CylinderRotator cylinderRotator3;
    private CylinderRotator cylinderRotator4;
    private EllipticalCylinderRotator cylinderRotator5;

    private EllipticalRing ellipticalRing;
    private EllipticalCylinder ellipticalCylinder;
    private EllipticalCylinderRotator ellipticalCylinderRotator;
    private AxesDrawer axesDrawer;
    private EnButton enButton;
    private ButtonRenderer buttonRenderer;
    private long windowHandle;
    public void run() {
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
        axesDrawer = new AxesDrawer();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        enButton = new EnButton();
        buttonRenderer = new ButtonRenderer();
        this.ellipticalCylinder = new EllipticalCylinder(
                5.425f, 3.72f, 2.0f, 32,
                -5.40f, 0.0f, -1.0f);
        this.ellipticalCylinderRotator = new EllipticalCylinderRotator(ellipticalCylinder);

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(1200, 680, "Semestral PG2, Marek Z. 2024 ©", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
            keyControllers.handleMouse((float) xpos, (float) ypos, mousePressed);
        });

        glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
            enButton.handleMouseClick(button, action);
            if (button == GLFW_MOUSE_BUTTON_LEFT) {
                if (action == GLFW_PRESS) {
                    mousePressed = true;
                    firstMouse = true;
                } else if (action == GLFW_RELEASE) {
                    mousePressed = false;
                }
            } else if (button == GLFW_MOUSE_BUTTON_RIGHT) {
                if (action == GLFW_PRESS) {

                    cylinderRotator1.startRotation();
                    cylinderRotator2.startRotation();
                    cylinderRotator3.startRotation();
                    cylinderRotator4.startRotation();
                    cylinderRotator5.startRotation();
                    ellipticalCylinderRotator.startRotation();
                } else if (action == GLFW_RELEASE) {

                    cylinderRotator1.stopRotation();
                    cylinderRotator2.stopRotation();
                    cylinderRotator3.stopRotation();
                    cylinderRotator4.stopRotation();
                    cylinderRotator5.stopRotation();
                    ellipticalCylinderRotator.stopRotation();
                }
            }
        });

        glfwSetScrollCallback(window, (window, xoffset, yoffset) -> {
            keyControllers.handleScroll((float) yoffset);
        });

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            keyControllers.handleKey(key, action);
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);


        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        float aspectRatio = 1200.0f / 680.0f;
        glFrustum(-aspectRatio, aspectRatio, -1, 1, 1, 100);

        camera = new Camera(10, 30, 45);
        keyControllers = new KeyControllers(camera, window);

        Vector3f rotationAxis1 = new Vector3f(0.0f, 0.0f, 1.0f);
        Vector3f rotationAxis2 = new Vector3f(0.0f, 0.0f, 1.0f);
        Vector3f rotationAxis3 = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f rotationAxis4 = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f rotationAxis5 = new Vector3f(0.0f, 0.0f, -1.0f);


        cylinder1 = new Cylinder(3.0f, 6.0f, 32, -7.7f, 0.0f, -3.0f, "src/main/java/org/example/resources/1.png", rotationAxis1);
        cylinder2 = new Cylinder(2.0f, 4.0f, 32, -2.0f, 0.0f, -2.0f, "src/main/java/org/example/resources/cc.png", rotationAxis2);
        cylinder3 = new Cylinder(0.5f, 5.0f, 32, 0.5f, 0.0f, -2.5f, "src/main/java/org/example/resources/cc.png", rotationAxis3);
        cylinder4 = new Cylinder(1.0f, 4.0f, 32, 3.00f, 0.0f, -2.0f, "src/main/java/org/example/resources/1.png", rotationAxis4);
        cylinder5 = new EllipticalCylinder(2.05f, 1.4f, 3.0f, 32, 2.00f, 0.0f, -1.5f);


        cylinderRotator1 = new CylinderRotator(cylinder1);
        cylinderRotator2 = new CylinderRotator(cylinder2);
        cylinderRotator3 = new CylinderRotator(cylinder3);
        cylinderRotator4 = new CylinderRotator(cylinder4);
        cylinderRotator5 = new EllipticalCylinderRotator(cylinder5);
        axesDrawer = new AxesDrawer();


    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            Matrix4f viewMatrix = camera.getViewMatrix();
            FloatBuffer fb = BufferUtils.createFloatBuffer(16);
            viewMatrix.get(fb);


            glMatrixMode(GL_MODELVIEW);
            glLoadMatrixf(fb);

            glColor3f(0.0f, 0.0f, 1.0f);
            ellipticalCylinder.render(ellipticalCylinderRotator.isRotating());
            glColor3f(1.0f, 1.0f, 1.0f);

            axesDrawer.drawAxes();

            axesDrawer.drawArrowhead(20.0f, 0.0f, 0.0f, 255.0f, 255.0f, 0.0f);

            Vector2f buttonPosition = new Vector2f(20.0f, 20.0f);
            Vector2f buttonSize = new Vector2f(100.0f, 40.0f);
            buttonRenderer.renderButton(buttonPosition, buttonSize);

            cylinder1.render();
            cylinder2.render();
            cylinder3.render();
            cylinder4.render();
            cylinder5.render(ellipticalCylinderRotator.isRotating());

            cylinderRotator1.update();
            cylinderRotator2.update();
            cylinderRotator3.update();
            cylinderRotator4.update();
            cylinderRotator5.update();

            ellipticalCylinderRotator.update();

            glfwSwapBuffers(window);
            glfwPollEvents();
            glfwShowWindow(window);
        }
    }


    public static void main(String[] args) {
        new Main().run();
    }
}