package net.md_5.bungee.api.chat;

import java.beans.ConstructorProperties;
import java.util.Arrays;

public final class HoverEvent
{
    private final HoverEvent.Action action;
    private final BaseComponent[] value;

    public HoverEvent.Action getAction()
    {
        return this.action;
    }

    public BaseComponent[] getValue()
    {
        return this.value;
    }

    public String toString()
    {
        return "HoverEvent(action=" + this.getAction() + ", value=" + Arrays.deepToString(this.getValue()) + ")";
    }

    @ConstructorProperties( {"action", "value"})
    public HoverEvent(HoverEvent.Action action, BaseComponent[] value)
    {
        this.action = action;
        this.value = value;
    }

    public static enum Action
    {
        SHOW_TEXT,
        SHOW_ACHIEVEMENT,
        SHOW_ITEM,
        SHOW_ENTITY;
    }
}
