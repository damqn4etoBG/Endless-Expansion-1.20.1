package net.damqn4etobg.endlessexpansion.fluid;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ModFluidTypes {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");
    public static final ResourceLocation NUCLEAR_WASTE_OVERLAY_RL = new ResourceLocation(EndlessExpansion.MODID, "misc/in_nuclear_waste");
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, EndlessExpansion.MODID);

    public static final RegistryObject<FluidType> NUCLEAR_WASTE_FLUID_TYPE = registerNuclearWaste("nuclear_waste_fluid",
            FluidType.Properties.create().lightLevel(2).density(15).viscosity(5).sound(SoundAction.get("drink"),
                    SoundEvents.HONEY_DRINK));
    public static final RegistryObject<FluidType> LUMINITE_ESSENCE_FLUID_TYPE = registerLuminite("luminite_essence_fluid",
            FluidType.Properties.create().lightLevel(15).density(15).viscosity(5).sound(SoundAction.get("bucket_fill"), SoundEvents.BUCKET_FILL)
                    .sound(SoundAction.get("bucket_empty"), SoundEvents.BUCKET_EMPTY)
                    .sound(SoundAction.get("hit"), SoundEvents.GENERIC_SPLASH)
                    .sound(SoundAction.get("ambient"), SoundEvents.WATER_AMBIENT));

    private static RegistryObject<FluidType> registerNuclearWaste(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, NUCLEAR_WASTE_OVERLAY_RL,
                0xA1F0B00E, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f), properties));
    }

    private static RegistryObject<FluidType> registerLuminite(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL,
                0xE6FFFF00, new Vector3f(250f / 255f, 223f / 255f, 50f / 255f), properties));
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}