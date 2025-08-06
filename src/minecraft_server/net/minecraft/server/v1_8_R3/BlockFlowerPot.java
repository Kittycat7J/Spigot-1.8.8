package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockFlowerPot extends BlockContainer
{
    public static final BlockStateInteger LEGACY_DATA = BlockStateInteger.of("legacy_data", 0, 15);
    public static final BlockStateEnum<BlockFlowerPot.EnumFlowerPotContents> CONTENTS = BlockStateEnum.<BlockFlowerPot.EnumFlowerPotContents>of("contents", BlockFlowerPot.EnumFlowerPotContents.class);

    public BlockFlowerPot()
    {
        super(Material.ORIENTABLE);
        this.j(this.blockStateList.getBlockData().set(CONTENTS, BlockFlowerPot.EnumFlowerPotContents.EMPTY).set(LEGACY_DATA, Integer.valueOf(0)));
        this.j();
    }

    public String getName()
    {
        return LocaleI18n.get("item.flowerPot.name");
    }

    public void j()
    {
        float f = 0.375F;
        float f1 = f / 2.0F;
        this.a(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, f, 0.5F + f1);
    }

    public boolean c()
    {
        return false;
    }

    public int b()
    {
        return 3;
    }

    public boolean d()
    {
        return false;
    }

    public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_)
    {
        ItemStack itemstack = p_interact_4_.inventory.getItemInHand();

        if (itemstack != null && itemstack.getItem() instanceof ItemBlock)
        {
            TileEntityFlowerPot tileentityflowerpot = this.f(p_interact_1_, p_interact_2_);

            if (tileentityflowerpot == null)
            {
                return false;
            }
            else if (tileentityflowerpot.b() != null)
            {
                return false;
            }
            else
            {
                Block block = Block.asBlock(itemstack.getItem());

                if (!this.a(block, itemstack.getData()))
                {
                    return false;
                }
                else
                {
                    tileentityflowerpot.a(itemstack.getItem(), itemstack.getData());
                    tileentityflowerpot.update();
                    p_interact_1_.notify(p_interact_2_);
                    p_interact_4_.b(StatisticList.T);

                    if (!p_interact_4_.abilities.canInstantlyBuild && --itemstack.count <= 0)
                    {
                        p_interact_4_.inventory.setItem(p_interact_4_.inventory.itemInHandIndex, (ItemStack)null);
                    }

                    return true;
                }
            }
        }
        else
        {
            return false;
        }
    }

    private boolean a(Block p_a_1_, int p_a_2_)
    {
        return p_a_1_ != Blocks.YELLOW_FLOWER && p_a_1_ != Blocks.RED_FLOWER && p_a_1_ != Blocks.CACTUS && p_a_1_ != Blocks.BROWN_MUSHROOM && p_a_1_ != Blocks.RED_MUSHROOM && p_a_1_ != Blocks.SAPLING && p_a_1_ != Blocks.DEADBUSH ? p_a_1_ == Blocks.TALLGRASS && p_a_2_ == BlockLongGrass.EnumTallGrassType.FERN.a() : true;
    }

    public int getDropData(World p_getDropData_1_, BlockPosition p_getDropData_2_)
    {
        TileEntityFlowerPot tileentityflowerpot = this.f(p_getDropData_1_, p_getDropData_2_);
        return tileentityflowerpot != null && tileentityflowerpot.b() != null ? tileentityflowerpot.c() : 0;
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        return super.canPlace(p_canPlace_1_, p_canPlace_2_) && World.a((IBlockAccess)p_canPlace_1_, (BlockPosition)p_canPlace_2_.down());
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        if (!World.a((IBlockAccess)p_doPhysics_1_, (BlockPosition)p_doPhysics_2_.down()))
        {
            this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, 0);
            p_doPhysics_1_.setAir(p_doPhysics_2_);
        }
    }

    public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_)
    {
        TileEntityFlowerPot tileentityflowerpot = this.f(p_remove_1_, p_remove_2_);

        if (tileentityflowerpot != null && tileentityflowerpot.b() != null)
        {
            a(p_remove_1_, p_remove_2_, new ItemStack(tileentityflowerpot.b(), 1, tileentityflowerpot.c()));
            tileentityflowerpot.a((Item)null, 0);
        }

        super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EntityHuman p_a_4_)
    {
        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);

        if (p_a_4_.abilities.canInstantlyBuild)
        {
            TileEntityFlowerPot tileentityflowerpot = this.f(p_a_1_, p_a_2_);

            if (tileentityflowerpot != null)
            {
                tileentityflowerpot.a((Item)null, 0);
            }
        }
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Items.FLOWER_POT;
    }

    private TileEntityFlowerPot f(World p_f_1_, BlockPosition p_f_2_)
    {
        TileEntity tileentity = p_f_1_.getTileEntity(p_f_2_);
        return tileentity instanceof TileEntityFlowerPot ? (TileEntityFlowerPot)tileentity : null;
    }

    public TileEntity a(World p_a_1_, int p_a_2_)
    {
        Object object = null;
        int i = 0;

        switch (p_a_2_)
        {
            case 1:
                object = Blocks.RED_FLOWER;
                i = BlockFlowers.EnumFlowerVarient.POPPY.b();
                break;

            case 2:
                object = Blocks.YELLOW_FLOWER;
                break;

            case 3:
                object = Blocks.SAPLING;
                i = BlockWood.EnumLogVariant.OAK.a();
                break;

            case 4:
                object = Blocks.SAPLING;
                i = BlockWood.EnumLogVariant.SPRUCE.a();
                break;

            case 5:
                object = Blocks.SAPLING;
                i = BlockWood.EnumLogVariant.BIRCH.a();
                break;

            case 6:
                object = Blocks.SAPLING;
                i = BlockWood.EnumLogVariant.JUNGLE.a();
                break;

            case 7:
                object = Blocks.RED_MUSHROOM;
                break;

            case 8:
                object = Blocks.BROWN_MUSHROOM;
                break;

            case 9:
                object = Blocks.CACTUS;
                break;

            case 10:
                object = Blocks.DEADBUSH;
                break;

            case 11:
                object = Blocks.TALLGRASS;
                i = BlockLongGrass.EnumTallGrassType.FERN.a();
                break;

            case 12:
                object = Blocks.SAPLING;
                i = BlockWood.EnumLogVariant.ACACIA.a();
                break;

            case 13:
                object = Blocks.SAPLING;
                i = BlockWood.EnumLogVariant.DARK_OAK.a();
        }

        return new TileEntityFlowerPot(Item.getItemOf((Block)object), i);
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {CONTENTS, LEGACY_DATA});
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((Integer)p_toLegacyData_1_.get(LEGACY_DATA)).intValue();
    }

    public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_)
    {
        BlockFlowerPot.EnumFlowerPotContents blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.EMPTY;
        TileEntity tileentity = p_updateState_2_.getTileEntity(p_updateState_3_);

        if (tileentity instanceof TileEntityFlowerPot)
        {
            TileEntityFlowerPot tileentityflowerpot = (TileEntityFlowerPot)tileentity;
            Item item = tileentityflowerpot.b();

            if (item instanceof ItemBlock)
            {
                int i = tileentityflowerpot.c();
                Block block = Block.asBlock(item);

                if (block == Blocks.SAPLING)
                {
                    switch (BlockFlowerPot.SyntheticClass_1.a[BlockWood.EnumLogVariant.a(i).ordinal()])
                    {
                        case 1:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.OAK_SAPLING;
                            break;

                        case 2:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.SPRUCE_SAPLING;
                            break;

                        case 3:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.BIRCH_SAPLING;
                            break;

                        case 4:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.JUNGLE_SAPLING;
                            break;

                        case 5:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.ACACIA_SAPLING;
                            break;

                        case 6:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.DARK_OAK_SAPLING;
                            break;

                        default:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.EMPTY;
                    }
                }
                else if (block == Blocks.TALLGRASS)
                {
                    switch (i)
                    {
                        case 0:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.DEAD_BUSH;
                            break;

                        case 1:
                        default:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.EMPTY;
                            break;

                        case 2:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.FERN;
                    }
                }
                else if (block == Blocks.YELLOW_FLOWER)
                {
                    blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.DANDELION;
                }
                else if (block == Blocks.RED_FLOWER)
                {
                    switch (BlockFlowerPot.SyntheticClass_1.b[BlockFlowers.EnumFlowerVarient.a(BlockFlowers.EnumFlowerType.RED, i).ordinal()])
                    {
                        case 1:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.POPPY;
                            break;

                        case 2:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.BLUE_ORCHID;
                            break;

                        case 3:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.ALLIUM;
                            break;

                        case 4:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.HOUSTONIA;
                            break;

                        case 5:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.RED_TULIP;
                            break;

                        case 6:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.ORANGE_TULIP;
                            break;

                        case 7:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.WHITE_TULIP;
                            break;

                        case 8:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.PINK_TULIP;
                            break;

                        case 9:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.OXEYE_DAISY;
                            break;

                        default:
                            blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.EMPTY;
                    }
                }
                else if (block == Blocks.RED_MUSHROOM)
                {
                    blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.MUSHROOM_RED;
                }
                else if (block == Blocks.BROWN_MUSHROOM)
                {
                    blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.MUSHROOM_BROWN;
                }
                else if (block == Blocks.DEADBUSH)
                {
                    blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.DEAD_BUSH;
                }
                else if (block == Blocks.CACTUS)
                {
                    blockflowerpot$enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.CACTUS;
                }
            }
        }

        return p_updateState_1_.set(CONTENTS, blockflowerpot$enumflowerpotcontents);
    }

    public static enum EnumFlowerPotContents implements INamable
    {
        EMPTY("empty"),
        POPPY("rose"),
        BLUE_ORCHID("blue_orchid"),
        ALLIUM("allium"),
        HOUSTONIA("houstonia"),
        RED_TULIP("red_tulip"),
        ORANGE_TULIP("orange_tulip"),
        WHITE_TULIP("white_tulip"),
        PINK_TULIP("pink_tulip"),
        OXEYE_DAISY("oxeye_daisy"),
        DANDELION("dandelion"),
        OAK_SAPLING("oak_sapling"),
        SPRUCE_SAPLING("spruce_sapling"),
        BIRCH_SAPLING("birch_sapling"),
        JUNGLE_SAPLING("jungle_sapling"),
        ACACIA_SAPLING("acacia_sapling"),
        DARK_OAK_SAPLING("dark_oak_sapling"),
        MUSHROOM_RED("mushroom_red"),
        MUSHROOM_BROWN("mushroom_brown"),
        DEAD_BUSH("dead_bush"),
        FERN("fern"),
        CACTUS("cactus");

        private final String w;

        private EnumFlowerPotContents(String p_i126_3_)
        {
            this.w = p_i126_3_;
        }

        public String toString()
        {
            return this.w;
        }

        public String getName()
        {
            return this.w;
        }
    }

    static class SyntheticClass_1
    {
        static final int[] a;
        static final int[] b = new int[BlockFlowers.EnumFlowerVarient.values().length];

        static
        {
            try
            {
                b[BlockFlowers.EnumFlowerVarient.POPPY.ordinal()] = 1;
            }
            catch (NoSuchFieldError var14)
            {
                ;
            }

            try
            {
                b[BlockFlowers.EnumFlowerVarient.BLUE_ORCHID.ordinal()] = 2;
            }
            catch (NoSuchFieldError var13)
            {
                ;
            }

            try
            {
                b[BlockFlowers.EnumFlowerVarient.ALLIUM.ordinal()] = 3;
            }
            catch (NoSuchFieldError var12)
            {
                ;
            }

            try
            {
                b[BlockFlowers.EnumFlowerVarient.HOUSTONIA.ordinal()] = 4;
            }
            catch (NoSuchFieldError var11)
            {
                ;
            }

            try
            {
                b[BlockFlowers.EnumFlowerVarient.RED_TULIP.ordinal()] = 5;
            }
            catch (NoSuchFieldError var10)
            {
                ;
            }

            try
            {
                b[BlockFlowers.EnumFlowerVarient.ORANGE_TULIP.ordinal()] = 6;
            }
            catch (NoSuchFieldError var9)
            {
                ;
            }

            try
            {
                b[BlockFlowers.EnumFlowerVarient.WHITE_TULIP.ordinal()] = 7;
            }
            catch (NoSuchFieldError var8)
            {
                ;
            }

            try
            {
                b[BlockFlowers.EnumFlowerVarient.PINK_TULIP.ordinal()] = 8;
            }
            catch (NoSuchFieldError var7)
            {
                ;
            }

            try
            {
                b[BlockFlowers.EnumFlowerVarient.OXEYE_DAISY.ordinal()] = 9;
            }
            catch (NoSuchFieldError var6)
            {
                ;
            }

            a = new int[BlockWood.EnumLogVariant.values().length];

            try
            {
                a[BlockWood.EnumLogVariant.OAK.ordinal()] = 1;
            }
            catch (NoSuchFieldError var5)
            {
                ;
            }

            try
            {
                a[BlockWood.EnumLogVariant.SPRUCE.ordinal()] = 2;
            }
            catch (NoSuchFieldError var4)
            {
                ;
            }

            try
            {
                a[BlockWood.EnumLogVariant.BIRCH.ordinal()] = 3;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }

            try
            {
                a[BlockWood.EnumLogVariant.JUNGLE.ordinal()] = 4;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                a[BlockWood.EnumLogVariant.ACACIA.ordinal()] = 5;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }

            try
            {
                a[BlockWood.EnumLogVariant.DARK_OAK.ordinal()] = 6;
            }
            catch (NoSuchFieldError var0)
            {
                ;
            }
        }
    }
}
