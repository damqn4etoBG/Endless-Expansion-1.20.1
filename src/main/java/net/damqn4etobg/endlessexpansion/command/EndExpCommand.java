package net.damqn4etobg.endlessexpansion.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.damqn4etobg.endlessexpansion.capability.freeze.PlayerFreezeProvider;
import net.damqn4etobg.endlessexpansion.dimension.ModDimensions;
import net.damqn4etobg.endlessexpansion.dimension.portal.WorldBeyondCommandTeleporter;
import net.damqn4etobg.endlessexpansion.networking.ModMessages;
import net.damqn4etobg.endlessexpansion.networking.packet.FreezeDataSyncS2CPacket;
import net.damqn4etobg.endlessexpansion.tag.ModTags;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.Collection;

public class EndExpCommand {
    public EndExpCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("endexp")
                .requires((commandSourceStack -> commandSourceStack.hasPermission(2)))
                .then(Commands.literal("tpWorldBeyond")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .executes(this::executeTpWB))

                )
                .then(Commands.literal("freeze")
                        .then(Commands.literal("add")
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0, 10))
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .executes(this::executeAddFreeze))))

                        .then(Commands.literal("subtract")
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0, 10))
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .executes(this::executeSubFreeze)))))
        );
    }

    private int executeTpWB(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Collection<? extends ServerPlayer> targets = EntityArgument.getPlayers(ctx, "targets");

        for (ServerPlayer player : targets) {
            BlockPos pos = player.getOnPos();
            if (player.level() instanceof ServerLevel serverlevel) {
                MinecraftServer minecraftserver = serverlevel.getServer();
                ResourceKey<Level> resourcekey = player.level().dimension() == ModDimensions.WORLD_BEYOND_LEVEL_KEY ?
                        Level.OVERWORLD : ModDimensions.WORLD_BEYOND_LEVEL_KEY;
                ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
                if (portalDimension != null && !player.isPassenger()) {
                    if (resourcekey == ModDimensions.WORLD_BEYOND_LEVEL_KEY) {
                        player.changeDimension(portalDimension, new WorldBeyondCommandTeleporter(pos, true));
                    } else {
                        player.changeDimension(portalDimension, new WorldBeyondCommandTeleporter(pos, false));
                    }
                }
            }
        }

        if (targets.size() == 1) {
            ctx.getSource().sendSuccess(() -> Component.translatable("commands.endlessexpansion.tpWorldBeyond", targets.iterator().next().getName()), true);
        } else {
            ctx.getSource().sendSuccess(() -> Component.translatable("commands.endlessexpansion.tpWorldBeyond_multiple", targets.size()), true);
        }
        return 1;
    }

    private int executeAddFreeze(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        int amount = IntegerArgumentType.getInteger(ctx, "amount");
        Collection<? extends ServerPlayer> targets = EntityArgument.getPlayers(ctx, "targets");

        for (ServerPlayer player : targets) {
            BlockPos playerPos = player.blockPosition();
            Holder<Biome> holder = player.level().getBiome(playerPos);
            boolean inSpecificBiome = holder.is(ModTags.Biomes.IS_FROZEN_WASTES);

            if (player.level().dimension() == ModDimensions.WORLD_BEYOND_LEVEL_KEY) {
                player.getCapability(PlayerFreezeProvider.PLAYER_FREEZE).ifPresent(freeze -> {
                    if (inSpecificBiome) {
                        freeze.addFreeze(amount);
                    }
                    ModMessages.sendToPlayer(new FreezeDataSyncS2CPacket(freeze.getFreeze()), player);
                });
            } else {
                player.getCapability(PlayerFreezeProvider.PLAYER_FREEZE).ifPresent(freeze -> {
                    ModMessages.sendToPlayer(new FreezeDataSyncS2CPacket(freeze.getFreeze()), player);
                });
            }
        }

        if (targets.size() == 1) {
            ctx.getSource().sendSuccess(() -> Component.translatable("commands.endlessexpansion.freeze.add", amount, targets.iterator().next().getName()), true);
        } else {
            ctx.getSource().sendSuccess(() -> Component.translatable("commands.endlessexpansion.freeze.add_multiple", amount, targets.size()), true);
        }
        return 1;
    }

    private int executeSubFreeze(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        int amount = IntegerArgumentType.getInteger(ctx, "amount");
        Collection<? extends ServerPlayer> targets = EntityArgument.getPlayers(ctx, "targets");

        for (ServerPlayer player : targets) {
            BlockPos playerPos = player.blockPosition();
            Holder<Biome> holder = player.level().getBiome(playerPos);
            boolean inSpecificBiome = holder.is(ModTags.Biomes.IS_FROZEN_WASTES);
            if (player.level().dimension() == ModDimensions.WORLD_BEYOND_LEVEL_KEY) {
                player.getCapability(PlayerFreezeProvider.PLAYER_FREEZE).ifPresent(freeze -> {
                    if (inSpecificBiome) {
                        freeze.subFreeze(amount);
                    }
                    ModMessages.sendToPlayer(new FreezeDataSyncS2CPacket(freeze.getFreeze()), player);
                });
            } else {
                player.getCapability(PlayerFreezeProvider.PLAYER_FREEZE).ifPresent(freeze -> {
                    ModMessages.sendToPlayer(new FreezeDataSyncS2CPacket(freeze.getFreeze()), player);
                });
            }
        }

        if (targets.size() == 1) {
            ctx.getSource().sendSuccess(() -> Component.translatable("commands.endlessexpansion.freeze.subtract", amount, targets.iterator().next().getName()), true);
        } else {
            ctx.getSource().sendSuccess(() -> Component.translatable("commands.endlessexpansion.freeze.subtract_multiple", amount, targets.size()), true);
        }
        return 1;
    }
}