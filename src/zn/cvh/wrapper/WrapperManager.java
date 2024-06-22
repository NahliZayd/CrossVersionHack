package zn.cvh.wrapper;

import zn.cvh.utils.ClassDiscoverer;
import zn.cvh.wrapper.wrappers.MinecraftClient;
import zn.cvh.wrapper.wrappers.World;

import java.util.logging.Logger;

public class WrapperManager {
    private static final Logger LOGGER = Logger.getLogger(WrapperManager.class.getName());

    public static WrapperManager instance;
    public MinecraftClient Minecraft;

    public ClassDiscoverer classDiscoverer;


    public WrapperManager(ClassDiscoverer discoverer) throws Exception {
        LOGGER.info("Initializing WrapperManager...");
        instance = this;
        try {
            classDiscoverer = discoverer;
            LOGGER.info("ClassDiscoverer initialized.");

            Minecraft = new MinecraftClient();
            LOGGER.info("MinecraftClient initialized.");



            LOGGER.info("WrapperManager instance created successfully.");
        } catch (Exception e) {
            LOGGER.severe("Failed to initialize WrapperManager: " + e.getMessage());
            e.printStackTrace(); // Affiche la trace complète de l'exception
            throw e;
        }
    }

}
