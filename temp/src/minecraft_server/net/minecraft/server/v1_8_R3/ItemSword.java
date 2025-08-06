package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Multimap;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumAnimation;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;

public class ItemSword extends Item {
   private float a;
   private final Item.EnumToolMaterial b;

   public ItemSword(Item.EnumToolMaterial p_i518_1_) {
      this.b = p_i518_1_;
      this.maxStackSize = 1;
      this.setMaxDurability(p_i518_1_.a());
      this.a(CreativeModeTab.j);
      this.a = 4.0F + p_i518_1_.c();
   }

   public float g() {
      return this.b.c();
   }

   public float getDestroySpeed(ItemStack p_getDestroySpeed_1_, Block p_getDestroySpeed_2_) {
      if(p_getDestroySpeed_2_ == Blocks.WEB) {
         return 15.0F;
      } else {
         Material material = p_getDestroySpeed_2_.getMaterial();
         return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.CORAL && material != Material.LEAVES && material != Material.PUMPKIN?1.0F:1.5F;
      }
   }

   public boolean a(ItemStack p_a_1_, EntityLiving p_a_2_, EntityLiving p_a_3_) {
      p_a_1_.damage(1, p_a_3_);
      return true;
   }

   public boolean a(ItemStack p_a_1_, World p_a_2_, Block p_a_3_, BlockPosition p_a_4_, EntityLiving p_a_5_) {
      if((double)p_a_3_.g(p_a_2_, p_a_4_) != 0.0D) {
         p_a_1_.damage(2, p_a_5_);
      }

      return true;
   }

   public EnumAnimation e(ItemStack p_e_1_) {
      return EnumAnimation.BLOCK;
   }

   public int d(ItemStack p_d_1_) {
      return 72000;
   }

   public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_) {
      p_a_3_.a(p_a_1_, this.d(p_a_1_));
      return p_a_1_;
   }

   public boolean canDestroySpecialBlock(Block p_canDestroySpecialBlock_1_) {
      return p_canDestroySpecialBlock_1_ == Blocks.WEB;
   }

   public int b() {
      return this.b.e();
   }

   public String h() {
      return this.b.toString();
   }

   public boolean a(ItemStack p_a_1_, ItemStack p_a_2_) {
      return this.b.f() == p_a_2_.getItem()?true:super.a(p_a_1_, p_a_2_);
   }

   public Multimap<String, AttributeModifier> i() {
      Multimap multimap = super.i();
      multimap.put(GenericAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(f, "Weapon modifier", (double)this.a, 0));
      return multimap;
   }
}
