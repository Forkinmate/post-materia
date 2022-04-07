package draylar.postmateria.registry;

import draylar.postmateria.PostMateria;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.List;

public class PMWorld {

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> SOUL_SAND_SOULBLAZE_ORE = ConfiguredFeatures.register(
            PostMateria.id("soul_sand_soulblaze_ore").toString(),
            Feature.ORE,
            new OreFeatureConfig(
                    new BlockMatchRuleTest(Blocks.SOUL_SAND),
                    PMBlocks.SOULBLAZE_ORE.getDefaultState(),
                    20));

    public static final RegistryEntry<PlacedFeature> SOUL_SAND_SOULBLAZE_ORE_PLACED = PlacedFeatures.register(
            PostMateria.id("soul_sand_soulblaze_ore").toString(),
            SOUL_SAND_SOULBLAZE_ORE,
            List.of(CountPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, BiomePlacementModifier.of()));


    public static void initialize() {
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY),
                GenerationStep.Feature.UNDERGROUND_ORES,
                SOUL_SAND_SOULBLAZE_ORE_PLACED.getKey().get());
    }
}
