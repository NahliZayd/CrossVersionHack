package zn.cvh.agent; import java.lang.instrument.ClassFileTransformer;
 import java.lang.instrument.IllegalClassFormatException;
 import java.lang.instrument.Instrumentation;
 import java.security.ProtectionDomain;
 
 
 
 public class Agent
   implements ClassFileTransformer
 {
   private static Agent agent = null;
   
   private Instrumentation instrumentation;
   
   public static Agent getInstance() {
     return agent;
   }
 
   
   public static void agentmain(String s, Instrumentation i) {
     agent = new Agent(i);
     i.addTransformer(agent);
   }
 
 
   
   public Agent(Instrumentation i) {
     this.instrumentation = i;
   }
 
 
   
   public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
     return classfileBuffer;
   }
 }


