package zn.cvh.ui.listeners;

import zn.cvh.module.value.values.DoubleValue;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderListener implements ChangeListener {
    private final DoubleValue value;
    private final JLabel label;

    public SliderListener(DoubleValue value, JLabel label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.value.setValue(((JSlider) e.getSource()).getValue());
        this.label.setText(this.value.getName() + ": " + this.value.getValue());
    }
}
