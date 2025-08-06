package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPotion;
import net.minecraft.server.v1_8_R3.EnumAnimation;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.LocaleI18n;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.PotionBrewer;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.World;

public class ItemPotion extends Item {
   private Map<Integer, List<MobEffect>> a = Maps.<Integer, List<MobEffect>>newHashMap();
   private static final Map<List<MobEffect>, Integer> b = Maps.<List<MobEffect>, Integer>newLinkedHashMap();

   public ItemPotion() {
      this.c(1);
      this.a(true);
      this.setMaxDurability(0);
      this.a(CreativeModeTab.k);
   }

   public List<MobEffect> h(ItemStack p_h_1_) {
      if(p_h_1_.hasTag() && p_h_1_.getTag().hasKeyOfType("CustomPotionEffects", 9)) {
         ArrayList arraylist = Lists.newArrayList();
         NBTTagList nbttaglist = p_h_1_.getTag().getList("CustomPotionEffects", 10);

         for(int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.get(i);
            MobEffect mobeffect = MobEffect.b(nbttagcompound);
            if(mobeffect != null) {
               arraylist.add(mobeffect);
            }
         }

         return arraylist;
      } else {
         List list = (List)this.a.get(Integer.valueOf(p_h_1_.getData()));
         if(list == null) {
            list = PotionBrewer.getEffects(p_h_1_.getData(), false);
            this.a.put(Integer.valueOf(p_h_1_.getData()), list);
         }

         return list;
      }
   }

   public List<MobEffect> e(int p_e_1_) {
      List list = (List)this.a.get(Integer.valueOf(p_e_1_));
      if(list == null) {
         list = PotionBrewer.getEffects(p_e_1_, false);
         this.a.put(Integer.valueOf(p_e_1_), list);
      }

      return list;
   }

   public ItemStack b(ItemStack p_b_1_, World p_b_2_, EntityHuman p_b_3_) {
      if(!p_b_3_.abilities.canInstantlyBuild) {
         --p_b_1_.count;
      }

      if(!p_b_2_.isClientSide) {
         List list = this.h(p_b_1_);
         if(list != null) {
            for(MobEffect mobeffect : list) {
               p_b_3_.addEffect(new MobEffect(mobeffect));
            }
         }
      }

      p_b_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
      if(!p_b_3_.abilities.canInstantlyBuild) {
         if(p_b_1_.count <= 0) {
            return new ItemStack(Items.GLASS_BOTTLE);
         }

         p_b_3_.inventory.pickup(new ItemStack(Items.GLASS_BOTTLE));
      }

      return p_b_1_;
   }

   public int d(ItemStack p_d_1_) {
      return 32;
   }

   public EnumAnimation e(ItemStack p_e_1_) {
      return EnumAnimation.DRINK;
   }

   public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_) {
      if(f(p_a_1_.getData())) {
         if(!p_a_3_.abilities.canInstantlyBuild) {
            --p_a_1_.count;
         }

         p_a_2_.makeSound(p_a_3_, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
         if(!p_a_2_.isClientSide) {
            p_a_2_.addEntity(new EntityPotion(p_a_2_, p_a_3_, p_a_1_));
         }

         p_a_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
         return p_a_1_;
      } else {
         p_a_3_.a(p_a_1_, this.d(p_a_1_));
         return p_a_1_;
      }
   }

   public static boolean f(int p_f_0_) {
      return (p_f_0_ & 16384) != 0;
   }

   public String a(ItemStack p_a_1_) {
      if(p_a_1_.getData() == 0) {
         return LocaleI18n.get("item.emptyPotion.name").trim();
      } else {
         String s = "";
         if(f(p_a_1_.getData())) {
            s = LocaleI18n.get("potion.prefix.grenade").trim() + " ";
         }

         List list = Items.POTION.h(p_a_1_);
         if(list != null && !list.isEmpty()) {
            String s2 = ((MobEffect)list.get(0)).g();
            s2 = s2 + ".postfix";
            return s + LocaleI18n.get(s2).trim();
         } else {
            String s1 = PotionBrewer.c(p_a_1_.getData());
            return LocaleI18n.get(s1).trim() + " " + super.a(p_a_1_);
         }
      }
   }
}
