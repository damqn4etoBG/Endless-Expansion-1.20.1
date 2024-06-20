package net.damqn4etobg.endlessexpansion.block.custom;

import net.damqn4etobg.endlessexpansion.dimension.portal.WorldBeyondPortalShape;
import net.damqn4etobg.endlessexpansion.dimension.portal.WorldBeyondTeleporter;
import net.damqn4etobg.endlessexpansion.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class WorldBeyondPortalBlock extends NetherPortalBlock {
    public WorldBeyondPortalBlock() {
        super(BlockBehaviour.Properties.of()
                .pushReaction(PushReaction.BLOCK)
                .noCollission()
                .randomTicks()
                .strength(-1.0F)
                .sound(SoundType.GLASS)
                .lightLevel(s -> 10)
                .noLootTable());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
    }

    public static void portalSpawn(Level world, BlockPos pos) {
        Optional<WorldBeyondPortalShape> optional = WorldBeyondPortalShape.findEmptyPortalShape(world, pos, Direction.Axis.X);
        if (optional.isPresent()) {
            optional.get().createPortalBlocks();
        }
    }

    @Override
    public BlockState updateShape(BlockState p_54928_, Direction p_54929_, BlockState p_54930_, LevelAccessor p_54931_, BlockPos p_54932_, BlockPos p_54933_) {
        Direction.Axis direction$axis = p_54929_.getAxis();
        Direction.Axis direction$axis1 = p_54928_.getValue(AXIS);
        boolean flag = direction$axis1 != direction$axis && direction$axis.isHorizontal();
        return !flag && !p_54930_.is(this) && !(new WorldBeyondPortalShape(p_54931_, p_54932_, direction$axis1)).isComplete() ? Blocks.AIR.defaultBlockState() : super.updateShape(p_54928_, p_54929_, p_54930_, p_54931_, p_54932_, p_54933_);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        for (int i = 0; i < 1; i++) {
            double px = pos.getX() + random.nextFloat();
            double py = pos.getY() + 0.5;
            double pz = pos.getZ() + random.nextFloat();
            double vx = (random.nextFloat() - 0.5) / 4.0;
            double vy = -0.05D;
            double vz = (random.nextFloat() - 0.5) / 4.0;

            world.addParticle(ModParticles.SNOWFLAKE.get(), px, py, pz, vx, vy, vz);
        }
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity.canChangeDimensions() && !entity.level().isClientSide()) {
            if (entity.isOnPortalCooldown()) {
                entity.setPortalCooldown();
            } else if (entity.level().dimension() != ResourceKey.create(Registries.DIMENSION, new ResourceLocation("endlessexpansion:world_beyond"))) {
                entity.setPortalCooldown();
                teleportToDimension(entity, pos, ResourceKey.create(Registries.DIMENSION, new ResourceLocation("endlessexpansion:world_beyond")));
                entity.rotate(Rotation.CLOCKWISE_180);
            } else {
                entity.setPortalCooldown();
                teleportToDimension(entity, pos, Level.OVERWORLD);
            }
        }
    }

    private void teleportToDimension(Entity entity, BlockPos pos, ResourceKey<Level> destinationType) {
        entity.changeDimension(entity.getServer().getLevel(destinationType), new WorldBeyondTeleporter(entity.getServer().getLevel(destinationType), pos));
    }
}
