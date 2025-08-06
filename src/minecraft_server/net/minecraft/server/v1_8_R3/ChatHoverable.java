package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Map;

public class ChatHoverable
{
    private final ChatHoverable.EnumHoverAction a;
    private final IChatBaseComponent b;

    public ChatHoverable(ChatHoverable.EnumHoverAction p_i938_1_, IChatBaseComponent p_i938_2_)
    {
        this.a = p_i938_1_;
        this.b = p_i938_2_;
    }

    public ChatHoverable.EnumHoverAction a()
    {
        return this.a;
    }

    public IChatBaseComponent b()
    {
        return this.b;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass())
        {
            ChatHoverable chathoverable = (ChatHoverable)p_equals_1_;

            if (this.a != chathoverable.a)
            {
                return false;
            }
            else
            {
                if (this.b != null)
                {
                    if (!this.b.equals(chathoverable.b))
                    {
                        return false;
                    }
                }
                else if (chathoverable.b != null)
                {
                    return false;
                }

                return true;
            }
        }
        else
        {
            return false;
        }
    }

    public String toString()
    {
        return "HoverEvent{action=" + this.a + ", value=\'" + this.b + '\'' + '}';
    }

    public int hashCode()
    {
        int i = this.a.hashCode();
        i = 31 * i + (this.b != null ? this.b.hashCode() : 0);
        return i;
    }

    public static enum EnumHoverAction
    {
        SHOW_TEXT("show_text", true),
        SHOW_ACHIEVEMENT("show_achievement", true),
        SHOW_ITEM("show_item", true),
        SHOW_ENTITY("show_entity", true);

        private static final Map<String, ChatHoverable.EnumHoverAction> e = Maps.<String, ChatHoverable.EnumHoverAction>newHashMap();
        private final boolean f;
        private final String g;

        private EnumHoverAction(String p_i937_3_, boolean p_i937_4_)
        {
            this.g = p_i937_3_;
            this.f = p_i937_4_;
        }

        public boolean a()
        {
            return this.f;
        }

        public String b()
        {
            return this.g;
        }

        public static ChatHoverable.EnumHoverAction a(String p_a_0_)
        {
            return (ChatHoverable.EnumHoverAction)e.get(p_a_0_);
        }

        static {
            for (ChatHoverable.EnumHoverAction chathoverable$enumhoveraction : values())
            {
                e.put(chathoverable$enumhoveraction.b(), chathoverable$enumhoveraction);
            }
        }
    }
}
