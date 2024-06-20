package zn.cvh.wrapper; import java.lang.reflect.Field;
 
 
 
 
 
 public class WrapperEntityPlayer
 {
   public Object playerObj;
   public Field playerField;
   private Class<?> playerClass;
   private Class<?> axisAlignedBBClass;
   private Field rotationYaw;
   private Field rotationPitch;
   private Field posX;
   private Field posY;
   private Field posZ;
   
   public WrapperEntityPlayer(WrapperMinecraft cMinecraft) {
     try {
       this.playerField = cMinecraft.getMinecraftClass().getField("field_71439_g");
       this.playerClass = this.playerField.getType();
       
       this.rotationYaw = this.playerClass.getField("field_70177_z");
       this.rotationPitch = this.playerClass.getField("field_70125_A");
       
       this.posX = this.playerClass.getField("field_70165_t");
       this.posY = this.playerClass.getField("field_70163_u");
       this.posZ = this.playerClass.getField("field_70161_v");
       
       this.playerObj = this.playerField.get(cMinecraft.getMinecraftObject());
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
     return this.rotationYaw.getFloat(this.playerObj);
   }
 
 
   
   public float getPitch() throws IllegalAccessException {
     return this.rotationPitch.getFloat(this.playerObj);
   }
 
 
   
   public void setYaw(float yaw) throws IllegalAccessException {
     this.rotationYaw.setFloat(this.playerObj, yaw);
   }
 
 
   
   public void setPitch(float pitch) throws IllegalAccessException {
     this.rotationPitch.setFloat(this.playerObj, pitch);
   }
 
 
   
   public double getPosX() throws IllegalAccessException {
     return this.posX.getDouble(this.playerObj);
   }
 
 
   
   public double getPosY() throws IllegalAccessException {
     return this.posY.getDouble(this.playerObj);
   }
 
 
   
   public double getPosZ() throws IllegalAccessException {
     return this.posZ.getDouble(this.playerObj);
   }
 }


