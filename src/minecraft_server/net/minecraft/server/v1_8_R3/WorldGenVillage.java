package net.minecraft.server.v1_8_R3;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class WorldGenVillage extends StructureGenerator
{
    public static final List<BiomeBase> d = Arrays.asList(new BiomeBase[] {BiomeBase.PLAINS, BiomeBase.DESERT, BiomeBase.SAVANNA});
    private int f;
    private int g;
    private int h;

    public WorldGenVillage()
    {
        this.g = 32;
        this.h = 8;
    }

    public WorldGenVillage(Map<String, String> p_i169_1_)
    {
        this();

        for (Entry entry : p_i169_1_.entrySet())
        {
            if (((String)entry.getKey()).equals("size"))
            {
                this.f = MathHelper.a((String)entry.getValue(), this.f, 0);
            }
            else if (((String)entry.getKey()).equals("distance"))
            {
                this.g = MathHelper.a((String)entry.getValue(), this.g, this.h + 1);
            }
        }
    }

    public String a()
    {
        return "Village";
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
        Random random = this.c.a(k, l, this.c.spigotConfig.villageSeed);
        k = k * this.g;
        l = l * this.g;
        k = k + random.nextInt(this.g - this.h);
        l = l + random.nextInt(this.g - this.h);

        if (i == k && j == l)
        {
            boolean flag = this.c.getWorldChunkManager().a(i * 16 + 8, j * 16 + 8, 0, d);

            if (flag)
            {
                return true;
            }
        }

        return false;
    }

    protected StructureStart b(int p_b_1_, int p_b_2_)
    {
        return new WorldGenVillage.WorldGenVillageStart(this.c, this.b, p_b_1_, p_b_2_, this.f);
    }

    public static class WorldGenVillageStart extends StructureStart
    {
        private boolean c;

        public WorldGenVillageStart()
        {
        }

        public WorldGenVillageStart(World p_i100_1_, Random p_i100_2_, int p_i100_3_, int p_i100_4_, int p_i100_5_)
        {
            super(p_i100_3_, p_i100_4_);
            List list = WorldGenVillagePieces.a(p_i100_2_, p_i100_5_);
            WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces$worldgenvillagestartpiece = new WorldGenVillagePieces.WorldGenVillageStartPiece(p_i100_1_.getWorldChunkManager(), 0, p_i100_2_, (p_i100_3_ << 4) + 2, (p_i100_4_ << 4) + 2, list, p_i100_5_);
            this.a.add(worldgenvillagepieces$worldgenvillagestartpiece);
            worldgenvillagepieces$worldgenvillagestartpiece.a(worldgenvillagepieces$worldgenvillagestartpiece, this.a, p_i100_2_);
            List list1 = worldgenvillagepieces$worldgenvillagestartpiece.g;
            List list2 = worldgenvillagepieces$worldgenvillagestartpiece.f;

            while (!list1.isEmpty() || !list2.isEmpty())
            {
                if (list1.isEmpty())
                {
                    int i = p_i100_2_.nextInt(list2.size());
                    StructurePiece structurepiece = (StructurePiece)list2.remove(i);
                    structurepiece.a((StructurePiece)worldgenvillagepieces$worldgenvillagestartpiece, this.a, (Random)p_i100_2_);
                }
                else
                {
                    int j = p_i100_2_.nextInt(list1.size());
                    StructurePiece structurepiece2 = (StructurePiece)list1.remove(j);
                    structurepiece2.a((StructurePiece)worldgenvillagepieces$worldgenvillagestartpiece, this.a, (Random)p_i100_2_);
                }
            }

            this.c();
            int k = 0;

            for (StructurePiece structurepiece1 : this.a)
            {
                if (!(structurepiece1 instanceof WorldGenVillagePieces.WorldGenVillageRoadPiece))
                {
                    ++k;
                }
            }

            this.c = k > 2;
        }

        public boolean d()
        {
            return this.c;
        }

        public void a(NBTTagCompound p_a_1_)
        {
            super.a(p_a_1_);
            p_a_1_.setBoolean("Valid", this.c);
        }

        public void b(NBTTagCompound p_b_1_)
        {
            super.b(p_b_1_);
            this.c = p_b_1_.getBoolean("Valid");
        }
    }
}
