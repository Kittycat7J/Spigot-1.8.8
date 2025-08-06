package net.minecraft.server.v1_8_R3;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class OpList extends JsonList<GameProfile, OpListEntry>
{
    public OpList(File p_i1082_1_)
    {
        super(p_i1082_1_);
    }

    protected JsonListEntry<GameProfile> a(JsonObject p_a_1_)
    {
        return new OpListEntry(p_a_1_);
    }

    public String[] getEntries()
    {
        String[] astring = new String[this.e().size()];
        int i = 0;

        for (OpListEntry oplistentry : this.e().values())
        {
            astring[i++] = ((GameProfile)oplistentry.getKey()).getName();
        }

        return astring;
    }

    public boolean b(GameProfile p_b_1_)
    {
        OpListEntry oplistentry = (OpListEntry)this.get(p_b_1_);
        return oplistentry != null ? oplistentry.b() : false;
    }

    protected String c(GameProfile p_c_1_)
    {
        return p_c_1_.getId().toString();
    }

    public GameProfile a(String p_a_1_)
    {
        for (OpListEntry oplistentry : this.e().values())
        {
            if (p_a_1_.equalsIgnoreCase(((GameProfile)oplistentry.getKey()).getName()))
            {
                return (GameProfile)oplistentry.getKey();
            }
        }

        return null;
    }
}
