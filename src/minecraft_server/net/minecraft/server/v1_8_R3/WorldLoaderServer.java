package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldLoaderServer extends WorldLoader
{
    private static final Logger b = LogManager.getLogger();

    public WorldLoaderServer(File p_i840_1_)
    {
        super(p_i840_1_);
    }

    protected int c()
    {
        return 19133;
    }

    public void d()
    {
        RegionFileCache.a();
    }

    public IDataManager a(String p_a_1_, boolean p_a_2_)
    {
        return new ServerNBTManager(this.a, p_a_1_, p_a_2_);
    }

    public boolean isConvertable(String p_isConvertable_1_)
    {
        WorldData worlddata = this.c(p_isConvertable_1_);
        return worlddata != null && worlddata.l() != this.c();
    }

    public boolean convert(String p_convert_1_, IProgressUpdate p_convert_2_)
    {
        p_convert_2_.a(0);
        ArrayList arraylist = Lists.newArrayList();
        ArrayList arraylist1 = Lists.newArrayList();
        ArrayList arraylist2 = Lists.newArrayList();
        File file1 = new File(this.a, p_convert_1_);
        File file2 = new File(file1, "DIM-1");
        File file3 = new File(file1, "DIM1");
        b.info("Scanning folders...");
        this.a(file1, arraylist);

        if (file2.exists())
        {
            this.a(file2, arraylist1);
        }

        if (file3.exists())
        {
            this.a(file3, arraylist2);
        }

        int i = arraylist.size() + arraylist1.size() + arraylist2.size();
        b.info("Total conversion count is " + i);
        WorldData worlddata = this.c(p_convert_1_);
        Object object = null;

        if (worlddata.getType() == WorldType.FLAT)
        {
            object = new WorldChunkManagerHell(BiomeBase.PLAINS, 0.5F);
        }
        else
        {
            object = new WorldChunkManager(worlddata.getSeed(), worlddata.getType(), worlddata.getGeneratorOptions());
        }

        this.a(new File(file1, "region"), arraylist, (WorldChunkManager)object, 0, i, p_convert_2_);
        this.a(new File(file2, "region"), arraylist1, new WorldChunkManagerHell(BiomeBase.HELL, 0.0F), arraylist.size(), i, p_convert_2_);
        this.a(new File(file3, "region"), arraylist2, new WorldChunkManagerHell(BiomeBase.SKY, 0.0F), arraylist.size() + arraylist1.size(), i, p_convert_2_);
        worlddata.e(19133);

        if (worlddata.getType() == WorldType.NORMAL_1_1)
        {
            worlddata.a(WorldType.NORMAL);
        }

        this.g(p_convert_1_);
        IDataManager idatamanager = this.a(p_convert_1_, false);
        idatamanager.saveWorldData(worlddata);
        return true;
    }

    private void g(String p_g_1_)
    {
        File file1 = new File(this.a, p_g_1_);

        if (!file1.exists())
        {
            b.warn("Unable to create level.dat_mcr backup");
        }
        else
        {
            File file2 = new File(file1, "level.dat");

            if (!file2.exists())
            {
                b.warn("Unable to create level.dat_mcr backup");
            }
            else
            {
                File file3 = new File(file1, "level.dat_mcr");

                if (!file2.renameTo(file3))
                {
                    b.warn("Unable to create level.dat_mcr backup");
                }
            }
        }
    }

    private void a(File p_a_1_, Iterable<File> p_a_2_, WorldChunkManager p_a_3_, int p_a_4_, int p_a_5_, IProgressUpdate p_a_6_)
    {
        for (File file1 : p_a_2_)
        {
            this.a(p_a_1_, file1, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
            ++p_a_4_;
            int i = (int)Math.round(100.0D * (double)p_a_4_ / (double)p_a_5_);
            p_a_6_.a(i);
        }
    }

    private void a(File p_a_1_, File p_a_2_, WorldChunkManager p_a_3_, int p_a_4_, int p_a_5_, IProgressUpdate p_a_6_)
    {
        try
        {
            String s = p_a_2_.getName();
            RegionFile regionfile = new RegionFile(p_a_2_);
            RegionFile regionfile1 = new RegionFile(new File(p_a_1_, s.substring(0, s.length() - ".mcr".length()) + ".mca"));

            for (int i = 0; i < 32; ++i)
            {
                for (int j = 0; j < 32; ++j)
                {
                    if (regionfile.c(i, j) && !regionfile1.c(i, j))
                    {
                        DataInputStream datainputstream = regionfile.a(i, j);

                        if (datainputstream == null)
                        {
                            b.warn("Failed to fetch input stream");
                        }
                        else
                        {
                            NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(datainputstream);
                            datainputstream.close();
                            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Level");
                            OldChunkLoader.OldChunk oldchunkloader$oldchunk = OldChunkLoader.a(nbttagcompound1);
                            NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                            NBTTagCompound nbttagcompound3 = new NBTTagCompound();
                            nbttagcompound2.set("Level", nbttagcompound3);
                            OldChunkLoader.a(oldchunkloader$oldchunk, nbttagcompound3, p_a_3_);
                            DataOutputStream dataoutputstream = regionfile1.b(i, j);
                            NBTCompressedStreamTools.a((NBTTagCompound)nbttagcompound2, (DataOutput)dataoutputstream);
                            dataoutputstream.close();
                        }
                    }
                }

                int k = (int)Math.round(100.0D * (double)(p_a_4_ * 1024) / (double)(p_a_5_ * 1024));
                int l = (int)Math.round(100.0D * (double)((i + 1) * 32 + p_a_4_ * 1024) / (double)(p_a_5_ * 1024));

                if (l > k)
                {
                    p_a_6_.a(l);
                }
            }

            regionfile.c();
            regionfile1.c();
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    private void a(File p_a_1_, Collection<File> p_a_2_)
    {
        File file1 = new File(p_a_1_, "region");
        class ChunkFilenameFilter implements FilenameFilter
        {
            public boolean accept(File p_accept_1_, String p_accept_2_)
            {
                return p_accept_2_.endsWith(".mcr");
            }
        }
        File[] afile = file1.listFiles(new ChunkFilenameFilter());

        if (afile != null)
        {
            Collections.addAll(p_a_2_, afile);
        }
    }
}
