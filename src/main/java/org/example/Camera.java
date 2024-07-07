package org.example;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Vector3f position;
    private Vector3f initialPosition;
    private float pitch;
    private float yaw;
    private float initialPitch;
    private float initialYaw;
    private float distance;
    private float initialDistance;

    public Camera(float distance, float pitch, float yaw) {
        this.initialPosition = new Vector3f(0, 0, 0);
        this.position = new Vector3f(initialPosition);
        this.initialPitch = pitch;
        this.initialYaw = yaw;
        this.pitch = pitch;
        this.yaw = yaw;
        this.initialDistance = distance;
        this.distance = distance;
    }


    public Matrix4f getViewMatrix() {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();

        float x = (float) (distance * Math.sin(Math.toRadians(pitch)) * Math.cos(Math.toRadians(yaw)));
        float y = (float) (distance * Math.cos(Math.toRadians(pitch)));
        float z = (float) (distance * Math.sin(Math.toRadians(pitch)) * Math.sin(Math.toRadians(yaw)));

        viewMatrix.lookAt(new Vector3f(x, y, z).add(position), position, new Vector3f(0, 1, 0));
        return viewMatrix;
    }


    public void rotate(float pitchDelta, float yawDelta) {
        this.pitch += pitchDelta;
        this.yaw += yawDelta;
    }

    public void zoom(float distanceDelta) {
        this.distance += distanceDelta;
        if (this.distance < 1.0f) {
            this.distance = 1.0f;
        }
    }

    public void move(float deltaX, float deltaY) {
        Vector3f forward = new Vector3f(
                (float) Math.sin(Math.toRadians(yaw)),
                0,
                (float) Math.cos(Math.toRadians(yaw))
        ).normalize();

        Vector3f right = new Vector3f(forward).cross(new Vector3f(0, 1, 0)).normalize();

        Vector3f moveVector = new Vector3f(right).mul(deltaX).add(new Vector3f(forward).mul(deltaY));
        position.add(moveVector);
    }

    public void resetPosition() {
        position.set(initialPosition);
        pitch = initialPitch;
        yaw = initialYaw;
        distance = initialDistance;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getDistance() {
        return distance;
    }
}
