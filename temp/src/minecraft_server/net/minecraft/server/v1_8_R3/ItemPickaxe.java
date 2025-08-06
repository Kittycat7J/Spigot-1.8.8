package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.ItemTool;
import net.minecraft.server.v1_8_R3.Material;

public class ItemPickaxe extends ItemTool {
   private static final Set<Block> c = Sets.newHashSet(new Block[]{Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB});

   protected ItemPickaxe(Item.EnumToolMaterial p_i510_1_) {
      super(2.0F, p_i510_1_, c);
   }

   public boolean canDestroySpecialBlock(Block p_canDestroySpecialBlock_1_) {
      return p_canDestroySpecialBlock_1_ == Blocks.OBSIDIAN?this.b.d() == 3:(p_canDestroySpecialBlock_1_ != Blocks.DIAMOND_BLOCK && p_canDestroySpecialBlock_1_ != Blocks.DIAMOND_ORE?(p_canDestroySpecialBlock_1_ != Blocks.EMERALD_ORE && p_canDestroySpecialBlock_1_ != Blocks.EMERALD_BLOCK?(p_canDestroySpecialBlock_1_ != Blocks.GOLD_BLOCK && p_canDestroySpecialBlock_1_ != Blocks.GOLD_ORE?(p_canDestroySpecialBlock_1_ != Blocks.IRON_BLOCK && p_canDestroySpecialBlock_1_ != Blocks.IRON_ORE?(p_canDestroySpecialBlock_1_ != Blocks.LAPIS_BLOCK && p_canDestroySpecialBlock_1_ != Blocks.LAPIS_ORE?(p_canDestroySpecialBlock_1_ != Blocks.REDSTONE_ORE && p_canDestroySpecialBlock_1_ != Blocks.LIT_REDSTONE_ORE?(p_canDestroySpecialBlock_1_.getMaterial() == Material.STONE?true:(p_canDestroySpecialBlock_1_.getMaterial() == Material.ORE?true:p_canDestroySpecialBlock_1_.getMaterial() == Material.HEAVY)):this.b.d() >= 2):this.b.d() >= 1):this.b.d() >= 1):this.b.d() >= 2):this.b.d() >= 2):this.b.d() >= 2);
   }

   public float getDestroySpeed(ItemStack p_getDestroySpeed_1_, Block p_getDestroySpeed_2_) {
      return p_getDestroySpeed_2_.getMaterial() != Material.ORE && p_getDestroySpeed_2_.getMaterial() != Material.HEAVY && p_getDestroySpeed_2_.getMaterial() != Material.STONE?super.getDestroySpeed(p_getDestroySpeed_1_, p_getDestroySpeed_2_):this.a;
   }
}
