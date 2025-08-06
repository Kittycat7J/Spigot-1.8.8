package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class WorldGenMonumentPieces
{
    public static void a()
    {
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPiece1.class, "OMB");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPiece2.class, "OMCR");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPiece3.class, "OMDXR");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPiece4.class, "OMDXYR");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPiece5.class, "OMDYR");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPiece6.class, "OMDYZR");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPiece7.class, "OMDZR");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPieceEntry.class, "OMEntry");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPiecePenthouse.class, "OMPenthouse");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPieceSimple.class, "OMSimple");
        WorldGenFactory.a(WorldGenMonumentPieces.WorldGenMonumentPieceSimpleT.class, "OMSimpleT");
    }

    interface IWorldGenMonumentPieceSelector
    {
        boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker var1);

        WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection var1, WorldGenMonumentPieces.WorldGenMonumentStateTracker var2, Random var3);
    }

    public abstract static class WorldGenMonumentPiece extends StructurePiece
    {
        protected static final IBlockData a = Blocks.PRISMARINE.fromLegacyData(BlockPrismarine.b);
        protected static final IBlockData b = Blocks.PRISMARINE.fromLegacyData(BlockPrismarine.N);
        protected static final IBlockData c = Blocks.PRISMARINE.fromLegacyData(BlockPrismarine.O);
        protected static final IBlockData d = b;
        protected static final IBlockData e = Blocks.SEA_LANTERN.getBlockData();
        protected static final IBlockData f = Blocks.WATER.getBlockData();
        protected static final int g = b(2, 0, 0);
        protected static final int h = b(2, 2, 0);
        protected static final int i = b(0, 1, 0);
        protected static final int j = b(4, 1, 0);
        protected WorldGenMonumentPieces.WorldGenMonumentStateTracker k;

        protected static final int b(int p_b_0_, int p_b_1_, int p_b_2_)
        {
            return p_b_1_ * 25 + p_b_2_ * 5 + p_b_0_;
        }

        public WorldGenMonumentPiece()
        {
            super(0);
        }

        public WorldGenMonumentPiece(int p_i766_1_)
        {
            super(p_i766_1_);
        }

        public WorldGenMonumentPiece(EnumDirection p_i767_1_, StructureBoundingBox p_i767_2_)
        {
            super(1);
            this.m = p_i767_1_;
            this.l = p_i767_2_;
        }

        protected WorldGenMonumentPiece(int p_i768_1_, EnumDirection p_i768_2_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_i768_3_, int p_i768_4_, int p_i768_5_, int p_i768_6_)
        {
            super(p_i768_1_);
            this.m = p_i768_2_;
            this.k = p_i768_3_;
            int ix = p_i768_3_.a;
            int jx = ix % 5;
            int kx = ix / 5 % 5;
            int l = ix / 25;

            if (p_i768_2_ != EnumDirection.NORTH && p_i768_2_ != EnumDirection.SOUTH)
            {
                this.l = new StructureBoundingBox(0, 0, 0, p_i768_6_ * 8 - 1, p_i768_5_ * 4 - 1, p_i768_4_ * 8 - 1);
            }
            else
            {
                this.l = new StructureBoundingBox(0, 0, 0, p_i768_4_ * 8 - 1, p_i768_5_ * 4 - 1, p_i768_6_ * 8 - 1);
            }

            switch (p_i768_2_)
            {
                case NORTH:
                    this.l.a(jx * 8, l * 4, -(kx + p_i768_6_) * 8 + 1);
                    break;

                case SOUTH:
                    this.l.a(jx * 8, l * 4, kx * 8);
                    break;

                case WEST:
                    this.l.a(-(kx + p_i768_6_) * 8 + 1, l * 4, jx * 8);
                    break;

                default:
                    this.l.a(kx * 8, l * 4, jx * 8);
            }
        }

        protected void a(NBTTagCompound p_a_1_)
        {
        }

        protected void b(NBTTagCompound p_b_1_)
        {
        }

        protected void a(World p_a_1_, StructureBoundingBox p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_, int p_a_8_, boolean p_a_9_)
        {
            for (int ix = p_a_4_; ix <= p_a_7_; ++ix)
            {
                for (int jx = p_a_3_; jx <= p_a_6_; ++jx)
                {
                    for (int kx = p_a_5_; kx <= p_a_8_; ++kx)
                    {
                        if (!p_a_9_ || this.a(p_a_1_, jx, ix, kx, p_a_2_).getBlock().getMaterial() != Material.AIR)
                        {
                            if (this.d(ix) >= p_a_1_.F())
                            {
                                this.a(p_a_1_, Blocks.AIR.getBlockData(), jx, ix, kx, p_a_2_);
                            }
                            else
                            {
                                this.a(p_a_1_, f, jx, ix, kx, p_a_2_);
                            }
                        }
                    }
                }
            }
        }

        protected void a(World p_a_1_, StructureBoundingBox p_a_2_, int p_a_3_, int p_a_4_, boolean p_a_5_)
        {
            if (p_a_5_)
            {
                this.a(p_a_1_, p_a_2_, p_a_3_ + 0, 0, p_a_4_ + 0, p_a_3_ + 2, 0, p_a_4_ + 8 - 1, a, a, false);
                this.a(p_a_1_, p_a_2_, p_a_3_ + 5, 0, p_a_4_ + 0, p_a_3_ + 8 - 1, 0, p_a_4_ + 8 - 1, a, a, false);
                this.a(p_a_1_, p_a_2_, p_a_3_ + 3, 0, p_a_4_ + 0, p_a_3_ + 4, 0, p_a_4_ + 2, a, a, false);
                this.a(p_a_1_, p_a_2_, p_a_3_ + 3, 0, p_a_4_ + 5, p_a_3_ + 4, 0, p_a_4_ + 8 - 1, a, a, false);
                this.a(p_a_1_, p_a_2_, p_a_3_ + 3, 0, p_a_4_ + 2, p_a_3_ + 4, 0, p_a_4_ + 2, b, b, false);
                this.a(p_a_1_, p_a_2_, p_a_3_ + 3, 0, p_a_4_ + 5, p_a_3_ + 4, 0, p_a_4_ + 5, b, b, false);
                this.a(p_a_1_, p_a_2_, p_a_3_ + 2, 0, p_a_4_ + 3, p_a_3_ + 2, 0, p_a_4_ + 4, b, b, false);
                this.a(p_a_1_, p_a_2_, p_a_3_ + 5, 0, p_a_4_ + 3, p_a_3_ + 5, 0, p_a_4_ + 4, b, b, false);
            }
            else
            {
                this.a(p_a_1_, p_a_2_, p_a_3_ + 0, 0, p_a_4_ + 0, p_a_3_ + 8 - 1, 0, p_a_4_ + 8 - 1, a, a, false);
            }
        }

        protected void a(World p_a_1_, StructureBoundingBox p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_, int p_a_8_, IBlockData p_a_9_)
        {
            for (int ix = p_a_4_; ix <= p_a_7_; ++ix)
            {
                for (int jx = p_a_3_; jx <= p_a_6_; ++jx)
                {
                    for (int kx = p_a_5_; kx <= p_a_8_; ++kx)
                    {
                        if (this.a(p_a_1_, jx, ix, kx, p_a_2_) == f)
                        {
                            this.a(p_a_1_, p_a_9_, jx, ix, kx, p_a_2_);
                        }
                    }
                }
            }
        }

        protected boolean a(StructureBoundingBox p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_)
        {
            int ix = this.a(p_a_2_, p_a_3_);
            int jx = this.b(p_a_2_, p_a_3_);
            int kx = this.a(p_a_4_, p_a_5_);
            int l = this.b(p_a_4_, p_a_5_);
            return p_a_1_.a(Math.min(ix, kx), Math.min(jx, l), Math.max(ix, kx), Math.max(jx, l));
        }

        protected boolean a(World p_a_1_, StructureBoundingBox p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_)
        {
            int ix = this.a(p_a_3_, p_a_5_);
            int jx = this.d(p_a_4_);
            int kx = this.b(p_a_3_, p_a_5_);

            if (p_a_2_.b((BaseBlockPosition)(new BlockPosition(ix, jx, kx))))
            {
                EntityGuardian entityguardian = new EntityGuardian(p_a_1_);
                entityguardian.setElder(true);
                entityguardian.heal(entityguardian.getMaxHealth());
                entityguardian.setPositionRotation((double)ix + 0.5D, (double)jx, (double)kx + 0.5D, 0.0F, 0.0F);
                entityguardian.prepare(p_a_1_.E(new BlockPosition(entityguardian)), (GroupDataEntity)null);
                p_a_1_.addEntity(entityguardian);
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public static class WorldGenMonumentPiece1 extends WorldGenMonumentPieces.WorldGenMonumentPiece
    {
        private WorldGenMonumentPieces.WorldGenMonumentStateTracker o;
        private WorldGenMonumentPieces.WorldGenMonumentStateTracker p;
        private List<WorldGenMonumentPieces.WorldGenMonumentPiece> q = Lists.<WorldGenMonumentPieces.WorldGenMonumentPiece>newArrayList();

        public WorldGenMonumentPiece1()
        {
        }

        public WorldGenMonumentPiece1(Random p_i757_1_, int p_i757_2_, int p_i757_3_, EnumDirection p_i757_4_)
        {
            super(0);
            this.m = p_i757_4_;

            switch (this.m)
            {
                case NORTH:
                case SOUTH:
                    this.l = new StructureBoundingBox(p_i757_2_, 39, p_i757_3_, p_i757_2_ + 58 - 1, 61, p_i757_3_ + 58 - 1);
                    break;

                default:
                    this.l = new StructureBoundingBox(p_i757_2_, 39, p_i757_3_, p_i757_2_ + 58 - 1, 61, p_i757_3_ + 58 - 1);
            }

            List list = this.a(p_i757_1_);
            this.o.d = true;
            this.q.add(new WorldGenMonumentPieces.WorldGenMonumentPieceEntry(this.m, this.o));
            this.q.add(new WorldGenMonumentPieces.WorldGenMonumentPiece2(this.m, this.p, p_i757_1_));
            ArrayList arraylist = Lists.newArrayList();
            arraylist.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector6());
            arraylist.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector4());
            arraylist.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector3());
            arraylist.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector7());
            arraylist.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector5());
            arraylist.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector1());
            arraylist.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector2());
            label319:

            for (WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker : list)
            {
                if (!worldgenmonumentpieces$worldgenmonumentstatetracker.d && !worldgenmonumentpieces$worldgenmonumentstatetracker.b())
                {
                    Iterator iterator = arraylist.iterator();
                    WorldGenMonumentPieces.IWorldGenMonumentPieceSelector worldgenmonumentpieces$iworldgenmonumentpieceselector;

                    while (true)
                    {
                        if (!iterator.hasNext())
                        {
                            continue label319;
                        }

                        worldgenmonumentpieces$iworldgenmonumentpieceselector = (WorldGenMonumentPieces.IWorldGenMonumentPieceSelector)iterator.next();

                        if (worldgenmonumentpieces$iworldgenmonumentpieceselector.a(worldgenmonumentpieces$worldgenmonumentstatetracker))
                        {
                            break;
                        }
                    }

                    this.q.add(worldgenmonumentpieces$iworldgenmonumentpieceselector.a(this.m, worldgenmonumentpieces$worldgenmonumentstatetracker, p_i757_1_));
                }
            }

            int j = this.l.b;
            int k = this.a(9, 22);
            int l = this.b(9, 22);

            for (WorldGenMonumentPieces.WorldGenMonumentPiece worldgenmonumentpieces$worldgenmonumentpiece : this.q)
            {
                worldgenmonumentpieces$worldgenmonumentpiece.c().a(k, j, l);
            }

            StructureBoundingBox structureboundingbox1 = StructureBoundingBox.a(this.a(1, 1), this.d(1), this.b(1, 1), this.a(23, 21), this.d(8), this.b(23, 21));
            StructureBoundingBox structureboundingbox2 = StructureBoundingBox.a(this.a(34, 1), this.d(1), this.b(34, 1), this.a(56, 21), this.d(8), this.b(56, 21));
            StructureBoundingBox structureboundingbox = StructureBoundingBox.a(this.a(22, 22), this.d(13), this.b(22, 22), this.a(35, 35), this.d(17), this.b(35, 35));
            int i = p_i757_1_.nextInt();
            this.q.add(new WorldGenMonumentPieces.WorldGenMonumentPiece8(this.m, structureboundingbox1, i++));
            this.q.add(new WorldGenMonumentPieces.WorldGenMonumentPiece8(this.m, structureboundingbox2, i++));
            this.q.add(new WorldGenMonumentPieces.WorldGenMonumentPiecePenthouse(this.m, structureboundingbox));
        }

        private List<WorldGenMonumentPieces.WorldGenMonumentStateTracker> a(Random p_a_1_)
        {
            WorldGenMonumentPieces.WorldGenMonumentStateTracker[] aworldgenmonumentpieces$worldgenmonumentstatetracker = new WorldGenMonumentPieces.WorldGenMonumentStateTracker[75];

            for (int i = 0; i < 5; ++i)
            {
                for (int j = 0; j < 4; ++j)
                {
                    byte b0 = 0;
                    int k = b(i, b0, j);
                    aworldgenmonumentpieces$worldgenmonumentstatetracker[k] = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(k);
                }
            }

            for (int l1 = 0; l1 < 5; ++l1)
            {
                for (int k2 = 0; k2 < 4; ++k2)
                {
                    byte b1 = 1;
                    int k3 = b(l1, b1, k2);
                    aworldgenmonumentpieces$worldgenmonumentstatetracker[k3] = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(k3);
                }
            }

            for (int i2 = 1; i2 < 4; ++i2)
            {
                for (int l2 = 0; l2 < 2; ++l2)
                {
                    byte b2 = 2;
                    int l3 = b(i2, b2, l2);
                    aworldgenmonumentpieces$worldgenmonumentstatetracker[l3] = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(l3);
                }
            }

            this.o = aworldgenmonumentpieces$worldgenmonumentstatetracker[g];

            for (int j2 = 0; j2 < 5; ++j2)
            {
                for (int i3 = 0; i3 < 5; ++i3)
                {
                    for (int j3 = 0; j3 < 3; ++j3)
                    {
                        int i4 = b(j2, j3, i3);

                        if (aworldgenmonumentpieces$worldgenmonumentstatetracker[i4] != null)
                        {
                            for (EnumDirection enumdirection : EnumDirection.values())
                            {
                                int l = j2 + enumdirection.getAdjacentX();
                                int i1 = j3 + enumdirection.getAdjacentY();
                                int j1 = i3 + enumdirection.getAdjacentZ();

                                if (l >= 0 && l < 5 && j1 >= 0 && j1 < 5 && i1 >= 0 && i1 < 3)
                                {
                                    int k1 = b(l, i1, j1);

                                    if (aworldgenmonumentpieces$worldgenmonumentstatetracker[k1] != null)
                                    {
                                        if (j1 != i3)
                                        {
                                            aworldgenmonumentpieces$worldgenmonumentstatetracker[i4].a(enumdirection.opposite(), aworldgenmonumentpieces$worldgenmonumentstatetracker[k1]);
                                        }
                                        else
                                        {
                                            aworldgenmonumentpieces$worldgenmonumentstatetracker[i4].a(enumdirection, aworldgenmonumentpieces$worldgenmonumentstatetracker[k1]);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker;
            aworldgenmonumentpieces$worldgenmonumentstatetracker[h].a(EnumDirection.UP, worldgenmonumentpieces$worldgenmonumentstatetracker = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(1003));
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker1;
            aworldgenmonumentpieces$worldgenmonumentstatetracker[i].a(EnumDirection.SOUTH, worldgenmonumentpieces$worldgenmonumentstatetracker1 = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(1001));
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker2;
            aworldgenmonumentpieces$worldgenmonumentstatetracker[j].a(EnumDirection.SOUTH, worldgenmonumentpieces$worldgenmonumentstatetracker2 = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(1002));
            worldgenmonumentpieces$worldgenmonumentstatetracker.d = true;
            worldgenmonumentpieces$worldgenmonumentstatetracker1.d = true;
            worldgenmonumentpieces$worldgenmonumentstatetracker2.d = true;
            this.o.e = true;
            this.p = aworldgenmonumentpieces$worldgenmonumentstatetracker[b(p_a_1_.nextInt(4), 0, 2)];
            this.p.d = true;
            this.p.b[EnumDirection.EAST.a()].d = true;
            this.p.b[EnumDirection.NORTH.a()].d = true;
            this.p.b[EnumDirection.EAST.a()].b[EnumDirection.NORTH.a()].d = true;
            this.p.b[EnumDirection.UP.a()].d = true;
            this.p.b[EnumDirection.EAST.a()].b[EnumDirection.UP.a()].d = true;
            this.p.b[EnumDirection.NORTH.a()].b[EnumDirection.UP.a()].d = true;
            this.p.b[EnumDirection.EAST.a()].b[EnumDirection.NORTH.a()].b[EnumDirection.UP.a()].d = true;
            ArrayList arraylist = Lists.newArrayList();

            for (WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker4 : aworldgenmonumentpieces$worldgenmonumentstatetracker)
            {
                if (worldgenmonumentpieces$worldgenmonumentstatetracker4 != null)
                {
                    worldgenmonumentpieces$worldgenmonumentstatetracker4.a();
                    arraylist.add(worldgenmonumentpieces$worldgenmonumentstatetracker4);
                }
            }

            worldgenmonumentpieces$worldgenmonumentstatetracker.a();
            Collections.shuffle(arraylist, p_a_1_);
            int j4 = 1;

            for (WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker3 : arraylist)
            {
                int k4 = 0;
                int l4 = 0;

                while (k4 < 2 && l4 < 5)
                {
                    ++l4;
                    int i5 = p_a_1_.nextInt(6);

                    if (worldgenmonumentpieces$worldgenmonumentstatetracker3.c[i5])
                    {
                        int j5 = EnumDirection.fromType1(i5).opposite().a();
                        worldgenmonumentpieces$worldgenmonumentstatetracker3.c[i5] = false;
                        worldgenmonumentpieces$worldgenmonumentstatetracker3.b[i5].c[j5] = false;

                        if (worldgenmonumentpieces$worldgenmonumentstatetracker3.a(j4++) && worldgenmonumentpieces$worldgenmonumentstatetracker3.b[i5].a(j4++))
                        {
                            ++k4;
                        }
                        else
                        {
                            worldgenmonumentpieces$worldgenmonumentstatetracker3.c[i5] = true;
                            worldgenmonumentpieces$worldgenmonumentstatetracker3.b[i5].c[j5] = true;
                        }
                    }
                }
            }

            arraylist.add(worldgenmonumentpieces$worldgenmonumentstatetracker);
            arraylist.add(worldgenmonumentpieces$worldgenmonumentstatetracker1);
            arraylist.add(worldgenmonumentpieces$worldgenmonumentstatetracker2);
            return arraylist;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            int i = Math.max(p_a_1_.F(), 64) - this.l.b;
            this.a(p_a_1_, p_a_3_, 0, 0, 0, 58, i, 58, false);
            this.a(false, 0, p_a_1_, p_a_2_, p_a_3_);
            this.a(true, 33, p_a_1_, p_a_2_, p_a_3_);
            this.b(p_a_1_, p_a_2_, p_a_3_);
            this.c(p_a_1_, p_a_2_, p_a_3_);
            this.d(p_a_1_, p_a_2_, p_a_3_);
            this.e(p_a_1_, p_a_2_, p_a_3_);
            this.f(p_a_1_, p_a_2_, p_a_3_);
            this.g(p_a_1_, p_a_2_, p_a_3_);

            for (int j = 0; j < 7; ++j)
            {
                int k = 0;

                while (k < 7)
                {
                    if (k == 0 && j == 3)
                    {
                        k = 6;
                    }

                    int l = j * 9;
                    int i1 = k * 9;

                    for (int j1 = 0; j1 < 4; ++j1)
                    {
                        for (int k1 = 0; k1 < 4; ++k1)
                        {
                            this.a(p_a_1_, b, l + j1, 0, i1 + k1, p_a_3_);
                            this.b(p_a_1_, b, l + j1, -1, i1 + k1, p_a_3_);
                        }
                    }

                    if (j != 0 && j != 6)
                    {
                        k += 6;
                    }
                    else
                    {
                        ++k;
                    }
                }
            }

            for (int l1 = 0; l1 < 5; ++l1)
            {
                this.a(p_a_1_, p_a_3_, -1 - l1, 0 + l1 * 2, -1 - l1, -1 - l1, 23, 58 + l1, false);
                this.a(p_a_1_, p_a_3_, 58 + l1, 0 + l1 * 2, -1 - l1, 58 + l1, 23, 58 + l1, false);
                this.a(p_a_1_, p_a_3_, 0 - l1, 0 + l1 * 2, -1 - l1, 57 + l1, 23, -1 - l1, false);
                this.a(p_a_1_, p_a_3_, 0 - l1, 0 + l1 * 2, 58 + l1, 57 + l1, 23, 58 + l1, false);
            }

            for (WorldGenMonumentPieces.WorldGenMonumentPiece worldgenmonumentpieces$worldgenmonumentpiece : this.q)
            {
                if (worldgenmonumentpieces$worldgenmonumentpiece.c().a(p_a_3_))
                {
                    worldgenmonumentpieces$worldgenmonumentpiece.a(p_a_1_, p_a_2_, p_a_3_);
                }
            }

            return true;
        }

        private void a(boolean p_a_1_, int p_a_2_, World p_a_3_, Random p_a_4_, StructureBoundingBox p_a_5_)
        {
            boolean flag = true;

            if (this.a(p_a_5_, p_a_2_, 0, p_a_2_ + 23, 20))
            {
                this.a(p_a_3_, p_a_5_, p_a_2_ + 0, 0, 0, p_a_2_ + 24, 0, 20, a, a, false);
                this.a(p_a_3_, p_a_5_, p_a_2_ + 0, 1, 0, p_a_2_ + 24, 10, 20, false);

                for (int i = 0; i < 4; ++i)
                {
                    this.a(p_a_3_, p_a_5_, p_a_2_ + i, i + 1, i, p_a_2_ + i, i + 1, 20, b, b, false);
                    this.a(p_a_3_, p_a_5_, p_a_2_ + i + 7, i + 5, i + 7, p_a_2_ + i + 7, i + 5, 20, b, b, false);
                    this.a(p_a_3_, p_a_5_, p_a_2_ + 17 - i, i + 5, i + 7, p_a_2_ + 17 - i, i + 5, 20, b, b, false);
                    this.a(p_a_3_, p_a_5_, p_a_2_ + 24 - i, i + 1, i, p_a_2_ + 24 - i, i + 1, 20, b, b, false);
                    this.a(p_a_3_, p_a_5_, p_a_2_ + i + 1, i + 1, i, p_a_2_ + 23 - i, i + 1, i, b, b, false);
                    this.a(p_a_3_, p_a_5_, p_a_2_ + i + 8, i + 5, i + 7, p_a_2_ + 16 - i, i + 5, i + 7, b, b, false);
                }

                this.a(p_a_3_, p_a_5_, p_a_2_ + 4, 4, 4, p_a_2_ + 6, 4, 20, a, a, false);
                this.a(p_a_3_, p_a_5_, p_a_2_ + 7, 4, 4, p_a_2_ + 17, 4, 6, a, a, false);
                this.a(p_a_3_, p_a_5_, p_a_2_ + 18, 4, 4, p_a_2_ + 20, 4, 20, a, a, false);
                this.a(p_a_3_, p_a_5_, p_a_2_ + 11, 8, 11, p_a_2_ + 13, 8, 20, a, a, false);
                this.a(p_a_3_, d, p_a_2_ + 12, 9, 12, p_a_5_);
                this.a(p_a_3_, d, p_a_2_ + 12, 9, 15, p_a_5_);
                this.a(p_a_3_, d, p_a_2_ + 12, 9, 18, p_a_5_);
                int i1 = p_a_1_ ? p_a_2_ + 19 : p_a_2_ + 5;
                int j = p_a_1_ ? p_a_2_ + 5 : p_a_2_ + 19;

                for (int k = 20; k >= 5; k -= 3)
                {
                    this.a(p_a_3_, d, i1, 5, k, p_a_5_);
                }

                for (int j1 = 19; j1 >= 7; j1 -= 3)
                {
                    this.a(p_a_3_, d, j, 5, j1, p_a_5_);
                }

                for (int k1 = 0; k1 < 4; ++k1)
                {
                    int l = p_a_1_ ? p_a_2_ + (24 - (17 - k1 * 3)) : p_a_2_ + 17 - k1 * 3;
                    this.a(p_a_3_, d, l, 5, 5, p_a_5_);
                }

                this.a(p_a_3_, d, j, 5, 5, p_a_5_);
                this.a(p_a_3_, p_a_5_, p_a_2_ + 11, 1, 12, p_a_2_ + 13, 7, 12, a, a, false);
                this.a(p_a_3_, p_a_5_, p_a_2_ + 12, 1, 11, p_a_2_ + 12, 7, 13, a, a, false);
            }
        }

        private void b(World p_b_1_, Random p_b_2_, StructureBoundingBox p_b_3_)
        {
            if (this.a(p_b_3_, 22, 5, 35, 17))
            {
                this.a(p_b_1_, p_b_3_, 25, 0, 0, 32, 8, 20, false);

                for (int i = 0; i < 4; ++i)
                {
                    this.a(p_b_1_, p_b_3_, 24, 2, 5 + i * 4, 24, 4, 5 + i * 4, b, b, false);
                    this.a(p_b_1_, p_b_3_, 22, 4, 5 + i * 4, 23, 4, 5 + i * 4, b, b, false);
                    this.a(p_b_1_, b, 25, 5, 5 + i * 4, p_b_3_);
                    this.a(p_b_1_, b, 26, 6, 5 + i * 4, p_b_3_);
                    this.a(p_b_1_, e, 26, 5, 5 + i * 4, p_b_3_);
                    this.a(p_b_1_, p_b_3_, 33, 2, 5 + i * 4, 33, 4, 5 + i * 4, b, b, false);
                    this.a(p_b_1_, p_b_3_, 34, 4, 5 + i * 4, 35, 4, 5 + i * 4, b, b, false);
                    this.a(p_b_1_, b, 32, 5, 5 + i * 4, p_b_3_);
                    this.a(p_b_1_, b, 31, 6, 5 + i * 4, p_b_3_);
                    this.a(p_b_1_, e, 31, 5, 5 + i * 4, p_b_3_);
                    this.a(p_b_1_, p_b_3_, 27, 6, 5 + i * 4, 30, 6, 5 + i * 4, a, a, false);
                }
            }
        }

        private void c(World p_c_1_, Random p_c_2_, StructureBoundingBox p_c_3_)
        {
            if (this.a(p_c_3_, 15, 20, 42, 21))
            {
                this.a(p_c_1_, p_c_3_, 15, 0, 21, 42, 0, 21, a, a, false);
                this.a(p_c_1_, p_c_3_, 26, 1, 21, 31, 3, 21, false);
                this.a(p_c_1_, p_c_3_, 21, 12, 21, 36, 12, 21, a, a, false);
                this.a(p_c_1_, p_c_3_, 17, 11, 21, 40, 11, 21, a, a, false);
                this.a(p_c_1_, p_c_3_, 16, 10, 21, 41, 10, 21, a, a, false);
                this.a(p_c_1_, p_c_3_, 15, 7, 21, 42, 9, 21, a, a, false);
                this.a(p_c_1_, p_c_3_, 16, 6, 21, 41, 6, 21, a, a, false);
                this.a(p_c_1_, p_c_3_, 17, 5, 21, 40, 5, 21, a, a, false);
                this.a(p_c_1_, p_c_3_, 21, 4, 21, 36, 4, 21, a, a, false);
                this.a(p_c_1_, p_c_3_, 22, 3, 21, 26, 3, 21, a, a, false);
                this.a(p_c_1_, p_c_3_, 31, 3, 21, 35, 3, 21, a, a, false);
                this.a(p_c_1_, p_c_3_, 23, 2, 21, 25, 2, 21, a, a, false);
                this.a(p_c_1_, p_c_3_, 32, 2, 21, 34, 2, 21, a, a, false);
                this.a(p_c_1_, p_c_3_, 28, 4, 20, 29, 4, 21, b, b, false);
                this.a(p_c_1_, b, 27, 3, 21, p_c_3_);
                this.a(p_c_1_, b, 30, 3, 21, p_c_3_);
                this.a(p_c_1_, b, 26, 2, 21, p_c_3_);
                this.a(p_c_1_, b, 31, 2, 21, p_c_3_);
                this.a(p_c_1_, b, 25, 1, 21, p_c_3_);
                this.a(p_c_1_, b, 32, 1, 21, p_c_3_);

                for (int i = 0; i < 7; ++i)
                {
                    this.a(p_c_1_, c, 28 - i, 6 + i, 21, p_c_3_);
                    this.a(p_c_1_, c, 29 + i, 6 + i, 21, p_c_3_);
                }

                for (int j = 0; j < 4; ++j)
                {
                    this.a(p_c_1_, c, 28 - j, 9 + j, 21, p_c_3_);
                    this.a(p_c_1_, c, 29 + j, 9 + j, 21, p_c_3_);
                }

                this.a(p_c_1_, c, 28, 12, 21, p_c_3_);
                this.a(p_c_1_, c, 29, 12, 21, p_c_3_);

                for (int k = 0; k < 3; ++k)
                {
                    this.a(p_c_1_, c, 22 - k * 2, 8, 21, p_c_3_);
                    this.a(p_c_1_, c, 22 - k * 2, 9, 21, p_c_3_);
                    this.a(p_c_1_, c, 35 + k * 2, 8, 21, p_c_3_);
                    this.a(p_c_1_, c, 35 + k * 2, 9, 21, p_c_3_);
                }

                this.a(p_c_1_, p_c_3_, 15, 13, 21, 42, 15, 21, false);
                this.a(p_c_1_, p_c_3_, 15, 1, 21, 15, 6, 21, false);
                this.a(p_c_1_, p_c_3_, 16, 1, 21, 16, 5, 21, false);
                this.a(p_c_1_, p_c_3_, 17, 1, 21, 20, 4, 21, false);
                this.a(p_c_1_, p_c_3_, 21, 1, 21, 21, 3, 21, false);
                this.a(p_c_1_, p_c_3_, 22, 1, 21, 22, 2, 21, false);
                this.a(p_c_1_, p_c_3_, 23, 1, 21, 24, 1, 21, false);
                this.a(p_c_1_, p_c_3_, 42, 1, 21, 42, 6, 21, false);
                this.a(p_c_1_, p_c_3_, 41, 1, 21, 41, 5, 21, false);
                this.a(p_c_1_, p_c_3_, 37, 1, 21, 40, 4, 21, false);
                this.a(p_c_1_, p_c_3_, 36, 1, 21, 36, 3, 21, false);
                this.a(p_c_1_, p_c_3_, 33, 1, 21, 34, 1, 21, false);
                this.a(p_c_1_, p_c_3_, 35, 1, 21, 35, 2, 21, false);
            }
        }

        private void d(World p_d_1_, Random p_d_2_, StructureBoundingBox p_d_3_)
        {
            if (this.a(p_d_3_, 21, 21, 36, 36))
            {
                this.a(p_d_1_, p_d_3_, 21, 0, 22, 36, 0, 36, a, a, false);
                this.a(p_d_1_, p_d_3_, 21, 1, 22, 36, 23, 36, false);

                for (int i = 0; i < 4; ++i)
                {
                    this.a(p_d_1_, p_d_3_, 21 + i, 13 + i, 21 + i, 36 - i, 13 + i, 21 + i, b, b, false);
                    this.a(p_d_1_, p_d_3_, 21 + i, 13 + i, 36 - i, 36 - i, 13 + i, 36 - i, b, b, false);
                    this.a(p_d_1_, p_d_3_, 21 + i, 13 + i, 22 + i, 21 + i, 13 + i, 35 - i, b, b, false);
                    this.a(p_d_1_, p_d_3_, 36 - i, 13 + i, 22 + i, 36 - i, 13 + i, 35 - i, b, b, false);
                }

                this.a(p_d_1_, p_d_3_, 25, 16, 25, 32, 16, 32, a, a, false);
                this.a(p_d_1_, p_d_3_, 25, 17, 25, 25, 19, 25, b, b, false);
                this.a(p_d_1_, p_d_3_, 32, 17, 25, 32, 19, 25, b, b, false);
                this.a(p_d_1_, p_d_3_, 25, 17, 32, 25, 19, 32, b, b, false);
                this.a(p_d_1_, p_d_3_, 32, 17, 32, 32, 19, 32, b, b, false);
                this.a(p_d_1_, b, 26, 20, 26, p_d_3_);
                this.a(p_d_1_, b, 27, 21, 27, p_d_3_);
                this.a(p_d_1_, e, 27, 20, 27, p_d_3_);
                this.a(p_d_1_, b, 26, 20, 31, p_d_3_);
                this.a(p_d_1_, b, 27, 21, 30, p_d_3_);
                this.a(p_d_1_, e, 27, 20, 30, p_d_3_);
                this.a(p_d_1_, b, 31, 20, 31, p_d_3_);
                this.a(p_d_1_, b, 30, 21, 30, p_d_3_);
                this.a(p_d_1_, e, 30, 20, 30, p_d_3_);
                this.a(p_d_1_, b, 31, 20, 26, p_d_3_);
                this.a(p_d_1_, b, 30, 21, 27, p_d_3_);
                this.a(p_d_1_, e, 30, 20, 27, p_d_3_);
                this.a(p_d_1_, p_d_3_, 28, 21, 27, 29, 21, 27, a, a, false);
                this.a(p_d_1_, p_d_3_, 27, 21, 28, 27, 21, 29, a, a, false);
                this.a(p_d_1_, p_d_3_, 28, 21, 30, 29, 21, 30, a, a, false);
                this.a(p_d_1_, p_d_3_, 30, 21, 28, 30, 21, 29, a, a, false);
            }
        }

        private void e(World p_e_1_, Random p_e_2_, StructureBoundingBox p_e_3_)
        {
            if (this.a(p_e_3_, 0, 21, 6, 58))
            {
                this.a(p_e_1_, p_e_3_, 0, 0, 21, 6, 0, 57, a, a, false);
                this.a(p_e_1_, p_e_3_, 0, 1, 21, 6, 7, 57, false);
                this.a(p_e_1_, p_e_3_, 4, 4, 21, 6, 4, 53, a, a, false);

                for (int i = 0; i < 4; ++i)
                {
                    this.a(p_e_1_, p_e_3_, i, i + 1, 21, i, i + 1, 57 - i, b, b, false);
                }

                for (int j = 23; j < 53; j += 3)
                {
                    this.a(p_e_1_, d, 5, 5, j, p_e_3_);
                }

                this.a(p_e_1_, d, 5, 5, 52, p_e_3_);

                for (int k = 0; k < 4; ++k)
                {
                    this.a(p_e_1_, p_e_3_, k, k + 1, 21, k, k + 1, 57 - k, b, b, false);
                }

                this.a(p_e_1_, p_e_3_, 4, 1, 52, 6, 3, 52, a, a, false);
                this.a(p_e_1_, p_e_3_, 5, 1, 51, 5, 3, 53, a, a, false);
            }

            if (this.a(p_e_3_, 51, 21, 58, 58))
            {
                this.a(p_e_1_, p_e_3_, 51, 0, 21, 57, 0, 57, a, a, false);
                this.a(p_e_1_, p_e_3_, 51, 1, 21, 57, 7, 57, false);
                this.a(p_e_1_, p_e_3_, 51, 4, 21, 53, 4, 53, a, a, false);

                for (int l = 0; l < 4; ++l)
                {
                    this.a(p_e_1_, p_e_3_, 57 - l, l + 1, 21, 57 - l, l + 1, 57 - l, b, b, false);
                }

                for (int i1 = 23; i1 < 53; i1 += 3)
                {
                    this.a(p_e_1_, d, 52, 5, i1, p_e_3_);
                }

                this.a(p_e_1_, d, 52, 5, 52, p_e_3_);
                this.a(p_e_1_, p_e_3_, 51, 1, 52, 53, 3, 52, a, a, false);
                this.a(p_e_1_, p_e_3_, 52, 1, 51, 52, 3, 53, a, a, false);
            }

            if (this.a(p_e_3_, 0, 51, 57, 57))
            {
                this.a(p_e_1_, p_e_3_, 7, 0, 51, 50, 0, 57, a, a, false);
                this.a(p_e_1_, p_e_3_, 7, 1, 51, 50, 10, 57, false);

                for (int j1 = 0; j1 < 4; ++j1)
                {
                    this.a(p_e_1_, p_e_3_, j1 + 1, j1 + 1, 57 - j1, 56 - j1, j1 + 1, 57 - j1, b, b, false);
                }
            }
        }

        private void f(World p_f_1_, Random p_f_2_, StructureBoundingBox p_f_3_)
        {
            if (this.a(p_f_3_, 7, 21, 13, 50))
            {
                this.a(p_f_1_, p_f_3_, 7, 0, 21, 13, 0, 50, a, a, false);
                this.a(p_f_1_, p_f_3_, 7, 1, 21, 13, 10, 50, false);
                this.a(p_f_1_, p_f_3_, 11, 8, 21, 13, 8, 53, a, a, false);

                for (int i = 0; i < 4; ++i)
                {
                    this.a(p_f_1_, p_f_3_, i + 7, i + 5, 21, i + 7, i + 5, 54, b, b, false);
                }

                for (int j = 21; j <= 45; j += 3)
                {
                    this.a(p_f_1_, d, 12, 9, j, p_f_3_);
                }
            }

            if (this.a(p_f_3_, 44, 21, 50, 54))
            {
                this.a(p_f_1_, p_f_3_, 44, 0, 21, 50, 0, 50, a, a, false);
                this.a(p_f_1_, p_f_3_, 44, 1, 21, 50, 10, 50, false);
                this.a(p_f_1_, p_f_3_, 44, 8, 21, 46, 8, 53, a, a, false);

                for (int k = 0; k < 4; ++k)
                {
                    this.a(p_f_1_, p_f_3_, 50 - k, k + 5, 21, 50 - k, k + 5, 54, b, b, false);
                }

                for (int l = 21; l <= 45; l += 3)
                {
                    this.a(p_f_1_, d, 45, 9, l, p_f_3_);
                }
            }

            if (this.a(p_f_3_, 8, 44, 49, 54))
            {
                this.a(p_f_1_, p_f_3_, 14, 0, 44, 43, 0, 50, a, a, false);
                this.a(p_f_1_, p_f_3_, 14, 1, 44, 43, 10, 50, false);

                for (int i1 = 12; i1 <= 45; i1 += 3)
                {
                    this.a(p_f_1_, d, i1, 9, 45, p_f_3_);
                    this.a(p_f_1_, d, i1, 9, 52, p_f_3_);

                    if (i1 == 12 || i1 == 18 || i1 == 24 || i1 == 33 || i1 == 39 || i1 == 45)
                    {
                        this.a(p_f_1_, d, i1, 9, 47, p_f_3_);
                        this.a(p_f_1_, d, i1, 9, 50, p_f_3_);
                        this.a(p_f_1_, d, i1, 10, 45, p_f_3_);
                        this.a(p_f_1_, d, i1, 10, 46, p_f_3_);
                        this.a(p_f_1_, d, i1, 10, 51, p_f_3_);
                        this.a(p_f_1_, d, i1, 10, 52, p_f_3_);
                        this.a(p_f_1_, d, i1, 11, 47, p_f_3_);
                        this.a(p_f_1_, d, i1, 11, 50, p_f_3_);
                        this.a(p_f_1_, d, i1, 12, 48, p_f_3_);
                        this.a(p_f_1_, d, i1, 12, 49, p_f_3_);
                    }
                }

                for (int j1 = 0; j1 < 3; ++j1)
                {
                    this.a(p_f_1_, p_f_3_, 8 + j1, 5 + j1, 54, 49 - j1, 5 + j1, 54, a, a, false);
                }

                this.a(p_f_1_, p_f_3_, 11, 8, 54, 46, 8, 54, b, b, false);
                this.a(p_f_1_, p_f_3_, 14, 8, 44, 43, 8, 53, a, a, false);
            }
        }

        private void g(World p_g_1_, Random p_g_2_, StructureBoundingBox p_g_3_)
        {
            if (this.a(p_g_3_, 14, 21, 20, 43))
            {
                this.a(p_g_1_, p_g_3_, 14, 0, 21, 20, 0, 43, a, a, false);
                this.a(p_g_1_, p_g_3_, 14, 1, 22, 20, 14, 43, false);
                this.a(p_g_1_, p_g_3_, 18, 12, 22, 20, 12, 39, a, a, false);
                this.a(p_g_1_, p_g_3_, 18, 12, 21, 20, 12, 21, b, b, false);

                for (int i = 0; i < 4; ++i)
                {
                    this.a(p_g_1_, p_g_3_, i + 14, i + 9, 21, i + 14, i + 9, 43 - i, b, b, false);
                }

                for (int j = 23; j <= 39; j += 3)
                {
                    this.a(p_g_1_, d, 19, 13, j, p_g_3_);
                }
            }

            if (this.a(p_g_3_, 37, 21, 43, 43))
            {
                this.a(p_g_1_, p_g_3_, 37, 0, 21, 43, 0, 43, a, a, false);
                this.a(p_g_1_, p_g_3_, 37, 1, 22, 43, 14, 43, false);
                this.a(p_g_1_, p_g_3_, 37, 12, 22, 39, 12, 39, a, a, false);
                this.a(p_g_1_, p_g_3_, 37, 12, 21, 39, 12, 21, b, b, false);

                for (int k = 0; k < 4; ++k)
                {
                    this.a(p_g_1_, p_g_3_, 43 - k, k + 9, 21, 43 - k, k + 9, 43 - k, b, b, false);
                }

                for (int l = 23; l <= 39; l += 3)
                {
                    this.a(p_g_1_, d, 38, 13, l, p_g_3_);
                }
            }

            if (this.a(p_g_3_, 15, 37, 42, 43))
            {
                this.a(p_g_1_, p_g_3_, 21, 0, 37, 36, 0, 43, a, a, false);
                this.a(p_g_1_, p_g_3_, 21, 1, 37, 36, 14, 43, false);
                this.a(p_g_1_, p_g_3_, 21, 12, 37, 36, 12, 39, a, a, false);

                for (int i1 = 0; i1 < 4; ++i1)
                {
                    this.a(p_g_1_, p_g_3_, 15 + i1, i1 + 9, 43 - i1, 42 - i1, i1 + 9, 43 - i1, b, b, false);
                }

                for (int j1 = 21; j1 <= 36; j1 += 3)
                {
                    this.a(p_g_1_, d, j1, 13, 38, p_g_3_);
                }
            }
        }
    }

    public static class WorldGenMonumentPiece2 extends WorldGenMonumentPieces.WorldGenMonumentPiece
    {
        public WorldGenMonumentPiece2()
        {
        }

        public WorldGenMonumentPiece2(EnumDirection p_i758_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_i758_2_, Random p_i758_3_)
        {
            super(1, p_i758_1_, p_i758_2_, 2, 2, 2);
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 1, 8, 0, 14, 8, 14, a);
            int i = 7;
            IBlockData iblockdata = b;
            this.a(p_a_1_, p_a_3_, 0, i, 0, 0, i, 15, iblockdata, iblockdata, false);
            this.a(p_a_1_, p_a_3_, 15, i, 0, 15, i, 15, iblockdata, iblockdata, false);
            this.a(p_a_1_, p_a_3_, 1, i, 0, 15, i, 0, iblockdata, iblockdata, false);
            this.a(p_a_1_, p_a_3_, 1, i, 15, 14, i, 15, iblockdata, iblockdata, false);

            for (i = 1; i <= 6; ++i)
            {
                iblockdata = b;

                if (i == 2 || i == 6)
                {
                    iblockdata = a;
                }

                for (int j = 0; j <= 15; j += 15)
                {
                    this.a(p_a_1_, p_a_3_, j, i, 0, j, i, 1, iblockdata, iblockdata, false);
                    this.a(p_a_1_, p_a_3_, j, i, 6, j, i, 9, iblockdata, iblockdata, false);
                    this.a(p_a_1_, p_a_3_, j, i, 14, j, i, 15, iblockdata, iblockdata, false);
                }

                this.a(p_a_1_, p_a_3_, 1, i, 0, 1, i, 0, iblockdata, iblockdata, false);
                this.a(p_a_1_, p_a_3_, 6, i, 0, 9, i, 0, iblockdata, iblockdata, false);
                this.a(p_a_1_, p_a_3_, 14, i, 0, 14, i, 0, iblockdata, iblockdata, false);
                this.a(p_a_1_, p_a_3_, 1, i, 15, 14, i, 15, iblockdata, iblockdata, false);
            }

            this.a(p_a_1_, p_a_3_, 6, 3, 6, 9, 6, 9, c, c, false);
            this.a(p_a_1_, p_a_3_, 7, 4, 7, 8, 5, 8, Blocks.GOLD_BLOCK.getBlockData(), Blocks.GOLD_BLOCK.getBlockData(), false);

            for (i = 3; i <= 6; i += 3)
            {
                for (int k = 6; k <= 9; k += 3)
                {
                    this.a(p_a_1_, e, k, i, 6, p_a_3_);
                    this.a(p_a_1_, e, k, i, 9, p_a_3_);
                }
            }

            this.a(p_a_1_, p_a_3_, 5, 1, 6, 5, 2, 6, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 1, 9, 5, 2, 9, b, b, false);
            this.a(p_a_1_, p_a_3_, 10, 1, 6, 10, 2, 6, b, b, false);
            this.a(p_a_1_, p_a_3_, 10, 1, 9, 10, 2, 9, b, b, false);
            this.a(p_a_1_, p_a_3_, 6, 1, 5, 6, 2, 5, b, b, false);
            this.a(p_a_1_, p_a_3_, 9, 1, 5, 9, 2, 5, b, b, false);
            this.a(p_a_1_, p_a_3_, 6, 1, 10, 6, 2, 10, b, b, false);
            this.a(p_a_1_, p_a_3_, 9, 1, 10, 9, 2, 10, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 2, 5, 5, 6, 5, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 2, 10, 5, 6, 10, b, b, false);
            this.a(p_a_1_, p_a_3_, 10, 2, 5, 10, 6, 5, b, b, false);
            this.a(p_a_1_, p_a_3_, 10, 2, 10, 10, 6, 10, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 7, 1, 5, 7, 6, b, b, false);
            this.a(p_a_1_, p_a_3_, 10, 7, 1, 10, 7, 6, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 7, 9, 5, 7, 14, b, b, false);
            this.a(p_a_1_, p_a_3_, 10, 7, 9, 10, 7, 14, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 7, 5, 6, 7, 5, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 7, 10, 6, 7, 10, b, b, false);
            this.a(p_a_1_, p_a_3_, 9, 7, 5, 14, 7, 5, b, b, false);
            this.a(p_a_1_, p_a_3_, 9, 7, 10, 14, 7, 10, b, b, false);
            this.a(p_a_1_, p_a_3_, 2, 1, 2, 2, 1, 3, b, b, false);
            this.a(p_a_1_, p_a_3_, 3, 1, 2, 3, 1, 2, b, b, false);
            this.a(p_a_1_, p_a_3_, 13, 1, 2, 13, 1, 3, b, b, false);
            this.a(p_a_1_, p_a_3_, 12, 1, 2, 12, 1, 2, b, b, false);
            this.a(p_a_1_, p_a_3_, 2, 1, 12, 2, 1, 13, b, b, false);
            this.a(p_a_1_, p_a_3_, 3, 1, 13, 3, 1, 13, b, b, false);
            this.a(p_a_1_, p_a_3_, 13, 1, 12, 13, 1, 13, b, b, false);
            this.a(p_a_1_, p_a_3_, 12, 1, 13, 12, 1, 13, b, b, false);
            return true;
        }
    }

    public static class WorldGenMonumentPiece3 extends WorldGenMonumentPieces.WorldGenMonumentPiece
    {
        public WorldGenMonumentPiece3()
        {
        }

        public WorldGenMonumentPiece3(EnumDirection p_i759_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_i759_2_, Random p_i759_3_)
        {
            super(1, p_i759_1_, p_i759_2_, 2, 1, 1);
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker = this.k.b[EnumDirection.EAST.a()];
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker1 = this.k;

            if (this.k.a / 25 > 0)
            {
                this.a(p_a_1_, p_a_3_, 8, 0, worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.DOWN.a()]);
                this.a(p_a_1_, p_a_3_, 0, 0, worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.DOWN.a()]);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.b[EnumDirection.UP.a()] == null)
            {
                this.a(p_a_1_, p_a_3_, 1, 4, 1, 7, 4, 6, a);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()] == null)
            {
                this.a(p_a_1_, p_a_3_, 8, 4, 1, 14, 4, 6, a);
            }

            this.a(p_a_1_, p_a_3_, 0, 3, 0, 0, 3, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 15, 3, 0, 15, 3, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 3, 0, 15, 3, 0, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 3, 7, 14, 3, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 0, 2, 7, a, a, false);
            this.a(p_a_1_, p_a_3_, 15, 2, 0, 15, 2, 7, a, a, false);
            this.a(p_a_1_, p_a_3_, 1, 2, 0, 15, 2, 0, a, a, false);
            this.a(p_a_1_, p_a_3_, 1, 2, 7, 14, 2, 7, a, a, false);
            this.a(p_a_1_, p_a_3_, 0, 1, 0, 0, 1, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 15, 1, 0, 15, 1, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 1, 0, 15, 1, 0, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 1, 7, 14, 1, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 1, 0, 10, 1, 4, b, b, false);
            this.a(p_a_1_, p_a_3_, 6, 2, 0, 9, 2, 3, a, a, false);
            this.a(p_a_1_, p_a_3_, 5, 3, 0, 10, 3, 4, b, b, false);
            this.a(p_a_1_, e, 6, 2, 3, p_a_3_);
            this.a(p_a_1_, e, 9, 2, 3, p_a_3_);

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.SOUTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 3, 1, 0, 4, 2, 0, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.NORTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 3, 1, 7, 4, 2, 7, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.WEST.a()])
            {
                this.a(p_a_1_, p_a_3_, 0, 1, 3, 0, 2, 4, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.SOUTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 11, 1, 0, 12, 2, 0, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.NORTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 11, 1, 7, 12, 2, 7, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.EAST.a()])
            {
                this.a(p_a_1_, p_a_3_, 15, 1, 3, 15, 2, 4, false);
            }

            return true;
        }
    }

    public static class WorldGenMonumentPiece4 extends WorldGenMonumentPieces.WorldGenMonumentPiece
    {
        public WorldGenMonumentPiece4()
        {
        }

        public WorldGenMonumentPiece4(EnumDirection p_i760_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_i760_2_, Random p_i760_3_)
        {
            super(1, p_i760_1_, p_i760_2_, 2, 2, 1);
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker = this.k.b[EnumDirection.EAST.a()];
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker1 = this.k;
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker2 = worldgenmonumentpieces$worldgenmonumentstatetracker1.b[EnumDirection.UP.a()];
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker3 = worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()];

            if (this.k.a / 25 > 0)
            {
                this.a(p_a_1_, p_a_3_, 8, 0, worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.DOWN.a()]);
                this.a(p_a_1_, p_a_3_, 0, 0, worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.DOWN.a()]);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker2.b[EnumDirection.UP.a()] == null)
            {
                this.a(p_a_1_, p_a_3_, 1, 8, 1, 7, 8, 6, a);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker3.b[EnumDirection.UP.a()] == null)
            {
                this.a(p_a_1_, p_a_3_, 8, 8, 1, 14, 8, 6, a);
            }

            for (int i = 1; i <= 7; ++i)
            {
                IBlockData iblockdata = b;

                if (i == 2 || i == 6)
                {
                    iblockdata = a;
                }

                this.a(p_a_1_, p_a_3_, 0, i, 0, 0, i, 7, iblockdata, iblockdata, false);
                this.a(p_a_1_, p_a_3_, 15, i, 0, 15, i, 7, iblockdata, iblockdata, false);
                this.a(p_a_1_, p_a_3_, 1, i, 0, 15, i, 0, iblockdata, iblockdata, false);
                this.a(p_a_1_, p_a_3_, 1, i, 7, 14, i, 7, iblockdata, iblockdata, false);
            }

            this.a(p_a_1_, p_a_3_, 2, 1, 3, 2, 7, 4, b, b, false);
            this.a(p_a_1_, p_a_3_, 3, 1, 2, 4, 7, 2, b, b, false);
            this.a(p_a_1_, p_a_3_, 3, 1, 5, 4, 7, 5, b, b, false);
            this.a(p_a_1_, p_a_3_, 13, 1, 3, 13, 7, 4, b, b, false);
            this.a(p_a_1_, p_a_3_, 11, 1, 2, 12, 7, 2, b, b, false);
            this.a(p_a_1_, p_a_3_, 11, 1, 5, 12, 7, 5, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 1, 3, 5, 3, 4, b, b, false);
            this.a(p_a_1_, p_a_3_, 10, 1, 3, 10, 3, 4, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 7, 2, 10, 7, 5, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 5, 2, 5, 7, 2, b, b, false);
            this.a(p_a_1_, p_a_3_, 10, 5, 2, 10, 7, 2, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 5, 5, 5, 7, 5, b, b, false);
            this.a(p_a_1_, p_a_3_, 10, 5, 5, 10, 7, 5, b, b, false);
            this.a(p_a_1_, b, 6, 6, 2, p_a_3_);
            this.a(p_a_1_, b, 9, 6, 2, p_a_3_);
            this.a(p_a_1_, b, 6, 6, 5, p_a_3_);
            this.a(p_a_1_, b, 9, 6, 5, p_a_3_);
            this.a(p_a_1_, p_a_3_, 5, 4, 3, 6, 4, 4, b, b, false);
            this.a(p_a_1_, p_a_3_, 9, 4, 3, 10, 4, 4, b, b, false);
            this.a(p_a_1_, e, 5, 4, 2, p_a_3_);
            this.a(p_a_1_, e, 5, 4, 5, p_a_3_);
            this.a(p_a_1_, e, 10, 4, 2, p_a_3_);
            this.a(p_a_1_, e, 10, 4, 5, p_a_3_);

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.SOUTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 3, 1, 0, 4, 2, 0, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.NORTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 3, 1, 7, 4, 2, 7, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.WEST.a()])
            {
                this.a(p_a_1_, p_a_3_, 0, 1, 3, 0, 2, 4, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.SOUTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 11, 1, 0, 12, 2, 0, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.NORTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 11, 1, 7, 12, 2, 7, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.EAST.a()])
            {
                this.a(p_a_1_, p_a_3_, 15, 1, 3, 15, 2, 4, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker2.c[EnumDirection.SOUTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 3, 5, 0, 4, 6, 0, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker2.c[EnumDirection.NORTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 3, 5, 7, 4, 6, 7, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker2.c[EnumDirection.WEST.a()])
            {
                this.a(p_a_1_, p_a_3_, 0, 5, 3, 0, 6, 4, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker3.c[EnumDirection.SOUTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 11, 5, 0, 12, 6, 0, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker3.c[EnumDirection.NORTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 11, 5, 7, 12, 6, 7, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker3.c[EnumDirection.EAST.a()])
            {
                this.a(p_a_1_, p_a_3_, 15, 5, 3, 15, 6, 4, false);
            }

            return true;
        }
    }

    public static class WorldGenMonumentPiece5 extends WorldGenMonumentPieces.WorldGenMonumentPiece
    {
        public WorldGenMonumentPiece5()
        {
        }

        public WorldGenMonumentPiece5(EnumDirection p_i761_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_i761_2_, Random p_i761_3_)
        {
            super(1, p_i761_1_, p_i761_2_, 1, 2, 1);
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (this.k.a / 25 > 0)
            {
                this.a(p_a_1_, p_a_3_, 0, 0, this.k.c[EnumDirection.DOWN.a()]);
            }

            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker = this.k.b[EnumDirection.UP.a()];

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()] == null)
            {
                this.a(p_a_1_, p_a_3_, 1, 8, 1, 6, 8, 6, a);
            }

            this.a(p_a_1_, p_a_3_, 0, 4, 0, 0, 4, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 7, 4, 0, 7, 4, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 4, 0, 6, 4, 0, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 4, 7, 6, 4, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 2, 4, 1, 2, 4, 2, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 4, 2, 1, 4, 2, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 4, 1, 5, 4, 2, b, b, false);
            this.a(p_a_1_, p_a_3_, 6, 4, 2, 6, 4, 2, b, b, false);
            this.a(p_a_1_, p_a_3_, 2, 4, 5, 2, 4, 6, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 4, 5, 1, 4, 5, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 4, 5, 5, 4, 6, b, b, false);
            this.a(p_a_1_, p_a_3_, 6, 4, 5, 6, 4, 5, b, b, false);
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker1 = this.k;

            for (int i = 1; i <= 5; i += 4)
            {
                byte b0 = 0;

                if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.SOUTH.a()])
                {
                    this.a(p_a_1_, p_a_3_, 2, i, b0, 2, i + 2, b0, b, b, false);
                    this.a(p_a_1_, p_a_3_, 5, i, b0, 5, i + 2, b0, b, b, false);
                    this.a(p_a_1_, p_a_3_, 3, i + 2, b0, 4, i + 2, b0, b, b, false);
                }
                else
                {
                    this.a(p_a_1_, p_a_3_, 0, i, b0, 7, i + 2, b0, b, b, false);
                    this.a(p_a_1_, p_a_3_, 0, i + 1, b0, 7, i + 1, b0, a, a, false);
                }

                b0 = 7;

                if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.NORTH.a()])
                {
                    this.a(p_a_1_, p_a_3_, 2, i, b0, 2, i + 2, b0, b, b, false);
                    this.a(p_a_1_, p_a_3_, 5, i, b0, 5, i + 2, b0, b, b, false);
                    this.a(p_a_1_, p_a_3_, 3, i + 2, b0, 4, i + 2, b0, b, b, false);
                }
                else
                {
                    this.a(p_a_1_, p_a_3_, 0, i, b0, 7, i + 2, b0, b, b, false);
                    this.a(p_a_1_, p_a_3_, 0, i + 1, b0, 7, i + 1, b0, a, a, false);
                }

                byte b1 = 0;

                if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.WEST.a()])
                {
                    this.a(p_a_1_, p_a_3_, b1, i, 2, b1, i + 2, 2, b, b, false);
                    this.a(p_a_1_, p_a_3_, b1, i, 5, b1, i + 2, 5, b, b, false);
                    this.a(p_a_1_, p_a_3_, b1, i + 2, 3, b1, i + 2, 4, b, b, false);
                }
                else
                {
                    this.a(p_a_1_, p_a_3_, b1, i, 0, b1, i + 2, 7, b, b, false);
                    this.a(p_a_1_, p_a_3_, b1, i + 1, 0, b1, i + 1, 7, a, a, false);
                }

                b1 = 7;

                if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.EAST.a()])
                {
                    this.a(p_a_1_, p_a_3_, b1, i, 2, b1, i + 2, 2, b, b, false);
                    this.a(p_a_1_, p_a_3_, b1, i, 5, b1, i + 2, 5, b, b, false);
                    this.a(p_a_1_, p_a_3_, b1, i + 2, 3, b1, i + 2, 4, b, b, false);
                }
                else
                {
                    this.a(p_a_1_, p_a_3_, b1, i, 0, b1, i + 2, 7, b, b, false);
                    this.a(p_a_1_, p_a_3_, b1, i + 1, 0, b1, i + 1, 7, a, a, false);
                }

                worldgenmonumentpieces$worldgenmonumentstatetracker1 = worldgenmonumentpieces$worldgenmonumentstatetracker;
            }

            return true;
        }
    }

    public static class WorldGenMonumentPiece6 extends WorldGenMonumentPieces.WorldGenMonumentPiece
    {
        public WorldGenMonumentPiece6()
        {
        }

        public WorldGenMonumentPiece6(EnumDirection p_i762_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_i762_2_, Random p_i762_3_)
        {
            super(1, p_i762_1_, p_i762_2_, 1, 2, 2);
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker = this.k.b[EnumDirection.NORTH.a()];
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker1 = this.k;
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker2 = worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()];
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker3 = worldgenmonumentpieces$worldgenmonumentstatetracker1.b[EnumDirection.UP.a()];

            if (this.k.a / 25 > 0)
            {
                this.a(p_a_1_, p_a_3_, 0, 8, worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.DOWN.a()]);
                this.a(p_a_1_, p_a_3_, 0, 0, worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.DOWN.a()]);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker3.b[EnumDirection.UP.a()] == null)
            {
                this.a(p_a_1_, p_a_3_, 1, 8, 1, 6, 8, 7, a);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker2.b[EnumDirection.UP.a()] == null)
            {
                this.a(p_a_1_, p_a_3_, 1, 8, 8, 6, 8, 14, a);
            }

            for (int i = 1; i <= 7; ++i)
            {
                IBlockData iblockdata = b;

                if (i == 2 || i == 6)
                {
                    iblockdata = a;
                }

                this.a(p_a_1_, p_a_3_, 0, i, 0, 0, i, 15, iblockdata, iblockdata, false);
                this.a(p_a_1_, p_a_3_, 7, i, 0, 7, i, 15, iblockdata, iblockdata, false);
                this.a(p_a_1_, p_a_3_, 1, i, 0, 6, i, 0, iblockdata, iblockdata, false);
                this.a(p_a_1_, p_a_3_, 1, i, 15, 6, i, 15, iblockdata, iblockdata, false);
            }

            for (int j = 1; j <= 7; ++j)
            {
                IBlockData iblockdata1 = c;

                if (j == 2 || j == 6)
                {
                    iblockdata1 = e;
                }

                this.a(p_a_1_, p_a_3_, 3, j, 7, 4, j, 8, iblockdata1, iblockdata1, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.SOUTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 3, 1, 0, 4, 2, 0, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.EAST.a()])
            {
                this.a(p_a_1_, p_a_3_, 7, 1, 3, 7, 2, 4, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.WEST.a()])
            {
                this.a(p_a_1_, p_a_3_, 0, 1, 3, 0, 2, 4, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.NORTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 3, 1, 15, 4, 2, 15, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.WEST.a()])
            {
                this.a(p_a_1_, p_a_3_, 0, 1, 11, 0, 2, 12, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.EAST.a()])
            {
                this.a(p_a_1_, p_a_3_, 7, 1, 11, 7, 2, 12, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker3.c[EnumDirection.SOUTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 3, 5, 0, 4, 6, 0, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker3.c[EnumDirection.EAST.a()])
            {
                this.a(p_a_1_, p_a_3_, 7, 5, 3, 7, 6, 4, false);
                this.a(p_a_1_, p_a_3_, 5, 4, 2, 6, 4, 5, b, b, false);
                this.a(p_a_1_, p_a_3_, 6, 1, 2, 6, 3, 2, b, b, false);
                this.a(p_a_1_, p_a_3_, 6, 1, 5, 6, 3, 5, b, b, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker3.c[EnumDirection.WEST.a()])
            {
                this.a(p_a_1_, p_a_3_, 0, 5, 3, 0, 6, 4, false);
                this.a(p_a_1_, p_a_3_, 1, 4, 2, 2, 4, 5, b, b, false);
                this.a(p_a_1_, p_a_3_, 1, 1, 2, 1, 3, 2, b, b, false);
                this.a(p_a_1_, p_a_3_, 1, 1, 5, 1, 3, 5, b, b, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker2.c[EnumDirection.NORTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 3, 5, 15, 4, 6, 15, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker2.c[EnumDirection.WEST.a()])
            {
                this.a(p_a_1_, p_a_3_, 0, 5, 11, 0, 6, 12, false);
                this.a(p_a_1_, p_a_3_, 1, 4, 10, 2, 4, 13, b, b, false);
                this.a(p_a_1_, p_a_3_, 1, 1, 10, 1, 3, 10, b, b, false);
                this.a(p_a_1_, p_a_3_, 1, 1, 13, 1, 3, 13, b, b, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker2.c[EnumDirection.EAST.a()])
            {
                this.a(p_a_1_, p_a_3_, 7, 5, 11, 7, 6, 12, false);
                this.a(p_a_1_, p_a_3_, 5, 4, 10, 6, 4, 13, b, b, false);
                this.a(p_a_1_, p_a_3_, 6, 1, 10, 6, 3, 10, b, b, false);
                this.a(p_a_1_, p_a_3_, 6, 1, 13, 6, 3, 13, b, b, false);
            }

            return true;
        }
    }

    public static class WorldGenMonumentPiece7 extends WorldGenMonumentPieces.WorldGenMonumentPiece
    {
        public WorldGenMonumentPiece7()
        {
        }

        public WorldGenMonumentPiece7(EnumDirection p_i763_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_i763_2_, Random p_i763_3_)
        {
            super(1, p_i763_1_, p_i763_2_, 1, 1, 2);
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker = this.k.b[EnumDirection.NORTH.a()];
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker1 = this.k;

            if (this.k.a / 25 > 0)
            {
                this.a(p_a_1_, p_a_3_, 0, 8, worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.DOWN.a()]);
                this.a(p_a_1_, p_a_3_, 0, 0, worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.DOWN.a()]);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.b[EnumDirection.UP.a()] == null)
            {
                this.a(p_a_1_, p_a_3_, 1, 4, 1, 6, 4, 7, a);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()] == null)
            {
                this.a(p_a_1_, p_a_3_, 1, 4, 8, 6, 4, 14, a);
            }

            this.a(p_a_1_, p_a_3_, 0, 3, 0, 0, 3, 15, b, b, false);
            this.a(p_a_1_, p_a_3_, 7, 3, 0, 7, 3, 15, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 3, 0, 7, 3, 0, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 3, 15, 6, 3, 15, b, b, false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 0, 2, 15, a, a, false);
            this.a(p_a_1_, p_a_3_, 7, 2, 0, 7, 2, 15, a, a, false);
            this.a(p_a_1_, p_a_3_, 1, 2, 0, 7, 2, 0, a, a, false);
            this.a(p_a_1_, p_a_3_, 1, 2, 15, 6, 2, 15, a, a, false);
            this.a(p_a_1_, p_a_3_, 0, 1, 0, 0, 1, 15, b, b, false);
            this.a(p_a_1_, p_a_3_, 7, 1, 0, 7, 1, 15, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 1, 0, 7, 1, 0, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 1, 15, 6, 1, 15, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 1, 1, 1, 1, 2, b, b, false);
            this.a(p_a_1_, p_a_3_, 6, 1, 1, 6, 1, 2, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 3, 1, 1, 3, 2, b, b, false);
            this.a(p_a_1_, p_a_3_, 6, 3, 1, 6, 3, 2, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 1, 13, 1, 1, 14, b, b, false);
            this.a(p_a_1_, p_a_3_, 6, 1, 13, 6, 1, 14, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 3, 13, 1, 3, 14, b, b, false);
            this.a(p_a_1_, p_a_3_, 6, 3, 13, 6, 3, 14, b, b, false);
            this.a(p_a_1_, p_a_3_, 2, 1, 6, 2, 3, 6, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 1, 6, 5, 3, 6, b, b, false);
            this.a(p_a_1_, p_a_3_, 2, 1, 9, 2, 3, 9, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 1, 9, 5, 3, 9, b, b, false);
            this.a(p_a_1_, p_a_3_, 3, 2, 6, 4, 2, 6, b, b, false);
            this.a(p_a_1_, p_a_3_, 3, 2, 9, 4, 2, 9, b, b, false);
            this.a(p_a_1_, p_a_3_, 2, 2, 7, 2, 2, 8, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 2, 7, 5, 2, 8, b, b, false);
            this.a(p_a_1_, e, 2, 2, 5, p_a_3_);
            this.a(p_a_1_, e, 5, 2, 5, p_a_3_);
            this.a(p_a_1_, e, 2, 2, 10, p_a_3_);
            this.a(p_a_1_, e, 5, 2, 10, p_a_3_);
            this.a(p_a_1_, b, 2, 3, 5, p_a_3_);
            this.a(p_a_1_, b, 5, 3, 5, p_a_3_);
            this.a(p_a_1_, b, 2, 3, 10, p_a_3_);
            this.a(p_a_1_, b, 5, 3, 10, p_a_3_);

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.SOUTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 3, 1, 0, 4, 2, 0, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.EAST.a()])
            {
                this.a(p_a_1_, p_a_3_, 7, 1, 3, 7, 2, 4, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker1.c[EnumDirection.WEST.a()])
            {
                this.a(p_a_1_, p_a_3_, 0, 1, 3, 0, 2, 4, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.NORTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 3, 1, 15, 4, 2, 15, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.WEST.a()])
            {
                this.a(p_a_1_, p_a_3_, 0, 1, 11, 0, 2, 12, false);
            }

            if (worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.EAST.a()])
            {
                this.a(p_a_1_, p_a_3_, 7, 1, 11, 7, 2, 12, false);
            }

            return true;
        }
    }

    public static class WorldGenMonumentPiece8 extends WorldGenMonumentPieces.WorldGenMonumentPiece
    {
        private int o;

        public WorldGenMonumentPiece8()
        {
        }

        public WorldGenMonumentPiece8(EnumDirection p_i771_1_, StructureBoundingBox p_i771_2_, int p_i771_3_)
        {
            super(p_i771_1_, p_i771_2_);
            this.o = p_i771_3_ & 1;
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (this.o == 0)
            {
                for (int i = 0; i < 4; ++i)
                {
                    this.a(p_a_1_, p_a_3_, 10 - i, 3 - i, 20 - i, 12 + i, 3 - i, 20, b, b, false);
                }

                this.a(p_a_1_, p_a_3_, 7, 0, 6, 15, 0, 16, b, b, false);
                this.a(p_a_1_, p_a_3_, 6, 0, 6, 6, 3, 20, b, b, false);
                this.a(p_a_1_, p_a_3_, 16, 0, 6, 16, 3, 20, b, b, false);
                this.a(p_a_1_, p_a_3_, 7, 1, 7, 7, 1, 20, b, b, false);
                this.a(p_a_1_, p_a_3_, 15, 1, 7, 15, 1, 20, b, b, false);
                this.a(p_a_1_, p_a_3_, 7, 1, 6, 9, 3, 6, b, b, false);
                this.a(p_a_1_, p_a_3_, 13, 1, 6, 15, 3, 6, b, b, false);
                this.a(p_a_1_, p_a_3_, 8, 1, 7, 9, 1, 7, b, b, false);
                this.a(p_a_1_, p_a_3_, 13, 1, 7, 14, 1, 7, b, b, false);
                this.a(p_a_1_, p_a_3_, 9, 0, 5, 13, 0, 5, b, b, false);
                this.a(p_a_1_, p_a_3_, 10, 0, 7, 12, 0, 7, c, c, false);
                this.a(p_a_1_, p_a_3_, 8, 0, 10, 8, 0, 12, c, c, false);
                this.a(p_a_1_, p_a_3_, 14, 0, 10, 14, 0, 12, c, c, false);

                for (int k = 18; k >= 7; k -= 3)
                {
                    this.a(p_a_1_, e, 6, 3, k, p_a_3_);
                    this.a(p_a_1_, e, 16, 3, k, p_a_3_);
                }

                this.a(p_a_1_, e, 10, 0, 10, p_a_3_);
                this.a(p_a_1_, e, 12, 0, 10, p_a_3_);
                this.a(p_a_1_, e, 10, 0, 12, p_a_3_);
                this.a(p_a_1_, e, 12, 0, 12, p_a_3_);
                this.a(p_a_1_, e, 8, 3, 6, p_a_3_);
                this.a(p_a_1_, e, 14, 3, 6, p_a_3_);
                this.a(p_a_1_, b, 4, 2, 4, p_a_3_);
                this.a(p_a_1_, e, 4, 1, 4, p_a_3_);
                this.a(p_a_1_, b, 4, 0, 4, p_a_3_);
                this.a(p_a_1_, b, 18, 2, 4, p_a_3_);
                this.a(p_a_1_, e, 18, 1, 4, p_a_3_);
                this.a(p_a_1_, b, 18, 0, 4, p_a_3_);
                this.a(p_a_1_, b, 4, 2, 18, p_a_3_);
                this.a(p_a_1_, e, 4, 1, 18, p_a_3_);
                this.a(p_a_1_, b, 4, 0, 18, p_a_3_);
                this.a(p_a_1_, b, 18, 2, 18, p_a_3_);
                this.a(p_a_1_, e, 18, 1, 18, p_a_3_);
                this.a(p_a_1_, b, 18, 0, 18, p_a_3_);
                this.a(p_a_1_, b, 9, 7, 20, p_a_3_);
                this.a(p_a_1_, b, 13, 7, 20, p_a_3_);
                this.a(p_a_1_, p_a_3_, 6, 0, 21, 7, 4, 21, b, b, false);
                this.a(p_a_1_, p_a_3_, 15, 0, 21, 16, 4, 21, b, b, false);
                this.a(p_a_1_, p_a_3_, 11, 2, 16);
            }
            else if (this.o == 1)
            {
                this.a(p_a_1_, p_a_3_, 9, 3, 18, 13, 3, 20, b, b, false);
                this.a(p_a_1_, p_a_3_, 9, 0, 18, 9, 2, 18, b, b, false);
                this.a(p_a_1_, p_a_3_, 13, 0, 18, 13, 2, 18, b, b, false);
                byte b2 = 9;
                byte b0 = 20;
                byte b1 = 5;

                for (int j = 0; j < 2; ++j)
                {
                    this.a(p_a_1_, b, b2, b1 + 1, b0, p_a_3_);
                    this.a(p_a_1_, e, b2, b1, b0, p_a_3_);
                    this.a(p_a_1_, b, b2, b1 - 1, b0, p_a_3_);
                    b2 = 13;
                }

                this.a(p_a_1_, p_a_3_, 7, 3, 7, 15, 3, 14, b, b, false);
                b2 = 10;

                for (int l = 0; l < 2; ++l)
                {
                    this.a(p_a_1_, p_a_3_, b2, 0, 10, b2, 6, 10, b, b, false);
                    this.a(p_a_1_, p_a_3_, b2, 0, 12, b2, 6, 12, b, b, false);
                    this.a(p_a_1_, e, b2, 0, 10, p_a_3_);
                    this.a(p_a_1_, e, b2, 0, 12, p_a_3_);
                    this.a(p_a_1_, e, b2, 4, 10, p_a_3_);
                    this.a(p_a_1_, e, b2, 4, 12, p_a_3_);
                    b2 = 12;
                }

                b2 = 8;

                for (int i1 = 0; i1 < 2; ++i1)
                {
                    this.a(p_a_1_, p_a_3_, b2, 0, 7, b2, 2, 7, b, b, false);
                    this.a(p_a_1_, p_a_3_, b2, 0, 14, b2, 2, 14, b, b, false);
                    b2 = 14;
                }

                this.a(p_a_1_, p_a_3_, 8, 3, 8, 8, 3, 13, c, c, false);
                this.a(p_a_1_, p_a_3_, 14, 3, 8, 14, 3, 13, c, c, false);
                this.a(p_a_1_, p_a_3_, 11, 5, 13);
            }

            return true;
        }
    }

    public static class WorldGenMonumentPieceEntry extends WorldGenMonumentPieces.WorldGenMonumentPiece
    {
        public WorldGenMonumentPieceEntry()
        {
        }

        public WorldGenMonumentPieceEntry(EnumDirection p_i764_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_i764_2_)
        {
            super(1, p_i764_1_, p_i764_2_, 1, 1, 1);
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 0, 3, 0, 2, 3, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 3, 0, 7, 3, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 1, 2, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 6, 2, 0, 7, 2, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 0, 1, 0, 0, 1, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 7, 1, 0, 7, 1, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 0, 1, 7, 7, 3, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 1, 0, 2, 3, 0, b, b, false);
            this.a(p_a_1_, p_a_3_, 5, 1, 0, 6, 3, 0, b, b, false);

            if (this.k.c[EnumDirection.NORTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 3, 1, 7, 4, 2, 7, false);
            }

            if (this.k.c[EnumDirection.WEST.a()])
            {
                this.a(p_a_1_, p_a_3_, 0, 1, 3, 1, 2, 4, false);
            }

            if (this.k.c[EnumDirection.EAST.a()])
            {
                this.a(p_a_1_, p_a_3_, 6, 1, 3, 7, 2, 4, false);
            }

            return true;
        }
    }

    public static class WorldGenMonumentPiecePenthouse extends WorldGenMonumentPieces.WorldGenMonumentPiece
    {
        public WorldGenMonumentPiecePenthouse()
        {
        }

        public WorldGenMonumentPiecePenthouse(EnumDirection p_i765_1_, StructureBoundingBox p_i765_2_)
        {
            super(p_i765_1_, p_i765_2_);
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            this.a(p_a_1_, p_a_3_, 2, -1, 2, 11, -1, 11, b, b, false);
            this.a(p_a_1_, p_a_3_, 0, -1, 0, 1, -1, 11, a, a, false);
            this.a(p_a_1_, p_a_3_, 12, -1, 0, 13, -1, 11, a, a, false);
            this.a(p_a_1_, p_a_3_, 2, -1, 0, 11, -1, 1, a, a, false);
            this.a(p_a_1_, p_a_3_, 2, -1, 12, 11, -1, 13, a, a, false);
            this.a(p_a_1_, p_a_3_, 0, 0, 0, 0, 0, 13, b, b, false);
            this.a(p_a_1_, p_a_3_, 13, 0, 0, 13, 0, 13, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 0, 0, 12, 0, 0, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 0, 13, 12, 0, 13, b, b, false);

            for (int i = 2; i <= 11; i += 3)
            {
                this.a(p_a_1_, e, 0, 0, i, p_a_3_);
                this.a(p_a_1_, e, 13, 0, i, p_a_3_);
                this.a(p_a_1_, e, i, 0, 0, p_a_3_);
            }

            this.a(p_a_1_, p_a_3_, 2, 0, 3, 4, 0, 9, b, b, false);
            this.a(p_a_1_, p_a_3_, 9, 0, 3, 11, 0, 9, b, b, false);
            this.a(p_a_1_, p_a_3_, 4, 0, 9, 9, 0, 11, b, b, false);
            this.a(p_a_1_, b, 5, 0, 8, p_a_3_);
            this.a(p_a_1_, b, 8, 0, 8, p_a_3_);
            this.a(p_a_1_, b, 10, 0, 10, p_a_3_);
            this.a(p_a_1_, b, 3, 0, 10, p_a_3_);
            this.a(p_a_1_, p_a_3_, 3, 0, 3, 3, 0, 7, c, c, false);
            this.a(p_a_1_, p_a_3_, 10, 0, 3, 10, 0, 7, c, c, false);
            this.a(p_a_1_, p_a_3_, 6, 0, 10, 7, 0, 10, c, c, false);
            byte b0 = 3;

            for (int j = 0; j < 2; ++j)
            {
                for (int k = 2; k <= 8; k += 3)
                {
                    this.a(p_a_1_, p_a_3_, b0, 0, k, b0, 2, k, b, b, false);
                }

                b0 = 10;
            }

            this.a(p_a_1_, p_a_3_, 5, 0, 10, 5, 2, 10, b, b, false);
            this.a(p_a_1_, p_a_3_, 8, 0, 10, 8, 2, 10, b, b, false);
            this.a(p_a_1_, p_a_3_, 6, -1, 7, 7, -1, 8, c, c, false);
            this.a(p_a_1_, p_a_3_, 6, -1, 3, 7, -1, 4, false);
            this.a(p_a_1_, p_a_3_, 6, 1, 6);
            return true;
        }
    }

    static class WorldGenMonumentPieceSelector1 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector
    {
        private WorldGenMonumentPieceSelector1()
        {
        }

        public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker p_a_1_)
        {
            return !p_a_1_.c[EnumDirection.WEST.a()] && !p_a_1_.c[EnumDirection.EAST.a()] && !p_a_1_.c[EnumDirection.NORTH.a()] && !p_a_1_.c[EnumDirection.SOUTH.a()] && !p_a_1_.c[EnumDirection.UP.a()];
        }

        public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection p_a_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_a_2_, Random p_a_3_)
        {
            p_a_2_.d = true;
            return new WorldGenMonumentPieces.WorldGenMonumentPieceSimpleT(p_a_1_, p_a_2_, p_a_3_);
        }
    }

    static class WorldGenMonumentPieceSelector2 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector
    {
        private WorldGenMonumentPieceSelector2()
        {
        }

        public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker p_a_1_)
        {
            return true;
        }

        public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection p_a_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_a_2_, Random p_a_3_)
        {
            p_a_2_.d = true;
            return new WorldGenMonumentPieces.WorldGenMonumentPieceSimple(p_a_1_, p_a_2_, p_a_3_);
        }
    }

    static class WorldGenMonumentPieceSelector3 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector
    {
        private WorldGenMonumentPieceSelector3()
        {
        }

        public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker p_a_1_)
        {
            return p_a_1_.c[EnumDirection.NORTH.a()] && !p_a_1_.b[EnumDirection.NORTH.a()].d;
        }

        public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection p_a_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_a_2_, Random p_a_3_)
        {
            WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker = p_a_2_;

            if (!p_a_2_.c[EnumDirection.NORTH.a()] || p_a_2_.b[EnumDirection.NORTH.a()].d)
            {
                worldgenmonumentpieces$worldgenmonumentstatetracker = p_a_2_.b[EnumDirection.SOUTH.a()];
            }

            worldgenmonumentpieces$worldgenmonumentstatetracker.d = true;
            worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.NORTH.a()].d = true;
            return new WorldGenMonumentPieces.WorldGenMonumentPiece7(p_a_1_, worldgenmonumentpieces$worldgenmonumentstatetracker, p_a_3_);
        }
    }

    static class WorldGenMonumentPieceSelector4 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector
    {
        private WorldGenMonumentPieceSelector4()
        {
        }

        public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker p_a_1_)
        {
            if (p_a_1_.c[EnumDirection.NORTH.a()] && !p_a_1_.b[EnumDirection.NORTH.a()].d && p_a_1_.c[EnumDirection.UP.a()] && !p_a_1_.b[EnumDirection.UP.a()].d)
            {
                WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker = p_a_1_.b[EnumDirection.NORTH.a()];
                return worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.UP.a()] && !worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()].d;
            }
            else
            {
                return false;
            }
        }

        public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection p_a_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_a_2_, Random p_a_3_)
        {
            p_a_2_.d = true;
            p_a_2_.b[EnumDirection.NORTH.a()].d = true;
            p_a_2_.b[EnumDirection.UP.a()].d = true;
            p_a_2_.b[EnumDirection.NORTH.a()].b[EnumDirection.UP.a()].d = true;
            return new WorldGenMonumentPieces.WorldGenMonumentPiece6(p_a_1_, p_a_2_, p_a_3_);
        }
    }

    static class WorldGenMonumentPieceSelector5 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector
    {
        private WorldGenMonumentPieceSelector5()
        {
        }

        public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker p_a_1_)
        {
            return p_a_1_.c[EnumDirection.UP.a()] && !p_a_1_.b[EnumDirection.UP.a()].d;
        }

        public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection p_a_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_a_2_, Random p_a_3_)
        {
            p_a_2_.d = true;
            p_a_2_.b[EnumDirection.UP.a()].d = true;
            return new WorldGenMonumentPieces.WorldGenMonumentPiece5(p_a_1_, p_a_2_, p_a_3_);
        }
    }

    static class WorldGenMonumentPieceSelector6 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector
    {
        private WorldGenMonumentPieceSelector6()
        {
        }

        public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker p_a_1_)
        {
            if (p_a_1_.c[EnumDirection.EAST.a()] && !p_a_1_.b[EnumDirection.EAST.a()].d && p_a_1_.c[EnumDirection.UP.a()] && !p_a_1_.b[EnumDirection.UP.a()].d)
            {
                WorldGenMonumentPieces.WorldGenMonumentStateTracker worldgenmonumentpieces$worldgenmonumentstatetracker = p_a_1_.b[EnumDirection.EAST.a()];
                return worldgenmonumentpieces$worldgenmonumentstatetracker.c[EnumDirection.UP.a()] && !worldgenmonumentpieces$worldgenmonumentstatetracker.b[EnumDirection.UP.a()].d;
            }
            else
            {
                return false;
            }
        }

        public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection p_a_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_a_2_, Random p_a_3_)
        {
            p_a_2_.d = true;
            p_a_2_.b[EnumDirection.EAST.a()].d = true;
            p_a_2_.b[EnumDirection.UP.a()].d = true;
            p_a_2_.b[EnumDirection.EAST.a()].b[EnumDirection.UP.a()].d = true;
            return new WorldGenMonumentPieces.WorldGenMonumentPiece4(p_a_1_, p_a_2_, p_a_3_);
        }
    }

    static class WorldGenMonumentPieceSelector7 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector
    {
        private WorldGenMonumentPieceSelector7()
        {
        }

        public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker p_a_1_)
        {
            return p_a_1_.c[EnumDirection.EAST.a()] && !p_a_1_.b[EnumDirection.EAST.a()].d;
        }

        public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection p_a_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_a_2_, Random p_a_3_)
        {
            p_a_2_.d = true;
            p_a_2_.b[EnumDirection.EAST.a()].d = true;
            return new WorldGenMonumentPieces.WorldGenMonumentPiece3(p_a_1_, p_a_2_, p_a_3_);
        }
    }

    public static class WorldGenMonumentPieceSimple extends WorldGenMonumentPieces.WorldGenMonumentPiece
    {
        private int o;

        public WorldGenMonumentPieceSimple()
        {
        }

        public WorldGenMonumentPieceSimple(EnumDirection p_i769_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_i769_2_, Random p_i769_3_)
        {
            super(1, p_i769_1_, p_i769_2_, 1, 1, 1);
            this.o = p_i769_3_.nextInt(3);
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (this.k.a / 25 > 0)
            {
                this.a(p_a_1_, p_a_3_, 0, 0, this.k.c[EnumDirection.DOWN.a()]);
            }

            if (this.k.b[EnumDirection.UP.a()] == null)
            {
                this.a(p_a_1_, p_a_3_, 1, 4, 1, 6, 4, 6, a);
            }

            boolean flag = this.o != 0 && p_a_2_.nextBoolean() && !this.k.c[EnumDirection.DOWN.a()] && !this.k.c[EnumDirection.UP.a()] && this.k.c() > 1;

            if (this.o == 0)
            {
                this.a(p_a_1_, p_a_3_, 0, 1, 0, 2, 1, 2, b, b, false);
                this.a(p_a_1_, p_a_3_, 0, 3, 0, 2, 3, 2, b, b, false);
                this.a(p_a_1_, p_a_3_, 0, 2, 0, 0, 2, 2, a, a, false);
                this.a(p_a_1_, p_a_3_, 1, 2, 0, 2, 2, 0, a, a, false);
                this.a(p_a_1_, e, 1, 2, 1, p_a_3_);
                this.a(p_a_1_, p_a_3_, 5, 1, 0, 7, 1, 2, b, b, false);
                this.a(p_a_1_, p_a_3_, 5, 3, 0, 7, 3, 2, b, b, false);
                this.a(p_a_1_, p_a_3_, 7, 2, 0, 7, 2, 2, a, a, false);
                this.a(p_a_1_, p_a_3_, 5, 2, 0, 6, 2, 0, a, a, false);
                this.a(p_a_1_, e, 6, 2, 1, p_a_3_);
                this.a(p_a_1_, p_a_3_, 0, 1, 5, 2, 1, 7, b, b, false);
                this.a(p_a_1_, p_a_3_, 0, 3, 5, 2, 3, 7, b, b, false);
                this.a(p_a_1_, p_a_3_, 0, 2, 5, 0, 2, 7, a, a, false);
                this.a(p_a_1_, p_a_3_, 1, 2, 7, 2, 2, 7, a, a, false);
                this.a(p_a_1_, e, 1, 2, 6, p_a_3_);
                this.a(p_a_1_, p_a_3_, 5, 1, 5, 7, 1, 7, b, b, false);
                this.a(p_a_1_, p_a_3_, 5, 3, 5, 7, 3, 7, b, b, false);
                this.a(p_a_1_, p_a_3_, 7, 2, 5, 7, 2, 7, a, a, false);
                this.a(p_a_1_, p_a_3_, 5, 2, 7, 6, 2, 7, a, a, false);
                this.a(p_a_1_, e, 6, 2, 6, p_a_3_);

                if (this.k.c[EnumDirection.SOUTH.a()])
                {
                    this.a(p_a_1_, p_a_3_, 3, 3, 0, 4, 3, 0, b, b, false);
                }
                else
                {
                    this.a(p_a_1_, p_a_3_, 3, 3, 0, 4, 3, 1, b, b, false);
                    this.a(p_a_1_, p_a_3_, 3, 2, 0, 4, 2, 0, a, a, false);
                    this.a(p_a_1_, p_a_3_, 3, 1, 0, 4, 1, 1, b, b, false);
                }

                if (this.k.c[EnumDirection.NORTH.a()])
                {
                    this.a(p_a_1_, p_a_3_, 3, 3, 7, 4, 3, 7, b, b, false);
                }
                else
                {
                    this.a(p_a_1_, p_a_3_, 3, 3, 6, 4, 3, 7, b, b, false);
                    this.a(p_a_1_, p_a_3_, 3, 2, 7, 4, 2, 7, a, a, false);
                    this.a(p_a_1_, p_a_3_, 3, 1, 6, 4, 1, 7, b, b, false);
                }

                if (this.k.c[EnumDirection.WEST.a()])
                {
                    this.a(p_a_1_, p_a_3_, 0, 3, 3, 0, 3, 4, b, b, false);
                }
                else
                {
                    this.a(p_a_1_, p_a_3_, 0, 3, 3, 1, 3, 4, b, b, false);
                    this.a(p_a_1_, p_a_3_, 0, 2, 3, 0, 2, 4, a, a, false);
                    this.a(p_a_1_, p_a_3_, 0, 1, 3, 1, 1, 4, b, b, false);
                }

                if (this.k.c[EnumDirection.EAST.a()])
                {
                    this.a(p_a_1_, p_a_3_, 7, 3, 3, 7, 3, 4, b, b, false);
                }
                else
                {
                    this.a(p_a_1_, p_a_3_, 6, 3, 3, 7, 3, 4, b, b, false);
                    this.a(p_a_1_, p_a_3_, 7, 2, 3, 7, 2, 4, a, a, false);
                    this.a(p_a_1_, p_a_3_, 6, 1, 3, 7, 1, 4, b, b, false);
                }
            }
            else if (this.o == 1)
            {
                this.a(p_a_1_, p_a_3_, 2, 1, 2, 2, 3, 2, b, b, false);
                this.a(p_a_1_, p_a_3_, 2, 1, 5, 2, 3, 5, b, b, false);
                this.a(p_a_1_, p_a_3_, 5, 1, 5, 5, 3, 5, b, b, false);
                this.a(p_a_1_, p_a_3_, 5, 1, 2, 5, 3, 2, b, b, false);
                this.a(p_a_1_, e, 2, 2, 2, p_a_3_);
                this.a(p_a_1_, e, 2, 2, 5, p_a_3_);
                this.a(p_a_1_, e, 5, 2, 5, p_a_3_);
                this.a(p_a_1_, e, 5, 2, 2, p_a_3_);
                this.a(p_a_1_, p_a_3_, 0, 1, 0, 1, 3, 0, b, b, false);
                this.a(p_a_1_, p_a_3_, 0, 1, 1, 0, 3, 1, b, b, false);
                this.a(p_a_1_, p_a_3_, 0, 1, 7, 1, 3, 7, b, b, false);
                this.a(p_a_1_, p_a_3_, 0, 1, 6, 0, 3, 6, b, b, false);
                this.a(p_a_1_, p_a_3_, 6, 1, 7, 7, 3, 7, b, b, false);
                this.a(p_a_1_, p_a_3_, 7, 1, 6, 7, 3, 6, b, b, false);
                this.a(p_a_1_, p_a_3_, 6, 1, 0, 7, 3, 0, b, b, false);
                this.a(p_a_1_, p_a_3_, 7, 1, 1, 7, 3, 1, b, b, false);
                this.a(p_a_1_, a, 1, 2, 0, p_a_3_);
                this.a(p_a_1_, a, 0, 2, 1, p_a_3_);
                this.a(p_a_1_, a, 1, 2, 7, p_a_3_);
                this.a(p_a_1_, a, 0, 2, 6, p_a_3_);
                this.a(p_a_1_, a, 6, 2, 7, p_a_3_);
                this.a(p_a_1_, a, 7, 2, 6, p_a_3_);
                this.a(p_a_1_, a, 6, 2, 0, p_a_3_);
                this.a(p_a_1_, a, 7, 2, 1, p_a_3_);

                if (!this.k.c[EnumDirection.SOUTH.a()])
                {
                    this.a(p_a_1_, p_a_3_, 1, 3, 0, 6, 3, 0, b, b, false);
                    this.a(p_a_1_, p_a_3_, 1, 2, 0, 6, 2, 0, a, a, false);
                    this.a(p_a_1_, p_a_3_, 1, 1, 0, 6, 1, 0, b, b, false);
                }

                if (!this.k.c[EnumDirection.NORTH.a()])
                {
                    this.a(p_a_1_, p_a_3_, 1, 3, 7, 6, 3, 7, b, b, false);
                    this.a(p_a_1_, p_a_3_, 1, 2, 7, 6, 2, 7, a, a, false);
                    this.a(p_a_1_, p_a_3_, 1, 1, 7, 6, 1, 7, b, b, false);
                }

                if (!this.k.c[EnumDirection.WEST.a()])
                {
                    this.a(p_a_1_, p_a_3_, 0, 3, 1, 0, 3, 6, b, b, false);
                    this.a(p_a_1_, p_a_3_, 0, 2, 1, 0, 2, 6, a, a, false);
                    this.a(p_a_1_, p_a_3_, 0, 1, 1, 0, 1, 6, b, b, false);
                }

                if (!this.k.c[EnumDirection.EAST.a()])
                {
                    this.a(p_a_1_, p_a_3_, 7, 3, 1, 7, 3, 6, b, b, false);
                    this.a(p_a_1_, p_a_3_, 7, 2, 1, 7, 2, 6, a, a, false);
                    this.a(p_a_1_, p_a_3_, 7, 1, 1, 7, 1, 6, b, b, false);
                }
            }
            else if (this.o == 2)
            {
                this.a(p_a_1_, p_a_3_, 0, 1, 0, 0, 1, 7, b, b, false);
                this.a(p_a_1_, p_a_3_, 7, 1, 0, 7, 1, 7, b, b, false);
                this.a(p_a_1_, p_a_3_, 1, 1, 0, 6, 1, 0, b, b, false);
                this.a(p_a_1_, p_a_3_, 1, 1, 7, 6, 1, 7, b, b, false);
                this.a(p_a_1_, p_a_3_, 0, 2, 0, 0, 2, 7, c, c, false);
                this.a(p_a_1_, p_a_3_, 7, 2, 0, 7, 2, 7, c, c, false);
                this.a(p_a_1_, p_a_3_, 1, 2, 0, 6, 2, 0, c, c, false);
                this.a(p_a_1_, p_a_3_, 1, 2, 7, 6, 2, 7, c, c, false);
                this.a(p_a_1_, p_a_3_, 0, 3, 0, 0, 3, 7, b, b, false);
                this.a(p_a_1_, p_a_3_, 7, 3, 0, 7, 3, 7, b, b, false);
                this.a(p_a_1_, p_a_3_, 1, 3, 0, 6, 3, 0, b, b, false);
                this.a(p_a_1_, p_a_3_, 1, 3, 7, 6, 3, 7, b, b, false);
                this.a(p_a_1_, p_a_3_, 0, 1, 3, 0, 2, 4, c, c, false);
                this.a(p_a_1_, p_a_3_, 7, 1, 3, 7, 2, 4, c, c, false);
                this.a(p_a_1_, p_a_3_, 3, 1, 0, 4, 2, 0, c, c, false);
                this.a(p_a_1_, p_a_3_, 3, 1, 7, 4, 2, 7, c, c, false);

                if (this.k.c[EnumDirection.SOUTH.a()])
                {
                    this.a(p_a_1_, p_a_3_, 3, 1, 0, 4, 2, 0, false);
                }

                if (this.k.c[EnumDirection.NORTH.a()])
                {
                    this.a(p_a_1_, p_a_3_, 3, 1, 7, 4, 2, 7, false);
                }

                if (this.k.c[EnumDirection.WEST.a()])
                {
                    this.a(p_a_1_, p_a_3_, 0, 1, 3, 0, 2, 4, false);
                }

                if (this.k.c[EnumDirection.EAST.a()])
                {
                    this.a(p_a_1_, p_a_3_, 7, 1, 3, 7, 2, 4, false);
                }
            }

            if (flag)
            {
                this.a(p_a_1_, p_a_3_, 3, 1, 3, 4, 1, 4, b, b, false);
                this.a(p_a_1_, p_a_3_, 3, 2, 3, 4, 2, 4, a, a, false);
                this.a(p_a_1_, p_a_3_, 3, 3, 3, 4, 3, 4, b, b, false);
            }

            return true;
        }
    }

    public static class WorldGenMonumentPieceSimpleT extends WorldGenMonumentPieces.WorldGenMonumentPiece
    {
        public WorldGenMonumentPieceSimpleT()
        {
        }

        public WorldGenMonumentPieceSimpleT(EnumDirection p_i770_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_i770_2_, Random p_i770_3_)
        {
            super(1, p_i770_1_, p_i770_2_, 1, 1, 1);
        }

        public boolean a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (this.k.a / 25 > 0)
            {
                this.a(p_a_1_, p_a_3_, 0, 0, this.k.c[EnumDirection.DOWN.a()]);
            }

            if (this.k.b[EnumDirection.UP.a()] == null)
            {
                this.a(p_a_1_, p_a_3_, 1, 4, 1, 6, 4, 6, a);
            }

            for (int i = 1; i <= 6; ++i)
            {
                for (int j = 1; j <= 6; ++j)
                {
                    if (p_a_2_.nextInt(3) != 0)
                    {
                        int k = 2 + (p_a_2_.nextInt(4) == 0 ? 0 : 1);
                        this.a(p_a_1_, p_a_3_, i, k, j, i, 3, j, Blocks.SPONGE.fromLegacyData(1), Blocks.SPONGE.fromLegacyData(1), false);
                    }
                }
            }

            this.a(p_a_1_, p_a_3_, 0, 1, 0, 0, 1, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 7, 1, 0, 7, 1, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 1, 0, 6, 1, 0, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 1, 7, 6, 1, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 0, 2, 0, 0, 2, 7, c, c, false);
            this.a(p_a_1_, p_a_3_, 7, 2, 0, 7, 2, 7, c, c, false);
            this.a(p_a_1_, p_a_3_, 1, 2, 0, 6, 2, 0, c, c, false);
            this.a(p_a_1_, p_a_3_, 1, 2, 7, 6, 2, 7, c, c, false);
            this.a(p_a_1_, p_a_3_, 0, 3, 0, 0, 3, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 7, 3, 0, 7, 3, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 3, 0, 6, 3, 0, b, b, false);
            this.a(p_a_1_, p_a_3_, 1, 3, 7, 6, 3, 7, b, b, false);
            this.a(p_a_1_, p_a_3_, 0, 1, 3, 0, 2, 4, c, c, false);
            this.a(p_a_1_, p_a_3_, 7, 1, 3, 7, 2, 4, c, c, false);
            this.a(p_a_1_, p_a_3_, 3, 1, 0, 4, 2, 0, c, c, false);
            this.a(p_a_1_, p_a_3_, 3, 1, 7, 4, 2, 7, c, c, false);

            if (this.k.c[EnumDirection.SOUTH.a()])
            {
                this.a(p_a_1_, p_a_3_, 3, 1, 0, 4, 2, 0, false);
            }

            return true;
        }
    }

    static class WorldGenMonumentStateTracker
    {
        int a;
        WorldGenMonumentPieces.WorldGenMonumentStateTracker[] b = new WorldGenMonumentPieces.WorldGenMonumentStateTracker[6];
        boolean[] c = new boolean[6];
        boolean d;
        boolean e;
        int f;

        public WorldGenMonumentStateTracker(int p_i772_1_)
        {
            this.a = p_i772_1_;
        }

        public void a(EnumDirection p_a_1_, WorldGenMonumentPieces.WorldGenMonumentStateTracker p_a_2_)
        {
            this.b[p_a_1_.a()] = p_a_2_;
            p_a_2_.b[p_a_1_.opposite().a()] = this;
        }

        public void a()
        {
            for (int i = 0; i < 6; ++i)
            {
                this.c[i] = this.b[i] != null;
            }
        }

        public boolean a(int p_a_1_)
        {
            if (this.e)
            {
                return true;
            }
            else
            {
                this.f = p_a_1_;

                for (int i = 0; i < 6; ++i)
                {
                    if (this.b[i] != null && this.c[i] && this.b[i].f != p_a_1_ && this.b[i].a(p_a_1_))
                    {
                        return true;
                    }
                }

                return false;
            }
        }

        public boolean b()
        {
            return this.a >= 75;
        }

        public int c()
        {
            int i = 0;

            for (int j = 0; j < 6; ++j)
            {
                if (this.c[j])
                {
                    ++i;
                }
            }

            return i;
        }
    }
}
