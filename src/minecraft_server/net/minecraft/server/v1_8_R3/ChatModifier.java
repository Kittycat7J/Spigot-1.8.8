package net.minecraft.server.v1_8_R3;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class ChatModifier
{
    private ChatModifier a;
    private EnumChatFormat b;
    private Boolean c;
    private Boolean d;
    private Boolean e;
    private Boolean f;
    private Boolean g;
    private ChatClickable h;
    private ChatHoverable i;
    private String j;
    private static final ChatModifier k = new ChatModifier()
    {
        public EnumChatFormat getColor()
        {
            return null;
        }
        public boolean isBold()
        {
            return false;
        }
        public boolean isItalic()
        {
            return false;
        }
        public boolean isStrikethrough()
        {
            return false;
        }
        public boolean isUnderlined()
        {
            return false;
        }
        public boolean isRandom()
        {
            return false;
        }
        public ChatClickable h()
        {
            return null;
        }
        public ChatHoverable i()
        {
            return null;
        }
        public String j()
        {
            return null;
        }
        public ChatModifier setColor(EnumChatFormat p_setColor_1_)
        {
            throw new UnsupportedOperationException();
        }
        public ChatModifier setBold(Boolean p_setBold_1_)
        {
            throw new UnsupportedOperationException();
        }
        public ChatModifier setItalic(Boolean p_setItalic_1_)
        {
            throw new UnsupportedOperationException();
        }
        public ChatModifier setStrikethrough(Boolean p_setStrikethrough_1_)
        {
            throw new UnsupportedOperationException();
        }
        public ChatModifier setUnderline(Boolean p_setUnderline_1_)
        {
            throw new UnsupportedOperationException();
        }
        public ChatModifier setRandom(Boolean p_setRandom_1_)
        {
            throw new UnsupportedOperationException();
        }
        public ChatModifier setChatClickable(ChatClickable p_setChatClickable_1_)
        {
            throw new UnsupportedOperationException();
        }
        public ChatModifier setChatHoverable(ChatHoverable p_setChatHoverable_1_)
        {
            throw new UnsupportedOperationException();
        }
        public ChatModifier setChatModifier(ChatModifier p_setChatModifier_1_)
        {
            throw new UnsupportedOperationException();
        }
        public String toString()
        {
            return "Style.ROOT";
        }
        public ChatModifier clone()
        {
            return this;
        }
        public ChatModifier n()
        {
            return this;
        }
    };

    public EnumChatFormat getColor()
    {
        return this.b == null ? this.o().getColor() : this.b;
    }

    public boolean isBold()
    {
        return this.c == null ? this.o().isBold() : this.c.booleanValue();
    }

    public boolean isItalic()
    {
        return this.d == null ? this.o().isItalic() : this.d.booleanValue();
    }

    public boolean isStrikethrough()
    {
        return this.f == null ? this.o().isStrikethrough() : this.f.booleanValue();
    }

    public boolean isUnderlined()
    {
        return this.e == null ? this.o().isUnderlined() : this.e.booleanValue();
    }

    public boolean isRandom()
    {
        return this.g == null ? this.o().isRandom() : this.g.booleanValue();
    }

    public boolean g()
    {
        return this.c == null && this.d == null && this.f == null && this.e == null && this.g == null && this.b == null && this.h == null && this.i == null;
    }

    public ChatClickable h()
    {
        return this.h == null ? this.o().h() : this.h;
    }

    public ChatHoverable i()
    {
        return this.i == null ? this.o().i() : this.i;
    }

    public String j()
    {
        return this.j == null ? this.o().j() : this.j;
    }

    public ChatModifier setColor(EnumChatFormat p_setColor_1_)
    {
        this.b = p_setColor_1_;
        return this;
    }

    public ChatModifier setBold(Boolean p_setBold_1_)
    {
        this.c = p_setBold_1_;
        return this;
    }

    public ChatModifier setItalic(Boolean p_setItalic_1_)
    {
        this.d = p_setItalic_1_;
        return this;
    }

    public ChatModifier setStrikethrough(Boolean p_setStrikethrough_1_)
    {
        this.f = p_setStrikethrough_1_;
        return this;
    }

    public ChatModifier setUnderline(Boolean p_setUnderline_1_)
    {
        this.e = p_setUnderline_1_;
        return this;
    }

    public ChatModifier setRandom(Boolean p_setRandom_1_)
    {
        this.g = p_setRandom_1_;
        return this;
    }

    public ChatModifier setChatClickable(ChatClickable p_setChatClickable_1_)
    {
        this.h = p_setChatClickable_1_;
        return this;
    }

    public ChatModifier setChatHoverable(ChatHoverable p_setChatHoverable_1_)
    {
        this.i = p_setChatHoverable_1_;
        return this;
    }

    public ChatModifier setInsertion(String p_setInsertion_1_)
    {
        this.j = p_setInsertion_1_;
        return this;
    }

    public ChatModifier setChatModifier(ChatModifier p_setChatModifier_1_)
    {
        this.a = p_setChatModifier_1_;
        return this;
    }

    private ChatModifier o()
    {
        return this.a == null ? k : this.a;
    }

    public String toString()
    {
        return "Style{hasParent=" + (this.a != null) + ", color=" + this.b + ", bold=" + this.c + ", italic=" + this.d + ", underlined=" + this.e + ", obfuscated=" + this.g + ", clickEvent=" + this.h() + ", hoverEvent=" + this.i() + ", insertion=" + this.j() + '}';
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof ChatModifier))
        {
            return false;
        }
        else
        {
            label16:
            {
                ChatModifier chatmodifier = (ChatModifier)p_equals_1_;

                if (this.isBold() == chatmodifier.isBold() && this.getColor() == chatmodifier.getColor() && this.isItalic() == chatmodifier.isItalic() && this.isRandom() == chatmodifier.isRandom() && this.isStrikethrough() == chatmodifier.isStrikethrough() && this.isUnderlined() == chatmodifier.isUnderlined())
                {
                    label87:
                    {
                        if (this.h() != null)
                        {
                            if (!this.h().equals(chatmodifier.h()))
                            {
                                break label87;
                            }
                        }
                        else if (chatmodifier.h() != null)
                        {
                            break label87;
                        }

                        if (this.i() != null)
                        {
                            if (!this.i().equals(chatmodifier.i()))
                            {
                                break label87;
                            }
                        }
                        else if (chatmodifier.i() != null)
                        {
                            break label87;
                        }

                        if (this.j() != null)
                        {
                            if (this.j().equals(chatmodifier.j()))
                            {
                                break label16;
                            }
                        }
                        else if (chatmodifier.j() == null)
                        {
                            break label16;
                        }
                    }
                }

                boolean flag1 = false;
                return flag1;
            }
            boolean flag = true;
            return flag;
        }
    }

    public int hashCode()
    {
        int i = this.b == null ? 0 : this.b.hashCode();
        i = 31 * i + (this.c == null ? 0 : this.c.hashCode());
        i = 31 * i + (this.d == null ? 0 : this.d.hashCode());
        i = 31 * i + (this.e == null ? 0 : this.e.hashCode());
        i = 31 * i + (this.f == null ? 0 : this.f.hashCode());
        i = 31 * i + (this.g == null ? 0 : this.g.hashCode());
        i = 31 * i + (this.h == null ? 0 : this.h.hashCode());
        i = 31 * i + (this.i == null ? 0 : this.i.hashCode());
        i = 31 * i + (this.j == null ? 0 : this.j.hashCode());
        return i;
    }

    public ChatModifier clone()
    {
        ChatModifier chatmodifier = new ChatModifier();
        chatmodifier.c = this.c;
        chatmodifier.d = this.d;
        chatmodifier.f = this.f;
        chatmodifier.e = this.e;
        chatmodifier.g = this.g;
        chatmodifier.b = this.b;
        chatmodifier.h = this.h;
        chatmodifier.i = this.i;
        chatmodifier.a = this.a;
        chatmodifier.j = this.j;
        return chatmodifier;
    }

    public ChatModifier n()
    {
        ChatModifier chatmodifier = new ChatModifier();
        chatmodifier.setBold(Boolean.valueOf(this.isBold()));
        chatmodifier.setItalic(Boolean.valueOf(this.isItalic()));
        chatmodifier.setStrikethrough(Boolean.valueOf(this.isStrikethrough()));
        chatmodifier.setUnderline(Boolean.valueOf(this.isUnderlined()));
        chatmodifier.setRandom(Boolean.valueOf(this.isRandom()));
        chatmodifier.setColor(this.getColor());
        chatmodifier.setChatClickable(this.h());
        chatmodifier.setChatHoverable(this.i());
        chatmodifier.setInsertion(this.j());
        return chatmodifier;
    }

    public static class ChatModifierSerializer implements JsonDeserializer<ChatModifier>, JsonSerializer<ChatModifier>
    {
        public ChatModifier a(JsonElement p_a_1_, Type p_a_2_, JsonDeserializationContext p_a_3_) throws JsonParseException
        {
            if (p_a_1_.isJsonObject())
            {
                ChatModifier chatmodifier = new ChatModifier();
                JsonObject jsonobject = p_a_1_.getAsJsonObject();

                if (jsonobject == null)
                {
                    return null;
                }
                else
                {
                    if (jsonobject.has("bold"))
                    {
                        chatmodifier.c = Boolean.valueOf(jsonobject.get("bold").getAsBoolean());
                    }

                    if (jsonobject.has("italic"))
                    {
                        chatmodifier.d = Boolean.valueOf(jsonobject.get("italic").getAsBoolean());
                    }

                    if (jsonobject.has("underlined"))
                    {
                        chatmodifier.e = Boolean.valueOf(jsonobject.get("underlined").getAsBoolean());
                    }

                    if (jsonobject.has("strikethrough"))
                    {
                        chatmodifier.f = Boolean.valueOf(jsonobject.get("strikethrough").getAsBoolean());
                    }

                    if (jsonobject.has("obfuscated"))
                    {
                        chatmodifier.g = Boolean.valueOf(jsonobject.get("obfuscated").getAsBoolean());
                    }

                    if (jsonobject.has("color"))
                    {
                        chatmodifier.b = (EnumChatFormat)p_a_3_.deserialize(jsonobject.get("color"), EnumChatFormat.class);
                    }

                    if (jsonobject.has("insertion"))
                    {
                        chatmodifier.j = jsonobject.get("insertion").getAsString();
                    }

                    if (jsonobject.has("clickEvent"))
                    {
                        JsonObject jsonobject1 = jsonobject.getAsJsonObject("clickEvent");

                        if (jsonobject1 != null)
                        {
                            JsonPrimitive jsonprimitive = jsonobject1.getAsJsonPrimitive("action");
                            ChatClickable.EnumClickAction chatclickable$enumclickaction = jsonprimitive == null ? null : ChatClickable.EnumClickAction.a(jsonprimitive.getAsString());
                            JsonPrimitive jsonprimitive1 = jsonobject1.getAsJsonPrimitive("value");
                            String s = jsonprimitive1 == null ? null : jsonprimitive1.getAsString();

                            if (chatclickable$enumclickaction != null && s != null && chatclickable$enumclickaction.a())
                            {
                                chatmodifier.h = new ChatClickable(chatclickable$enumclickaction, s);
                            }
                        }
                    }

                    if (jsonobject.has("hoverEvent"))
                    {
                        JsonObject jsonobject2 = jsonobject.getAsJsonObject("hoverEvent");

                        if (jsonobject2 != null)
                        {
                            JsonPrimitive jsonprimitive2 = jsonobject2.getAsJsonPrimitive("action");
                            ChatHoverable.EnumHoverAction chathoverable$enumhoveraction = jsonprimitive2 == null ? null : ChatHoverable.EnumHoverAction.a(jsonprimitive2.getAsString());
                            IChatBaseComponent ichatbasecomponent = (IChatBaseComponent)p_a_3_.deserialize(jsonobject2.get("value"), IChatBaseComponent.class);

                            if (chathoverable$enumhoveraction != null && ichatbasecomponent != null && chathoverable$enumhoveraction.a())
                            {
                                chatmodifier.i = new ChatHoverable(chathoverable$enumhoveraction, ichatbasecomponent);
                            }
                        }
                    }

                    return chatmodifier;
                }
            }
            else
            {
                return null;
            }
        }

        public JsonElement a(ChatModifier p_a_1_, Type p_a_2_, JsonSerializationContext p_a_3_)
        {
            if (p_a_1_.g())
            {
                return null;
            }
            else
            {
                JsonObject jsonobject = new JsonObject();

                if (p_a_1_.c != null)
                {
                    jsonobject.addProperty("bold", p_a_1_.c);
                }

                if (p_a_1_.d != null)
                {
                    jsonobject.addProperty("italic", p_a_1_.d);
                }

                if (p_a_1_.e != null)
                {
                    jsonobject.addProperty("underlined", p_a_1_.e);
                }

                if (p_a_1_.f != null)
                {
                    jsonobject.addProperty("strikethrough", p_a_1_.f);
                }

                if (p_a_1_.g != null)
                {
                    jsonobject.addProperty("obfuscated", p_a_1_.g);
                }

                if (p_a_1_.b != null)
                {
                    jsonobject.add("color", p_a_3_.serialize(p_a_1_.b));
                }

                if (p_a_1_.j != null)
                {
                    jsonobject.add("insertion", p_a_3_.serialize(p_a_1_.j));
                }

                if (p_a_1_.h != null)
                {
                    JsonObject jsonobject1 = new JsonObject();
                    jsonobject1.addProperty("action", p_a_1_.h.a().b());
                    jsonobject1.addProperty("value", p_a_1_.h.b());
                    jsonobject.add("clickEvent", jsonobject1);
                }

                if (p_a_1_.i != null)
                {
                    JsonObject jsonobject2 = new JsonObject();
                    jsonobject2.addProperty("action", p_a_1_.i.a().b());
                    jsonobject2.add("value", p_a_3_.serialize(p_a_1_.i.b()));
                    jsonobject.add("hoverEvent", jsonobject2);
                }

                return jsonobject;
            }
        }

        public JsonElement serialize(ChatModifier p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_)
        {
            return this.a(p_serialize_1_, p_serialize_2_, p_serialize_3_);
        }

        public ChatModifier deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            return this.a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
        }
    }
}
