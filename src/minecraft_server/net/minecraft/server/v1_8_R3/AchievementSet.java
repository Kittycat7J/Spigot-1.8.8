package net.minecraft.server.v1_8_R3;

import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Set;

public class AchievementSet extends ForwardingSet<String> implements IJsonStatistic
{
    private final Set<String> a = Sets.<String>newHashSet();

    public void a(JsonElement p_a_1_)
    {
        if (p_a_1_.isJsonArray())
        {
            for (JsonElement jsonelement : p_a_1_.getAsJsonArray())
            {
                this.add(jsonelement.getAsString());
            }
        }
    }

    public JsonElement a()
    {
        JsonArray jsonarray = new JsonArray();

        for (String s : this)
        {
            jsonarray.add(new JsonPrimitive(s));
        }

        return jsonarray;
    }

    protected Set<String> delegate()
    {
        return this.a;
    }
}
