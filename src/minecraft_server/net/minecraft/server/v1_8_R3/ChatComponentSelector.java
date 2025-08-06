package net.minecraft.server.v1_8_R3;

public class ChatComponentSelector extends ChatBaseComponent
{
    private final String b;

    public ChatComponentSelector(String p_i940_1_)
    {
        this.b = p_i940_1_;
    }

    public String g()
    {
        return this.b;
    }

    public String getText()
    {
        return this.b;
    }

    public ChatComponentSelector h()
    {
        ChatComponentSelector chatcomponentselector = new ChatComponentSelector(this.b);
        chatcomponentselector.setChatModifier(this.getChatModifier().clone());

        for (IChatBaseComponent ichatbasecomponent : this.a())
        {
            chatcomponentselector.addSibling(ichatbasecomponent.f());
        }

        return chatcomponentselector;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof ChatComponentSelector))
        {
            return false;
        }
        else
        {
            ChatComponentSelector chatcomponentselector = (ChatComponentSelector)p_equals_1_;
            return this.b.equals(chatcomponentselector.b) && super.equals(p_equals_1_);
        }
    }

    public String toString()
    {
        return "SelectorComponent{pattern=\'" + this.b + '\'' + ", siblings=" + this.a + ", style=" + this.getChatModifier() + '}';
    }
}
