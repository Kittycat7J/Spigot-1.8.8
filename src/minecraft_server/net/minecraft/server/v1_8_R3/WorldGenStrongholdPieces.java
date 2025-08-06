package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;

public class WorldGenStrongholdPieces
{
    private static final WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight[] b = new WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight[] {new WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight(WorldGenStrongholdPieces.WorldGenStrongholdStairs.class, 40, 0), new WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight(WorldGenStrongholdPieces.WorldGenStrongholdPrison.class, 5, 5), new WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight(WorldGenStrongholdPieces.WorldGenStrongholdLeftTurn.class, 20, 0), new WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight(WorldGenStrongholdPieces.WorldGenStrongholdRightTurn.class, 20, 0), new WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight(WorldGenStrongholdPieces.WorldGenStrongholdRoomCrossing.class, 10, 6), new WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight(WorldGenStrongholdPieces.WorldGenStrongholdStairsStraight.class, 5, 5), new WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight(WorldGenStrongholdPieces.WorldGenStrongholdStairs2.class, 5, 5), new WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight(WorldGenStrongholdPieces.WorldGenStrongholdCrossing.class, 5, 4), new WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight(WorldGenStrongholdPieces.WorldGenStrongholdChestCorridor.class, 5, 4), new WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight(WorldGenStrongholdPieces.WorldGenStrongholdLibrary.class, 10, 2)
        {
            public boolean a(int p_a_1_)
            {
                return super.a(p_a_1_) && p_a_1_ > 4;
            }
        }, new WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight(WorldGenStrongholdPieces.WorldGenStrongholdPortalRoom.class, 20, 1)
        {
            public boolean a(int p_a_1_)
            {
                return super.a(p_a_1_) && p_a_1_ > 5;
            }
        }
    };
    private static List<WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight> c;
    private static Class <? extends WorldGenStrongholdPieces.WorldGenStrongholdPiece > d;
    static int a;
    private static final WorldGenStrongholdPieces.WorldGenStrongholdStones e = new WorldGenStrongholdPieces.WorldGenStrongholdStones();

    public static void a()
    {
        WorldGenFactory.a(WorldGenStrongholdPieces.WorldGenStrongholdChestCorridor.class, "SHCC");
        WorldGenFactory.a(WorldGenStrongholdPieces.WorldGenStrongholdCorridor.class, "SHFC");
        WorldGenFactory.a(WorldGenStrongholdPieces.WorldGenStrongholdCrossing.class, "SH5C");
        WorldGenFactory.a(WorldGenStrongholdPieces.WorldGenStrongholdLeftTurn.class, "SHLT");
        WorldGenFactory.a(WorldGenStrongholdPieces.WorldGenStrongholdLibrary.class, "SHLi");
        WorldGenFactory.a(WorldGenStrongholdPieces.WorldGenStrongholdPortalRoom.class, "SHPR");
        WorldGenFactory.a(WorldGenStrongholdPieces.WorldGenStrongholdPrison.class, "SHPH");
        WorldGenFactory.a(WorldGenStrongholdPieces.WorldGenStrongholdRightTurn.class, "SHRT");
        WorldGenFactory.a(WorldGenStrongholdPieces.WorldGenStrongholdRoomCrossing.class, "SHRC");
        WorldGenFactory.a(WorldGenStrongholdPieces.WorldGenStrongholdStairs2.class, "SHSD");
        WorldGenFactory.a(WorldGenStrongholdPieces.WorldGenStrongholdStart.class, "SHStart");
        WorldGenFactory.a(WorldGenStrongholdPieces.WorldGenStrongholdStairs.class, "SHS");
        WorldGenFactory.a(WorldGenStrongholdPieces.WorldGenStrongholdStairsStraight.class, "SHSSD");
    }

    public static void b()
    {
        c = Lists.<WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight>newArrayList();

        for (WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight worldgenstrongholdpieces$worldgenstrongholdpieceweight : b)
        {
            worldgenstrongholdpieces$worldgenstrongholdpieceweight.c = 0;
            c.add(worldgenstrongholdpieces$worldgenstrongholdpieceweight);
        }

        d = null;
    }

    private static boolean d()
    {
        boolean flag = false;
        a = 0;

        for (WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight worldgenstrongholdpieces$worldgenstrongholdpieceweight : c)
        {
            if (worldgenstrongholdpieces$worldgenstrongholdpieceweight.d > 0 && worldgenstrongholdpieces$worldgenstrongholdpieceweight.c < worldgenstrongholdpieces$worldgenstrongholdpieceweight.d)
            {
                flag = true;
            }

            a += worldgenstrongholdpieces$worldgenstrongholdpieceweight.b;
        }

        return flag;
    }

    private static WorldGenStrongholdPieces.WorldGenStrongholdPiece a(Class <? extends WorldGenStrongholdPieces.WorldGenStrongholdPiece > p_a_0_, List<StructurePiece> p_a_1_, Random p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, EnumDirection p_a_6_, int p_a_7_)
    {
        Object object = null;

        if (p_a_0_ == WorldGenStrongholdPieces.WorldGenStrongholdStairs.class)
        {
            object = WorldGenStrongholdPieces.WorldGenStrongholdStairs.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_);
        }
        else if (p_a_0_ == WorldGenStrongholdPieces.WorldGenStrongholdPrison.class)
        {
            object = WorldGenStrongholdPieces.WorldGenStrongholdPrison.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_);
        }
        else if (p_a_0_ == WorldGenStrongholdPieces.WorldGenStrongholdLeftTurn.class)
        {
            object = WorldGenStrongholdPieces.WorldGenStrongholdLeftTurn.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_);
        }
        else if (p_a_0_ == WorldGenStrongholdPieces.WorldGenStrongholdRightTurn.class)
        {
            object = WorldGenStrongholdPieces.WorldGenStrongholdRightTurn.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_);
        }
        else if (p_a_0_ == WorldGenStrongholdPieces.WorldGenStrongholdRoomCrossing.class)
        {
            object = WorldGenStrongholdPieces.WorldGenStrongholdRoomCrossing.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_);
        }
        else if (p_a_0_ == WorldGenStrongholdPieces.WorldGenStrongholdStairsStraight.class)
        {
            object = WorldGenStrongholdPieces.WorldGenStrongholdStairsStraight.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_);
        }
        else if (p_a_0_ == WorldGenStrongholdPieces.WorldGenStrongholdStairs2.class)
        {
            object = WorldGenStrongholdPieces.WorldGenStrongholdStairs2.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_);
        }
        else if (p_a_0_ == WorldGenStrongholdPieces.WorldGenStrongholdCrossing.class)
        {
            object = WorldGenStrongholdPieces.WorldGenStrongholdCrossing.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_);
        }
        else if (p_a_0_ == WorldGenStrongholdPieces.WorldGenStrongholdChestCorridor.class)
        {
            object = WorldGenStrongholdPieces.WorldGenStrongholdChestCorridor.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_);
        }
        else if (p_a_0_ == WorldGenStrongholdPieces.WorldGenStrongholdLibrary.class)
        {
            object = WorldGenStrongholdPieces.WorldGenStrongholdLibrary.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_);
        }
        else if (p_a_0_ == WorldGenStrongholdPieces.WorldGenStrongholdPortalRoom.class)
        {
            object = WorldGenStrongholdPieces.WorldGenStrongholdPortalRoom.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_);
        }

        return (WorldGenStrongholdPieces.WorldGenStrongholdPiece)object;
    }

    private static WorldGenStrongholdPieces.WorldGenStrongholdPiece b(WorldGenStrongholdPieces.WorldGenStrongholdStart p_b_0_, List<StructurePiece> p_b_1_, Random p_b_2_, int p_b_3_, int p_b_4_, int p_b_5_, EnumDirection p_b_6_, int p_b_7_)
    {
        if (!d())
        {
            return null;
        }
        else
        {
            if (d != null)
            {
                WorldGenStrongholdPieces.WorldGenStrongholdPiece worldgenstrongholdpieces$worldgenstrongholdpiece = a(d, p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_6_, p_b_7_);
                d = null;

                if (worldgenstrongholdpieces$worldgenstrongholdpiece != null)
                {
                    return worldgenstrongholdpieces$worldgenstrongholdpiece;
                }
            }

            int j = 0;

            while (j < 5)
            {
                ++j;
                int i = p_b_2_.nextInt(a);

                for (WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight worldgenstrongholdpieces$worldgenstrongholdpieceweight : c)
                {
                    i -= worldgenstrongholdpieces$worldgenstrongholdpieceweight.b;

                    if (i < 0)
                    {
                        if (!worldgenstrongholdpieces$worldgenstrongholdpieceweight.a(p_b_7_) || worldgenstrongholdpieces$worldgenstrongholdpieceweight == p_b_0_.a)
                        {
                            break;
                        }

                        WorldGenStrongholdPieces.WorldGenStrongholdPiece worldgenstrongholdpieces$worldgenstrongholdpiece1 = a(worldgenstrongholdpieces$worldgenstrongholdpieceweight.a, p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_6_, p_b_7_);

                        if (worldgenstrongholdpieces$worldgenstrongholdpiece1 != null)
                        {
                            ++worldgenstrongholdpieces$worldgenstrongholdpieceweight.c;
                            p_b_0_.a = worldgenstrongholdpieces$worldgenstrongholdpieceweight;

                            if (!worldgenstrongholdpieces$worldgenstrongholdpieceweight.a())
                            {
                                c.remove(worldgenstrongholdpieces$worldgenstrongholdpieceweight);
                            }

                            return worldgenstrongholdpieces$worldgenstrongholdpiece1;
                        }
                    }
                }
            }

            StructureBoundingBox structureboundingbox = WorldGenStrongholdPieces.WorldGenStrongholdCorridor.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_6_);

            if (structureboundingbox != null && structureboundingbox.b > 1)
            {
                return new WorldGenStrongholdPieces.WorldGenStrongholdCorridor(p_b_7_, p_b_2_, structureboundingbox, p_b_6_);
            }
            else
            {
                return null;
            }
        }
    }

    private static StructurePiece c(WorldGenStrongholdPieces.WorldGenStrongholdStart p_c_0_, List<StructurePiece> p_c_1_, Random p_c_2_, int p_c_3_, int p_c_4_, int p_c_5_, EnumDirection p_c_6_, int p_c_7_)
    {
        if (p_c_7_ > 50)
        {
            return null;
        }
        else if (Math.abs(p_c_3_ - p_c_0_.c().a) <= 112 && Math.abs(p_c_5_ - p_c_0_.c().c) <= 112)
        {
            WorldGenStrongholdPieces.WorldGenStrongholdPiece worldgenstrongholdpieces$worldgenstrongholdpiece = b(p_c_0_, p_c_1_, p_c_2_, p_c_3_, p_c_4_, p_c_5_, p_c_6_, p_c_7_ + 1);

            if (worldgenstrongholdpieces$worldgenstrongholdpiece != null)
            {
                p_c_1_.add(worldgenstrongholdpieces$worldgenstrongholdpiece);
                p_c_0_.c.add(worldgenstrongholdpieces$worldgenstrongholdpiece);
            }

            return worldgenstrongholdpieces$worldgenstrongholdpiece;
        }
        else
        {
            return null;
        }
    }

    public static class WorldGenStrongholdChestCorridor extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
    {
        private static final List<StructurePieceTreasure> a = Lists.newArrayList(new StructurePieceTreasure[] {new StructurePieceTreasure(Items.ENDER_PEARL, 0, 1, 1, 10), new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 3), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 1, 3, 5), new StructurePieceTreasure(Items.REDSTONE, 0, 4, 9, 5), new StructurePieceTreasure(Items.BREAD, 0, 1, 3, 15), new StructurePieceTreasure(Items.APPLE, 0, 1, 3, 15), new StructurePieceTreasure(Items.IRON_PICKAXE, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_SWORD, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_CHESTPLATE, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_HELMET, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_LEGGINGS, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_BOOTS, 0, 1, 1, 5), new StructurePieceTreasure(Items.GOLDEN_APPLE, 0, 1, 1, 1), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 1), new StructurePieceTreasure(Items.IRON_HORSE_ARMOR, 0, 1, 1, 1), new StructurePieceTreasure(Items.GOLDEN_HORSE_ARMOR, 0, 1, 1, 1), new StructurePieceTreasure(Items.DIAMOND_HORSE_ARMOR, 0, 1, 1, 1)});
        private boolean b;

        public WorldGenStrongholdChestCorridor()
        {
        }

        public WorldGenStrongholdChestCorridor(int p_i778_1_, Random p_i778_2_, StructureBoundingBox p_i778_3_, EnumDirection p_i778_4_)
        {
            super(p_i778_1_);
            this.m = p_i778_4_;
            this.d = this.a(p_i778_2_);
            this.l = p_i778_3_;
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            p_a_1_.setBoolean("Chest", this.b);
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
            this.b = p_b_1_.getBoolean("Chest");
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            this.a((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, 1, 1);
        }

        public static WorldGenStrongholdPieces.WorldGenStrongholdChestCorridor a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -1, -1, 0, 5, 5, 7, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenStrongholdPieces.WorldGenStrongholdChestCorridor(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (this.a(p_a_1_, p_a_3_))
            {
                return false;
            }
            else
            {
                this.a(p_a_1_, p_a_3_, 0, 0, 0, 4, 4, 6, true, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_2_, p_a_3_, this.d, 1, 1, 0);
                this.a(p_a_1_, p_a_2_, p_a_3_, WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.OPENING, 1, 1, 6);
                this.a(p_a_1_, p_a_3_, 3, 1, 2, 3, 1, 4, Blocks.STONEBRICK.getBlockData(), Blocks.STONEBRICK.getBlockData(), false);
                this.a(p_a_1_, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.SMOOTHBRICK.a()), 3, 1, 1, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.SMOOTHBRICK.a()), 3, 1, 5, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.SMOOTHBRICK.a()), 3, 2, 2, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.SMOOTHBRICK.a()), 3, 2, 4, p_a_3_);

                for (int i = 2; i <= 4; ++i)
                {
                    this.a(p_a_1_, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.SMOOTHBRICK.a()), 2, 1, i, p_a_3_);
                }

                if (!this.b && p_a_3_.b((BaseBlockPosition)(new BlockPosition(this.a(3, 3), this.d(2), this.b(3, 3)))))
                {
                    this.b = true;
                    this.a(p_a_1_, p_a_3_, p_a_2_, 3, 2, 3, StructurePieceTreasure.a(a, new StructurePieceTreasure[] {Items.ENCHANTED_BOOK.b(p_a_2_)}), 2 + p_a_2_.nextInt(2));
                }

                return true;
            }
        }
    }

    public static class WorldGenStrongholdCorridor extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
    {
        private int a;

        public WorldGenStrongholdCorridor()
        {
        }

        public WorldGenStrongholdCorridor(int p_i779_1_, Random p_i779_2_, StructureBoundingBox p_i779_3_, EnumDirection p_i779_4_)
        {
            super(p_i779_1_);
            this.m = p_i779_4_;
            this.l = p_i779_3_;
            this.a = p_i779_4_ != EnumDirection.NORTH && p_i779_4_ != EnumDirection.SOUTH ? p_i779_3_.c() : p_i779_3_.e();
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            p_a_1_.setInt("Steps", this.a);
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
            this.a = p_b_1_.getInt("Steps");
        }

        public static StructureBoundingBox a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_)
        {
            boolean flag = true;
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -1, -1, 0, 5, 5, 4, p_a_5_);
            StructurePiece structurepiece = StructurePiece.a(p_a_0_, structureboundingbox);

            if (structurepiece == null)
            {
                return null;
            }
            else
            {
                if (structurepiece.c().b == structureboundingbox.b)
                {
                    for (int i = 3; i >= 1; --i)
                    {
                        structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -1, -1, 0, 5, 5, i - 1, p_a_5_);

                        if (!structurepiece.c().a(structureboundingbox))
                        {
                            return StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -1, -1, 0, 5, 5, i, p_a_5_);
                        }
                    }
                }

                return null;
            }
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (this.a(p_a_1_, p_a_3_))
            {
                return false;
            }
            else
            {
                for (int i = 0; i < this.a; ++i)
                {
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 0, 0, i, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 1, 0, i, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 2, 0, i, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 3, 0, i, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 4, 0, i, p_a_3_);

                    for (int j = 1; j <= 3; ++j)
                    {
                        this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 0, j, i, p_a_3_);
                        this.a(p_a_1_, Blocks.AIR.getBlockData(), 1, j, i, p_a_3_);
                        this.a(p_a_1_, Blocks.AIR.getBlockData(), 2, j, i, p_a_3_);
                        this.a(p_a_1_, Blocks.AIR.getBlockData(), 3, j, i, p_a_3_);
                        this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 4, j, i, p_a_3_);
                    }

                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 0, 4, i, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 1, 4, i, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 2, 4, i, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 3, 4, i, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 4, 4, i, p_a_3_);
                }

                return true;
            }
        }
    }

    public static class WorldGenStrongholdCrossing extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
    {
        private boolean a;
        private boolean b;
        private boolean c;
        private boolean e;

        public WorldGenStrongholdCrossing()
        {
        }

        public WorldGenStrongholdCrossing(int p_i780_1_, Random p_i780_2_, StructureBoundingBox p_i780_3_, EnumDirection p_i780_4_)
        {
            super(p_i780_1_);
            this.m = p_i780_4_;
            this.d = this.a(p_i780_2_);
            this.l = p_i780_3_;
            this.a = p_i780_2_.nextBoolean();
            this.b = p_i780_2_.nextBoolean();
            this.c = p_i780_2_.nextBoolean();
            this.e = p_i780_2_.nextInt(3) > 0;
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            p_a_1_.setBoolean("leftLow", this.a);
            p_a_1_.setBoolean("leftHigh", this.b);
            p_a_1_.setBoolean("rightLow", this.c);
            p_a_1_.setBoolean("rightHigh", this.e);
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
            this.a = p_b_1_.getBoolean("leftLow");
            this.b = p_b_1_.getBoolean("leftHigh");
            this.c = p_b_1_.getBoolean("rightLow");
            this.e = p_b_1_.getBoolean("rightHigh");
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            int i = 3;
            int j = 5;

            if (this.m == EnumDirection.WEST || this.m == EnumDirection.NORTH)
            {
                i = 8 - i;
                j = 8 - j;
            }

            this.a((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, 5, 1);

            if (this.a)
            {
                this.b((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, i, 1);
            }

            if (this.b)
            {
                this.b((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, j, 7);
            }

            if (this.c)
            {
                this.c((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, i, 1);
            }

            if (this.e)
            {
                this.c((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, j, 7);
            }
        }

        public static WorldGenStrongholdPieces.WorldGenStrongholdCrossing a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -4, -3, 0, 10, 9, 11, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenStrongholdPieces.WorldGenStrongholdCrossing(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (this.a(p_a_1_, p_a_3_))
            {
                return false;
            }
            else
            {
                this.a(p_a_1_, p_a_3_, 0, 0, 0, 9, 8, 10, true, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_2_, p_a_3_, this.d, 4, 3, 0);

                if (this.a)
                {
                    this.a(p_a_1_, p_a_3_, 0, 3, 1, 0, 5, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                }

                if (this.c)
                {
                    this.a(p_a_1_, p_a_3_, 9, 3, 1, 9, 5, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                }

                if (this.b)
                {
                    this.a(p_a_1_, p_a_3_, 0, 5, 7, 0, 7, 9, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                }

                if (this.e)
                {
                    this.a(p_a_1_, p_a_3_, 9, 5, 7, 9, 7, 9, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                }

                this.a(p_a_1_, p_a_3_, 5, 1, 10, 7, 3, 10, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 1, 2, 1, 8, 2, 6, false, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_3_, 4, 1, 5, 4, 4, 9, false, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_3_, 8, 1, 5, 8, 4, 9, false, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_3_, 1, 4, 7, 3, 4, 9, false, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_3_, 1, 3, 5, 3, 3, 6, false, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_3_, 1, 3, 4, 3, 3, 4, Blocks.STONE_SLAB.getBlockData(), Blocks.STONE_SLAB.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 1, 4, 6, 3, 4, 6, Blocks.STONE_SLAB.getBlockData(), Blocks.STONE_SLAB.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 5, 1, 7, 7, 1, 8, false, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_3_, 5, 1, 9, 7, 1, 9, Blocks.STONE_SLAB.getBlockData(), Blocks.STONE_SLAB.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 5, 2, 7, 7, 2, 7, Blocks.STONE_SLAB.getBlockData(), Blocks.STONE_SLAB.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 4, 5, 7, 4, 5, 9, Blocks.STONE_SLAB.getBlockData(), Blocks.STONE_SLAB.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 8, 5, 7, 8, 5, 9, Blocks.STONE_SLAB.getBlockData(), Blocks.STONE_SLAB.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 5, 5, 7, 7, 5, 9, Blocks.DOUBLE_STONE_SLAB.getBlockData(), Blocks.DOUBLE_STONE_SLAB.getBlockData(), false);
                this.a(p_a_1_, Blocks.TORCH.getBlockData(), 6, 5, 6, p_a_3_);
                return true;
            }
        }
    }

    public static class WorldGenStrongholdLeftTurn extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
    {
        public WorldGenStrongholdLeftTurn()
        {
        }

        public WorldGenStrongholdLeftTurn(int p_i781_1_, Random p_i781_2_, StructureBoundingBox p_i781_3_, EnumDirection p_i781_4_)
        {
            super(p_i781_1_);
            this.m = p_i781_4_;
            this.d = this.a(p_i781_2_);
            this.l = p_i781_3_;
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            if (this.m != EnumDirection.NORTH && this.m != EnumDirection.EAST)
            {
                this.c((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, 1, 1);
            }
            else
            {
                this.b((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, 1, 1);
            }
        }

        public static WorldGenStrongholdPieces.WorldGenStrongholdLeftTurn a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -1, -1, 0, 5, 5, 5, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenStrongholdPieces.WorldGenStrongholdLeftTurn(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (this.a(p_a_1_, p_a_3_))
            {
                return false;
            }
            else
            {
                this.a(p_a_1_, p_a_3_, 0, 0, 0, 4, 4, 4, true, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_2_, p_a_3_, this.d, 1, 1, 0);

                if (this.m != EnumDirection.NORTH && this.m != EnumDirection.EAST)
                {
                    this.a(p_a_1_, p_a_3_, 4, 1, 1, 4, 3, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                }
                else
                {
                    this.a(p_a_1_, p_a_3_, 0, 1, 1, 0, 3, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                }

                return true;
            }
        }
    }

    public static class WorldGenStrongholdLibrary extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
    {
        private static final List<StructurePieceTreasure> a = Lists.newArrayList(new StructurePieceTreasure[] {new StructurePieceTreasure(Items.BOOK, 0, 1, 3, 20), new StructurePieceTreasure(Items.PAPER, 0, 2, 7, 20), new StructurePieceTreasure(Items.MAP, 0, 1, 1, 1), new StructurePieceTreasure(Items.COMPASS, 0, 1, 1, 1)});
        private boolean b;

        public WorldGenStrongholdLibrary()
        {
        }

        public WorldGenStrongholdLibrary(int p_i782_1_, Random p_i782_2_, StructureBoundingBox p_i782_3_, EnumDirection p_i782_4_)
        {
            super(p_i782_1_);
            this.m = p_i782_4_;
            this.d = this.a(p_i782_2_);
            this.l = p_i782_3_;
            this.b = p_i782_3_.d() > 6;
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            p_a_1_.setBoolean("Tall", this.b);
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
            this.b = p_b_1_.getBoolean("Tall");
        }

        public static WorldGenStrongholdPieces.WorldGenStrongholdLibrary a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -4, -1, 0, 14, 11, 15, p_a_5_);

            if (!a(structureboundingbox) || StructurePiece.a(p_a_0_, structureboundingbox) != null)
            {
                structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -4, -1, 0, 14, 6, 15, p_a_5_);

                if (!a(structureboundingbox) || StructurePiece.a(p_a_0_, structureboundingbox) != null)
                {
                    return null;
                }
            }

            return new WorldGenStrongholdPieces.WorldGenStrongholdLibrary(p_a_6_, p_a_1_, structureboundingbox, p_a_5_);
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (this.a(p_a_1_, p_a_3_))
            {
                return false;
            }
            else
            {
                byte b0 = 11;

                if (!this.b)
                {
                    b0 = 6;
                }

                this.a(p_a_1_, p_a_3_, 0, 0, 0, 13, b0 - 1, 14, true, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_2_, p_a_3_, this.d, 4, 1, 0);
                this.a(p_a_1_, p_a_3_, p_a_2_, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.WEB.getBlockData(), Blocks.WEB.getBlockData(), false);
                boolean flag = true;
                boolean flag1 = true;

                for (int i = 1; i <= 13; ++i)
                {
                    if ((i - 1) % 4 == 0)
                    {
                        this.a(p_a_1_, p_a_3_, 1, 1, i, 1, 4, i, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
                        this.a(p_a_1_, p_a_3_, 12, 1, i, 12, 4, i, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
                        this.a(p_a_1_, Blocks.TORCH.getBlockData(), 2, 3, i, p_a_3_);
                        this.a(p_a_1_, Blocks.TORCH.getBlockData(), 11, 3, i, p_a_3_);

                        if (this.b)
                        {
                            this.a(p_a_1_, p_a_3_, 1, 6, i, 1, 9, i, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
                            this.a(p_a_1_, p_a_3_, 12, 6, i, 12, 9, i, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
                        }
                    }
                    else
                    {
                        this.a(p_a_1_, p_a_3_, 1, 1, i, 1, 4, i, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);
                        this.a(p_a_1_, p_a_3_, 12, 1, i, 12, 4, i, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);

                        if (this.b)
                        {
                            this.a(p_a_1_, p_a_3_, 1, 6, i, 1, 9, i, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);
                            this.a(p_a_1_, p_a_3_, 12, 6, i, 12, 9, i, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);
                        }
                    }
                }

                for (int j = 3; j < 12; j += 2)
                {
                    this.a(p_a_1_, p_a_3_, 3, 1, j, 4, 3, j, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);
                    this.a(p_a_1_, p_a_3_, 6, 1, j, 7, 3, j, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);
                    this.a(p_a_1_, p_a_3_, 9, 1, j, 10, 3, j, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);
                }

                if (this.b)
                {
                    this.a(p_a_1_, p_a_3_, 1, 5, 1, 3, 5, 13, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
                    this.a(p_a_1_, p_a_3_, 10, 5, 1, 12, 5, 13, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
                    this.a(p_a_1_, p_a_3_, 4, 5, 1, 9, 5, 2, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
                    this.a(p_a_1_, p_a_3_, 4, 5, 12, 9, 5, 13, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
                    this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 9, 5, 11, p_a_3_);
                    this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 8, 5, 11, p_a_3_);
                    this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 9, 5, 10, p_a_3_);
                    this.a(p_a_1_, p_a_3_, 3, 6, 2, 3, 6, 12, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
                    this.a(p_a_1_, p_a_3_, 10, 6, 2, 10, 6, 10, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
                    this.a(p_a_1_, p_a_3_, 4, 6, 2, 9, 6, 2, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
                    this.a(p_a_1_, p_a_3_, 4, 6, 12, 8, 6, 12, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
                    this.a(p_a_1_, Blocks.FENCE.getBlockData(), 9, 6, 11, p_a_3_);
                    this.a(p_a_1_, Blocks.FENCE.getBlockData(), 8, 6, 11, p_a_3_);
                    this.a(p_a_1_, Blocks.FENCE.getBlockData(), 9, 6, 10, p_a_3_);
                    int k = this.a(Blocks.LADDER, 3);
                    this.a(p_a_1_, Blocks.LADDER.fromLegacyData(k), 10, 1, 13, p_a_3_);
                    this.a(p_a_1_, Blocks.LADDER.fromLegacyData(k), 10, 2, 13, p_a_3_);
                    this.a(p_a_1_, Blocks.LADDER.fromLegacyData(k), 10, 3, 13, p_a_3_);
                    this.a(p_a_1_, Blocks.LADDER.fromLegacyData(k), 10, 4, 13, p_a_3_);
                    this.a(p_a_1_, Blocks.LADDER.fromLegacyData(k), 10, 5, 13, p_a_3_);
                    this.a(p_a_1_, Blocks.LADDER.fromLegacyData(k), 10, 6, 13, p_a_3_);
                    this.a(p_a_1_, Blocks.LADDER.fromLegacyData(k), 10, 7, 13, p_a_3_);
                    byte b1 = 7;
                    byte b2 = 7;
                    this.a(p_a_1_, Blocks.FENCE.getBlockData(), b1 - 1, 9, b2, p_a_3_);
                    this.a(p_a_1_, Blocks.FENCE.getBlockData(), b1, 9, b2, p_a_3_);
                    this.a(p_a_1_, Blocks.FENCE.getBlockData(), b1 - 1, 8, b2, p_a_3_);
                    this.a(p_a_1_, Blocks.FENCE.getBlockData(), b1, 8, b2, p_a_3_);
                    this.a(p_a_1_, Blocks.FENCE.getBlockData(), b1 - 1, 7, b2, p_a_3_);
                    this.a(p_a_1_, Blocks.FENCE.getBlockData(), b1, 7, b2, p_a_3_);
                    this.a(p_a_1_, Blocks.FENCE.getBlockData(), b1 - 2, 7, b2, p_a_3_);
                    this.a(p_a_1_, Blocks.FENCE.getBlockData(), b1 + 1, 7, b2, p_a_3_);
                    this.a(p_a_1_, Blocks.FENCE.getBlockData(), b1 - 1, 7, b2 - 1, p_a_3_);
                    this.a(p_a_1_, Blocks.FENCE.getBlockData(), b1 - 1, 7, b2 + 1, p_a_3_);
                    this.a(p_a_1_, Blocks.FENCE.getBlockData(), b1, 7, b2 - 1, p_a_3_);
                    this.a(p_a_1_, Blocks.FENCE.getBlockData(), b1, 7, b2 + 1, p_a_3_);
                    this.a(p_a_1_, Blocks.TORCH.getBlockData(), b1 - 2, 8, b2, p_a_3_);
                    this.a(p_a_1_, Blocks.TORCH.getBlockData(), b1 + 1, 8, b2, p_a_3_);
                    this.a(p_a_1_, Blocks.TORCH.getBlockData(), b1 - 1, 8, b2 - 1, p_a_3_);
                    this.a(p_a_1_, Blocks.TORCH.getBlockData(), b1 - 1, 8, b2 + 1, p_a_3_);
                    this.a(p_a_1_, Blocks.TORCH.getBlockData(), b1, 8, b2 - 1, p_a_3_);
                    this.a(p_a_1_, Blocks.TORCH.getBlockData(), b1, 8, b2 + 1, p_a_3_);
                }

                this.a(p_a_1_, p_a_3_, p_a_2_, 3, 3, 5, StructurePieceTreasure.a(a, new StructurePieceTreasure[] {Items.ENCHANTED_BOOK.a(p_a_2_, 1, 5, 2)}), 1 + p_a_2_.nextInt(4));

                if (this.b)
                {
                    this.a(p_a_1_, Blocks.AIR.getBlockData(), 12, 9, 1, p_a_3_);
                    this.a(p_a_1_, p_a_3_, p_a_2_, 12, 8, 1, StructurePieceTreasure.a(a, new StructurePieceTreasure[] {Items.ENCHANTED_BOOK.a(p_a_2_, 1, 5, 2)}), 1 + p_a_2_.nextInt(4));
                }

                return true;
            }
        }
    }

    abstract static class WorldGenStrongholdPiece extends StructurePiece
    {
        protected WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType d = WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.OPENING;

        public WorldGenStrongholdPiece()
        {
        }

        protected WorldGenStrongholdPiece(int p_i794_1_)
        {
            super(p_i794_1_);
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            p_a_1_.setString("EntryDoor", this.d.name());
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            this.d = WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.valueOf(p_b_1_.getString("EntryDoor"));
        }

        protected void a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_, WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_)
        {
            switch (p_a_4_)
            {
                case OPENING:
                default:
                    this.a(p_a_1_, p_a_3_, p_a_5_, p_a_6_, p_a_7_, p_a_5_ + 3 - 1, p_a_6_ + 3 - 1, p_a_7_, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                    break;

                case WOOD_DOOR:
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), p_a_5_, p_a_6_, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), p_a_5_, p_a_6_ + 1, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), p_a_5_, p_a_6_ + 2, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), p_a_5_ + 1, p_a_6_ + 2, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), p_a_5_ + 2, p_a_6_ + 2, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), p_a_5_ + 2, p_a_6_ + 1, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), p_a_5_ + 2, p_a_6_, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.WOODEN_DOOR.getBlockData(), p_a_5_ + 1, p_a_6_, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.WOODEN_DOOR.fromLegacyData(8), p_a_5_ + 1, p_a_6_ + 1, p_a_7_, p_a_3_);
                    break;

                case GRATES:
                    this.a(p_a_1_, Blocks.AIR.getBlockData(), p_a_5_ + 1, p_a_6_, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.AIR.getBlockData(), p_a_5_ + 1, p_a_6_ + 1, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.IRON_BARS.getBlockData(), p_a_5_, p_a_6_, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.IRON_BARS.getBlockData(), p_a_5_, p_a_6_ + 1, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.IRON_BARS.getBlockData(), p_a_5_, p_a_6_ + 2, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.IRON_BARS.getBlockData(), p_a_5_ + 1, p_a_6_ + 2, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.IRON_BARS.getBlockData(), p_a_5_ + 2, p_a_6_ + 2, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.IRON_BARS.getBlockData(), p_a_5_ + 2, p_a_6_ + 1, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.IRON_BARS.getBlockData(), p_a_5_ + 2, p_a_6_, p_a_7_, p_a_3_);
                    break;

                case IRON_DOOR:
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), p_a_5_, p_a_6_, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), p_a_5_, p_a_6_ + 1, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), p_a_5_, p_a_6_ + 2, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), p_a_5_ + 1, p_a_6_ + 2, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), p_a_5_ + 2, p_a_6_ + 2, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), p_a_5_ + 2, p_a_6_ + 1, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), p_a_5_ + 2, p_a_6_, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.IRON_DOOR.getBlockData(), p_a_5_ + 1, p_a_6_, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.IRON_DOOR.fromLegacyData(8), p_a_5_ + 1, p_a_6_ + 1, p_a_7_, p_a_3_);
                    this.a(p_a_1_, Blocks.STONE_BUTTON.fromLegacyData(this.a(Blocks.STONE_BUTTON, 4)), p_a_5_ + 2, p_a_6_ + 1, p_a_7_ + 1, p_a_3_);
                    this.a(p_a_1_, Blocks.STONE_BUTTON.fromLegacyData(this.a(Blocks.STONE_BUTTON, 3)), p_a_5_ + 2, p_a_6_ + 1, p_a_7_ - 1, p_a_3_);
            }
        }

        protected WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType a(Random p_a_1_)
        {
            int i = p_a_1_.nextInt(5);

            switch (i)
            {
                case 0:
                case 1:
                default:
                    return WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.OPENING;

                case 2:
                    return WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.WOOD_DOOR;

                case 3:
                    return WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.GRATES;

                case 4:
                    return WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.IRON_DOOR;
            }
        }

        protected StructurePiece a(WorldGenStrongholdPieces.WorldGenStrongholdStart p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_, int p_a_4_, int p_a_5_)
        {
            if (this.m != null)
            {
                switch (this.m)
                {
                    case NORTH:
                        return WorldGenStrongholdPieces.c(p_a_1_, p_a_2_, p_a_3_, this.l.a + p_a_4_, this.l.b + p_a_5_, this.l.c - 1, this.m, this.d());

                    case SOUTH:
                        return WorldGenStrongholdPieces.c(p_a_1_, p_a_2_, p_a_3_, this.l.a + p_a_4_, this.l.b + p_a_5_, this.l.f + 1, this.m, this.d());

                    case WEST:
                        return WorldGenStrongholdPieces.c(p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.b + p_a_5_, this.l.c + p_a_4_, this.m, this.d());

                    case EAST:
                        return WorldGenStrongholdPieces.c(p_a_1_, p_a_2_, p_a_3_, this.l.d + 1, this.l.b + p_a_5_, this.l.c + p_a_4_, this.m, this.d());
                }
            }

            return null;
        }

        protected StructurePiece b(WorldGenStrongholdPieces.WorldGenStrongholdStart p_b_1_, List<StructurePiece> p_b_2_, Random p_b_3_, int p_b_4_, int p_b_5_)
        {
            if (this.m != null)
            {
                switch (this.m)
                {
                    case NORTH:
                        return WorldGenStrongholdPieces.c(p_b_1_, p_b_2_, p_b_3_, this.l.a - 1, this.l.b + p_b_4_, this.l.c + p_b_5_, EnumDirection.WEST, this.d());

                    case SOUTH:
                        return WorldGenStrongholdPieces.c(p_b_1_, p_b_2_, p_b_3_, this.l.a - 1, this.l.b + p_b_4_, this.l.c + p_b_5_, EnumDirection.WEST, this.d());

                    case WEST:
                        return WorldGenStrongholdPieces.c(p_b_1_, p_b_2_, p_b_3_, this.l.a + p_b_5_, this.l.b + p_b_4_, this.l.c - 1, EnumDirection.NORTH, this.d());

                    case EAST:
                        return WorldGenStrongholdPieces.c(p_b_1_, p_b_2_, p_b_3_, this.l.a + p_b_5_, this.l.b + p_b_4_, this.l.c - 1, EnumDirection.NORTH, this.d());
                }
            }

            return null;
        }

        protected StructurePiece c(WorldGenStrongholdPieces.WorldGenStrongholdStart p_c_1_, List<StructurePiece> p_c_2_, Random p_c_3_, int p_c_4_, int p_c_5_)
        {
            if (this.m != null)
            {
                switch (this.m)
                {
                    case NORTH:
                        return WorldGenStrongholdPieces.c(p_c_1_, p_c_2_, p_c_3_, this.l.d + 1, this.l.b + p_c_4_, this.l.c + p_c_5_, EnumDirection.EAST, this.d());

                    case SOUTH:
                        return WorldGenStrongholdPieces.c(p_c_1_, p_c_2_, p_c_3_, this.l.d + 1, this.l.b + p_c_4_, this.l.c + p_c_5_, EnumDirection.EAST, this.d());

                    case WEST:
                        return WorldGenStrongholdPieces.c(p_c_1_, p_c_2_, p_c_3_, this.l.a + p_c_5_, this.l.b + p_c_4_, this.l.f + 1, EnumDirection.SOUTH, this.d());

                    case EAST:
                        return WorldGenStrongholdPieces.c(p_c_1_, p_c_2_, p_c_3_, this.l.a + p_c_5_, this.l.b + p_c_4_, this.l.f + 1, EnumDirection.SOUTH, this.d());
                }
            }

            return null;
        }

        protected static boolean a(StructureBoundingBox p_a_0_)
        {
            return p_a_0_ != null && p_a_0_.b > 10;
        }

        public static enum WorldGenStrongholdDoorType
        {
            OPENING,
            WOOD_DOOR,
            GRATES,
            IRON_DOOR;
        }
    }

    static class WorldGenStrongholdPieceWeight
    {
        public Class <? extends WorldGenStrongholdPieces.WorldGenStrongholdPiece > a;
        public final int b;
        public int c;
        public int d;

        public WorldGenStrongholdPieceWeight(Class <? extends WorldGenStrongholdPieces.WorldGenStrongholdPiece > p_i783_1_, int p_i783_2_, int p_i783_3_)
        {
            this.a = p_i783_1_;
            this.b = p_i783_2_;
            this.d = p_i783_3_;
        }

        public boolean a(int p_a_1_)
        {
            return this.d == 0 || this.c < this.d;
        }

        public boolean a()
        {
            return this.d == 0 || this.c < this.d;
        }
    }

    public static class WorldGenStrongholdPortalRoom extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
    {
        private boolean a;

        public WorldGenStrongholdPortalRoom()
        {
        }

        public WorldGenStrongholdPortalRoom(int p_i784_1_, Random p_i784_2_, StructureBoundingBox p_i784_3_, EnumDirection p_i784_4_)
        {
            super(p_i784_1_);
            this.m = p_i784_4_;
            this.l = p_i784_3_;
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            p_a_1_.setBoolean("Mob", this.a);
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
            this.a = p_b_1_.getBoolean("Mob");
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            if (p_a_1_ != null)
            {
                ((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_).b = this;
            }
        }

        public static WorldGenStrongholdPieces.WorldGenStrongholdPortalRoom a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -4, -1, 0, 11, 8, 16, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenStrongholdPieces.WorldGenStrongholdPortalRoom(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 0, 0, 0, 10, 7, 15, false, p_a_2_, WorldGenStrongholdPieces.e);
            this.a(p_a_1_, p_a_2_, p_a_3_, WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.GRATES, 4, 1, 0);
            int i = 6;
            this.a(p_a_1_, p_a_3_, 1, i, 1, 1, i, 14, false, p_a_2_, WorldGenStrongholdPieces.e);
            this.a(p_a_1_, p_a_3_, 9, i, 1, 9, i, 14, false, p_a_2_, WorldGenStrongholdPieces.e);
            this.a(p_a_1_, p_a_3_, 2, i, 1, 8, i, 2, false, p_a_2_, WorldGenStrongholdPieces.e);
            this.a(p_a_1_, p_a_3_, 2, i, 14, 8, i, 14, false, p_a_2_, WorldGenStrongholdPieces.e);
            this.a(p_a_1_, p_a_3_, 1, 1, 1, 2, 1, 4, false, p_a_2_, WorldGenStrongholdPieces.e);
            this.a(p_a_1_, p_a_3_, 8, 1, 1, 9, 1, 4, false, p_a_2_, WorldGenStrongholdPieces.e);
            this.a(p_a_1_, p_a_3_, 1, 1, 1, 1, 1, 3, Blocks.FLOWING_LAVA.getBlockData(), Blocks.FLOWING_LAVA.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 9, 1, 1, 9, 1, 3, Blocks.FLOWING_LAVA.getBlockData(), Blocks.FLOWING_LAVA.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 3, 1, 8, 7, 1, 12, false, p_a_2_, WorldGenStrongholdPieces.e);
            this.a(p_a_1_, p_a_3_, 4, 1, 9, 6, 1, 11, Blocks.FLOWING_LAVA.getBlockData(), Blocks.FLOWING_LAVA.getBlockData(), false);

            for (int j = 3; j < 14; j += 2)
            {
                this.a(p_a_1_, p_a_3_, 0, 3, j, 0, 4, j, Blocks.IRON_BARS.getBlockData(), Blocks.IRON_BARS.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 10, 3, j, 10, 4, j, Blocks.IRON_BARS.getBlockData(), Blocks.IRON_BARS.getBlockData(), false);
            }

            for (int k1 = 2; k1 < 9; k1 += 2)
            {
                this.a(p_a_1_, p_a_3_, k1, 3, 15, k1, 4, 15, Blocks.IRON_BARS.getBlockData(), Blocks.IRON_BARS.getBlockData(), false);
            }

            int l1 = this.a(Blocks.STONE_BRICK_STAIRS, 3);
            this.a(p_a_1_, p_a_3_, 4, 1, 5, 6, 1, 7, false, p_a_2_, WorldGenStrongholdPieces.e);
            this.a(p_a_1_, p_a_3_, 4, 2, 6, 6, 2, 7, false, p_a_2_, WorldGenStrongholdPieces.e);
            this.a(p_a_1_, p_a_3_, 4, 3, 7, 6, 3, 7, false, p_a_2_, WorldGenStrongholdPieces.e);

            for (int k = 4; k <= 6; ++k)
            {
                this.a(p_a_1_, Blocks.STONE_BRICK_STAIRS.fromLegacyData(l1), k, 1, 4, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_BRICK_STAIRS.fromLegacyData(l1), k, 2, 5, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_BRICK_STAIRS.fromLegacyData(l1), k, 3, 6, p_a_3_);
            }

            int i2 = EnumDirection.NORTH.b();
            int l = EnumDirection.SOUTH.b();
            int i1 = EnumDirection.EAST.b();
            int j1 = EnumDirection.WEST.b();

            if (this.m != null)
            {
                switch (this.m)
                {
                    case SOUTH:
                        i2 = EnumDirection.SOUTH.b();
                        l = EnumDirection.NORTH.b();
                        break;

                    case WEST:
                        i2 = EnumDirection.WEST.b();
                        l = EnumDirection.EAST.b();
                        i1 = EnumDirection.SOUTH.b();
                        j1 = EnumDirection.NORTH.b();
                        break;

                    case EAST:
                        i2 = EnumDirection.EAST.b();
                        l = EnumDirection.WEST.b();
                        i1 = EnumDirection.SOUTH.b();
                        j1 = EnumDirection.NORTH.b();
                }
            }

            this.a(p_a_1_, Blocks.END_PORTAL_FRAME.fromLegacyData(i2).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(p_a_2_.nextFloat() > 0.9F)), 4, 3, 8, p_a_3_);
            this.a(p_a_1_, Blocks.END_PORTAL_FRAME.fromLegacyData(i2).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(p_a_2_.nextFloat() > 0.9F)), 5, 3, 8, p_a_3_);
            this.a(p_a_1_, Blocks.END_PORTAL_FRAME.fromLegacyData(i2).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(p_a_2_.nextFloat() > 0.9F)), 6, 3, 8, p_a_3_);
            this.a(p_a_1_, Blocks.END_PORTAL_FRAME.fromLegacyData(l).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(p_a_2_.nextFloat() > 0.9F)), 4, 3, 12, p_a_3_);
            this.a(p_a_1_, Blocks.END_PORTAL_FRAME.fromLegacyData(l).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(p_a_2_.nextFloat() > 0.9F)), 5, 3, 12, p_a_3_);
            this.a(p_a_1_, Blocks.END_PORTAL_FRAME.fromLegacyData(l).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(p_a_2_.nextFloat() > 0.9F)), 6, 3, 12, p_a_3_);
            this.a(p_a_1_, Blocks.END_PORTAL_FRAME.fromLegacyData(i1).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(p_a_2_.nextFloat() > 0.9F)), 3, 3, 9, p_a_3_);
            this.a(p_a_1_, Blocks.END_PORTAL_FRAME.fromLegacyData(i1).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(p_a_2_.nextFloat() > 0.9F)), 3, 3, 10, p_a_3_);
            this.a(p_a_1_, Blocks.END_PORTAL_FRAME.fromLegacyData(i1).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(p_a_2_.nextFloat() > 0.9F)), 3, 3, 11, p_a_3_);
            this.a(p_a_1_, Blocks.END_PORTAL_FRAME.fromLegacyData(j1).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(p_a_2_.nextFloat() > 0.9F)), 7, 3, 9, p_a_3_);
            this.a(p_a_1_, Blocks.END_PORTAL_FRAME.fromLegacyData(j1).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(p_a_2_.nextFloat() > 0.9F)), 7, 3, 10, p_a_3_);
            this.a(p_a_1_, Blocks.END_PORTAL_FRAME.fromLegacyData(j1).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(p_a_2_.nextFloat() > 0.9F)), 7, 3, 11, p_a_3_);

            if (!this.a)
            {
                i = this.d(3);
                BlockPosition blockposition = new BlockPosition(this.a(5, 6), i, this.b(5, 6));

                if (p_a_3_.b((BaseBlockPosition)blockposition))
                {
                    this.a = true;
                    p_a_1_.setTypeAndData(blockposition, Blocks.MOB_SPAWNER.getBlockData(), 2);
                    TileEntity tileentity = p_a_1_.getTileEntity(blockposition);

                    if (tileentity instanceof TileEntityMobSpawner)
                    {
                        ((TileEntityMobSpawner)tileentity).getSpawner().setMobName("Silverfish");
                    }
                }
            }

            return true;
        }
    }

    public static class WorldGenStrongholdPrison extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
    {
        public WorldGenStrongholdPrison()
        {
        }

        public WorldGenStrongholdPrison(int p_i785_1_, Random p_i785_2_, StructureBoundingBox p_i785_3_, EnumDirection p_i785_4_)
        {
            super(p_i785_1_);
            this.m = p_i785_4_;
            this.d = this.a(p_i785_2_);
            this.l = p_i785_3_;
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            this.a((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, 1, 1);
        }

        public static WorldGenStrongholdPieces.WorldGenStrongholdPrison a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -1, -1, 0, 9, 5, 11, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenStrongholdPieces.WorldGenStrongholdPrison(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (this.a(p_a_1_, p_a_3_))
            {
                return false;
            }
            else
            {
                this.a(p_a_1_, p_a_3_, 0, 0, 0, 8, 4, 10, true, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_2_, p_a_3_, this.d, 1, 1, 0);
                this.a(p_a_1_, p_a_3_, 1, 1, 10, 3, 3, 10, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 4, 1, 1, 4, 3, 1, false, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_3_, 4, 1, 3, 4, 3, 3, false, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_3_, 4, 1, 7, 4, 3, 7, false, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_3_, 4, 1, 9, 4, 3, 9, false, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_3_, 4, 1, 4, 4, 3, 6, Blocks.IRON_BARS.getBlockData(), Blocks.IRON_BARS.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 5, 1, 5, 7, 3, 5, Blocks.IRON_BARS.getBlockData(), Blocks.IRON_BARS.getBlockData(), false);
                this.a(p_a_1_, Blocks.IRON_BARS.getBlockData(), 4, 3, 2, p_a_3_);
                this.a(p_a_1_, Blocks.IRON_BARS.getBlockData(), 4, 3, 8, p_a_3_);
                this.a(p_a_1_, Blocks.IRON_DOOR.fromLegacyData(this.a(Blocks.IRON_DOOR, 3)), 4, 1, 2, p_a_3_);
                this.a(p_a_1_, Blocks.IRON_DOOR.fromLegacyData(this.a(Blocks.IRON_DOOR, 3) + 8), 4, 2, 2, p_a_3_);
                this.a(p_a_1_, Blocks.IRON_DOOR.fromLegacyData(this.a(Blocks.IRON_DOOR, 3)), 4, 1, 8, p_a_3_);
                this.a(p_a_1_, Blocks.IRON_DOOR.fromLegacyData(this.a(Blocks.IRON_DOOR, 3) + 8), 4, 2, 8, p_a_3_);
                return true;
            }
        }
    }

    public static class WorldGenStrongholdRightTurn extends WorldGenStrongholdPieces.WorldGenStrongholdLeftTurn
    {
        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            if (this.m != EnumDirection.NORTH && this.m != EnumDirection.EAST)
            {
                this.b((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, 1, 1);
            }
            else
            {
                this.c((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, 1, 1);
            }
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (this.a(p_a_1_, p_a_3_))
            {
                return false;
            }
            else
            {
                this.a(p_a_1_, p_a_3_, 0, 0, 0, 4, 4, 4, true, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_2_, p_a_3_, this.d, 1, 1, 0);

                if (this.m != EnumDirection.NORTH && this.m != EnumDirection.EAST)
                {
                    this.a(p_a_1_, p_a_3_, 0, 1, 1, 0, 3, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                }
                else
                {
                    this.a(p_a_1_, p_a_3_, 4, 1, 1, 4, 3, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                }

                return true;
            }
        }
    }

    public static class WorldGenStrongholdRoomCrossing extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
    {
        private static final List<StructurePieceTreasure> b = Lists.newArrayList(new StructurePieceTreasure[] {new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 1, 3, 5), new StructurePieceTreasure(Items.REDSTONE, 0, 4, 9, 5), new StructurePieceTreasure(Items.COAL, 0, 3, 8, 10), new StructurePieceTreasure(Items.BREAD, 0, 1, 3, 15), new StructurePieceTreasure(Items.APPLE, 0, 1, 3, 15), new StructurePieceTreasure(Items.IRON_PICKAXE, 0, 1, 1, 1)});
        protected int a;

        public WorldGenStrongholdRoomCrossing()
        {
        }

        public WorldGenStrongholdRoomCrossing(int p_i786_1_, Random p_i786_2_, StructureBoundingBox p_i786_3_, EnumDirection p_i786_4_)
        {
            super(p_i786_1_);
            this.m = p_i786_4_;
            this.d = this.a(p_i786_2_);
            this.l = p_i786_3_;
            this.a = p_i786_2_.nextInt(5);
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            p_a_1_.setInt("Type", this.a);
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
            this.a = p_b_1_.getInt("Type");
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            this.a((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, 4, 1);
            this.b((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, 1, 4);
            this.c((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, 1, 4);
        }

        public static WorldGenStrongholdPieces.WorldGenStrongholdRoomCrossing a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -4, -1, 0, 11, 7, 11, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenStrongholdPieces.WorldGenStrongholdRoomCrossing(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (this.a(p_a_1_, p_a_3_))
            {
                return false;
            }
            else
            {
                this.a(p_a_1_, p_a_3_, 0, 0, 0, 10, 6, 10, true, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_2_, p_a_3_, this.d, 4, 1, 0);
                this.a(p_a_1_, p_a_3_, 4, 1, 10, 6, 3, 10, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 0, 1, 4, 0, 3, 6, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 10, 1, 4, 10, 3, 6, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);

                switch (this.a)
                {
                    case 0:
                        this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 5, 1, 5, p_a_3_);
                        this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 5, 2, 5, p_a_3_);
                        this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 5, 3, 5, p_a_3_);
                        this.a(p_a_1_, Blocks.TORCH.getBlockData(), 4, 3, 5, p_a_3_);
                        this.a(p_a_1_, Blocks.TORCH.getBlockData(), 6, 3, 5, p_a_3_);
                        this.a(p_a_1_, Blocks.TORCH.getBlockData(), 5, 3, 4, p_a_3_);
                        this.a(p_a_1_, Blocks.TORCH.getBlockData(), 5, 3, 6, p_a_3_);
                        this.a(p_a_1_, Blocks.STONE_SLAB.getBlockData(), 4, 1, 4, p_a_3_);
                        this.a(p_a_1_, Blocks.STONE_SLAB.getBlockData(), 4, 1, 5, p_a_3_);
                        this.a(p_a_1_, Blocks.STONE_SLAB.getBlockData(), 4, 1, 6, p_a_3_);
                        this.a(p_a_1_, Blocks.STONE_SLAB.getBlockData(), 6, 1, 4, p_a_3_);
                        this.a(p_a_1_, Blocks.STONE_SLAB.getBlockData(), 6, 1, 5, p_a_3_);
                        this.a(p_a_1_, Blocks.STONE_SLAB.getBlockData(), 6, 1, 6, p_a_3_);
                        this.a(p_a_1_, Blocks.STONE_SLAB.getBlockData(), 5, 1, 4, p_a_3_);
                        this.a(p_a_1_, Blocks.STONE_SLAB.getBlockData(), 5, 1, 6, p_a_3_);
                        break;

                    case 1:
                        for (int i1 = 0; i1 < 5; ++i1)
                        {
                            this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 3, 1, 3 + i1, p_a_3_);
                            this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 7, 1, 3 + i1, p_a_3_);
                            this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 3 + i1, 1, 3, p_a_3_);
                            this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 3 + i1, 1, 7, p_a_3_);
                        }

                        this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 5, 1, 5, p_a_3_);
                        this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 5, 2, 5, p_a_3_);
                        this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 5, 3, 5, p_a_3_);
                        this.a(p_a_1_, Blocks.FLOWING_WATER.getBlockData(), 5, 4, 5, p_a_3_);
                        break;

                    case 2:
                        for (int i = 1; i <= 9; ++i)
                        {
                            this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 1, 3, i, p_a_3_);
                            this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 9, 3, i, p_a_3_);
                        }

                        for (int j = 1; j <= 9; ++j)
                        {
                            this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), j, 3, 1, p_a_3_);
                            this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), j, 3, 9, p_a_3_);
                        }

                        this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 5, 1, 4, p_a_3_);
                        this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 5, 1, 6, p_a_3_);
                        this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 5, 3, 4, p_a_3_);
                        this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 5, 3, 6, p_a_3_);
                        this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 4, 1, 5, p_a_3_);
                        this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 6, 1, 5, p_a_3_);
                        this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 4, 3, 5, p_a_3_);
                        this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 6, 3, 5, p_a_3_);

                        for (int k = 1; k <= 3; ++k)
                        {
                            this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 4, k, 4, p_a_3_);
                            this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 6, k, 4, p_a_3_);
                            this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 4, k, 6, p_a_3_);
                            this.a(p_a_1_, Blocks.COBBLESTONE.getBlockData(), 6, k, 6, p_a_3_);
                        }

                        this.a(p_a_1_, Blocks.TORCH.getBlockData(), 5, 3, 5, p_a_3_);

                        for (int l = 2; l <= 8; ++l)
                        {
                            this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 2, 3, l, p_a_3_);
                            this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 3, 3, l, p_a_3_);

                            if (l <= 3 || l >= 7)
                            {
                                this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 4, 3, l, p_a_3_);
                                this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 5, 3, l, p_a_3_);
                                this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 6, 3, l, p_a_3_);
                            }

                            this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 7, 3, l, p_a_3_);
                            this.a(p_a_1_, Blocks.PLANKS.getBlockData(), 8, 3, l, p_a_3_);
                        }

                        this.a(p_a_1_, Blocks.LADDER.fromLegacyData(this.a(Blocks.LADDER, EnumDirection.WEST.a())), 9, 1, 3, p_a_3_);
                        this.a(p_a_1_, Blocks.LADDER.fromLegacyData(this.a(Blocks.LADDER, EnumDirection.WEST.a())), 9, 2, 3, p_a_3_);
                        this.a(p_a_1_, Blocks.LADDER.fromLegacyData(this.a(Blocks.LADDER, EnumDirection.WEST.a())), 9, 3, 3, p_a_3_);
                        this.a(p_a_1_, p_a_3_, p_a_2_, 3, 4, 8, StructurePieceTreasure.a(b, new StructurePieceTreasure[] {Items.ENCHANTED_BOOK.b(p_a_2_)}), 1 + p_a_2_.nextInt(4));
                }

                return true;
            }
        }
    }

    public static class WorldGenStrongholdStairs extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
    {
        private boolean a;
        private boolean b;

        public WorldGenStrongholdStairs()
        {
        }

        public WorldGenStrongholdStairs(int p_i791_1_, Random p_i791_2_, StructureBoundingBox p_i791_3_, EnumDirection p_i791_4_)
        {
            super(p_i791_1_);
            this.m = p_i791_4_;
            this.d = this.a(p_i791_2_);
            this.l = p_i791_3_;
            this.a = p_i791_2_.nextInt(2) == 0;
            this.b = p_i791_2_.nextInt(2) == 0;
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            p_a_1_.setBoolean("Left", this.a);
            p_a_1_.setBoolean("Right", this.b);
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
            this.a = p_b_1_.getBoolean("Left");
            this.b = p_b_1_.getBoolean("Right");
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            this.a((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, 1, 1);

            if (this.a)
            {
                this.b((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, 1, 2);
            }

            if (this.b)
            {
                this.c((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, 1, 2);
            }
        }

        public static WorldGenStrongholdPieces.WorldGenStrongholdStairs a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -1, -1, 0, 5, 5, 7, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenStrongholdPieces.WorldGenStrongholdStairs(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (this.a(p_a_1_, p_a_3_))
            {
                return false;
            }
            else
            {
                this.a(p_a_1_, p_a_3_, 0, 0, 0, 4, 4, 6, true, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_2_, p_a_3_, this.d, 1, 1, 0);
                this.a(p_a_1_, p_a_2_, p_a_3_, WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.OPENING, 1, 1, 6);
                this.a(p_a_1_, p_a_3_, p_a_2_, 0.1F, 1, 2, 1, Blocks.TORCH.getBlockData());
                this.a(p_a_1_, p_a_3_, p_a_2_, 0.1F, 3, 2, 1, Blocks.TORCH.getBlockData());
                this.a(p_a_1_, p_a_3_, p_a_2_, 0.1F, 1, 2, 5, Blocks.TORCH.getBlockData());
                this.a(p_a_1_, p_a_3_, p_a_2_, 0.1F, 3, 2, 5, Blocks.TORCH.getBlockData());

                if (this.a)
                {
                    this.a(p_a_1_, p_a_3_, 0, 1, 2, 0, 3, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                }

                if (this.b)
                {
                    this.a(p_a_1_, p_a_3_, 4, 1, 2, 4, 3, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                }

                return true;
            }
        }
    }

    public static class WorldGenStrongholdStairs2 extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
    {
        private boolean a;

        public WorldGenStrongholdStairs2()
        {
        }

        public WorldGenStrongholdStairs2(int p_i788_1_, Random p_i788_2_, int p_i788_3_, int p_i788_4_)
        {
            super(p_i788_1_);
            this.a = true;
            this.m = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(p_i788_2_);
            this.d = WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.OPENING;

            switch (this.m)
            {
                case NORTH:
                case SOUTH:
                    this.l = new StructureBoundingBox(p_i788_3_, 64, p_i788_4_, p_i788_3_ + 5 - 1, 74, p_i788_4_ + 5 - 1);
                    break;

                default:
                    this.l = new StructureBoundingBox(p_i788_3_, 64, p_i788_4_, p_i788_3_ + 5 - 1, 74, p_i788_4_ + 5 - 1);
            }
        }

        public WorldGenStrongholdStairs2(int p_i789_1_, Random p_i789_2_, StructureBoundingBox p_i789_3_, EnumDirection p_i789_4_)
        {
            super(p_i789_1_);
            this.a = false;
            this.m = p_i789_4_;
            this.d = this.a(p_i789_2_);
            this.l = p_i789_3_;
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            p_a_1_.setBoolean("Source", this.a);
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
            this.a = p_b_1_.getBoolean("Source");
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            if (this.a)
            {
                WorldGenStrongholdPieces.d = WorldGenStrongholdPieces.WorldGenStrongholdCrossing.class;
            }

            this.a((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, 1, 1);
        }

        public static WorldGenStrongholdPieces.WorldGenStrongholdStairs2 a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -1, -7, 0, 5, 11, 5, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenStrongholdPieces.WorldGenStrongholdStairs2(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (this.a(p_a_1_, p_a_3_))
            {
                return false;
            }
            else
            {
                this.a(p_a_1_, p_a_3_, 0, 0, 0, 4, 10, 4, true, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_2_, p_a_3_, this.d, 1, 7, 0);
                this.a(p_a_1_, p_a_2_, p_a_3_, WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.OPENING, 1, 1, 4);
                this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 2, 6, 1, p_a_3_);
                this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 1, 5, 1, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE.a()), 1, 6, 1, p_a_3_);
                this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 1, 5, 2, p_a_3_);
                this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 1, 4, 3, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE.a()), 1, 5, 3, p_a_3_);
                this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 2, 4, 3, p_a_3_);
                this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 3, 3, 3, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE.a()), 3, 4, 3, p_a_3_);
                this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 3, 3, 2, p_a_3_);
                this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 3, 2, 1, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE.a()), 3, 3, 1, p_a_3_);
                this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 2, 2, 1, p_a_3_);
                this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 1, 1, 1, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE.a()), 1, 2, 1, p_a_3_);
                this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 1, 1, 2, p_a_3_);
                this.a(p_a_1_, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE.a()), 1, 1, 3, p_a_3_);
                return true;
            }
        }
    }

    public static class WorldGenStrongholdStairsStraight extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
    {
        public WorldGenStrongholdStairsStraight()
        {
        }

        public WorldGenStrongholdStairsStraight(int p_i792_1_, Random p_i792_2_, StructureBoundingBox p_i792_3_, EnumDirection p_i792_4_)
        {
            super(p_i792_1_);
            this.m = p_i792_4_;
            this.d = this.a(p_i792_2_);
            this.l = p_i792_3_;
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            this.a((WorldGenStrongholdPieces.WorldGenStrongholdStart)p_a_1_, p_a_2_, p_a_3_, 1, 1);
        }

        public static WorldGenStrongholdPieces.WorldGenStrongholdStairsStraight a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -1, -7, 0, 5, 11, 8, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenStrongholdPieces.WorldGenStrongholdStairsStraight(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (this.a(p_a_1_, p_a_3_))
            {
                return false;
            }
            else
            {
                this.a(p_a_1_, p_a_3_, 0, 0, 0, 4, 10, 7, true, p_a_2_, WorldGenStrongholdPieces.e);
                this.a(p_a_1_, p_a_2_, p_a_3_, this.d, 1, 7, 0);
                this.a(p_a_1_, p_a_2_, p_a_3_, WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.OPENING, 1, 1, 7);
                int i = this.a(Blocks.STONE_STAIRS, 2);

                for (int j = 0; j < 6; ++j)
                {
                    this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(i), 1, 6 - j, 1 + j, p_a_3_);
                    this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(i), 2, 6 - j, 1 + j, p_a_3_);
                    this.a(p_a_1_, Blocks.STONE_STAIRS.fromLegacyData(i), 3, 6 - j, 1 + j, p_a_3_);

                    if (j < 5)
                    {
                        this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 1, 5 - j, 1 + j, p_a_3_);
                        this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 2, 5 - j, 1 + j, p_a_3_);
                        this.a(p_a_1_, Blocks.STONEBRICK.getBlockData(), 3, 5 - j, 1 + j, p_a_3_);
                    }
                }

                return true;
            }
        }
    }

    public static class WorldGenStrongholdStart extends WorldGenStrongholdPieces.WorldGenStrongholdStairs2
    {
        public WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight a;
        public WorldGenStrongholdPieces.WorldGenStrongholdPortalRoom b;
        public List<StructurePiece> c = Lists.<StructurePiece>newArrayList();

        public WorldGenStrongholdStart()
        {
        }

        public WorldGenStrongholdStart(int p_i790_1_, Random p_i790_2_, int p_i790_3_, int p_i790_4_)
        {
            super(0, p_i790_2_, p_i790_3_, p_i790_4_);
        }

        public BlockPosition a()
        {
            return this.b != null ? this.b.a() : super.a();
        }
    }

    static class WorldGenStrongholdStones extends StructurePiece.StructurePieceBlockSelector
    {
        private WorldGenStrongholdStones()
        {
        }

        public void a(Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, boolean p_a_5_)
        {
            if (p_a_5_)
            {
                float f = p_a_1_.nextFloat();

                if (f < 0.2F)
                {
                    this.a = Blocks.STONEBRICK.fromLegacyData(BlockSmoothBrick.O);
                }
                else if (f < 0.5F)
                {
                    this.a = Blocks.STONEBRICK.fromLegacyData(BlockSmoothBrick.N);
                }
                else if (f < 0.55F)
                {
                    this.a = Blocks.MONSTER_EGG.fromLegacyData(BlockMonsterEggs.EnumMonsterEggVarient.STONEBRICK.a());
                }
                else
                {
                    this.a = Blocks.STONEBRICK.getBlockData();
                }
            }
            else
            {
                this.a = Blocks.AIR.getBlockData();
            }
        }
    }
}
