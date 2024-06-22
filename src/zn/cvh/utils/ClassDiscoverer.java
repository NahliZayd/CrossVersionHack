package zn.cvh.utils;

import java.lang.instrument.Instrumentation;

public class ClassDiscoverer {
    private Class<?> minecraftClass;
    private Class<?> axisAlignedBBClass;
    private Class<?> threadExecutor;
    private Class<?> entityLivingBaseClass;
    private Class<?> entityJoinClass;

    public ClassDiscoverer(Instrumentation instrumentation) {
        Class[] arrclass = instrumentation.getAllLoadedClasses();
        int n = arrclass.length;
        int n2 = 0;
        while (n2 < n) {
            Class clazz = arrclass[n2];
            if (clazz.getName().equals("net.minecraft.client.Minecraft")) {
                this.minecraftClass = clazz;
            }
            if (clazz.getName().equals("net.minecraft.util.AxisAlignedBB") || clazz.getName().equals("net.minecraft.util.math.AxisAlignedBB")) {
                this.axisAlignedBBClass = clazz;
            }
            if(clazz.getName().equals("net.minecraft.client.util.ThreadExecutor")) {
                this.threadExecutor = clazz;
            }
            if(clazz.getName().equals("net.minecraft.entity.EntityLivingBase")) {
                this.entityLivingBaseClass = clazz;
            }
            if(clazz.getName().equals(" net.minecraftforge.event.entity.EntityJoinWorldEvent")) {
                this.entityJoinClass = clazz;
            }
            ++n2;
        }
    }

    public Class<?> getThreadExecutor() {
        return this.threadExecutor;
    }



    public Class<?> getMinecraftClass() {
        return this.minecraftClass;
    }

    public Class<?> getAxisAlignedBBClass() {
        return this.axisAlignedBBClass;
    }

    public Class<?> getEntityLivingBaseClass() {
        return this.entityLivingBaseClass;
    }

    public Class<?> getEntityJoinClass() {
        return this.entityJoinClass;
    }
}
