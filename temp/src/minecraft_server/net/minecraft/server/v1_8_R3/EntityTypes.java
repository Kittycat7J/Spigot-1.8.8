package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityBat;
import net.minecraft.server.v1_8_R3.EntityBlaze;
import net.minecraft.server.v1_8_R3.EntityBoat;
import net.minecraft.server.v1_8_R3.EntityCaveSpider;
import net.minecraft.server.v1_8_R3.EntityChicken;
import net.minecraft.server.v1_8_R3.EntityCow;
import net.minecraft.server.v1_8_R3.EntityCreeper;
import net.minecraft.server.v1_8_R3.EntityEgg;
import net.minecraft.server.v1_8_R3.EntityEnderCrystal;
import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.EntityEnderPearl;
import net.minecraft.server.v1_8_R3.EntityEnderSignal;
import net.minecraft.server.v1_8_R3.EntityEnderman;
import net.minecraft.server.v1_8_R3.EntityEndermite;
import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityFallingBlock;
import net.minecraft.server.v1_8_R3.EntityFireworks;
import net.minecraft.server.v1_8_R3.EntityGhast;
import net.minecraft.server.v1_8_R3.EntityGiantZombie;
import net.minecraft.server.v1_8_R3.EntityGuardian;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityIronGolem;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EntityItemFrame;
import net.minecraft.server.v1_8_R3.EntityLargeFireball;
import net.minecraft.server.v1_8_R3.EntityLeash;
import net.minecraft.server.v1_8_R3.EntityLightning;
import net.minecraft.server.v1_8_R3.EntityMagmaCube;
import net.minecraft.server.v1_8_R3.EntityMinecartAbstract;
import net.minecraft.server.v1_8_R3.EntityMinecartChest;
import net.minecraft.server.v1_8_R3.EntityMinecartCommandBlock;
import net.minecraft.server.v1_8_R3.EntityMinecartFurnace;
import net.minecraft.server.v1_8_R3.EntityMinecartHopper;
import net.minecraft.server.v1_8_R3.EntityMinecartMobSpawner;
import net.minecraft.server.v1_8_R3.EntityMinecartRideable;
import net.minecraft.server.v1_8_R3.EntityMinecartTNT;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EntityMushroomCow;
import net.minecraft.server.v1_8_R3.EntityOcelot;
import net.minecraft.server.v1_8_R3.EntityPainting;
import net.minecraft.server.v1_8_R3.EntityPig;
import net.minecraft.server.v1_8_R3.EntityPigZombie;
import net.minecraft.server.v1_8_R3.EntityPotion;
import net.minecraft.server.v1_8_R3.EntityRabbit;
import net.minecraft.server.v1_8_R3.EntitySheep;
import net.minecraft.server.v1_8_R3.EntitySilverfish;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.EntitySmallFireball;
import net.minecraft.server.v1_8_R3.EntitySnowball;
import net.minecraft.server.v1_8_R3.EntitySnowman;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.EntitySquid;
import net.minecraft.server.v1_8_R3.EntityTNTPrimed;
import net.minecraft.server.v1_8_R3.EntityThrownExpBottle;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.EntityWitch;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.EntityWitherSkull;
import net.minecraft.server.v1_8_R3.EntityWolf;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Statistic;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityTypes {
   private static final Logger b = LogManager.getLogger();
   private static final Map<String, Class<? extends Entity>> c = Maps.<String, Class<? extends Entity>>newHashMap();
   private static final Map<Class<? extends Entity>, String> d = Maps.<Class<? extends Entity>, String>newHashMap();
   private static final Map<Integer, Class<? extends Entity>> e = Maps.<Integer, Class<? extends Entity>>newHashMap();
   private static final Map<Class<? extends Entity>, Integer> f = Maps.<Class<? extends Entity>, Integer>newHashMap();
   private static final Map<String, Integer> g = Maps.<String, Integer>newHashMap();
   public static final Map<Integer, EntityTypes.MonsterEggInfo> eggInfo = Maps.<Integer, EntityTypes.MonsterEggInfo>newLinkedHashMap();

   private static void a(Class<? extends Entity> p_a_0_, String p_a_1_, int p_a_2_) {
      if(c.containsKey(p_a_1_)) {
         throw new IllegalArgumentException("ID is already registered: " + p_a_1_);
      } else if(e.containsKey(Integer.valueOf(p_a_2_))) {
         throw new IllegalArgumentException("ID is already registered: " + p_a_2_);
      } else if(p_a_2_ == 0) {
         throw new IllegalArgumentException("Cannot register to reserved id: " + p_a_2_);
      } else if(p_a_0_ == null) {
         throw new IllegalArgumentException("Cannot register null clazz for id: " + p_a_2_);
      } else {
         c.put(p_a_1_, p_a_0_);
         d.put(p_a_0_, p_a_1_);
         e.put(Integer.valueOf(p_a_2_), p_a_0_);
         f.put(p_a_0_, Integer.valueOf(p_a_2_));
         g.put(p_a_1_, Integer.valueOf(p_a_2_));
      }
   }

   private static void a(Class<? extends Entity> p_a_0_, String p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_) {
      a(p_a_0_, p_a_1_, p_a_2_);
      eggInfo.put(Integer.valueOf(p_a_2_), new EntityTypes.MonsterEggInfo(p_a_2_, p_a_3_, p_a_4_));
   }

   public static Entity createEntityByName(String p_createEntityByName_0_, World p_createEntityByName_1_) {
      Entity entity = null;

      try {
         Class oclass = (Class)c.get(p_createEntityByName_0_);
         if(oclass != null) {
            entity = (Entity)oclass.getConstructor(new Class[]{World.class}).newInstance(new Object[]{p_createEntityByName_1_});
         }
      } catch (Exception exception) {
         exception.printStackTrace();
      }

      return entity;
   }

   public static Entity a(NBTTagCompound p_a_0_, World p_a_1_) {
      Entity entity = null;
      if("Minecart".equals(p_a_0_.getString("id"))) {
         p_a_0_.setString("id", EntityMinecartAbstract.EnumMinecartType.a(p_a_0_.getInt("Type")).b());
         p_a_0_.remove("Type");
      }

      try {
         Class oclass = (Class)c.get(p_a_0_.getString("id"));
         if(oclass != null) {
            entity = (Entity)oclass.getConstructor(new Class[]{World.class}).newInstance(new Object[]{p_a_1_});
         }
      } catch (Exception exception) {
         exception.printStackTrace();
      }

      if(entity != null) {
         entity.f(p_a_0_);
      } else {
         b.warn("Skipping Entity with id " + p_a_0_.getString("id"));
      }

      return entity;
   }

   public static Entity a(int p_a_0_, World p_a_1_) {
      Entity entity = null;

      try {
         Class oclass = a(p_a_0_);
         if(oclass != null) {
            entity = (Entity)oclass.getConstructor(new Class[]{World.class}).newInstance(new Object[]{p_a_1_});
         }
      } catch (Exception exception) {
         exception.printStackTrace();
      }

      if(entity == null) {
         b.warn("Skipping Entity with id " + p_a_0_);
      }

      return entity;
   }

   public static int a(Entity p_a_0_) {
      Integer integer = (Integer)f.get(p_a_0_.getClass());
      return integer == null?0:integer.intValue();
   }

   public static Class<? extends Entity> a(int p_a_0_) {
      return (Class)e.get(Integer.valueOf(p_a_0_));
   }

   public static String b(Entity p_b_0_) {
      return (String)d.get(p_b_0_.getClass());
   }

   public static String b(int p_b_0_) {
      return (String)d.get(a(p_b_0_));
   }

   public static void a() {
   }

   public static List<String> b() {
      Set set = c.keySet();
      ArrayList arraylist = Lists.newArrayList();

      for(String s : set) {
         Class oclass = (Class)c.get(s);
         if((oclass.getModifiers() & 1024) != 1024) {
            arraylist.add(s);
         }
      }

      arraylist.add("LightningBolt");
      return arraylist;
   }

   public static boolean a(Entity p_a_0_, String p_a_1_) {
      String s = b(p_a_0_);
      if(s == null && p_a_0_ instanceof EntityHuman) {
         s = "Player";
      } else if(s == null && p_a_0_ instanceof EntityLightning) {
         s = "LightningBolt";
      }

      return p_a_1_.equals(s);
   }

   public static boolean b(String p_b_0_) {
      return "Player".equals(p_b_0_) || b().contains(p_b_0_);
   }

   static {
      a(EntityItem.class, "Item", 1);
      a(EntityExperienceOrb.class, "XPOrb", 2);
      a(EntityEgg.class, "ThrownEgg", 7);
      a(EntityLeash.class, "LeashKnot", 8);
      a(EntityPainting.class, "Painting", 9);
      a(EntityArrow.class, "Arrow", 10);
      a(EntitySnowball.class, "Snowball", 11);
      a(EntityLargeFireball.class, "Fireball", 12);
      a(EntitySmallFireball.class, "SmallFireball", 13);
      a(EntityEnderPearl.class, "ThrownEnderpearl", 14);
      a(EntityEnderSignal.class, "EyeOfEnderSignal", 15);
      a(EntityPotion.class, "ThrownPotion", 16);
      a(EntityThrownExpBottle.class, "ThrownExpBottle", 17);
      a(EntityItemFrame.class, "ItemFrame", 18);
      a(EntityWitherSkull.class, "WitherSkull", 19);
      a(EntityTNTPrimed.class, "PrimedTnt", 20);
      a(EntityFallingBlock.class, "FallingSand", 21);
      a(EntityFireworks.class, "FireworksRocketEntity", 22);
      a(EntityArmorStand.class, "ArmorStand", 30);
      a(EntityBoat.class, "Boat", 41);
      a(EntityMinecartRideable.class, EntityMinecartAbstract.EnumMinecartType.RIDEABLE.b(), 42);
      a(EntityMinecartChest.class, EntityMinecartAbstract.EnumMinecartType.CHEST.b(), 43);
      a(EntityMinecartFurnace.class, EntityMinecartAbstract.EnumMinecartType.FURNACE.b(), 44);
      a(EntityMinecartTNT.class, EntityMinecartAbstract.EnumMinecartType.TNT.b(), 45);
      a(EntityMinecartHopper.class, EntityMinecartAbstract.EnumMinecartType.HOPPER.b(), 46);
      a(EntityMinecartMobSpawner.class, EntityMinecartAbstract.EnumMinecartType.SPAWNER.b(), 47);
      a(EntityMinecartCommandBlock.class, EntityMinecartAbstract.EnumMinecartType.COMMAND_BLOCK.b(), 40);
      a(EntityInsentient.class, "Mob", 48);
      a(EntityMonster.class, "Monster", 49);
      a(EntityCreeper.class, "Creeper", 50, 894731, 0);
      a(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
      a(EntitySpider.class, "Spider", 52, 3419431, 11013646);
      a(EntityGiantZombie.class, "Giant", 53);
      a(EntityZombie.class, "Zombie", 54, '\uafaf', 7969893);
      a(EntitySlime.class, "Slime", 55, 5349438, 8306542);
      a(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
      a(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
      a(EntityEnderman.class, "Enderman", 58, 1447446, 0);
      a(EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
      a(EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
      a(EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
      a(EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
      a(EntityEnderDragon.class, "EnderDragon", 63);
      a(EntityWither.class, "WitherBoss", 64);
      a(EntityBat.class, "Bat", 65, 4996656, 986895);
      a(EntityWitch.class, "Witch", 66, 3407872, 5349438);
      a(EntityEndermite.class, "Endermite", 67, 1447446, 7237230);
      a(EntityGuardian.class, "Guardian", 68, 5931634, 15826224);
      a(EntityPig.class, "Pig", 90, 15771042, 14377823);
      a(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
      a(EntityCow.class, "Cow", 92, 4470310, 10592673);
      a(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
      a(EntitySquid.class, "Squid", 94, 2243405, 7375001);
      a(EntityWolf.class, "Wolf", 95, 14144467, 13545366);
      a(EntityMushroomCow.class, "MushroomCow", 96, 10489616, 12040119);
      a(EntitySnowman.class, "SnowMan", 97);
      a(EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
      a(EntityIronGolem.class, "VillagerGolem", 99);
      a(EntityHorse.class, "EntityHorse", 100, 12623485, 15656192);
      a(EntityRabbit.class, "Rabbit", 101, 10051392, 7555121);
      a(EntityVillager.class, "Villager", 120, 5651507, 12422002);
      a(EntityEnderCrystal.class, "EnderCrystal", 200);
   }

   public static class MonsterEggInfo {
      public final int a;
      public final int b;
      public final int c;
      public final Statistic killEntityStatistic;
      public final Statistic e;

      public MonsterEggInfo(int p_i1144_1_, int p_i1144_2_, int p_i1144_3_) {
         this.a = p_i1144_1_;
         this.b = p_i1144_2_;
         this.c = p_i1144_3_;
         this.killEntityStatistic = StatisticList.a(this);
         this.e = StatisticList.b(this);
      }
   }
}
