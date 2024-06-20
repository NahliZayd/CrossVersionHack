package zn.cvh.client;

import zn.cvh.module.Mod;
import zn.cvh.wrappers.WrapperMinecraft;

public class ClientThread extends Thread {
    private final ClientLoader parent;

    public ClientThread(final ClientLoader parent) {
        this.parent = parent;
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    WrapperMinecraft.cPlayer.playerObj = WrapperMinecraft.cPlayer.playerField.get(WrapperMinecraft.getInstance().getMinecraftObject());
                } catch (Exception ex) {
                }
                for (final Mod module : this.parent.getModules()) {
                    if (module.isEnabled()) {
                        module.update();
                        module.postUpdate();
                    }
                }
                Thread.sleep(1000 / 20);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
