package zn.cvh.client;

import zn.cvh.module.Mod;
import zn.cvh.wrapper.WrapperManager;

public class ClientThread extends Thread {
    private final ClientLoader parent;

    public ClientThread(final ClientLoader parent) {
        this.parent = parent;
    }

    @Override
    public void run() {

            while (true) {



                for (final Mod module : this.parent.getModules()) {
                    if (module.isEnabled()) {
                        try {
                            module.update();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        module.postUpdate();
                    }
                }
                try {
                    Thread.sleep(1000 / 20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }

}
