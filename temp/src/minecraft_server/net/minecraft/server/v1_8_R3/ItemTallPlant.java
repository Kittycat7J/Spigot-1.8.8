package net.minecraft.server.v1_8_R3;

import com.google.common.base.Function;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.ItemMultiTexture;
import net.minecraft.server.v1_8_R3.ItemStack;

public class ItemTallPlant extends ItemMultiTexture {
   public ItemTallPlant(Block p_i1273_1_, Block p_i1273_2_, Function<ItemStack, String> p_i1273_3_) {
      super(p_i1273_1_, p_i1273_2_, p_i1273_3_);
   }
}
