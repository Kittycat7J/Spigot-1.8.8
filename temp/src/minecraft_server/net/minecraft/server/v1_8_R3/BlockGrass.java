package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockDirt;
import net.minecraft.server.v1_8_R3.BlockFlowers;
import net.minecraft.server.v1_8_R3.BlockLongGrass;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockFragilePlantElement;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockSpreadEvent;

public class BlockGrass extends Block implements IBlockFragilePlantElement {
   public static final BlockStateBoolean SNOWY = BlockStateBoolean.of("snowy");

   protected BlockGrass() {
      super(Material.GRASS);
      this.j(this.blockStateList.getBlockData().set(SNOWY, Boolean.valueOf(false)));
      this.a(true);
      this.a(CreativeModeTab.b);
   }

   public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_) {
      Block block = p_updateState_2_.getType(p_updateState_3_.up()).getBlock();
      return p_updateState_1_.set(SNOWY, Boolean.valueOf(block == Blocks.SNOW || block == Blocks.SNOW_LAYER));
   }

   public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_) {
      if(!p_b_1_.isClientSide) {
         if(p_b_1_.getLightLevel(p_b_2_.up()) < 4 && p_b_1_.getType(p_b_2_.up()).getBlock().p() > 2) {
            org.bukkit.World world1 = p_b_1_.getWorld();
            org.bukkit.block.BlockState blockstate1 = world1.getBlockAt(p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ()).getState();
            blockstate1.setType(CraftMagicNumbers.getMaterial(Blocks.DIRT));
            BlockFadeEvent blockfadeevent = new BlockFadeEvent(blockstate1.getBlock(), blockstate1);
            p_b_1_.getServer().getPluginManager().callEvent(blockfadeevent);
            if(!blockfadeevent.isCancelled()) {
               blockstate1.update(true);
            }
         } else if(p_b_1_.getLightLevel(p_b_2_.up()) >= 9) {
            for(int i = 0; i < Math.min(4, Math.max(20, (int)(400.0F / p_b_1_.growthOdds))); ++i) {
               BlockPosition blockposition = p_b_2_.a(p_b_4_.nextInt(3) - 1, p_b_4_.nextInt(5) - 3, p_b_4_.nextInt(3) - 1);
               Block block = p_b_1_.getType(blockposition.up()).getBlock();
               IBlockData iblockdata = p_b_1_.getType(blockposition);
               if(iblockdata.getBlock() == Blocks.DIRT && iblockdata.get(BlockDirt.VARIANT) == BlockDirt.EnumDirtVariant.DIRT && p_b_1_.getLightLevel(blockposition.up()) >= 4 && block.p() <= 2) {
                  org.bukkit.World world = p_b_1_.getWorld();
                  org.bukkit.block.BlockState blockstate = world.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()).getState();
                  blockstate.setType(CraftMagicNumbers.getMaterial((Block)Blocks.GRASS));
                  BlockSpreadEvent blockspreadevent = new BlockSpreadEvent(blockstate.getBlock(), world.getBlockAt(p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ()), blockstate);
                  p_b_1_.getServer().getPluginManager().callEvent(blockspreadevent);
                  if(!blockspreadevent.isCancelled()) {
                     blockstate.update(true);
                  }
               }
            }
         }
      }

   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return Blocks.DIRT.getDropType(Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.DIRT), p_getDropType_2_, p_getDropType_3_);
   }

   public boolean a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, boolean p_a_4_) {
      return true;
   }

   public boolean a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_) {
      return true;
   }

   public void b(World p_b_1_, Random p_b_2_, BlockPosition p_b_3_, IBlockData p_b_4_) {
      BlockPosition blockposition = p_b_3_.up();

      for(int i = 0; i < 128; ++i) {
         BlockPosition blockposition1 = blockposition;
         int j = 0;

         while(true) {
            if(j >= i / 16) {
               if(p_b_1_.getType(blockposition1).getBlock().material == Material.AIR) {
                  if(p_b_2_.nextInt(8) == 0) {
                     BlockFlowers.EnumFlowerVarient blockflowers$enumflowervarient = p_b_1_.getBiome(blockposition1).a(p_b_2_, blockposition1);
                     BlockFlowers blockflowers = blockflowers$enumflowervarient.a().a();
                     IBlockData iblockdata = blockflowers.getBlockData().set(blockflowers.n(), blockflowers$enumflowervarient);
                     if(blockflowers.f(p_b_1_, blockposition1, iblockdata)) {
                        CraftEventFactory.handleBlockGrowEvent(p_b_1_, blockposition1.getX(), blockposition1.getY(), blockposition1.getZ(), iblockdata.getBlock(), iblockdata.getBlock().toLegacyData(iblockdata));
                     }
                  } else {
                     IBlockData iblockdata1 = Blocks.TALLGRASS.getBlockData().set(BlockLongGrass.TYPE, BlockLongGrass.EnumTallGrassType.GRASS);
                     if(Blocks.TALLGRASS.f(p_b_1_, blockposition1, iblockdata1)) {
                        CraftEventFactory.handleBlockGrowEvent(p_b_1_, blockposition1.getX(), blockposition1.getY(), blockposition1.getZ(), iblockdata1.getBlock(), iblockdata1.getBlock().toLegacyData(iblockdata1));
                     }
                  }
               }
               break;
            }

            blockposition1 = blockposition1.a(p_b_2_.nextInt(3) - 1, (p_b_2_.nextInt(3) - 1) * p_b_2_.nextInt(3) / 2, p_b_2_.nextInt(3) - 1);
            if(p_b_1_.getType(blockposition1.down()).getBlock() != Blocks.GRASS || p_b_1_.getType(blockposition1).getBlock().isOccluding()) {
               break;
            }

            ++j;
         }
      }

   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return 0;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{SNOWY});
   }
}
