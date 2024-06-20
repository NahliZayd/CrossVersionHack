package zn.cvh;

import com.sun.tools.attach.*;
import zn.cvh.agent.Agent;
import zn.cvh.agent.AgentLoader;
import zn.cvh.client.ClientLoader;
import zn.cvh.client.ClientThread;
import zn.cvh.module.Mod;
import zn.cvh.module.modules.Reach;
import zn.cvh.module.modules.SmoothAimbot;
import zn.cvh.module.modules.Triggerbot;
import zn.cvh.module.value.Values;
import zn.cvh.module.value.values.BoolValues;
import zn.cvh.module.value.values.DoubleValue;
import zn.cvh.ui.UIFrame;
import zn.cvh.ui.listeners.CheckboxListener;
import zn.cvh.ui.listeners.FrameListener;
import zn.cvh.ui.listeners.SliderListener;
import zn.cvh.utils.ClassDiscoverer;
import zn.cvh.utils.CustomBoundingBox;
import zn.cvh.utils.OSUtil;
import zn.cvh.utils.TimeUtil;
import zn.cvh.wrappers.Vars;
import zn.cvh.wrappers.WrapperEntity;
import zn.cvh.wrappers.WrapperEntityPlayer;
import zn.cvh.wrappers.WrapperMinecraft;

import javax.swing.*;
import java.io.IOException;

public class CVHMain {
    public static void main(String[] args) {
        try {
            new CVHMain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CVHMain() throws AgentInitializationException, AgentLoadException, AttachNotSupportedException, IOException, InterruptedException {

        String pid = null;
        for (VirtualMachineDescriptor vm : VirtualMachine.list()) {
            if (!vm.displayName().startsWith("net.minecraft.launchwrapper.Launch")) continue;
            pid = vm.id();
        }
        if (pid == null) {
            JOptionPane.showMessageDialog(null, "Minecraft not found!", "Cross Version Hack By Zayd", 0);
        } else {

            JOptionPane.showMessageDialog(null, "Minecraft found! PID: " + pid, "Cross Version Hack By Zayd", 1);
            AgentLoader.attachAgentToJVM(pid,
                    Agent.class,
                    Reach.class,
                    CustomBoundingBox.class,
                    SliderListener.class,
                    Values.class,
                    DoubleValue.class,
                    BoolValues.class,
                    UIFrame.class,
                    FrameListener.class,
                    ClientLoader.class,
                    OSUtil.class,
                    ClientThread.class,
                    Triggerbot.class,
                    ClassDiscoverer.class,
                    Mod.class,
                    Vars.class,
                    WrapperMinecraft.class,
                    WrapperEntityPlayer.class,
                    CheckboxListener.class,
                    TimeUtil.class,
                    SmoothAimbot.class,
                    WrapperEntity.class);

        }
    }
}
