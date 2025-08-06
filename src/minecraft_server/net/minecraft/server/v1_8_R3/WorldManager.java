package net.minecraft.server.v1_8_R3;

import java.util.Iterator;

public class WorldManager implements IWorldAccess
{
    private MinecraftServer a;
    private WorldServer world;

    public WorldManager(MinecraftServer p_i189_1_, WorldServer p_i189_2_)
    {
        this.a = p_i189_1_;
        this.world = p_i189_2_;
    }

    public void a(int p_a_1_, boolean p_a_2_, double p_a_3_, double p_a_5_, double p_a_7_, double p_a_9_, double p_a_11_, double p_a_13_, int... p_a_15_)
    {
    }

    public void a(Entity p_a_1_)
    {
        this.world.getTracker().track(p_a_1_);
    }

    public void b(Entity p_b_1_)
    {
        this.world.getTracker().untrackEntity(p_b_1_);
        this.world.getScoreboard().a(p_b_1_);
    }

    public void a(String p_a_1_, double p_a_2_, double p_a_4_, double p_a_6_, float p_a_8_, float p_a_9_)
    {
        this.a.getPlayerList().sendPacketNearby(p_a_2_, p_a_4_, p_a_6_, p_a_8_ > 1.0F ? (double)(16.0F * p_a_8_) : 16.0D, this.world.dimension, new PacketPlayOutNamedSoundEffect(p_a_1_, p_a_2_, p_a_4_, p_a_6_, p_a_8_, p_a_9_));
    }

    public void a(EntityHuman p_a_1_, String p_a_2_, double p_a_3_, double p_a_5_, double p_a_7_, float p_a_9_, float p_a_10_)
    {
        this.a.getPlayerList().sendPacketNearby(p_a_1_, p_a_3_, p_a_5_, p_a_7_, p_a_9_ > 1.0F ? (double)(16.0F * p_a_9_) : 16.0D, this.world.dimension, new PacketPlayOutNamedSoundEffect(p_a_2_, p_a_3_, p_a_5_, p_a_7_, p_a_9_, p_a_10_));
    }

    public void a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_)
    {
    }

    public void a(BlockPosition p_a_1_)
    {
        this.world.getPlayerChunkMap().flagDirty(p_a_1_);
    }

    public void b(BlockPosition p_b_1_)
    {
    }

    public void a(String p_a_1_, BlockPosition p_a_2_)
    {
    }

    public void a(EntityHuman p_a_1_, int p_a_2_, BlockPosition p_a_3_, int p_a_4_)
    {
        this.a.getPlayerList().sendPacketNearby(p_a_1_, (double)p_a_3_.getX(), (double)p_a_3_.getY(), (double)p_a_3_.getZ(), 64.0D, this.world.dimension, new PacketPlayOutWorldEvent(p_a_2_, p_a_3_, p_a_4_, false));
    }

    public void a(int p_a_1_, BlockPosition p_a_2_, int p_a_3_)
    {
        this.a.getPlayerList().sendAll(new PacketPlayOutWorldEvent(p_a_1_, p_a_2_, p_a_3_, true));
    }

    public void b(int p_b_1_, BlockPosition p_b_2_, int p_b_3_)
    {
        Iterator iterator = this.a.getPlayerList().v().iterator();
        EntityHuman entityhuman = null;
        Entity entity = this.world.a(p_b_1_);

        if (entity instanceof EntityHuman)
        {
            entityhuman = (EntityHuman)entity;
        }

        while (iterator.hasNext())
        {
            EntityPlayer entityplayer = (EntityPlayer)iterator.next();

            if (entityplayer != null && entityplayer.world == this.world && entityplayer.getId() != p_b_1_)
            {
                double d0 = (double)p_b_2_.getX() - entityplayer.locX;
                double d1 = (double)p_b_2_.getY() - entityplayer.locY;
                double d2 = (double)p_b_2_.getZ() - entityplayer.locZ;

                if ((entityhuman == null || !(entityhuman instanceof EntityPlayer) || entityplayer.getBukkitEntity().canSee(((EntityPlayer)entityhuman).getBukkitEntity())) && d0 * d0 + d1 * d1 + d2 * d2 < 1024.0D)
                {
                    entityplayer.playerConnection.sendPacket(new PacketPlayOutBlockBreakAnimation(p_b_1_, p_b_2_, p_b_3_));
                }
            }
        }
    }
}
