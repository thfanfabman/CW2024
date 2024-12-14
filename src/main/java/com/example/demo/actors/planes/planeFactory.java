package com.example.demo.actors.planes;

/**
 * Factory class responsible for creating different types of fighter planes.
 * This class provides methods to create instances of various fighter plane types.
 */
public class planeFactory {

    /**
     * Creates a fighter plane (actor) based on the specified type.
     *
     * @param type The type of plane to create (e.g., "enemy", "enemy2", "miniboss", "boss", "user").
     * @param x    The initial X-coordinate of the plane.
     * @param y    The initial Y-coordinate of the plane.
     * @return An instance of a {@code FighterPlane} based on the specified type.
     * @throws IllegalArgumentException If the specified plane type is unknown.
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

    /**
     * Creates a user-controlled plane with the specified health.
     *
     * @param health The initial health of the user-controlled plane.
     * @return An instance of a {@code UserPlane} with the specified health.
     */
    public static UserPlane createUserPlane(int health) {
        return new UserPlane(health);
    }
}
