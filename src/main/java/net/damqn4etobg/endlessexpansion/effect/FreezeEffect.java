package net.damqn4etobg.endlessexpansion.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;


public class FreezeEffect extends MobEffect {
    public FreezeEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int amplifier) {
        BlockPos playerPos = pLivingEntity.blockPosition().below();
        BlockState blockState = pLivingEntity.level().getBlockState(playerPos);
        if (pLivingEntity.hasEffect(ModMobEffects.FREEZING.get()) && pLivingEntity.onGround() || pLivingEntity.isInWater()) {
            pLivingEntity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
        }
        // oh lmao
        pLivingEntity.isInPowderSnow = true;
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
