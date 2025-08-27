package net.minecraft.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.command.*;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.gen.ChunkProviderServer;

public class CommandRepopulate extends CommandBase {
    @Override public String getCommandName() { return "repopulate"; }
    @Override public int getRequiredPermissionLevel() { return 2; }
    @Override public String getCommandUsage(ICommandSender sender) { return "/repopulate [x z] [now|async]"; }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1 || args.length > 3) {
            throw new WrongUsageException(getCommandUsage(sender));
        }

        World world = sender.getEntityWorld();
        BlockPos pos = sender.getPosition();

        // Default chunk coords from sender's position
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;

        // Parse coordinates if provided (supports "~")
        if (args.length >= 2) {
            CommandBase.CoordinateArg xArg = CommandBase.parseCoordinate(pos.getX(), args[0], false);
            CommandBase.CoordinateArg zArg = CommandBase.parseCoordinate(pos.getZ(), args[1], false);

            chunkX = (int) xArg.func_179628_a() >> 4;
            chunkZ = (int) zArg.func_179628_a() >> 4; // use func_179628_a() for block position
        }

        boolean now = false;
        boolean async = false;

        // Handle mode argument
        if (args.length == 3) {
            String mode = args[2];
            if ("now".equalsIgnoreCase(mode)) {
                now = true;
            } else if ("async".equalsIgnoreCase(mode)) {
                now = true;
                async = true;
            } else {
                throw new WrongUsageException(getCommandUsage(sender));
            }
        }

        if (now) {
            if (!isAreaLoaded(world, chunkX, chunkZ)) {
                notifyOperators(sender, this, "Warning: Area not loaded for repopulation!");
            }

            if (async) {

                final World finalWorld = sender.getEntityWorld();
                final int finalChunkX = chunkX;
                final int finalChunkZ = chunkZ;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            repopulate(finalWorld, finalChunkX, finalChunkZ);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            } else {
                repopulate(world, chunkX, chunkZ);
            }
        } else {
            world.getChunkFromChunkCoords(chunkX, chunkZ).setTerrainPopulated(false);
        }

        sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
        notifyOperators(sender, this, "commands.repopulate.success", chunkX, chunkZ);
    }


    private void repopulate(World world, int x, int z) {
        ChunkProviderServer provider = (ChunkProviderServer) world.getChunkProvider();
        Chunk chunk = world.getChunkFromChunkCoords(x, z);
        chunk.setTerrainPopulated(false);
        provider.populate(provider, x, z);
    }

    private boolean isAreaLoaded(World world, int x, int z) {
        return world.getChunkProvider().chunkExists(x, z)
                && world.getChunkProvider().chunkExists(x + 1, z)
                && world.getChunkProvider().chunkExists(x, z + 1)
                && world.getChunkProvider().chunkExists(x + 1, z + 1);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return args.length == 2?getListOfStringsMatchingLastWord(args, this.getAllUsernames()):null;
    }

    protected String[] getAllUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    public boolean isUsernameIndex(String[] args, int index) {
        return index == 1;
    }

}
