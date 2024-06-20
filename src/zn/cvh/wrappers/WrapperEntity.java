package zn.cvh.wrappers;

import zn.cvh.agent.Agent;
import zn.cvh.utils.ClassDiscoverer;
import zn.cvh.utils.CustomBoundingBox;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class WrapperEntity {

    private Object entityObj;
    private Class<?> entityClass;
    private Field rotationYaw;
    private Field rotationPitch;
    private Field posX;
    private Field posY;
    private Field posZ;
    private Field width;
    private Field height;

    private Class<?> axisAlignedBBClass;


    public WrapperEntity(Object entityObj) {
        try {
            this.entityObj = entityObj;
            this.entityClass = entityObj.getClass();
            this.rotationYaw = this.entityClass.getField("field_70177_z");
            this.rotationPitch = this.entityClass.getField("field_70125_A");
            this.posX = this.entityClass.getField("field_70165_t");
            this.posY = this.entityClass.getField("field_70163_u");
            this.posZ = this.entityClass.getField("field_70161_v");
            this.width = this.entityClass.getField("field_70130_N");
            this.height = this.entityClass.getField("field_70131_O");

            this.axisAlignedBBClass = new ClassDiscoverer(Agent.getInstance().getInstrumentation()).getAxisAlignedBBClass();

            System.out.println("axisAlignedBBClass: " + axisAlignedBBClass + "\n");


        } catch (Exception exception) {

        }
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

    public double getDistanceToEntity(WrapperEntityPlayer cPlayer) throws IllegalAccessException {
        double deltaX = getPosX() - WrapperMinecraft.cPlayer.getPosX();
        double deltaZ = getPosZ() - WrapperMinecraft.cPlayer.getPosZ();
        double deltaY = getPosY() - WrapperMinecraft.cPlayer.getPosY();
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

}
