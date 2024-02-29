package net.opsucht.permissionapi;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Permission {

    private static @Nullable PermissionProvider instance = null;

    @ApiStatus.Internal
    private Permission() {
        throw new UnsupportedOperationException();
    }

    public static @NotNull PermissionProvider get() {
        if (Permission.instance == null) {
            throw new IllegalStateException("Permission has not been initialized");
        }

        return Permission.instance;
    }

    public static boolean isInitialized() {
        return Permission.instance != null;
    }

    public static void set(PermissionProvider instance) {
        Permission.instance = instance;
    }
}