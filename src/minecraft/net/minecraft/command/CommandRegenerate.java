package net.minecraft.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.gen.ChunkProviderServer;

public class CommandRegenerate extends CommandBase {

    @Override
    public String getCommandName() {
        return "regenerate";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/regenerate [x z] [now]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1 || args.length > 3) {
            throw new WrongUsageException(getCommandUsage(sender));
        }

        World world = sender.getEntityWorld();
        

        // World world = sender.getEntityWorld();
        BlockPos pos = sender.getPosition();

        // Default chunk coords from sender's position
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;

        // Parse coordinates if provided (supports "~")
        if (args.length >= 2) {
            CommandBase.CoordinateArg xArg = CommandBase.parseCoordinate(pos.getX(), args[0], false);
            CommandBase.CoordinateArg zArg = CommandBase.parseCoordinate(pos.getZ(), args[1], false);

            chunkX = (int) xArg.func_179628_a() >> 4;
            chunkZ = (int) zArg.func_179628_a() >> 4;
        }

        boolean now = false;

        // Handle mode argument
         if (args.length == 3 || args.length == 1) {
            String mode = args[args.length - 1];
            if ("now".equalsIgnoreCase(mode)) {
                now = true;
            } else {
                throw new WrongUsageException(getCommandUsage(sender));
            }
        }

        // /* a method (uncomment to stop use)
        // Delete / erase the chunk completely
            ChunkProviderServer provider = (ChunkProviderServer) world.getChunkProvider();

            if (provider.chunkLoader instanceof AnvilChunkLoader) {
                ((AnvilChunkLoader) provider.chunkLoader).deleteChunk(world, chunkX, chunkZ);
            }

            // Regenerate immediately if "now" is specified
            if (now) {
                provider.dropChunk(chunkX, chunkZ);
                provider.loadChunk(chunkX, chunkZ);
            }

            sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
            notifyOperators(sender, this, "Regenerated chunk " + chunkX + ", " + chunkZ);
        
        
        /* */
        /* alt method
        IChunkProvider provider = world.getChunkProvider();
        // Remove the chunk from memory
        long chunkKey = ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ);
        if (provider instanceof ChunkProviderServer) {
            ChunkProviderServer cps = (ChunkProviderServer) provider;
            Chunk chunk = (Chunk) cps.id2ChunkMap.getValueByKey(chunkKey);
            if (chunk != null) {
                chunk.onChunkUnload();
                cps.loadedChunks.remove(chunk);
                cps.id2ChunkMap.remove(chunkKey);
            }

            // regenerate immediately if "now"
            if (now) {
                Chunk newChunk = cps.loadChunk(chunkX, chunkZ); // Will generate new chunk if not on disk
                newChunk.populateChunk(cps, cps, chunkX, chunkZ);
            }
        }

        sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
        notifyOperators(sender, this, "Chunk at " + chunkX + ", " + chunkZ + " cleared" + (now ? " and regenerated" : ""));
        */
    }
    

    // Optional helper to check if surrounding area is loaded
    private boolean isAreaLoaded(World world, int chunkX, int chunkZ) {
        return world.getChunkProvider().chunkExists(chunkX, chunkZ)
            && world.getChunkProvider().chunkExists(chunkX + 1, chunkZ)
            && world.getChunkProvider().chunkExists(chunkX, chunkZ + 1)
            && world.getChunkProvider().chunkExists(chunkX + 1, chunkZ + 1);
    }



    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            // suggest relative coords
            return func_175771_a(args, 0, pos);
        }
        if (args.length == 2) {
            return Arrays.asList("now");
        }
        return Collections.emptyList();
    }
}
