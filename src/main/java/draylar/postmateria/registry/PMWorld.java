package draylar.postmateria.registry;

import draylar.postmateria.PostMateria;
import draylar.postmateria.mixin.StructureFeatureAccessor;
import draylar.postmateria.world.PhantasmaMeteorFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureSets;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.List;

public class PMWorld {

    public static final StructureFeature<DefaultFeatureConfig> PHANTASMA_METEOR_SF = register("phantasma_meteor", new PhantasmaMeteorFeature(DefaultFeatureConfig.CODEC), GenerationStep.Feature.TOP_LAYER_MODIFICATION);
    public static final RegistryEntry<ConfiguredStructureFeature<?, ?>> PHANTASMA_METEOR_CSF = register(keyOf("phantasma_meteor"), PHANTASMA_METEOR_SF.configure(new DefaultFeatureConfig(), BiomeTags.DESERT_PYRAMID_HAS_STRUCTURE));
    public static final StructurePieceType PHANTASMA_METEOR_PIECE = registerPiece("phantasma_meteor", PhantasmaMeteorFeature.Generator::new);
    public static final RegistryEntry<StructureSet> METEORS = StructureSets.register(structureSetKey("meteors"), PHANTASMA_METEOR_CSF, new RandomSpreadStructurePlacement(10, 5, SpreadType.TRIANGULAR, 57159829));

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> SOUL_SAND_SOULBLAZE_ORE = ConfiguredFeatures.register(
            PostMateria.id("soul_sand_soulblaze_ore").toString(),
            Feature.ORE,
            new OreFeatureConfig(
                    new BlockMatchRuleTest(Blocks.SOUL_SAND),
                    PMBlocks.SOULBLAZE_ORE.getDefaultState(),
                    3));

    public static final RegistryEntry<PlacedFeature> SOUL_SAND_SOULBLAZE_ORE_PLACED = PlacedFeatures.register(
            PostMateria.id("soul_sand_soulblaze_ore").toString(),
            SOUL_SAND_SOULBLAZE_ORE,
            List.of(CountPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, BiomePlacementModifier.of()));

    public static void initialize() {
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY),
                GenerationStep.Feature.UNDERGROUND_ORES,
                SOUL_SAND_SOULBLAZE_ORE_PLACED.getKey().get());
    }

    private static <F extends StructureFeature<?>> F register(String name, F structureFeature, GenerationStep.Feature step) {
        StructureFeatureAccessor.getSTRUCTURE_TO_GENERATION_STEP().put(structureFeature, step);
        return Registry.register(Registry.STRUCTURE_FEATURE, name, structureFeature);
    }

    private static <FC extends FeatureConfig, F extends StructureFeature<FC>> RegistryEntry<ConfiguredStructureFeature<?, ?>> register(RegistryKey<ConfiguredStructureFeature<?, ?>> key, ConfiguredStructureFeature<FC, F> configuredStructureFeature) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, key, configuredStructureFeature);
    }

    private static StructurePieceType registerPiece(String id, StructurePieceType type) {
        return  Registry.register(Registry.STRUCTURE_PIECE, PostMateria.id(id), type);
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, PostMateria.id(name), feature);
    }

    private static RegistryKey<ConfiguredStructureFeature<?, ?>> keyOf(String id) {
        return RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, PostMateria.id(id));
    }

    private static RegistryKey<StructureSet> structureSetKey(String id) {
        return RegistryKey.of(Registry.STRUCTURE_SET_KEY, PostMateria.id(id));
    }
}
