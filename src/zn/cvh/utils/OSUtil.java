package zn.cvh.utils;

import java.io.File;

public class OSUtil {
    public static String getPlatform() {
        final String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return "WINDOWS";
        }
        if (osName.contains("mac")) {
            return "MAC";
        }
        return "OTHER";
    }

    public static File getWorkingDirectory() {
        final String userHome = System.getProperty("user.home", ".");
        final String platform;
        switch (platform = getPlatform()) {
            case "MAC": {
                final File workingDirectory = new File(userHome, "Library/Application Support/minecraft");
                return workingDirectory;
            }
            case "WINDOWS": {
                final String applicationData = System.getenv("APPDATA");
                final String folder = (applicationData != null) ? applicationData : userHome;
                final File workingDirectory = new File(folder, ".minecraft/");
                return workingDirectory;
            }
            default:
                break;
        }
        final File workingDirectory = new File(userHome, "minecraft/");
        return workingDirectory;
    }
}
