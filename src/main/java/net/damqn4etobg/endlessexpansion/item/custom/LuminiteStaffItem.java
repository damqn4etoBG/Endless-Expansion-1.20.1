package net.damqn4etobg.endlessexpansion.item.custom;

import net.damqn4etobg.endlessexpansion.block.custom.WorldBeyondPortalBlock;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LuminiteStaffItem extends Item {
    public LuminiteStaffItem() {
        super(new Item.Properties().durability(32));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player entity = context.getPlayer();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        ItemStack itemstack = context.getItemInHand();
        Level world = context.getLevel();
        if (!entity.mayUseItemAt(pos, context.getClickedFace(), itemstack)) {
            return InteractionResult.FAIL;
        } else {
            boolean success = false;
            if (world.isEmptyBlock(pos)) {
                WorldBeyondPortalBlock.portalSpawn(world, pos);
                itemstack.hurtAndBreak(1, entity, c -> c.broadcastBreakEvent(context.getHand()));
                success = true;
            }
            return success ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.endlessexpansion.luminite_staff"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.endlessexpansion.holdshift"));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
