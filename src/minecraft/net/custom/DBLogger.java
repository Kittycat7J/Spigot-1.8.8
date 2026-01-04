package net.custom;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Random;

public final class DBLogger {

    /* =========================
       CONFIG
       ========================= */

    private static final int QUEUE_LIMIT = 20000;

    /* =========================
       STATE
       ========================= */

    private static final BlockingQueue<LogEntry> QUEUE =
            new LinkedBlockingQueue<LogEntry>(QUEUE_LIMIT);

    private static volatile boolean running = false;
    private static Thread writerThread;
    private static Connection connection;

    private DBLogger() {}

    /* =========================
       LOG ENTRY
       ========================= */

    private static final class LogEntry {
        final long timestamp;
        final long tick;
        final String file;
        final Random rand;
        final String call;
        final int bound;
        final String note;
        int repetitions = 1;

        LogEntry(long timestamp, long tick, String file, Random rand, String call, int bound, String note) {
            this.timestamp = timestamp;
            this.tick = tick;
            this.file = file;
            this.rand = rand;
            this.call = call;
            this.bound = bound;
            this.note = note;
        }

        boolean sameAs(LogEntry other) {
            return other != null &&
                    tick == other.tick &&
                    bound == other.bound &&
                    file.equals(other.file) &&
                    rand.equals(other.rand) &&
                    call.equals(other.call) &&
                    note.equals(other.note);
        }
    }

    /* =========================
       LIFECYCLE
       ========================= */

    public static synchronized void start() {
        if (running) return;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:events.db");

            connection.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS logs (" +
                            "timestamp INTEGER, " +
                            "tick INTEGER, " +
                            "file TEXT, " +
                            "rand TEXT, " +
                            "call TEXT, " +
                            "bound INTEGER, " +
                            "note TEXT, " +
                            "repetitions INTEGER)"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        running = true;

        writerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                writeLoop();
            }
        }, "DBLogger");

        writerThread.setDaemon(true);
        writerThread.start();
    }

    public static synchronized void stop() {
        if (!running) return;
        running = false;

        if (writerThread != null) {
            writerThread.interrupt();
        }
    }

    public static boolean isRunning() {
        return running;
    }

    /* =========================
       WRITER LOOP
       ========================= */

    private static void writeLoop() {
        PreparedStatement ps = null;
        LogEntry last = null;

        try {
            ps = connection.prepareStatement(
                    "INSERT INTO logs " +
                            "(timestamp, tick, file, rand, call, bound, note, repetitions) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );

            while (running || !QUEUE.isEmpty()) {
                LogEntry entry = QUEUE.poll();
                if (entry == null) {
                    Thread.sleep(10);
                    continue;
                }

                if (entry.sameAs(last)) {
                    last.repetitions++;
                } else {
                    if (last != null) {
                        write(ps, last);
                    }
                    last = entry;
                }
            }
        } catch (Exception ignored) {
        } finally {
            try {
                if (last != null && ps != null) {
                    write(ps, last);
                }
            } catch (SQLException ignored) {}

            try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
            try { if (connection != null) connection.close(); } catch (SQLException ignored) {}
        }
    }

    private static void write(PreparedStatement ps, LogEntry e) throws SQLException {
        ps.clearParameters();
        ps.setLong(1, e.timestamp);
        ps.setLong(2, e.tick);
        ps.setString(3, e.file);
        ps.setString(4, e.rand.toString());
        ps.setString(5, e.call);
        if (e.bound == -1) {
            ps.setNull(6, java.sql.Types.INTEGER);
        } else {
            ps.setInt(6, e.bound);
        }
        ps.setString(7, e.note);
        ps.setInt(8, e.repetitions);
        ps.executeUpdate();
    }

    /* =========================
       PUBLIC API
       ========================= */

    public static void log(String file, Random rand, String call, int bound, String note) {
        if (!running) return;

        LogEntry entry = new LogEntry(
                System.currentTimeMillis(),
                getCurrentTick(),
                file,
                rand,
                call,
                bound,
                note
        );

        QUEUE.offer(entry);
    }

    private static long getCurrentTick() {
        try {
            MinecraftServer server = MinecraftServer.getServer();
            if (server != null && server.worldServers != null && server.worldServers.length > 0) {
                WorldServer world = server.worldServers[0];
                if (world != null) {
                    return world.getTotalWorldTime();
                }
            }
        } catch (Throwable ignored) {}

        return -1;
    }
}


// DBLogger.log("file.java", random, "method", bound, "note");

// DBLogger.log("BiomeDecorator.java", this.randomGenerator, "nextInt", 16, "gen sand decorate");
// DBLogger.log("BlockEnchantmentTable.java", rand, "nextFloat", -1, "Particle spawn coordinates");
// DBLogger.log("TileEntityHopper.java", worldIn.rand, "rand.nextInt", list.size(), "Selecting random inventory entity for hopper transfer, bound is list.size()");
