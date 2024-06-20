package zn.cvh.module.value.values;

import zn.cvh.module.value.Values;

public class DoubleValue extends Values {

    private double value;
    private final double min;
    private final double max;

    public DoubleValue(String name, double value, double min, double max) {
        super(name);
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
