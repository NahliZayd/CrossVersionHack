package zn.cvh.client;

import zn.cvh.module.Mod;
import zn.cvh.wrapper.WrapperMinecraft;


 public class ClientThread
   extends Thread
 {
   private ClientLoader parent;
   
   public ClientThread(ClientLoader parent) {
     this.parent = parent;
   }
   
   public void run() {
     try {
       while (true) {
         try {
           WrapperMinecraft.cPlayer.playerObj = WrapperMinecraft.cPlayer.playerField.get(WrapperMinecraft.getInstance().getMinecraftObject());
         } catch (Exception exception) {}
         
         Thread.sleep(1L);
         for (Mod module : this.parent.getModules()) {
           if (module.isEnabled()) {
             module.update();
           }
         } 
       } 
     } catch (Exception e) {
       e.printStackTrace();
       return;
     } 
   }
 }


