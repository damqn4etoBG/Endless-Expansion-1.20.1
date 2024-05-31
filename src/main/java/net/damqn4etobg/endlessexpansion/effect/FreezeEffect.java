package net.damqn4etobg.endlessexpansion.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;


public class FreezeEffect extends MobEffect {
    private Minecraft minecraft = Minecraft.getInstance();
    public FreezeEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int amplifier) {
        GuiGraphics guiGraphics = new GuiGraphics(minecraft, minecraft.renderBuffers().bufferSource());
        BlockPos playerPos = pLivingEntity.blockPosition().below();
        BlockState blockState = pLivingEntity.level().getBlockState(playerPos);
        if (pLivingEntity.hasEffect(ModMobEffects.FREEZING.get()) && !(blockState.isAir() || blockState.is(Blocks.WATER))) {
            pLivingEntity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
            //FreezeEffectRenderer.renderOutline(guiGraphics);
        }

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
