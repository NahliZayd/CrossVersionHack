package zn.cvh.module.modules;


import zn.cvh.module.Mod;
import zn.cvh.module.value.values.DoubleValue;
import zn.cvh.utils.CustomBoundingBox;
import zn.cvh.wrappers.WrapperEntity;
import zn.cvh.wrappers.WrapperMinecraft;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Reach extends Mod {

    private final Field theWorldField;
    private final Field entitiesField;
    private final Field inGameHasFocusField;
    public DoubleValue minReach = new DoubleValue("Minimum Range", 3.0, 3.0, 6.0);
    public DoubleValue maxReach = new DoubleValue("Maximum Range", 3.5, 3.0, 6.0);

    public Reach() throws Exception {
        super("Reach");
        this.theWorldField = mcClass.getField("field_71441_e");
        this.entitiesField = this.theWorldField.getType().getField("field_72996_f");
        this.inGameHasFocusField = mcClass.getField("field_71415_G");
        addValue(minReach);
        addValue(maxReach);
    }

    @Override
    public void update() throws Exception {
        boolean inGameHasFocus = this.inGameHasFocusField.getBoolean(mcObj);

        if (!inGameHasFocus || this.theWorldField.get(mcObj) == null) {
            return;
        }


        Object objEntities = this.entitiesField.get(this.theWorldField.get(mcObj));
        List<?> objEntitiesList = (List<?>) objEntities;
        List<Object> entitiesCopy = new ArrayList<>(objEntitiesList);

        WrapperEntity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;


        for (Object o : entitiesCopy) {
            if (o == WrapperMinecraft.cPlayer.playerObj) continue;
            WrapperEntity entity = new WrapperEntity(o);
            if (!((Boolean) o.getClass().getMethod("func_70089_S", new Class[0]).invoke(o, new Object[0])).booleanValue())
                continue;
            double distance = entity.getDistanceToEntity(WrapperMinecraft.cPlayer);


            if (distance < closestDistance && distance < 10.0) {
                closestEntity = entity;
                closestDistance = distance;
            }
        }


        if (closestEntity != null) {
            double x = closestEntity.getPosX();
            double y = closestEntity.getPosY();
            double z = closestEntity.getPosZ();
            double f = getDist() - 3.0;


            if (WrapperMinecraft.cPlayer.getDistanceToEntity(closestEntity) > 2.9) {

                double c = Math.hypot(WrapperMinecraft.cPlayer.getPosX() - x, WrapperMinecraft.cPlayer.getPosZ() - z);
                if (f > c) {
                    f -= c;
                }
                float r = a(x, z);
                if (a(WrapperMinecraft.cPlayer.getYaw(), r) > 90.0D) {
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
            }
        }
    }

    private double getDist() {
        if (minReach.getValue() == maxReach.getValue()) {
            return minReach.getValue();
        }
        return ThreadLocalRandom.current().nextDouble(minReach.getValue(), maxReach.getValue());
    }

    private float a(double ex, double ez) throws IllegalAccessException {
        double x = ex - WrapperMinecraft.cPlayer.getPosX();
        double z = ez - WrapperMinecraft.cPlayer.getPosZ();
        float y = (float) Math.toDegrees(-Math.atan(x / z));
        if ((z < 0.0) && (x < 0.0)) {
            y = (float) (90.0 + Math.toDegrees(Math.atan(z / x)));
        } else if ((z < 0.0) && (x > 0.0)) {
            y = (float) (-90.0 + Math.toDegrees(Math.atan(z / x)));
        }
        return y;
    }

    @Override
    public void postUpdate() {

    }
}

