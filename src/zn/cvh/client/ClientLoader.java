package zn.cvh.client;

import zn.cvh.module.Mod;
import zn.cvh.module.modules.Reach;
import zn.cvh.module.modules.SmoothAimbot;
import zn.cvh.module.modules.Triggerbot;
import zn.cvh.ui.UIFrame;
import zn.cvh.utils.ClassDiscoverer;
import zn.cvh.wrapper.WrapperManager;

import javax.swing.*;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

public class ClientLoader {
    private Instrumentation inst;
    private UIFrame concealUI;
    private final List<Mod> modules = new ArrayList<>();
    private ClientThread moduleThread;
    public JSlider aimbotForce;
    public ClassDiscoverer discoverer;

    public ClientLoader(Instrumentation inst) {
        if (inst != null) {
            this.inst = inst;
            this.discoverer = new ClassDiscoverer(inst);
            try {
                new WrapperManager(this.discoverer);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        try {
            this.modules.add(new SmoothAimbot());
            this.modules.add(new Triggerbot());
            this.modules.add(new Reach());
        } catch (Exception e) {
            e.printStackTrace();
        }



        SwingUtilities.invokeLater(() -> {
            try {
                this.concealUI = new UIFrame(this);
                concealUI.show();
                System.out.println("UIFrame initialized and shown.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        if (inst != null) {
            this.moduleThread = new ClientThread(this);
            this.moduleThread.start();
        }
    }

    public void closing() {
        if (this.moduleThread != null) {
            this.moduleThread.stop();
        }
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
