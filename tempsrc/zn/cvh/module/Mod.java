package zn.cvh.module;

import zn.cvh.wrapper.WrapperMinecraft;


 
 public abstract class Mod
 {
   private String name;
   private boolean enabled = false;
   protected static Object mcObj;
   protected static Class<?> mcClass;
   
   public Mod(String name) throws Exception {
     this.name = name;
     mcClass = WrapperMinecraft.getInstance().getMinecraftClass();
     mcObj = WrapperMinecraft.getInstance().getMinecraftObject();
   }
   
   public abstract void update() throws Exception;
   
   public String getName() {
     return this.name;
   }
   
   public void setEnabled(boolean enabled) {
     this.enabled = enabled;
   }
   
   public boolean isEnabled() {
     return this.enabled;
   }
 }


