package draylar.postmateria.registry;

import draylar.postmateria.PostMateria;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;

public class PMWorld {

    public static final ConfiguredFeature<?, ?> SOUL_SAND_SOULBLAZE_ORE = Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            PostMateria.id("soul_sand_soulblaze_ore"),
            Feature.ORE.configure(
                    new OreFeatureConfig(
                            new BlockMatchRuleTest(Blocks.SOUL_SAND),
                            PMBlocks.SOULBLAZE_ORE.getDefaultState(),
                            20
                    )).range(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.getBottom(), YOffset.getTop()))).spreadHorizontally().repeat(100));


    public static void initialize() {
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY),
                GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, PostMateria.id("soul_sand_soulblaze_ore")));
    }
}
