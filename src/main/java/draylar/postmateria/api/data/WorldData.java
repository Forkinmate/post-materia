package draylar.postmateria.api.data;

import draylar.postmateria.impl.WorldDataAccessor;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

/**
 * Represents arbitrary data attached to a {@link World}.
 */
public interface WorldData {

    /**
     * Serializes this data into a {@link NbtCompound}.
     * @param root a mutable tag to write data into
     */
    void writeNbt(NbtCompound root);

    /**
     * Deserializes data into this instance from the given {@link NbtCompound}.
     * @param root tag to read data from
     */
    void readNbt(NbtCompound root);

    /**
     * @return the {@link World} associated with this {@link WorldData} instance.
     */
    World getWorld();

    default void markDirty() {
        ((WorldDataAccessor) getWorld()).postMateria_getWorldDataState().markDirty();
    }
}
