package net.damqn4etobg.endlessexpansion.worldgen.feature.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public class EverySurfaceBlockPlacement extends PlacementModifier {
    public static final Codec<EverySurfaceBlockPlacement> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceKey.codec(Registries.BIOME).fieldOf("biome").forGetter(config -> config.biome),
                    Codec.INT.optionalFieldOf("offset", 0).forGetter(config -> config.offset)
            ).apply(instance, EverySurfaceBlockPlacement::new)
    );

    private final ResourceKey<Biome> biome;
    private final int offset;

    public EverySurfaceBlockPlacement(ResourceKey<Biome> biome, int offset) {
        this.biome = biome;
        this.offset = offset;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
        LevelAccessor world = context.getLevel();
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;

        Stream.Builder<BlockPos> positions = Stream.builder();

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos chunkPos = new BlockPos((chunkX + x) * 16, 0, (chunkZ + z) * 16);
                for (int bx = 0; bx < 16; bx++) {
                    for (int bz = 0; bz < 16; bz++) {
                        BlockPos surfacePos = world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, chunkPos.offset(bx, 0, bz));
                        if (world.getBiome(surfacePos).is(this.biome)) {
                            positions.add(surfacePos.above(this.offset));
                        }
                    }
                }
            }
        }

        return positions.build();
    }

    @Override
    public PlacementModifierType<?> type() {
        return ModPlacementModifierTypes.EVERY_SURFACE_BLOCK.get();
    }
}
