package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.ItemFood;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateHealth;
import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

public class FoodMetaData {
   public int foodLevel = 20;
   public float saturationLevel = 5.0F;
   public float exhaustionLevel;
   private int foodTickTimer;
   private EntityHuman entityhuman;
   private int e = 20;

   public FoodMetaData() {
      throw new AssertionError("Whoopsie, we missed the bukkit.");
   }

   public FoodMetaData(EntityHuman p_i230_1_) {
      Validate.notNull(p_i230_1_);
      this.entityhuman = p_i230_1_;
   }

   public void eat(int p_eat_1_, float p_eat_2_) {
      this.foodLevel = Math.min(p_eat_1_ + this.foodLevel, 20);
      this.saturationLevel = Math.min(this.saturationLevel + (float)p_eat_1_ * p_eat_2_ * 2.0F, (float)this.foodLevel);
   }

   public void a(ItemFood p_a_1_, ItemStack p_a_2_) {
      int i = this.foodLevel;
      FoodLevelChangeEvent foodlevelchangeevent = CraftEventFactory.callFoodLevelChangeEvent(this.entityhuman, p_a_1_.getNutrition(p_a_2_) + i);
      if(!foodlevelchangeevent.isCancelled()) {
         this.eat(foodlevelchangeevent.getFoodLevel() - i, p_a_1_.getSaturationModifier(p_a_2_));
      }

      ((EntityPlayer)this.entityhuman).playerConnection.sendPacket(new PacketPlayOutUpdateHealth(((EntityPlayer)this.entityhuman).getBukkitEntity().getScaledHealth(), this.entityhuman.getFoodData().foodLevel, this.entityhuman.getFoodData().saturationLevel));
   }

   public void a(EntityHuman p_a_1_) {
      EnumDifficulty enumdifficulty = p_a_1_.world.getDifficulty();
      this.e = this.foodLevel;
      if(this.exhaustionLevel > 4.0F) {
         this.exhaustionLevel -= 4.0F;
         if(this.saturationLevel > 0.0F) {
            this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
         } else if(enumdifficulty != EnumDifficulty.PEACEFUL) {
            FoodLevelChangeEvent foodlevelchangeevent = CraftEventFactory.callFoodLevelChangeEvent(p_a_1_, Math.max(this.foodLevel - 1, 0));
            if(!foodlevelchangeevent.isCancelled()) {
               this.foodLevel = foodlevelchangeevent.getFoodLevel();
            }

            ((EntityPlayer)p_a_1_).playerConnection.sendPacket(new PacketPlayOutUpdateHealth(((EntityPlayer)p_a_1_).getBukkitEntity().getScaledHealth(), this.foodLevel, this.saturationLevel));
         }
      }

      if(p_a_1_.world.getGameRules().getBoolean("naturalRegeneration") && this.foodLevel >= 18 && p_a_1_.cm()) {
         ++this.foodTickTimer;
         if(this.foodTickTimer >= 80) {
            p_a_1_.heal(1.0F, RegainReason.SATIATED);
            this.a(p_a_1_.world.spigotConfig.regenExhaustion);
            this.foodTickTimer = 0;
         }
      } else if(this.foodLevel <= 0) {
         ++this.foodTickTimer;
         if(this.foodTickTimer >= 80) {
            if(p_a_1_.getHealth() > 10.0F || enumdifficulty == EnumDifficulty.HARD || p_a_1_.getHealth() > 1.0F && enumdifficulty == EnumDifficulty.NORMAL) {
               p_a_1_.damageEntity(DamageSource.STARVE, 1.0F);
            }

            this.foodTickTimer = 0;
         }
      } else {
         this.foodTickTimer = 0;
      }

   }

   public void a(NBTTagCompound p_a_1_) {
      if(p_a_1_.hasKeyOfType("foodLevel", 99)) {
         this.foodLevel = p_a_1_.getInt("foodLevel");
         this.foodTickTimer = p_a_1_.getInt("foodTickTimer");
         this.saturationLevel = p_a_1_.getFloat("foodSaturationLevel");
         this.exhaustionLevel = p_a_1_.getFloat("foodExhaustionLevel");
      }

   }

   public void b(NBTTagCompound p_b_1_) {
      p_b_1_.setInt("foodLevel", this.foodLevel);
      p_b_1_.setInt("foodTickTimer", this.foodTickTimer);
      p_b_1_.setFloat("foodSaturationLevel", this.saturationLevel);
      p_b_1_.setFloat("foodExhaustionLevel", this.exhaustionLevel);
   }

   public int getFoodLevel() {
      return this.foodLevel;
   }

   public boolean c() {
      return this.foodLevel < 20;
   }

   public void a(float p_a_1_) {
      this.exhaustionLevel = Math.min(this.exhaustionLevel + p_a_1_, 40.0F);
   }

   public float getSaturationLevel() {
      return this.saturationLevel;
   }

   public void a(int p_a_1_) {
      this.foodLevel = p_a_1_;
   }
}
