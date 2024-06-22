package zn.cvh.wrapper.wrappers;

import zn.cvh.agent.Agent;
import zn.cvh.utils.ClassDiscoverer;
import zn.cvh.utils.CustomBoundingBox;
import zn.cvh.wrapper.Wrapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Entity extends Wrapper {
    public Object entityObj;
    public Field rotationYaw;
    public Field rotationPitch;
    public Field posX;
    public Field posY;
    public Field posZ;
    public Field width;
    public Field height;
    public Class<?> axisAlignedBBClass;
    
    public Entity(Object entityObj) throws NoSuchFieldException {
        super();
        this.entityObj = entityObj;
        clazz = entityObj.getClass();
        this.rotationYaw = this.clazz.getField("field_70177_z");
        this.rotationPitch = this.clazz.getField("field_70125_A");
        this.posX = this.clazz.getField("field_70165_t");
        this.posY = this.clazz.getField("field_70163_u");
        this.posZ = this.clazz.getField("field_70161_v");
        this.width = this.clazz.getField("field_70130_N");
        this.height = this.clazz.getField("field_70131_O");
        this.axisAlignedBBClass = new ClassDiscoverer(Agent.getInstance().getInstrumentation()).getAxisAlignedBBClass();


    }

    public float[] getViewAngles() throws IllegalAccessException {
        return new float[]{this.getYaw(), this.getPitch()};
    }

    public void setViewAngles(float yaw, float pitch) throws IllegalAccessException {
        this.setYaw(yaw);
        this.setPitch(pitch);
    }

    public float getYaw() throws IllegalAccessException {
        return this.rotationYaw.getFloat(this.entityObj);
    }

    public float getPitch() throws IllegalAccessException {
        return this.rotationPitch.getFloat(this.entityObj);
    }

    public void setYaw(float yaw) throws IllegalAccessException {
        this.rotationYaw.setFloat(this.entityObj, yaw);
    }

    public void setPitch(float pitch) throws IllegalAccessException {
        this.rotationPitch.setFloat(this.entityObj, pitch);
    }

    public double getPosX() throws IllegalAccessException {
        return this.posX.getDouble(this.entityObj);
    }

    public double getPosY() throws IllegalAccessException {
        return this.posY.getDouble(this.entityObj);
    }

    public double getPosZ() throws IllegalAccessException {
        return this.posZ.getDouble(this.entityObj);
    }

    public double getWidth() throws IllegalAccessException {
        return this.width.getDouble(this.entityObj);
    }

    public double getHeight() throws IllegalAccessException {
        return this.height.getDouble(this.entityObj);
    }

    public double getDistanceToEntity(Entity entity) throws IllegalAccessException {
        double deltaX = getPosX() - entity.getPosX();
        double deltaZ = getPosZ() - entity.getPosZ();
        double deltaY = getPosY() - entity.getPosY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        return distance;
    }

    public void setEntityBoundingBox(CustomBoundingBox nBounding) {
        try {


            Constructor<?> constructor = axisAlignedBBClass.getConstructor(double.class, double.class, double.class, double.class, double.class, double.class);


            Object axisAlignedBB = constructor.newInstance(nBounding.minX, nBounding.minY, nBounding.minZ, nBounding.maxX, nBounding.maxY, nBounding.maxZ);


            Method setBoundingBoxMethod = entityObj.getClass().getMethod("func_174826_a", axisAlignedBBClass);


            setBoundingBoxMethod.invoke(entityObj, axisAlignedBB);


        } catch (Exception exception) {
            System.out.println("Error setting entity bounding box: " + exception);
            exception.printStackTrace();

        }
    }

    public boolean isAlive() {
        try {
            return (Boolean) clazz.getMethod("func_70089_S").invoke(entityObj);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean isEntityLivingBase() {
        return clazz == classDiscoverer.getEntityLivingBaseClass();
    }

    public double getEyeHeight() {
        return 1;
    }
}
