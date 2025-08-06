package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

public abstract class StructureGenerator extends WorldGenBase
{
    private PersistentStructure d;
    protected Map<Long, StructureStart> e = Maps.<Long, StructureStart>newHashMap();

    public abstract String a();

    protected final void a(World p_a_1_, final int p_a_2_, final int p_a_3_, int p_a_4_, int p_a_5_, ChunkSnapshot p_a_6_)
    {
        this.a(p_a_1_);

        if (!this.e.containsKey(Long.valueOf(ChunkCoordIntPair.a(p_a_2_, p_a_3_))))
        {
            this.b.nextInt();

            try
            {
                if (this.a(p_a_2_, p_a_3_))
                {
                    StructureStart structurestart = this.b(p_a_2_, p_a_3_);
                    this.e.put(Long.valueOf(ChunkCoordIntPair.a(p_a_2_, p_a_3_)), structurestart);
                    this.a(p_a_2_, p_a_3_, structurestart);
                }
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.a(throwable, "Exception preparing structure feature");
                CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Feature being prepared");
                crashreportsystemdetails.a("Is feature chunk", new Callable()
                {
                    public String a() throws Exception
                    {
                        return StructureGenerator.this.a(p_a_2_, p_a_3_) ? "True" : "False";
                    }
                    public Object call() throws Exception
                    {
                        return this.a();
                    }
                });
                crashreportsystemdetails.a((String)"Chunk location", String.format("%d,%d", new Object[] {Integer.valueOf(p_a_2_), Integer.valueOf(p_a_3_)}));
                crashreportsystemdetails.a("Chunk pos hash", new Callable()
                {
                    public String a() throws Exception
                    {
                        return String.valueOf(ChunkCoordIntPair.a(p_a_2_, p_a_3_));
                    }
                    public Object call() throws Exception
                    {
                        return this.a();
                    }
                });
                crashreportsystemdetails.a("Structure type", new Callable()
                {
                    public String a() throws Exception
                    {
                        return StructureGenerator.this.getClass().getCanonicalName();
                    }
                    public Object call() throws Exception
                    {
                        return this.a();
                    }
                });
                throw new ReportedException(crashreport);
            }
        }
    }

    public boolean a(World p_a_1_, Random p_a_2_, ChunkCoordIntPair p_a_3_)
    {
        this.a(p_a_1_);
        int i = (p_a_3_.x << 4) + 8;
        int j = (p_a_3_.z << 4) + 8;
        boolean flag = false;

        for (StructureStart structurestart : this.e.values())
        {
            if (structurestart.d() && structurestart.a(p_a_3_) && structurestart.a().a(i, j, i + 15, j + 15))
            {
                structurestart.a(p_a_1_, p_a_2_, new StructureBoundingBox(i, j, i + 15, j + 15));
                structurestart.b(p_a_3_);
                flag = true;
                this.a(structurestart.e(), structurestart.f(), structurestart);
            }
        }

        return flag;
    }

    public boolean b(BlockPosition p_b_1_)
    {
        this.a(this.c);
        return this.c(p_b_1_) != null;
    }

    protected StructureStart c(BlockPosition p_c_1_)
    {
        label18:

        for (StructureStart structurestart : this.e.values())
        {
            if (structurestart.d() && structurestart.a().b((BaseBlockPosition)p_c_1_))
            {
                Iterator iterator = structurestart.b().iterator();

                while (true)
                {
                    if (!iterator.hasNext())
                    {
                        continue label18;
                    }

                    StructurePiece structurepiece = (StructurePiece)iterator.next();

                    if (structurepiece.c().b((BaseBlockPosition)p_c_1_))
                    {
                        break;
                    }
                }

                return structurestart;
            }
        }

        return null;
    }

    public boolean a(World p_a_1_, BlockPosition p_a_2_)
    {
        this.a(p_a_1_);

        for (StructureStart structurestart : this.e.values())
        {
            if (structurestart.d() && structurestart.a().b((BaseBlockPosition)p_a_2_))
            {
                return true;
            }
        }

        return false;
    }

    public BlockPosition getNearestGeneratedFeature(World p_getNearestGeneratedFeature_1_, BlockPosition p_getNearestGeneratedFeature_2_)
    {
        this.c = p_getNearestGeneratedFeature_1_;
        this.a(p_getNearestGeneratedFeature_1_);
        this.b.setSeed(p_getNearestGeneratedFeature_1_.getSeed());
        long i = this.b.nextLong();
        long j = this.b.nextLong();
        long k = (long)(p_getNearestGeneratedFeature_2_.getX() >> 4) * i;
        long l = (long)(p_getNearestGeneratedFeature_2_.getZ() >> 4) * j;
        this.b.setSeed(k ^ l ^ p_getNearestGeneratedFeature_1_.getSeed());
        this.a(p_getNearestGeneratedFeature_1_, p_getNearestGeneratedFeature_2_.getX() >> 4, p_getNearestGeneratedFeature_2_.getZ() >> 4, 0, 0, (ChunkSnapshot)null);
        double d0 = Double.MAX_VALUE;
        BlockPosition blockposition = null;

        for (StructureStart structurestart : this.e.values())
        {
            if (structurestart.d())
            {
                StructurePiece structurepiece = (StructurePiece)structurestart.b().get(0);
                BlockPosition blockposition1 = structurepiece.a();
                double d1 = blockposition1.i(p_getNearestGeneratedFeature_2_);

                if (d1 < d0)
                {
                    d0 = d1;
                    blockposition = blockposition1;
                }
            }
        }

        if (blockposition != null)
        {
            return blockposition;
        }
        else
        {
            List list = this.z_();

            if (list != null)
            {
                BlockPosition blockposition2 = null;

                for (BlockPosition blockposition3 : list)
                {
                    double d2 = blockposition3.i(p_getNearestGeneratedFeature_2_);

                    if (d2 < d0)
                    {
                        d0 = d2;
                        blockposition2 = blockposition3;
                    }
                }

                return blockposition2;
            }
            else
            {
                return null;
            }
        }
    }

    protected List<BlockPosition> z_()
    {
        return null;
    }

    private void a(World p_a_1_)
    {
        if (this.d == null)
        {
            if (p_a_1_.spigotConfig.saveStructureInfo && !this.a().equals("Mineshaft"))
            {
                this.d = (PersistentStructure)p_a_1_.a(PersistentStructure.class, this.a());
            }
            else
            {
                this.d = new PersistentStructure(this.a());
            }

            if (this.d == null)
            {
                this.d = new PersistentStructure(this.a());
                p_a_1_.a((String)this.a(), (PersistentBase)this.d);
            }
            else
            {
                NBTTagCompound nbttagcompound = this.d.a();

                for (String s : nbttagcompound.c())
                {
                    NBTBase nbtbase = nbttagcompound.get(s);

                    if (nbtbase.getTypeId() == 10)
                    {
                        NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbtbase;

                        if (nbttagcompound1.hasKey("ChunkX") && nbttagcompound1.hasKey("ChunkZ"))
                        {
                            int i = nbttagcompound1.getInt("ChunkX");
                            int j = nbttagcompound1.getInt("ChunkZ");
                            StructureStart structurestart = WorldGenFactory.a(nbttagcompound1, p_a_1_);

                            if (structurestart != null)
                            {
                                this.e.put(Long.valueOf(ChunkCoordIntPair.a(i, j)), structurestart);
                            }
                        }
                    }
                }
            }
        }
    }

    private void a(int p_a_1_, int p_a_2_, StructureStart p_a_3_)
    {
        this.d.a(p_a_3_.a(p_a_1_, p_a_2_), p_a_1_, p_a_2_);
        this.d.c();
    }

    protected abstract boolean a(int var1, int var2);

    protected abstract StructureStart b(int var1, int var2);
}
