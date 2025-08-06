package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;

public class WorldGenNetherPieces
{
    private static final WorldGenNetherPieces.WorldGenNetherPieceWeight[] a = new WorldGenNetherPieces.WorldGenNetherPieceWeight[] {new WorldGenNetherPieces.WorldGenNetherPieceWeight(WorldGenNetherPieces.WorldGenNetherPiece3.class, 30, 0, true), new WorldGenNetherPieces.WorldGenNetherPieceWeight(WorldGenNetherPieces.WorldGenNetherPiece1.class, 10, 4), new WorldGenNetherPieces.WorldGenNetherPieceWeight(WorldGenNetherPieces.WorldGenNetherPiece13.class, 10, 4), new WorldGenNetherPieces.WorldGenNetherPieceWeight(WorldGenNetherPieces.WorldGenNetherPiece14.class, 10, 3), new WorldGenNetherPieces.WorldGenNetherPieceWeight(WorldGenNetherPieces.WorldGenNetherPiece12.class, 5, 2), new WorldGenNetherPieces.WorldGenNetherPieceWeight(WorldGenNetherPieces.WorldGenNetherPiece6.class, 5, 1)};
    private static final WorldGenNetherPieces.WorldGenNetherPieceWeight[] b = new WorldGenNetherPieces.WorldGenNetherPieceWeight[] {new WorldGenNetherPieces.WorldGenNetherPieceWeight(WorldGenNetherPieces.WorldGenNetherPiece9.class, 25, 0, true), new WorldGenNetherPieces.WorldGenNetherPieceWeight(WorldGenNetherPieces.WorldGenNetherPiece7.class, 15, 5), new WorldGenNetherPieces.WorldGenNetherPieceWeight(WorldGenNetherPieces.WorldGenNetherPiece10.class, 5, 10), new WorldGenNetherPieces.WorldGenNetherPieceWeight(WorldGenNetherPieces.WorldGenNetherPiece8.class, 5, 10), new WorldGenNetherPieces.WorldGenNetherPieceWeight(WorldGenNetherPieces.WorldGenNetherPiece4.class, 10, 3, true), new WorldGenNetherPieces.WorldGenNetherPieceWeight(WorldGenNetherPieces.WorldGenNetherPiece5.class, 7, 2), new WorldGenNetherPieces.WorldGenNetherPieceWeight(WorldGenNetherPieces.WorldGenNetherPiece11.class, 5, 2)};

    public static void a()
    {
        WorldGenFactory.a(WorldGenNetherPieces.WorldGenNetherPiece1.class, "NeBCr");
        WorldGenFactory.a(WorldGenNetherPieces.WorldGenNetherPiece2.class, "NeBEF");
        WorldGenFactory.a(WorldGenNetherPieces.WorldGenNetherPiece3.class, "NeBS");
        WorldGenFactory.a(WorldGenNetherPieces.WorldGenNetherPiece4.class, "NeCCS");
        WorldGenFactory.a(WorldGenNetherPieces.WorldGenNetherPiece5.class, "NeCTB");
        WorldGenFactory.a(WorldGenNetherPieces.WorldGenNetherPiece6.class, "NeCE");
        WorldGenFactory.a(WorldGenNetherPieces.WorldGenNetherPiece7.class, "NeSCSC");
        WorldGenFactory.a(WorldGenNetherPieces.WorldGenNetherPiece8.class, "NeSCLT");
        WorldGenFactory.a(WorldGenNetherPieces.WorldGenNetherPiece9.class, "NeSC");
        WorldGenFactory.a(WorldGenNetherPieces.WorldGenNetherPiece10.class, "NeSCRT");
        WorldGenFactory.a(WorldGenNetherPieces.WorldGenNetherPiece11.class, "NeCSR");
        WorldGenFactory.a(WorldGenNetherPieces.WorldGenNetherPiece12.class, "NeMT");
        WorldGenFactory.a(WorldGenNetherPieces.WorldGenNetherPiece13.class, "NeRC");
        WorldGenFactory.a(WorldGenNetherPieces.WorldGenNetherPiece14.class, "NeSR");
        WorldGenFactory.a(WorldGenNetherPieces.WorldGenNetherPiece15.class, "NeStart");
    }

    private static WorldGenNetherPieces.WorldGenNetherPiece b(WorldGenNetherPieces.WorldGenNetherPieceWeight p_b_0_, List<StructurePiece> p_b_1_, Random p_b_2_, int p_b_3_, int p_b_4_, int p_b_5_, EnumDirection p_b_6_, int p_b_7_)
    {
        Class oclass = p_b_0_.a;
        Object object = null;

        if (oclass == WorldGenNetherPieces.WorldGenNetherPiece3.class)
        {
            object = WorldGenNetherPieces.WorldGenNetherPiece3.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_6_, p_b_7_);
        }
        else if (oclass == WorldGenNetherPieces.WorldGenNetherPiece1.class)
        {
            object = WorldGenNetherPieces.WorldGenNetherPiece1.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_6_, p_b_7_);
        }
        else if (oclass == WorldGenNetherPieces.WorldGenNetherPiece13.class)
        {
            object = WorldGenNetherPieces.WorldGenNetherPiece13.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_6_, p_b_7_);
        }
        else if (oclass == WorldGenNetherPieces.WorldGenNetherPiece14.class)
        {
            object = WorldGenNetherPieces.WorldGenNetherPiece14.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_7_, p_b_6_);
        }
        else if (oclass == WorldGenNetherPieces.WorldGenNetherPiece12.class)
        {
            object = WorldGenNetherPieces.WorldGenNetherPiece12.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_7_, p_b_6_);
        }
        else if (oclass == WorldGenNetherPieces.WorldGenNetherPiece6.class)
        {
            object = WorldGenNetherPieces.WorldGenNetherPiece6.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_6_, p_b_7_);
        }
        else if (oclass == WorldGenNetherPieces.WorldGenNetherPiece9.class)
        {
            object = WorldGenNetherPieces.WorldGenNetherPiece9.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_6_, p_b_7_);
        }
        else if (oclass == WorldGenNetherPieces.WorldGenNetherPiece10.class)
        {
            object = WorldGenNetherPieces.WorldGenNetherPiece10.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_6_, p_b_7_);
        }
        else if (oclass == WorldGenNetherPieces.WorldGenNetherPiece8.class)
        {
            object = WorldGenNetherPieces.WorldGenNetherPiece8.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_6_, p_b_7_);
        }
        else if (oclass == WorldGenNetherPieces.WorldGenNetherPiece4.class)
        {
            object = WorldGenNetherPieces.WorldGenNetherPiece4.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_6_, p_b_7_);
        }
        else if (oclass == WorldGenNetherPieces.WorldGenNetherPiece5.class)
        {
            object = WorldGenNetherPieces.WorldGenNetherPiece5.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_6_, p_b_7_);
        }
        else if (oclass == WorldGenNetherPieces.WorldGenNetherPiece7.class)
        {
            object = WorldGenNetherPieces.WorldGenNetherPiece7.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_6_, p_b_7_);
        }
        else if (oclass == WorldGenNetherPieces.WorldGenNetherPiece11.class)
        {
            object = WorldGenNetherPieces.WorldGenNetherPiece11.a(p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_6_, p_b_7_);
        }

        return (WorldGenNetherPieces.WorldGenNetherPiece)object;
    }

    abstract static class WorldGenNetherPiece extends StructurePiece
    {
        protected static final List<StructurePieceTreasure> a = Lists.newArrayList(new StructurePieceTreasure[] {new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 5), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 5), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 1, 3, 15), new StructurePieceTreasure(Items.GOLDEN_SWORD, 0, 1, 1, 5), new StructurePieceTreasure(Items.GOLDEN_CHESTPLATE, 0, 1, 1, 5), new StructurePieceTreasure(Items.FLINT_AND_STEEL, 0, 1, 1, 5), new StructurePieceTreasure(Items.NETHER_WART, 0, 3, 7, 5), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 10), new StructurePieceTreasure(Items.GOLDEN_HORSE_ARMOR, 0, 1, 1, 8), new StructurePieceTreasure(Items.IRON_HORSE_ARMOR, 0, 1, 1, 5), new StructurePieceTreasure(Items.DIAMOND_HORSE_ARMOR, 0, 1, 1, 3), new StructurePieceTreasure(Item.getItemOf(Blocks.OBSIDIAN), 0, 2, 4, 2)});

        public WorldGenNetherPiece()
        {
        }

        protected WorldGenNetherPiece(int p_i742_1_)
        {
            super(p_i742_1_);
        }

        protected void b(NBTTagCompound p_b_1_)
        {
        }

        protected void a(NBTTagCompound p_a_1_)
        {
        }

        private int a(List<WorldGenNetherPieces.WorldGenNetherPieceWeight> p_a_1_)
        {
            boolean flag = false;
            int i = 0;

            for (WorldGenNetherPieces.WorldGenNetherPieceWeight worldgennetherpieces$worldgennetherpieceweight : p_a_1_)
            {
                if (worldgennetherpieces$worldgennetherpieceweight.d > 0 && worldgennetherpieces$worldgennetherpieceweight.c < worldgennetherpieces$worldgennetherpieceweight.d)
                {
                    flag = true;
                }

                i += worldgennetherpieces$worldgennetherpieceweight.b;
            }

            return flag ? i : -1;
        }

        private WorldGenNetherPieces.WorldGenNetherPiece a(WorldGenNetherPieces.WorldGenNetherPiece15 p_a_1_, List<WorldGenNetherPieces.WorldGenNetherPieceWeight> p_a_2_, List<StructurePiece> p_a_3_, Random p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_, EnumDirection p_a_8_, int p_a_9_)
        {
            int i = this.a(p_a_2_);
            boolean flag = i > 0 && p_a_9_ <= 30;
            int j = 0;

            while (j < 5 && flag)
            {
                ++j;
                int k = p_a_4_.nextInt(i);

                for (WorldGenNetherPieces.WorldGenNetherPieceWeight worldgennetherpieces$worldgennetherpieceweight : p_a_2_)
                {
                    k -= worldgennetherpieces$worldgennetherpieceweight.b;

                    if (k < 0)
                    {
                        if (!worldgennetherpieces$worldgennetherpieceweight.a(p_a_9_) || worldgennetherpieces$worldgennetherpieceweight == p_a_1_.b && !worldgennetherpieces$worldgennetherpieceweight.e)
                        {
                            break;
                        }

                        WorldGenNetherPieces.WorldGenNetherPiece worldgennetherpieces$worldgennetherpiece = WorldGenNetherPieces.b(worldgennetherpieces$worldgennetherpieceweight, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, p_a_8_, p_a_9_);

                        if (worldgennetherpieces$worldgennetherpiece != null)
                        {
                            ++worldgennetherpieces$worldgennetherpieceweight.c;
                            p_a_1_.b = worldgennetherpieces$worldgennetherpieceweight;

                            if (!worldgennetherpieces$worldgennetherpieceweight.a())
                            {
                                p_a_2_.remove(worldgennetherpieces$worldgennetherpieceweight);
                            }

                            return worldgennetherpieces$worldgennetherpiece;
                        }
                    }
                }
            }

            return WorldGenNetherPieces.WorldGenNetherPiece2.a(p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, p_a_8_, p_a_9_);
        }

        private StructurePiece a(WorldGenNetherPieces.WorldGenNetherPiece15 p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, EnumDirection p_a_7_, int p_a_8_, boolean p_a_9_)
        {
            if (Math.abs(p_a_4_ - p_a_1_.c().a) <= 112 && Math.abs(p_a_6_ - p_a_1_.c().c) <= 112)
            {
                List list = p_a_1_.c;

                if (p_a_9_)
                {
                    list = p_a_1_.d;
                }

                WorldGenNetherPieces.WorldGenNetherPiece worldgennetherpieces$worldgennetherpiece = this.a(p_a_1_, list, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, p_a_8_ + 1);

                if (worldgennetherpieces$worldgennetherpiece != null)
                {
                    p_a_2_.add(worldgennetherpieces$worldgennetherpiece);
                    p_a_1_.e.add(worldgennetherpieces$worldgennetherpiece);
                }

                return worldgennetherpieces$worldgennetherpiece;
            }
            else
            {
                return WorldGenNetherPieces.WorldGenNetherPiece2.a(p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, p_a_8_);
            }
        }

        protected StructurePiece a(WorldGenNetherPieces.WorldGenNetherPiece15 p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_, int p_a_4_, int p_a_5_, boolean p_a_6_)
        {
            if (this.m != null)
            {
                switch (this.m)
                {
                    case NORTH:
                        return this.a(p_a_1_, p_a_2_, p_a_3_, this.l.a + p_a_4_, this.l.b + p_a_5_, this.l.c - 1, this.m, this.d(), p_a_6_);

                    case SOUTH:
                        return this.a(p_a_1_, p_a_2_, p_a_3_, this.l.a + p_a_4_, this.l.b + p_a_5_, this.l.f + 1, this.m, this.d(), p_a_6_);

                    case WEST:
                        return this.a(p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.b + p_a_5_, this.l.c + p_a_4_, this.m, this.d(), p_a_6_);

                    case EAST:
                        return this.a(p_a_1_, p_a_2_, p_a_3_, this.l.d + 1, this.l.b + p_a_5_, this.l.c + p_a_4_, this.m, this.d(), p_a_6_);
                }
            }

            return null;
        }

        protected StructurePiece b(WorldGenNetherPieces.WorldGenNetherPiece15 p_b_1_, List<StructurePiece> p_b_2_, Random p_b_3_, int p_b_4_, int p_b_5_, boolean p_b_6_)
        {
            if (this.m != null)
            {
                switch (this.m)
                {
                    case NORTH:
                        return this.a(p_b_1_, p_b_2_, p_b_3_, this.l.a - 1, this.l.b + p_b_4_, this.l.c + p_b_5_, EnumDirection.WEST, this.d(), p_b_6_);

                    case SOUTH:
                        return this.a(p_b_1_, p_b_2_, p_b_3_, this.l.a - 1, this.l.b + p_b_4_, this.l.c + p_b_5_, EnumDirection.WEST, this.d(), p_b_6_);

                    case WEST:
                        return this.a(p_b_1_, p_b_2_, p_b_3_, this.l.a + p_b_5_, this.l.b + p_b_4_, this.l.c - 1, EnumDirection.NORTH, this.d(), p_b_6_);

                    case EAST:
                        return this.a(p_b_1_, p_b_2_, p_b_3_, this.l.a + p_b_5_, this.l.b + p_b_4_, this.l.c - 1, EnumDirection.NORTH, this.d(), p_b_6_);
                }
            }

            return null;
        }

        protected StructurePiece c(WorldGenNetherPieces.WorldGenNetherPiece15 p_c_1_, List<StructurePiece> p_c_2_, Random p_c_3_, int p_c_4_, int p_c_5_, boolean p_c_6_)
        {
            if (this.m != null)
            {
                switch (this.m)
                {
                    case NORTH:
                        return this.a(p_c_1_, p_c_2_, p_c_3_, this.l.d + 1, this.l.b + p_c_4_, this.l.c + p_c_5_, EnumDirection.EAST, this.d(), p_c_6_);

                    case SOUTH:
                        return this.a(p_c_1_, p_c_2_, p_c_3_, this.l.d + 1, this.l.b + p_c_4_, this.l.c + p_c_5_, EnumDirection.EAST, this.d(), p_c_6_);

                    case WEST:
                        return this.a(p_c_1_, p_c_2_, p_c_3_, this.l.a + p_c_5_, this.l.b + p_c_4_, this.l.f + 1, EnumDirection.SOUTH, this.d(), p_c_6_);

                    case EAST:
                        return this.a(p_c_1_, p_c_2_, p_c_3_, this.l.a + p_c_5_, this.l.b + p_c_4_, this.l.f + 1, EnumDirection.SOUTH, this.d(), p_c_6_);
                }
            }

            return null;
        }

        protected static boolean a(StructureBoundingBox p_a_0_)
        {
            return p_a_0_ != null && p_a_0_.b > 10;
        }
    }

    public static class WorldGenNetherPiece1 extends WorldGenNetherPieces.WorldGenNetherPiece
    {
        public WorldGenNetherPiece1()
        {
        }

        public WorldGenNetherPiece1(int p_i729_1_, Random p_i729_2_, StructureBoundingBox p_i729_3_, EnumDirection p_i729_4_)
        {
            super(p_i729_1_);
            this.m = p_i729_4_;
            this.l = p_i729_3_;
        }

        protected WorldGenNetherPiece1(Random p_i730_1_, int p_i730_2_, int p_i730_3_)
        {
            super(0);
            this.m = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(p_i730_1_);

            switch (this.m)
            {
                case NORTH:
                case SOUTH:
                    this.l = new StructureBoundingBox(p_i730_2_, 64, p_i730_3_, p_i730_2_ + 19 - 1, 73, p_i730_3_ + 19 - 1);
                    break;

                default:
                    this.l = new StructureBoundingBox(p_i730_2_, 64, p_i730_3_, p_i730_2_ + 19 - 1, 73, p_i730_3_ + 19 - 1);
            }
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            this.a((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 8, 3, false);
            this.b((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 3, 8, false);
            this.c((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 3, 8, false);
        }

        public static WorldGenNetherPieces.WorldGenNetherPiece1 a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -8, -3, 0, 19, 10, 19, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenNetherPieces.WorldGenNetherPiece1(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 7, 3, 0, 11, 4, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 3, 7, 18, 4, 11, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 8, 5, 0, 10, 7, 18, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 5, 8, 18, 7, 10, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 7, 5, 0, 7, 5, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 7, 5, 11, 7, 5, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 11, 5, 0, 11, 5, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 11, 5, 11, 11, 5, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 5, 7, 7, 5, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 11, 5, 7, 18, 5, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 5, 11, 7, 5, 11, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 11, 5, 11, 18, 5, 11, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 7, 2, 0, 11, 2, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 7, 2, 13, 11, 2, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 7, 0, 0, 11, 1, 3, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 7, 0, 15, 11, 1, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);

            for (int i = 7; i <= 11; ++i)
            {
                for (int j = 0; j <= 2; ++j)
                {
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, p_a_3_);
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), i, -1, 18 - j, p_a_3_);
                }
            }

            this.a(p_a_1_, p_a_3_, 0, 2, 7, 5, 2, 11, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 13, 2, 7, 18, 2, 11, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 0, 7, 3, 1, 11, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 15, 0, 7, 18, 1, 11, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);

            for (int k = 0; k <= 2; ++k)
            {
                for (int l = 7; l <= 11; ++l)
                {
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), k, -1, l, p_a_3_);
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), 18 - k, -1, l, p_a_3_);
                }
            }

            return true;
        }
    }

    public static class WorldGenNetherPiece10 extends WorldGenNetherPieces.WorldGenNetherPiece
    {
        private boolean b;

        public WorldGenNetherPiece10()
        {
        }

        public WorldGenNetherPiece10(int p_i739_1_, Random p_i739_2_, StructureBoundingBox p_i739_3_, EnumDirection p_i739_4_)
        {
            super(p_i739_1_);
            this.m = p_i739_4_;
            this.l = p_i739_3_;
            this.b = p_i739_2_.nextInt(3) == 0;
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
            this.b = p_b_1_.getBoolean("Chest");
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            p_a_1_.setBoolean("Chest", this.b);
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            this.c((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 0, 1, true);
        }

        public static WorldGenNetherPieces.WorldGenNetherPiece10 a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -1, 0, 0, 5, 7, 5, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenNetherPieces.WorldGenNetherPiece10(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 4, 5, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 0, 5, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 3, 1, 0, 4, 1, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 3, 3, 0, 4, 3, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 2, 0, 4, 5, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 2, 4, 4, 5, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 3, 4, 1, 4, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 3, 3, 4, 3, 4, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);

            if (this.b && p_a_3_.b((BaseBlockPosition)(new BlockPosition(this.a(1, 3), this.d(2), this.b(1, 3)))))
            {
                this.b = false;
                this.a(p_a_1_, p_a_3_, p_a_2_, 1, 2, 3, a, 2 + p_a_2_.nextInt(4));
            }

            this.a(p_a_1_, p_a_3_, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);

            for (int i = 0; i <= 4; ++i)
            {
                for (int j = 0; j <= 4; ++j)
                {
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, p_a_3_);
                }
            }

            return true;
        }
    }

    public static class WorldGenNetherPiece11 extends WorldGenNetherPieces.WorldGenNetherPiece
    {
        public WorldGenNetherPiece11()
        {
        }

        public WorldGenNetherPiece11(int p_i740_1_, Random p_i740_2_, StructureBoundingBox p_i740_3_, EnumDirection p_i740_4_)
        {
            super(p_i740_1_);
            this.m = p_i740_4_;
            this.l = p_i740_3_;
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            this.a((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 5, 3, true);
            this.a((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 5, 11, true);
        }

        public static WorldGenNetherPieces.WorldGenNetherPiece11 a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -5, -3, 0, 13, 14, 13, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenNetherPieces.WorldGenNetherPiece11(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 5, 0, 12, 13, 12, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);

            for (int i = 1; i <= 11; i += 2)
            {
                this.a(p_a_1_, p_a_3_, i, 10, 0, i, 11, 0, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, i, 10, 12, i, 11, 12, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 0, 10, i, 0, 11, i, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 12, 10, i, 12, 11, i, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
                this.a(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), i, 13, 0, p_a_3_);
                this.a(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), i, 13, 12, p_a_3_);
                this.a(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), 0, 13, i, p_a_3_);
                this.a(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), 12, 13, i, p_a_3_);
                this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), i + 1, 13, 0, p_a_3_);
                this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), i + 1, 13, 12, p_a_3_);
                this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), 0, 13, i + 1, p_a_3_);
                this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), 12, 13, i + 1, p_a_3_);
            }

            this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), 0, 13, 0, p_a_3_);
            this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), 0, 13, 12, p_a_3_);
            this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), 0, 13, 0, p_a_3_);
            this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), 12, 13, 0, p_a_3_);

            for (int j1 = 3; j1 <= 9; j1 += 2)
            {
                this.a(p_a_1_, p_a_3_, 1, 7, j1, 1, 8, j1, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 11, 7, j1, 11, 8, j1, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            }

            int k1 = this.a(Blocks.NETHER_BRICK_STAIRS, 3);

            for (int j = 0; j <= 6; ++j)
            {
                int k = j + 4;

                for (int l = 5; l <= 7; ++l)
                {
                    this.a(p_a_1_, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(k1), l, 5 + j, k, p_a_3_);
                }

                if (k >= 5 && k <= 8)
                {
                    this.a(p_a_1_, p_a_3_, 5, 5, k, 7, j + 4, k, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
                }
                else if (k >= 9 && k <= 10)
                {
                    this.a(p_a_1_, p_a_3_, 5, 8, k, 7, j + 4, k, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
                }

                if (j >= 1)
                {
                    this.a(p_a_1_, p_a_3_, 5, 6 + j, k, 7, 9 + j, k, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                }
            }

            for (int l1 = 5; l1 <= 7; ++l1)
            {
                this.a(p_a_1_, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(k1), l1, 12, 11, p_a_3_);
            }

            this.a(p_a_1_, p_a_3_, 5, 6, 7, 5, 7, 7, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 7, 6, 7, 7, 7, 7, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 5, 13, 12, 7, 13, 12, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 5, 2, 3, 5, 3, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 5, 9, 3, 5, 10, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 5, 4, 2, 5, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 9, 5, 2, 10, 5, 3, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 9, 5, 9, 10, 5, 10, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 10, 5, 4, 10, 5, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            int i2 = this.a(Blocks.NETHER_BRICK_STAIRS, 0);
            int j2 = this.a(Blocks.NETHER_BRICK_STAIRS, 1);
            this.a(p_a_1_, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(j2), 4, 5, 2, p_a_3_);
            this.a(p_a_1_, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(j2), 4, 5, 3, p_a_3_);
            this.a(p_a_1_, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(j2), 4, 5, 9, p_a_3_);
            this.a(p_a_1_, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(j2), 4, 5, 10, p_a_3_);
            this.a(p_a_1_, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(i2), 8, 5, 2, p_a_3_);
            this.a(p_a_1_, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(i2), 8, 5, 3, p_a_3_);
            this.a(p_a_1_, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(i2), 8, 5, 9, p_a_3_);
            this.a(p_a_1_, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(i2), 8, 5, 10, p_a_3_);
            this.a(p_a_1_, p_a_3_, 3, 4, 4, 4, 4, 8, Blocks.SOUL_SAND.getBlockData(), Blocks.SOUL_SAND.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 8, 4, 4, 9, 4, 8, Blocks.SOUL_SAND.getBlockData(), Blocks.SOUL_SAND.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 3, 5, 4, 4, 5, 8, Blocks.NETHER_WART.getBlockData(), Blocks.NETHER_WART.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 8, 5, 4, 9, 5, 8, Blocks.NETHER_WART.getBlockData(), Blocks.NETHER_WART.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);

            for (int k2 = 4; k2 <= 8; ++k2)
            {
                for (int i1 = 0; i1 <= 2; ++i1)
                {
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), k2, -1, i1, p_a_3_);
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), k2, -1, 12 - i1, p_a_3_);
                }
            }

            for (int l2 = 0; l2 <= 2; ++l2)
            {
                for (int i3 = 4; i3 <= 8; ++i3)
                {
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), l2, -1, i3, p_a_3_);
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), 12 - l2, -1, i3, p_a_3_);
                }
            }

            return true;
        }
    }

    public static class WorldGenNetherPiece12 extends WorldGenNetherPieces.WorldGenNetherPiece
    {
        private boolean b;

        public WorldGenNetherPiece12()
        {
        }

        public WorldGenNetherPiece12(int p_i741_1_, Random p_i741_2_, StructureBoundingBox p_i741_3_, EnumDirection p_i741_4_)
        {
            super(p_i741_1_);
            this.m = p_i741_4_;
            this.l = p_i741_3_;
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
            this.b = p_b_1_.getBoolean("Mob");
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            p_a_1_.setBoolean("Mob", this.b);
        }

        public static WorldGenNetherPieces.WorldGenNetherPiece12 a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, EnumDirection p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -2, 0, 0, 7, 8, 9, p_a_6_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenNetherPieces.WorldGenNetherPiece12(p_a_5_, p_a_1_, structureboundingbox, p_a_6_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 6, 7, 7, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 0, 0, 5, 1, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 2, 1, 5, 2, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 3, 2, 5, 3, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 4, 3, 5, 4, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 2, 0, 1, 4, 2, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 5, 2, 0, 5, 4, 2, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 5, 2, 1, 5, 3, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 5, 5, 2, 5, 5, 3, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 5, 3, 0, 5, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 6, 5, 3, 6, 5, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 5, 8, 5, 5, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), 1, 6, 3, p_a_3_);
            this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), 5, 6, 3, p_a_3_);
            this.a(p_a_1_, p_a_3_, 0, 6, 3, 0, 6, 8, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 6, 6, 3, 6, 6, 8, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 6, 8, 5, 7, 8, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 8, 8, 4, 8, 8, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);

            if (!this.b)
            {
                BlockPosition blockposition = new BlockPosition(this.a(3, 5), this.d(5), this.b(3, 5));

                if (p_a_3_.b((BaseBlockPosition)blockposition))
                {
                    this.b = true;
                    p_a_1_.setTypeAndData(blockposition, Blocks.MOB_SPAWNER.getBlockData(), 2);
                    TileEntity tileentity = p_a_1_.getTileEntity(blockposition);

                    if (tileentity instanceof TileEntityMobSpawner)
                    {
                        ((TileEntityMobSpawner)tileentity).getSpawner().setMobName("Blaze");
                    }
                }
            }

            for (int i = 0; i <= 6; ++i)
            {
                for (int j = 0; j <= 6; ++j)
                {
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, p_a_3_);
                }
            }

            return true;
        }
    }

    public static class WorldGenNetherPiece13 extends WorldGenNetherPieces.WorldGenNetherPiece
    {
        public WorldGenNetherPiece13()
        {
        }

        public WorldGenNetherPiece13(int p_i745_1_, Random p_i745_2_, StructureBoundingBox p_i745_3_, EnumDirection p_i745_4_)
        {
            super(p_i745_1_);
            this.m = p_i745_4_;
            this.l = p_i745_3_;
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            this.a((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 2, 0, false);
            this.b((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 0, 2, false);
            this.c((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 0, 2, false);
        }

        public static WorldGenNetherPieces.WorldGenNetherPiece13 a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -2, 0, 0, 7, 9, 7, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenNetherPieces.WorldGenNetherPiece13(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 6, 7, 6, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 1, 6, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 6, 1, 6, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 5, 2, 0, 6, 6, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 5, 2, 6, 6, 6, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 0, 6, 1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 5, 0, 6, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 6, 2, 0, 6, 6, 1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 6, 2, 5, 6, 6, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 6, 0, 4, 6, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 5, 0, 4, 5, 0, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 6, 6, 4, 6, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 5, 6, 4, 5, 6, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 6, 2, 0, 6, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 5, 2, 0, 5, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 6, 6, 2, 6, 6, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 6, 5, 2, 6, 5, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);

            for (int i = 0; i <= 6; ++i)
            {
                for (int j = 0; j <= 6; ++j)
                {
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, p_a_3_);
                }
            }

            return true;
        }
    }

    public static class WorldGenNetherPiece14 extends WorldGenNetherPieces.WorldGenNetherPiece
    {
        public WorldGenNetherPiece14()
        {
        }

        public WorldGenNetherPiece14(int p_i746_1_, Random p_i746_2_, StructureBoundingBox p_i746_3_, EnumDirection p_i746_4_)
        {
            super(p_i746_1_);
            this.m = p_i746_4_;
            this.l = p_i746_3_;
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            this.c((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 6, 2, false);
        }

        public static WorldGenNetherPieces.WorldGenNetherPiece14 a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, EnumDirection p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -2, 0, 0, 7, 11, 7, p_a_6_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenNetherPieces.WorldGenNetherPiece14(p_a_5_, p_a_1_, structureboundingbox, p_a_6_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 6, 10, 6, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 1, 8, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 5, 2, 0, 6, 8, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 1, 0, 8, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 6, 2, 1, 6, 8, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 2, 6, 5, 8, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 3, 2, 0, 5, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 6, 3, 2, 6, 5, 2, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 6, 3, 4, 6, 5, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), 5, 2, 5, p_a_3_);
            this.a(p_a_1_, p_a_3_, 4, 2, 5, 4, 3, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 3, 2, 5, 3, 4, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 2, 5, 2, 5, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 2, 5, 1, 6, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 7, 1, 5, 7, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 6, 8, 2, 6, 8, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 6, 0, 4, 8, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 5, 0, 4, 5, 0, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);

            for (int i = 0; i <= 6; ++i)
            {
                for (int j = 0; j <= 6; ++j)
                {
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, p_a_3_);
                }
            }

            return true;
        }
    }

    public static class WorldGenNetherPiece15 extends WorldGenNetherPieces.WorldGenNetherPiece1
    {
        public WorldGenNetherPieces.WorldGenNetherPieceWeight b;
        public List<WorldGenNetherPieces.WorldGenNetherPieceWeight> c;
        public List<WorldGenNetherPieces.WorldGenNetherPieceWeight> d;
        public List<StructurePiece> e = Lists.<StructurePiece>newArrayList();

        public WorldGenNetherPiece15()
        {
        }

        public WorldGenNetherPiece15(Random p_i747_1_, int p_i747_2_, int p_i747_3_)
        {
            super(p_i747_1_, p_i747_2_, p_i747_3_);
            this.c = Lists.<WorldGenNetherPieces.WorldGenNetherPieceWeight>newArrayList();

            for (WorldGenNetherPieces.WorldGenNetherPieceWeight worldgennetherpieces$worldgennetherpieceweight : WorldGenNetherPieces.a)
            {
                worldgennetherpieces$worldgennetherpieceweight.c = 0;
                this.c.add(worldgennetherpieces$worldgennetherpieceweight);
            }

            this.d = Lists.<WorldGenNetherPieces.WorldGenNetherPieceWeight>newArrayList();

            for (WorldGenNetherPieces.WorldGenNetherPieceWeight worldgennetherpieces$worldgennetherpieceweight1 : WorldGenNetherPieces.b)
            {
                worldgennetherpieces$worldgennetherpieceweight1.c = 0;
                this.d.add(worldgennetherpieces$worldgennetherpieceweight1);
            }
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
        }
    }

    public static class WorldGenNetherPiece2 extends WorldGenNetherPieces.WorldGenNetherPiece
    {
        private int b;

        public WorldGenNetherPiece2()
        {
        }

        public WorldGenNetherPiece2(int p_i731_1_, Random p_i731_2_, StructureBoundingBox p_i731_3_, EnumDirection p_i731_4_)
        {
            super(p_i731_1_);
            this.m = p_i731_4_;
            this.l = p_i731_3_;
            this.b = p_i731_2_.nextInt();
        }

        public static WorldGenNetherPieces.WorldGenNetherPiece2 a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -1, -3, 0, 5, 10, 8, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenNetherPieces.WorldGenNetherPiece2(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
            this.b = p_b_1_.getInt("Seed");
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            p_a_1_.setInt("Seed", this.b);
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            Random random = new Random((long)this.b);

            for (int i = 0; i <= 4; ++i)
            {
                for (int j = 3; j <= 4; ++j)
                {
                    int k = random.nextInt(8);
                    this.a(p_a_1_, p_a_3_, i, j, 0, i, j, k, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
                }
            }

            int l = random.nextInt(8);
            this.a(p_a_1_, p_a_3_, 0, 5, 0, 0, 5, l, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            l = random.nextInt(8);
            this.a(p_a_1_, p_a_3_, 4, 5, 0, 4, 5, l, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);

            for (l = 0; l <= 4; ++l)
            {
                int i1 = random.nextInt(5);
                this.a(p_a_1_, p_a_3_, l, 2, 0, l, 2, i1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            }

            for (l = 0; l <= 4; ++l)
            {
                for (int j1 = 0; j1 <= 1; ++j1)
                {
                    int k1 = random.nextInt(3);
                    this.a(p_a_1_, p_a_3_, l, j1, 0, l, j1, k1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
                }
            }

            return true;
        }
    }

    public static class WorldGenNetherPiece3 extends WorldGenNetherPieces.WorldGenNetherPiece
    {
        public WorldGenNetherPiece3()
        {
        }

        public WorldGenNetherPiece3(int p_i732_1_, Random p_i732_2_, StructureBoundingBox p_i732_3_, EnumDirection p_i732_4_)
        {
            super(p_i732_1_);
            this.m = p_i732_4_;
            this.l = p_i732_3_;
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            this.a((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 1, 3, false);
        }

        public static WorldGenNetherPieces.WorldGenNetherPiece3 a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -1, -3, 0, 5, 10, 19, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenNetherPieces.WorldGenNetherPiece3(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 0, 3, 0, 4, 4, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 5, 0, 3, 7, 18, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 5, 0, 0, 5, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 5, 0, 4, 5, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 4, 2, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 13, 4, 2, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 0, 0, 4, 1, 3, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 0, 15, 4, 1, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);

            for (int i = 0; i <= 4; ++i)
            {
                for (int j = 0; j <= 2; ++j)
                {
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, p_a_3_);
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), i, -1, 18 - j, p_a_3_);
                }
            }

            this.a(p_a_1_, p_a_3_, 0, 1, 1, 0, 4, 1, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 3, 4, 0, 4, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 3, 14, 0, 4, 14, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 1, 17, 0, 4, 17, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 1, 1, 4, 4, 1, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 3, 4, 4, 4, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 3, 14, 4, 4, 14, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 1, 17, 4, 4, 17, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            return true;
        }
    }

    public static class WorldGenNetherPiece4 extends WorldGenNetherPieces.WorldGenNetherPiece
    {
        public WorldGenNetherPiece4()
        {
        }

        public WorldGenNetherPiece4(int p_i733_1_, Random p_i733_2_, StructureBoundingBox p_i733_3_, EnumDirection p_i733_4_)
        {
            super(p_i733_1_);
            this.m = p_i733_4_;
            this.l = p_i733_3_;
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            this.a((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 1, 0, true);
        }

        public static WorldGenNetherPieces.WorldGenNetherPiece4 a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -1, -7, 0, 5, 14, 10, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenNetherPieces.WorldGenNetherPiece4(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            int i = this.a(Blocks.NETHER_BRICK_STAIRS, 2);

            for (int j = 0; j <= 9; ++j)
            {
                int k = Math.max(1, 7 - j);
                int l = Math.min(Math.max(k + 5, 14 - j), 13);
                int i1 = j;
                this.a(p_a_1_, p_a_3_, 0, 0, j, 4, k, j, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 1, k + 1, j, 3, l - 1, j, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);

                if (j <= 6)
                {
                    this.a(p_a_1_, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(i), 1, k + 1, j, p_a_3_);
                    this.a(p_a_1_, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(i), 2, k + 1, j, p_a_3_);
                    this.a(p_a_1_, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(i), 3, k + 1, j, p_a_3_);
                }

                this.a(p_a_1_, p_a_3_, 0, l, j, 4, l, j, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 0, k + 1, j, 0, l - 1, j, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 4, k + 1, j, 4, l - 1, j, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);

                if ((j & 1) == 0)
                {
                    this.a(p_a_1_, p_a_3_, 0, k + 2, j, 0, k + 3, j, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
                    this.a(p_a_1_, p_a_3_, 4, k + 2, j, 4, k + 3, j, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
                }

                for (int j1 = 0; j1 <= 4; ++j1)
                {
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), j1, -1, i1, p_a_3_);
                }
            }

            return true;
        }
    }

    public static class WorldGenNetherPiece5 extends WorldGenNetherPieces.WorldGenNetherPiece
    {
        public WorldGenNetherPiece5()
        {
        }

        public WorldGenNetherPiece5(int p_i734_1_, Random p_i734_2_, StructureBoundingBox p_i734_3_, EnumDirection p_i734_4_)
        {
            super(p_i734_1_);
            this.m = p_i734_4_;
            this.l = p_i734_3_;
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            byte b0 = 1;

            if (this.m == EnumDirection.WEST || this.m == EnumDirection.NORTH)
            {
                b0 = 5;
            }

            this.b((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 0, b0, p_a_3_.nextInt(8) > 0);
            this.c((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 0, b0, p_a_3_.nextInt(8) > 0);
        }

        public static WorldGenNetherPieces.WorldGenNetherPiece5 a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -3, 0, 0, 9, 7, 9, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenNetherPieces.WorldGenNetherPiece5(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 0, 0, 0, 8, 1, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 8, 5, 8, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 6, 0, 8, 6, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 2, 5, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 6, 2, 0, 8, 5, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 3, 0, 1, 4, 0, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 7, 3, 0, 7, 4, 0, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 4, 8, 2, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 1, 4, 2, 2, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 6, 1, 4, 7, 2, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 3, 8, 8, 3, 8, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 3, 6, 0, 3, 7, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 8, 3, 6, 8, 3, 7, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 3, 4, 0, 5, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 8, 3, 4, 8, 5, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 3, 5, 2, 5, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 6, 3, 5, 7, 5, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 4, 5, 1, 5, 5, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 7, 4, 5, 7, 5, 5, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);

            for (int i = 0; i <= 5; ++i)
            {
                for (int j = 0; j <= 8; ++j)
                {
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), j, -1, i, p_a_3_);
                }
            }

            return true;
        }
    }

    public static class WorldGenNetherPiece6 extends WorldGenNetherPieces.WorldGenNetherPiece
    {
        public WorldGenNetherPiece6()
        {
        }

        public WorldGenNetherPiece6(int p_i735_1_, Random p_i735_2_, StructureBoundingBox p_i735_3_, EnumDirection p_i735_4_)
        {
            super(p_i735_1_);
            this.m = p_i735_4_;
            this.l = p_i735_3_;
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            this.a((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 5, 3, true);
        }

        public static WorldGenNetherPieces.WorldGenNetherPiece6 a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -5, -3, 0, 13, 14, 13, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenNetherPieces.WorldGenNetherPiece6(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 5, 0, 12, 13, 12, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 5, 8, 0, 7, 8, 0, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);

            for (int i = 1; i <= 11; i += 2)
            {
                this.a(p_a_1_, p_a_3_, i, 10, 0, i, 11, 0, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, i, 10, 12, i, 11, 12, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 0, 10, i, 0, 11, i, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 12, 10, i, 12, 11, i, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
                this.a(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), i, 13, 0, p_a_3_);
                this.a(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), i, 13, 12, p_a_3_);
                this.a(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), 0, 13, i, p_a_3_);
                this.a(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), 12, 13, i, p_a_3_);
                this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), i + 1, 13, 0, p_a_3_);
                this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), i + 1, 13, 12, p_a_3_);
                this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), 0, 13, i + 1, p_a_3_);
                this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), 12, 13, i + 1, p_a_3_);
            }

            this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), 0, 13, 0, p_a_3_);
            this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), 0, 13, 12, p_a_3_);
            this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), 0, 13, 0, p_a_3_);
            this.a(p_a_1_, Blocks.NETHER_BRICK_FENCE.getBlockData(), 12, 13, 0, p_a_3_);

            for (int k = 3; k <= 9; k += 2)
            {
                this.a(p_a_1_, p_a_3_, 1, 7, k, 1, 8, k, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 11, 7, k, 11, 8, k, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            }

            this.a(p_a_1_, p_a_3_, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);

            for (int l = 4; l <= 8; ++l)
            {
                for (int j = 0; j <= 2; ++j)
                {
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), l, -1, j, p_a_3_);
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), l, -1, 12 - j, p_a_3_);
                }
            }

            for (int i1 = 0; i1 <= 2; ++i1)
            {
                for (int j1 = 4; j1 <= 8; ++j1)
                {
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), i1, -1, j1, p_a_3_);
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), 12 - i1, -1, j1, p_a_3_);
                }
            }

            this.a(p_a_1_, p_a_3_, 5, 5, 5, 7, 5, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 6, 1, 6, 6, 4, 6, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), 6, 0, 6, p_a_3_);
            this.a(p_a_1_, Blocks.FLOWING_LAVA.getBlockData(), 6, 5, 6, p_a_3_);
            BlockPosition blockposition = new BlockPosition(this.a(6, 6), this.d(5), this.b(6, 6));

            if (p_a_3_.b((BaseBlockPosition)blockposition))
            {
                p_a_1_.a((Block)Blocks.FLOWING_LAVA, (BlockPosition)blockposition, (Random)p_a_2_);
            }

            return true;
        }
    }

    public static class WorldGenNetherPiece7 extends WorldGenNetherPieces.WorldGenNetherPiece
    {
        public WorldGenNetherPiece7()
        {
        }

        public WorldGenNetherPiece7(int p_i736_1_, Random p_i736_2_, StructureBoundingBox p_i736_3_, EnumDirection p_i736_4_)
        {
            super(p_i736_1_);
            this.m = p_i736_4_;
            this.l = p_i736_3_;
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            this.a((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 1, 0, true);
            this.b((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 0, 1, true);
            this.c((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 0, 1, true);
        }

        public static WorldGenNetherPieces.WorldGenNetherPiece7 a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -1, 0, 0, 5, 7, 5, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenNetherPieces.WorldGenNetherPiece7(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 4, 5, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 0, 5, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 2, 0, 4, 5, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 4, 0, 5, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 2, 4, 4, 5, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);

            for (int i = 0; i <= 4; ++i)
            {
                for (int j = 0; j <= 4; ++j)
                {
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, p_a_3_);
                }
            }

            return true;
        }
    }

    public static class WorldGenNetherPiece8 extends WorldGenNetherPieces.WorldGenNetherPiece
    {
        private boolean b;

        public WorldGenNetherPiece8()
        {
        }

        public WorldGenNetherPiece8(int p_i737_1_, Random p_i737_2_, StructureBoundingBox p_i737_3_, EnumDirection p_i737_4_)
        {
            super(p_i737_1_);
            this.m = p_i737_4_;
            this.l = p_i737_3_;
            this.b = p_i737_2_.nextInt(3) == 0;
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
            this.b = p_b_1_.getBoolean("Chest");
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            p_a_1_.setBoolean("Chest", this.b);
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            this.b((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 0, 1, true);
        }

        public static WorldGenNetherPieces.WorldGenNetherPiece8 a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -1, 0, 0, 5, 7, 5, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenNetherPieces.WorldGenNetherPiece8(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 4, 5, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 2, 0, 4, 5, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 3, 1, 4, 4, 1, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 3, 3, 4, 4, 3, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 0, 5, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 4, 3, 5, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 1, 3, 4, 1, 4, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 3, 3, 4, 3, 4, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);

            if (this.b && p_a_3_.b((BaseBlockPosition)(new BlockPosition(this.a(3, 3), this.d(2), this.b(3, 3)))))
            {
                this.b = false;
                this.a(p_a_1_, p_a_3_, p_a_2_, 3, 2, 3, a, 2 + p_a_2_.nextInt(4));
            }

            this.a(p_a_1_, p_a_3_, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);

            for (int i = 0; i <= 4; ++i)
            {
                for (int j = 0; j <= 4; ++j)
                {
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, p_a_3_);
                }
            }

            return true;
        }
    }

    public static class WorldGenNetherPiece9 extends WorldGenNetherPieces.WorldGenNetherPiece
    {
        public WorldGenNetherPiece9()
        {
        }

        public WorldGenNetherPiece9(int p_i738_1_, Random p_i738_2_, StructureBoundingBox p_i738_3_, EnumDirection p_i738_4_)
        {
            super(p_i738_1_);
            this.m = p_i738_4_;
            this.l = p_i738_3_;
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            this.a((WorldGenNetherPieces.WorldGenNetherPiece15)p_a_1_, p_a_2_, p_a_3_, 1, 0, true);
        }

        public static WorldGenNetherPieces.WorldGenNetherPiece9 a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(p_a_2_, p_a_3_, p_a_4_, -1, 0, 0, 5, 7, 5, p_a_5_);
            return a(structureboundingbox) && StructurePiece.a(p_a_0_, structureboundingbox) == null ? new WorldGenNetherPieces.WorldGenNetherPiece9(p_a_6_, p_a_1_, structureboundingbox, p_a_5_) : null;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 4, 5, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 0, 5, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 2, 0, 4, 5, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 3, 1, 0, 4, 1, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 3, 3, 0, 4, 3, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 3, 1, 4, 4, 1, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 4, 3, 3, 4, 4, 3, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
            this.a(p_a_1_, p_a_3_, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);

            for (int i = 0; i <= 4; ++i)
            {
                for (int j = 0; j <= 4; ++j)
                {
                    this.b(p_a_1_, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, p_a_3_);
                }
            }

            return true;
        }
    }

    static class WorldGenNetherPieceWeight
    {
        public Class <? extends WorldGenNetherPieces.WorldGenNetherPiece > a;
        public final int b;
        public int c;
        public int d;
        public boolean e;

        public WorldGenNetherPieceWeight(Class <? extends WorldGenNetherPieces.WorldGenNetherPiece > p_i743_1_, int p_i743_2_, int p_i743_3_, boolean p_i743_4_)
        {
            this.a = p_i743_1_;
            this.b = p_i743_2_;
            this.d = p_i743_3_;
            this.e = p_i743_4_;
        }

        public WorldGenNetherPieceWeight(Class <? extends WorldGenNetherPieces.WorldGenNetherPiece > p_i744_1_, int p_i744_2_, int p_i744_3_)
        {
            this(p_i744_1_, p_i744_2_, p_i744_3_, false);
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
}
