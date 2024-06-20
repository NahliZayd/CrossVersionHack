package zn.cvh;
import com.sun.tools.attach.AgentInitializationException;
 import com.sun.tools.attach.AgentLoadException;
 import com.sun.tools.attach.AttachNotSupportedException;
 import com.sun.tools.attach.VirtualMachine;
 import com.sun.tools.attach.VirtualMachineDescriptor;
import zn.cvh.agent.Agent;
import zn.cvh.agent.AgentLoader;
import zn.cvh.agent.ClassDiscoverer;
import zn.cvh.client.ClientLoader;
import zn.cvh.client.ClientThread;
import zn.cvh.module.Mod;
import zn.cvh.module.modules.SmoothAimbot;
import zn.cvh.module.modules.Triggerbot;
import zn.cvh.ui.CheckboxListener;
import zn.cvh.ui.FrameListener;
import zn.cvh.ui.UIFrame;
import zn.cvh.util.OSUtil;
import zn.cvh.util.TimeUtil;
import zn.cvh.wrapper.Vars;
import zn.cvh.wrapper.WrapperEntity;
import zn.cvh.wrapper.WrapperEntityPlayer;
import zn.cvh.wrapper.WrapperMinecraft;
 import java.io.IOException;
 import javax.swing.JOptionPane;
 
 
 
 
 
 public class CVHMain
 {
   public static void main(String[] args) {
        try {
        new CVHMain();
        } catch (Exception e) {
        e.printStackTrace();
        }
   }
   
   public CVHMain() throws AgentInitializationException, AgentLoadException, AttachNotSupportedException, IOException, InterruptedException {
     ClientLoader.init();
     
     String pid = null;
     for (VirtualMachineDescriptor vm : VirtualMachine.list()) {
       if (vm.displayName().contains("net.minecraft.launchwrapper.Launch")) {
         pid = vm.id();
       }
     } 
     
     if (pid == null) {
       JOptionPane.showMessageDialog(null, "Minecraft not found!", "CVH Ghost Client", 0);
       
       return;
     } else {
         JOptionPane.showMessageDialog(null, "Minecraft found!", "CVH Ghost Client", 1);
     }
     AgentLoader.attachAgentToJVM(pid, Agent.class, new Class[] { UIFrame.class, FrameListener.class, ClientLoader.class, OSUtil.class, ClientThread.class, Triggerbot.class, ClassDiscoverer.class, Mod.class, Vars.class, WrapperMinecraft.class, WrapperEntityPlayer.class, CheckboxListener.class, TimeUtil.class, SmoothAimbot.class, WrapperEntity.class });
   }
 }



