package net.damqn4etobg.endlessexpansion.worldgen.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record UnderwaterHillsConfiguration(BlockState hillBlock, int minRadius, int maxRadius, int minHeight,
                                           int maxHeight) implements FeatureConfiguration {
    public static final Codec<UnderwaterHillsConfiguration> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BlockState.CODEC.fieldOf("hill_block").forGetter(config -> config.hillBlock),
                    Codec.INT.fieldOf("min_radius").forGetter(config -> config.minRadius),
                    Codec.INT.fieldOf("max_radius").forGetter(config -> config.maxRadius),
                    Codec.INT.fieldOf("min_height").forGetter(config -> config.minHeight),
                    Codec.INT.fieldOf("max_height").forGetter(config -> config.maxHeight)
            ).apply(instance, UnderwaterHillsConfiguration::new)
    );

}
