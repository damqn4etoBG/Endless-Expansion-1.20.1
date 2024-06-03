package net.damqn4etobg.endlessexpansion.sound;

import net.damqn4etobg.endlessexpansion.EndlessExpansionConfig;

public class ModSoundOptions {
    public static boolean ON() {
        String currentString = EndlessExpansionConfig.loadConfig().getModSounds();
        if (currentString.equals("ON")) {
            return true;
        } else if(currentString.equals("Partial")) {
            return false;
        } else if(currentString.equals("OFF")) {
            return false;
        }
        return false;
    }
    public static boolean Partial() {
        String currentString = EndlessExpansionConfig.loadConfig().getModSounds();
        if (currentString.equals("ON")) {
            return false;
        } else if(currentString.equals("Partial")) {
            return true;
        } else if(currentString.equals("OFF")) {
            return false;
        }
        return false;
    }
    public static boolean OFF() {
        String currentString = EndlessExpansionConfig.loadConfig().getModSounds();
        if (currentString.equals("ON")) {
            return false;
        } else if(currentString.equals("Partial")) {
            return false;
        } else if(currentString.equals("OFF")) {
            return true;
        }
        return false;
    }
}