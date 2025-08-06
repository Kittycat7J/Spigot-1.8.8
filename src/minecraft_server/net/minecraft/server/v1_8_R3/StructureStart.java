package net.minecraft.server.v1_8_R3;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public abstract class StructureStart
{
    protected LinkedList<StructurePiece> a = new LinkedList();
    protected StructureBoundingBox b;
    private int c;
    private int d;

    public StructureStart()
    {
    }

    public StructureStart(int p_i797_1_, int p_i797_2_)
    {
        this.c = p_i797_1_;
        this.d = p_i797_2_;
    }

    public StructureBoundingBox a()
    {
        return this.b;
    }

    public LinkedList<StructurePiece> b()
    {
        return this.a;
    }

    public void a(World p_a_1_, Random p_a_2_, StructureBoundingBox p_a_3_)
    {
        Iterator iterator = this.a.iterator();

        while (iterator.hasNext())
        {
            StructurePiece structurepiece = (StructurePiece)iterator.next();

            if (structurepiece.c().a(p_a_3_) && !structurepiece.a(p_a_1_, p_a_2_, p_a_3_))
            {
                iterator.remove();
            }
        }
    }

    protected void c()
    {
        this.b = StructureBoundingBox.a();

        for (StructurePiece structurepiece : this.a)
        {
            this.b.b(structurepiece.c());
        }
    }

    public NBTTagCompound a(int p_a_1_, int p_a_2_)
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setString("id", WorldGenFactory.a(this));
        nbttagcompound.setInt("ChunkX", p_a_1_);
        nbttagcompound.setInt("ChunkZ", p_a_2_);
        nbttagcompound.set("BB", this.b.g());
        NBTTagList nbttaglist = new NBTTagList();

        for (StructurePiece structurepiece : this.a)
        {
            nbttaglist.add(structurepiece.b());
        }

        nbttagcompound.set("Children", nbttaglist);
        this.a(nbttagcompound);
        return nbttagcompound;
    }

    public void a(NBTTagCompound p_a_1_)
    {
    }

    public void a(World p_a_1_, NBTTagCompound p_a_2_)
    {
        this.c = p_a_2_.getInt("ChunkX");
        this.d = p_a_2_.getInt("ChunkZ");

        if (p_a_2_.hasKey("BB"))
        {
            this.b = new StructureBoundingBox(p_a_2_.getIntArray("BB"));
        }

        NBTTagList nbttaglist = p_a_2_.getList("Children", 10);

        for (int i = 0; i < nbttaglist.size(); ++i)
        {
            this.a.add(WorldGenFactory.b(nbttaglist.get(i), p_a_1_));
        }

        this.b(p_a_2_);
    }

    public void b(NBTTagCompound p_b_1_)
    {
    }

    protected void a(World p_a_1_, Random p_a_2_, int p_a_3_)
    {
        int i = p_a_1_.F() - p_a_3_;
        int j = this.b.d() + 1;

        if (j < i)
        {
            j += p_a_2_.nextInt(i - j);
        }

        int k = j - this.b.e;
        this.b.a(0, k, 0);

        for (StructurePiece structurepiece : this.a)
        {
            structurepiece.a(0, k, 0);
        }
    }

    protected void a(World p_a_1_, Random p_a_2_, int p_a_3_, int p_a_4_)
    {
        int i = p_a_4_ - p_a_3_ + 1 - this.b.d();
        int j = 1;

        if (i > 1)
        {
            j = p_a_3_ + p_a_2_.nextInt(i);
        }
        else
        {
            j = p_a_3_;
        }

        int k = j - this.b.b;
        this.b.a(0, k, 0);

        for (StructurePiece structurepiece : this.a)
        {
            structurepiece.a(0, k, 0);
        }
    }

    public boolean d()
    {
        return true;
    }

    public boolean a(ChunkCoordIntPair p_a_1_)
    {
        return true;
    }

    public void b(ChunkCoordIntPair p_b_1_)
    {
    }

    public int e()
    {
        return this.c;
    }

    public int f()
    {
        return this.d;
    }
}
