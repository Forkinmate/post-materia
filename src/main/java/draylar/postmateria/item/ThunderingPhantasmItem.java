package draylar.postmateria.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ThunderingPhantasmItem extends Item {

    public ThunderingPhantasmItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {

            // Summon Lightning where the player is looking.
            HitResult raycast = user.raycast(128, 0, false);
            LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            lightning.updatePosition(raycast.getPos().getX(), raycast.getPos().getY(), raycast.getPos().getZ());
            world.spawnEntity(lightning);

            // Update the cooldown for the Thundering Phantasm item.
            user.getItemCooldownManager().set(this, 20 * 3);

            // Damage the item.
            user.getStackInHand(hand).damage(1, user, holder -> holder.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
