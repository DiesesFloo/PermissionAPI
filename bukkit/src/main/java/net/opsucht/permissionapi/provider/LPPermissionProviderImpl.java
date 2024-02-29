package net.opsucht.permissionapi.provider;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.query.QueryOptions;
import net.opsucht.permissionapi.provider.util.AbstractPermissionProviderImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class LPPermissionProviderImpl extends AbstractPermissionProviderImpl {

    private LuckPerms api;

    public LPPermissionProviderImpl() {

        try {
            api = LuckPermsProvider.get();
        } catch (Exception ignored) {}
    }

    @Override
    public @NotNull String getProviderName() {
        return "LuckPerms";
    }

    @Override
    public boolean hasPermission(@NotNull UUID uuid, @NotNull String permission) {
        return api.getUserManager().getUser(uuid)
                .getCachedData()
                .getPermissionData()
                .checkPermission(permission).asBoolean();
    }

    @Override
    public void addPermission(@NotNull UUID uuid, @NotNull String permission) {
        final User user = api.getUserManager().getUser(uuid);
        user.data().add(PermissionNode.builder().permission(permission).build());
        api.getUserManager().saveUser(user).join();
    }

    @Override
    public void removePermission(@NotNull UUID uuid, @NotNull String permission) {
        final User user = api.getUserManager().getUser(uuid);
        PermissionNode node = user.getNodes().stream()
                .filter(NodeType.PERMISSION::matches)
                .map(NodeType.PERMISSION::cast)
                .filter(permissionNode -> permissionNode.getPermission().equals(permission))
                .findFirst().orElseThrow(NullPointerException::new);
        user.data().remove(node);
        api.getUserManager().saveUser(user).join();
    }

    @Override
    public @NotNull Set<String> getUserGroups(@NotNull UUID uuid) {
        return api.getUserManager().getUser(uuid)
                .getInheritedGroups(QueryOptions.defaultContextualOptions())
                .stream()
                .map(Group::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public @NotNull Set<String> getAllGroups() {
        return api.getGroupManager()
                .getLoadedGroups()
                .stream()
                .map(Group::getName)
                .collect(Collectors.toSet());
    }
}
