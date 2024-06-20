package zn.cvh.agent;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class AgentLoader {
    public static void attachAgentToJVM(String pid, Class agent, Class... resources) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        VirtualMachine vm = VirtualMachine.attach(pid);
        vm.loadAgent(AgentLoader.generateAgentJar(agent, resources).getAbsolutePath());
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
        jos.putNextEntry(new JarEntry(agent.getName().replace('.', '/') + ".class"));
        jos.write(AgentLoader.getBytesFromStream(agent.getClassLoader().getResourceAsStream(AgentLoader.unqualify(agent))));
        jos.closeEntry();
        Class[] arrclass = resources;
        int n = arrclass.length;
        int n2 = 0;
        while (n2 < n) {
            Class clazz = arrclass[n2];
            String name = AgentLoader.unqualify(clazz);
            jos.putNextEntry(new JarEntry(name));
            jos.write(AgentLoader.getBytesFromStream(clazz.getClassLoader().getResourceAsStream(name)));
            jos.closeEntry();
            ++n2;
        }
        jos.close();
        return jarFile;
    }

    private static String unqualify(Class clazz) {
        return clazz.getName().replace('.', '/') + ".class";
    }

    private static byte[] getBytesFromStream(InputStream stream) throws IOException {
        int nRead;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[65536];
        while ((nRead = stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }
}
