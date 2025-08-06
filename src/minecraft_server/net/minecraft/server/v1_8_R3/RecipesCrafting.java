package net.minecraft.server.v1_8_R3;

public class RecipesCrafting
{
    public void a(CraftingManager p_a_1_)
    {
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.CHEST), new Object[] {"###", "# #", "###", '#', Blocks.PLANKS});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.TRAPPED_CHEST), new Object[] {"#-", '#', Blocks.CHEST, '-', Blocks.TRIPWIRE_HOOK});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.ENDER_CHEST), new Object[] {"###", "#E#", "###", '#', Blocks.OBSIDIAN, 'E', Items.ENDER_EYE});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.FURNACE), new Object[] {"###", "# #", "###", '#', Blocks.COBBLESTONE});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.CRAFTING_TABLE), new Object[] {"##", "##", '#', Blocks.PLANKS});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.SANDSTONE), new Object[] {"##", "##", '#', new ItemStack(Blocks.SAND, 1, BlockSand.EnumSandVariant.SAND.a())});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.RED_SANDSTONE), new Object[] {"##", "##", '#', new ItemStack(Blocks.SAND, 1, BlockSand.EnumSandVariant.RED_SAND.a())});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.SANDSTONE, 4, BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), new Object[] {"##", "##", '#', new ItemStack(Blocks.SANDSTONE, 1, BlockSandStone.EnumSandstoneVariant.DEFAULT.a())});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.RED_SANDSTONE, 4, BlockRedSandstone.EnumRedSandstoneVariant.SMOOTH.a()), new Object[] {"##", "##", '#', new ItemStack(Blocks.RED_SANDSTONE, 1, BlockRedSandstone.EnumRedSandstoneVariant.DEFAULT.a())});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.SANDSTONE, 1, BlockSandStone.EnumSandstoneVariant.CHISELED.a()), new Object[] {"#", "#", '#', new ItemStack(Blocks.STONE_SLAB, 1, BlockDoubleStepAbstract.EnumStoneSlabVariant.SAND.a())});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.RED_SANDSTONE, 1, BlockRedSandstone.EnumRedSandstoneVariant.CHISELED.a()), new Object[] {"#", "#", '#', new ItemStack(Blocks.STONE_SLAB2, 1, BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant.RED_SANDSTONE.a())});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, BlockQuartz.EnumQuartzVariant.CHISELED.a()), new Object[] {"#", "#", '#', new ItemStack(Blocks.STONE_SLAB, 1, BlockDoubleStepAbstract.EnumStoneSlabVariant.QUARTZ.a())});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 2, BlockQuartz.EnumQuartzVariant.LINES_Y.a()), new Object[] {"#", "#", '#', new ItemStack(Blocks.QUARTZ_BLOCK, 1, BlockQuartz.EnumQuartzVariant.DEFAULT.a())});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.STONEBRICK, 4), new Object[] {"##", "##", '#', new ItemStack(Blocks.STONE, 1, BlockStone.EnumStoneVariant.STONE.a())});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.STONEBRICK, 1, BlockSmoothBrick.P), new Object[] {"#", "#", '#', new ItemStack(Blocks.STONE_SLAB, 1, BlockDoubleStepAbstract.EnumStoneSlabVariant.SMOOTHBRICK.a())});
        p_a_1_.registerShapelessRecipe(new ItemStack(Blocks.STONEBRICK, 1, BlockSmoothBrick.N), new Object[] {Blocks.STONEBRICK, Blocks.VINE});
        p_a_1_.registerShapelessRecipe(new ItemStack(Blocks.MOSSY_COBBLESTONE, 1), new Object[] {Blocks.COBBLESTONE, Blocks.VINE});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.IRON_BARS, 16), new Object[] {"###", "###", '#', Items.IRON_INGOT});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.GLASS_PANE, 16), new Object[] {"###", "###", '#', Blocks.GLASS});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.REDSTONE_LAMP, 1), new Object[] {" R ", "RGR", " R ", 'R', Items.REDSTONE, 'G', Blocks.GLOWSTONE});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.BEACON, 1), new Object[] {"GGG", "GSG", "OOO", 'G', Blocks.GLASS, 'S', Items.NETHER_STAR, 'O', Blocks.OBSIDIAN});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.NETHER_BRICK, 1), new Object[] {"NN", "NN", 'N', Items.NETHERBRICK});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.STONE, 2, BlockStone.EnumStoneVariant.DIORITE.a()), new Object[] {"CQ", "QC", 'C', Blocks.COBBLESTONE, 'Q', Items.QUARTZ});
        p_a_1_.registerShapelessRecipe(new ItemStack(Blocks.STONE, 1, BlockStone.EnumStoneVariant.GRANITE.a()), new Object[] {new ItemStack(Blocks.STONE, 1, BlockStone.EnumStoneVariant.DIORITE.a()), Items.QUARTZ});
        p_a_1_.registerShapelessRecipe(new ItemStack(Blocks.STONE, 2, BlockStone.EnumStoneVariant.ANDESITE.a()), new Object[] {new ItemStack(Blocks.STONE, 1, BlockStone.EnumStoneVariant.DIORITE.a()), Blocks.COBBLESTONE});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.DIRT, 4, BlockDirt.EnumDirtVariant.COARSE_DIRT.a()), new Object[] {"DG", "GD", 'D', new ItemStack(Blocks.DIRT, 1, BlockDirt.EnumDirtVariant.DIRT.a()), 'G', Blocks.GRAVEL});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.STONE, 4, BlockStone.EnumStoneVariant.DIORITE_SMOOTH.a()), new Object[] {"SS", "SS", 'S', new ItemStack(Blocks.STONE, 1, BlockStone.EnumStoneVariant.DIORITE.a())});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.STONE, 4, BlockStone.EnumStoneVariant.GRANITE_SMOOTH.a()), new Object[] {"SS", "SS", 'S', new ItemStack(Blocks.STONE, 1, BlockStone.EnumStoneVariant.GRANITE.a())});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.STONE, 4, BlockStone.EnumStoneVariant.ANDESITE_SMOOTH.a()), new Object[] {"SS", "SS", 'S', new ItemStack(Blocks.STONE, 1, BlockStone.EnumStoneVariant.ANDESITE.a())});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.PRISMARINE, 1, BlockPrismarine.b), new Object[] {"SS", "SS", 'S', Items.PRISMARINE_SHARD});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.PRISMARINE, 1, BlockPrismarine.N), new Object[] {"SSS", "SSS", "SSS", 'S', Items.PRISMARINE_SHARD});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.PRISMARINE, 1, BlockPrismarine.O), new Object[] {"SSS", "SIS", "SSS", 'S', Items.PRISMARINE_SHARD, 'I', new ItemStack(Items.DYE, 1, EnumColor.BLACK.getInvColorIndex())});
        p_a_1_.registerShapedRecipe(new ItemStack(Blocks.SEA_LANTERN, 1, 0), new Object[] {"SCS", "CCC", "SCS", 'S', Items.PRISMARINE_SHARD, 'C', Items.PRISMARINE_CRYSTALS});
    }
}
