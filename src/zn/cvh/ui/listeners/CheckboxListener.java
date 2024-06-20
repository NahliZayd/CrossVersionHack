package zn.cvh.ui.listeners;

import zn.cvh.module.Mod;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CheckboxListener
        implements ItemListener {
    private final Mod mod;

    public CheckboxListener(Mod mod) {
        this.mod = mod;
    }

    public void itemStateChanged(ItemEvent e) {
        this.mod.setEnabled(e.getStateChange() == 1);
    }
}
