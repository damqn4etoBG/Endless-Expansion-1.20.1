package net.damqn4etobg.endlessexpansion.networking.packet;

import net.damqn4etobg.endlessexpansion.capability.dash.PlayerDashProvider;
import net.damqn4etobg.endlessexpansion.effect.ModMobEffects;
import net.damqn4etobg.endlessexpansion.particle.ModParticles;
import net.damqn4etobg.endlessexpansion.sound.ModSoundOptions;
import net.damqn4etobg.endlessexpansion.sound.ModSounds;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DashC2SPacket {

    public DashC2SPacket() {

    }

    public DashC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender(); // Server-side player

            if (player != null && player.hasEffect(ModMobEffects.SHADOW_STATE.get())) {
                player.getCapability(PlayerDashProvider.PLAYER_DASH).ifPresent(dash -> {
                    if (dash.canDash()) {
                        Level world = player.level();
                        RandomSource random = RandomSource.create();
                        if (!world.isClientSide() && !ModSoundOptions.OFF()) {
                            world.playSound(null, player.blockPosition(), ModSounds.DASH.get(), SoundSource.PLAYERS, 0.8f + (random.nextFloat() * 0.2f), 1f);
                        }
                    }
                });
            }
        });
        return true;
    }
}
