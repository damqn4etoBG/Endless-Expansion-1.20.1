package net.damqn4etobg.endlessexpansion.capability.freeze;

import net.minecraft.nbt.CompoundTag;

public class PlayerFreeze {
    private int freeze;
    private final int MIN_FREEZE = 0;
    private final int MAX_FREEZE = 10;

    public int getFreeze() {
        return freeze;
    }

    public void addFreeze(int add) {
        this.freeze = Math.min(freeze + add, MAX_FREEZE);
    }

    public void subFreeze(int sub) {
        this.freeze = Math.max(freeze - sub, MIN_FREEZE);
    }

    public void copyFrom(PlayerFreeze source) {
        this.freeze = source.freeze;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("freeze", freeze);
    }

    public void loadNBTData(CompoundTag nbt) {
        freeze = nbt.getInt("freeze");
    }
}
