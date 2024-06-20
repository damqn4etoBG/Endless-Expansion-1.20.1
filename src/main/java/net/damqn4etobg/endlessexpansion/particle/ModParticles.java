package net.damqn4etobg.endlessexpansion.particle;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EndlessExpansion.MODID);
    public static final RegistryObject<SimpleParticleType> SNOWFLAKE =
            PARTICLE_TYPES.register("snowflake", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SHADOW_ORB =
            PARTICLE_TYPES.register("shadow_orb", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SHADOW_STRIP =
            PARTICLE_TYPES.register("shadow_strip", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SHADOW_SMOKE =
            PARTICLE_TYPES.register("shadow_smoke", () -> new SimpleParticleType(true));
    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
