package net.damqn4etobg.endlessexpansion.item.alchemy;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, EndlessExpansion.MODID);
    public static final RegistryObject<Potion> MYSTICAL_POTION = POTIONS.register("mystical_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.HEALTH_BOOST, 2400, 2),
                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2400, 1),
                    new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2400, 1)));

    public static final RegistryObject<Potion> GLOWING_POTION = POTIONS.register("glowing_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.GLOWING, 2400, 1)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
