package draylar.postmateria.item;

import draylar.postmateria.api.client.PhantasmiteTooltipHelper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PhantasmiteArmorItem extends ArmorItem {

    public PhantasmiteArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(new TranslatableText("postmateria.translation.phantasmite_armor"));

        // If the player has all 4 Phantasmite armor equipped, they gain an additional 10% bonus.
        if(world != null && world.isClient) {
            if(PhantasmiteTooltipHelper.hasFullSet()) {
                tooltip.add(new TranslatableText("postmateria.translation.phantasmite_set"));
            }
        }
    }
}
