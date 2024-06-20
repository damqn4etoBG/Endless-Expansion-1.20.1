package net.damqn4etobg.endlessexpansion.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.damqn4etobg.endlessexpansion.dimension.ModDimensions;
import net.damqn4etobg.endlessexpansion.dimension.portal.WorldBeyondCommandTeleporter;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class EndExpCommand {

    public EndExpCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("endexp")
                .then(Commands.literal("tpWorldBeyond")
                        .requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                        .executes(this::executeTpWB)
                )
        );
    }
    private int executeTpWB(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        BlockPos pos = player.getOnPos();
        if (player.level() instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = player.level().dimension() == ModDimensions.WORLD_BEYOND_LEVEL_KEY ?
                    Level.OVERWORLD : ModDimensions.WORLD_BEYOND_LEVEL_KEY;
            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if (portalDimension != null && !player.isPassenger()) {
                if(resourcekey == ModDimensions.WORLD_BEYOND_LEVEL_KEY) {
                    player.changeDimension(portalDimension, new WorldBeyondCommandTeleporter(pos, true));
                } else {
                    player.changeDimension(portalDimension, new WorldBeyondCommandTeleporter(pos, false));
                }
            }
        }

        return 1;
    }
}
