package net.minecraft.server.v1_8_R3;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class WhiteListEntry extends JsonListEntry<GameProfile>
{
    public WhiteListEntry(GameProfile p_i1089_1_)
    {
        super(p_i1089_1_);
    }

    public WhiteListEntry(JsonObject p_i1090_1_)
    {
        super(b(p_i1090_1_), p_i1090_1_);
    }

    protected void a(JsonObject p_a_1_)
    {
        if (this.getKey() != null)
        {
            p_a_1_.addProperty("uuid", ((GameProfile)this.getKey()).getId() == null ? "" : ((GameProfile)this.getKey()).getId().toString());
            p_a_1_.addProperty("name", ((GameProfile)this.getKey()).getName());
            super.a(p_a_1_);
        }
    }

    private static GameProfile b(JsonObject p_b_0_)
    {
        if (p_b_0_.has("uuid") && p_b_0_.has("name"))
        {
            String s = p_b_0_.get("uuid").getAsString();
            UUID uuid;

            try
            {
                uuid = UUID.fromString(s);
            }
            catch (Throwable var4)
            {
                return null;
            }

            return new GameProfile(uuid, p_b_0_.get("name").getAsString());
        }
        else
        {
            return null;
        }
    }
}
