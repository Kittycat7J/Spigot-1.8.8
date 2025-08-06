package net.minecraft.server.v1_8_R3;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DedicatedPlayerList extends PlayerList
{
    private static final Logger f = LogManager.getLogger();

    public DedicatedPlayerList(DedicatedServer p_i1061_1_)
    {
        super(p_i1061_1_);
        this.a(p_i1061_1_.a("view-distance", 10));
        this.maxPlayers = p_i1061_1_.a("max-players", 20);
        this.setHasWhitelist(p_i1061_1_.a("white-list", false));

        if (!p_i1061_1_.T())
        {
            this.getProfileBans().a(true);
            this.getIPBans().a(true);
        }

        this.z();
        this.x();
        this.y();
        this.w();
        this.A();
        this.C();
        this.B();

        if (!this.getWhitelist().c().exists())
        {
            this.D();
        }
    }

    public void setHasWhitelist(boolean p_setHasWhitelist_1_)
    {
        super.setHasWhitelist(p_setHasWhitelist_1_);
        this.getServer().a("white-list", (Object)Boolean.valueOf(p_setHasWhitelist_1_));
        this.getServer().a();
    }

    public void addOp(GameProfile p_addOp_1_)
    {
        super.addOp(p_addOp_1_);
        this.B();
    }

    public void removeOp(GameProfile p_removeOp_1_)
    {
        super.removeOp(p_removeOp_1_);
        this.B();
    }

    public void removeWhitelist(GameProfile p_removeWhitelist_1_)
    {
        super.removeWhitelist(p_removeWhitelist_1_);
        this.D();
    }

    public void addWhitelist(GameProfile p_addWhitelist_1_)
    {
        super.addWhitelist(p_addWhitelist_1_);
        this.D();
    }

    public void reloadWhitelist()
    {
        this.C();
    }

    private void w()
    {
        try
        {
            this.getIPBans().save();
        }
        catch (IOException ioexception)
        {
            f.warn((String)"Failed to save ip banlist: ", (Throwable)ioexception);
        }
    }

    private void x()
    {
        try
        {
            this.getProfileBans().save();
        }
        catch (IOException ioexception)
        {
            f.warn((String)"Failed to save user banlist: ", (Throwable)ioexception);
        }
    }

    private void y()
    {
        try
        {
            this.getIPBans().load();
        }
        catch (IOException ioexception)
        {
            f.warn((String)"Failed to load ip banlist: ", (Throwable)ioexception);
        }
    }

    private void z()
    {
        try
        {
            this.getProfileBans().load();
        }
        catch (IOException ioexception)
        {
            f.warn((String)"Failed to load user banlist: ", (Throwable)ioexception);
        }
    }

    private void A()
    {
        try
        {
            this.getOPs().load();
        }
        catch (Exception exception)
        {
            f.warn((String)"Failed to load operators list: ", (Throwable)exception);
        }
    }

    private void B()
    {
        try
        {
            this.getOPs().save();
        }
        catch (Exception exception)
        {
            f.warn((String)"Failed to save operators list: ", (Throwable)exception);
        }
    }

    private void C()
    {
        try
        {
            this.getWhitelist().load();
        }
        catch (Exception exception)
        {
            f.warn((String)"Failed to load white-list: ", (Throwable)exception);
        }
    }

    private void D()
    {
        try
        {
            this.getWhitelist().save();
        }
        catch (Exception exception)
        {
            f.warn((String)"Failed to save white-list: ", (Throwable)exception);
        }
    }

    public boolean isWhitelisted(GameProfile p_isWhitelisted_1_)
    {
        return !this.getHasWhitelist() || this.isOp(p_isWhitelisted_1_) || this.getWhitelist().isWhitelisted(p_isWhitelisted_1_);
    }

    public DedicatedServer getServer()
    {
        return (DedicatedServer)super.getServer();
    }

    public boolean f(GameProfile p_f_1_)
    {
        return this.getOPs().b(p_f_1_);
    }
}
