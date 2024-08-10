package net.damqn4etobg.endlessexpansion.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties MYSTICAL_COOKIE = new FoodProperties.Builder().nutrition(4)
            .saturationMod(0.3f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1), 1f)
            .effect(() -> new MobEffectInstance(MobEffects.JUMP, 600, 1), 1f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 600), 0.75f).build();
}
