package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockDispenser;
import net.minecraft.server.v1_8_R3.DispenseBehaviorItem;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IDispenseBehavior;
import net.minecraft.server.v1_8_R3.IPosition;
import net.minecraft.server.v1_8_R3.IProjectile;
import net.minecraft.server.v1_8_R3.ISourceBlock;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.TileEntityDispenser;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.projectiles.CraftBlockProjectileSource;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;

public abstract class DispenseBehaviorProjectile extends DispenseBehaviorItem {
   public ItemStack b(ISourceBlock p_b_1_, ItemStack p_b_2_) {
      World world = p_b_1_.getWorld();
      IPosition iposition = BlockDispenser.a(p_b_1_);
      EnumDirection enumdirection = BlockDispenser.b(p_b_1_.f());
      IProjectile iprojectile = this.a(world, iposition);
      ItemStack itemstack = p_b_2_.cloneAndSubtract(1);
      org.bukkit.block.Block block = world.getWorld().getBlockAt(p_b_1_.getBlockPosition().getX(), p_b_1_.getBlockPosition().getY(), p_b_1_.getBlockPosition().getZ());
      CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(itemstack);
      BlockDispenseEvent blockdispenseevent = new BlockDispenseEvent(block, craftitemstack.clone(), new Vector((double)enumdirection.getAdjacentX(), (double)((float)enumdirection.getAdjacentY() + 0.1F), (double)enumdirection.getAdjacentZ()));
      if(!BlockDispenser.eventFired) {
         world.getServer().getPluginManager().callEvent(blockdispenseevent);
      }

      if(blockdispenseevent.isCancelled()) {
         ++p_b_2_.count;
         return p_b_2_;
      } else {
         if(!blockdispenseevent.getItem().equals(craftitemstack)) {
            ++p_b_2_.count;
            ItemStack itemstack1 = CraftItemStack.asNMSCopy(blockdispenseevent.getItem());
            IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(itemstack1.getItem());
            if(idispensebehavior != IDispenseBehavior.NONE && idispensebehavior != this) {
               idispensebehavior.a(p_b_1_, itemstack1);
               return p_b_2_;
            }
         }

         iprojectile.shoot(blockdispenseevent.getVelocity().getX(), blockdispenseevent.getVelocity().getY(), blockdispenseevent.getVelocity().getZ(), this.getPower(), this.a());
         ((Entity)iprojectile).projectileSource = new CraftBlockProjectileSource((TileEntityDispenser)p_b_1_.getTileEntity());
         world.addEntity((Entity)iprojectile);
         return p_b_2_;
      }
   }

   protected void a(ISourceBlock p_a_1_) {
      p_a_1_.getWorld().triggerEffect(1002, p_a_1_.getBlockPosition(), 0);
   }

   protected abstract IProjectile a(World var1, IPosition var2);

   protected float a() {
      return 6.0F;
   }

   protected float getPower() {
      return 1.1F;
   }
}
