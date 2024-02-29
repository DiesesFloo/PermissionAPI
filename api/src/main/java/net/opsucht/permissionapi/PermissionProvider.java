package net.opsucht.permissionapi;

import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;

/**
 * Provides generic api for:
 * - LuckPerms
 * - GroupManager
 * - PermissionsEx
 * <p>
 * Instance can be accessed by {@link Permission#get()},
 * when one of the above plugins is available and {@link Permission#isInitialized()} is true
 */
public interface PermissionProvider {

    /**
     * Get provider name (eg. LuckPerms)
     *
     * @return provider name
     */
    @NotNull String getProviderName();

    /**
     * Check if player by {@link UUID} has permission
     *
     * @param uuid       player uuid
     * @param permission to check
     * @return if player has permission
     * @throws NullPointerException if uuid or permission is null
     */
    boolean has(@NotNull UUID uuid, @NotNull String permission);

    /**
     * Add permission to player by {@link UUID}
     *
     * @param uuid       player uuid
     * @param permission to add
     * @throws NullPointerException if uuid or permission is null
     */
    void add(@NotNull UUID uuid, @NotNull String permission);

    /**
     * Remove permission from player by {@link UUID}
     *
     * @param uuid       player uuid
     * @param permission to remove
     * @throws NullPointerException if uuid or permission is null
     */
    void remove(@NotNull UUID uuid, @NotNull String permission);

    /**
     * Get player groups by {@link UUID}
     *
     * @param uuid player uuid
     * @return player groups
     * @throws NullPointerException if uuid is null
     */
    @NotNull Set<String> getGroups(@NotNull UUID uuid);

    /**
     * Get all available groups
     *
     * @return all groups
     */
    @NotNull Set<String> getGroups();
}