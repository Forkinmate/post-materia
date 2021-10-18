package draylar.postmateria.mixin;

import draylar.postmateria.api.data.WorldDataKey;
import draylar.postmateria.api.data.WorldDataRegistry;
import draylar.postmateria.api.data.WorldDataState;
import draylar.postmateria.impl.WorldDataAccessor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerPersistentStateMixin extends World implements WorldDataAccessor {

    @Shadow
    public abstract PersistentStateManager getPersistentStateManager();

    @Unique
    private WorldDataState state;

    protected ServerPersistentStateMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DimensionType dimensionType, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
        super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);
    }

    @Inject(
            method = "<init>",
            at = @At("RETURN"))
    private void initializeWorldDataProviders(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey<World> worldKey, DimensionType dimensionType, WorldGenerationProgressListener worldGenerationProgressListener, ChunkGenerator chunkGenerator, boolean debugWorld, long seed, List<Spawner> spawners, boolean shouldTickTime, CallbackInfo ci) {
        state = getPersistentStateManager().getOrCreate(
                compound -> WorldDataState.readNbt((ServerWorld) (Object) this, compound),
                () -> new WorldDataState((ServerWorld) (Object) this),
                WorldDataState.nameFor(getDimension()));

        // Add State trackers based on "this" world's type.
        // The Overlord should always exist, so we use it to store 'global' (server-wide) data.
        // Register global data suppliers now.
        if (worldKey == World.OVERWORLD) {
            WorldDataRegistry.getGlobalSuppliers().forEach((key, supplier) -> {

                // Only register the given key if it does not exist yet.
                if (!state.getData().containsKey(key)) {
                    state.add((WorldDataKey) key, supplier.apply((ServerWorld) (Object) this));
                }
            });
        }

        // Register per-world data suppliers.
        WorldDataRegistry.getWorldSuppliers().forEach((key, supplier) -> {

            // Only register the given key if it does not exist yet.
            if (!state.getData().containsKey(key)) {
                state.add((WorldDataKey) key, supplier.apply((ServerWorld) (Object) this));
            }
        });

    }

    @Override
    public WorldDataState postMateria_getWorldDataState() {
        return state;
    }
}
