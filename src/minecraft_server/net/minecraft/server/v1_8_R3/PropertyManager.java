package net.minecraft.server.v1_8_R3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;

public class PropertyManager
{
    private static final Logger a = LogManager.getLogger();
    public final Properties properties;
    private final File file;
    private OptionSet options;

    public PropertyManager(File p_i130_1_)
    {
        this.properties = new Properties();
        this.options = null;
        this.file = p_i130_1_;

        if (p_i130_1_.exists())
        {
            FileInputStream fileinputstream = null;

            try
            {
                fileinputstream = new FileInputStream(p_i130_1_);
                this.properties.load(fileinputstream);
            }
            catch (Exception exception)
            {
                a.warn((String)("Failed to load " + p_i130_1_), (Throwable)exception);
                this.a();
            }
            finally
            {
                if (fileinputstream != null)
                {
                    try
                    {
                        fileinputstream.close();
                    }
                    catch (IOException var10)
                    {
                        ;
                    }
                }
            }
        }
        else
        {
            a.warn(p_i130_1_ + " does not exist");
            this.a();
        }
    }

    public PropertyManager(OptionSet p_i131_1_)
    {
        this((File)p_i131_1_.valueOf("config"));
        this.options = p_i131_1_;
    }

    private <T> T getOverride(String p_getOverride_1_, T p_getOverride_2_)
    {
        return (T)(this.options != null && this.options.has(p_getOverride_1_) && !p_getOverride_1_.equals("online-mode") ? this.options.valueOf(p_getOverride_1_) : p_getOverride_2_);
    }

    public void a()
    {
        a.info("Generating new properties file");
        this.savePropertiesFile();
    }

    public void savePropertiesFile()
    {
        FileOutputStream fileoutputstream = null;

        try
        {
            if (!this.file.exists() || this.file.canWrite())
            {
                fileoutputstream = new FileOutputStream(this.file);
                this.properties.store(fileoutputstream, "Minecraft server properties");
                return;
            }
        }
        catch (Exception exception)
        {
            a.warn((String)("Failed to save " + this.file), (Throwable)exception);
            this.a();
            return;
        }
        finally
        {
            if (fileoutputstream != null)
            {
                try
                {
                    fileoutputstream.close();
                }
                catch (IOException var10)
                {
                    ;
                }
            }
        }
    }

    public File c()
    {
        return this.file;
    }

    public String getString(String p_getString_1_, String p_getString_2_)
    {
        if (!this.properties.containsKey(p_getString_1_))
        {
            this.properties.setProperty(p_getString_1_, p_getString_2_);
            this.savePropertiesFile();
            this.savePropertiesFile();
        }

        return (String)this.getOverride(p_getString_1_, this.properties.getProperty(p_getString_1_, p_getString_2_));
    }

    public int getInt(String p_getInt_1_, int p_getInt_2_)
    {
        try
        {
            return ((Integer)this.getOverride(p_getInt_1_, Integer.valueOf(Integer.parseInt(this.getString(p_getInt_1_, "" + p_getInt_2_))))).intValue();
        }
        catch (Exception var3)
        {
            this.properties.setProperty(p_getInt_1_, "" + p_getInt_2_);
            this.savePropertiesFile();
            return ((Integer)this.getOverride(p_getInt_1_, Integer.valueOf(p_getInt_2_))).intValue();
        }
    }

    public long getLong(String p_getLong_1_, long p_getLong_2_)
    {
        try
        {
            return ((Long)this.getOverride(p_getLong_1_, Long.valueOf(Long.parseLong(this.getString(p_getLong_1_, "" + p_getLong_2_))))).longValue();
        }
        catch (Exception var4)
        {
            this.properties.setProperty(p_getLong_1_, "" + p_getLong_2_);
            this.savePropertiesFile();
            return ((Long)this.getOverride(p_getLong_1_, Long.valueOf(p_getLong_2_))).longValue();
        }
    }

    public boolean getBoolean(String p_getBoolean_1_, boolean p_getBoolean_2_)
    {
        try
        {
            return ((Boolean)this.getOverride(p_getBoolean_1_, Boolean.valueOf(Boolean.parseBoolean(this.getString(p_getBoolean_1_, "" + p_getBoolean_2_))))).booleanValue();
        }
        catch (Exception var3)
        {
            this.properties.setProperty(p_getBoolean_1_, "" + p_getBoolean_2_);
            this.savePropertiesFile();
            return ((Boolean)this.getOverride(p_getBoolean_1_, Boolean.valueOf(p_getBoolean_2_))).booleanValue();
        }
    }

    public void setProperty(String p_setProperty_1_, Object p_setProperty_2_)
    {
        this.properties.setProperty(p_setProperty_1_, "" + p_setProperty_2_);
    }
}
