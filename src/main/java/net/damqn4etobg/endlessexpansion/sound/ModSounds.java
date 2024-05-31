package net.damqn4etobg.endlessexpansion.sound;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, EndlessExpansion.MODID);
    public static final RegistryObject<SoundEvent> ARBOR_WOOD_BREAK = registerSoundEvent("arbor_wood.break");
    public static final RegistryObject<SoundEvent> ARBOR_WOOD_HIT = registerSoundEvent("arbor_wood.hit");
    public static final RegistryObject<SoundEvent> ARBOR_WOOD_PLACE = registerSoundEvent("arbor_wood.place");
    public static final ForgeSoundType ARBOR_WOOD_SOUNDS = new ForgeSoundType(1f, 0.85f,
            () -> SoundEvents.WOOD_BREAK, () -> SoundEvents.WOOD_STEP, () -> SoundEvents.WOOD_PLACE, () -> SoundEvents.WOOD_HIT, () -> SoundEvents.WOOD_FALL);
    public static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(EndlessExpansion.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }
    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}