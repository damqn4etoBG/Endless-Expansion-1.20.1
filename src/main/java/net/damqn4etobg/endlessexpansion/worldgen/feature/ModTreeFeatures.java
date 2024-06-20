package net.damqn4etobg.endlessexpansion.worldgen.feature;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModTreeFeatures {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, EndlessExpansion.MODID);
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, EndlessExpansion.MODID);
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, EndlessExpansion.MODID);
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, EndlessExpansion.MODID);

//    public static final RegistryObject<TreeDecoratorType<RedMushroomTrunkDecorator>> TRUNK_RED_MUSHROOM
//            = TREE_DECORATORS.register("trunk_red_mushroom", () -> new TreeDecoratorType<>(RedMushroomTrunkDecorator.CODEC));
//
//    public static final RegistryObject<TreeDecoratorType<RedMushroomSporeDecorator>> RED_MUSHROOM_SPORES
//            = TREE_DECORATORS.register("red_mushroom_spores", () -> new TreeDecoratorType<>(RedMushroomSporeDecorator.CODEC));

    private static <P extends PlacementModifier> RegistryObject<PlacementModifierType<P>> registerPlacer(String name, Supplier<PlacementModifierType<P>> factory) {
        return PLACEMENT_MODIFIERS.register(name, factory);
    }

    public static void register(IEventBus eventBus) {
        TREE_DECORATORS.register(eventBus);
    }
}
