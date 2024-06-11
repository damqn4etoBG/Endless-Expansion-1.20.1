package net.damqn4etobg.endlessexpansion.worldgen.biome.surface;

import net.damqn4etobg.endlessexpansion.block.ModBlocks;
import net.damqn4etobg.endlessexpansion.worldgen.biome.ModBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import static net.minecraft.world.level.levelgen.SurfaceRules.abovePreliminarySurface;
import static net.minecraft.world.level.levelgen.SurfaceRules.stoneDepthCheck;

public class ModSurfaceRules {
    public static final SurfaceRules.ConditionSource NEW_DEFAULT_BLOCK = stoneDepthCheck(0, true, 1000, CaveSurface.FLOOR);
    private static final SurfaceRules.RuleSource TITANUM_GRASS = makeStateRule(ModBlocks.TITANUM_GRASS_BLOCK.get());
    private static final SurfaceRules.RuleSource TITANUM_SOIL = makeStateRule(ModBlocks.TITANUM_SOIL.get());
    private static final SurfaceRules.RuleSource SNOW = makeStateRule(Blocks.SNOW_BLOCK);
    private static final SurfaceRules.RuleSource PACKED_SNOW = makeStateRule(ModBlocks.PACKED_SNOW_BLOCK.get());
    private static final SurfaceRules.RuleSource MUD = makeStateRule(Blocks.MUD);
    private static final SurfaceRules.RuleSource SAND = makeStateRule(Blocks.SAND);
    private static final SurfaceRules.RuleSource SANDSTONE = makeStateRule(Blocks.SANDSTONE);
    private static final SurfaceRules.RuleSource BLACKSTONE = makeStateRule(Blocks.BLACKSTONE);
    private static final SurfaceRules.RuleSource GRASS = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource titanumSurface =
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), TITANUM_GRASS), SurfaceRules.ifTrue(abovePreliminarySurface(), TITANUM_SOIL));
        SurfaceRules.RuleSource snowSurface =
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), SNOW), SurfaceRules.ifTrue(abovePreliminarySurface(), PACKED_SNOW));
        SurfaceRules.RuleSource mudSurface =
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), MUD), SurfaceRules.ifTrue(abovePreliminarySurface(), MUD));
        SurfaceRules.RuleSource sandSurface =
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), SAND), SurfaceRules.ifTrue(abovePreliminarySurface(), SANDSTONE));
        SurfaceRules.RuleSource grassSurface =
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), GRASS), SurfaceRules.ifTrue(abovePreliminarySurface(), DIRT));
        SurfaceRules.RuleSource blackStoneSurface =
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), BLACKSTONE), SurfaceRules.ifTrue(abovePreliminarySurface(), DIRT));

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.TITANIC_FOREST), titanumSurface),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.FROZEN_WASTES), snowSurface),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.SUNKEN_WASTES), mudSurface),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.SCORCHED_WASTES), sandSurface),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.VOLCANIC_WASTES), blackStoneSurface),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ZERZURA), grassSurface)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
