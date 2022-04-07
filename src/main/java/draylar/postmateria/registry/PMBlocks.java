package draylar.postmateria.registry;

import draylar.postmateria.PostMateria;
import draylar.postmateria.block.PhantasmaGeyserBlock;
import draylar.postmateria.block.PhantasmaSpawnerBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoulSandBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class PMBlocks {

    // Phantasmite ---------------------------------------------------------------------------------------------------
    public static final Block PHANTASMITE_ORE = register("phantasmite_ore", new OreBlock(
                    FabricBlockSettings.of(Material.STONE)
                            .requiresTool()
                            .strength(3, 3)
                            .sounds(BlockSoundGroup.DEEPSLATE)),
            new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.MATERIALS));

    public static final Block PHANTASMITE_BLOCK = register("phantasmite_block", new Block(
            FabricBlockSettings.of(Material.METAL)
                    .requiresTool()
                    .strength(3, 3)
    ), new Item.Settings().rarity(Rarity.EPIC));

    public static final Block PHANTASMITE_GEYSER = register("phantasmite_geyser", new PhantasmaGeyserBlock(
            FabricBlockSettings.of(Material.STONE)
                    .requiresTool()
                    .strength(3, 3)
                    .sounds(BlockSoundGroup.DEEPSLATE)
    ), new Item.Settings().rarity(Rarity.EPIC));

    public static final Block PHANTASMA_SPAWNER = register("phantasma_spawner", new PhantasmaSpawnerBlock(
            FabricBlockSettings.of(Material.STONE)
                    .requiresTool()
                    .strength(3, 3)
                    .sounds(BlockSoundGroup.DEEPSLATE)
                    .nonOpaque()
    ), new Item.Settings().rarity(Rarity.EPIC));

    // Soulblaze ---------------------------------------------------------------------------------------------------
    public static final Block SOULBLAZE_ORE = register("soulblaze_ore", new SoulSandBlock(
                    FabricBlockSettings.of(Material.AGGREGATE)
                            .velocityMultiplier(0.4f)
                            .requiresTool()
                            .strength(2, 2)
                            .sounds(BlockSoundGroup.SOUL_SAND)),
            new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.MATERIALS));

    private static <T extends Block> T register(String name, T block, Item.Settings settings) {
        T registeredBlock = Registry.register(Registry.BLOCK, PostMateria.id(name), block);
        Registry.register(Registry.ITEM, PostMateria.id(name), new BlockItem(registeredBlock, settings));
        return registeredBlock;
    }

    private static <T extends Block> T register(String name, T block) {
        return Registry.register(Registry.BLOCK, PostMateria.id(name), block);
    }

    public static void init() {
        // NO-OP
    }

    private PMBlocks() {
        // NO-OP
    }
}
