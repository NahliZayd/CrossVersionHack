package zn.cvh.wrapper.wrappers;

import zn.cvh.agent.Agent;
import zn.cvh.utils.ReflectionHelper;
import zn.cvh.wrapper.Wrapper;
import zn.cvh.wrapper.WrapperManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class MinecraftClient extends Wrapper {

    World world;
    EntityPlayer thePlayer;
    Field inGameHasFocus ;
    private  Field objectMouseOverField ;
    private Field entityHitField;

    public MinecraftClient() {
        super();
        clazz = classDiscoverer.getMinecraftClass();
        rh = new ReflectionHelper(clazz);

    }

    public Object instance() throws Exception {
        return clazz.getMethod("func_71410_x").invoke(null);
    }


    public Object getEntityHit() throws Exception {
        objectMouseOverField = getClazz().getField("field_71476_x");
        this.objectMouseOverField.setAccessible(true);
        this.entityHitField = this.objectMouseOverField.getType().getField("field_72308_g");
        return entityHitField.get(objectMouseOverField.get(instance()));
    }

    public boolean inGameHasFocus() throws Exception {
        this.inGameHasFocus = getClazz().getField("field_71415_G");
        return inGameHasFocus.get(instance()).equals(true);
    }

    public Object getPlayerObj() throws Exception {
        return rh.getField("field_71439_g", instance());
    }

    public EntityPlayer getPlayer() throws Exception {
        if(thePlayer == null) thePlayer = new EntityPlayer(getPlayerObj());
        return thePlayer;// new EntityPlayer(getPlayerObj());
    }

    public Object getWorldObj() throws Exception {
        return rh.getField("field_71441_e", instance());
    }

    public World getWorld() throws Exception {
        if(world == null) world = new World(getWorldObj());
        return world;
    }


}