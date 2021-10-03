package draylar.postmateria.world;

import com.mojang.serialization.Codec;
import draylar.postmateria.PostMateria;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

/**
 * This class represents an {@link OreFeature} which only spawns after the Wither has been slain.
 */
public class WitherOreFeature extends OreFeature {

    public WitherOreFeature(Codec<OreFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<OreFeatureConfig> context) {
        if(context.getWorld().getServer() != null) {
            if (PostMateria.getGlobalData(context.getWorld().getServer(), PostMateria.WITHER_SLAIN_DATA).isWitherSlain()) {
                // Let the world data container know Soulblaze Ore has attempted to generate inside this chunk.
                // We intentionally only flag inside the Wither Slain check so that future Chunk Loads check to see if they can re-generate Soulblaze Ore.
                PostMateria.getData(context.getWorld().toServerWorld(), PostMateria.PREVIOUS_GENERATION_DATA).flagSoulblaze(new ChunkPos(context.getOrigin()));

                // Attempt to generate the feature.
                return super.generate(context);
            }
        }

        return false;
    }
}
