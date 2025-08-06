package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import java.util.List;

public class PacketPlayOutMapChunkBulk implements Packet<PacketListenerPlayOut>
{
    private int[] a;
    private int[] b;
    private PacketPlayOutMapChunk.ChunkMap[] c;
    private boolean d;
    private World world;

    public PacketPlayOutMapChunkBulk()
    {
    }

    public PacketPlayOutMapChunkBulk(List<Chunk> p_i259_1_)
    {
        int i = p_i259_1_.size();
        this.a = new int[i];
        this.b = new int[i];
        this.c = new PacketPlayOutMapChunk.ChunkMap[i];
        this.d = !((Chunk)p_i259_1_.get(0)).getWorld().worldProvider.o();

        for (int j = 0; j < i; ++j)
        {
            Chunk chunk = (Chunk)p_i259_1_.get(j);
            PacketPlayOutMapChunk.ChunkMap packetplayoutmapchunk$chunkmap = PacketPlayOutMapChunk.a(chunk, true, this.d, 65535);
            this.a[j] = chunk.locX;
            this.b[j] = chunk.locZ;
            this.c[j] = packetplayoutmapchunk$chunkmap;
        }

        this.world = ((Chunk)p_i259_1_.get(0)).getWorld();
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.d = p_a_1_.readBoolean();
        int i = p_a_1_.e();
        this.a = new int[i];
        this.b = new int[i];
        this.c = new PacketPlayOutMapChunk.ChunkMap[i];

        for (int j = 0; j < i; ++j)
        {
            this.a[j] = p_a_1_.readInt();
            this.b[j] = p_a_1_.readInt();
            this.c[j] = new PacketPlayOutMapChunk.ChunkMap();
            this.c[j].b = p_a_1_.readShort() & 65535;
            this.c[j].a = new byte[PacketPlayOutMapChunk.a(Integer.bitCount(this.c[j].b), this.d, true)];
        }

        for (int k = 0; k < i; ++k)
        {
            p_a_1_.readBytes(this.c[k].a);
        }
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeBoolean(this.d);
        p_b_1_.b(this.c.length);

        for (int i = 0; i < this.a.length; ++i)
        {
            p_b_1_.writeInt(this.a[i]);
            p_b_1_.writeInt(this.b[i]);
            p_b_1_.writeShort((short)(this.c[i].b & 65535));
        }

        for (int j = 0; j < this.a.length; ++j)
        {
            this.world.spigotConfig.antiXrayInstance.obfuscate(this.a[j], this.b[j], this.c[j].b, this.c[j].a, this.world);
            p_b_1_.writeBytes(this.c[j].a);
        }
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
