package net.opsucht.permissionapi;

import net.opsucht.permissionapi.commands.TestCommand;
import net.opsucht.permissionapi.provider.GMPermissionProviderImpl;
import net.opsucht.permissionapi.provider.LPPermissionProviderImpl;
import net.opsucht.permissionapi.provider.PEXPermissionProviderImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PermissionAPIPlugin extends JavaPlugin {

    private static final Logger LOGGER = Bukkit.getLogger();
    private final Map<Class<? extends PermissionProvider>, String> providers = new HashMap<>();

    @Override
    public void onEnable() {
        setup();
    }

    private void setup() {
        registerProviders();
        setupPermissionPlugin();
    }

    /**
     * Registers all permission providers
     */
    private void registerProviders() {
        registerProvider(GMPermissionProviderImpl.class, "GroupManager");
        registerProvider(PEXPermissionProviderImpl.class, "PermissionsEx");
        registerProvider(LPPermissionProviderImpl.class, "LuckPerms");
    }

    /**
     * Register a new permission provider
     *
     * @param clazz      class of the permission provider
     * @param pluginName of the permission plugin for the provider
     */
    private void registerProvider(Class<? extends PermissionProvider> clazz, String pluginName) {
        providers.put(clazz, pluginName);
    }

    /**
     * Checks the existence of all plugins
     * in providers and if one plugin is present
     * sets it as permission provider
     */
    private void setupPermissionPlugin() {
        PluginManager pm = getServer().getPluginManager();

        for (var entry : providers.entrySet()) {
            String name = entry.getValue();

            if (!pluginIsOnServer(pm, name)) {
                continue;
            }

            try {
                LOGGER.info(String.format("Permission provider was set to: '%s'!", name));
                Permission.set(entry.getKey().getDeclaredConstructor().newInstance());
                return;
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                LOGGER.warning(e.getMessage());
            }
        }

        LOGGER.warning(String.format("No compatible permission provider for 'permissionapi' found. " + "Please install one of the following plugins on your server: %s! " + "Disabling Plugin", providers.values().parallelStream().map(s -> "'" + s + "'").collect(Collectors.joining(", "))));
        pm.disablePlugin(this);
    }

    /**
     * Checks if a plugin is on the server by name
     *
     * @param pm         server's {@link PluginManager}
     * @param pluginName to check existence on server
     * @return if the plugin is on the server
     */
    private boolean pluginIsOnServer(PluginManager pm, String pluginName) {
        return pm.getPlugin(pluginName) != null;
    }


}
