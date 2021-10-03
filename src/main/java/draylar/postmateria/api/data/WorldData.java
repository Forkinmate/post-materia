package draylar.postmateria.api.data;

import draylar.postmateria.impl.WorldDataAccessor;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public interface WorldData {
    NbtCompound writeNbt(NbtCompound root);
    void readNbt(NbtCompound root);
    World getWorld();

    default void markDirty() {
        ((WorldDataAccessor) getWorld()).postMateria_getWorldDataState().markDirty();
    }
}
