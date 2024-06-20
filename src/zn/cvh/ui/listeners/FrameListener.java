package zn.cvh.ui.listeners;

import zn.cvh.client.ClientLoader;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FrameListener
        implements WindowListener {
    private final ClientLoader parent;

    public FrameListener(ClientLoader parent) {
        this.parent = parent;
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        try {
            this.parent.closing();
        } catch (Exception exception) {

        }
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
}
