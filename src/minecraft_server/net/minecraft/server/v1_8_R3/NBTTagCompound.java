package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

public class NBTTagCompound extends NBTBase
{
    private Map<String, NBTBase> map = Maps.<String, NBTBase>newHashMap();

    void write(DataOutput p_write_1_) throws IOException
    {
        for (String s : this.map.keySet())
        {
            NBTBase nbtbase = (NBTBase)this.map.get(s);
            a(s, nbtbase, p_write_1_);
        }

        p_write_1_.writeByte(0);
    }

    void load(DataInput p_load_1_, int p_load_2_, NBTReadLimiter p_load_3_) throws IOException
    {
        p_load_3_.a(384L);

        if (p_load_2_ > 512)
        {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        else
        {
            this.map.clear();
            byte b0;

            while ((b0 = a(p_load_1_, p_load_3_)) != 0)
            {
                String s = b(p_load_1_, p_load_3_);
                p_load_3_.a((long)(224 + 16 * s.length()));
                NBTBase nbtbase = a(b0, s, p_load_1_, p_load_2_ + 1, p_load_3_);

                if (this.map.put(s, nbtbase) != null)
                {
                    p_load_3_.a(288L);
                }
            }
        }
    }

    public Set<String> c()
    {
        return this.map.keySet();
    }

    public byte getTypeId()
    {
        return (byte)10;
    }

    public void set(String p_set_1_, NBTBase p_set_2_)
    {
        this.map.put(p_set_1_, p_set_2_);
    }

    public void setByte(String p_setByte_1_, byte p_setByte_2_)
    {
        this.map.put(p_setByte_1_, new NBTTagByte(p_setByte_2_));
    }

    public void setShort(String p_setShort_1_, short p_setShort_2_)
    {
        this.map.put(p_setShort_1_, new NBTTagShort(p_setShort_2_));
    }

    public void setInt(String p_setInt_1_, int p_setInt_2_)
    {
        this.map.put(p_setInt_1_, new NBTTagInt(p_setInt_2_));
    }

    public void setLong(String p_setLong_1_, long p_setLong_2_)
    {
        this.map.put(p_setLong_1_, new NBTTagLong(p_setLong_2_));
    }

    public void setFloat(String p_setFloat_1_, float p_setFloat_2_)
    {
        this.map.put(p_setFloat_1_, new NBTTagFloat(p_setFloat_2_));
    }

    public void setDouble(String p_setDouble_1_, double p_setDouble_2_)
    {
        this.map.put(p_setDouble_1_, new NBTTagDouble(p_setDouble_2_));
    }

    public void setString(String p_setString_1_, String p_setString_2_)
    {
        this.map.put(p_setString_1_, new NBTTagString(p_setString_2_));
    }

    public void setByteArray(String p_setByteArray_1_, byte[] p_setByteArray_2_)
    {
        this.map.put(p_setByteArray_1_, new NBTTagByteArray(p_setByteArray_2_));
    }

    public void setIntArray(String p_setIntArray_1_, int[] p_setIntArray_2_)
    {
        this.map.put(p_setIntArray_1_, new NBTTagIntArray(p_setIntArray_2_));
    }

    public void setBoolean(String p_setBoolean_1_, boolean p_setBoolean_2_)
    {
        this.setByte(p_setBoolean_1_, (byte)(p_setBoolean_2_ ? 1 : 0));
    }

    public NBTBase get(String p_get_1_)
    {
        return (NBTBase)this.map.get(p_get_1_);
    }

    public byte b(String p_b_1_)
    {
        NBTBase nbtbase = (NBTBase)this.map.get(p_b_1_);
        return nbtbase != null ? nbtbase.getTypeId() : 0;
    }

    public boolean hasKey(String p_hasKey_1_)
    {
        return this.map.containsKey(p_hasKey_1_);
    }

    public boolean hasKeyOfType(String p_hasKeyOfType_1_, int p_hasKeyOfType_2_)
    {
        byte b0 = this.b(p_hasKeyOfType_1_);

        if (b0 == p_hasKeyOfType_2_)
        {
            return true;
        }
        else if (p_hasKeyOfType_2_ != 99)
        {
            if (b0 > 0)
            {
                ;
            }

            return false;
        }
        else
        {
            return b0 == 1 || b0 == 2 || b0 == 3 || b0 == 4 || b0 == 5 || b0 == 6;
        }
    }

    public byte getByte(String p_getByte_1_)
    {
        try
        {
            return !this.hasKeyOfType(p_getByte_1_, 99) ? 0 : ((NBTBase.NBTNumber)this.map.get(p_getByte_1_)).f();
        }
        catch (ClassCastException var3)
        {
            return (byte)0;
        }
    }

    public short getShort(String p_getShort_1_)
    {
        try
        {
            return !this.hasKeyOfType(p_getShort_1_, 99) ? 0 : ((NBTBase.NBTNumber)this.map.get(p_getShort_1_)).e();
        }
        catch (ClassCastException var3)
        {
            return (short)0;
        }
    }

    public int getInt(String p_getInt_1_)
    {
        try
        {
            return !this.hasKeyOfType(p_getInt_1_, 99) ? 0 : ((NBTBase.NBTNumber)this.map.get(p_getInt_1_)).d();
        }
        catch (ClassCastException var3)
        {
            return 0;
        }
    }

    public long getLong(String p_getLong_1_)
    {
        try
        {
            return !this.hasKeyOfType(p_getLong_1_, 99) ? 0L : ((NBTBase.NBTNumber)this.map.get(p_getLong_1_)).c();
        }
        catch (ClassCastException var3)
        {
            return 0L;
        }
    }

    public float getFloat(String p_getFloat_1_)
    {
        try
        {
            return !this.hasKeyOfType(p_getFloat_1_, 99) ? 0.0F : ((NBTBase.NBTNumber)this.map.get(p_getFloat_1_)).h();
        }
        catch (ClassCastException var3)
        {
            return 0.0F;
        }
    }

    public double getDouble(String p_getDouble_1_)
    {
        try
        {
            return !this.hasKeyOfType(p_getDouble_1_, 99) ? 0.0D : ((NBTBase.NBTNumber)this.map.get(p_getDouble_1_)).g();
        }
        catch (ClassCastException var3)
        {
            return 0.0D;
        }
    }

    public String getString(String p_getString_1_)
    {
        try
        {
            return !this.hasKeyOfType(p_getString_1_, 8) ? "" : ((NBTBase)this.map.get(p_getString_1_)).a_();
        }
        catch (ClassCastException var3)
        {
            return "";
        }
    }

    public byte[] getByteArray(String p_getByteArray_1_)
    {
        try
        {
            return !this.hasKeyOfType(p_getByteArray_1_, 7) ? new byte[0] : ((NBTTagByteArray)this.map.get(p_getByteArray_1_)).c();
        }
        catch (ClassCastException classcastexception)
        {
            throw new ReportedException(this.a(p_getByteArray_1_, 7, classcastexception));
        }
    }

    public int[] getIntArray(String p_getIntArray_1_)
    {
        try
        {
            return !this.hasKeyOfType(p_getIntArray_1_, 11) ? new int[0] : ((NBTTagIntArray)this.map.get(p_getIntArray_1_)).c();
        }
        catch (ClassCastException classcastexception)
        {
            throw new ReportedException(this.a(p_getIntArray_1_, 11, classcastexception));
        }
    }

    public NBTTagCompound getCompound(String p_getCompound_1_)
    {
        try
        {
            return !this.hasKeyOfType(p_getCompound_1_, 10) ? new NBTTagCompound() : (NBTTagCompound)this.map.get(p_getCompound_1_);
        }
        catch (ClassCastException classcastexception)
        {
            throw new ReportedException(this.a(p_getCompound_1_, 10, classcastexception));
        }
    }

    public NBTTagList getList(String p_getList_1_, int p_getList_2_)
    {
        try
        {
            if (this.b(p_getList_1_) != 9)
            {
                return new NBTTagList();
            }
            else
            {
                NBTTagList nbttaglist = (NBTTagList)this.map.get(p_getList_1_);
                return nbttaglist.size() > 0 && nbttaglist.f() != p_getList_2_ ? new NBTTagList() : nbttaglist;
            }
        }
        catch (ClassCastException classcastexception)
        {
            throw new ReportedException(this.a(p_getList_1_, 9, classcastexception));
        }
    }

    public boolean getBoolean(String p_getBoolean_1_)
    {
        return this.getByte(p_getBoolean_1_) != 0;
    }

    public void remove(String p_remove_1_)
    {
        this.map.remove(p_remove_1_);
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder("{");

        for (Entry entry : this.map.entrySet())
        {
            if (stringbuilder.length() != 1)
            {
                stringbuilder.append(',');
            }

            stringbuilder.append((String)entry.getKey()).append(':').append(entry.getValue());
        }

        return stringbuilder.append('}').toString();
    }

    public boolean isEmpty()
    {
        return this.map.isEmpty();
    }

    private CrashReport a(final String p_a_1_, final int p_a_2_, ClassCastException p_a_3_)
    {
        CrashReport crashreport = CrashReport.a(p_a_3_, "Reading NBT data");
        CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Corrupt NBT tag", 1);
        crashreportsystemdetails.a("Tag type found", new Callable<String>()
        {
            public String a() throws Exception
            {
                return NBTBase.a[((NBTBase)NBTTagCompound.this.map.get(p_a_1_)).getTypeId()];
            }
        });
        crashreportsystemdetails.a("Tag type expected", new Callable<String>()
        {
            public String a() throws Exception
            {
                return NBTBase.a[p_a_2_];
            }
        });
        crashreportsystemdetails.a((String)"Tag name", p_a_1_);
        return crashreport;
    }

    public NBTBase clone()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        for (String s : this.map.keySet())
        {
            nbttagcompound.set(s, ((NBTBase)this.map.get(s)).clone());
        }

        return nbttagcompound;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (super.equals(p_equals_1_))
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)p_equals_1_;
            return this.map.entrySet().equals(nbttagcompound.map.entrySet());
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return super.hashCode() ^ this.map.hashCode();
    }

    private static void a(String p_a_0_, NBTBase p_a_1_, DataOutput p_a_2_) throws IOException
    {
        p_a_2_.writeByte(p_a_1_.getTypeId());

        if (p_a_1_.getTypeId() != 0)
        {
            p_a_2_.writeUTF(p_a_0_);
            p_a_1_.write(p_a_2_);
        }
    }

    private static byte a(DataInput p_a_0_, NBTReadLimiter p_a_1_) throws IOException
    {
        return p_a_0_.readByte();
    }

    private static String b(DataInput p_b_0_, NBTReadLimiter p_b_1_) throws IOException
    {
        return p_b_0_.readUTF();
    }

    static NBTBase a(byte p_a_0_, String p_a_1_, DataInput p_a_2_, int p_a_3_, NBTReadLimiter p_a_4_) throws IOException
    {
        NBTBase nbtbase = NBTBase.createTag(p_a_0_);

        try
        {
            nbtbase.load(p_a_2_, p_a_3_, p_a_4_);
            return nbtbase;
        }
        catch (IOException ioexception)
        {
            CrashReport crashreport = CrashReport.a(ioexception, "Loading NBT data");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("NBT Tag");
            crashreportsystemdetails.a((String)"Tag name", p_a_1_);
            crashreportsystemdetails.a((String)"Tag type", Byte.valueOf(p_a_0_));
            throw new ReportedException(crashreport);
        }
    }

    public void a(NBTTagCompound p_a_1_)
    {
        for (String s : p_a_1_.map.keySet())
        {
            NBTBase nbtbase = (NBTBase)p_a_1_.map.get(s);

            if (nbtbase.getTypeId() == 10)
            {
                if (this.hasKeyOfType(s, 10))
                {
                    NBTTagCompound nbttagcompound = this.getCompound(s);
                    nbttagcompound.a((NBTTagCompound)nbtbase);
                }
                else
                {
                    this.set(s, nbtbase.clone());
                }
            }
            else
            {
                this.set(s, nbtbase.clone());
            }
        }
    }
}
