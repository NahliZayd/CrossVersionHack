package zn.cvh.agent; import com.sun.tools.attach.AgentInitializationException;
 import com.sun.tools.attach.AgentLoadException;
 import com.sun.tools.attach.AttachNotSupportedException;
 import com.sun.tools.attach.VirtualMachine;
 import java.io.ByteArrayOutputStream;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.util.jar.Attributes;
 import java.util.jar.JarEntry;
 import java.util.jar.JarOutputStream;
 import java.util.jar.Manifest;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class AgentLoader
 {
   public static void attachAgentToJVM(String pid, Class agent, Class... resources) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
     VirtualMachine vm = VirtualMachine.attach(pid);
     vm.loadAgent(generateAgentJar(agent, resources).getAbsolutePath());
     vm.detach();
   }
 
 
 
 
 
 
 
 
 
   
   public static File generateAgentJar(Class agent, Class... resources) throws IOException {
     File jarFile = File.createTempFile("agent", ".jar");
     jarFile.deleteOnExit();
     
     Manifest manifest = new Manifest();
     Attributes mainAttributes = manifest.getMainAttributes();
     
     mainAttributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
     mainAttributes.put(new Attributes.Name("Agent-Class"), agent.getName());
     mainAttributes.put(new Attributes.Name("Can-Retransform-Classes"), "true");
     mainAttributes.put(new Attributes.Name("Can-Redefine-Classes"), "true");
     
     JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFile), manifest);
     
     jos.putNextEntry(new JarEntry(String.valueOf(agent.getName().replace('.', '/')) + ".class"));
     
     jos.write(getBytesFromStream(agent.getClassLoader().getResourceAsStream(unqualify(agent))));
     jos.closeEntry(); byte b; int i;
     Class[] arrayOfClass;
     for (i = (arrayOfClass = resources).length, b = 0; b < i; ) { Class clazz = arrayOfClass[b];
       String name = unqualify(clazz);
       jos.putNextEntry(new JarEntry(name));
       jos.write(getBytesFromStream(clazz.getClassLoader().getResourceAsStream(name)));
       jos.closeEntry();
       b++; }
     
     jos.close();
     return jarFile;
   }
   
   private static String unqualify(Class clazz) {
     return String.valueOf(clazz.getName().replace('.', '/')) + ".class";
   }
   
   private static byte[] getBytesFromStream(InputStream stream) throws IOException {
     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
     
     byte[] data = new byte[65536]; int nRead;
     while ((nRead = stream.read(data, 0, data.length)) != -1) {
       buffer.write(data, 0, nRead);
     }
     buffer.flush();
     return buffer.toByteArray();
   }
 }


