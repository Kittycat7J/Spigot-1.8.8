package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHanging;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityItemFrame;
import net.minecraft.server.v1_8_R3.EntityPainting;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.painting.PaintingPlaceEvent;

public class ItemHanging extends Item {
   private final Class<? extends EntityHanging> a;

   public ItemHanging(Class<? extends EntityHanging> p_i208_1_) {
      this.a = p_i208_1_;
      this.a(CreativeModeTab.c);
   }

   public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_) {
      if(p_interactWith_5_ == EnumDirection.DOWN) {
         return false;
      } else if(p_interactWith_5_ == EnumDirection.UP) {
         return false;
      } else {
         BlockPosition blockposition = p_interactWith_4_.shift(p_interactWith_5_);
         if(!p_interactWith_2_.a(blockposition, p_interactWith_5_, p_interactWith_1_)) {
            return false;
         } else {
            EntityHanging entityhanging = this.a(p_interactWith_3_, blockposition, p_interactWith_5_);
            if(entityhanging != null && entityhanging.survives()) {
               if(!p_interactWith_3_.isClientSide) {
                  Player player = p_interactWith_2_ == null?null:(Player)p_interactWith_2_.getBukkitEntity();
                  org.bukkit.block.Block block = p_interactWith_3_.getWorld().getBlockAt(p_interactWith_4_.getX(), p_interactWith_4_.getY(), p_interactWith_4_.getZ());
                  BlockFace blockface = CraftBlock.notchToBlockFace(p_interactWith_5_);
                  HangingPlaceEvent hangingplaceevent = new HangingPlaceEvent((Hanging)entityhanging.getBukkitEntity(), player, block, blockface);
                  p_interactWith_3_.getServer().getPluginManager().callEvent(hangingplaceevent);
                  PaintingPlaceEvent paintingplaceevent = null;
                  if(entityhanging instanceof EntityPainting) {
                     paintingplaceevent = new PaintingPlaceEvent((Painting)entityhanging.getBukkitEntity(), player, block, blockface);
                     paintingplaceevent.setCancelled(hangingplaceevent.isCancelled());
                     p_interactWith_3_.getServer().getPluginManager().callEvent(paintingplaceevent);
                  }

                  if(hangingplaceevent.isCancelled() || paintingplaceevent != null && paintingplaceevent.isCancelled()) {
                     return false;
                  }

                  p_interactWith_3_.addEntity(entityhanging);
               }

               --p_interactWith_1_.count;
            }

            return true;
         }
      }
   }

   private EntityHanging a(World p_a_1_, BlockPosition p_a_2_, EnumDirection p_a_3_) {
      return (EntityHanging)(this.a == EntityPainting.class?new EntityPainting(p_a_1_, p_a_2_, p_a_3_):(this.a == EntityItemFrame.class?new EntityItemFrame(p_a_1_, p_a_2_, p_a_3_):null));
   }
}
