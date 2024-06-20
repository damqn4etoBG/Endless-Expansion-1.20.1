package net.damqn4etobg.endlessexpansion.util.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapabilities {
    public static final Capability<ITemperature> TEMPERATURE = CapabilityManager.get(new CapabilityToken<>(){});
}
