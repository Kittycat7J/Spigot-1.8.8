package net.minecraft.server.v1_8_R3;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Type;
import java.util.UUID;

public class ServerPing
{
    private IChatBaseComponent a;
    private ServerPing.ServerPingPlayerSample b;
    private ServerPing.ServerData c;
    private String d;

    public IChatBaseComponent a()
    {
        return this.a;
    }

    public void setMOTD(IChatBaseComponent p_setMOTD_1_)
    {
        this.a = p_setMOTD_1_;
    }

    public ServerPing.ServerPingPlayerSample b()
    {
        return this.b;
    }

    public void setPlayerSample(ServerPing.ServerPingPlayerSample p_setPlayerSample_1_)
    {
        this.b = p_setPlayerSample_1_;
    }

    public ServerPing.ServerData c()
    {
        return this.c;
    }

    public void setServerInfo(ServerPing.ServerData p_setServerInfo_1_)
    {
        this.c = p_setServerInfo_1_;
    }

    public void setFavicon(String p_setFavicon_1_)
    {
        this.d = p_setFavicon_1_;
    }

    public String d()
    {
        return this.d;
    }

    public static class Serializer implements JsonDeserializer<ServerPing>, JsonSerializer<ServerPing>
    {
        public ServerPing a(JsonElement p_a_1_, Type p_a_2_, JsonDeserializationContext p_a_3_) throws JsonParseException
        {
            JsonObject jsonobject = ChatDeserializer.l(p_a_1_, "status");
            ServerPing serverping = new ServerPing();

            if (jsonobject.has("description"))
            {
                serverping.setMOTD((IChatBaseComponent)p_a_3_.deserialize(jsonobject.get("description"), IChatBaseComponent.class));
            }

            if (jsonobject.has("players"))
            {
                serverping.setPlayerSample((ServerPing.ServerPingPlayerSample)p_a_3_.deserialize(jsonobject.get("players"), ServerPing.ServerPingPlayerSample.class));
            }

            if (jsonobject.has("version"))
            {
                serverping.setServerInfo((ServerPing.ServerData)p_a_3_.deserialize(jsonobject.get("version"), ServerPing.ServerData.class));
            }

            if (jsonobject.has("favicon"))
            {
                serverping.setFavicon(ChatDeserializer.h(jsonobject, "favicon"));
            }

            return serverping;
        }

        public JsonElement a(ServerPing p_a_1_, Type p_a_2_, JsonSerializationContext p_a_3_)
        {
            JsonObject jsonobject = new JsonObject();

            if (p_a_1_.a() != null)
            {
                jsonobject.add("description", p_a_3_.serialize(p_a_1_.a()));
            }

            if (p_a_1_.b() != null)
            {
                jsonobject.add("players", p_a_3_.serialize(p_a_1_.b()));
            }

            if (p_a_1_.c() != null)
            {
                jsonobject.add("version", p_a_3_.serialize(p_a_1_.c()));
            }

            if (p_a_1_.d() != null)
            {
                jsonobject.addProperty("favicon", p_a_1_.d());
            }

            return jsonobject;
        }
    }

    public static class ServerData
    {
        private final String a;
        private final int b;

        public ServerData(String p_i1054_1_, int p_i1054_2_)
        {
            this.a = p_i1054_1_;
            this.b = p_i1054_2_;
        }

        public String a()
        {
            return this.a;
        }

        public int b()
        {
            return this.b;
        }

        public static class Serializer implements JsonDeserializer<ServerPing.ServerData>, JsonSerializer<ServerPing.ServerData>
        {
            public ServerPing.ServerData a(JsonElement p_a_1_, Type p_a_2_, JsonDeserializationContext p_a_3_) throws JsonParseException
            {
                JsonObject jsonobject = ChatDeserializer.l(p_a_1_, "version");
                return new ServerPing.ServerData(ChatDeserializer.h(jsonobject, "name"), ChatDeserializer.m(jsonobject, "protocol"));
            }

            public JsonElement a(ServerPing.ServerData p_a_1_, Type p_a_2_, JsonSerializationContext p_a_3_)
            {
                JsonObject jsonobject = new JsonObject();
                jsonobject.addProperty("name", p_a_1_.a());
                jsonobject.addProperty("protocol", (Number)Integer.valueOf(p_a_1_.b()));
                return jsonobject;
            }
        }
    }

    public static class ServerPingPlayerSample
    {
        private final int a;
        private final int b;
        private GameProfile[] c;

        public ServerPingPlayerSample(int p_i1053_1_, int p_i1053_2_)
        {
            this.a = p_i1053_1_;
            this.b = p_i1053_2_;
        }

        public int a()
        {
            return this.a;
        }

        public int b()
        {
            return this.b;
        }

        public GameProfile[] c()
        {
            return this.c;
        }

        public void a(GameProfile[] p_a_1_)
        {
            this.c = p_a_1_;
        }

        public static class Serializer implements JsonDeserializer<ServerPing.ServerPingPlayerSample>, JsonSerializer<ServerPing.ServerPingPlayerSample>
        {
            public ServerPing.ServerPingPlayerSample a(JsonElement p_a_1_, Type p_a_2_, JsonDeserializationContext p_a_3_) throws JsonParseException
            {
                JsonObject jsonobject = ChatDeserializer.l(p_a_1_, "players");
                ServerPing.ServerPingPlayerSample serverping$serverpingplayersample = new ServerPing.ServerPingPlayerSample(ChatDeserializer.m(jsonobject, "max"), ChatDeserializer.m(jsonobject, "online"));

                if (ChatDeserializer.d(jsonobject, "sample"))
                {
                    JsonArray jsonarray = ChatDeserializer.t(jsonobject, "sample");

                    if (jsonarray.size() > 0)
                    {
                        GameProfile[] agameprofile = new GameProfile[jsonarray.size()];

                        for (int i = 0; i < agameprofile.length; ++i)
                        {
                            JsonObject jsonobject1 = ChatDeserializer.l(jsonarray.get(i), "player[" + i + "]");
                            String s = ChatDeserializer.h(jsonobject1, "id");
                            agameprofile[i] = new GameProfile(UUID.fromString(s), ChatDeserializer.h(jsonobject1, "name"));
                        }

                        serverping$serverpingplayersample.a(agameprofile);
                    }
                }

                return serverping$serverpingplayersample;
            }

            public JsonElement a(ServerPing.ServerPingPlayerSample p_a_1_, Type p_a_2_, JsonSerializationContext p_a_3_)
            {
                JsonObject jsonobject = new JsonObject();
                jsonobject.addProperty("max", (Number)Integer.valueOf(p_a_1_.a()));
                jsonobject.addProperty("online", (Number)Integer.valueOf(p_a_1_.b()));

                if (p_a_1_.c() != null && p_a_1_.c().length > 0)
                {
                    JsonArray jsonarray = new JsonArray();

                    for (int i = 0; i < p_a_1_.c().length; ++i)
                    {
                        JsonObject jsonobject1 = new JsonObject();
                        UUID uuid = p_a_1_.c()[i].getId();
                        jsonobject1.addProperty("id", uuid == null ? "" : uuid.toString());
                        jsonobject1.addProperty("name", p_a_1_.c()[i].getName());
                        jsonarray.add(jsonobject1);
                    }

                    jsonobject.add("sample", jsonarray);
                }

                return jsonobject;
            }
        }
    }
}
