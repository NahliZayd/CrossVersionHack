package zn.cvh.module;

import zn.cvh.module.value.Values;
import zn.cvh.wrappers.WrapperMinecraft;

import java.util.ArrayList;

public abstract class Mod {
    private final String name;
    private boolean enabled = false;
    protected static Object mcObj;
    protected static Class<?> mcClass;
    private final ArrayList<Values> values = new ArrayList<Values>();

    public Mod(String name) throws Exception {
        this.name = name;
        mcClass = WrapperMinecraft.getInstance().getMinecraftClass();
        mcObj = WrapperMinecraft.getInstance().getMinecraftObject();
    }

    public void addValue(Values value) {
        this.values.add(value);
    }

    public ArrayList<Values> getValues() {
        return this.values;
    }

    public abstract void update() throws Exception;

    public void postUpdate() {

    }

    public String getName() {
        return this.name;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
