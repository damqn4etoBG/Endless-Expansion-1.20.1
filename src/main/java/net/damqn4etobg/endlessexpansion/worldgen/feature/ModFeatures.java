package net.damqn4etobg.endlessexpansion.worldgen.feature;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.worldgen.feature.configuration.StructureFeatureConfiguration;
import net.damqn4etobg.endlessexpansion.worldgen.feature.configuration.UnderwaterHillsConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, EndlessExpansion.MODID);
    public static final RegistryObject<Feature<?>> STRUCTURE_FEATURE = FEATURES.register("structure_feature",
            () -> new StructureFeature(StructureFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>> BLACKSTONE_COLUMNS = FEATURES.register("blackstone_columns",
            () -> new BlackstoneColumnsFeature(ColumnFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>> VERTICAL_WATER_LINE = FEATURES.register("vertical_water_line",
            () -> new VerticalWaterLineFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>> UNDERWATER_HILLS = FEATURES.register("underwater_hills",
            () -> new UnderwaterHillsFeature(UnderwaterHillsConfiguration.CODEC));
    public static final RegistryObject<Feature<?>> ORE_STONE = FEATURES.register("ore_stone",
            () -> new OreFeature(OreConfiguration.CODEC));


    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
