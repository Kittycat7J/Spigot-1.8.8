package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;

public class WorldGenMineshaftPieces
{
    private static final List<StructurePieceTreasure> a = Lists.newArrayList(new StructurePieceTreasure[] {new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 1, 3, 5), new StructurePieceTreasure(Items.REDSTONE, 0, 4, 9, 5), new StructurePieceTreasure(Items.DYE, EnumColor.BLUE.getInvColorIndex(), 4, 9, 5), new StructurePieceTreasure(Items.DIAMOND, 0, 1, 2, 3), new StructurePieceTreasure(Items.COAL, 0, 3, 8, 10), new StructurePieceTreasure(Items.BREAD, 0, 1, 3, 15), new StructurePieceTreasure(Items.IRON_PICKAXE, 0, 1, 1, 1), new StructurePieceTreasure(Item.getItemOf(Blocks.RAIL), 0, 4, 8, 1), new StructurePieceTreasure(Items.MELON_SEEDS, 0, 2, 4, 10), new StructurePieceTreasure(Items.PUMPKIN_SEEDS, 0, 2, 4, 10), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 3), new StructurePieceTreasure(Items.IRON_HORSE_ARMOR, 0, 1, 1, 1)});

    public static void a()
    {
        WorldGenFactory.a(WorldGenMineshaftPieces.WorldGenMineshaftCorridor.class, "MSCorridor");
        WorldGenFactory.a(WorldGenMineshaftPieces.WorldGenMineshaftCross.class, "MSCrossing");
        WorldGenFactory.a(WorldGenMineshaftPieces.WorldGenMineshaftRoom.class, "MSRoom");
        WorldGenFactory.a(WorldGenMineshaftPieces.WorldGenMineshaftStairs.class, "MSStairs");
    }

    private static StructurePiece a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_, int p_a_6_)
    {
        int i = p_a_1_.nextInt(100);

        if (i >= 80)
        {
            StructureBoundingBox structureboundingbox = WorldGenMineshaftPieces.WorldGenMineshaftCross.a(p_a_0_, p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_);

            if (structureboundingbox != null)
            {
                return new WorldGenMineshaftPieces.WorldGenMineshaftCross(p_a_6_, p_a_1_, structureboundingbox, p_a_5_);
            }
        }
        else if (i >= 70)
        {
            StructureBoundingBox structureboundingbox1 = WorldGenMineshaftPieces.WorldGenMineshaftStairs.a(p_a_0_, p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_);

            if (structureboundingbox1 != null)
            {
                return new WorldGenMineshaftPieces.WorldGenMineshaftStairs(p_a_6_, p_a_1_, structureboundingbox1, p_a_5_);
            }
        }
        else
        {
            StructureBoundingBox structureboundingbox2 = WorldGenMineshaftPieces.WorldGenMineshaftCorridor.a(p_a_0_, p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_);

            if (structureboundingbox2 != null)
            {
                return new WorldGenMineshaftPieces.WorldGenMineshaftCorridor(p_a_6_, p_a_1_, structureboundingbox2, p_a_5_);
            }
        }

        return null;
    }

    private static StructurePiece b(StructurePiece p_b_0_, List<StructurePiece> p_b_1_, Random p_b_2_, int p_b_3_, int p_b_4_, int p_b_5_, EnumDirection p_b_6_, int p_b_7_)
    {
        if (p_b_7_ > 8)
        {
            return null;
        }
        else if (Math.abs(p_b_3_ - p_b_0_.c().a) <= 80 && Math.abs(p_b_5_ - p_b_0_.c().c) <= 80)
        {
            StructurePiece structurepiece = a(p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_6_, p_b_7_ + 1);

            if (structurepiece != null)
            {
                p_b_1_.add(structurepiece);
                structurepiece.a(p_b_0_, p_b_1_, p_b_2_);
            }

            return structurepiece;
        }
        else
        {
            return null;
        }
    }

    public static class WorldGenMineshaftCorridor extends StructurePiece
    {
        private boolean a;
        private boolean b;
        private boolean c;
        private int d;

        public WorldGenMineshaftCorridor()
        {
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            p_a_1_.setBoolean("hr", this.a);
            p_a_1_.setBoolean("sc", this.b);
            p_a_1_.setBoolean("hps", this.c);
            p_a_1_.setInt("Num", this.d);
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            this.a = p_b_1_.getBoolean("hr");
            this.b = p_b_1_.getBoolean("sc");
            this.c = p_b_1_.getBoolean("hps");
            this.d = p_b_1_.getInt("Num");
        }

        public WorldGenMineshaftCorridor(int p_i723_1_, Random p_i723_2_, StructureBoundingBox p_i723_3_, EnumDirection p_i723_4_)
        {
            super(p_i723_1_);
            this.m = p_i723_4_;
            this.l = p_i723_3_;
            this.a = p_i723_2_.nextInt(3) == 0;
            this.b = !this.a && p_i723_2_.nextInt(23) == 0;

            if (this.m != EnumDirection.NORTH && this.m != EnumDirection.SOUTH)
            {
                this.d = p_i723_3_.c() / 5;
            }
            else
            {
                this.d = p_i723_3_.e() / 5;
            }
        }

        public static StructureBoundingBox a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_)
        {
            StructureBoundingBox structureboundingbox = new StructureBoundingBox(p_a_2_, p_a_3_, p_a_4_, p_a_2_, p_a_3_ + 2, p_a_4_);
            int i;

            for (i = p_a_1_.nextInt(3) + 2; i > 0; --i)
            {
                int j = i * 5;

                switch (p_a_5_)
                {
                    case NORTH:
                        structureboundingbox.d = p_a_2_ + 2;
                        structureboundingbox.c = p_a_4_ - (j - 1);
                        break;

                    case SOUTH:
                        structureboundingbox.d = p_a_2_ + 2;
                        structureboundingbox.f = p_a_4_ + (j - 1);
                        break;

                    case WEST:
                        structureboundingbox.a = p_a_2_ - (j - 1);
                        structureboundingbox.f = p_a_4_ + 2;
                        break;

                    case EAST:
                        structureboundingbox.d = p_a_2_ + (j - 1);
                        structureboundingbox.f = p_a_4_ + 2;
                }

                if (StructurePiece.a(p_a_0_, structureboundingbox) == null)
                {
                    break;
                }
            }

            return i > 0 ? structureboundingbox : null;
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            int i = this.d();
            int j = p_a_3_.nextInt(4);

            if (this.m != null)
            {
                switch (this.m)
                {
                    case NORTH:
                        if (j <= 1)
                        {
                            WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a, this.l.b - 1 + p_a_3_.nextInt(3), this.l.c - 1, this.m, i);
                        }
                        else if (j == 2)
                        {
                            WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.b - 1 + p_a_3_.nextInt(3), this.l.c, EnumDirection.WEST, i);
                        }
                        else
                        {
                            WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.d + 1, this.l.b - 1 + p_a_3_.nextInt(3), this.l.c, EnumDirection.EAST, i);
                        }

                        break;

                    case SOUTH:
                        if (j <= 1)
                        {
                            WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a, this.l.b - 1 + p_a_3_.nextInt(3), this.l.f + 1, this.m, i);
                        }
                        else if (j == 2)
                        {
                            WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.b - 1 + p_a_3_.nextInt(3), this.l.f - 3, EnumDirection.WEST, i);
                        }
                        else
                        {
                            WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.d + 1, this.l.b - 1 + p_a_3_.nextInt(3), this.l.f - 3, EnumDirection.EAST, i);
                        }

                        break;

                    case WEST:
                        if (j <= 1)
                        {
                            WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.b - 1 + p_a_3_.nextInt(3), this.l.c, this.m, i);
                        }
                        else if (j == 2)
                        {
                            WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a, this.l.b - 1 + p_a_3_.nextInt(3), this.l.c - 1, EnumDirection.NORTH, i);
                        }
                        else
                        {
                            WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a, this.l.b - 1 + p_a_3_.nextInt(3), this.l.f + 1, EnumDirection.SOUTH, i);
                        }

                        break;

                    case EAST:
                        if (j <= 1)
                        {
                            WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.d + 1, this.l.b - 1 + p_a_3_.nextInt(3), this.l.c, this.m, i);
                        }
                        else if (j == 2)
                        {
                            WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.d - 3, this.l.b - 1 + p_a_3_.nextInt(3), this.l.c - 1, EnumDirection.NORTH, i);
                        }
                        else
                        {
                            WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.d - 3, this.l.b - 1 + p_a_3_.nextInt(3), this.l.f + 1, EnumDirection.SOUTH, i);
                        }
                }
            }

            if (i < 8)
            {
                if (this.m != EnumDirection.NORTH && this.m != EnumDirection.SOUTH)
                {
                    for (int i1 = this.l.a + 3; i1 + 3 <= this.l.d; i1 += 5)
                    {
                        int j1 = p_a_3_.nextInt(5);

                        if (j1 == 0)
                        {
                            WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, i1, this.l.b, this.l.c - 1, EnumDirection.NORTH, i + 1);
                        }
                        else if (j1 == 1)
                        {
                            WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, i1, this.l.b, this.l.f + 1, EnumDirection.SOUTH, i + 1);
                        }
                    }
                }
                else
                {
                    for (int k = this.l.c + 3; k + 3 <= this.l.f; k += 5)
                    {
                        int l = p_a_3_.nextInt(5);

                        if (l == 0)
                        {
                            WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.b, k, EnumDirection.WEST, i + 1);
                        }
                        else if (l == 1)
                        {
                            WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.d + 1, this.l.b, k, EnumDirection.EAST, i + 1);
                        }
                    }
                }
            }
        }

        protected boolean a(World p_a_1_, StructureBoundingBox p_a_2_, Random p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, List<StructurePieceTreasure> p_a_7_, int p_a_8_)
        {
            BlockPosition blockposition = new BlockPosition(this.a(p_a_4_, p_a_6_), this.d(p_a_5_), this.b(p_a_4_, p_a_6_));

            if (p_a_2_.b((BaseBlockPosition)blockposition) && p_a_1_.getType(blockposition).getBlock().getMaterial() == Material.AIR)
            {
                int i = p_a_3_.nextBoolean() ? 1 : 0;
                p_a_1_.setTypeAndData(blockposition, Blocks.RAIL.fromLegacyData(this.a(Blocks.RAIL, i)), 2);
                EntityMinecartChest entityminecartchest = new EntityMinecartChest(p_a_1_, (double)((float)blockposition.getX() + 0.5F), (double)((float)blockposition.getY() + 0.5F), (double)((float)blockposition.getZ() + 0.5F));
                StructurePieceTreasure.a(p_a_3_, p_a_7_, (IInventory)entityminecartchest, p_a_8_);
                p_a_1_.addEntity(entityminecartchest);
                return true;
            }
            else
            {
                return false;
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
                boolean flag = false;
                boolean flag1 = true;
                boolean flag2 = false;
                boolean flag3 = true;
                int i = this.d * 5 - 1;
                this.a(p_a_1_, p_a_3_, 0, 0, 0, 2, 1, i, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, p_a_2_, 0.8F, 0, 2, 0, 2, 2, i, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);

                if (this.b)
                {
                    this.a(p_a_1_, p_a_3_, p_a_2_, 0.6F, 0, 0, 0, 2, 1, i, Blocks.WEB.getBlockData(), Blocks.AIR.getBlockData(), false);
                }

                for (int j = 0; j < this.d; ++j)
                {
                    int k = 2 + j * 5;
                    this.a(p_a_1_, p_a_3_, 0, 0, k, 0, 1, k, Blocks.FENCE.getBlockData(), Blocks.AIR.getBlockData(), false);
                    this.a(p_a_1_, p_a_3_, 2, 0, k, 2, 1, k, Blocks.FENCE.getBlockData(), Blocks.AIR.getBlockData(), false);

                    if (p_a_2_.nextInt(4) == 0)
                    {
                        this.a(p_a_1_, p_a_3_, 0, 2, k, 0, 2, k, Blocks.PLANKS.getBlockData(), Blocks.AIR.getBlockData(), false);
                        this.a(p_a_1_, p_a_3_, 2, 2, k, 2, 2, k, Blocks.PLANKS.getBlockData(), Blocks.AIR.getBlockData(), false);
                    }
                    else
                    {
                        this.a(p_a_1_, p_a_3_, 0, 2, k, 2, 2, k, Blocks.PLANKS.getBlockData(), Blocks.AIR.getBlockData(), false);
                    }

                    this.a(p_a_1_, p_a_3_, p_a_2_, 0.1F, 0, 2, k - 1, Blocks.WEB.getBlockData());
                    this.a(p_a_1_, p_a_3_, p_a_2_, 0.1F, 2, 2, k - 1, Blocks.WEB.getBlockData());
                    this.a(p_a_1_, p_a_3_, p_a_2_, 0.1F, 0, 2, k + 1, Blocks.WEB.getBlockData());
                    this.a(p_a_1_, p_a_3_, p_a_2_, 0.1F, 2, 2, k + 1, Blocks.WEB.getBlockData());
                    this.a(p_a_1_, p_a_3_, p_a_2_, 0.05F, 0, 2, k - 2, Blocks.WEB.getBlockData());
                    this.a(p_a_1_, p_a_3_, p_a_2_, 0.05F, 2, 2, k - 2, Blocks.WEB.getBlockData());
                    this.a(p_a_1_, p_a_3_, p_a_2_, 0.05F, 0, 2, k + 2, Blocks.WEB.getBlockData());
                    this.a(p_a_1_, p_a_3_, p_a_2_, 0.05F, 2, 2, k + 2, Blocks.WEB.getBlockData());
                    this.a(p_a_1_, p_a_3_, p_a_2_, 0.05F, 1, 2, k - 1, Blocks.TORCH.fromLegacyData(EnumDirection.UP.a()));
                    this.a(p_a_1_, p_a_3_, p_a_2_, 0.05F, 1, 2, k + 1, Blocks.TORCH.fromLegacyData(EnumDirection.UP.a()));

                    if (p_a_2_.nextInt(100) == 0)
                    {
                        this.a(p_a_1_, p_a_3_, p_a_2_, 2, 0, k - 1, StructurePieceTreasure.a(WorldGenMineshaftPieces.a, new StructurePieceTreasure[] {Items.ENCHANTED_BOOK.b(p_a_2_)}), 3 + p_a_2_.nextInt(4));
                    }

                    if (p_a_2_.nextInt(100) == 0)
                    {
                        this.a(p_a_1_, p_a_3_, p_a_2_, 0, 0, k + 1, StructurePieceTreasure.a(WorldGenMineshaftPieces.a, new StructurePieceTreasure[] {Items.ENCHANTED_BOOK.b(p_a_2_)}), 3 + p_a_2_.nextInt(4));
                    }

                    if (this.b && !this.c)
                    {
                        int l = this.d(0);
                        int i1 = k - 1 + p_a_2_.nextInt(3);
                        int j1 = this.a(1, i1);
                        i1 = this.b(1, i1);
                        BlockPosition blockposition = new BlockPosition(j1, l, i1);

                        if (p_a_3_.b((BaseBlockPosition)blockposition))
                        {
                            this.c = true;
                            p_a_1_.setTypeAndData(blockposition, Blocks.MOB_SPAWNER.getBlockData(), 2);
                            TileEntity tileentity = p_a_1_.getTileEntity(blockposition);

                            if (tileentity instanceof TileEntityMobSpawner)
                            {
                                ((TileEntityMobSpawner)tileentity).getSpawner().setMobName("CaveSpider");
                            }
                        }
                    }
                }

                for (int k1 = 0; k1 <= 2; ++k1)
                {
                    for (int i2 = 0; i2 <= i; ++i2)
                    {
                        byte b0 = -1;
                        IBlockData iblockdata1 = this.a(p_a_1_, k1, b0, i2, p_a_3_);

                        if (iblockdata1.getBlock().getMaterial() == Material.AIR)
                        {
                            byte b1 = -1;
                            this.a(p_a_1_, Blocks.PLANKS.getBlockData(), k1, b1, i2, p_a_3_);
                        }
                    }
                }

                if (this.a)
                {
                    for (int l1 = 0; l1 <= i; ++l1)
                    {
                        IBlockData iblockdata = this.a(p_a_1_, 1, -1, l1, p_a_3_);

                        if (iblockdata.getBlock().getMaterial() != Material.AIR && iblockdata.getBlock().o())
                        {
                            this.a(p_a_1_, p_a_3_, p_a_2_, 0.7F, 1, 0, l1, Blocks.RAIL.fromLegacyData(this.a(Blocks.RAIL, 0)));
                        }
                    }
                }

                return true;
            }
        }
    }

    public static class WorldGenMineshaftCross extends StructurePiece
    {
        private EnumDirection a;
        private boolean b;

        public WorldGenMineshaftCross()
        {
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            p_a_1_.setBoolean("tf", this.b);
            p_a_1_.setInt("D", this.a.b());
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            this.b = p_b_1_.getBoolean("tf");
            this.a = EnumDirection.fromType2(p_b_1_.getInt("D"));
        }

        public WorldGenMineshaftCross(int p_i724_1_, Random p_i724_2_, StructureBoundingBox p_i724_3_, EnumDirection p_i724_4_)
        {
            super(p_i724_1_);
            this.a = p_i724_4_;
            this.l = p_i724_3_;
            this.b = p_i724_3_.d() > 3;
        }

        public static StructureBoundingBox a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_)
        {
            StructureBoundingBox structureboundingbox = new StructureBoundingBox(p_a_2_, p_a_3_, p_a_4_, p_a_2_, p_a_3_ + 2, p_a_4_);

            if (p_a_1_.nextInt(4) == 0)
            {
                structureboundingbox.e += 4;
            }

            switch (p_a_5_)
            {
                case NORTH:
                    structureboundingbox.a = p_a_2_ - 1;
                    structureboundingbox.d = p_a_2_ + 3;
                    structureboundingbox.c = p_a_4_ - 4;
                    break;

                case SOUTH:
                    structureboundingbox.a = p_a_2_ - 1;
                    structureboundingbox.d = p_a_2_ + 3;
                    structureboundingbox.f = p_a_4_ + 4;
                    break;

                case WEST:
                    structureboundingbox.a = p_a_2_ - 4;
                    structureboundingbox.c = p_a_4_ - 1;
                    structureboundingbox.f = p_a_4_ + 3;
                    break;

                case EAST:
                    structureboundingbox.d = p_a_2_ + 4;
                    structureboundingbox.c = p_a_4_ - 1;
                    structureboundingbox.f = p_a_4_ + 3;
            }

            return StructurePiece.a(p_a_0_, structureboundingbox) != null ? null : structureboundingbox;
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            int i = this.d();

            switch (this.a)
            {
                case NORTH:
                    WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a + 1, this.l.b, this.l.c - 1, EnumDirection.NORTH, i);
                    WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.b, this.l.c + 1, EnumDirection.WEST, i);
                    WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.d + 1, this.l.b, this.l.c + 1, EnumDirection.EAST, i);
                    break;

                case SOUTH:
                    WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a + 1, this.l.b, this.l.f + 1, EnumDirection.SOUTH, i);
                    WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.b, this.l.c + 1, EnumDirection.WEST, i);
                    WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.d + 1, this.l.b, this.l.c + 1, EnumDirection.EAST, i);
                    break;

                case WEST:
                    WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a + 1, this.l.b, this.l.c - 1, EnumDirection.NORTH, i);
                    WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a + 1, this.l.b, this.l.f + 1, EnumDirection.SOUTH, i);
                    WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.b, this.l.c + 1, EnumDirection.WEST, i);
                    break;

                case EAST:
                    WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a + 1, this.l.b, this.l.c - 1, EnumDirection.NORTH, i);
                    WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a + 1, this.l.b, this.l.f + 1, EnumDirection.SOUTH, i);
                    WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.d + 1, this.l.b, this.l.c + 1, EnumDirection.EAST, i);
            }

            if (this.b)
            {
                if (p_a_3_.nextBoolean())
                {
                    WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a + 1, this.l.b + 3 + 1, this.l.c - 1, EnumDirection.NORTH, i);
                }

                if (p_a_3_.nextBoolean())
                {
                    WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.b + 3 + 1, this.l.c + 1, EnumDirection.WEST, i);
                }

                if (p_a_3_.nextBoolean())
                {
                    WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.d + 1, this.l.b + 3 + 1, this.l.c + 1, EnumDirection.EAST, i);
                }

                if (p_a_3_.nextBoolean())
                {
                    WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a + 1, this.l.b + 3 + 1, this.l.f + 1, EnumDirection.SOUTH, i);
                }
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
                if (this.b)
                {
                    this.a(p_a_1_, p_a_3_, this.l.a + 1, this.l.b, this.l.c, this.l.d - 1, this.l.b + 3 - 1, this.l.f, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                    this.a(p_a_1_, p_a_3_, this.l.a, this.l.b, this.l.c + 1, this.l.d, this.l.b + 3 - 1, this.l.f - 1, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                    this.a(p_a_1_, p_a_3_, this.l.a + 1, this.l.e - 2, this.l.c, this.l.d - 1, this.l.e, this.l.f, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                    this.a(p_a_1_, p_a_3_, this.l.a, this.l.e - 2, this.l.c + 1, this.l.d, this.l.e, this.l.f - 1, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                    this.a(p_a_1_, p_a_3_, this.l.a + 1, this.l.b + 3, this.l.c + 1, this.l.d - 1, this.l.b + 3, this.l.f - 1, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                }
                else
                {
                    this.a(p_a_1_, p_a_3_, this.l.a + 1, this.l.b, this.l.c, this.l.d - 1, this.l.e, this.l.f, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                    this.a(p_a_1_, p_a_3_, this.l.a, this.l.b, this.l.c + 1, this.l.d, this.l.e, this.l.f - 1, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                }

                this.a(p_a_1_, p_a_3_, this.l.a + 1, this.l.b, this.l.c + 1, this.l.a + 1, this.l.e, this.l.c + 1, Blocks.PLANKS.getBlockData(), Blocks.AIR.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, this.l.a + 1, this.l.b, this.l.f - 1, this.l.a + 1, this.l.e, this.l.f - 1, Blocks.PLANKS.getBlockData(), Blocks.AIR.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, this.l.d - 1, this.l.b, this.l.c + 1, this.l.d - 1, this.l.e, this.l.c + 1, Blocks.PLANKS.getBlockData(), Blocks.AIR.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, this.l.d - 1, this.l.b, this.l.f - 1, this.l.d - 1, this.l.e, this.l.f - 1, Blocks.PLANKS.getBlockData(), Blocks.AIR.getBlockData(), false);

                for (int i = this.l.a; i <= this.l.d; ++i)
                {
                    for (int j = this.l.c; j <= this.l.f; ++j)
                    {
                        if (this.a(p_a_1_, i, this.l.b - 1, j, p_a_3_).getBlock().getMaterial() == Material.AIR)
                        {
                            this.a(p_a_1_, Blocks.PLANKS.getBlockData(), i, this.l.b - 1, j, p_a_3_);
                        }
                    }
                }

                return true;
            }
        }
    }

    public static class WorldGenMineshaftRoom extends StructurePiece
    {
        private List<StructureBoundingBox> a = Lists.<StructureBoundingBox>newLinkedList();

        public WorldGenMineshaftRoom()
        {
        }

        public WorldGenMineshaftRoom(int p_i725_1_, Random p_i725_2_, int p_i725_3_, int p_i725_4_)
        {
            super(p_i725_1_);
            this.l = new StructureBoundingBox(p_i725_3_, 50, p_i725_4_, p_i725_3_ + 7 + p_i725_2_.nextInt(6), 54 + p_i725_2_.nextInt(6), p_i725_4_ + 7 + p_i725_2_.nextInt(6));
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            int i = this.d();
            int j = this.l.d() - 3 - 1;

            if (j <= 0)
            {
                j = 1;
            }

            int k;

            for (k = 0; k < this.l.c(); k = k + 4)
            {
                k = k + p_a_3_.nextInt(this.l.c());

                if (k + 3 > this.l.c())
                {
                    break;
                }

                StructurePiece structurepiece = WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a + k, this.l.b + p_a_3_.nextInt(j) + 1, this.l.c - 1, EnumDirection.NORTH, i);

                if (structurepiece != null)
                {
                    StructureBoundingBox structureboundingbox = structurepiece.c();
                    this.a.add(new StructureBoundingBox(structureboundingbox.a, structureboundingbox.b, this.l.c, structureboundingbox.d, structureboundingbox.e, this.l.c + 1));
                }
            }

            for (k = 0; k < this.l.c(); k = k + 4)
            {
                k = k + p_a_3_.nextInt(this.l.c());

                if (k + 3 > this.l.c())
                {
                    break;
                }

                StructurePiece structurepiece1 = WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a + k, this.l.b + p_a_3_.nextInt(j) + 1, this.l.f + 1, EnumDirection.SOUTH, i);

                if (structurepiece1 != null)
                {
                    StructureBoundingBox structureboundingbox1 = structurepiece1.c();
                    this.a.add(new StructureBoundingBox(structureboundingbox1.a, structureboundingbox1.b, this.l.f - 1, structureboundingbox1.d, structureboundingbox1.e, this.l.f));
                }
            }

            for (k = 0; k < this.l.e(); k = k + 4)
            {
                k = k + p_a_3_.nextInt(this.l.e());

                if (k + 3 > this.l.e())
                {
                    break;
                }

                StructurePiece structurepiece2 = WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.b + p_a_3_.nextInt(j) + 1, this.l.c + k, EnumDirection.WEST, i);

                if (structurepiece2 != null)
                {
                    StructureBoundingBox structureboundingbox2 = structurepiece2.c();
                    this.a.add(new StructureBoundingBox(this.l.a, structureboundingbox2.b, structureboundingbox2.c, this.l.a + 1, structureboundingbox2.e, structureboundingbox2.f));
                }
            }

            for (k = 0; k < this.l.e(); k = k + 4)
            {
                k = k + p_a_3_.nextInt(this.l.e());

                if (k + 3 > this.l.e())
                {
                    break;
                }

                StructurePiece structurepiece3 = WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.d + 1, this.l.b + p_a_3_.nextInt(j) + 1, this.l.c + k, EnumDirection.EAST, i);

                if (structurepiece3 != null)
                {
                    StructureBoundingBox structureboundingbox3 = structurepiece3.c();
                    this.a.add(new StructureBoundingBox(this.l.d - 1, structureboundingbox3.b, structureboundingbox3.c, this.l.d, structureboundingbox3.e, structureboundingbox3.f));
                }
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
                this.a(p_a_1_, p_a_3_, this.l.a, this.l.b, this.l.c, this.l.d, this.l.b, this.l.f, Blocks.DIRT.getBlockData(), Blocks.AIR.getBlockData(), true);
                this.a(p_a_1_, p_a_3_, this.l.a, this.l.b + 1, this.l.c, this.l.d, Math.min(this.l.b + 3, this.l.e), this.l.f, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);

                for (StructureBoundingBox structureboundingbox : this.a)
                {
                    this.a(p_a_1_, p_a_3_, structureboundingbox.a, structureboundingbox.e - 2, structureboundingbox.c, structureboundingbox.d, structureboundingbox.e, structureboundingbox.f, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                }

                this.a(p_a_1_, p_a_3_, this.l.a, this.l.b + 4, this.l.c, this.l.d, this.l.e, this.l.f, Blocks.AIR.getBlockData(), false);
                return true;
            }
        }

        public void a(int p_a_1_, int p_a_2_, int p_a_3_)
        {
            super.a(p_a_1_, p_a_2_, p_a_3_);

            for (StructureBoundingBox structureboundingbox : this.a)
            {
                structureboundingbox.a(p_a_1_, p_a_2_, p_a_3_);
            }
        }

        protected void a(NBTTagCompound p_a_1_)
        {
            NBTTagList nbttaglist = new NBTTagList();

            for (StructureBoundingBox structureboundingbox : this.a)
            {
                nbttaglist.add(structureboundingbox.g());
            }

            p_a_1_.set("Entrances", nbttaglist);
        }

        protected void b(NBTTagCompound p_b_1_)
        {
            NBTTagList nbttaglist = p_b_1_.getList("Entrances", 11);

            for (int i = 0; i < nbttaglist.size(); ++i)
            {
                this.a.add(new StructureBoundingBox(nbttaglist.c(i)));
            }
        }
    }

    public static class WorldGenMineshaftStairs extends StructurePiece
    {
        public WorldGenMineshaftStairs()
        {
        }

        public WorldGenMineshaftStairs(int p_i726_1_, Random p_i726_2_, StructureBoundingBox p_i726_3_, EnumDirection p_i726_4_)
        {
            super(p_i726_1_);
            this.m = p_i726_4_;
            this.l = p_i726_3_;
        }

        protected void a(NBTTagCompound p_a_1_)
        {
        }

        protected void b(NBTTagCompound p_b_1_)
        {
        }

        public static StructureBoundingBox a(List<StructurePiece> p_a_0_, Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, EnumDirection p_a_5_)
        {
            StructureBoundingBox structureboundingbox = new StructureBoundingBox(p_a_2_, p_a_3_ - 5, p_a_4_, p_a_2_, p_a_3_ + 2, p_a_4_);

            switch (p_a_5_)
            {
                case NORTH:
                    structureboundingbox.d = p_a_2_ + 2;
                    structureboundingbox.c = p_a_4_ - 8;
                    break;

                case SOUTH:
                    structureboundingbox.d = p_a_2_ + 2;
                    structureboundingbox.f = p_a_4_ + 8;
                    break;

                case WEST:
                    structureboundingbox.a = p_a_2_ - 8;
                    structureboundingbox.f = p_a_4_ + 2;
                    break;

                case EAST:
                    structureboundingbox.d = p_a_2_ + 8;
                    structureboundingbox.f = p_a_4_ + 2;
            }

            return StructurePiece.a(p_a_0_, structureboundingbox) != null ? null : structureboundingbox;
        }

        public void a(StructurePiece p_a_1_, List<StructurePiece> p_a_2_, Random p_a_3_)
        {
            int i = this.d();

            if (this.m != null)
            {
                switch (this.m)
                {
                    case NORTH:
                        WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a, this.l.b, this.l.c - 1, EnumDirection.NORTH, i);
                        break;

                    case SOUTH:
                        WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a, this.l.b, this.l.f + 1, EnumDirection.SOUTH, i);
                        break;

                    case WEST:
                        WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.a - 1, this.l.b, this.l.c, EnumDirection.WEST, i);
                        break;

                    case EAST:
                        WorldGenMineshaftPieces.b(p_a_1_, p_a_2_, p_a_3_, this.l.d + 1, this.l.b, this.l.c, EnumDirection.EAST, i);
                }
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
                this.a(p_a_1_, p_a_3_, 0, 5, 0, 2, 7, 1, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                this.a(p_a_1_, p_a_3_, 0, 0, 7, 2, 2, 8, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);

                for (int i = 0; i < 5; ++i)
                {
                    this.a(p_a_1_, p_a_3_, 0, 5 - i - (i < 4 ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
                }

                return true;
            }
        }
    }
}
