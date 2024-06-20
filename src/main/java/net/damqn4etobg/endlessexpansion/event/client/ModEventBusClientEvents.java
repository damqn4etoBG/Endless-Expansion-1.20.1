package net.damqn4etobg.endlessexpansion.event.client;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.block.entity.ModBlockEntities;
import net.damqn4etobg.endlessexpansion.entity.client.ModModelLayers;
import net.damqn4etobg.endlessexpansion.entity.client.WraithModel;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EndlessExpansion.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.WRAITH_LAYER, WraithModel::createBodyLayer);

        event.registerLayerDefinition(ModModelLayers.ARBOR_BOAT_LAYER, BoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayers.ARBOR_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
    }
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        //event.registerBlockEntityRenderer(ModBlockEntities.INFUSER.get(), InfuserBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.ARBOR_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.ARBOR_HANGING_SIGN.get(), HangingSignRenderer::new);
    }
}
