package net.damqn4etobg.endlessexpansion.entity.client;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation WRAITH_LAYER = new ModelLayerLocation(
            new ResourceLocation(EndlessExpansion.MODID, "wraith_layer"), "main");
    public static final ModelLayerLocation ARBOR_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(EndlessExpansion.MODID, "boat/arbor"), "main");
    public static final ModelLayerLocation ARBOR_CHEST_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(EndlessExpansion.MODID, "chest_boat/arbor"), "main");
    public static final ModelLayerLocation SHADOWSTEEL_ARMOR_LAYER_INNER = new ModelLayerLocation(
            new ResourceLocation(EndlessExpansion.MODID, "shadowsteel_armor_layer_inner"), "main");
    public static final ModelLayerLocation SHADOWSTEEL_ARMOR_LAYER_OUTER = new ModelLayerLocation(
            new ResourceLocation(EndlessExpansion.MODID, "shadowsteel_armor_layer_outer"), "main");
}
