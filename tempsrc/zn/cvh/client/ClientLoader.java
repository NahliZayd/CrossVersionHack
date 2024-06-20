package zn.cvh.client;



 import zn.cvh.agent.ClassDiscoverer;
import zn.cvh.module.Mod;
import zn.cvh.module.modules.SmoothAimbot;
import zn.cvh.module.modules.Triggerbot;
import zn.cvh.ui.UIFrame;

import java.lang.instrument.Instrumentation;

        
        
        
         import java.util.ArrayList;
 import java.util.List;

         import javax.swing.JSlider;
 
 
 
 
 
 public class ClientLoader
 {
   private Instrumentation inst;
   private UIFrame concealUI;
   private List<Mod> modules = new ArrayList<>();
   
   private ClientThread moduleThread;
   public JSlider aimbotForce;
   
   public ClientLoader(Instrumentation inst) {
     this.inst = inst;
     ClassDiscoverer discoverer = new ClassDiscoverer(inst);
 
     
     try {
       this.modules.add(new SmoothAimbot(this));
       this.modules.add(new Triggerbot());
     } catch (Exception e) {
       e.printStackTrace();
     } 
     
     UIFrame concealUI = new UIFrame(this);
     concealUI.show();
     
     this.moduleThread = new ClientThread(this);
     this.moduleThread.start();
   }
   
   public static void init() {
			return;
   }
 
   
   public void closing() {
     this.moduleThread.stop();
     this.modules.clear();
   }
   
   public Instrumentation getInst() {
     return this.inst;
   }
   
   public UIFrame getConcealUI() {
     return this.concealUI;
   }
   
   public List<Mod> getModules() {
     return this.modules;
   }
 }


