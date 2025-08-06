package net.minecraft.server.v1_8_R3;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldLoader implements Convertable
{
    private static final Logger b = LogManager.getLogger();
    protected final File a;

    public WorldLoader(File p_i842_1_)
    {
        if (!p_i842_1_.exists())
        {
            p_i842_1_.mkdirs();
        }

        this.a = p_i842_1_;
    }

    public void d()
    {
    }

    public WorldData c(String p_c_1_)
    {
        File file1 = new File(this.a, p_c_1_);

        if (!file1.exists())
        {
            return null;
        }
        else
        {
            File file2 = new File(file1, "level.dat");

            if (file2.exists())
            {
                try
                {
                    NBTTagCompound nbttagcompound2 = NBTCompressedStreamTools.a((InputStream)(new FileInputStream(file2)));
                    NBTTagCompound nbttagcompound3 = nbttagcompound2.getCompound("Data");
                    return new WorldData(nbttagcompound3);
                }
                catch (Exception exception1)
                {
                    b.error((String)("Exception reading " + file2), (Throwable)exception1);
                }
            }

            file2 = new File(file1, "level.dat_old");

            if (file2.exists())
            {
                try
                {
                    NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a((InputStream)(new FileInputStream(file2)));
                    NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Data");
                    return new WorldData(nbttagcompound1);
                }
                catch (Exception exception)
                {
                    b.error((String)("Exception reading " + file2), (Throwable)exception);
                }
            }

            return null;
        }
    }

    public boolean e(String p_e_1_)
    {
        File file1 = new File(this.a, p_e_1_);

        if (!file1.exists())
        {
            return true;
        }
        else
        {
            b.info("Deleting level " + p_e_1_);

            for (int i = 1; i <= 5; ++i)
            {
                b.info("Attempt " + i + "...");

                if (a(file1.listFiles()))
                {
                    break;
                }

                b.warn("Unsuccessful in deleting contents.");

                if (i < 5)
                {
                    try
                    {
                        Thread.sleep(500L);
                    }
                    catch (InterruptedException var5)
                    {
                        ;
                    }
                }
            }

            return file1.delete();
        }
    }

    protected static boolean a(File[] p_a_0_)
    {
        for (int i = 0; i < p_a_0_.length; ++i)
        {
            File file1 = p_a_0_[i];
            b.debug("Deleting " + file1);

            if (file1.isDirectory() && !a(file1.listFiles()))
            {
                b.warn("Couldn\'t delete directory " + file1);
                return false;
            }

            if (!file1.delete())
            {
                b.warn("Couldn\'t delete file " + file1);
                return false;
            }
        }

        return true;
    }

    public IDataManager a(String p_a_1_, boolean p_a_2_)
    {
        return new WorldNBTStorage(this.a, p_a_1_, p_a_2_);
    }

    public boolean isConvertable(String p_isConvertable_1_)
    {
        return false;
    }

    public boolean convert(String p_convert_1_, IProgressUpdate p_convert_2_)
    {
        return false;
    }
}
