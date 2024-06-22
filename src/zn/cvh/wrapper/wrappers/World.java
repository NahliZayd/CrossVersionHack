package zn.cvh.wrapper.wrappers;

import zn.cvh.wrapper.Wrapper;
import zn.cvh.wrapper.WrapperManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class World extends WrapperOOP {
    private final Field entitiesField;

    private final Field playerEntitiesField;
    private final Object entities; //func_72910_y
    private final Object players; //func_71536_c
    Field worldField = WrapperManager.instance.Minecraft.getClazz().getField("field_71441_e");

    public World(Object obj) throws Exception {
        super(obj);
        //on utilise rh et on utilisera le func_72910_y

        this.entitiesField  = worldField.getType().getField("field_72996_f");
        playerEntitiesField = this.worldField.getType().getField("field_73010_i");
        entities = entitiesField.get(obj);
        players = playerEntitiesField.get(obj);

    }

    public List<Entity> getEntities() throws Exception {
        List<?> objEntitiesList = (List<?>) entities;
        List<Object> entitiesCopy = new ArrayList<>(objEntitiesList);
        List<Entity> entities = new ArrayList<>();

        for (Object o : entitiesCopy) {
            entities.add(new Entity(o));
        }

        return entities;
    }

    public List<EntityPlayer> getPlayers() throws Exception {
        List<?> objEntitiesList = (List<?>) players;
        List<Object> entitiesCopy = new ArrayList<>(objEntitiesList);
        List<EntityPlayer> entities = new ArrayList<>();

        for (Object o : entitiesCopy) {
            entities.add(new EntityPlayer(o));
        }

        return entities;
    }

}
