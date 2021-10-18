package draylar.postmateria.registry;

import draylar.postmateria.PostMateria;
import draylar.postmateria.item.*;
import draylar.postmateria.material.MateriaToolMaterials;
import draylar.postmateria.material.PhantasmiteArmorMaterial;
import draylar.postmateria.material.PhantasmiteToolMaterial;
import draylar.postmateria.material.SoulblazeArmorMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class PMItems {

    // Phantasmite
    public static final Item PHANTASMITE = register("phantasmite", new Item(new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.MATERIALS)));
    public static final Item PHANTASMITE_SWORD = register("phantasmite_sword", new PhantasmiteSwordItem(PhantasmiteToolMaterial.INSTANCE, 4, -2.4f, new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));
    public static final Item PHANTASMITE_PICKAXE = register("phantasmite_pickaxe", new PhantasmitePickaxeItem(MateriaToolMaterials.PHANTASMITE, -1, -2.0f, new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));
    public static final Item PHANTASMITE_SHOVEL = register("phantasmite_shovel", new ShovelItem(MateriaToolMaterials.PHANTASMITE, 0, -2.0f, new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));
    public static final Item PHANTASMITE_AXE = register("phantasmite_axe", new AxeItem(MateriaToolMaterials.PHANTASMITE, 5, -2.6f, new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)) {});
    public static final Item PHANTASMITE_HOE = register("phantasmite_hoe", new HoeItem(MateriaToolMaterials.PHANTASMITE, -2, -2.0f, new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)) {});
    public static final Item THUNDERING_PHANTASM = register("thundering_phantasm", new ThunderingPhantasmItem(new Item.Settings().rarity(Rarity.EPIC).maxDamage(500).group(ItemGroup.COMBAT)));
    public static final Item PHANTASMITE_HELMET = register("phantasmite_helmet", new PhantasmiteArmorItem(PhantasmiteArmorMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));
    public static final Item PHANTASMITE_CHESTPLATE = register("phantasmite_chestplate", new PhantasmiteArmorItem(PhantasmiteArmorMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));
    public static final Item PHANTASMITE_LEGGINGS = register("phantasmite_leggings", new PhantasmiteArmorItem(PhantasmiteArmorMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));
    public static final Item PHANTASMITE_BOOTS = register("phantasmite_boots", new PhantasmiteArmorItem(PhantasmiteArmorMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Settings().rarity(Rarity.EPIC).group(ItemGroup.COMBAT)));

    // Soulblaze
    public static final Item SOULBLAZE = register("soulblaze", new Item(new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.MATERIALS)));
    public static final Item SOULBLAZE_SWORD = register("soulblaze_sword", new SoulblazeSwordItem(PhantasmiteToolMaterial.INSTANCE, 4, -2.4f, new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.COMBAT)));
    public static final Item SOULBLAZE_PICKAXE = register("soulblaze_pickaxe", new PickaxeItem(MateriaToolMaterials.SOULBLAZE, 1, -2.0f, new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.COMBAT)) {});
    public static final Item SOULBLAZE_SHOVEL = register("soulblaze_shovel", new ShovelItem(MateriaToolMaterials.SOULBLAZE, -2, -2.0f, new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.COMBAT)));
    public static final Item SOULBLAZE_AXE = register("soulblaze_axe", new AxeItem(MateriaToolMaterials.SOULBLAZE, 5, -2.0f, new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.COMBAT)) {});
    public static final Item SOULBLAZE_HOE = register("soulblaze_hoe", new HoeItem(MateriaToolMaterials.SOULBLAZE, -3, -2.0f, new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.COMBAT)) {});
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
