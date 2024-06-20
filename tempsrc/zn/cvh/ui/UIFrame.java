package zn.cvh.ui; import zn.cvh.client.ClientLoader;
import zn.cvh.module.Mod;

import javax.swing.JCheckBox;
 import javax.swing.JFrame;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import javax.swing.JSlider;
 
 
 
 public class UIFrame
 {
   private ClientLoader parent;
   
   public UIFrame(ClientLoader parent) {
     this.parent = parent;
   }
   
   public void show() {
     JFrame frame = new JFrame("Xerxes Ghost Client");
     frame.addWindowListener(new FrameListener(this.parent));
     frame.setVisible(true);
     JPanel main = new JPanel();
     
     for (Mod mod : this.parent.getModules()) {
       JCheckBox moduleCheckbox = new JCheckBox(mod.getName());
       moduleCheckbox.addItemListener(new CheckboxListener(mod));
       main.add(moduleCheckbox);
     } 
     
     JLabel forceLabel = new JLabel("Aimbot force: ");
     JSlider forceInput = new JSlider(0, 1000, 500);
     
     main.add(forceLabel);
     main.add(forceInput);
     
     frame.add(main);
     frame.pack();
     frame.setSize(300, 135);
     
     this.parent.aimbotForce = forceInput;
   }
 }


