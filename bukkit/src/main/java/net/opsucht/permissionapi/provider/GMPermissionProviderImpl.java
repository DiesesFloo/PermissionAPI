package net.opsucht.permissionapi.provider;

import net.opsucht.permissionapi.provider.util.AbstractPermissionProviderImpl;
import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.data.Group;
import org.anjocaido.groupmanager.data.User;
import org.anjocaido.groupmanager.dataholder.OverloadedWorldHolder;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class GMPermissionProviderImpl extends AbstractPermissionProviderImpl {

    private GroupManager groupManager;

    public GMPermissionProviderImpl() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("GroupManager");

        if (plugin != null) {
            groupManager = (GroupManager) plugin;
        }
    }

    @Override
    public @NotNull String getProviderName() {
        return "GroupManager";
    }

    @Override
    public boolean hasPermission(@NotNull UUID uuid, @NotNull String permission) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getDefaultWorld().getPermissionsHandler();
        if (handler == null) return false;

        return handler.getUserPermissionBoolean(uuid.toString(), permission);
    }

    @Override
    public void addPermission(@NotNull UUID uuid, @NotNull String permission) {
        final User user = groupManager.getWorldsHolder().getDefaultWorld().getUser(uuid.toString());
        user.addPermission(permission);
    }

    @Override
    public void removePermission(@NotNull UUID uuid, @NotNull String permission) {
        final User user = groupManager.getWorldsHolder().getDefaultWorld().getUser(uuid.toString());
        user.removePermission(permission);
    }

    @Override
    public @NotNull Set<String> getUserGroups(@NotNull UUID uuid)  {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getDefaultWorld().getPermissionsHandler();
        if (handler == null) return Collections.emptySet();

        return Set.of(handler.getGroups(uuid.toString()));
    }

    @Override
    public @NotNull Set<String> getAllGroups() {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        return handler.getGroupList().parallelStream()
                .map(Group::getName)
                .collect(Collectors.toSet());
    }
}
