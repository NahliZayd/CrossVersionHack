package zn.cvh.wrapper.wrappers;


import zn.cvh.wrapper.Wrapper;

public abstract class WrapperOOP extends Wrapper {

    protected final Object instance;

    public WrapperOOP(Object instance) {
        super();
        this.instance = instance;
    }

    public Object getInstance() {
        return instance;
    }
}
