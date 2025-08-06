package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.ItemTool;
import net.minecraft.server.v1_8_R3.Material;

public class ItemAxe extends ItemTool {
   private static final Set<Block> c = Sets.newHashSet(new Block[]{Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER});

   protected ItemAxe(Item.EnumToolMaterial p_i1255_1_) {
      super(3.0F, p_i1255_1_, c);
   }

   public float getDestroySpeed(ItemStack p_getDestroySpeed_1_, Block p_getDestroySpeed_2_) {
      return p_getDestroySpeed_2_.getMaterial() != Material.WOOD && p_getDestroySpeed_2_.getMaterial() != Material.PLANT && p_getDestroySpeed_2_.getMaterial() != Material.REPLACEABLE_PLANT?super.getDestroySpeed(p_getDestroySpeed_1_, p_getDestroySpeed_2_):this.a;
   }
}
