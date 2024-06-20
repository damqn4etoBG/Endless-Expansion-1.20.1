package net.damqn4etobg.endlessexpansion.event;

import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.command.EndExpCommand;
import net.damqn4etobg.endlessexpansion.freeze.PlayerFreezeProvider;
import net.damqn4etobg.endlessexpansion.networking.ModMessages;
import net.damqn4etobg.endlessexpansion.networking.packet.FreezeDataSyncS2CPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
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
}
