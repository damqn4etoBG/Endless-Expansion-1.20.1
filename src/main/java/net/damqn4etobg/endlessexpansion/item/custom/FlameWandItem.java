package net.damqn4etobg.endlessexpansion.item.custom;

import net.damqn4etobg.endlessexpansion.effect.ModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class FlameWandItem extends Item {
    public FlameWandItem(Properties pProperties) {
        super(pProperties);
    }
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (itemstack.isDamageableItem() && itemstack.getDamageValue() < itemstack.getMaxDamage()) {
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                    SoundEvents.FIRECHARGE_USE, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            pPlayer.getCooldowns().addCooldown(this, 20);
            Position position = pPlayer.getPosition(Minecraft.getInstance().getDeltaFrameTime());
            Direction direction = pPlayer.getDirection();
            double d0 = position.x() + (double) ((float) direction.getStepX() * 0.35F);
            double d1 = position.y() + (double) ((float) direction.getStepY() * 0.35F) + 1;
            double d2 = position.z() + (double) ((float) direction.getStepZ() * 0.35F);
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
        }

        @Override
        protected void onHitEntity(EntityHitResult pResult) {
            super.onHitEntity(pResult);
            Entity entity = pResult.getEntity();
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                if(livingEntity.hasEffect(ModMobEffects.FREEZING.get())) {
                    livingEntity.removeEffect(ModMobEffects.FREEZING.get());
                }
            }
        }

        @Override
        protected void onHitBlock(BlockHitResult pResult) {
            super.onHitBlock(pResult);
            BlockPos blockPos = pResult.getBlockPos();
            BlockState blockState = this.level().getBlockState(blockPos);

            if (blockState.getBlock() == Blocks.WATER) {
                this.remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }
}