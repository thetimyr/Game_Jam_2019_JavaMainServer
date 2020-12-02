package SkyCrafting.Main.Entyti;

import SkyCrafting.Main.Entyti.quest.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.*;
import java.lang.reflect.*;

public enum EntityTypes
{
	SYKA("Квай-Гон_Джинн", 54, (Class<? extends Entity>)EntityEblan.class),
    ZOMBIE("Пустынник", 54, (Class<? extends Entity>)EntityZombie.class), 
    Pointer("Главарь точек", 57, (Class<? extends Entity>)EntytiDronLOH.class), 
    DefPointer("Охранник точек", 54, (Class<? extends Entity>)DefPointer.class), 
    MUDAK("Пустынник", 66, (Class<? extends Entity>)Mudak.class), 
    PAVUK("Павук шнюк - пещернодрочащий", 59, (Class<? extends Entity>)EntityPavuk.class), 
    BOSSPAVUK("Босс Павуков-шнюков", 52, (Class<? extends Entity>)BossPavuk.class),
    ZOMBIEOTRECK("Отрек1", 57, (Class<? extends Entity>)EntityOtreck1.class), 
    ZOMBIEOTRECK2("Отрек1", 57, (Class<? extends Entity>)EntityOtreck2.class), 
    ZOMBIEOTRECK3("Отрек1", 57, (Class<? extends Entity>)EntityOtreck3.class), 
    JOCKEY("Клон", 51, (Class<? extends Entity>)EntityJockey.class), 
    DARTOXR("Охраник дарта", 51, (Class<? extends Entity>)EntityDartOxr.class), 
    POV("Повстанец", 54, (Class<? extends Entity>)EntityPovst.class), 
    Master("Мастер Камино", 51, (Class<? extends Entity>)EntityMaster.class), 
    Slime("Слайм", 55, (Class<? extends Entity>)EntitySlimeCustom.class), 
    Chubaka("Чубака", 54, (Class<? extends Entity>)EntityChubaka1.class), 
    Mini("Миньйон", 55, (Class<? extends Entity>)EntitySlimeTEMP.class), 
    Mini1("Миньйон1", 55, (Class<? extends Entity>)EntitySlimeTEMP1.class), 
    Lucar("Темный рыцарь", 54, (Class<? extends Entity>)EntityLucar.class), 
    Yoda("ЧИКСА", 54, (Class<? extends Entity>)EntityYoda.class), 
    Voin("Воин джаббы", 54, (Class<? extends Entity>)EntityVoin.class), 
    Shtorm("Штурмовик", 54, (Class<? extends Entity>)EntityShtorm.class), 
    Povstanes("Усиленный Повстанец", 54, (Class<? extends Entity>)EntityPovstanes.class), 
    Dart("Дарт вейдер", 54, (Class<? extends Entity>)EntityDart.class),
    BobaFett("Бобафетт", 54, (Class<? extends Entity>)EntityBobaFett.class),
    Rey("Рей", 54, (Class<? extends Entity>)EntityRey.class),
    Obivan("Обиван", 54, (Class<? extends Entity>)EntityObivan.class),
    Vindy("Винду", 54, (Class<? extends Entity>)EntityVindy.class),
    Adge("Айд-жи", 54, (Class<? extends Entity>)EntityAdge.class), 
    Vuki("Эвоки", 54, (Class<? extends Entity>)EntityVuki.class), 
    P("Палпатин", 54, (Class<? extends Entity>)EntityPalpatin.class), 
    P1("Охраник Палпатина", 54, (Class<? extends Entity>)EntityPolty.class), 
    D("Дроид", 58, (Class<? extends Entity>)EntityDroid.class), 
    O("Охотник за головами", 54, (Class<? extends Entity>)EntityOxotnik.class), 
    Golem("Босс хота", 99, (Class<? extends Entity>)EntityIronGolem.class), 
    R2D2("R2D21", 99, (Class<? extends Entity>)EntityR2D2.class), 
    RD("R2D2", 54, (Class<? extends Entity>)R2D2.class), 
    Shudow("Гигант", 53, (Class<? extends Entity>)EntityShudov.class), 
    Shudow1("Гигант", 53, (Class<? extends Entity>)EntityShudov1.class), 
    Magma("Лавовый куб", 62, (Class<? extends Entity>)EntityMagma.class), 
    TemnuySlizen("Темный слизень", 62, (Class<? extends Entity>)EntityTemnuySlizen.class), 
    Mol("Дарт Мол", 54, (Class<? extends Entity>)EntityDartmol.class), 
    Jabba("Джаба", 55, (Class<? extends Entity>)EntitySlimeJabba.class),
    COW("Корова", 92, (Class<? extends Entity>)PooCow.class);
	
    
    private static Map<Integer, UUID> assoc;
    String name;
    int id;
    Class<? extends Entity> custom;
    
    static {
        EntityTypes.assoc = new HashMap<Integer, UUID>();
    }
    
    private EntityTypes(final String name, final int id, final Class<? extends Entity> custom) {
        this.name = name;
        this.id = id;
        addToMaps(this.custom = custom, name, id);
    }
    
    public static Entity spawnEntity(final Entity entity, final Location loc) {
        entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftWorld)loc.getWorld()).getHandle().addEntity(entity);
        return entity;
    }
    
    @SuppressWarnings("null")
	public static void spawnEntity(final EntityTypes entityType, final Location loc, final Spawner spawner) {
        try {
            final Entity entity = (Entity)entityType.getEntityClass().getConstructor(Spawner.class).newInstance(spawner);
            entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
            ((CraftWorld)loc.getWorld()).getHandle().addEntity(entity);
        }
        catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex2) {
            final Exception ex = null;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("rawtypes")
	private static void addToMaps(final Class clazz, final String name, final int id) {
        ((Map)getPrivateField("c", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(name, clazz);
        ((Map)getPrivateField("d", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, name);
        ((Map)getPrivateField("f", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, id);
    }
    
    public static Object getPrivateField(final String fieldName, final Class<?> clazz, final PathfinderGoalSelector object) {
        Object o = null;
        try {
            final Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        return o;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Class<? extends Entity> getEntityClass() {
        return this.custom;
    }
    
    public static void associate(final Entity entity, final Spawner spawner) {
        EntityTypes.assoc.put(entity.hashCode(), spawner.getUid());
    }
    
    public static Spawner getSpawnerByEntity(final Entity entity) {
        return Spawner.spawners.get(EntityTypes.assoc.get(entity.hashCode()));
    }
}
