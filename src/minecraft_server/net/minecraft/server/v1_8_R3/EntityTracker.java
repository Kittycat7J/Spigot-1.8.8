package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.Callable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spigotmc.AsyncCatcher;
import org.spigotmc.TrackingRange;

public class EntityTracker
{
    private static final Logger a = LogManager.getLogger();
    private final WorldServer world;
    private Set<EntityTrackerEntry> c = Sets.<EntityTrackerEntry>newHashSet();
    public IntHashMap<EntityTrackerEntry> trackedEntities = new IntHashMap();
    private int e;

    public EntityTracker(WorldServer p_i47_1_)
    {
        this.world = p_i47_1_;
        this.e = p_i47_1_.getMinecraftServer().getPlayerList().d();
    }

    public void track(Entity p_track_1_)
    {
        if (p_track_1_ instanceof EntityPlayer)
        {
            this.addEntity(p_track_1_, 512, 2);
            EntityPlayer entityplayer = (EntityPlayer)p_track_1_;

            for (EntityTrackerEntry entitytrackerentry : this.c)
            {
                if (entitytrackerentry.tracker != entityplayer)
                {
                    entitytrackerentry.updatePlayer(entityplayer);
                }
            }
        }
        else if (p_track_1_ instanceof EntityFishingHook)
        {
            this.addEntity(p_track_1_, 64, 5, true);
        }
        else if (p_track_1_ instanceof EntityArrow)
        {
            this.addEntity(p_track_1_, 64, 20, false);
        }
        else if (p_track_1_ instanceof EntitySmallFireball)
        {
            this.addEntity(p_track_1_, 64, 10, false);
        }
        else if (p_track_1_ instanceof EntityFireball)
        {
            this.addEntity(p_track_1_, 64, 10, false);
        }
        else if (p_track_1_ instanceof EntitySnowball)
        {
            this.addEntity(p_track_1_, 64, 10, true);
        }
        else if (p_track_1_ instanceof EntityEnderPearl)
        {
            this.addEntity(p_track_1_, 64, 10, true);
        }
        else if (p_track_1_ instanceof EntityEnderSignal)
        {
            this.addEntity(p_track_1_, 64, 4, true);
        }
        else if (p_track_1_ instanceof EntityEgg)
        {
            this.addEntity(p_track_1_, 64, 10, true);
        }
        else if (p_track_1_ instanceof EntityPotion)
        {
            this.addEntity(p_track_1_, 64, 10, true);
        }
        else if (p_track_1_ instanceof EntityThrownExpBottle)
        {
            this.addEntity(p_track_1_, 64, 10, true);
        }
        else if (p_track_1_ instanceof EntityFireworks)
        {
            this.addEntity(p_track_1_, 64, 10, true);
        }
        else if (p_track_1_ instanceof EntityItem)
        {
            this.addEntity(p_track_1_, 64, 20, true);
        }
        else if (p_track_1_ instanceof EntityMinecartAbstract)
        {
            this.addEntity(p_track_1_, 80, 3, true);
        }
        else if (p_track_1_ instanceof EntityBoat)
        {
            this.addEntity(p_track_1_, 80, 3, true);
        }
        else if (p_track_1_ instanceof EntitySquid)
        {
            this.addEntity(p_track_1_, 64, 3, true);
        }
        else if (p_track_1_ instanceof EntityWither)
        {
            this.addEntity(p_track_1_, 80, 3, false);
        }
        else if (p_track_1_ instanceof EntityBat)
        {
            this.addEntity(p_track_1_, 80, 3, false);
        }
        else if (p_track_1_ instanceof EntityEnderDragon)
        {
            this.addEntity(p_track_1_, 160, 3, true);
        }
        else if (p_track_1_ instanceof IAnimal)
        {
            this.addEntity(p_track_1_, 80, 3, true);
        }
        else if (p_track_1_ instanceof EntityTNTPrimed)
        {
            this.addEntity(p_track_1_, 160, 10, true);
        }
        else if (p_track_1_ instanceof EntityFallingBlock)
        {
            this.addEntity(p_track_1_, 160, 20, true);
        }
        else if (p_track_1_ instanceof EntityHanging)
        {
            this.addEntity(p_track_1_, 160, Integer.MAX_VALUE, false);
        }
        else if (p_track_1_ instanceof EntityArmorStand)
        {
            this.addEntity(p_track_1_, 160, 3, true);
        }
        else if (p_track_1_ instanceof EntityExperienceOrb)
        {
            this.addEntity(p_track_1_, 160, 20, true);
        }
        else if (p_track_1_ instanceof EntityEnderCrystal)
        {
            this.addEntity(p_track_1_, 256, Integer.MAX_VALUE, false);
        }
    }

    public void addEntity(Entity p_addEntity_1_, int p_addEntity_2_, int p_addEntity_3_)
    {
        this.addEntity(p_addEntity_1_, p_addEntity_2_, p_addEntity_3_, false);
    }

    public void addEntity(Entity p_addEntity_1_, final int p_addEntity_2_, int p_addEntity_3_, boolean p_addEntity_4_)
    {
        AsyncCatcher.catchOp("entity track");
        p_addEntity_2_ = TrackingRange.getEntityTrackingRange(p_addEntity_1_, p_addEntity_2_);

        if (p_addEntity_2_ > this.e)
        {
            p_addEntity_2_ = this.e;
        }

        try
        {
            if (this.trackedEntities.b(p_addEntity_1_.getId()))
            {
                throw new IllegalStateException("Entity is already tracked!");
            }

            EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(p_addEntity_1_, p_addEntity_2_, p_addEntity_3_, p_addEntity_4_);
            this.c.add(entitytrackerentry);
            this.trackedEntities.a(p_addEntity_1_.getId(), entitytrackerentry);
            entitytrackerentry.scanPlayers(this.world.players);
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.a(throwable, "Adding entity to track");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity To Track");
            crashreportsystemdetails.a((String)"Tracking range", p_addEntity_2_ + " blocks");
            crashreportsystemdetails.a("Update interval", new Callable()
            {
                public String a() throws Exception
                {
                    String s = "Once per " + p_addEntity_2_ + " ticks";

                    if (p_addEntity_2_ == Integer.MAX_VALUE)
                    {
                        s = "Maximum (" + s + ")";
                    }

                    return s;
                }
                public Object call() throws Exception
                {
                    return this.a();
                }
            });
            p_addEntity_1_.appendEntityCrashDetails(crashreportsystemdetails);
            CrashReportSystemDetails crashreportsystemdetails1 = crashreport.a("Entity That Is Already Tracked");
            ((EntityTrackerEntry)this.trackedEntities.get(p_addEntity_1_.getId())).tracker.appendEntityCrashDetails(crashreportsystemdetails1);

            try
            {
                throw new ReportedException(crashreport);
            }
            catch (ReportedException reportedexception)
            {
                a.error((String)"\"Silently\" catching entity tracking error.", (Throwable)reportedexception);
            }
        }
    }

    public void untrackEntity(Entity p_untrackEntity_1_)
    {
        AsyncCatcher.catchOp("entity untrack");

        if (p_untrackEntity_1_ instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)p_untrackEntity_1_;

            for (EntityTrackerEntry entitytrackerentry : this.c)
            {
                entitytrackerentry.a(entityplayer);
            }
        }

        EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry)this.trackedEntities.d(p_untrackEntity_1_.getId());

        if (entitytrackerentry1 != null)
        {
            this.c.remove(entitytrackerentry1);
            entitytrackerentry1.a();
        }
    }

    public void updatePlayers()
    {
        ArrayList arraylist = Lists.newArrayList();

        for (EntityTrackerEntry entitytrackerentry : this.c)
        {
            entitytrackerentry.track(this.world.players);

            if (entitytrackerentry.n && entitytrackerentry.tracker instanceof EntityPlayer)
            {
                arraylist.add((EntityPlayer)entitytrackerentry.tracker);
            }
        }

        for (int i = 0; i < arraylist.size(); ++i)
        {
            EntityPlayer entityplayer = (EntityPlayer)arraylist.get(i);

            for (EntityTrackerEntry entitytrackerentry1 : this.c)
            {
                if (entitytrackerentry1.tracker != entityplayer)
                {
                    entitytrackerentry1.updatePlayer(entityplayer);
                }
            }
        }
    }

    public void a(EntityPlayer p_a_1_)
    {
        for (EntityTrackerEntry entitytrackerentry : this.c)
        {
            if (entitytrackerentry.tracker == p_a_1_)
            {
                entitytrackerentry.scanPlayers(this.world.players);
            }
            else
            {
                entitytrackerentry.updatePlayer(p_a_1_);
            }
        }
    }

    public void a(Entity p_a_1_, Packet p_a_2_)
    {
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.trackedEntities.get(p_a_1_.getId());

        if (entitytrackerentry != null)
        {
            entitytrackerentry.broadcast(p_a_2_);
        }
    }

    public void sendPacketToEntity(Entity p_sendPacketToEntity_1_, Packet p_sendPacketToEntity_2_)
    {
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.trackedEntities.get(p_sendPacketToEntity_1_.getId());

        if (entitytrackerentry != null)
        {
            entitytrackerentry.broadcastIncludingSelf(p_sendPacketToEntity_2_);
        }
    }

    public void untrackPlayer(EntityPlayer p_untrackPlayer_1_)
    {
        for (EntityTrackerEntry entitytrackerentry : this.c)
        {
            entitytrackerentry.clear(p_untrackPlayer_1_);
        }
    }

    public void a(EntityPlayer p_a_1_, Chunk p_a_2_)
    {
        for (EntityTrackerEntry entitytrackerentry : this.c)
        {
            if (entitytrackerentry.tracker != p_a_1_ && entitytrackerentry.tracker.ae == p_a_2_.locX && entitytrackerentry.tracker.ag == p_a_2_.locZ)
            {
                entitytrackerentry.updatePlayer(p_a_1_);
            }
        }
    }
}
