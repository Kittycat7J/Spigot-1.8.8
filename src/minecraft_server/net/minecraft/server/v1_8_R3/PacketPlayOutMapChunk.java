package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;

public class PacketPlayOutMapChunk implements Packet<PacketListenerPlayOut>
{
    private int a;
    private int b;
    private PacketPlayOutMapChunk.ChunkMap c;
    private boolean d;

    public PacketPlayOutMapChunk()
    {
    }

    public PacketPlayOutMapChunk(Chunk p_i401_1_, boolean p_i401_2_, int p_i401_3_)
    {
        this.a = p_i401_1_.locX;
        this.b = p_i401_1_.locZ;
        this.d = p_i401_2_;
        this.c = a(p_i401_1_, p_i401_2_, !p_i401_1_.getWorld().worldProvider.o(), p_i401_3_);
        p_i401_1_.world.spigotConfig.antiXrayInstance.obfuscateSync(p_i401_1_.locX, p_i401_1_.locZ, this.c.b, this.c.a, p_i401_1_.world);
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.readInt();
        this.b = p_a_1_.readInt();
        this.d = p_a_1_.readBoolean();
        this.c = new PacketPlayOutMapChunk.ChunkMap();
        this.c.b = p_a_1_.readShort();
        this.c.a = p_a_1_.a();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeInt(this.a);
        p_b_1_.writeInt(this.b);
        p_b_1_.writeBoolean(this.d);
        p_b_1_.writeShort((short)(this.c.b & 65535));
        p_b_1_.a(this.c.a);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }

    protected static int a(int p_a_0_, boolean p_a_1_, boolean p_a_2_)
    {
        int i = p_a_0_ * 2 * 16 * 16 * 16;
        int j = p_a_0_ * 16 * 16 * 16 / 2;
        int k = p_a_1_ ? p_a_0_ * 16 * 16 * 16 / 2 : 0;
        int l = p_a_2_ ? 256 : 0;
        return i + j + k + l;
    }

    public static PacketPlayOutMapChunk.ChunkMap a(Chunk p_a_0_, boolean p_a_1_, boolean p_a_2_, int p_a_3_)
    {
        ChunkSection[] achunksection = p_a_0_.getSections();
        PacketPlayOutMapChunk.ChunkMap packetplayoutmapchunk$chunkmap = new PacketPlayOutMapChunk.ChunkMap();
        ArrayList arraylist = Lists.newArrayList();

        for (int i = 0; i < achunksection.length; ++i)
        {
            ChunkSection chunksection = achunksection[i];

            if (chunksection != null && (!p_a_1_ || !chunksection.a()) && (p_a_3_ & 1 << i) != 0)
            {
                packetplayoutmapchunk$chunkmap.b |= 1 << i;
                arraylist.add(chunksection);
            }
        }

        packetplayoutmapchunk$chunkmap.a = new byte[a(Integer.bitCount(packetplayoutmapchunk$chunkmap.b), p_a_2_, p_a_1_)];
        int j1 = 0;

        for (ChunkSection chunksection1 : arraylist)
        {
            char[] achar = chunksection1.getIdArray();

            for (char c0 : achar)
            {
                packetplayoutmapchunk$chunkmap.a[j1++] = (byte)(c0 & 255);
                packetplayoutmapchunk$chunkmap.a[j1++] = (byte)(c0 >> 8 & 255);
            }
        }

        for (ChunkSection chunksection2 : arraylist)
        {
            j1 = a(chunksection2.getEmittedLightArray().a(), packetplayoutmapchunk$chunkmap.a, j1);
        }

        if (p_a_2_)
        {
            for (ChunkSection chunksection3 : arraylist)
            {
                j1 = a(chunksection3.getSkyLightArray().a(), packetplayoutmapchunk$chunkmap.a, j1);
            }
        }

        if (p_a_1_)
        {
            a(p_a_0_.getBiomeIndex(), packetplayoutmapchunk$chunkmap.a, j1);
        }

        return packetplayoutmapchunk$chunkmap;
    }

    private static int a(byte[] p_a_0_, byte[] p_a_1_, int p_a_2_)
    {
        System.arraycopy(p_a_0_, 0, p_a_1_, p_a_2_, p_a_0_.length);
        return p_a_2_ + p_a_0_.length;
    }

    public static class ChunkMap
    {
        public byte[] a;
        public int b;
    }
}
