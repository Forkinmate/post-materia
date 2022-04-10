package draylar.postmateria;

import dev.hephaestus.fiblib.api.BlockFib;
import dev.hephaestus.fiblib.api.BlockFibRegistry;
import draylar.postmateria.command.GenerateCommand;
import draylar.postmateria.command.WorldDataTestCommand;
import draylar.postmateria.data.PreviousGenerationData;
import draylar.postmateria.data.WorldWitherData;
import draylar.postmateria.registry.PMBlocks;
import draylar.postmateria.registry.PMEntities;
import draylar.postmateria.registry.PMItems;
import draylar.postmateria.registry.PMWorld;
import draylar.worlddata.api.WorldData;
import draylar.worlddata.api.WorldDataKey;
import draylar.worlddata.api.WorldDataRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.chunk.Chunk;

import java.util.HashMap;
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
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            GenerateCommand.initialize();
            WorldDataTestCommand.initialize();
        }

        // Setup Fibs for Soulblaze Ore
        BlockFib fib = BlockFib.builder(PMBlocks.SOULBLAZE_ORE, Blocks.SOUL_SAND)
                .withCondition(player -> player.getServer() != null && !WorldData.getGlobalData(player.server, WITHER_SLAIN_DATA).isWitherSlain())
                .build();

        BlockFibRegistry.register(fib);
    }

    public static Identifier id(String name) {
        return new Identifier("postmateria", name);
    }
}
