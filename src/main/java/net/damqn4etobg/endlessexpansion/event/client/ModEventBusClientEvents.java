package net.damqn4etobg.endlessexpansion.event.client;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.block.entity.ModBlockEntities;
import net.damqn4etobg.endlessexpansion.entity.client.ModModelLayers;
import net.damqn4etobg.endlessexpansion.entity.client.WraithModel;
import net.damqn4etobg.endlessexpansion.particle.ModParticles;
import net.damqn4etobg.endlessexpansion.particle.custom.ShadowOrbParticle;
import net.damqn4etobg.endlessexpansion.particle.custom.ShadowSmokeParticle;
import net.damqn4etobg.endlessexpansion.particle.custom.ShadowStripParticle;
import net.damqn4etobg.endlessexpansion.particle.custom.SnowflakeParticle;
import net.damqn4etobg.endlessexpansion.util.KeyBinding;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
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

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SHADOW_ORB.get(), ShadowOrbParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SHADOW_STRIP.get(), ShadowStripParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SHADOW_SMOKE.get(), ShadowSmokeParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("freeze", FreezingHudOverlay.HUD_FREEZE);
        EndlessExpansion.LOGGER.info("Registering Freeze Overlay");
    }
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.DASHING_KEY);
    }
}
