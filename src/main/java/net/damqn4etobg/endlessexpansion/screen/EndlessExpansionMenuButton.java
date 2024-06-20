package net.damqn4etobg.endlessexpansion.screen;

import net.damqn4etobg.endlessexpansion.block.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EndlessExpansionMenuButton extends Button {

    // Code from https://github.com/Creators-of-Create/Create
    // Modified to work with this
    public static final ItemStack ICON = ModBlocks.ARBOR_SAPLING.get().asItem().getDefaultInstance();

    public EndlessExpansionMenuButton(int x, int y) {
        super(x, y, 20, 20, Component.empty(), EndlessExpansionMenuButton::click, DEFAULT_NARRATION);
    }

    @Override
    public void renderString(GuiGraphics graphics, Font pFont, int pColor) {
        graphics.renderItem(ICON, getX() + 2, getY() + 2);
    }

    public static void click(Button b) {
        Minecraft.getInstance().setScreen(new EndlessExpansionMainMenuScreen(Minecraft.getInstance().screen));
    }

    public static class SingleMenuRow {
        public final String leftKey, rightKey;

        public SingleMenuRow(String leftKey, String rightKey) {
            this.leftKey = leftKey;
            this.rightKey = rightKey;
        }

        public SingleMenuRow(String centerKey) {
            this(centerKey, centerKey);
        }
    }

    public static class MenuRows {
        public static final MenuRows MAIN_MENU = new MenuRows(Arrays.asList(
                new SingleMenuRow("menu.singleplayer"),
                new SingleMenuRow("menu.multiplayer"),
                new SingleMenuRow("fml.menu.mods", "menu.online"),
                new SingleMenuRow("narrator.button.language", "narrator.button.accessibility")
        ));

        public static final MenuRows INGAME_MENU = new MenuRows(Arrays.asList(
                new SingleMenuRow("menu.returnToGame"),
                new SingleMenuRow("gui.advancements", "gui.stats"),
                new SingleMenuRow("menu.sendFeedback", "menu.reportBugs"),
                new SingleMenuRow("menu.options", "menu.shareToLan"),
                new SingleMenuRow("menu.returnToMenu")
        ));

        protected final List<String> leftButtonKeys, rightButtonKeys;

        public MenuRows(List<SingleMenuRow> variants) {
            leftButtonKeys = variants.stream().map(r -> r.leftKey).collect(Collectors.toList());
            rightButtonKeys = variants.stream().map(r -> r.rightKey).collect(Collectors.toList());
        }
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT)
    public static class OpenConfigButtonHandler {

        @SubscribeEvent
        public static void onGuiInit(ScreenEvent.Init event) {
            Screen gui = event.getScreen();

            MenuRows menu = null;
            int rowIdx = 0, offsetX = 0;
            if (gui instanceof TitleScreen) {
                menu = MenuRows.MAIN_MENU;
                rowIdx = 2;
                offsetX = 4;
            } else if (gui instanceof PauseScreen) {
                menu = MenuRows.INGAME_MENU;
                rowIdx = 3;
                offsetX = 4;
            } else if (gui instanceof ModTitleScreen) {
                menu = MenuRows.MAIN_MENU;
                rowIdx = 2;
                offsetX = 4;
            }

            if (rowIdx != 0 && menu != null) {
                boolean onLeft = offsetX < 0;
                String targetKey = (onLeft ? menu.leftButtonKeys : menu.rightButtonKeys).get(rowIdx - 1);
                String targetText = I18n.get(targetKey);

                int offsetX_ = offsetX;
                MutableObject<GuiEventListener> toAdd = new MutableObject<>(null);
                event.getListenersList()
                        .stream()
                        .filter(w -> w instanceof AbstractWidget)
                        .map(w -> (AbstractWidget) w)
                        .filter(w -> w.getMessage().getString().equals(targetText))
                        .findFirst()
                        .ifPresent(w -> {
                            EndlessExpansionMenuButton button = new EndlessExpansionMenuButton(w.getX() + offsetX_ + (onLeft ? -20 : w.getWidth()), w.getY());
                            button.setTooltip(Tooltip.create(Component.translatable("menu.endlessexpansion.config.name")));
                            toAdd.setValue(button);
                        });

                if (toAdd.getValue() != null)
                    event.addListener(toAdd.getValue());
            }
        }

    }
}
