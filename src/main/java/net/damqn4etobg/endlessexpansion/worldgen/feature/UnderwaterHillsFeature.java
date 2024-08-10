package net.damqn4etobg.endlessexpansion.worldgen.feature;

import com.mojang.serialization.Codec;
import net.damqn4etobg.endlessexpansion.worldgen.feature.configuration.UnderwaterHillsConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class UnderwaterHillsFeature extends Feature<UnderwaterHillsConfiguration> {
    public UnderwaterHillsFeature(Codec<UnderwaterHillsConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<UnderwaterHillsConfiguration> context) {
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        WorldGenLevel world = context.level();
        UnderwaterHillsConfiguration config = context.config();

        int hillRadius = random.nextIntBetweenInclusive(config.minRadius(), config.maxRadius());
        int hillHeight = random.nextIntBetweenInclusive(config.minHeight(), config.maxHeight());

        for (int x = -hillRadius; x <= hillRadius; x++) {
            for (int z = -hillRadius; z <= hillRadius; z++) {
                if (x * x + z * z <= hillRadius * hillRadius) {
                    double distanceFromCenter = Math.sqrt(x * x + z * z) / hillRadius;
                    int yOffset = (int) (hillHeight * (1 - distanceFromCenter * distanceFromCenter));

                    BlockPos hillPos = origin.offset(x, yOffset, z);
                    if (world.getBlockState(hillPos).is(Blocks.WATER)) {
                        world.setBlock(hillPos, config.hillBlock(), 2);

                        // Fill any air gaps below the hill
                        BlockPos below = hillPos.below();
                        while (world.getBlockState(below).is(Blocks.WATER) || world.getBlockState(below).isAir()) {
                            world.setBlock(below, config.hillBlock(), 2);
                            below = below.below();
                        }
                    }
                }
            }
        }
        return true;
    }
}
