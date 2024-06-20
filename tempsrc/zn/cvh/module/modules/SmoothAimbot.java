package zn.cvh.module.modules; import zn.cvh.client.ClientLoader;
import zn.cvh.module.Mod;
import zn.cvh.wrapper.WrapperEntity;
import zn.cvh.wrapper.WrapperMinecraft;

import java.lang.reflect.Field;
import java.util.List;


 
 
 
 public class SmoothAimbot
   extends Mod
 {
   private Field theWorldField;
   private Field playerEntitiesField;
   private Field inGameHasFocusField;
   private ClientLoader parent;
   
   public SmoothAimbot(ClientLoader parent) throws Exception {
     super("Smooth Aimbot");
     this.parent = parent;
     this.theWorldField = mcClass.getField("field_71441_e");
     this.playerEntitiesField = this.theWorldField.getType().getField("field_73010_i");
     this.inGameHasFocusField = mcClass.getField("field_71415_G");
   }
 
   
   public void update() throws Exception {
     try {
       boolean inGameHasFocus = this.inGameHasFocusField.getBoolean(mcObj);
       if (inGameHasFocus && this.parent.aimbotForce.getValue() != 0) {
         Object playerEntitiesObj = this.playerEntitiesField.get(this.theWorldField.get(mcObj));
         WrapperEntity closestEntity = null;
         double closestDistance = 3.9D;
         
         for (Object o : ((List) playerEntitiesObj)) {
           if (o != WrapperMinecraft.cPlayer.playerObj) {
             WrapperEntity entity = new WrapperEntity(o);
             if (((Boolean)o.getClass().getMethod("func_70089_S", new Class[0]).invoke(o, new Object[0])).booleanValue()) {
               double deltaX = entity.getPosX() - WrapperMinecraft.cPlayer.getPosX();
               double deltaZ = entity.getPosZ() - WrapperMinecraft.cPlayer.getPosZ();
               double deltaY = entity.getPosY() - WrapperMinecraft.cPlayer.getPosY();

               double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
               if (distance < closestDistance) {
                 float yaw = WrapperMinecraft.cPlayer.getYaw() + getYawChangeToEntity(entity);
                 if (Math.abs(yaw - WrapperMinecraft.cPlayer.getYaw()) < 35.0F) {
                   closestEntity = entity;
                   closestDistance = distance;
                 }
               }
             }
           }
         }
         if (closestEntity != null) {
           int force = 1000 - this.parent.aimbotForce.getValue() + 1;
           float yaw = WrapperMinecraft.cPlayer.getYaw() + getYawChangeToEntity(closestEntity) / force;
           
           if (!Float.isNaN(yaw)) {
             WrapperMinecraft.cPlayer.setYaw(yaw);
           }
         } 
         if (Float.isNaN(WrapperMinecraft.cPlayer.getYaw())) {
           WrapperMinecraft.cPlayer.setYaw(0.0F);
         }
         if (Float.isNaN(WrapperMinecraft.cPlayer.getPitch())) {
           WrapperMinecraft.cPlayer.setPitch(0.0F);
         }
       } 
     } catch (Exception exception) {}
   }
 
 
   
   public float getYawChangeToEntity(WrapperEntity entity) throws IllegalAccessException {
     double yawToEntity, deltaX = entity.getPosX() - WrapperMinecraft.cPlayer.getPosX();
     double deltaZ = entity.getPosZ() - WrapperMinecraft.cPlayer.getPosZ();
 
     
     if (deltaZ < 0.0D && deltaX < 0.0D) {
       yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
     }
     else if (deltaZ < 0.0D && deltaX > 0.0D) {
       yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
     } else {
       yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
     } 
     
     return wrapAngleTo180_float(-(WrapperMinecraft.cPlayer.getYaw() - (float)yawToEntity));
   }
   
   public float wrapAngleTo180_float(float angle) {
     angle %= 360.0F;
     if (angle >= 180.0F) {
       angle -= 360.0F;
     }
     if (angle < -180.0F) {
       angle += 360.0F;
     }
     return angle;
   }
 }


