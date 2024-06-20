package zn.cvh.module.modules;

import zn.cvh.client.ClientLoader;
import zn.cvh.module.Mod;
import zn.cvh.module.value.values.DoubleValue;
import zn.cvh.wrappers.WrapperEntity;
import zn.cvh.wrappers.WrapperMinecraft;

import java.lang.reflect.Field;
import java.util.List;

public class SmoothAimbot
        extends Mod {
    private final Field theWorldField;
    private final Field playerEntitiesField;
    private final Field inGameHasFocusField;
    private final ClientLoader parent;

    public DoubleValue aimbotForce = new DoubleValue("Aimbot Force", 0.0, 0.0, 1000.0);

    public SmoothAimbot(ClientLoader parent) throws Exception {
        super("Smooth Aimbot");
        this.parent = parent;
        this.theWorldField = mcClass.getField("field_71441_e");
        this.playerEntitiesField = this.theWorldField.getType().getField("field_73010_i");
        this.inGameHasFocusField = mcClass.getField("field_71415_G");
        addValue(aimbotForce);
    }

    public void update() throws Exception {
        try {
            boolean inGameHasFocus = this.inGameHasFocusField.getBoolean(mcObj);
            if (inGameHasFocus && aimbotForce.getValue() != 0) {
                Object playerEntitiesObj = this.playerEntitiesField.get(this.theWorldField.get(mcObj));
                WrapperEntity closestEntity = null;
                double closestDistance = 3.9;
                for (Object o : (List) playerEntitiesObj) {
                    float yaw;
                    if (o == WrapperMinecraft.cPlayer.playerObj) continue;
                    WrapperEntity entity = new WrapperEntity(o);
                    if (!((Boolean) o.getClass().getMethod("func_70089_S", new Class[0]).invoke(o, new Object[0])).booleanValue())
                        continue;
                    double deltaX = entity.getPosX() - WrapperMinecraft.cPlayer.getPosX();
                    double deltaZ = entity.getPosZ() - WrapperMinecraft.cPlayer.getPosZ();
                    double deltaY = entity.getPosY() - WrapperMinecraft.cPlayer.getPosY();
                    double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
                    if (distance >= closestDistance || Math.abs((yaw = WrapperMinecraft.cPlayer.getYaw() + this.getYawChangeToEntity(entity)) - WrapperMinecraft.cPlayer.getYaw()) >= 35.0f)
                        continue;
                    closestEntity = entity;
                    closestDistance = distance;
                }
                if (closestEntity != null) {
                    int force = 1000 - this.parent.aimbotForce.getValue() + 1;
                    float yaw = WrapperMinecraft.cPlayer.getYaw() + this.getYawChangeToEntity(closestEntity) / (float) force;
                    if (!Float.isNaN(yaw)) {
                        WrapperMinecraft.cPlayer.setYaw(yaw);
                    }
                }
                if (Float.isNaN(WrapperMinecraft.cPlayer.getYaw())) {
                    WrapperMinecraft.cPlayer.setYaw(0.0f);
                }
                if (Float.isNaN(WrapperMinecraft.cPlayer.getPitch())) {
                    WrapperMinecraft.cPlayer.setPitch(0.0f);
                }
            }
        } catch (Exception E) {

        }
    }

    public float getYawChangeToEntity(WrapperEntity entity) throws IllegalAccessException {
        double deltaX = entity.getPosX() - WrapperMinecraft.cPlayer.getPosX();
        double deltaZ = entity.getPosZ() - WrapperMinecraft.cPlayer.getPosZ();
        double yawToEntity = deltaZ < 0.0 && deltaX < 0.0 ? 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : (deltaZ < 0.0 && deltaX > 0.0 ? -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : Math.toDegrees(-Math.atan(deltaX / deltaZ)));
        return this.wrapAngleTo180_float(-WrapperMinecraft.cPlayer.getYaw() - (float) yawToEntity);
    }

    public float wrapAngleTo180_float(float angle) {
        if ((angle %= 360.0f) >= 180.0f) {
            angle -= 360.0f;
        }
        if (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }
}
