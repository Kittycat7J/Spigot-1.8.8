package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

public class WorldGenMonument extends StructureGenerator
{
    private int f;
    private int g;
    public static final List<BiomeBase> d = Arrays.asList(new BiomeBase[] {BiomeBase.OCEAN, BiomeBase.DEEP_OCEAN, BiomeBase.RIVER, BiomeBase.FROZEN_OCEAN, BiomeBase.FROZEN_RIVER});
    private static final List<BiomeBase.BiomeMeta> h = Lists.<BiomeBase.BiomeMeta>newArrayList();

    public WorldGenMonument()
    {
        this.f = 32;
        this.g = 5;
    }

    public WorldGenMonument(Map<String, String> p_i749_1_)
    {
        this();

        for (Entry entry : p_i749_1_.entrySet())
        {
            if (((String)entry.getKey()).equals("spacing"))
            {
                this.f = MathHelper.a((String)entry.getValue(), this.f, 1);
            }
            else if (((String)entry.getKey()).equals("separation"))
            {
                this.g = MathHelper.a((String)entry.getValue(), this.g, 1);
            }
        }
    }

    public String a()
    {
        return "Monument";
    }

    protected boolean a(int p_a_1_, int p_a_2_)
    {
        int i = p_a_1_;
        int j = p_a_2_;

        if (p_a_1_ < 0)
        {
            p_a_1_ -= this.f - 1;
        }

        if (p_a_2_ < 0)
        {
            p_a_2_ -= this.f - 1;
        }

        int k = p_a_1_ / this.f;
        int l = p_a_2_ / this.f;
        Random random = this.c.a(k, l, 10387313);
        k = k * this.f;
        l = l * this.f;
        k = k + (random.nextInt(this.f - this.g) + random.nextInt(this.f - this.g)) / 2;
        l = l + (random.nextInt(this.f - this.g) + random.nextInt(this.f - this.g)) / 2;

        if (i == k && j == l)
        {
            if (this.c.getWorldChunkManager().getBiome(new BlockPosition(i * 16 + 8, 64, j * 16 + 8), (BiomeBase)null) != BiomeBase.DEEP_OCEAN)
            {
                return false;
            }

            boolean flag = this.c.getWorldChunkManager().a(i * 16 + 8, j * 16 + 8, 29, d);

            if (flag)
            {
                return true;
            }
        }

        return false;
    }

    protected StructureStart b(int p_b_1_, int p_b_2_)
    {
        return new WorldGenMonument.WorldGenMonumentStart(this.c, this.b, p_b_1_, p_b_2_);
    }

    public List<BiomeBase.BiomeMeta> b()
    {
        return h;
    }

    static
    {
        h.add(new BiomeBase.BiomeMeta(EntityGuardian.class, 1, 2, 4));
    }

    public static class WorldGenMonumentStart extends StructureStart
    {
        private Set<ChunkCoordIntPair> c = Sets.<ChunkCoordIntPair>newHashSet();
        private boolean d;

        public WorldGenMonumentStart()
        {
        }

        public WorldGenMonumentStart(World p_i748_1_, Random p_i748_2_, int p_i748_3_, int p_i748_4_)
        {
            super(p_i748_3_, p_i748_4_);
            this.b(p_i748_1_, p_i748_2_, p_i748_3_, p_i748_4_);
        }

        private void b(World p_b_1_, Random p_b_2_, int p_b_3_, int p_b_4_)
        {
            p_b_2_.setSeed(p_b_1_.getSeed());
            long i = p_b_2_.nextLong();
            long j = p_b_2_.nextLong();
            long k = (long)p_b_3_ * i;
            long l = (long)p_b_4_ * j;
            p_b_2_.setSeed(k ^ l ^ p_b_1_.getSeed());
            int i1 = p_b_3_ * 16 + 8 - 29;
            int j1 = p_b_4_ * 16 + 8 - 29;
            EnumDirection enumdirection = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(p_b_2_);
            this.a.add(new WorldGenMonumentPieces.WorldGenMonumentPiece1(p_b_2_, i1, j1, enumdirection));
            this.c();
            this.d = true;
        }

        public void a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
        {
            if (!this.d)
            {
                this.a.clear();
                this.b(p_a_1_, p_a_2_, this.e(), this.f());
            }

            super.a(p_a_1_, p_a_2_, p_a_3_);
        }

        public boolean a(ChunkCoordIntPair p_a_1_)
        {
            return this.c.contains(p_a_1_) ? false : super.a(p_a_1_);
        }

        public void b(ChunkCoordIntPair p_b_1_)
        {
            super.b(p_b_1_);
            this.c.add(p_b_1_);
        }

        public void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            NBTTagList nbttaglist = new NBTTagList();

            for (ChunkCoordIntPair chunkcoordintpair : this.c)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setInt("X", chunkcoordintpair.x);
                nbttagcompound.setInt("Z", chunkcoordintpair.z);
                nbttaglist.add(nbttagcompound);
            }

            p_a_1_.set("Processed", nbttaglist);
        }

        public void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);

            if (p_b_1_.hasKeyOfType("Processed", 9))
            {
                NBTTagList nbttaglist = p_b_1_.getList("Processed", 10);

                for (int i = 0; i < nbttaglist.size(); ++i)
                {
                    NBTTagCompound nbttagcompound = nbttaglist.get(i);
                    this.c.add(new ChunkCoordIntPair(nbttagcompound.getInt("X"), nbttagcompound.getInt("Z")));
                }
            }
        }
    }
}
