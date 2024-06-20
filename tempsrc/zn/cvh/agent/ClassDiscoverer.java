package zn.cvh.agent; import java.lang.instrument.Instrumentation;
 
 public class ClassDiscoverer
 {
   private Class<?> minecraftClass;
   
   public ClassDiscoverer(Instrumentation instrumentation) {
     byte b;
     int i;
     Class[] arrayOfClass;
     for (i = (arrayOfClass = instrumentation.getAllLoadedClasses()).length, b = 0; b < i; ) { Class<?> clazz = arrayOfClass[b];
       if (clazz.getName().equals("net.minecraft.client.Minecraft")) {
         this.minecraftClass = clazz;
       }
       b++; }
   
   }
   
   public Class<?> getMinecraftClass() {
     return this.minecraftClass;
   }
 }


