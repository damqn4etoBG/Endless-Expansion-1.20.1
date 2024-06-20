package net.damqn4etobg.endlessexpansion.effect;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, EndlessExpansion.MODID);

    public static final RegistryObject<MobEffect> FREEZING = MOB_EFFECTS.register("freezing",
            () -> new FreezeEffect(MobEffectCategory.HARMFUL, 3124687));

    public static final RegistryObject<MobEffect> SHADOW_STATE = MOB_EFFECTS.register("shadow_state",
            () -> new ShadowStateEffect(MobEffectCategory.BENEFICIAL, 1315860).addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    "95103241-9486-42f0-91f0-251667b144f3", 0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
