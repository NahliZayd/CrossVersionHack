package zn.cvh.wrapper;


import zn.cvh.agent.Agent;
import zn.cvh.utils.ClassDiscoverer;
import zn.cvh.utils.ReflectionHelper;

public abstract class Wrapper {

     public ClassDiscoverer classDiscoverer;
    protected Class<?> clazz;
    protected ReflectionHelper rh;

    public Wrapper() {
        if (WrapperManager.instance != null) {
            classDiscoverer = WrapperManager.instance.classDiscoverer;
        } else {
            throw new IllegalStateException("WrapperManager instance is null. Make sure it is initialized correctly.");
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }
}