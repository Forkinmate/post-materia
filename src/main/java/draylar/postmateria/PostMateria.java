package draylar.postmateria;

import draylar.postmateria.api.data.WorldData;
import draylar.postmateria.api.data.WorldDataKey;
import draylar.postmateria.api.data.WorldDataRegistry;
import draylar.postmateria.command.GenerateCommand;
import draylar.postmateria.command.WorldDataTestCommand;
import draylar.postmateria.data.PreviousGenerationData;
import draylar.postmateria.data.WorldWitherData;
import draylar.postmateria.impl.WorldDataAccessor;
import draylar.postmateria.registry.PMBlocks;
import draylar.postmateria.registry.PMEntities;
import draylar.postmateria.registry.PMItems;
import draylar.postmateria.registry.PMWorld;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.ConfiguredFeatures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostMateria implements ModInitializer {

    public static final WorldDataKey<WorldWitherData> WITHER_SLAIN_DATA = WorldDataRegistry.registerGlobal(id("wither_slain"), WorldWitherData::new);
    public static final WorldDataKey<PreviousGenerationData> PREVIOUS_GENERATION_DATA = WorldDataRegistry.register(id("previous_generation"), PreviousGenerationData::new);
    public Map<ServerWorld, Chunk> queuedSoulblazeRetrogen = new HashMap<>();

    @Override
    public void onInitialize() {
        PMItems.init();
        PMBlocks.init();
        PMEntities.init();
        PMWorld.initialize();

        // Debugging commands
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            GenerateCommand.initialize();
            WorldDataTestCommand.initialize();
        }

        // Do not call the inside callback while the server is starting up (it freezes)!
        // TODO: I think we can/should remove this
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {

            // When a chunk is loaded, attempt to regenerate features.
            ServerChunkEvents.CHUNK_LOAD.register((world, chunk) -> {

                // If the wither has been slain, ensure Soulblaze Ore has generated.
                if (getGlobalData(world.getServer(), WITHER_SLAIN_DATA).isWitherSlain()) {
                    if (!getData(world, PREVIOUS_GENERATION_DATA).hasSoulblaze(chunk)) {
                        queuedSoulblazeRetrogen.put(world, chunk);
                    }
                }
            });
        });

        // Handle queue to avoid freezing the server thread with World#getChunk
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            queuedSoulblazeRetrogen.forEach((world, chunk) -> {
                PMWorld.SOUL_SAND_SOULBLAZE_ORE.generate(world, world.getChunkManager().getChunkGenerator(), world.random, chunk.getPos().getBlockPos(0, 0, 0));
            });

            queuedSoulblazeRetrogen.clear();
        });
    }

    public static Identifier id(String name) {
        return new Identifier("postmateria", name);
    }

    public static <T extends WorldData> T getGlobalData(MinecraftServer server, WorldDataKey<T> key) {
        return ((WorldDataAccessor) server.getWorld(World.OVERWORLD)).postMateria_getWorldDataState().get(key);
    }

    public static <T extends WorldData> T getData(ServerWorld world, WorldDataKey<T> key) {
        return ((WorldDataAccessor) world).postMateria_getWorldDataState().get(key);
    }
}
