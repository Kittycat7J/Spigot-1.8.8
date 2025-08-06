package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityAgeable;
import net.minecraft.server.v1_8_R3.EntityAnimal;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.PathfinderGoalBreed;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalFollowParent;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalPanic;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.PathfinderGoalTempt;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class EntityCow extends EntityAnimal {
   public EntityCow(World p_i96_1_) {
      super(p_i96_1_);
      this.setSize(0.9F, 1.3F);
      ((Navigation)this.getNavigation()).a(true);
      this.goalSelector.a(0, new PathfinderGoalFloat(this));
      this.goalSelector.a(1, new PathfinderGoalPanic(this, 2.0D));
      this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
      this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.25D, Items.WHEAT, false));
      this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.25D));
      this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
      this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
      this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
   }

   protected void initAttributes() {
      super.initAttributes();
      this.getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
      this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.20000000298023224D);
   }

   protected String z() {
      return "mob.cow.say";
   }

   protected String bo() {
      return "mob.cow.hurt";
   }

   protected String bp() {
      return "mob.cow.hurt";
   }

   protected void a(BlockPosition p_a_1_, Block p_a_2_) {
      this.makeSound("mob.cow.step", 0.15F, 1.0F);
   }

   protected float bB() {
      return 0.4F;
   }

   protected Item getLoot() {
      return Items.LEATHER;
   }

   protected void dropDeathLoot(boolean p_dropDeathLoot_1_, int p_dropDeathLoot_2_) {
      int i = this.random.nextInt(3) + this.random.nextInt(1 + p_dropDeathLoot_2_);

      for(int j = 0; j < i; ++j) {
         this.a(Items.LEATHER, 1);
      }

      i = this.random.nextInt(3) + 1 + this.random.nextInt(1 + p_dropDeathLoot_2_);

      for(int k1 = 0; k1 < i; ++k1) {
         if(this.isBurning()) {
            this.a(Items.COOKED_BEEF, 1);
         } else {
            this.a(Items.BEEF, 1);
         }
      }

   }

   public boolean a(EntityHuman p_a_1_) {
      ItemStack itemstack = p_a_1_.inventory.getItemInHand();
      if(itemstack != null && itemstack.getItem() == Items.BUCKET && !p_a_1_.abilities.canInstantlyBuild && !this.isBaby()) {
         Location location = this.getBukkitEntity().getLocation();
         PlayerBucketFillEvent playerbucketfillevent = CraftEventFactory.callPlayerBucketFillEvent(p_a_1_, location.getBlockX(), location.getBlockY(), location.getBlockZ(), (EnumDirection)null, itemstack, Items.MILK_BUCKET);
         if(playerbucketfillevent.isCancelled()) {
            return false;
         } else {
            ItemStack itemstack1 = CraftItemStack.asNMSCopy(playerbucketfillevent.getItemStack());
            if(--itemstack.count <= 0) {
               p_a_1_.inventory.setItem(p_a_1_.inventory.itemInHandIndex, itemstack1);
            } else if(!p_a_1_.inventory.pickup(itemstack1)) {
               p_a_1_.drop(itemstack1, false);
            }

            return true;
         }
      } else {
         return super.a(p_a_1_);
      }
   }

   public EntityCow b(EntityAgeable p_b_1_) {
      return new EntityCow(this.world);
   }

   public float getHeadHeight() {
      return this.length;
   }

   public EntityAgeable createChild(EntityAgeable p_createChild_1_) {
      return this.b(p_createChild_1_);
   }
}
