package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockWood;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntitySheep;
import net.minecraft.server.v1_8_R3.EnumColor;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockFragilePlantElement;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.DyeColor;
import org.bukkit.entity.Sheep;
import org.bukkit.event.entity.SheepDyeWoolEvent;

public class ItemDye extends Item {
   public static final int[] a = new int[]{1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};

   public ItemDye() {
      this.a(true);
      this.setMaxDurability(0);
      this.a(CreativeModeTab.l);
   }

   public String e_(ItemStack p_e__1_) {
      int i = p_e__1_.getData();
      return super.getName() + "." + EnumColor.fromInvColorIndex(i).d();
   }

   public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_) {
      if(!p_interactWith_2_.a(p_interactWith_4_.shift(p_interactWith_5_), p_interactWith_5_, p_interactWith_1_)) {
         return false;
      } else {
         EnumColor enumcolor = EnumColor.fromInvColorIndex(p_interactWith_1_.getData());
         if(enumcolor == EnumColor.WHITE) {
            if(a(p_interactWith_1_, p_interactWith_3_, p_interactWith_4_)) {
               if(!p_interactWith_3_.isClientSide) {
                  p_interactWith_3_.triggerEffect(2005, p_interactWith_4_, 0);
               }

               return true;
            }
         } else if(enumcolor == EnumColor.BROWN) {
            IBlockData iblockdata = p_interactWith_3_.getType(p_interactWith_4_);
            Block block = iblockdata.getBlock();
            if(block == Blocks.LOG && iblockdata.get(BlockWood.VARIANT) == BlockWood.EnumLogVariant.JUNGLE) {
               if(p_interactWith_5_ == EnumDirection.DOWN) {
                  return false;
               }

               if(p_interactWith_5_ == EnumDirection.UP) {
                  return false;
               }

               p_interactWith_4_ = p_interactWith_4_.shift(p_interactWith_5_);
               if(p_interactWith_3_.isEmpty(p_interactWith_4_)) {
                  IBlockData iblockdata1 = Blocks.COCOA.getPlacedState(p_interactWith_3_, p_interactWith_4_, p_interactWith_5_, p_interactWith_6_, p_interactWith_7_, p_interactWith_8_, 0, p_interactWith_2_);
                  p_interactWith_3_.setTypeAndData(p_interactWith_4_, iblockdata1, 2);
                  if(!p_interactWith_2_.abilities.canInstantlyBuild) {
                     --p_interactWith_1_.count;
                  }
               }

               return true;
            }
         }

         return false;
      }
   }

   public static boolean a(ItemStack p_a_0_, World p_a_1_, BlockPosition p_a_2_) {
      IBlockData iblockdata = p_a_1_.getType(p_a_2_);
      if(iblockdata.getBlock() instanceof IBlockFragilePlantElement) {
         IBlockFragilePlantElement iblockfragileplantelement = (IBlockFragilePlantElement)iblockdata.getBlock();
         if(iblockfragileplantelement.a(p_a_1_, p_a_2_, iblockdata, p_a_1_.isClientSide)) {
            if(!p_a_1_.isClientSide) {
               if(iblockfragileplantelement.a(p_a_1_, p_a_1_.random, p_a_2_, iblockdata)) {
                  iblockfragileplantelement.b(p_a_1_, p_a_1_.random, p_a_2_, iblockdata);
               }

               --p_a_0_.count;
            }

            return true;
         }
      }

      return false;
   }

   public boolean a(ItemStack p_a_1_, EntityHuman p_a_2_, EntityLiving p_a_3_) {
      if(p_a_3_ instanceof EntitySheep) {
         EntitySheep entitysheep = (EntitySheep)p_a_3_;
         EnumColor enumcolor = EnumColor.fromInvColorIndex(p_a_1_.getData());
         if(!entitysheep.isSheared() && entitysheep.getColor() != enumcolor) {
            byte b0 = (byte)enumcolor.getColorIndex();
            SheepDyeWoolEvent sheepdyewoolevent = new SheepDyeWoolEvent((Sheep)entitysheep.getBukkitEntity(), DyeColor.getByData(b0));
            entitysheep.world.getServer().getPluginManager().callEvent(sheepdyewoolevent);
            if(sheepdyewoolevent.isCancelled()) {
               return false;
            }

            enumcolor = EnumColor.fromColorIndex(sheepdyewoolevent.getColor().getWoolData());
            entitysheep.setColor(enumcolor);
            --p_a_1_.count;
         }

         return true;
      } else {
         return false;
      }
   }
}
