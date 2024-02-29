package net.opsucht.permissionapi.provider;

import net.opsucht.permissionapi.provider.util.AbstractPermissionProviderImpl;
import org.jetbrains.annotations.NotNull;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.*;
import java.util.stream.Collectors;

public class PEXPermissionProviderImpl extends AbstractPermissionProviderImpl {

    @Override
    public @NotNull String getProviderName() {
        return "PermissionsEx";
    }

    @Override
    public boolean hasPermission(@NotNull UUID uuid, @NotNull String permission) {
        return PermissionsEx.getUser(uuid.toString())
                .has(permission);
    }

    @Override
    public void addPermission(@NotNull UUID uuid, @NotNull String permission) {
        PermissionsEx.getUser(uuid.toString())
                .addPermission(permission);
    }

    @Override
    public void removePermission(@NotNull UUID uuid, @NotNull String permission) {
        PermissionsEx.getUser(uuid.toString())
                .removePermission(permission);
    }

    @Override
    public @NotNull Set<String> getUserGroups(@NotNull UUID uuid) {
        return new HashSet<>(
                PermissionsEx.getUser(uuid.toString())
                        .getParentIdentifiers()
        );
    }

    @Override
    public @NotNull Set<String> getAllGroups() {
        return PermissionsEx.getPermissionManager()
                .getGroupList()
                .stream()
                .map(PermissionGroup::getName)
                .collect(Collectors.toSet());
    }


}
