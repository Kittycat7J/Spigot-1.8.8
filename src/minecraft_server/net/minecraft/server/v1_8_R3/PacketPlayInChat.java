package net.minecraft.server.v1_8_R3;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PacketPlayInChat implements Packet<PacketListenerPlayIn>
{
    private String a;
    private static final ExecutorService executors = Executors.newCachedThreadPool((new ThreadFactoryBuilder()).setDaemon(true).setNameFormat("Async Chat Thread - #%d").build());

    public PacketPlayInChat()
    {
    }

    public PacketPlayInChat(String p_i292_1_)
    {
        if (p_i292_1_.length() > 100)
        {
            p_i292_1_ = p_i292_1_.substring(0, 100);
        }

        this.a = p_i292_1_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.c(100);
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.a(this.a);
    }

    public void a(final PacketListenerPlayIn p_a_1_)
    {
        if (!this.a.startsWith("/"))
        {
            executors.submit(new Runnable()
            {
                public void run()
                {
                    p_a_1_.a(PacketPlayInChat.this);
                }
            });
        }
        else
        {
            p_a_1_.a(this);
        }
    }

    public String a()
    {
        return this.a;
    }
}
