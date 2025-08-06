package net.minecraft.server.v1_8_R3;

import io.netty.buffer.ByteBufInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.spigotmc.LimitStream;

public class NBTCompressedStreamTools
{
    public static NBTTagCompound a(InputStream p_a_0_) throws IOException
    {
        DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(p_a_0_)));
        NBTTagCompound nbttagcompound;

        try
        {
            nbttagcompound = a((DataInput)datainputstream, (NBTReadLimiter)NBTReadLimiter.a);
        }
        finally
        {
            datainputstream.close();
        }

        return nbttagcompound;
    }

    public static void a(NBTTagCompound p_a_0_, OutputStream p_a_1_) throws IOException
    {
        DataOutputStream dataoutputstream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(p_a_1_)));

        try
        {
            a((NBTTagCompound)p_a_0_, (DataOutput)dataoutputstream);
        }
        finally
        {
            dataoutputstream.close();
        }
    }

    public static NBTTagCompound a(DataInputStream p_a_0_) throws IOException
    {
        return a((DataInput)p_a_0_, (NBTReadLimiter)NBTReadLimiter.a);
    }

    public static NBTTagCompound a(DataInput p_a_0_, NBTReadLimiter p_a_1_) throws IOException
    {
        if (p_a_0_ instanceof ByteBufInputStream)
        {
            p_a_0_ = new DataInputStream(new LimitStream((InputStream)p_a_0_, p_a_1_));
        }

        NBTBase nbtbase = a((DataInput)p_a_0_, 0, p_a_1_);

        if (nbtbase instanceof NBTTagCompound)
        {
            return (NBTTagCompound)nbtbase;
        }
        else
        {
            throw new IOException("Root tag must be a named compound tag");
        }
    }

    public static void a(NBTTagCompound p_a_0_, DataOutput p_a_1_) throws IOException
    {
        a((NBTBase)p_a_0_, (DataOutput)p_a_1_);
    }

    private static void a(NBTBase p_a_0_, DataOutput p_a_1_) throws IOException
    {
        p_a_1_.writeByte(p_a_0_.getTypeId());

        if (p_a_0_.getTypeId() != 0)
        {
            p_a_1_.writeUTF("");
            p_a_0_.write(p_a_1_);
        }
    }

    private static NBTBase a(DataInput p_a_0_, int p_a_1_, NBTReadLimiter p_a_2_) throws IOException
    {
        byte b0 = p_a_0_.readByte();

        if (b0 == 0)
        {
            return new NBTTagEnd();
        }
        else
        {
            p_a_0_.readUTF();
            NBTBase nbtbase = NBTBase.createTag(b0);

            try
            {
                nbtbase.load(p_a_0_, p_a_1_, p_a_2_);
                return nbtbase;
            }
            catch (IOException ioexception)
            {
                CrashReport crashreport = CrashReport.a(ioexception, "Loading NBT data");
                CrashReportSystemDetails crashreportsystemdetails = crashreport.a("NBT Tag");
                crashreportsystemdetails.a((String)"Tag name", "[UNNAMED TAG]");
                crashreportsystemdetails.a((String)"Tag type", Byte.valueOf(b0));
                throw new ReportedException(crashreport);
            }
        }
    }
}
