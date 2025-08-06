package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class WorldGenLargeFeature extends StructureGenerator
{
    private static final List<BiomeBase> d = Arrays.asList(new BiomeBase[] {BiomeBase.DESERT, BiomeBase.DESERT_HILLS, BiomeBase.JUNGLE, BiomeBase.JUNGLE_HILLS, BiomeBase.SWAMPLAND});
    private List<BiomeBase.BiomeMeta> f;
    private int g;
    private int h;

    public WorldGenLargeFeature()
    {
        this.f = Lists.<BiomeBase.BiomeMeta>newArrayList();
        this.g = 32;
        this.h = 8;
        this.f.add(new BiomeBase.BiomeMeta(EntityWitch.class, 1, 1, 1));
    }

    public WorldGenLargeFeature(Map<String, String> p_i486_1_)
    {
        this();

        for (Entry entry : p_i486_1_.entrySet())
        {
            if (((String)entry.getKey()).equals("distance"))
            {
                this.g = MathHelper.a((String)entry.getValue(), this.g, this.h + 1);
            }
        }
    }

    public String a()
    {
        return "Temple";
    }

    protected boolean a(int p_a_1_, int p_a_2_)
    {
        int i = p_a_1_;
        int j = p_a_2_;

        if (p_a_1_ < 0)
        {
            p_a_1_ -= this.g - 1;
        }

        if (p_a_2_ < 0)
        {
            p_a_2_ -= this.g - 1;
        }

        int k = p_a_1_ / this.g;
        int l = p_a_2_ / this.g;
        Random random = this.c.a(k, l, this.c.spigotConfig.largeFeatureSeed);
        k = k * this.g;
        l = l * this.g;
        k = k + random.nextInt(this.g - this.h);
        l = l + random.nextInt(this.g - this.h);

        if (i == k && j == l)
        {
            BiomeBase biomebase = this.c.getWorldChunkManager().getBiome(new BlockPosition(i * 16 + 8, 0, j * 16 + 8));

            if (biomebase == null)
            {
                return false;
            }

            for (BiomeBase biomebase1 : d)
            {
                if (biomebase == biomebase1)
                {
                    return true;
                }
            }
        }

        return false;
    }

    protected StructureStart b(int p_b_1_, int p_b_2_)
    {
        return new WorldGenLargeFeature.WorldGenLargeFeatureStart(this.c, this.b, p_b_1_, p_b_2_);
    }

    public boolean a(BlockPosition p_a_1_)
    {
        StructureStart structurestart = this.c(p_a_1_);

        if (structurestart != null && structurestart instanceof WorldGenLargeFeature.WorldGenLargeFeatureStart && !structurestart.a.isEmpty())
        {
            StructurePiece structurepiece = (StructurePiece)structurestart.a.getFirst();
            return structurepiece instanceof WorldGenRegistration.WorldGenWitchHut;
        }
        else
        {
            return false;
        }
    }

    public List<BiomeBase.BiomeMeta> b()
    {
        return this.f;
    }

    public static class WorldGenLargeFeatureStart extends StructureStart
    {
        public WorldGenLargeFeatureStart()
        {
        }

        public WorldGenLargeFeatureStart(World p_i99_1_, Random p_i99_2_, int p_i99_3_, int p_i99_4_)
        {
            super(p_i99_3_, p_i99_4_);
            BiomeBase biomebase = p_i99_1_.getBiome(new BlockPosition(p_i99_3_ * 16 + 8, 0, p_i99_4_ * 16 + 8));

            if (biomebase != BiomeBase.JUNGLE && biomebase != BiomeBase.JUNGLE_HILLS)
            {
                if (biomebase == BiomeBase.SWAMPLAND)
                {
                    WorldGenRegistration.WorldGenWitchHut worldgenregistration$worldgenwitchhut = new WorldGenRegistration.WorldGenWitchHut(p_i99_2_, p_i99_3_ * 16, p_i99_4_ * 16);
                    this.a.add(worldgenregistration$worldgenwitchhut);
                }
                else if (biomebase == BiomeBase.DESERT || biomebase == BiomeBase.DESERT_HILLS)
                {
                    WorldGenRegistration.WorldGenPyramidPiece worldgenregistration$worldgenpyramidpiece = new WorldGenRegistration.WorldGenPyramidPiece(p_i99_2_, p_i99_3_ * 16, p_i99_4_ * 16);
                    this.a.add(worldgenregistration$worldgenpyramidpiece);
                }
            }
            else
            {
                WorldGenRegistration.WorldGenJungleTemple worldgenregistration$worldgenjungletemple = new WorldGenRegistration.WorldGenJungleTemple(p_i99_2_, p_i99_3_ * 16, p_i99_4_ * 16);
                this.a.add(worldgenregistration$worldgenjungletemple);
            }

            this.c();
        }
    }
}
