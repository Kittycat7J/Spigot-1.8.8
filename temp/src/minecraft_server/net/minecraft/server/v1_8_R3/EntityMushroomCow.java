package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityAgeable;
import net.minecraft.server.v1_8_R3.EntityCow;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class EntityMushroomCow extends EntityCow {
   public EntityMushroomCow(World p_i212_1_) {
      super(p_i212_1_);
      this.setSize(0.9F, 1.3F);
      this.bn = Blocks.MYCELIUM;
   }

   public boolean a(EntityHuman p_a_1_) {
      ItemStack itemstack = p_a_1_.inventory.getItemInHand();
      if(itemstack != null && itemstack.getItem() == Items.BOWL && this.getAge() >= 0) {
         if(itemstack.count == 1) {
            p_a_1_.inventory.setItem(p_a_1_.inventory.itemInHandIndex, new ItemStack(Items.MUSHROOM_STEW));
            return true;
         }

         if(p_a_1_.inventory.pickup(new ItemStack(Items.MUSHROOM_STEW)) && !p_a_1_.abilities.canInstantlyBuild) {
            p_a_1_.inventory.splitStack(p_a_1_.inventory.itemInHandIndex, 1);
            return true;
         }
      }

      if(itemstack != null && itemstack.getItem() == Items.SHEARS && this.getAge() >= 0) {
         PlayerShearEntityEvent playershearentityevent = new PlayerShearEntityEvent((Player)p_a_1_.getBukkitEntity(), this.getBukkitEntity());
         this.world.getServer().getPluginManager().callEvent(playershearentityevent);
         if(playershearentityevent.isCancelled()) {
            return false;
         } else {
            this.die();
            this.world.addParticle(EnumParticle.EXPLOSION_LARGE, this.locX, this.locY + (double)(this.length / 2.0F), this.locZ, 0.0D, 0.0D, 0.0D, new int[0]);
            if(!this.world.isClientSide) {
               EntityCow entitycow = new EntityCow(this.world);
               entitycow.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
               entitycow.setHealth(this.getHealth());
               entitycow.aI = this.aI;
               if(this.hasCustomName()) {
                  entitycow.setCustomName(this.getCustomName());
               }

               this.world.addEntity(entitycow);

               for(int i = 0; i < 5; ++i) {
                  this.world.addEntity(new EntityItem(this.world, this.locX, this.locY + (double)this.length, this.locZ, new ItemStack(Blocks.RED_MUSHROOM)));
               }

               itemstack.damage(1, p_a_1_);
               this.makeSound("mob.sheep.shear", 1.0F, 1.0F);
            }

            return true;
         }
      } else {
         return super.a(p_a_1_);
      }
   }

   public EntityMushroomCow c(EntityAgeable p_c_1_) {
      return new EntityMushroomCow(this.world);
   }

   public EntityCow b(EntityAgeable p_b_1_) {
      return this.c(p_b_1_);
   }

   public EntityAgeable createChild(EntityAgeable p_createChild_1_) {
      return this.c(p_createChild_1_);
   }
}
