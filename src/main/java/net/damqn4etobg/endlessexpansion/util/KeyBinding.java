package net.damqn4etobg.endlessexpansion.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_ENDEXP = "key.category.endlessexpansion.name";
    public static final String KEY_DASH = "key.endlessexpansion.dash";

    public static final KeyMapping DASHING_KEY = new KeyMapping(KEY_DASH, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, KEY_CATEGORY_ENDEXP);
}
