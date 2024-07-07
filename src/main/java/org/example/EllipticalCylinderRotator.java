package org.example;

public class EllipticalCylinderRotator {

    private final EllipticalCylinder ellipticalCylinder;
    private boolean isRotating = false;
    private float rotationSpeed = 1.0f;

    public EllipticalCylinderRotator(EllipticalCylinder ellipticalCylinder) {
        this.ellipticalCylinder = ellipticalCylinder;
    }
    public boolean isRotating() {
        return isRotating;
    }
    public void startRotation() {
        isRotating = true;
    }

    public void stopRotation() {
        isRotating = false;
    }

    public void update() {
        if (isRotating) {
            float newRotationAngle = ellipticalCylinder.getRotationAngle() + rotationSpeed;
            ellipticalCylinder.setRotationAngle(newRotationAngle);
        }
    }
}
