package com.example.demo.actors.projectiles;

public class projectileFactory {
    public static Projectile createProjectile(String type, double x, double y) {
        switch (type.toLowerCase()) {
            case "user":
                return new UserProjectile(x,y);
            case "enemy":
                return new EnemyProjectile(x, y);
            case "boss":
                return new BossProjectile(y);
            default:
                throw new IllegalArgumentException("Unknown projectile type: " + type);
        }
    }
}
