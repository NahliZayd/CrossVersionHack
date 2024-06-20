package zn.cvh.client;

import zn.cvh.module.Mod;
import zn.cvh.module.modules.Reach;
import zn.cvh.module.modules.SmoothAimbot;
import zn.cvh.module.modules.Triggerbot;
import zn.cvh.ui.UIFrame;
import zn.cvh.utils.ClassDiscoverer;
import zn.cvh.wrappers.WrapperMinecraft;

import javax.swing.*;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

public class ClientLoader {
    private final Instrumentation inst;
    private UIFrame concealUI;
    private final List<Mod> modules = new ArrayList();
    private final ClientThread moduleThread;
    public JSlider aimbotForce;
    public ClassDiscoverer discoverer;

    public ClientLoader(Instrumentation inst) {
        this.inst = inst;

        discoverer = new ClassDiscoverer(inst);
        new WrapperMinecraft(discoverer.getMinecraftClass());
        try {
            this.modules.add(new SmoothAimbot(this));
            this.modules.add(new Triggerbot());
            this.modules.add(new Reach());
        } catch (Exception e) {
            e.printStackTrace();
        }
        UIFrame concealUI = new UIFrame(this);
        concealUI.show();
        this.moduleThread = new ClientThread(this);
        this.moduleThread.start();
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
