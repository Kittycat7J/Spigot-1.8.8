package net.minecraft.server.v1_8_R3;

import java.util.Collection;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.AttributeMapBase;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.AttributeRanged;
import net.minecraft.server.v1_8_R3.IAttribute;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spigotmc.SpigotConfig;

public class GenericAttributes {
   private static final Logger f = LogManager.getLogger();
   public static final IAttribute maxHealth = (new AttributeRanged((IAttribute)null, "generic.maxHealth", 20.0D, 0.1D, SpigotConfig.maxHealth)).a("Max Health").a(true);
   public static final IAttribute FOLLOW_RANGE = (new AttributeRanged((IAttribute)null, "generic.followRange", 32.0D, 0.0D, 2048.0D)).a("Follow Range");
   public static final IAttribute c = (new AttributeRanged((IAttribute)null, "generic.knockbackResistance", 0.0D, 0.0D, 1.0D)).a("Knockback Resistance");
   public static final IAttribute MOVEMENT_SPEED = (new AttributeRanged((IAttribute)null, "generic.movementSpeed", 0.699999988079071D, 0.0D, SpigotConfig.movementSpeed)).a("Movement Speed").a(true);
   public static final IAttribute ATTACK_DAMAGE = new AttributeRanged((IAttribute)null, "generic.attackDamage", 2.0D, 0.0D, SpigotConfig.attackDamage);

   public static NBTTagList a(AttributeMapBase p_a_0_) {
      NBTTagList nbttaglist = new NBTTagList();

      for(AttributeInstance attributeinstance : p_a_0_.a()) {
         nbttaglist.add(a(attributeinstance));
      }

      return nbttaglist;
   }

   private static NBTTagCompound a(AttributeInstance p_a_0_) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      IAttribute iattribute = p_a_0_.getAttribute();
      nbttagcompound.setString("Name", iattribute.getName());
      nbttagcompound.setDouble("Base", p_a_0_.b());
      Collection collection = p_a_0_.c();
      if(collection != null && !collection.isEmpty()) {
         NBTTagList nbttaglist = new NBTTagList();

         for(AttributeModifier attributemodifier : collection) {
            if(attributemodifier.e()) {
               nbttaglist.add(a(attributemodifier));
            }
         }

         nbttagcompound.set("Modifiers", nbttaglist);
      }

      return nbttagcompound;
   }

   private static NBTTagCompound a(AttributeModifier p_a_0_) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.setString("Name", p_a_0_.b());
      nbttagcompound.setDouble("Amount", p_a_0_.d());
      nbttagcompound.setInt("Operation", p_a_0_.c());
      nbttagcompound.setLong("UUIDMost", p_a_0_.a().getMostSignificantBits());
      nbttagcompound.setLong("UUIDLeast", p_a_0_.a().getLeastSignificantBits());
      return nbttagcompound;
   }

   public static void a(AttributeMapBase p_a_0_, NBTTagList p_a_1_) {
      for(int i = 0; i < p_a_1_.size(); ++i) {
         NBTTagCompound nbttagcompound = p_a_1_.get(i);
         AttributeInstance attributeinstance = p_a_0_.a(nbttagcompound.getString("Name"));
         if(attributeinstance != null) {
            a(attributeinstance, nbttagcompound);
         } else {
            f.warn("Ignoring unknown attribute \'" + nbttagcompound.getString("Name") + "\'");
         }
      }

   }

   private static void a(AttributeInstance p_a_0_, NBTTagCompound p_a_1_) {
      p_a_0_.setValue(p_a_1_.getDouble("Base"));
      if(p_a_1_.hasKeyOfType("Modifiers", 9)) {
         NBTTagList nbttaglist = p_a_1_.getList("Modifiers", 10);

         for(int i = 0; i < nbttaglist.size(); ++i) {
            AttributeModifier attributemodifier = a(nbttaglist.get(i));
            if(attributemodifier != null) {
               AttributeModifier attributemodifier1 = p_a_0_.a(attributemodifier.a());
               if(attributemodifier1 != null) {
                  p_a_0_.c(attributemodifier1);
               }

               p_a_0_.b(attributemodifier);
            }
         }
      }

   }

   public static AttributeModifier a(NBTTagCompound p_a_0_) {
      UUID uuid = new UUID(p_a_0_.getLong("UUIDMost"), p_a_0_.getLong("UUIDLeast"));

      try {
         return new AttributeModifier(uuid, p_a_0_.getString("Name"), p_a_0_.getDouble("Amount"), p_a_0_.getInt("Operation"));
      } catch (Exception exception) {
         f.warn("Unable to create attribute: " + exception.getMessage());
         return null;
      }
   }
}
