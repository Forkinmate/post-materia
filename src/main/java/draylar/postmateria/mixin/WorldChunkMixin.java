package draylar.postmateria.mixin;

import net.minecraft.world.Heightmap;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(WorldChunk.class)
public class WorldChunkMixin {

    @Shadow
    @Final
    private Map<Heightmap.Type, Heightmap> heightmaps;

    @Inject(method = "sampleHeightmap", at = @At("HEAD"), cancellable = true)
    public void sampleHeightmap(Heightmap.Type type, int x, int z, CallbackInfoReturnable<Integer> cir) {
        // Prevent NPE when re-generating some features such as Ores
        if (!heightmaps.containsKey(type)) {
            if (type == Heightmap.Type.WORLD_SURFACE_WG) {
                type = Heightmap.Type.WORLD_SURFACE;
            }

            if (type == Heightmap.Type.OCEAN_FLOOR_WG) {
                type = Heightmap.Type.OCEAN_FLOOR;
            }

            cir.setReturnValue((this.heightmaps.get(type)).get(x & 15, z & 15) - 1);
        }
    }
}
