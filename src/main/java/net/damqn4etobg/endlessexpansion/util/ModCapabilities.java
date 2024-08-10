package net.damqn4etobg.endlessexpansion.util;

import net.damqn4etobg.endlessexpansion.capability.temperature.ITemperature;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapabilities {
    public static final Capability<ITemperature> TEMPERATURE = CapabilityManager.get(new CapabilityToken<>(){});
}
