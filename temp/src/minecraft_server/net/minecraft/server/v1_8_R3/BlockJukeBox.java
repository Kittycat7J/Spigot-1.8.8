package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockContainer;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

public class BlockJukeBox extends BlockContainer {
   public static final BlockStateBoolean HAS_RECORD = BlockStateBoolean.of("has_record");

   protected BlockJukeBox() {
      super(Material.WOOD, MaterialMapColor.l);
      this.j(this.blockStateList.getBlockData().set(HAS_RECORD, Boolean.valueOf(false)));
      this.a(CreativeModeTab.c);
   }

   public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_) {
      if(((Boolean)p_interact_3_.get(HAS_RECORD)).booleanValue()) {
         this.dropRecord(p_interact_1_, p_interact_2_, p_interact_3_);
         p_interact_3_ = p_interact_3_.set(HAS_RECORD, Boolean.valueOf(false));
         p_interact_1_.setTypeAndData(p_interact_2_, p_interact_3_, 2);
         return true;
      } else {
         return false;
      }
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, ItemStack p_a_4_) {
      if(!p_a_1_.isClientSide) {
         TileEntity tileentity = p_a_1_.getTileEntity(p_a_2_);
         if(tileentity instanceof BlockJukeBox.TileEntityRecordPlayer) {
            ((BlockJukeBox.TileEntityRecordPlayer)tileentity).setRecord(new ItemStack(p_a_4_.getItem(), 1, p_a_4_.getData()));
            p_a_1_.setTypeAndData(p_a_2_, p_a_3_.set(HAS_RECORD, Boolean.valueOf(true)), 2);
         }
      }

   }

   public void dropRecord(World p_dropRecord_1_, BlockPosition p_dropRecord_2_, IBlockData p_dropRecord_3_) {
      if(!p_dropRecord_1_.isClientSide) {
         TileEntity tileentity = p_dropRecord_1_.getTileEntity(p_dropRecord_2_);
         if(tileentity instanceof BlockJukeBox.TileEntityRecordPlayer) {
            BlockJukeBox.TileEntityRecordPlayer blockjukebox$tileentityrecordplayer = (BlockJukeBox.TileEntityRecordPlayer)tileentity;
            ItemStack itemstack = blockjukebox$tileentityrecordplayer.getRecord();
            if(itemstack != null) {
               p_dropRecord_1_.triggerEffect(1005, p_dropRecord_2_, 0);
               p_dropRecord_1_.a((BlockPosition)p_dropRecord_2_, (String)null);
               blockjukebox$tileentityrecordplayer.setRecord((ItemStack)null);
               float f = 0.7F;
               double d0 = (double)(p_dropRecord_1_.random.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
               double d1 = (double)(p_dropRecord_1_.random.nextFloat() * f) + (double)(1.0F - f) * 0.2D + 0.6D;
               double d2 = (double)(p_dropRecord_1_.random.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
               ItemStack itemstack1 = itemstack.cloneItemStack();
               EntityItem entityitem = new EntityItem(p_dropRecord_1_, (double)p_dropRecord_2_.getX() + d0, (double)p_dropRecord_2_.getY() + d1, (double)p_dropRecord_2_.getZ() + d2, itemstack1);
               entityitem.p();
               p_dropRecord_1_.addEntity(entityitem);
            }
         }
      }

   }

   public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_) {
      this.dropRecord(p_remove_1_, p_remove_2_, p_remove_3_);
      super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
   }

   public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_) {
      if(!p_dropNaturally_1_.isClientSide) {
         super.dropNaturally(p_dropNaturally_1_, p_dropNaturally_2_, p_dropNaturally_3_, p_dropNaturally_4_, 0);
      }

   }

   public TileEntity a(World p_a_1_, int p_a_2_) {
      return new BlockJukeBox.TileEntityRecordPlayer();
   }

   public boolean isComplexRedstone() {
      return true;
   }

   public int l(World p_l_1_, BlockPosition p_l_2_) {
      TileEntity tileentity = p_l_1_.getTileEntity(p_l_2_);
      if(tileentity instanceof BlockJukeBox.TileEntityRecordPlayer) {
         ItemStack itemstack = ((BlockJukeBox.TileEntityRecordPlayer)tileentity).getRecord();
         if(itemstack != null) {
            return Item.getId(itemstack.getItem()) + 1 - Item.getId(Items.RECORD_13);
         }
      }

      return 0;
   }

   public int b() {
      return 3;
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(HAS_RECORD, Boolean.valueOf(p_fromLegacyData_1_ > 0));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((Boolean)p_toLegacyData_1_.get(HAS_RECORD)).booleanValue()?1:0;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{HAS_RECORD});
   }

   public static class TileEntityRecordPlayer extends TileEntity {
      private ItemStack record;

      public void a(NBTTagCompound p_a_1_) {
         super.a(p_a_1_);
         if(p_a_1_.hasKeyOfType("RecordItem", 10)) {
            this.setRecord(ItemStack.createStack(p_a_1_.getCompound("RecordItem")));
         } else if(p_a_1_.getInt("Record") > 0) {
            this.setRecord(new ItemStack(Item.getById(p_a_1_.getInt("Record")), 1, 0));
         }

      }

      public void b(NBTTagCompound p_b_1_) {
         super.b(p_b_1_);
         if(this.getRecord() != null) {
            p_b_1_.set("RecordItem", this.getRecord().save(new NBTTagCompound()));
         }

      }

      public ItemStack getRecord() {
         return this.record;
      }

      public void setRecord(ItemStack p_setRecord_1_) {
         if(p_setRecord_1_ != null) {
            p_setRecord_1_.count = 1;
         }

         this.record = p_setRecord_1_;
         this.update();
      }
   }
}
