package net.damqn4etobg.endlessexpansion.capability.dash;

import net.minecraft.nbt.CompoundTag;

public class PlayerDash {
    private boolean canDash;
    private int dashTicksElapsed;
    private final int MAX_TICKS;

    public PlayerDash(int maxTicks) {
        this.MAX_TICKS = maxTicks;
        this.dashTicksElapsed = maxTicks;
        this.canDash = true;
    }

    public boolean canDash() {
        return canDash;
    }

    public void setCanDash(boolean canDash) {
        this.canDash = canDash;
    }

    public int getDashTicksElapsed() {
        return dashTicksElapsed;
    }

    public void incrementDashTicks() {
        if (dashTicksElapsed < MAX_TICKS) {
            dashTicksElapsed++;
            checkDashReady();
        }
    }

    public void resetDashTicks() {
        dashTicksElapsed = 0;
        canDash = false;
    }

    private void checkDashReady() {
        if (dashTicksElapsed >= MAX_TICKS) {
            canDash = true;
        }
    }

    public void copyFrom(PlayerDash source) {
        this.canDash = source.canDash;
        this.dashTicksElapsed = source.dashTicksElapsed;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("canDash", canDash);
        nbt.putInt("dashTicksElapsed", dashTicksElapsed);
    }

    public void loadNBTData(CompoundTag nbt) {
        canDash = nbt.getBoolean("canDash");
        dashTicksElapsed = nbt.getInt("dashTicksElapsed");
    }
}