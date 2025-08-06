package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityFishingHook;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

public class ItemFishingRod extends Item {
   public ItemFishingRod() {
      this.setMaxDurability(64);
      this.c(1);
      this.a(CreativeModeTab.i);
   }

   public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_) {
      if(p_a_3_.hookedFish != null) {
         int i = p_a_3_.hookedFish.l();
         p_a_1_.damage(i, p_a_3_);
         p_a_3_.bw();
      } else {
         EntityFishingHook entityfishinghook = new EntityFishingHook(p_a_2_, p_a_3_);
         PlayerFishEvent playerfishevent = new PlayerFishEvent((Player)p_a_3_.getBukkitEntity(), (org.bukkit.entity.Entity)null, (Fish)entityfishinghook.getBukkitEntity(), State.FISHING);
         p_a_2_.getServer().getPluginManager().callEvent(playerfishevent);
         if(playerfishevent.isCancelled()) {
            p_a_3_.hookedFish = null;
            return p_a_1_;
         }

         p_a_2_.makeSound(p_a_3_, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
         if(!p_a_2_.isClientSide) {
            p_a_2_.addEntity(entityfishinghook);
         }

         p_a_3_.bw();
         p_a_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
      }

      return p_a_1_;
   }

   public boolean f_(ItemStack p_f__1_) {
      return super.f_(p_f__1_);
   }

   public int b() {
      return 1;
   }
}
