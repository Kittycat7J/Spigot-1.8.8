package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.HashMap;
import net.minecraft.server.v1_8_R3.EntityBat;
import net.minecraft.server.v1_8_R3.EntityBlaze;
import net.minecraft.server.v1_8_R3.EntityCaveSpider;
import net.minecraft.server.v1_8_R3.EntityChicken;
import net.minecraft.server.v1_8_R3.EntityCow;
import net.minecraft.server.v1_8_R3.EntityCreeper;
import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.EntityEnderman;
import net.minecraft.server.v1_8_R3.EntityEndermite;
import net.minecraft.server.v1_8_R3.EntityGhast;
import net.minecraft.server.v1_8_R3.EntityGiantZombie;
import net.minecraft.server.v1_8_R3.EntityGuardian;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityIronGolem;
import net.minecraft.server.v1_8_R3.EntityMagmaCube;
import net.minecraft.server.v1_8_R3.EntityMushroomCow;
import net.minecraft.server.v1_8_R3.EntityOcelot;
import net.minecraft.server.v1_8_R3.EntityPig;
import net.minecraft.server.v1_8_R3.EntityPigZombie;
import net.minecraft.server.v1_8_R3.EntityRabbit;
import net.minecraft.server.v1_8_R3.EntitySheep;
import net.minecraft.server.v1_8_R3.EntitySilverfish;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.EntitySnowman;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.EntitySquid;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.EntityWitch;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.EntityWolf;
import net.minecraft.server.v1_8_R3.EntityZombie;

public class EntityPositionTypes {
   private static final HashMap<Class, EntityInsentient.EnumEntityPositionType> a = Maps.<Class, EntityInsentient.EnumEntityPositionType>newHashMap();

   public static EntityInsentient.EnumEntityPositionType a(Class p_a_0_) {
      return (EntityInsentient.EnumEntityPositionType)a.get(p_a_0_);
   }

   static {
      a.put(EntityBat.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityChicken.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityCow.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityHorse.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityMushroomCow.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityOcelot.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityPig.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityRabbit.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntitySheep.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntitySnowman.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntitySquid.class, EntityInsentient.EnumEntityPositionType.IN_WATER);
      a.put(EntityIronGolem.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityWolf.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityVillager.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityEnderDragon.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityWither.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityBlaze.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityCaveSpider.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityCreeper.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityEnderman.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityEndermite.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityGhast.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityGiantZombie.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityGuardian.class, EntityInsentient.EnumEntityPositionType.IN_WATER);
      a.put(EntityMagmaCube.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityPigZombie.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntitySilverfish.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntitySkeleton.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntitySlime.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntitySpider.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityWitch.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
      a.put(EntityZombie.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
   }
}
