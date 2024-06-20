package zn.cvh.wrappers;

public class WrapperMinecraft {
    private static WrapperMinecraft cMinecraft;
    public static WrapperEntityPlayer cPlayer;
    private Object minecraftObj;
    private final Class<?> minecraftClass;

    public WrapperMinecraft(Class<?> minecraft) {
        this.minecraftClass = minecraft;
        try {
            this.minecraftObj = minecraft.getMethod("func_71410_x", new Class[0]).invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cMinecraft = this;
        cPlayer = new WrapperEntityPlayer(this);
    }

    public Object getMinecraftObject() {
        return this.minecraftObj;
    }

    public Class<?> getMinecraftClass() {
        return this.minecraftClass;
    }

    public static WrapperMinecraft getInstance() {
        return cMinecraft;
    }
}
