package org.example;

public class CylinderRotator {

    private final Cylinder cylinder;
    private boolean isRotating = false;
    private float rotationSpeed = 1.0f;

    public CylinderRotator(Cylinder cylinder) {
        this.cylinder = cylinder;
    }

    public void startRotation() {
        isRotating = true;
    }

    public void stopRotation() {
        isRotating = false;
    }

    public void update() {
        if (isRotating) {
            float newRotationAngle = cylinder.getRotationAngle() + rotationSpeed;
            cylinder.setRotationAngle(newRotationAngle);
        }
    }
}
