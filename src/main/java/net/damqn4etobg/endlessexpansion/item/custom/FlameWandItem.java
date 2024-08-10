package net.damqn4etobg.endlessexpansion.item.custom;

import net.damqn4etobg.endlessexpansion.effect.ModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class FlameWandItem extends Item {
    public FlameWandItem(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (itemstack.isDamageableItem() && itemstack.getDamageValue() < itemstack.getMaxDamage()) {
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                    SoundEvents.FIRECHARGE_USE, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            pPlayer.getCooldowns().addCooldown(this, 20);
            double d0 = pPlayer.getX() + (double) ((float) pPlayer.getDirection().getStepX() * 0.35F);
            double d1 = pPlayer.getY() + (double) ((float) pPlayer.getDirection().getStepY() * 0.35F) + 1;
            double d2 = pPlayer.getZ() + (double) ((float) pPlayer.getDirection().getStepZ() * 0.35F);
            if (!pLevel.isClientSide) {
                SmallFireball smallfireball = new CustomSmallFireball(pLevel, d0, d1, d2, 0, 0, 0);
                // smallfireball.setItem(itemstack); the item shot, in this case the wand, we want a fireball.
                smallfireball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 3F, 1.0F);
                pLevel.addFreshEntity(smallfireball);
            }
            itemstack.hurtAndBreak(1, pPlayer, onBroken -> onBroken.broadcastBreakEvent(pUsedHand));
            return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    public static class CustomSmallFireball extends SmallFireball {
        private int ticksCounted = 0;

        public CustomSmallFireball(Level pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        }

        @Override
        public void tick() {
            super.tick();
            ticksCounted++;
            if (ticksCounted >= 40) {
                this.remove(Entity.RemovalReason.DISCARDED);
            }
            if(this.isInWater()) {
                this.remove(Entity.RemovalReason.DISCARDED);
            }
        }

        @Override
        protected void onHitEntity(EntityHitResult pResult) {
            super.onHitEntity(pResult);
            Entity entity = pResult.getEntity();
            if (entity instanceof LivingEntity livingEntity) {
                if(livingEntity.hasEffect(ModMobEffects.FREEZING.get())) {
                    livingEntity.removeEffect(ModMobEffects.FREEZING.get());
                }
            }
        }
    }
}