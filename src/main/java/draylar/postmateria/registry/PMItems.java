package draylar.postmateria.registry;

import draylar.postmateria.PostMateria;
import draylar.postmateria.item.*;
import draylar.postmateria.material.MateriaToolMaterials;
import draylar.postmateria.material.PhantasmiteArmorMaterial;
import draylar.postmateria.material.PhantasmiteToolMaterial;
import draylar.postmateria.material.SoulblazeArmorMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class PMItems {

    // Phantasmite
    public static final Item PHANTASMITE = register("phantasmite", new Item(new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.MATERIALS)));
    public static final Item PHANTASMITE_SWORD = register("phantasmite_sword", new PhantasmiteSwordItem(PhantasmiteToolMaterial.INSTANCE, 4, -2.4f, new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));
    public static final Item PHANTASMITE_PICKAXE = register("phantasmite_pickaxe", new Item(new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));
    public static final Item PHANTASMITE_SHOVEL = register("phantasmite_shovel", new Item(new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));
    public static final Item PHANTASMITE_AXE = register("phantasmite_axe", new Item(new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));
    public static final Item PHANTASMITE_HOE = register("phantasmite_hoe", new Item(new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));
    public static final Item THUNDERING_PHANTASM = register("thundering_phantasm", new Item(new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));
    public static final Item PHANTASMITE_HELMET = register("phantasmite_helmet", new PhantasmiteArmorItem(PhantasmiteArmorMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));
    public static final Item PHANTASMITE_CHESTPLATE = register("phantasmite_chestplate", new PhantasmiteArmorItem(PhantasmiteArmorMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));
    public static final Item PHANTASMITE_LEGGINGS = register("phantasmite_leggings", new PhantasmiteArmorItem(PhantasmiteArmorMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));
    public static final Item PHANTASMITE_BOOTS = register("phantasmite_boots", new PhantasmiteArmorItem(PhantasmiteArmorMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));

    // Soulblaze
    public static final Item SOULBLAZE = register("soulblaze", new Item(new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.MATERIALS)));
    public static final Item SOULBLAZE_SWORD = register("soulblaze_sword", new SoulblazeSwordItem(PhantasmiteToolMaterial.INSTANCE, 4, -2.4f, new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.COMBAT)));
    public static final Item SOULBLAZE_PICKAXE = register("soulblaze_pickaxe", new SoulblazePickaxeItem(MateriaToolMaterials.SOULBLAZE, 1, -2.0f, new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.COMBAT)));
    public static final Item SOULBLAZE_SHOVEL = register("soulblaze_shovel", new Item(new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.COMBAT)));
    public static final Item SOULBLAZE_AXE = register("soulblaze_axe", new Item(new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.COMBAT)));
    public static final Item SOULBLAZE_HOE = register("soulblaze_hoe", new Item(new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.COMBAT)));
    public static final Item SOULBLAZE_HELMET = register("soulblaze_helmet", new ArmorItem(SoulblazeArmorMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.COMBAT)));
    public static final Item SOULBLAZE_CHESTPLATE = register("soulblaze_chestplate", new ArmorItem(SoulblazeArmorMaterial.INSTANCE, EquipmentSlot.CHEST, new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.COMBAT)));
    public static final Item SOULBLAZE_LEGGINGS = register("soulblaze_leggings", new ArmorItem(SoulblazeArmorMaterial.INSTANCE, EquipmentSlot.LEGS, new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.COMBAT)));
    public static final Item SOULBLAZE_BOOTS = register("soulblaze_boots", new ArmorItem(SoulblazeArmorMaterial.INSTANCE, EquipmentSlot.FEET, new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.COMBAT)));
    public static final Item SOUL_SURFER = register("soul_surfer", new SoulSurferItem(new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.COMBAT).maxDamage(2000)));


    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registry.ITEM, PostMateria.id(name), item);
    }

    public static void init() {
        // NO-OP
    }

    private PMItems() {
        // NO-OP
    }
}
