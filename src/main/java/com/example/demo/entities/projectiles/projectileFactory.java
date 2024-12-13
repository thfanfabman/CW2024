package com.example.demo.entities.projectiles;

import com.example.demo.entities.planes.Boss;
import com.example.demo.entities.planes.EnemyPlane;
import com.example.demo.entities.planes.FighterPlane;

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
