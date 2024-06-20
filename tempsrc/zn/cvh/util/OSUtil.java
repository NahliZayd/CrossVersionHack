package zn.cvh.util; import java.io.File;
 
 
 
 public class OSUtil
 {
    private static File workingDirectory;

       public static String getPlatform() {
     String osName = System.getProperty("os.name").toLowerCase();
     
     if (osName.contains("win")) {
       return "WINDOWS";
     }
     
     if (osName.contains("mac")) {
       return "MAC";
     }
     
     return "OTHER";
   }
   
   public static File getWorkingDirectory() {
     String applicationData, folder, userHome = System.getProperty("user.home", ".");
     
     String str1;
     switch ((str1 = getPlatform()).hashCode()) { case 76079: if (!str1.equals("MAC")) {
           break;
         }
 
 
 
 
         
         workingDirectory = new File(userHome, "Library/Application Support/minecraft");
 
 
 
 
         
         return workingDirectory;case 2067476067: if (!str1.equals("WINDOWS")) break;  applicationData = System.getenv("APPDATA"); folder = (applicationData != null) ? applicationData : userHome; workingDirectory = new File(folder, ".minecraft/"); return workingDirectory; }  File workingDirectory = new File(userHome, "minecraft/"); return workingDirectory;
   }
 }


