package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class WorldGenStronghold extends StructureGenerator
{
    private List<BiomeBase> d;
    private boolean f;
    private ChunkCoordIntPair[] g;
    private double h;
    private int i;

    public WorldGenStronghold()
    {
        this.g = new ChunkCoordIntPair[3];
        this.h = 32.0D;
        this.i = 3;
        this.d = Lists.<BiomeBase>newArrayList();

        for (BiomeBase biomebase : BiomeBase.getBiomes())
        {
            if (biomebase != null && biomebase.an > 0.0F)
            {
                this.d.add(biomebase);
            }
        }
    }

    public WorldGenStronghold(Map<String, String> p_i775_1_)
    {
        this();

        for (Entry entry : p_i775_1_.entrySet())
        {
            if (((String)entry.getKey()).equals("distance"))
            {
                this.h = MathHelper.a((String)entry.getValue(), this.h, 1.0D);
            }
            else if (((String)entry.getKey()).equals("count"))
            {
                this.g = new ChunkCoordIntPair[MathHelper.a((String)entry.getValue(), this.g.length, 1)];
            }
            else if (((String)entry.getKey()).equals("spread"))
            {
                this.i = MathHelper.a((String)entry.getValue(), this.i, 1);
            }
        }
    }

    public String a()
    {
        return "Stronghold";
    }

    protected boolean a(int p_a_1_, int p_a_2_)
    {
        if (!this.f)
        {
            Random random = new Random();
            random.setSeed(this.c.getSeed());
            double d0 = random.nextDouble() * Math.PI * 2.0D;
            int ix = 1;

            for (int j = 0; j < this.g.length; ++j)
            {
                double d1 = (1.25D * (double)ix + random.nextDouble()) * this.h * (double)ix;
                int k = (int)Math.round(Math.cos(d0) * d1);
                int l = (int)Math.round(Math.sin(d0) * d1);
                BlockPosition blockposition = this.c.getWorldChunkManager().a((k << 4) + 8, (l << 4) + 8, 112, this.d, random);

                if (blockposition != null)
                {
                    k = blockposition.getX() >> 4;
                    l = blockposition.getZ() >> 4;
                }

                this.g[j] = new ChunkCoordIntPair(k, l);
                d0 += (Math.PI * 2D) * (double)ix / (double)this.i;

                if (j == this.i)
                {
                    ix += 2 + random.nextInt(5);
                    this.i += 1 + random.nextInt(2);
                }
            }

            this.f = true;
        }

        for (ChunkCoordIntPair chunkcoordintpair : this.g)
        {
            if (p_a_1_ == chunkcoordintpair.x && p_a_2_ == chunkcoordintpair.z)
            {
                return true;
            }
        }

        return false;
    }

    protected List<BlockPosition> z_()
    {
        ArrayList arraylist = Lists.newArrayList();

        for (ChunkCoordIntPair chunkcoordintpair : this.g)
        {
            if (chunkcoordintpair != null)
            {
                arraylist.add(chunkcoordintpair.a(64));
            }
        }

        return arraylist;
    }

    protected StructureStart b(int p_b_1_, int p_b_2_)
    {
        WorldGenStronghold.WorldGenStronghold2Start worldgenstronghold$worldgenstronghold2start;

        for (worldgenstronghold$worldgenstronghold2start = new WorldGenStronghold.WorldGenStronghold2Start(this.c, this.b, p_b_1_, p_b_2_); worldgenstronghold$worldgenstronghold2start.b().isEmpty() || ((WorldGenStrongholdPieces.WorldGenStrongholdStart)worldgenstronghold$worldgenstronghold2start.b().get(0)).b == null; worldgenstronghold$worldgenstronghold2start = new WorldGenStronghold.WorldGenStronghold2Start(this.c, this.b, p_b_1_, p_b_2_))
        {
            ;
        }

        return worldgenstronghold$worldgenstronghold2start;
    }

    public static class WorldGenStronghold2Start extends StructureStart
    {
        public WorldGenStronghold2Start()
        {
        }

        public WorldGenStronghold2Start(World p_i774_1_, Random p_i774_2_, int p_i774_3_, int p_i774_4_)
        {
            super(p_i774_3_, p_i774_4_);
            WorldGenStrongholdPieces.b();
            WorldGenStrongholdPieces.WorldGenStrongholdStart worldgenstrongholdpieces$worldgenstrongholdstart = new WorldGenStrongholdPieces.WorldGenStrongholdStart(0, p_i774_2_, (p_i774_3_ << 4) + 2, (p_i774_4_ << 4) + 2);
            this.a.add(worldgenstrongholdpieces$worldgenstrongholdstart);
            worldgenstrongholdpieces$worldgenstrongholdstart.a(worldgenstrongholdpieces$worldgenstrongholdstart, this.a, p_i774_2_);
            List list = worldgenstrongholdpieces$worldgenstrongholdstart.c;

            while (!list.isEmpty())
            {
                int i = p_i774_2_.nextInt(list.size());
                StructurePiece structurepiece = (StructurePiece)list.remove(i);
                structurepiece.a((StructurePiece)worldgenstrongholdpieces$worldgenstrongholdstart, this.a, (Random)p_i774_2_);
            }

            this.c();
            this.a(p_i774_1_, p_i774_2_, 10);
        }
    }
}
