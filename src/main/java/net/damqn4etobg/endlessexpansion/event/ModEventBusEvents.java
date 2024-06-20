package net.damqn4etobg.endlessexpansion.event;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.entity.ModEntities;
import net.damqn4etobg.endlessexpansion.entity.custom.WraithEntity;
import net.damqn4etobg.endlessexpansion.particle.ModParticles;
import net.damqn4etobg.endlessexpansion.particle.custom.ShadowOrbParticle;
import net.damqn4etobg.endlessexpansion.particle.custom.ShadowSmokeParticle;
import net.damqn4etobg.endlessexpansion.particle.custom.ShadowStripParticle;
import net.damqn4etobg.endlessexpansion.particle.custom.SnowflakeParticle;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EndlessExpansion.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.WRAITH.get(), WraithEntity.createAttributes().build());
    }
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SHADOW_ORB.get(), ShadowOrbParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SHADOW_STRIP.get(), ShadowStripParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SHADOW_SMOKE.get(), ShadowSmokeParticle.Provider::new);
    }
}
