package net.damqn4etobg.endlessexpansion.fluid;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.block.ModBlocks;
import net.damqn4etobg.endlessexpansion.item.ModItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, EndlessExpansion.MODID);

    public static final RegistryObject<FlowingFluid> SOURCE_NUCLEAR_WASTE = FLUIDS.register("nuclear_waste_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.NUCLEAR_WASTE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_NUCLEAR_WASTE = FLUIDS.register("flowing_nuclear_waste",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.NUCLEAR_WASTE_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> SOURCE_LUMINITE_ESSENCE = FLUIDS.register("luminite_essence_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.LUMINITE_ESSENCE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_LUMINITE_ESSENCE = FLUIDS.register("flowing_luminite_essence",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.LUMINITE_ESSENCE_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties NUCLEAR_WASTE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.NUCLEAR_WASTE_FLUID_TYPE, SOURCE_NUCLEAR_WASTE, FLOWING_NUCLEAR_WASTE)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(ModBlocks.NUCLEAR_WASTE_BLOCK)
            .bucket(ModItems.NUCLEAR_WASTE_BUCKET);

    public static final ForgeFlowingFluid.Properties LUMINITE_ESSENCE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.LUMINITE_ESSENCE_FLUID_TYPE, SOURCE_LUMINITE_ESSENCE, FLOWING_LUMINITE_ESSENCE)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(ModBlocks.LUMINITE_ESSENCE_BLOCK)
            .bucket(ModItems.LUMINITE_ESSENCE_BUCKET);


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
