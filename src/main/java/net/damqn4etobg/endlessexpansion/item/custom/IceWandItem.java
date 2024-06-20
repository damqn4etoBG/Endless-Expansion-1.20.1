package net.damqn4etobg.endlessexpansion.item.custom;

import net.damqn4etobg.endlessexpansion.effect.ModMobEffects;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import org.joml.Vector3f;

public class IceWandItem extends Item {
    public IceWandItem(Properties pProperties) {
        super(pProperties);
    }
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        ItemStack projectileStack = Blocks.ICE.asItem().getDefaultInstance();
        if (itemstack.isDamageableItem() && itemstack.getDamageValue() < itemstack.getMaxDamage()) {
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                    SoundEvents.SNOW_HIT, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            pPlayer.getCooldowns().addCooldown(this, 20);
            if (!pLevel.isClientSide) {
                Snowball snowball = new IceWandProjectile(pLevel, pPlayer);
                snowball.setItem(projectileStack);
                snowball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 2F, 1.0F);
                pLevel.addFreshEntity(snowball);
            }
            itemstack.hurtAndBreak(1, pPlayer, onBroken -> onBroken.broadcastBreakEvent(pUsedHand));
            return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }
    public static class IceWandProjectile extends Snowball {
        private static final int PARTICLE_INTERVAL = 1;
        private static final Vector3f PARTICLE_COLOR = new Vector3f(0.0f, 1.0f, 1.0f);

        public IceWandProjectile(EntityType<? extends Snowball> pEntityType, Level pLevel) {
            super(pEntityType, pLevel);
        }

        public IceWandProjectile(Level pLevel, LivingEntity pShooter) {
            super(pLevel, pShooter);
        }

        public IceWandProjectile(Level pLevel, double pX, double pY, double pZ) {
            super(pLevel, pX, pY, pZ);
        }

        @Override
        public void tick() {
            super.tick();
            if (!this.level().isClientSide() && this.tickCount % PARTICLE_INTERVAL == 0) {
                spawnTrailParticle();
            }
        }

        private void spawnTrailParticle() {
            // Adjust particle spawn position as needed
            double posX = this.getX();
            double posY = this.getY();
            double posZ = this.getZ();

            // Spawn blue particle at the projectile's position
            ((ServerLevel) this.level()).sendParticles(new DustParticleOptions(PARTICLE_COLOR, 1f),
                    posX, posY, posZ, 2, 0.0D, 0.0D, 0.0D, 0.0D);
        }

        @Override
        protected void onHitEntity(EntityHitResult pResult) {
            super.onHitEntity(pResult);
            Entity entity = pResult.getEntity();
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(ModMobEffects.FREEZING.get(), 200, 1, false, false));
            }
        }
    }
}
