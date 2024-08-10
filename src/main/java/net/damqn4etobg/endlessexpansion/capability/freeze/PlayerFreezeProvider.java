package net.damqn4etobg.endlessexpansion.capability.freeze;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerFreezeProvider implements ICapabilityProvider, INBTSerializable<CompoundTag>  {
    public static Capability<PlayerFreeze> PLAYER_FREEZE = CapabilityManager.get(new CapabilityToken<>() {
    });

    private PlayerFreeze freeze = null;
    private final LazyOptional<PlayerFreeze> optional = LazyOptional.of(this::createPlayerFreeze);

    private PlayerFreeze createPlayerFreeze() {
        if(this.freeze == null) {
            this.freeze = new PlayerFreeze();
        }

        return this.freeze;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_FREEZE) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerFreeze().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerFreeze().loadNBTData(nbt);
    }
}
