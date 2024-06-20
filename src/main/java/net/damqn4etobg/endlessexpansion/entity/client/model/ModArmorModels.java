package net.damqn4etobg.endlessexpansion.entity.client.model;

import net.damqn4etobg.endlessexpansion.entity.client.ModModelLayers;
import net.damqn4etobg.endlessexpansion.item.custom.ShadowsteelArmorItem;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class ModArmorModels {
    private static Map<EquipmentSlot, ModArmorModel> shadowsteel = Collections.emptyMap();
    private static Map<EquipmentSlot, ModArmorModel> make(EntityRendererProvider.Context ctx, ModelLayerLocation inner, ModelLayerLocation outer) {
        Map<EquipmentSlot, ModArmorModel> ret = new EnumMap<>(EquipmentSlot.class);
        for (var slot : EquipmentSlot.values()) {
            var mesh = ctx.bakeLayer(slot == EquipmentSlot.LEGS ? inner : outer);
            ret.put(slot, new ModArmorModel(mesh, slot));
        }
        return ret;
    }

    public static void init(EntityRendererProvider.Context ctx) {
        shadowsteel = make(ctx, ModModelLayers.SHADOWSTEEL_ARMOR_LAYER_INNER, ModModelLayers.SHADOWSTEEL_ARMOR_LAYER_OUTER);
    }

    @Nullable
    public static ModArmorModel get(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof ShadowsteelArmorItem armor) {
            return shadowsteel.get(armor.getEquipmentSlot());
        }
        return null;
    }
}
