package draylar.postmateria.world;

import com.mojang.serialization.Codec;
import draylar.postmateria.registry.PMBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.random.SimpleRandom;

import java.util.Random;

public class PhantasmaMeteorFeature extends Feature<DefaultFeatureConfig> {

    private static final PerlinNoiseSampler NOISE = new PerlinNoiseSampler(new SimpleRandom(new Random().nextLong()));

    public PhantasmaMeteorFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        return false;
    }

    public static void generate(World world, BlockPos pos) {
        // The meteor will spawn within the ground.
        pos = pos.down(3);

        // The box for checking positions is 50x50x50.
        for (BlockPos potentialPos : BlockPos.iterateOutwards(pos, 25, 10, 25)) {

            // For each position that is within the 'radius' of the meteor, place a block.
            double eval = NOISE.sample(potentialPos.getX(), potentialPos.getY(), potentialPos.getZ());
            if(potentialPos.getSquaredDistance(pos) <= 50 + eval * 10) {
                if(eval <= -0.3) {
                    world.setBlockState(potentialPos, PMBlocks.PHANTASMITE_ORE.getDefaultState());
                } else {
                    world.setBlockState(potentialPos, Blocks.DEEPSLATE.getDefaultState());
                }
            }

            // Effects outside the meteor circle
            else {
                // Check for an outer radius without respect to y-level
                double distance = Math.sqrt(Math.pow(potentialPos.getX() - pos.getX(), 2) + Math.pow(potentialPos.getZ() - pos.getZ(), 2));

                // <= 20 distance is air air/or lava.
                double sample = NOISE.sample(potentialPos.getX() / 5f, potentialPos.getY() / 5f, potentialPos.getZ() / 5f) * 8;
                if(distance <= 20 + sample) {
                    if(potentialPos.getY() >= pos.getY() - 5) {
                        world.setBlockState(potentialPos, Blocks.AIR.getDefaultState());

                        // Lava under the y-level.
                        if(potentialPos.getY() <= pos.getY()) {
                            world.setBlockState(potentialPos, Blocks.LAVA.getDefaultState());
                        }

                        // Phantasma Geysers
                        if(distance <= 15 + sample && distance >= 5) {
                            if(potentialPos.getY() == pos.getY() - 5) {
                                if(world.getRandom().nextDouble() <= 0.1) {
                                    world.setBlockState(potentialPos, PMBlocks.PHANTASMITE_GEYSER.getDefaultState());
                                }
                            }
                        }
                    }

                    // at the bottom y position, some blocks are phantasma geysers
                } else if(distance <= 25 + sample) {
                    if(!world.getBlockState(potentialPos).isAir()) {
                        if(world.random.nextDouble() <= 0.5) {
                            world.setBlockState(potentialPos, Blocks.DEEPSLATE.getDefaultState());
                        } else {
                            world.setBlockState(potentialPos, Blocks.COBBLED_DEEPSLATE.getDefaultState());
                        }
                    }
                }
            }
        }

        // The center of the meteor houses a custom mob spawner.
        // TODO: ^
    }
}
