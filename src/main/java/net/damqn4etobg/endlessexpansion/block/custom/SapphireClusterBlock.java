package net.damqn4etobg.endlessexpansion.block.custom;

import net.damqn4etobg.endlessexpansion.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SapphireClusterBlock extends AmethystClusterBlock {
    public SapphireClusterBlock(int pSize, int pOffset, Properties pProperties) {
        super(pSize, pOffset, pProperties);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        RandomSource random = RandomSource.create();
        double x = pPos.getX() + random.nextDouble() + 0.25;
        double y = pPos.getY() + random.nextDouble() + 0.125;
        double z = pPos.getZ() + random.nextDouble() + 0.25;

        if(random.nextFloat() < 0.25f) {
            pLevel.addParticle(ModParticles.SPARK.get(), x, y, z, 0d, 0.0125d, 0d);
        }
    }
}
