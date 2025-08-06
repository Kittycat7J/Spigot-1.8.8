package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class RemoteStatusListener extends RemoteConnectionThread
{
    private long h;
    private int i;
    private int j;
    private int k;
    private String l;
    private String m;
    private DatagramSocket n;
    private byte[] o = new byte[1460];
    private DatagramPacket p;
    private Map<SocketAddress, String> q;
    private String r;
    private String s;
    private Map<SocketAddress, RemoteStatusListener.RemoteStatusChallenge> t;
    private long u;
    private RemoteStatusReply v;
    private long w;

    public RemoteStatusListener(IMinecraftServer p_i1094_1_)
    {
        super(p_i1094_1_, "Query Listener");
        this.i = p_i1094_1_.a("query.port", 0);
        this.s = p_i1094_1_.E();
        this.j = p_i1094_1_.F();
        this.l = p_i1094_1_.G();
        this.k = p_i1094_1_.J();
        this.m = p_i1094_1_.U();
        this.w = 0L;
        this.r = "0.0.0.0";

        if (0 != this.s.length() && !this.r.equals(this.s))
        {
            this.r = this.s;
        }
        else
        {
            this.s = "0.0.0.0";

            try
            {
                InetAddress inetaddress = InetAddress.getLocalHost();
                this.r = inetaddress.getHostAddress();
            }
            catch (UnknownHostException unknownhostexception)
            {
                this.c("Unable to determine local host IP, please set server-ip in \'" + p_i1094_1_.b() + "\' : " + unknownhostexception.getMessage());
            }
        }

        if (0 == this.i)
        {
            this.i = this.j;
            this.b("Setting default query port to " + this.i);
            p_i1094_1_.a("query.port", (Object)Integer.valueOf(this.i));
            p_i1094_1_.a("debug", (Object)Boolean.valueOf(false));
            p_i1094_1_.a();
        }

        this.q = Maps.<SocketAddress, String>newHashMap();
        this.v = new RemoteStatusReply(1460);
        this.t = Maps.<SocketAddress, RemoteStatusListener.RemoteStatusChallenge>newHashMap();
        this.u = (new Date()).getTime();
    }

    private void a(byte[] p_a_1_, DatagramPacket p_a_2_) throws IOException
    {
        this.n.send(new DatagramPacket(p_a_1_, p_a_1_.length, p_a_2_.getSocketAddress()));
    }

    private boolean a(DatagramPacket p_a_1_) throws IOException
    {
        byte[] abyte = p_a_1_.getData();
        int ix = p_a_1_.getLength();
        SocketAddress socketaddress = p_a_1_.getSocketAddress();
        this.a((String)("Packet len " + ix + " [" + socketaddress + "]"));

        if (3 <= ix && -2 == abyte[0] && -3 == abyte[1])
        {
            this.a((String)("Packet \'" + StatusChallengeUtils.a(abyte[2]) + "\' [" + socketaddress + "]"));

            switch (abyte[2])
            {
                case 0:
                    if (!this.c(p_a_1_).booleanValue())
                    {
                        this.a((String)("Invalid challenge [" + socketaddress + "]"));
                        return false;
                    }
                    else if (15 == ix)
                    {
                        this.a(this.b(p_a_1_), p_a_1_);
                        this.a((String)("Rules [" + socketaddress + "]"));
                    }
                    else
                    {
                        RemoteStatusReply remotestatusreply = new RemoteStatusReply(1460);
                        remotestatusreply.a((int)0);
                        remotestatusreply.a(this.a(p_a_1_.getSocketAddress()));
                        remotestatusreply.a(this.l);
                        remotestatusreply.a("SMP");
                        remotestatusreply.a(this.m);
                        remotestatusreply.a(Integer.toString(this.d()));
                        remotestatusreply.a(Integer.toString(this.k));
                        remotestatusreply.a((short)this.j);
                        remotestatusreply.a(this.r);
                        this.a(remotestatusreply.a(), p_a_1_);
                        this.a((String)("Status [" + socketaddress + "]"));
                    }

                default:
                    return true;

                case 9:
                    this.d(p_a_1_);
                    this.a((String)("Challenge [" + socketaddress + "]"));
                    return true;
            }
        }
        else
        {
            this.a((String)("Invalid packet [" + socketaddress + "]"));
            return false;
        }
    }

    private byte[] b(DatagramPacket p_b_1_) throws IOException
    {
        long ix = MinecraftServer.az();

        if (ix < this.w + 5000L)
        {
            byte[] abyte = this.v.a();
            byte[] abyte1 = this.a(p_b_1_.getSocketAddress());
            abyte[1] = abyte1[0];
            abyte[2] = abyte1[1];
            abyte[3] = abyte1[2];
            abyte[4] = abyte1[3];
            return abyte;
        }
        else
        {
            this.w = ix;
            this.v.b();
            this.v.a((int)0);
            this.v.a(this.a(p_b_1_.getSocketAddress()));
            this.v.a("splitnum");
            this.v.a((int)128);
            this.v.a((int)0);
            this.v.a("hostname");
            this.v.a(this.l);
            this.v.a("gametype");
            this.v.a("SMP");
            this.v.a("game_id");
            this.v.a("MINECRAFT");
            this.v.a("version");
            this.v.a(this.b.getVersion());
            this.v.a("plugins");
            this.v.a(this.b.getPlugins());
            this.v.a("map");
            this.v.a(this.m);
            this.v.a("numplayers");
            this.v.a("" + this.d());
            this.v.a("maxplayers");
            this.v.a("" + this.k);
            this.v.a("hostport");
            this.v.a("" + this.j);
            this.v.a("hostip");
            this.v.a(this.r);
            this.v.a((int)0);
            this.v.a((int)1);
            this.v.a("player_");
            this.v.a((int)0);
            String[] astring = this.b.getPlayers();

            for (String sx : astring)
            {
                this.v.a(sx);
            }

            this.v.a((int)0);
            return this.v.a();
        }
    }

    private byte[] a(SocketAddress p_a_1_)
    {
        return ((RemoteStatusListener.RemoteStatusChallenge)this.t.get(p_a_1_)).c();
    }

    private Boolean c(DatagramPacket p_c_1_)
    {
        SocketAddress socketaddress = p_c_1_.getSocketAddress();

        if (!this.t.containsKey(socketaddress))
        {
            return Boolean.valueOf(false);
        }
        else
        {
            byte[] abyte = p_c_1_.getData();
            return ((RemoteStatusListener.RemoteStatusChallenge)this.t.get(socketaddress)).a() != StatusChallengeUtils.c(abyte, 7, p_c_1_.getLength()) ? Boolean.valueOf(false) : Boolean.valueOf(true);
        }
    }

    private void d(DatagramPacket p_d_1_) throws IOException
    {
        RemoteStatusListener.RemoteStatusChallenge remotestatuslistener$remotestatuschallenge = new RemoteStatusListener.RemoteStatusChallenge(p_d_1_);
        this.t.put(p_d_1_.getSocketAddress(), remotestatuslistener$remotestatuschallenge);
        this.a(remotestatuslistener$remotestatuschallenge.b(), p_d_1_);
    }

    private void f()
    {
        if (this.a)
        {
            long ix = MinecraftServer.az();

            if (ix >= this.h + 30000L)
            {
                this.h = ix;
                Iterator iterator = this.t.entrySet().iterator();

                while (iterator.hasNext())
                {
                    Entry entry = (Entry)iterator.next();

                    if (((RemoteStatusListener.RemoteStatusChallenge)entry.getValue()).a(ix).booleanValue())
                    {
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void run()
    {
        this.b("Query running on " + this.s + ":" + this.i);
        this.h = MinecraftServer.az();
        this.p = new DatagramPacket(this.o, this.o.length);

        try
        {
            while (this.a)
            {
                try
                {
                    this.n.receive(this.p);
                    this.f();
                    this.a(this.p);
                }
                catch (SocketTimeoutException var7)
                {
                    this.f();
                }
                catch (PortUnreachableException var8)
                {
                    ;
                }
                catch (IOException ioexception)
                {
                    this.a((Exception)ioexception);
                }
            }
        }
        finally
        {
            this.e();
        }
    }

    public void a()
    {
        if (!this.a)
        {
            if (0 < this.i && 65535 >= this.i)
            {
                if (this.g())
                {
                    super.a();
                }
            }
            else
            {
                this.c("Invalid query port " + this.i + " found in \'" + this.b.b() + "\' (queries disabled)");
            }
        }
    }

    private void a(Exception p_a_1_)
    {
        if (this.a)
        {
            this.c("Unexpected exception, buggy JRE? (" + p_a_1_.toString() + ")");

            if (!this.g())
            {
                this.d("Failed to recover from buggy JRE, shutting down!");
                this.a = false;
            }
        }
    }

    private boolean g()
    {
        try
        {
            this.n = new DatagramSocket(this.i, InetAddress.getByName(this.s));
            this.a((DatagramSocket)this.n);
            this.n.setSoTimeout(500);
            return true;
        }
        catch (SocketException socketexception)
        {
            this.c("Unable to initialise query system on " + this.s + ":" + this.i + " (Socket): " + socketexception.getMessage());
        }
        catch (UnknownHostException unknownhostexception)
        {
            this.c("Unable to initialise query system on " + this.s + ":" + this.i + " (Unknown Host): " + unknownhostexception.getMessage());
        }
        catch (Exception exception)
        {
            this.c("Unable to initialise query system on " + this.s + ":" + this.i + " (E): " + exception.getMessage());
        }

        return false;
    }

    class RemoteStatusChallenge
    {
        private long time = (new Date()).getTime();
        private int token;
        private byte[] identity;
        private byte[] response;
        private String f;

        public RemoteStatusChallenge(DatagramPacket p_i1093_2_)
        {
            byte[] abyte = p_i1093_2_.getData();
            this.identity = new byte[4];
            this.identity[0] = abyte[3];
            this.identity[1] = abyte[4];
            this.identity[2] = abyte[5];
            this.identity[3] = abyte[6];
            this.f = new String(this.identity);
            this.token = (new Random()).nextInt(16777216);
            this.response = String.format("\t%s%d\u0000", new Object[] {this.f, Integer.valueOf(this.token)}).getBytes();
        }

        public Boolean a(long p_a_1_)
        {
            return Boolean.valueOf(this.time < p_a_1_);
        }

        public int a()
        {
            return this.token;
        }

        public byte[] b()
        {
            return this.response;
        }

        public byte[] c()
        {
            return this.identity;
        }
    }
}
