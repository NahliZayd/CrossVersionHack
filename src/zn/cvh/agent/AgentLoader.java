package zn.cvh.agent;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.*;
import java.util.jar.*;

public class AgentLoader {
    public static void attachAgentToJVM(String pid, Class agent, Class... resources) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        System.out.println("Attaching agent to JVM with PID: " + pid);
        VirtualMachine vm = VirtualMachine.attach(pid);
        try {
            File agentJar = AgentLoader.generateAgentJar(agent, resources);
            System.out.println("Generated agent JAR at: " + agentJar.getAbsolutePath());
            vm.loadAgent(agentJar.getAbsolutePath());
            System.out.println("Agent loaded successfully.");
        } catch (AgentInitializationException | AgentLoadException e) {
            e.printStackTrace();
            throw e;
        } finally {
            vm.detach();
            System.out.println("Agent detached from JVM.");
        }
    }

    public static File generateAgentJar(Class agent, Class... resources) throws IOException {
        System.out.println("Generating agent JAR for class: " + agent.getName());
        File jarFile = File.createTempFile("agent", ".jar");
        jarFile.deleteOnExit();
        Manifest manifest = new Manifest();
        Attributes mainAttributes = manifest.getMainAttributes();
        mainAttributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        mainAttributes.put(new Attributes.Name("Agent-Class"), agent.getName());
        mainAttributes.put(new Attributes.Name("Can-Retransform-Classes"), "true");
        mainAttributes.put(new Attributes.Name("Can-Redefine-Classes"), "true");

        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFile), manifest)) {
            jos.putNextEntry(new JarEntry(agent.getName().replace('.', '/') + ".class"));
            jos.write(AgentLoader.getBytesFromStream(agent.getClassLoader().getResourceAsStream(AgentLoader.unqualify(agent))));
            jos.closeEntry();

            for (Class clazz : resources) {
                String name = AgentLoader.unqualify(clazz);
                jos.putNextEntry(new JarEntry(name));
                jos.write(AgentLoader.getBytesFromStream(clazz.getClassLoader().getResourceAsStream(name)));
                jos.closeEntry();
            }
        }

        System.out.println("Agent JAR generation complete.");
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
