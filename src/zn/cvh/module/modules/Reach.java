package zn.cvh.module.modules;

import zn.cvh.module.Mod;
import zn.cvh.module.value.values.DoubleValue;
import zn.cvh.utils.CustomBoundingBox;
import zn.cvh.wrapper.wrappers.Entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reach extends Mod {

    private static final Logger LOGGER = Logger.getLogger(Reach.class.getName());

    public DoubleValue minReach = new DoubleValue("Minimum Range", 3.0, 3.0, 6.0);
    public DoubleValue maxReach = new DoubleValue("Maximum Range", 3.5, 3.0, 6.0);

    public Reach() throws Exception {
        super("Reach");
        addValue(minReach);
        addValue(maxReach);
        LOGGER.info("Reach module initialized with minReach: " + minReach.getValue() + " and maxReach: " + maxReach.getValue());
    }

    @Override
    public void update() throws Exception {
        if (!mc.inGameHasFocus() || mc.getPlayer() == null || mc.getWorld() == null) {
            LOGGER.warning("Game is not focused or player/world is null.");
            if(mc.getPlayer() == null) LOGGER.warning("Player is null.");
            if(mc.getWorld() == null) LOGGER.warning("World is null.");
            if(!mc.inGameHasFocus()) LOGGER.warning("Game is not focused.");
            return;
        }

        Entity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;

        for (Entity entity : mc.getWorld().getEntities()) {
            if (entity.entityObj == mc.getPlayer().entityObj) continue;
            if (!entity.isAlive()) continue;
            double distance = entity.getDistanceToEntity(mc.getPlayer());
            if (distance < closestDistance && distance < 10.0) {
                closestEntity = entity;
                closestDistance = distance;
            }
        }

        if (closestEntity != null) {
            LOGGER.info("Closest entity found at distance: " + closestDistance);
            double x = closestEntity.getPosX();
            double y = closestEntity.getPosY();
            double z = closestEntity.getPosZ();
            double f = getDist() - 3.0;

            if (mc.getPlayer().getDistanceToEntity(closestEntity) > 2.9) {
                double c = Math.hypot(mc.getPlayer().getPosX() - x, mc.getPlayer().getPosZ() - z);
                if (f > c) {
                    f -= c;
                }
                float r = a(x, z);
                if (a(mc.getPlayer().getYaw(), r) > 90.0D) {
                    LOGGER.warning("Angle exceeds 90 degrees, aborting.");
                    return;
                }
                double aa = Math.cos(Math.toRadians(r + 90.0f));
                double bb = Math.sin(Math.toRadians(r + 90.0f));
                x -= (aa * f);
                z -= (bb * f);

                double maxX = x + closestEntity.getWidth() / 2;
                double maxY = y + closestEntity.getHeight();
                double maxZ = z + closestEntity.getWidth() / 2;

                CustomBoundingBox nBounding = new CustomBoundingBox(x - closestEntity.getWidth() / 2, y, z - closestEntity.getWidth() / 2, maxX, maxY, maxZ);
                closestEntity.setEntityBoundingBox(nBounding);
                LOGGER.info("Entity bounding box updated.");
            }
        } else {
            LOGGER.info("No closest entity found within range.");
        }
    }

    private double getDist() {
        double dist = (minReach.getValue() == maxReach.getValue()) ? minReach.getValue() : ThreadLocalRandom.current().nextDouble(minReach.getValue(), maxReach.getValue());
        LOGGER.fine("Generated distance: " + dist);
        return dist;
    }

    private float a(double ex, double ez) throws Exception {
        double x = ex - mc.getPlayer().getPosX();
        double z = ez - mc.getPlayer().getPosZ();
        float y = (float) Math.toDegrees(-Math.atan(x / z));
        if ((z < 0.0) && (x < 0.0)) {
            y = (float) (90.0 + Math.toDegrees(Math.atan(z / x)));
        } else if ((z < 0.0) && (x > 0.0)) {
            y = (float) (-90.0 + Math.toDegrees(Math.atan(z / x)));
        }
        LOGGER.fine("Calculated angle: " + y);
        return y;
    }

    @Override
    public void postUpdate() {
        LOGGER.info("Post update called.");
    }
}
