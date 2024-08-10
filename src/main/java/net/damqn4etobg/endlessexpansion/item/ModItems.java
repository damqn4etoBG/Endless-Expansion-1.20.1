package net.damqn4etobg.endlessexpansion.item;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.block.ModBlocks;
import net.damqn4etobg.endlessexpansion.entity.custom.ArborBoatEntity;
import net.damqn4etobg.endlessexpansion.fluid.ModFluids;
import net.damqn4etobg.endlessexpansion.item.custom.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EndlessExpansion.MODID);

    public static final RegistryObject<Item> URANIUM_INGOT = ITEMS.register("uranium_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LUMINITE_STAFF = ITEMS.register("luminite_staff",
            LuminiteStaffItem::new);

    public static final RegistryObject<Item> LUMINITE = ITEMS.register("luminite",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> NUCLEAR_WASTE_BUCKET = ITEMS.register("nuclear_waste_bucket",
            () -> new BucketItem(ModFluids.SOURCE_NUCLEAR_WASTE,
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<Item> LUMINITE_ESSENCE_BUCKET = ITEMS.register("luminite_essence_bucket",
            () -> new BucketItem(ModFluids.SOURCE_LUMINITE_ESSENCE,
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<Item> ARBOR_STICK = ITEMS.register("arbor_stick",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_COBALT = ITEMS.register("raw_cobalt",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CELESTIAL_INGOT = ITEMS.register("celestial_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COBALT_SWORD = ITEMS.register("cobalt_sword",
            () -> new SwordItem(ModToolTiers.COBALT, 3, -2.4f, new Item.Properties()));

    public static final RegistryObject<Item> COBALT_PICKAXE = ITEMS.register("cobalt_pickaxe",
            () -> new PickaxeItem(ModToolTiers.COBALT, 1, -2.8f, new Item.Properties()));

    public static final RegistryObject<Item> COBALT_SHOVEL = ITEMS.register("cobalt_shovel",
            () -> new ShovelItem(ModToolTiers.COBALT, 1, -3f, new Item.Properties()));

    public static final RegistryObject<Item> COBALT_AXE = ITEMS.register("cobalt_axe",
            () -> new AxeItem(ModToolTiers.COBALT, 6, -3.0f, new Item.Properties()));

    public static final RegistryObject<Item> COBALT_HOE = ITEMS.register("cobalt_hoe",
            () -> new HoeItem(ModToolTiers.COBALT, -3, 0, new Item.Properties()));

    public static final RegistryObject<Item> COBALT_PAXEL = ITEMS.register("cobalt_paxel",
            () -> new PaxelItem(ModToolTiers.COBALT, 5, -2.8f, new Item.Properties()));

    public static final RegistryObject<Item> CELESTIAL_SWORD = ITEMS.register("celestial_sword",
            () -> new SwordItem(ModToolTiers.CELESTIAL, 5, -2f, new Item.Properties()));

    public static final RegistryObject<Item> CELESTIAL_PICKAXE = ITEMS.register("celestial_pickaxe",
            () -> new PickaxeItem(ModToolTiers.CELESTIAL, 1, -2.8f, new Item.Properties()));

    public static final RegistryObject<Item> CELESTIAL_SHOVEL = ITEMS.register("celestial_shovel",
            () -> new ShovelItem(ModToolTiers.CELESTIAL, 1, -2.7f, new Item.Properties()));

    public static final RegistryObject<Item> CELESTIAL_AXE = ITEMS.register("celestial_axe",
            () -> new AxeItem(ModToolTiers.CELESTIAL, 8, -3.0f, new Item.Properties()));

    public static final RegistryObject<Item> CELESTIAL_HOE = ITEMS.register("celestial_hoe",
            () -> new HoeItem(ModToolTiers.CELESTIAL, -5, -2f, new Item.Properties()));

    public static final RegistryObject<Item> CELESTIAL_PAXEL = ITEMS.register("celestial_paxel",
            () -> new PaxelItem(ModToolTiers.CELESTIAL, 7, -2.8f, new Item.Properties()));

    public static final RegistryObject<Item> MYSTICAL_EVERBLUE_POWDER = ITEMS.register("mystical_everblue_powder",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COBALT_HELMET = ITEMS.register("cobalt_helmet",
            () -> new ArmorItem(ModArmorMaterials.COBALT, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> COBALT_CHESTPLATE = ITEMS.register("cobalt_chestplate",
            () -> new ArmorItem(ModArmorMaterials.COBALT, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> COBALT_LEGGINGS = ITEMS.register("cobalt_leggings",
            () -> new ArmorItem(ModArmorMaterials.COBALT, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> COBALT_BOOTS = ITEMS.register("cobalt_boots",
            () -> new ArmorItem(ModArmorMaterials.COBALT, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> CELESTIAL_HELMET = ITEMS.register("celestial_helmet",
            () -> new ArmorItem(ModArmorMaterials.CELESTIAL, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> CELESTIAL_CHESTPLATE = ITEMS.register("celestial_chestplate",
            () -> new ArmorItem(ModArmorMaterials.CELESTIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> CELESTIAL_LEGGINGS = ITEMS.register("celestial_leggings",
            () -> new ArmorItem(ModArmorMaterials.CELESTIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> CELESTIAL_BOOTS = ITEMS.register("celestial_boots",
            () -> new ArmorItem(ModArmorMaterials.CELESTIAL, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> PYRONIUM = ITEMS.register("pyronium",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PYRONIUM_INFUSED_COAL = ITEMS.register("pyronium_infused_coal",
            () -> new FuelItem(new Item.Properties(), 10000));

    public static final RegistryObject<Item> ARBOR_BOAT = ITEMS.register("arbor_boat",
            () -> new ArborBoatItem(false, ArborBoatEntity.Type.ARBOR, new Item.Properties()));

    public static final RegistryObject<Item> ARBOR_CHEST_BOAT = ITEMS.register("arbor_chest_boat",
            () -> new ArborBoatItem(true, ArborBoatEntity.Type.ARBOR, new Item.Properties()));

    public static final RegistryObject<Item> ARBOR_SIGN = ITEMS.register("arbor_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.ARBOR_SIGN.get(), ModBlocks.ARBOR_WALL_SIGN.get()));

    public static final RegistryObject<Item> ARBOR_HANGING_SIGN = ITEMS.register("arbor_hanging_sign",
            () -> new HangingSignItem(ModBlocks.ARBOR_HANGING_SIGN.get(), ModBlocks.ARBOR_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> BLACK_OPAL = ITEMS.register("black_opal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SHADOW_ESSENCE = ITEMS.register("shadow_essence",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SHADOWSTEEL_INGOT = ITEMS.register("shadowsteel_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SHADOWSTEEL_SWORD = ITEMS.register("shadowsteel_sword",
            () -> new SwordItem(ModToolTiers.SHADOWSTEEL, 6, -2f, new Item.Properties()));

    public static final RegistryObject<Item> SHADOWSTEEL_PICKAXE = ITEMS.register("shadowsteel_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SHADOWSTEEL, 1, -2.8f, new Item.Properties()));

    public static final RegistryObject<Item> SHADOWSTEEL_SHOVEL = ITEMS.register("shadowsteel_shovel",
            () -> new ShovelItem(ModToolTiers.SHADOWSTEEL, 1, -2.7f, new Item.Properties()));

    public static final RegistryObject<Item> SHADOWSTEEL_AXE = ITEMS.register("shadowsteel_axe",
            () -> new AxeItem(ModToolTiers.SHADOWSTEEL, 9, -3.0f, new Item.Properties()));

    public static final RegistryObject<Item> SHADOWSTEEL_HOE = ITEMS.register("shadowsteel_hoe",
            () -> new HoeItem(ModToolTiers.SHADOWSTEEL, -5, -2f, new Item.Properties()));

    public static final RegistryObject<Item> SHADOWSTEEL_PAXEL = ITEMS.register("shadowsteel_paxel",
            () -> new PaxelItem(ModToolTiers.SHADOWSTEEL, 7, -2.8f, new Item.Properties()));

    public static final RegistryObject<Item> SHADOWSTEEL_HELMET = ITEMS.register("shadowsteel_helmet",
            () -> new ModArmorItem(ModArmorMaterials.SHADOWSTEEL, ArmorItem.Type.HELMET, new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltip.add(Component.translatable("tooltip.endlessexpansion.armor_set_bonus"));
                        pTooltip.add(Component.translatable("tooltip.endlessexpansion.shadowsteel_bonus"));
                    } else {
                        pTooltip.add(Component.translatable("tooltip.endlessexpansion.holdshift"));
                    }
                }
            });

    public static final RegistryObject<Item> SHADOWSTEEL_CHESTPLATE = ITEMS.register("shadowsteel_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.SHADOWSTEEL, ArmorItem.Type.CHESTPLATE, new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltip.add(Component.translatable("tooltip.endlessexpansion.armor_set_bonus"));
                        pTooltip.add(Component.translatable("tooltip.endlessexpansion.shadowsteel_bonus"));
                    } else {
                        pTooltip.add(Component.translatable("tooltip.endlessexpansion.holdshift"));
                    }
                }
            });

    public static final RegistryObject<Item> SHADOWSTEEL_LEGGINGS = ITEMS.register("shadowsteel_leggings",
            () -> new ModArmorItem(ModArmorMaterials.SHADOWSTEEL, ArmorItem.Type.LEGGINGS, new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltip.add(Component.translatable("tooltip.endlessexpansion.armor_set_bonus"));
                        pTooltip.add(Component.translatable("tooltip.endlessexpansion.shadowsteel_bonus"));
                    } else {
                        pTooltip.add(Component.translatable("tooltip.endlessexpansion.holdshift"));
                    }
                }
            });

    public static final RegistryObject<Item> SHADOWSTEEL_BOOTS = ITEMS.register("shadowsteel_boots",
            () -> new ModArmorItem(ModArmorMaterials.SHADOWSTEEL, ArmorItem.Type.BOOTS, new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltip.add(Component.translatable("tooltip.endlessexpansion.armor_set_bonus"));
                        pTooltip.add(Component.translatable("tooltip.endlessexpansion.shadowsteel_bonus"));
                    } else {
                        pTooltip.add(Component.translatable("tooltip.endlessexpansion.holdshift"));
                    }
                }
            });
    public static final RegistryObject<Item> FLAME_WAND = ITEMS.register("flame_wand",
            () -> new FlameWandItem(new Item.Properties().stacksTo(1).durability(250)));

    public static final RegistryObject<Item> ICE_WAND = ITEMS.register("ice_wand",
            () -> new IceWandItem(new Item.Properties().stacksTo(1).durability(250)));

    public static final RegistryObject<Item> FIRE_CORE = ITEMS.register("fire_core",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GLACIAL_CRYSTAL = ITEMS.register("glacial_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_SAPPHIRE = ITEMS.register("raw_sapphire",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> REFINED_SAPPHIRE = ITEMS.register("refined_sapphire",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GLASS_VIAL = ITEMS.register("glass_vial",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MYSTICAL_COOKIE = ITEMS.register("mystical_cookie",
            () -> new Item(new Item.Properties().food(ModFoods.MYSTICAL_COOKIE)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
