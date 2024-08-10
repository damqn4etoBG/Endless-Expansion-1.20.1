package net.damqn4etobg.endlessexpansion.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class VerticalWaterLineFeature extends Feature<NoneFeatureConfiguration> {
    public VerticalWaterLineFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        BlockPos pos = pContext.origin();
        LevelAccessor world = pContext.level();
        RandomSource random = pContext.random();

        int minY = -45;
        int maxY = -45;

        int randomY = minY + random.nextInt(maxY - minY + 1);

        for (int y = 62; y >= randomY; y--) {
            BlockPos currentPos = new BlockPos(pos.getX(), y, pos.getZ());
            BlockState waterState = Blocks.WATER.defaultBlockState();
            world.setBlock(currentPos, waterState, 3);
        }
        return true;
    }
}