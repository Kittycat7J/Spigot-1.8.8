package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.inventory.InventoryView;

public class CraftingManager
{
    private static final CraftingManager a = new CraftingManager();
    public List<IRecipe> recipes = Lists.<IRecipe>newArrayList();
    public IRecipe lastRecipe;
    public InventoryView lastCraftView;

    public static CraftingManager getInstance()
    {
        return a;
    }

    public CraftingManager()
    {
        (new RecipesTools()).a(this);
        (new RecipesWeapons()).a(this);
        (new RecipeIngots()).a(this);
        (new RecipesFood()).a(this);
        (new RecipesCrafting()).a(this);
        (new RecipesArmor()).a(this);
        (new RecipesDyes()).a(this);
        this.recipes.add(new RecipeArmorDye());
        this.recipes.add(new RecipeBookClone());
        this.recipes.add(new RecipeMapClone());
        this.recipes.add(new RecipeMapExtend());
        this.recipes.add(new RecipeFireworks());
        this.recipes.add(new RecipeRepair());
        (new RecipesBanner()).a(this);
        this.registerShapedRecipe(new ItemStack(Items.PAPER, 3), new Object[] {"###", '#', Items.REEDS});
        this.registerShapelessRecipe(new ItemStack(Items.BOOK, 1), new Object[] {Items.PAPER, Items.PAPER, Items.PAPER, Items.LEATHER});
        this.registerShapelessRecipe(new ItemStack(Items.WRITABLE_BOOK, 1), new Object[] {Items.BOOK, new ItemStack(Items.DYE, 1, EnumColor.BLACK.getInvColorIndex()), Items.FEATHER});
        this.registerShapedRecipe(new ItemStack(Blocks.FENCE, 3), new Object[] {"W#W", "W#W", '#', Items.STICK, 'W', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.OAK.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.BIRCH_FENCE, 3), new Object[] {"W#W", "W#W", '#', Items.STICK, 'W', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.BIRCH.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.SPRUCE_FENCE, 3), new Object[] {"W#W", "W#W", '#', Items.STICK, 'W', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.SPRUCE.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.JUNGLE_FENCE, 3), new Object[] {"W#W", "W#W", '#', Items.STICK, 'W', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.JUNGLE.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.ACACIA_FENCE, 3), new Object[] {"W#W", "W#W", '#', Items.STICK, 'W', new ItemStack(Blocks.PLANKS, 1, 4 + BlockWood.EnumLogVariant.ACACIA.a() - 4)});
        this.registerShapedRecipe(new ItemStack(Blocks.DARK_OAK_FENCE, 3), new Object[] {"W#W", "W#W", '#', Items.STICK, 'W', new ItemStack(Blocks.PLANKS, 1, 4 + BlockWood.EnumLogVariant.DARK_OAK.a() - 4)});
        this.registerShapedRecipe(new ItemStack(Blocks.COBBLESTONE_WALL, 6, BlockCobbleWall.EnumCobbleVariant.NORMAL.a()), new Object[] {"###", "###", '#', Blocks.COBBLESTONE});
        this.registerShapedRecipe(new ItemStack(Blocks.COBBLESTONE_WALL, 6, BlockCobbleWall.EnumCobbleVariant.MOSSY.a()), new Object[] {"###", "###", '#', Blocks.MOSSY_COBBLESTONE});
        this.registerShapedRecipe(new ItemStack(Blocks.NETHER_BRICK_FENCE, 6), new Object[] {"###", "###", '#', Blocks.NETHER_BRICK});
        this.registerShapedRecipe(new ItemStack(Blocks.FENCE_GATE, 1), new Object[] {"#W#", "#W#", '#', Items.STICK, 'W', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.OAK.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.BIRCH_FENCE_GATE, 1), new Object[] {"#W#", "#W#", '#', Items.STICK, 'W', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.BIRCH.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.SPRUCE_FENCE_GATE, 1), new Object[] {"#W#", "#W#", '#', Items.STICK, 'W', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.SPRUCE.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.JUNGLE_FENCE_GATE, 1), new Object[] {"#W#", "#W#", '#', Items.STICK, 'W', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.JUNGLE.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.ACACIA_FENCE_GATE, 1), new Object[] {"#W#", "#W#", '#', Items.STICK, 'W', new ItemStack(Blocks.PLANKS, 1, 4 + BlockWood.EnumLogVariant.ACACIA.a() - 4)});
        this.registerShapedRecipe(new ItemStack(Blocks.DARK_OAK_FENCE_GATE, 1), new Object[] {"#W#", "#W#", '#', Items.STICK, 'W', new ItemStack(Blocks.PLANKS, 1, 4 + BlockWood.EnumLogVariant.DARK_OAK.a() - 4)});
        this.registerShapedRecipe(new ItemStack(Blocks.JUKEBOX, 1), new Object[] {"###", "#X#", "###", '#', Blocks.PLANKS, 'X', Items.DIAMOND});
        this.registerShapedRecipe(new ItemStack(Items.LEAD, 2), new Object[] {"~~ ", "~O ", "  ~", '~', Items.STRING, 'O', Items.SLIME});
        this.registerShapedRecipe(new ItemStack(Blocks.NOTEBLOCK, 1), new Object[] {"###", "#X#", "###", '#', Blocks.PLANKS, 'X', Items.REDSTONE});
        this.registerShapedRecipe(new ItemStack(Blocks.BOOKSHELF, 1), new Object[] {"###", "XXX", "###", '#', Blocks.PLANKS, 'X', Items.BOOK});
        this.registerShapedRecipe(new ItemStack(Blocks.SNOW, 1), new Object[] {"##", "##", '#', Items.SNOWBALL});
        this.registerShapedRecipe(new ItemStack(Blocks.SNOW_LAYER, 6), new Object[] {"###", '#', Blocks.SNOW});
        this.registerShapedRecipe(new ItemStack(Blocks.CLAY, 1), new Object[] {"##", "##", '#', Items.CLAY_BALL});
        this.registerShapedRecipe(new ItemStack(Blocks.BRICK_BLOCK, 1), new Object[] {"##", "##", '#', Items.BRICK});
        this.registerShapedRecipe(new ItemStack(Blocks.GLOWSTONE, 1), new Object[] {"##", "##", '#', Items.GLOWSTONE_DUST});
        this.registerShapedRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1), new Object[] {"##", "##", '#', Items.QUARTZ});
        this.registerShapedRecipe(new ItemStack(Blocks.WOOL, 1), new Object[] {"##", "##", '#', Items.STRING});
        this.registerShapedRecipe(new ItemStack(Blocks.TNT, 1), new Object[] {"X#X", "#X#", "X#X", 'X', Items.GUNPOWDER, '#', Blocks.SAND});
        this.registerShapedRecipe(new ItemStack(Blocks.STONE_SLAB, 6, BlockDoubleStepAbstract.EnumStoneSlabVariant.COBBLESTONE.a()), new Object[] {"###", '#', Blocks.COBBLESTONE});
        this.registerShapedRecipe(new ItemStack(Blocks.STONE_SLAB, 6, BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE.a()), new Object[] {"###", '#', new ItemStack(Blocks.STONE, BlockStone.EnumStoneVariant.STONE.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.STONE_SLAB, 6, BlockDoubleStepAbstract.EnumStoneSlabVariant.SAND.a()), new Object[] {"###", '#', Blocks.SANDSTONE});
        this.registerShapedRecipe(new ItemStack(Blocks.STONE_SLAB, 6, BlockDoubleStepAbstract.EnumStoneSlabVariant.BRICK.a()), new Object[] {"###", '#', Blocks.BRICK_BLOCK});
        this.registerShapedRecipe(new ItemStack(Blocks.STONE_SLAB, 6, BlockDoubleStepAbstract.EnumStoneSlabVariant.SMOOTHBRICK.a()), new Object[] {"###", '#', Blocks.STONEBRICK});
        this.registerShapedRecipe(new ItemStack(Blocks.STONE_SLAB, 6, BlockDoubleStepAbstract.EnumStoneSlabVariant.NETHERBRICK.a()), new Object[] {"###", '#', Blocks.NETHER_BRICK});
        this.registerShapedRecipe(new ItemStack(Blocks.STONE_SLAB, 6, BlockDoubleStepAbstract.EnumStoneSlabVariant.QUARTZ.a()), new Object[] {"###", '#', Blocks.QUARTZ_BLOCK});
        this.registerShapedRecipe(new ItemStack(Blocks.STONE_SLAB2, 6, BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant.RED_SANDSTONE.a()), new Object[] {"###", '#', Blocks.RED_SANDSTONE});
        this.registerShapedRecipe(new ItemStack(Blocks.WOODEN_SLAB, 6, 0), new Object[] {"###", '#', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.OAK.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.WOODEN_SLAB, 6, BlockWood.EnumLogVariant.BIRCH.a()), new Object[] {"###", '#', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.BIRCH.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.WOODEN_SLAB, 6, BlockWood.EnumLogVariant.SPRUCE.a()), new Object[] {"###", '#', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.SPRUCE.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.WOODEN_SLAB, 6, BlockWood.EnumLogVariant.JUNGLE.a()), new Object[] {"###", '#', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.JUNGLE.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.WOODEN_SLAB, 6, 4 + BlockWood.EnumLogVariant.ACACIA.a() - 4), new Object[] {"###", '#', new ItemStack(Blocks.PLANKS, 1, 4 + BlockWood.EnumLogVariant.ACACIA.a() - 4)});
        this.registerShapedRecipe(new ItemStack(Blocks.WOODEN_SLAB, 6, 4 + BlockWood.EnumLogVariant.DARK_OAK.a() - 4), new Object[] {"###", '#', new ItemStack(Blocks.PLANKS, 1, 4 + BlockWood.EnumLogVariant.DARK_OAK.a() - 4)});
        this.registerShapedRecipe(new ItemStack(Blocks.LADDER, 3), new Object[] {"# #", "###", "# #", '#', Items.STICK});
        this.registerShapedRecipe(new ItemStack(Items.WOODEN_DOOR, 3), new Object[] {"##", "##", "##", '#', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.OAK.a())});
        this.registerShapedRecipe(new ItemStack(Items.SPRUCE_DOOR, 3), new Object[] {"##", "##", "##", '#', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.SPRUCE.a())});
        this.registerShapedRecipe(new ItemStack(Items.BIRCH_DOOR, 3), new Object[] {"##", "##", "##", '#', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.BIRCH.a())});
        this.registerShapedRecipe(new ItemStack(Items.JUNGLE_DOOR, 3), new Object[] {"##", "##", "##", '#', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.JUNGLE.a())});
        this.registerShapedRecipe(new ItemStack(Items.ACACIA_DOOR, 3), new Object[] {"##", "##", "##", '#', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.ACACIA.a())});
        this.registerShapedRecipe(new ItemStack(Items.DARK_OAK_DOOR, 3), new Object[] {"##", "##", "##", '#', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.DARK_OAK.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.TRAPDOOR, 2), new Object[] {"###", "###", '#', Blocks.PLANKS});
        this.registerShapedRecipe(new ItemStack(Items.IRON_DOOR, 3), new Object[] {"##", "##", "##", '#', Items.IRON_INGOT});
        this.registerShapedRecipe(new ItemStack(Blocks.IRON_TRAPDOOR, 1), new Object[] {"##", "##", '#', Items.IRON_INGOT});
        this.registerShapedRecipe(new ItemStack(Items.SIGN, 3), new Object[] {"###", "###", " X ", '#', Blocks.PLANKS, 'X', Items.STICK});
        this.registerShapedRecipe(new ItemStack(Items.CAKE, 1), new Object[] {"AAA", "BEB", "CCC", 'A', Items.MILK_BUCKET, 'B', Items.SUGAR, 'C', Items.WHEAT, 'E', Items.EGG});
        this.registerShapedRecipe(new ItemStack(Items.SUGAR, 1), new Object[] {"#", '#', Items.REEDS});
        this.registerShapedRecipe(new ItemStack(Blocks.PLANKS, 4, BlockWood.EnumLogVariant.OAK.a()), new Object[] {"#", '#', new ItemStack(Blocks.LOG, 1, BlockWood.EnumLogVariant.OAK.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.PLANKS, 4, BlockWood.EnumLogVariant.SPRUCE.a()), new Object[] {"#", '#', new ItemStack(Blocks.LOG, 1, BlockWood.EnumLogVariant.SPRUCE.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.PLANKS, 4, BlockWood.EnumLogVariant.BIRCH.a()), new Object[] {"#", '#', new ItemStack(Blocks.LOG, 1, BlockWood.EnumLogVariant.BIRCH.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.PLANKS, 4, BlockWood.EnumLogVariant.JUNGLE.a()), new Object[] {"#", '#', new ItemStack(Blocks.LOG, 1, BlockWood.EnumLogVariant.JUNGLE.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.PLANKS, 4, 4 + BlockWood.EnumLogVariant.ACACIA.a() - 4), new Object[] {"#", '#', new ItemStack(Blocks.LOG2, 1, BlockWood.EnumLogVariant.ACACIA.a() - 4)});
        this.registerShapedRecipe(new ItemStack(Blocks.PLANKS, 4, 4 + BlockWood.EnumLogVariant.DARK_OAK.a() - 4), new Object[] {"#", '#', new ItemStack(Blocks.LOG2, 1, BlockWood.EnumLogVariant.DARK_OAK.a() - 4)});
        this.registerShapedRecipe(new ItemStack(Items.STICK, 4), new Object[] {"#", "#", '#', Blocks.PLANKS});
        this.registerShapedRecipe(new ItemStack(Blocks.TORCH, 4), new Object[] {"X", "#", 'X', Items.COAL, '#', Items.STICK});
        this.registerShapedRecipe(new ItemStack(Blocks.TORCH, 4), new Object[] {"X", "#", 'X', new ItemStack(Items.COAL, 1, 1), '#', Items.STICK});
        this.registerShapedRecipe(new ItemStack(Items.BOWL, 4), new Object[] {"# #", " # ", '#', Blocks.PLANKS});
        this.registerShapedRecipe(new ItemStack(Items.GLASS_BOTTLE, 3), new Object[] {"# #", " # ", '#', Blocks.GLASS});
        this.registerShapedRecipe(new ItemStack(Blocks.RAIL, 16), new Object[] {"X X", "X#X", "X X", 'X', Items.IRON_INGOT, '#', Items.STICK});
        this.registerShapedRecipe(new ItemStack(Blocks.GOLDEN_RAIL, 6), new Object[] {"X X", "X#X", "XRX", 'X', Items.GOLD_INGOT, 'R', Items.REDSTONE, '#', Items.STICK});
        this.registerShapedRecipe(new ItemStack(Blocks.ACTIVATOR_RAIL, 6), new Object[] {"XSX", "X#X", "XSX", 'X', Items.IRON_INGOT, '#', Blocks.REDSTONE_TORCH, 'S', Items.STICK});
        this.registerShapedRecipe(new ItemStack(Blocks.DETECTOR_RAIL, 6), new Object[] {"X X", "X#X", "XRX", 'X', Items.IRON_INGOT, 'R', Items.REDSTONE, '#', Blocks.STONE_PRESSURE_PLATE});
        this.registerShapedRecipe(new ItemStack(Items.MINECART, 1), new Object[] {"# #", "###", '#', Items.IRON_INGOT});
        this.registerShapedRecipe(new ItemStack(Items.CAULDRON, 1), new Object[] {"# #", "# #", "###", '#', Items.IRON_INGOT});
        this.registerShapedRecipe(new ItemStack(Items.BREWING_STAND, 1), new Object[] {" B ", "###", '#', Blocks.COBBLESTONE, 'B', Items.BLAZE_ROD});
        this.registerShapedRecipe(new ItemStack(Blocks.LIT_PUMPKIN, 1), new Object[] {"A", "B", 'A', Blocks.PUMPKIN, 'B', Blocks.TORCH});
        this.registerShapedRecipe(new ItemStack(Items.CHEST_MINECART, 1), new Object[] {"A", "B", 'A', Blocks.CHEST, 'B', Items.MINECART});
        this.registerShapedRecipe(new ItemStack(Items.FURNACE_MINECART, 1), new Object[] {"A", "B", 'A', Blocks.FURNACE, 'B', Items.MINECART});
        this.registerShapedRecipe(new ItemStack(Items.TNT_MINECART, 1), new Object[] {"A", "B", 'A', Blocks.TNT, 'B', Items.MINECART});
        this.registerShapedRecipe(new ItemStack(Items.HOPPER_MINECART, 1), new Object[] {"A", "B", 'A', Blocks.HOPPER, 'B', Items.MINECART});
        this.registerShapedRecipe(new ItemStack(Items.BOAT, 1), new Object[] {"# #", "###", '#', Blocks.PLANKS});
        this.registerShapedRecipe(new ItemStack(Items.BUCKET, 1), new Object[] {"# #", " # ", '#', Items.IRON_INGOT});
        this.registerShapedRecipe(new ItemStack(Items.FLOWER_POT, 1), new Object[] {"# #", " # ", '#', Items.BRICK});
        this.registerShapelessRecipe(new ItemStack(Items.FLINT_AND_STEEL, 1), new Object[] {new ItemStack(Items.IRON_INGOT, 1), new ItemStack(Items.FLINT, 1)});
        this.registerShapedRecipe(new ItemStack(Items.BREAD, 1), new Object[] {"###", '#', Items.WHEAT});
        this.registerShapedRecipe(new ItemStack(Blocks.OAK_STAIRS, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.OAK.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.BIRCH_STAIRS, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.BIRCH.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.SPRUCE_STAIRS, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.SPRUCE.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.JUNGLE_STAIRS, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.JUNGLE.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.ACACIA_STAIRS, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Blocks.PLANKS, 1, 4 + BlockWood.EnumLogVariant.ACACIA.a() - 4)});
        this.registerShapedRecipe(new ItemStack(Blocks.DARK_OAK_STAIRS, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Blocks.PLANKS, 1, 4 + BlockWood.EnumLogVariant.DARK_OAK.a() - 4)});
        this.registerShapedRecipe(new ItemStack(Items.FISHING_ROD, 1), new Object[] {"  #", " #X", "# X", '#', Items.STICK, 'X', Items.STRING});
        this.registerShapedRecipe(new ItemStack(Items.CARROT_ON_A_STICK, 1), new Object[] {"# ", " X", '#', Items.FISHING_ROD, 'X', Items.CARROT});
        this.registerShapedRecipe(new ItemStack(Blocks.STONE_STAIRS, 4), new Object[] {"#  ", "## ", "###", '#', Blocks.COBBLESTONE});
        this.registerShapedRecipe(new ItemStack(Blocks.BRICK_STAIRS, 4), new Object[] {"#  ", "## ", "###", '#', Blocks.BRICK_BLOCK});
        this.registerShapedRecipe(new ItemStack(Blocks.STONE_BRICK_STAIRS, 4), new Object[] {"#  ", "## ", "###", '#', Blocks.STONEBRICK});
        this.registerShapedRecipe(new ItemStack(Blocks.NETHER_BRICK_STAIRS, 4), new Object[] {"#  ", "## ", "###", '#', Blocks.NETHER_BRICK});
        this.registerShapedRecipe(new ItemStack(Blocks.SANDSTONE_STAIRS, 4), new Object[] {"#  ", "## ", "###", '#', Blocks.SANDSTONE});
        this.registerShapedRecipe(new ItemStack(Blocks.RED_SANDSTONE_STAIRS, 4), new Object[] {"#  ", "## ", "###", '#', Blocks.RED_SANDSTONE});
        this.registerShapedRecipe(new ItemStack(Blocks.QUARTZ_STAIRS, 4), new Object[] {"#  ", "## ", "###", '#', Blocks.QUARTZ_BLOCK});
        this.registerShapedRecipe(new ItemStack(Items.PAINTING, 1), new Object[] {"###", "#X#", "###", '#', Items.STICK, 'X', Blocks.WOOL});
        this.registerShapedRecipe(new ItemStack(Items.ITEM_FRAME, 1), new Object[] {"###", "#X#", "###", '#', Items.STICK, 'X', Items.LEATHER});
        this.registerShapedRecipe(new ItemStack(Items.GOLDEN_APPLE, 1, 0), new Object[] {"###", "#X#", "###", '#', Items.GOLD_INGOT, 'X', Items.APPLE});
        this.registerShapedRecipe(new ItemStack(Items.GOLDEN_APPLE, 1, 1), new Object[] {"###", "#X#", "###", '#', Blocks.GOLD_BLOCK, 'X', Items.APPLE});
        this.registerShapedRecipe(new ItemStack(Items.GOLDEN_CARROT, 1, 0), new Object[] {"###", "#X#", "###", '#', Items.GOLD_NUGGET, 'X', Items.CARROT});
        this.registerShapedRecipe(new ItemStack(Items.SPECKLED_MELON, 1), new Object[] {"###", "#X#", "###", '#', Items.GOLD_NUGGET, 'X', Items.MELON});
        this.registerShapedRecipe(new ItemStack(Blocks.LEVER, 1), new Object[] {"X", "#", '#', Blocks.COBBLESTONE, 'X', Items.STICK});
        this.registerShapedRecipe(new ItemStack(Blocks.TRIPWIRE_HOOK, 2), new Object[] {"I", "S", "#", '#', Blocks.PLANKS, 'S', Items.STICK, 'I', Items.IRON_INGOT});
        this.registerShapedRecipe(new ItemStack(Blocks.REDSTONE_TORCH, 1), new Object[] {"X", "#", '#', Items.STICK, 'X', Items.REDSTONE});
        this.registerShapedRecipe(new ItemStack(Items.REPEATER, 1), new Object[] {"#X#", "III", '#', Blocks.REDSTONE_TORCH, 'X', Items.REDSTONE, 'I', new ItemStack(Blocks.STONE, 1, BlockStone.EnumStoneVariant.STONE.a())});
        this.registerShapedRecipe(new ItemStack(Items.COMPARATOR, 1), new Object[] {" # ", "#X#", "III", '#', Blocks.REDSTONE_TORCH, 'X', Items.QUARTZ, 'I', new ItemStack(Blocks.STONE, 1, BlockStone.EnumStoneVariant.STONE.a())});
        this.registerShapedRecipe(new ItemStack(Items.CLOCK, 1), new Object[] {" # ", "#X#", " # ", '#', Items.GOLD_INGOT, 'X', Items.REDSTONE});
        this.registerShapedRecipe(new ItemStack(Items.COMPASS, 1), new Object[] {" # ", "#X#", " # ", '#', Items.IRON_INGOT, 'X', Items.REDSTONE});
        this.registerShapedRecipe(new ItemStack(Items.MAP, 1), new Object[] {"###", "#X#", "###", '#', Items.PAPER, 'X', Items.COMPASS});
        this.registerShapedRecipe(new ItemStack(Blocks.STONE_BUTTON, 1), new Object[] {"#", '#', new ItemStack(Blocks.STONE, 1, BlockStone.EnumStoneVariant.STONE.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.WOODEN_BUTTON, 1), new Object[] {"#", '#', Blocks.PLANKS});
        this.registerShapedRecipe(new ItemStack(Blocks.STONE_PRESSURE_PLATE, 1), new Object[] {"##", '#', new ItemStack(Blocks.STONE, 1, BlockStone.EnumStoneVariant.STONE.a())});
        this.registerShapedRecipe(new ItemStack(Blocks.WOODEN_PRESSURE_PLATE, 1), new Object[] {"##", '#', Blocks.PLANKS});
        this.registerShapedRecipe(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 1), new Object[] {"##", '#', Items.IRON_INGOT});
        this.registerShapedRecipe(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, 1), new Object[] {"##", '#', Items.GOLD_INGOT});
        this.registerShapedRecipe(new ItemStack(Blocks.DISPENSER, 1), new Object[] {"###", "#X#", "#R#", '#', Blocks.COBBLESTONE, 'X', Items.BOW, 'R', Items.REDSTONE});
        this.registerShapedRecipe(new ItemStack(Blocks.DROPPER, 1), new Object[] {"###", "# #", "#R#", '#', Blocks.COBBLESTONE, 'R', Items.REDSTONE});
        this.registerShapedRecipe(new ItemStack(Blocks.PISTON, 1), new Object[] {"TTT", "#X#", "#R#", '#', Blocks.COBBLESTONE, 'X', Items.IRON_INGOT, 'R', Items.REDSTONE, 'T', Blocks.PLANKS});
        this.registerShapedRecipe(new ItemStack(Blocks.STICKY_PISTON, 1), new Object[] {"S", "P", 'S', Items.SLIME, 'P', Blocks.PISTON});
        this.registerShapedRecipe(new ItemStack(Items.BED, 1), new Object[] {"###", "XXX", '#', Blocks.WOOL, 'X', Blocks.PLANKS});
        this.registerShapedRecipe(new ItemStack(Blocks.ENCHANTING_TABLE, 1), new Object[] {" B ", "D#D", "###", '#', Blocks.OBSIDIAN, 'B', Items.BOOK, 'D', Items.DIAMOND});
        this.registerShapedRecipe(new ItemStack(Blocks.ANVIL, 1), new Object[] {"III", " i ", "iii", 'I', Blocks.IRON_BLOCK, 'i', Items.IRON_INGOT});
        this.registerShapedRecipe(new ItemStack(Items.LEATHER), new Object[] {"##", "##", '#', Items.RABBIT_HIDE});
        this.registerShapelessRecipe(new ItemStack(Items.ENDER_EYE, 1), new Object[] {Items.ENDER_PEARL, Items.BLAZE_POWDER});
        this.registerShapelessRecipe(new ItemStack(Items.FIRE_CHARGE, 3), new Object[] {Items.GUNPOWDER, Items.BLAZE_POWDER, Items.COAL});
        this.registerShapelessRecipe(new ItemStack(Items.FIRE_CHARGE, 3), new Object[] {Items.GUNPOWDER, Items.BLAZE_POWDER, new ItemStack(Items.COAL, 1, 1)});
        this.registerShapedRecipe(new ItemStack(Blocks.DAYLIGHT_DETECTOR), new Object[] {"GGG", "QQQ", "WWW", 'G', Blocks.GLASS, 'Q', Items.QUARTZ, 'W', Blocks.WOODEN_SLAB});
        this.registerShapedRecipe(new ItemStack(Blocks.HOPPER), new Object[] {"I I", "ICI", " I ", 'I', Items.IRON_INGOT, 'C', Blocks.CHEST});
        this.registerShapedRecipe(new ItemStack(Items.ARMOR_STAND, 1), new Object[] {"///", " / ", "/_/", '/', Items.STICK, '_', new ItemStack(Blocks.STONE_SLAB, 1, BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE.a())});
        this.sort();
    }

    public void sort()
    {
        Collections.sort(this.recipes, new Comparator()
        {
            public int a(IRecipe p_a_1_, IRecipe p_a_2_)
            {
                return p_a_1_ instanceof ShapelessRecipes && p_a_2_ instanceof ShapedRecipes ? 1 : (p_a_2_ instanceof ShapelessRecipes && p_a_1_ instanceof ShapedRecipes ? -1 : (p_a_2_.a() < p_a_1_.a() ? -1 : (p_a_2_.a() > p_a_1_.a() ? 1 : 0)));
            }
            public int compare(Object p_compare_1_, Object p_compare_2_)
            {
                return this.a((IRecipe)p_compare_1_, (IRecipe)p_compare_2_);
            }
        });
    }

    public ShapedRecipes registerShapedRecipe(ItemStack p_registerShapedRecipe_1_, Object... p_registerShapedRecipe_2_)
    {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (p_registerShapedRecipe_2_[i] instanceof String[])
        {
            String[] astring = (String[])p_registerShapedRecipe_2_[i++];

            for (int l = 0; l < astring.length; ++l)
            {
                String s2 = astring[l];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }
        else
        {
            while (p_registerShapedRecipe_2_[i] instanceof String)
            {
                String s1 = (String)p_registerShapedRecipe_2_[i++];
                ++k;
                j = s1.length();
                s = s + s1;
            }
        }

        HashMap hashmap;

        for (hashmap = Maps.newHashMap(); i < p_registerShapedRecipe_2_.length; i += 2)
        {
            Character character = (Character)p_registerShapedRecipe_2_[i];
            ItemStack itemstack = null;

            if (p_registerShapedRecipe_2_[i + 1] instanceof Item)
            {
                itemstack = new ItemStack((Item)p_registerShapedRecipe_2_[i + 1]);
            }
            else if (p_registerShapedRecipe_2_[i + 1] instanceof Block)
            {
                itemstack = new ItemStack((Block)p_registerShapedRecipe_2_[i + 1], 1, 32767);
            }
            else if (p_registerShapedRecipe_2_[i + 1] instanceof ItemStack)
            {
                itemstack = (ItemStack)p_registerShapedRecipe_2_[i + 1];
            }

            hashmap.put(character, itemstack);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for (int i1 = 0; i1 < j * k; ++i1)
        {
            char c0 = s.charAt(i1);

            if (hashmap.containsKey(Character.valueOf(c0)))
            {
                aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).cloneItemStack();
            }
            else
            {
                aitemstack[i1] = null;
            }
        }

        ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, aitemstack, p_registerShapedRecipe_1_);
        this.recipes.add(shapedrecipes);
        return shapedrecipes;
    }

    public void registerShapelessRecipe(ItemStack p_registerShapelessRecipe_1_, Object... p_registerShapelessRecipe_2_)
    {
        ArrayList arraylist = Lists.newArrayList();

        for (Object object : p_registerShapelessRecipe_2_)
        {
            if (object instanceof ItemStack)
            {
                arraylist.add(((ItemStack)object).cloneItemStack());
            }
            else if (object instanceof Item)
            {
                arraylist.add(new ItemStack((Item)object));
            }
            else
            {
                if (!(object instanceof Block))
                {
                    throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
                }

                arraylist.add(new ItemStack((Block)object));
            }
        }

        this.recipes.add(new ShapelessRecipes(p_registerShapelessRecipe_1_, arraylist));
    }

    public void a(IRecipe p_a_1_)
    {
        this.recipes.add(p_a_1_);
    }

    public ItemStack craft(InventoryCrafting p_craft_1_, World p_craft_2_)
    {
        for (IRecipe irecipe : this.recipes)
        {
            if (irecipe.a(p_craft_1_, p_craft_2_))
            {
                p_craft_1_.currentRecipe = irecipe;
                ItemStack itemstack = irecipe.craftItem(p_craft_1_);
                return CraftEventFactory.callPreCraftEvent(p_craft_1_, itemstack, this.lastCraftView, false);
            }
        }

        p_craft_1_.currentRecipe = null;
        return null;
    }

    public ItemStack[] b(InventoryCrafting p_b_1_, World p_b_2_)
    {
        for (IRecipe irecipe : this.recipes)
        {
            if (irecipe.a(p_b_1_, p_b_2_))
            {
                return irecipe.b(p_b_1_);
            }
        }

        ItemStack[] aitemstack = new ItemStack[p_b_1_.getSize()];

        for (int i = 0; i < aitemstack.length; ++i)
        {
            aitemstack[i] = p_b_1_.getItem(i);
        }

        return aitemstack;
    }

    public List<IRecipe> getRecipes()
    {
        return this.recipes;
    }
}
