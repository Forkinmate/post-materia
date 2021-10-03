package draylar.postmateria.registry;

import draylar.postmateria.PostMateria;
import draylar.postmateria.entity.PhantasmaSpawnerBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

public class PMEntities {

    public static final BlockEntityType<PhantasmaSpawnerBlockEntity> PHANTASMA_SPAWNER = register("phantasma_spawner",
            FabricBlockEntityTypeBuilder.create(PhantasmaSpawnerBlockEntity::new, PMBlocks.PHANTASMA_SPAWNER)
                    .build(null));

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
