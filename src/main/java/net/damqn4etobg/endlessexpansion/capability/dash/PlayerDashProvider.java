package net.damqn4etobg.endlessexpansion.capability.dash;

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

public class PlayerDashProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<PlayerDash> PLAYER_DASH = CapabilityManager.get(new CapabilityToken<>() {
    });

    private PlayerDash dash = null;
    private final LazyOptional<PlayerDash> optional = LazyOptional.of(this::createPlayerDash);

    private PlayerDash createPlayerDash() {
        if (this.dash == null) {
            this.dash = new PlayerDash(40);
        }
        return this.dash;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_DASH) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerDash().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerDash().loadNBTData(nbt);
    }
}
