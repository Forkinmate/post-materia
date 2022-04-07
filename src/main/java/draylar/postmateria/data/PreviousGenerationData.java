package draylar.postmateria.data;

import draylar.worlddata.api.WorldData;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtLong;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

import java.util.HashSet;
import java.util.Set;

public class PreviousGenerationData implements WorldData {

    private final Set<ChunkPos> soulblazeGeneratedChunks = new HashSet<>();
    private final ServerWorld world;

    public PreviousGenerationData(ServerWorld world) {
        this.world = world;
    }

    @Override
    public void writeNbt(NbtCompound root) {
        NbtList soulblazeChunks = new NbtList();
        soulblazeGeneratedChunks.forEach(chunkPos -> soulblazeChunks.add(NbtLong.of(chunkPos.toLong())));
        root.put("SoulblazeChunks", soulblazeChunks);
    }

    @Override
    public void readNbt(NbtCompound root) {
        soulblazeGeneratedChunks.clear();
        NbtList soulblazeChunks = root.getList("SoulblazeChunks", NbtType.LONG);
        soulblazeChunks.forEach(tag -> {
            if(tag instanceof NbtLong longNbt) {
                soulblazeGeneratedChunks.add(new ChunkPos(longNbt.longValue()));
            }
        });
    }

    @Override
    public World getWorld() {
        return world;
    }

    public void flagSoulblaze(ChunkPos chunkPos) {
        soulblazeGeneratedChunks.add(chunkPos);
        markDirty();
    }

    public boolean hasSoulblaze(WorldChunk chunk) {
        return soulblazeGeneratedChunks.contains(chunk.getPos());
    }
}
