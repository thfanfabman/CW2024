package com.example.demo.actors.planes;

/**
 * factory class for creating different types of fighter planes.
 */
public class planeFactory {
    /**
     * creates a fighter plane/actor based on the specified type.
     *
     * @param type   the type of plane (eg. enemy,enemy2,miniboss,boss,user).
     * @param x      the initial X-coordinate of the plane.
     * @param y      the initial Y-coordinate of the plane.
     * @return an instance of a {@code FighterPlane}.
     * @throws IllegalArgumentException if the specified plane type is unknown.
     */
    public static FighterPlane createEnemyPlane(String type, double x, double y) {
        switch (type.toLowerCase()) {
            case "enemy":
                return new EnemyPlane(x, y);
            case "enemy2":
                return new EnemyPlane2(x, y);
            case "boss":
                return new Boss();
            default:
                throw new IllegalArgumentException("Unknown plane type: " + type);
        }
    }

    public static UserPlane createUserPlane(int health){
        return new UserPlane(health);
    }
}