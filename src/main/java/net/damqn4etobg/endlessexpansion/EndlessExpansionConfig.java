package net.damqn4etobg.endlessexpansion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EndlessExpansionConfig {
    public static final String MOD_VERSION = "1.20.1-1.5";
    private boolean customMainMenu;
    private String backgroundName = "Titanic Forest"; // Default value when config is made!!!
    private String modSounds = "ON";

    public boolean isCustomMainMenu() {
        return customMainMenu;
    }

    public String getBackgroundName() {
        return backgroundName;
    }

    public String getModSounds() {
        return modSounds;
    }

    public void setCustomMainMenu(boolean customMainMenu) {
        this.customMainMenu = customMainMenu;
        saveConfig();
    }

    public void setBackgroundName(String newBackgroundName) {
        this.backgroundName = newBackgroundName;
        saveConfig();
    }

    public void setModSounds(String newModSounds) {
        this.modSounds = newModSounds;
        saveConfig();
    }

    public void saveConfig() {
        try {
            // Create a Gson instance for serialization
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Convert the configuration to JSON
            String json = gson.toJson(this);

            // Define the file path for the configuration file (you can customize this)
            Path configPath = Paths.get("config/endless_expansion_config.json");

            // Write the JSON to the configuration file
            Files.write(configPath, json.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static EndlessExpansionConfig loadConfig() {
        try {
            // Define the file path for the configuration file
            Path configPath = Paths.get("config/endless_expansion_config.json");

            // Check if the configuration file exists
            if (Files.exists(configPath)) {
                // Read the JSON from the configuration file
                String json = new String(Files.readAllBytes(configPath));

                // Create a Gson instance for deserialization
                Gson gson = new Gson();

                // Deserialize the JSON into an EndlessExpansionConfig object
                return gson.fromJson(json, EndlessExpansionConfig.class);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // If the file doesn't exist or there was an error, return a new configuration
        return new EndlessExpansionConfig();
    }
}
