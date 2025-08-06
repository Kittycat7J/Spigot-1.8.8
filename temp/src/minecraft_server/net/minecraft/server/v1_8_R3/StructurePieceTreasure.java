package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.TileEntityDispenser;
import net.minecraft.server.v1_8_R3.WeightedRandom;

public class StructurePieceTreasure extends WeightedRandom.WeightedRandomChoice {
   private ItemStack b;
   private int c;
   private int d;

   public StructurePieceTreasure(Item p_i1124_1_, int p_i1124_2_, int p_i1124_3_, int p_i1124_4_, int p_i1124_5_) {
      super(p_i1124_5_);
      this.b = new ItemStack(p_i1124_1_, 1, p_i1124_2_);
      this.c = p_i1124_3_;
      this.d = p_i1124_4_;
   }

   public StructurePieceTreasure(ItemStack p_i1125_1_, int p_i1125_2_, int p_i1125_3_, int p_i1125_4_) {
      super(p_i1125_4_);
      this.b = p_i1125_1_;
      this.c = p_i1125_2_;
      this.d = p_i1125_3_;
   }

   public static void a(Random p_a_0_, List<StructurePieceTreasure> p_a_1_, IInventory p_a_2_, int p_a_3_) {
      for(int i = 0; i < p_a_3_; ++i) {
         StructurePieceTreasure structurepiecetreasure = (StructurePieceTreasure)WeightedRandom.a(p_a_0_, p_a_1_);
         int j = structurepiecetreasure.c + p_a_0_.nextInt(structurepiecetreasure.d - structurepiecetreasure.c + 1);
         if(structurepiecetreasure.b.getMaxStackSize() >= j) {
            ItemStack itemstack1 = structurepiecetreasure.b.cloneItemStack();
            itemstack1.count = j;
            p_a_2_.setItem(p_a_0_.nextInt(p_a_2_.getSize()), itemstack1);
         } else {
            for(int k = 0; k < j; ++k) {
               ItemStack itemstack = structurepiecetreasure.b.cloneItemStack();
               itemstack.count = 1;
               p_a_2_.setItem(p_a_0_.nextInt(p_a_2_.getSize()), itemstack);
            }
         }
      }

   }

   public static void a(Random p_a_0_, List<StructurePieceTreasure> p_a_1_, TileEntityDispenser p_a_2_, int p_a_3_) {
      for(int i = 0; i < p_a_3_; ++i) {
         StructurePieceTreasure structurepiecetreasure = (StructurePieceTreasure)WeightedRandom.a(p_a_0_, p_a_1_);
         int j = structurepiecetreasure.c + p_a_0_.nextInt(structurepiecetreasure.d - structurepiecetreasure.c + 1);
         if(structurepiecetreasure.b.getMaxStackSize() >= j) {
            ItemStack itemstack1 = structurepiecetreasure.b.cloneItemStack();
            itemstack1.count = j;
            p_a_2_.setItem(p_a_0_.nextInt(p_a_2_.getSize()), itemstack1);
         } else {
            for(int k = 0; k < j; ++k) {
               ItemStack itemstack = structurepiecetreasure.b.cloneItemStack();
               itemstack.count = 1;
               p_a_2_.setItem(p_a_0_.nextInt(p_a_2_.getSize()), itemstack);
            }
         }
      }

   }

   public static List<StructurePieceTreasure> a(List<StructurePieceTreasure> p_a_0_, StructurePieceTreasure... p_a_1_) {
      ArrayList arraylist = Lists.newArrayList(p_a_0_);
      Collections.addAll(arraylist, p_a_1_);
      return arraylist;
   }
}
