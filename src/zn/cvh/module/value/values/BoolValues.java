package zn.cvh.module.value.values;

import zn.cvh.module.value.Values;

public class BoolValues extends Values {
    private boolean value;

    public BoolValues(String name, boolean value) {

        super(name);
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
