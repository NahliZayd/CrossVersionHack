package zn.cvh.wrapper;


 
 
 
 public class WrapperMinecraft
 {
   private static WrapperMinecraft cMinecraft;
   public static WrapperEntityPlayer cPlayer;
   private Object minecraftObj;
   private Class<?> minecraftClass;
   
   public WrapperMinecraft(Class<?> minecraft) {
     this.minecraftClass = minecraft;
     
     try {
       this.minecraftObj = minecraft.getMethod(Vars.getMinecraft, new Class[0]).invoke(null, new Object[0]);
     }
     catch (Exception e) {
       
       e.printStackTrace();
     } 
     cMinecraft = this;
     cPlayer = new WrapperEntityPlayer(this);
   }
 
   
   public Object getMinecraftObject() {
     return this.minecraftObj;
   }
 
   
   public Class<?> getMinecraftClass() {
     return this.minecraftClass;
   }
 
   
   public static WrapperMinecraft getInstance() {
     return cMinecraft;
   }
 }


