package zn.cvh.ui;

import zn.cvh.client.ClientLoader;
import zn.cvh.module.Mod;
import zn.cvh.module.value.Values;
import zn.cvh.module.value.values.BoolValues;
import zn.cvh.module.value.values.DoubleValue;
import zn.cvh.ui.listeners.CheckboxListener;
import zn.cvh.ui.listeners.FrameListener;
import zn.cvh.ui.listeners.SliderListener;

import javax.swing.*;

public class UIFrame {
    public static JTextArea debugArea;
    public ClientLoader parent;
    Class<?> axisAlignedBBClass;

    public UIFrame(ClientLoader parent) {

        this.parent = parent;
    }

    public void show() {
        JFrame frame = new JFrame("Cross Version Hack By Zayd");
        if (this.parent != null) {
            frame.addWindowListener(new FrameListener(this.parent));
        }

        frame.setVisible(true);


        JTabbedPane main = new JTabbedPane();


        for (Mod mod : this.parent.getModules()) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JCheckBox moduleCheckbox = new JCheckBox(mod.getName());
            moduleCheckbox.addItemListener(new CheckboxListener(mod));
            panel.add(moduleCheckbox);

            for (Values value : mod.getValues()) {
                if (value instanceof DoubleValue) {

                    JLabel label = new JLabel(value.getName() + ": " + ((DoubleValue) value).getValue());
                    JSlider slider = new JSlider((int) ((DoubleValue) value).getMin(), (int) ((DoubleValue) value).getMax(), (int) ((DoubleValue) value).getValue());
                    slider.addChangeListener(new SliderListener((DoubleValue) value, label));
                    panel.add(label);
                    panel.add(slider);
                } else if (value instanceof BoolValues) {
                    JCheckBox checkbox = new JCheckBox(value.getName());
                    panel.add(checkbox);
                }
            }


            main.addTab(mod.getName(), panel);
        }

        frame.add(main);
        frame.pack();
        frame.setSize(400, 300);
        frame.setResizable(true);
    }
}