package net.damqn4etobg.endlessexpansion.worldgen.feature.placement;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModPlacementModifierTypes {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES =
            DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, EndlessExpansion.MODID);

    public static final RegistryObject<PlacementModifierType<EverySurfaceBlockPlacement>> EVERY_SURFACE_BLOCK = PLACEMENT_MODIFIER_TYPES.register("every_surface_block",
            () -> () -> EverySurfaceBlockPlacement.CODEC);

    public static final RegistryObject<PlacementModifierType<ExtendedCountPlacement>> EXTENDED_COUNT = PLACEMENT_MODIFIER_TYPES.register("extended_count",
            () -> () -> ExtendedCountPlacement.CODEC);

    public static void register(IEventBus eventBus) {
        PLACEMENT_MODIFIER_TYPES.register(eventBus);
    }
}
