package zn.cvh.wrapper.wrappers;

import zn.cvh.wrapper.WrapperManager;

public class EntityPlayer extends Entity {

        public EntityPlayer(Object obj) throws NoSuchFieldException {
            super(obj);
        }

        public EntityPlayer() throws Exception {
            this( WrapperManager.instance.Minecraft.instance().getClass().getField("field_71439_g").get(WrapperManager.instance.Minecraft.instance()));
        }


}
