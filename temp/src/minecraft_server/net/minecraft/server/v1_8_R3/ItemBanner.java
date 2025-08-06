package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockFloorSign;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockWallSign;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumColor;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.ItemBlock;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.LocaleI18n;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntityBanner;
import net.minecraft.server.v1_8_R3.World;

public class ItemBanner extends ItemBlock {
   public ItemBanner() {
      super(Blocks.STANDING_BANNER);
      this.maxStackSize = 16;
      this.a(CreativeModeTab.c);
      this.a(true);
      this.setMaxDurability(0);
   }

   public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_) {
      if(p_interactWith_5_ == EnumDirection.DOWN) {
         return false;
      } else if(!p_interactWith_3_.getType(p_interactWith_4_).getBlock().getMaterial().isBuildable()) {
         return false;
      } else {
         p_interactWith_4_ = p_interactWith_4_.shift(p_interactWith_5_);
         if(!p_interactWith_2_.a(p_interactWith_4_, p_interactWith_5_, p_interactWith_1_)) {
            return false;
         } else if(!Blocks.STANDING_BANNER.canPlace(p_interactWith_3_, p_interactWith_4_)) {
            return false;
         } else if(p_interactWith_3_.isClientSide) {
            return true;
         } else {
            if(p_interactWith_5_ == EnumDirection.UP) {
               int i = MathHelper.floor((double)((p_interactWith_2_.yaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
               p_interactWith_3_.setTypeAndData(p_interactWith_4_, Blocks.STANDING_BANNER.getBlockData().set(BlockFloorSign.ROTATION, Integer.valueOf(i)), 3);
            } else {
               p_interactWith_3_.setTypeAndData(p_interactWith_4_, Blocks.WALL_BANNER.getBlockData().set(BlockWallSign.FACING, p_interactWith_5_), 3);
            }

            --p_interactWith_1_.count;
            TileEntity tileentity = p_interactWith_3_.getTileEntity(p_interactWith_4_);
            if(tileentity instanceof TileEntityBanner) {
               ((TileEntityBanner)tileentity).a(p_interactWith_1_);
            }

            return true;
         }
      }
   }

   public String a(ItemStack p_a_1_) {
      String s = "item.banner.";
      EnumColor enumcolor = this.h(p_a_1_);
      s = s + enumcolor.d() + ".name";
      return LocaleI18n.get(s);
   }

   private EnumColor h(ItemStack p_h_1_) {
      NBTTagCompound nbttagcompound = p_h_1_.a("BlockEntityTag", false);
      EnumColor enumcolor = null;
      if(nbttagcompound != null && nbttagcompound.hasKey("Base")) {
         enumcolor = EnumColor.fromInvColorIndex(nbttagcompound.getInt("Base"));
      } else {
         enumcolor = EnumColor.fromInvColorIndex(p_h_1_.getData());
      }

      return enumcolor;
   }
}
