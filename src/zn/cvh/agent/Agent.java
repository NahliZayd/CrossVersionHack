package zn.cvh.agent;

import zn.cvh.client.ClientLoader;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Agent implements ClassFileTransformer {
    private static Agent agent = null;
    private final Instrumentation instrumentation;

    public static Agent getInstance() {
        return agent;
    }

    public static void agentmain(String s, Instrumentation i) {
        System.out.println("Agentmain called with args: " + s);
        try {
            agent = new Agent(i);
            i.addTransformer(agent);
            System.out.println("Transformer added. Initializing ClientLoader.");
            new ClientLoader(i);
            System.out.println("ClientLoader initialized successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Agent(Instrumentation i) {
        System.out.println("Agent constructor called");
        this.instrumentation = i;
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return classfileBuffer;
    }

    public Instrumentation getInstrumentation() {
        return this.instrumentation;
    }
}
