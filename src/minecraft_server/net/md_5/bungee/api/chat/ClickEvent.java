package net.md_5.bungee.api.chat;

import java.beans.ConstructorProperties;

public final class ClickEvent
{
    private final ClickEvent.Action action;
    private final String value;

    public ClickEvent.Action getAction()
    {
        return this.action;
    }

    public String getValue()
    {
        return this.value;
    }

    public String toString()
    {
        return "ClickEvent(action=" + this.getAction() + ", value=" + this.getValue() + ")";
    }

    @ConstructorProperties( {"action", "value"})
    public ClickEvent(ClickEvent.Action action, String value)
    {
        this.action = action;
        this.value = value;
    }

    public static enum Action
    {
        OPEN_URL,
        OPEN_FILE,
        RUN_COMMAND,
        SUGGEST_COMMAND,
        CHANGE_PAGE;
    }
}
