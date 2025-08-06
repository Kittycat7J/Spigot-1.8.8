package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockDispenser;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IDispenseBehavior;
import net.minecraft.server.v1_8_R3.IPosition;
import net.minecraft.server.v1_8_R3.ISourceBlock;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;

public class DispenseBehaviorItem implements IDispenseBehavior {
   public final ItemStack a(ISourceBlock p_a_1_, ItemStack p_a_2_) {
      ItemStack itemstack = this.b(p_a_1_, p_a_2_);
      this.a(p_a_1_);
      this.a(p_a_1_, BlockDispenser.b(p_a_1_.f()));
      return itemstack;
   }

   protected ItemStack b(ISourceBlock p_b_1_, ItemStack p_b_2_) {
      EnumDirection enumdirection = BlockDispenser.b(p_b_1_.f());
      BlockDispenser.a(p_b_1_);
      ItemStack itemstack = p_b_2_.cloneAndSubtract(1);
      if(!a(p_b_1_.getWorld(), itemstack, 6, enumdirection, p_b_1_)) {
         ++p_b_2_.count;
      }

      return p_b_2_;
   }

   public static boolean a(World p_a_0_, ItemStack p_a_1_, int p_a_2_, EnumDirection p_a_3_, ISourceBlock p_a_4_) {
      IPosition iposition = BlockDispenser.a(p_a_4_);
      double d0 = iposition.getX();
      double d1 = iposition.getY();
      double d2 = iposition.getZ();
      if(p_a_3_.k() == EnumDirection.EnumAxis.Y) {
         d1 = d1 - 0.125D;
      } else {
         d1 = d1 - 0.15625D;
      }

      EntityItem entityitem = new EntityItem(p_a_0_, d0, d1, d2, p_a_1_);
      double d3 = p_a_0_.random.nextDouble() * 0.1D + 0.2D;
      entityitem.motX = (double)p_a_3_.getAdjacentX() * d3;
      entityitem.motY = 0.20000000298023224D;
      entityitem.motZ = (double)p_a_3_.getAdjacentZ() * d3;
      entityitem.motX += p_a_0_.random.nextGaussian() * 0.007499999832361937D * (double)p_a_2_;
      entityitem.motY += p_a_0_.random.nextGaussian() * 0.007499999832361937D * (double)p_a_2_;
      entityitem.motZ += p_a_0_.random.nextGaussian() * 0.007499999832361937D * (double)p_a_2_;
      org.bukkit.block.Block block = p_a_0_.getWorld().getBlockAt(p_a_4_.getBlockPosition().getX(), p_a_4_.getBlockPosition().getY(), p_a_4_.getBlockPosition().getZ());
      CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(p_a_1_);
      BlockDispenseEvent blockdispenseevent = new BlockDispenseEvent(block, craftitemstack.clone(), new Vector(entityitem.motX, entityitem.motY, entityitem.motZ));
      if(!BlockDispenser.eventFired) {
         p_a_0_.getServer().getPluginManager().callEvent(blockdispenseevent);
      }

      if(blockdispenseevent.isCancelled()) {
         return false;
      } else {
         entityitem.setItemStack(CraftItemStack.asNMSCopy(blockdispenseevent.getItem()));
         entityitem.motX = blockdispenseevent.getVelocity().getX();
         entityitem.motY = blockdispenseevent.getVelocity().getY();
         entityitem.motZ = blockdispenseevent.getVelocity().getZ();
         if(blockdispenseevent.getItem().getType().equals(craftitemstack.getType())) {
            p_a_0_.addEntity(entityitem);
            return true;
         } else {
            ItemStack itemstack = CraftItemStack.asNMSCopy(blockdispenseevent.getItem());
            IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(itemstack.getItem());
            if(idispensebehavior != IDispenseBehavior.NONE && idispensebehavior.getClass() != DispenseBehaviorItem.class) {
               idispensebehavior.a(p_a_4_, itemstack);
            } else {
               p_a_0_.addEntity(entityitem);
            }

            return false;
         }
      }
   }

   protected void a(ISourceBlock p_a_1_) {
      p_a_1_.getWorld().triggerEffect(1000, p_a_1_.getBlockPosition(), 0);
   }

   protected void a(ISourceBlock p_a_1_, EnumDirection p_a_2_) {
      p_a_1_.getWorld().triggerEffect(2000, p_a_1_.getBlockPosition(), this.a(p_a_2_));
   }

   private int a(EnumDirection p_a_1_) {
      return p_a_1_.getAdjacentX() + 1 + (p_a_1_.getAdjacentZ() + 1) * 3;
   }
}
