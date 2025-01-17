package net.damqn4etobg.endlessexpansion.worldgen;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ARBOR_KEY = registerKey("arbor");
    public static final ResourceKey<ConfiguredFeature<?, ?>>  TITANUM_GRASS_KEY = registerKey("titanum_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>>  OVERWORLD_LUMINITE_ORE_KEY = registerKey("overworld_luminite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>>  MYSTICAL_EVERBLUE_ORCHID_KEY = registerKey("everblue_orchid");
    public static final ResourceKey<ConfiguredFeature<?, ?>>  WORLDBEYOND_COBALT_ORE_KEY = registerKey("worldbeyond_cobalt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>>  MUD_MOSS_KEY = registerKey("mud_moss");
    public static final ResourceKey<ConfiguredFeature<?, ?>>  BLACKSTONE_COLUMNS_KEY = registerKey("blackstone_columns");
    public static final ResourceKey<ConfiguredFeature<?, ?>>  PYRONIUM_KEY = registerKey("pyronium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GIANT_RED_MUSHROOM_KEY = registerKey("giant_red_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GIANT_BROWN_MUSHROOM_KEY = registerKey("giant_brown_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>>  SMALL_RED_MUSHROOM = registerKey("small_red_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>>  SMALL_BROWN_MUSHROOM = registerKey("small_brown_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>>  WORLDBEYOND_BLACK_OPAL_ORE_KEY = registerKey("worldbeyond_black_opal_ore");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest deepslateReplacables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest stoneReplacables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest basaltReplacables = new BlockMatchTest(Blocks.BASALT);

        List<OreConfiguration.TargetBlockState> overworldLuminiteOres = List.of(OreConfiguration.target(deepslateReplacables,
                ModBlocks.DEEPSLATE_LUMINITE_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> worldBeyondCobaltOres = List.of(OreConfiguration.target(stoneReplacables,
                ModBlocks.COBALT_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplacables, ModBlocks.DEEPSLATE_COBALT_ORE.get().defaultBlockState()));

        register(context, ARBOR_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.ARBOR_LOG.get()),
                new StraightTrunkPlacer(5, 6, 3),
                BlockStateProvider.simple(ModBlocks.ARBOR_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 4),
                new TwoLayersFeatureSize(1, 0, 2)).build());

        List<OreConfiguration.TargetBlockState> worldBeyondBlackOpalOres = List.of(OreConfiguration.target(deepslateReplacables,
                ModBlocks.DEEPSLATE_BLACK_OPAL_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_LUMINITE_ORE_KEY, Feature.ORE, new OreConfiguration(overworldLuminiteOres, 2));
        register(context, WORLDBEYOND_COBALT_ORE_KEY, Feature.ORE, new OreConfiguration(worldBeyondCobaltOres, 1));
        register(context, WORLDBEYOND_BLACK_OPAL_ORE_KEY, Feature.ORE, new OreConfiguration(worldBeyondBlackOpalOres, 1));
        register(context, PYRONIUM_KEY, Feature.ORE, new OreConfiguration(basaltReplacables,
                ModBlocks.PYRONIUM_ORE.get().defaultBlockState(), 1));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(EndlessExpansion.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}