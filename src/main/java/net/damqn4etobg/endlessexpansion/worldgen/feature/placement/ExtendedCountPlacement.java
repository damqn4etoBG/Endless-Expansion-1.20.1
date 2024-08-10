package net.damqn4etobg.endlessexpansion.worldgen.feature.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.placement.RepeatingPlacement;

public class ExtendedCountPlacement extends RepeatingPlacement {
    public static final Codec<ExtendedCountPlacement> CODEC = IntProvider.codec(0, 512).fieldOf("count").xmap(ExtendedCountPlacement::new, (countPlacement)
            -> countPlacement.count).codec();
    private final IntProvider count;

    private ExtendedCountPlacement(IntProvider p_191627_) {
        this.count = p_191627_;
    }

    public static ExtendedCountPlacement of(IntProvider pCount) {
        return new ExtendedCountPlacement(pCount);
    }

    public static ExtendedCountPlacement of(int pCount) {
        return of(ConstantInt.of(pCount));
    }

    protected int count(RandomSource pRandom, BlockPos pPos) {
        return this.count.sample(pRandom);
    }

    public PlacementModifierType<?> type() {
        return ModPlacementModifierTypes.EXTENDED_COUNT.get();
    }
}
