package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumAnimation;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ItemBow extends Item {
   public static final String[] a = new String[]{"pulling_0", "pulling_1", "pulling_2"};

   public ItemBow() {
      this.maxStackSize = 1;
      this.setMaxDurability(384);
      this.a(CreativeModeTab.j);
   }

   public void a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_, int p_a_4_) {
      boolean flag = p_a_3_.abilities.canInstantlyBuild || EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_INFINITE.id, p_a_1_) > 0;
      if(flag || p_a_3_.inventory.b(Items.ARROW)) {
         int i = this.d(p_a_1_) - p_a_4_;
         float f = (float)i / 20.0F;
         f = (f * f + f * 2.0F) / 3.0F;
         if((double)f < 0.1D) {
            return;
         }

         if(f > 1.0F) {
            f = 1.0F;
         }

         EntityArrow entityarrow = new EntityArrow(p_a_2_, p_a_3_, f * 2.0F);
         if(f == 1.0F) {
            entityarrow.setCritical(true);
         }

         int j = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, p_a_1_);
         if(j > 0) {
            entityarrow.b(entityarrow.j() + (double)j * 0.5D + 0.5D);
         }

         int k = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, p_a_1_);
         if(k > 0) {
            entityarrow.setKnockbackStrength(k);
         }

         if(EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, p_a_1_) > 0) {
            EntityCombustEvent entitycombustevent = new EntityCombustEvent(entityarrow.getBukkitEntity(), 100);
            entityarrow.world.getServer().getPluginManager().callEvent(entitycombustevent);
            if(!entitycombustevent.isCancelled()) {
               entityarrow.setOnFire(entitycombustevent.getDuration());
            }
         }

         EntityShootBowEvent entityshootbowevent = CraftEventFactory.callEntityShootBowEvent(p_a_3_, p_a_1_, entityarrow, f);
         if(entityshootbowevent.isCancelled()) {
            entityshootbowevent.getProjectile().remove();
            return;
         }

         if(entityshootbowevent.getProjectile() == entityarrow.getBukkitEntity()) {
            p_a_2_.addEntity(entityarrow);
         }

         p_a_1_.damage(1, p_a_3_);
         p_a_2_.makeSound(p_a_3_, "random.bow", 1.0F, 1.0F / (g.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
         if(flag) {
            entityarrow.fromPlayer = 2;
         } else {
            p_a_3_.inventory.a(Items.ARROW);
         }

         p_a_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
         boolean flag1 = p_a_2_.isClientSide;
      }

   }

   public ItemStack b(ItemStack p_b_1_, World p_b_2_, EntityHuman p_b_3_) {
      return p_b_1_;
   }

   public int d(ItemStack p_d_1_) {
      return 72000;
   }

   public EnumAnimation e(ItemStack p_e_1_) {
      return EnumAnimation.BOW;
   }

   public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_) {
      if(p_a_3_.abilities.canInstantlyBuild || p_a_3_.inventory.b(Items.ARROW)) {
         p_a_3_.a(p_a_1_, this.d(p_a_1_));
      }

      return p_a_1_;
   }

   public int b() {
      return 1;
   }
}
