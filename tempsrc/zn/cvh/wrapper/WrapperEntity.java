package zn.cvh.wrapper; import java.lang.reflect.Field;
 
 
 
 
 
 public class WrapperEntity
 {
   private Object entityObj;
   private Class<?> entityClass;
   private Field rotationYaw;
   private Field rotationPitch;
   private Field posX;
   private Field posY;
   private Field posZ;
   
   public WrapperEntity(Object entityObj) {
     try {
       this.entityObj = entityObj;
       this.entityClass = entityObj.getClass();
       this.rotationYaw = this.entityClass.getField("field_70177_z");
       this.rotationPitch = this.entityClass.getField("field_70125_A");
       
       this.posX = this.entityClass.getField("field_70165_t");
       this.posY = this.entityClass.getField("field_70163_u");
       this.posZ = this.entityClass.getField("field_70161_v");
     }
     catch (Exception exception) {}
   }
 
 
   
   public float[] getViewAngles() throws IllegalAccessException {
     return new float[] { getYaw(), getPitch() };
   }
 
 
   
   public void setViewAngles(float yaw, float pitch) throws IllegalAccessException {
     setYaw(yaw);
     setPitch(pitch);
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
 }


