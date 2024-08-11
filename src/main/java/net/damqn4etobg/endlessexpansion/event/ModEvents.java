package net.damqn4etobg.endlessexpansion.event;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.capability.dash.PlayerDashProvider;
import net.damqn4etobg.endlessexpansion.capability.freeze.PlayerFreezeProvider;
import net.damqn4etobg.endlessexpansion.command.EndExpCommand;
import net.damqn4etobg.endlessexpansion.dimension.ModDimensions;
import net.damqn4etobg.endlessexpansion.effect.ModMobEffects;
import net.damqn4etobg.endlessexpansion.event.client.ClientFreezeData;
import net.damqn4etobg.endlessexpansion.event.client.ModClientEvents;
import net.damqn4etobg.endlessexpansion.networking.ModMessages;
import net.damqn4etobg.endlessexpansion.networking.packet.FreezeC2SPacket;
import net.damqn4etobg.endlessexpansion.networking.packet.FreezeDataSyncS2CPacket;
import net.damqn4etobg.endlessexpansion.sound.ModSounds;
import net.damqn4etobg.endlessexpansion.worldgen.biome.ModBiomes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = EndlessExpansion.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    protected Minecraft minecraft;

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new EndExpCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerFreezeProvider.PLAYER_FREEZE).isPresent()) {
                event.addCapability(new ResourceLocation(EndlessExpansion.MODID, "properties"), new PlayerFreezeProvider());
            }
            if(!event.getObject().getCapability(PlayerDashProvider.PLAYER_DASH).isPresent()) {
                event.addCapability(new ResourceLocation(EndlessExpansion.MODID, "dash"), new PlayerDashProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerFreezeProvider.PLAYER_FREEZE).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerFreezeProvider.PLAYER_FREEZE).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().getCapability(PlayerDashProvider.PLAYER_DASH).ifPresent(oldDash -> {
                event.getEntity().getCapability(PlayerDashProvider.PLAYER_DASH).ifPresent(newDash -> {
                    newDash.copyFrom(oldDash);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerFreezeProvider.PLAYER_FREEZE).ifPresent(freeze -> {
                    ModMessages.sendToPlayer(new FreezeDataSyncS2CPacket(freeze.getFreeze()), player);
                });
            }
        }
    }


    // handle server-sided events
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            Level world = player.level();

            player.getCapability(PlayerFreezeProvider.PLAYER_FREEZE).ifPresent(freeze -> {
                if (freeze.getFreeze() >= 10 && (!player.isCreative() && !player.isSpectator())) {
                    player.addEffect(new MobEffectInstance(ModMobEffects.FREEZING.get(), 100, 0, false, false, true));
                }
            });

            if (player.hasEffect(ModMobEffects.SHADOW_STATE.get())) {
                if (!player.isInvisible()) {
                    player.setInvisible(true);
                }
            }
        }
    }
}
