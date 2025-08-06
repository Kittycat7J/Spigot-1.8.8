package net.minecraft.server.v1_8_R3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EULA
{
    private static final Logger a = LogManager.getLogger();
    private final File b;
    private final boolean c;

    public EULA(File p_i1059_1_)
    {
        this.b = p_i1059_1_;
        this.c = this.a(p_i1059_1_);
    }

    private boolean a(File p_a_1_)
    {
        FileInputStream fileinputstream = null;
        boolean flag = false;

        try
        {
            Properties properties = new Properties();
            fileinputstream = new FileInputStream(p_a_1_);
            properties.load(fileinputstream);
            flag = Boolean.parseBoolean(properties.getProperty("eula", "false"));
        }
        catch (Exception var8)
        {
            a.warn("Failed to load " + p_a_1_);
            this.b();
        }
        finally
        {
            IOUtils.closeQuietly((InputStream)fileinputstream);
        }

        return flag;
    }

    public boolean a()
    {
        return this.c;
    }

    public void b()
    {
        FileOutputStream fileoutputstream = null;

        try
        {
            Properties properties = new Properties();
            fileoutputstream = new FileOutputStream(this.b);
            properties.setProperty("eula", "false");
            properties.store(fileoutputstream, "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula).");
        }
        catch (Exception exception)
        {
            a.warn((String)("Failed to save " + this.b), (Throwable)exception);
        }
        finally
        {
            IOUtils.closeQuietly((OutputStream)fileoutputstream);
        }
    }
}
