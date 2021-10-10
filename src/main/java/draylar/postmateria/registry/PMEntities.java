package draylar.postmateria.registry;

import draylar.postmateria.PostMateria;
import draylar.postmateria.entity.PhantasmaSpawnerBlockEntity;
import draylar.postmateria.entity.SoulSurferEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class PMEntities {

    public static final BlockEntityType<PhantasmaSpawnerBlockEntity> PHANTASMA_SPAWNER = register("phantasma_spawner",
            FabricBlockEntityTypeBuilder.create(PhantasmaSpawnerBlockEntity::new, PMBlocks.PHANTASMA_SPAWNER)
                    .build(null));

    public static final EntityType<SoulSurferEntity> SOUL_SURFER = register(
            "soul_surfer",
            FabricEntityTypeBuilder.create()
                    .spawnGroup(SpawnGroup.MISC)
                    .dimensions(EntityDimensions.fixed(1, 1))
                    .entityFactory(SoulSurferEntity::new)
                    .build());

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> entity) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, PostMateria.id(name), entity);
    }

    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> entity) {
        return Registry.register(Registry.ENTITY_TYPE, PostMateria.id(name), entity);
    }

    public static void init() {
        // NO-OP
    }

    private PMEntities() {
        // NO-OP
    }
}
