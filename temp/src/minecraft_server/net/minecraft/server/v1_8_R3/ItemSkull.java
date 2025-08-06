package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockSkull;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.GameProfileSerializer;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.LocaleI18n;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntitySkull;
import net.minecraft.server.v1_8_R3.World;

public class ItemSkull extends Item {
   private static final String[] a = new String[]{"skeleton", "wither", "zombie", "char", "creeper"};

   public ItemSkull() {
      this.a((CreativeModeTab)CreativeModeTab.c);
      this.setMaxDurability(0);
      this.a(true);
   }

   public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_) {
      if(p_interactWith_5_ == EnumDirection.DOWN) {
         return false;
      } else {
         IBlockData iblockdata = p_interactWith_3_.getType(p_interactWith_4_);
         Block block = iblockdata.getBlock();
         boolean flag = block.a(p_interactWith_3_, p_interactWith_4_);
         if(!flag) {
            if(!p_interactWith_3_.getType(p_interactWith_4_).getBlock().getMaterial().isBuildable()) {
               return false;
            }

            p_interactWith_4_ = p_interactWith_4_.shift(p_interactWith_5_);
         }

         if(!p_interactWith_2_.a(p_interactWith_4_, p_interactWith_5_, p_interactWith_1_)) {
            return false;
         } else if(!Blocks.SKULL.canPlace(p_interactWith_3_, p_interactWith_4_)) {
            return false;
         } else {
            if(!p_interactWith_3_.isClientSide) {
               if(!Blocks.SKULL.canPlace(p_interactWith_3_, p_interactWith_4_)) {
                  return false;
               }

               p_interactWith_3_.setTypeAndData(p_interactWith_4_, Blocks.SKULL.getBlockData().set(BlockSkull.FACING, p_interactWith_5_), 3);
               int i = 0;
               if(p_interactWith_5_ == EnumDirection.UP) {
                  i = MathHelper.floor((double)(p_interactWith_2_.yaw * 16.0F / 360.0F) + 0.5D) & 15;
               }

               TileEntity tileentity = p_interactWith_3_.getTileEntity(p_interactWith_4_);
               if(tileentity instanceof TileEntitySkull) {
                  TileEntitySkull tileentityskull = (TileEntitySkull)tileentity;
                  if(p_interactWith_1_.getData() == 3) {
                     GameProfile gameprofile = null;
                     if(p_interactWith_1_.hasTag()) {
                        NBTTagCompound nbttagcompound = p_interactWith_1_.getTag();
                        if(nbttagcompound.hasKeyOfType("SkullOwner", 10)) {
                           gameprofile = GameProfileSerializer.deserialize(nbttagcompound.getCompound("SkullOwner"));
                        } else if(nbttagcompound.hasKeyOfType("SkullOwner", 8) && nbttagcompound.getString("SkullOwner").length() > 0) {
                           gameprofile = new GameProfile((UUID)null, nbttagcompound.getString("SkullOwner"));
                        }
                     }

                     tileentityskull.setGameProfile(gameprofile);
                  } else {
                     tileentityskull.setSkullType(p_interactWith_1_.getData());
                  }

                  tileentityskull.setRotation(i);
                  Blocks.SKULL.a(p_interactWith_3_, p_interactWith_4_, tileentityskull);
               }

               --p_interactWith_1_.count;
            }

            return true;
         }
      }
   }

   public int filterData(int p_filterData_1_) {
      return p_filterData_1_;
   }

   public String e_(ItemStack p_e__1_) {
      int i = p_e__1_.getData();
      if(i < 0 || i >= a.length) {
         i = 0;
      }

      return super.getName() + "." + a[i];
   }

   public String a(ItemStack p_a_1_) {
      if(p_a_1_.getData() == 3 && p_a_1_.hasTag()) {
         if(p_a_1_.getTag().hasKeyOfType("SkullOwner", 8)) {
            return LocaleI18n.a("item.skull.player.name", new Object[]{p_a_1_.getTag().getString("SkullOwner")});
         }

         if(p_a_1_.getTag().hasKeyOfType("SkullOwner", 10)) {
            NBTTagCompound nbttagcompound = p_a_1_.getTag().getCompound("SkullOwner");
            if(nbttagcompound.hasKeyOfType("Name", 8)) {
               return LocaleI18n.a("item.skull.player.name", new Object[]{nbttagcompound.getString("Name")});
            }
         }
      }

      return super.a(p_a_1_);
   }

   public boolean a(final NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      if(p_a_1_.hasKeyOfType("SkullOwner", 8) && p_a_1_.getString("SkullOwner").length() > 0) {
         GameProfile gameprofile = new GameProfile((UUID)null, p_a_1_.getString("SkullOwner"));
         TileEntitySkull.b(gameprofile, new Predicate<GameProfile>() {
            public boolean apply(GameProfile p_apply_1_) {
               p_a_1_.set("SkullOwner", GameProfileSerializer.serialize(new NBTTagCompound(), p_apply_1_));
               return false;
            }
         });
         return true;
      } else {
         return false;
      }
   }
}
