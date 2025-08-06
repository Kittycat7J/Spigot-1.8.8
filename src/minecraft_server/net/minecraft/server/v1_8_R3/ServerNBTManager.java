package net.minecraft.server.v1_8_R3;

import java.io.File;

public class ServerNBTManager extends WorldNBTStorage
{
    public ServerNBTManager(File p_i838_1_, String p_i838_2_, boolean p_i838_3_)
    {
        super(p_i838_1_, p_i838_2_, p_i838_3_);
    }

    public IChunkLoader createChunkLoader(WorldProvider p_createChunkLoader_1_)
    {
        File file1 = this.getDirectory();

        if (p_createChunkLoader_1_ instanceof WorldProviderHell)
        {
            File file3 = new File(file1, "DIM-1");
            file3.mkdirs();
            return new ChunkRegionLoader(file3);
        }
        else if (p_createChunkLoader_1_ instanceof WorldProviderTheEnd)
        {
            File file2 = new File(file1, "DIM1");
            file2.mkdirs();
            return new ChunkRegionLoader(file2);
        }
        else
        {
            return new ChunkRegionLoader(file1);
        }
    }

    public void saveWorldData(WorldData p_saveWorldData_1_, NBTTagCompound p_saveWorldData_2_)
    {
        p_saveWorldData_1_.e(19133);
        super.saveWorldData(p_saveWorldData_1_, p_saveWorldData_2_);
    }

    public void a()
    {
        try
        {
            FileIOThread.a().b();
        }
        catch (InterruptedException interruptedexception)
        {
            interruptedexception.printStackTrace();
        }

        RegionFileCache.a();
    }
}
