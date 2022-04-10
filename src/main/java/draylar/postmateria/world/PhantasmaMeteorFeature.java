package draylar.postmateria.world;

import com.mojang.serialization.Codec;
import draylar.postmateria.registry.PMBlocks;
import draylar.postmateria.registry.PMWorld;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.*;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.random.SimpleRandom;

import java.util.Random;

public class PhantasmaMeteorFeature extends StructureFeature<DefaultFeatureConfig> {

    private static final PerlinNoiseSampler NOISE = new PerlinNoiseSampler(new SimpleRandom(new Random().nextLong()));

    public PhantasmaMeteorFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec, StructureGeneratorFactory.simple(context -> {
            double distanceFromSpawn = Math.sqrt(Math.pow(context.chunkPos().getStartX(), 2) + Math.pow(context.chunkPos().getStartZ(), 2));
            return context.isBiomeValid(Heightmap.Type.WORLD_SURFACE_WG) && distanceFromSpawn >= 15_000;
        }, PhantasmaMeteorFeature::addPieces));
    }

    public static <C extends FeatureConfig> void addPieces(StructurePiecesCollector collector, StructurePiecesGenerator.Context<C> context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), 90, context.chunkPos().getStartZ());
        collector.addPiece(new Generator(blockPos));
    }

    public static class Generator extends StructurePiece {

        public Generator(BlockPos pos) {
            super(PMWorld.PHANTASMA_METEOR_PIECE, 0, new BlockBox(pos).expand(48));
            setOrientation(null);
        }

        public Generator(StructureContext context, NbtCompound compound) {
            super(PMWorld.PHANTASMA_METEOR_PIECE, compound);
            setOrientation(null);
        }

        @Override
        protected void writeNbt(StructureContext context, NbtCompound nbt) {

        }

        @Override
        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pos) {
            pos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, pos);

            // The meteor will spawn within the ground.
            pos = pos.down(3);

            // The box for checking positions is 50x50x50.
            for (BlockPos potentialPos : BlockPos.iterateOutwards(pos, 25, 10, 25)) {

                // For each position that is within the 'radius' of the meteor, place a block.
                double eval = NOISE.sample(potentialPos.getX(), potentialPos.getY(), potentialPos.getZ());
                if(potentialPos.getSquaredDistance(pos) <= 50 + eval * 10) {
                    if(eval <= -0.3) {
                        addBlock(world, PMBlocks.PHANTASMITE_ORE.getDefaultState(), potentialPos.getX(), potentialPos.getY(), potentialPos.getZ(), chunkBox);
                    } else {
                        addBlock(world, Blocks.DEEPSLATE.getDefaultState(), potentialPos.getX(), potentialPos.getY(), potentialPos.getZ(), chunkBox);
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
                            addBlock(world, Blocks.AIR.getDefaultState(), potentialPos.getX(), potentialPos.getY(), potentialPos.getZ(), chunkBox);

                            // Lava under the y-level.
                            if(potentialPos.getY() <= pos.getY()) {
                                addBlock(world, Blocks.LAVA.getDefaultState(), potentialPos.getX(), potentialPos.getY(), potentialPos.getZ(), chunkBox);
                            }

                            // Phantasma Geysers
                            if(distance <= 15 + sample && distance >= 5) {
                                if(potentialPos.getY() == pos.getY() - 5) {
                                    if(world.getRandom().nextDouble() <= 0.1) {
                                        addBlock(world, PMBlocks.PHANTASMITE_GEYSER.getDefaultState(), potentialPos.getX(), potentialPos.getY(), potentialPos.getZ(), chunkBox);
                                    }
                                }
                            }
                        }

                        // at the bottom y position, some blocks are phantasma geysers
                    } else if(distance <= 25 + sample) {
                        if(potentialPos.getY() < world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, potentialPos.getX(), potentialPos.getZ())) {
                            if(world.getRandom().nextDouble() <= 0.5) {
                                addBlock(world, Blocks.DEEPSLATE.getDefaultState(), potentialPos.getX(), potentialPos.getY(), potentialPos.getZ(), chunkBox);
                            } else {
                                addBlock(world, Blocks.COBBLED_DEEPSLATE.getDefaultState(), potentialPos.getX(), potentialPos.getY(), potentialPos.getZ(), chunkBox);
                            }
                        }
                    }
                }
            }

            // The center of the meteor houses a custom mob spawner.
            addBlock(world, PMBlocks.PHANTASMA_SPAWNER.getDefaultState(), pos.getX(), pos.getY(), pos.getZ(), chunkBox);
        }
    }
}
