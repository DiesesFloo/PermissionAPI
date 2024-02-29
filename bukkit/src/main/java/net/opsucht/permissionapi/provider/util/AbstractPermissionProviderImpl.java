package net.opsucht.permissionapi.provider.util;

import net.opsucht.permissionapi.PermissionProvider;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public abstract class AbstractPermissionProviderImpl implements PermissionProvider {
    protected static final Logger LOGGER = Bukkit.getLogger();

    @Override
    public boolean has(@NotNull UUID uuid, @NotNull String permission) {
        return submitCallable(() -> hasPermission(uuid, permission))
                .orElse(false);
    }

    protected abstract boolean hasPermission(@NotNull UUID uuid, @NotNull String permission);

    @Override
    public void add(@NotNull UUID uuid, @NotNull String permission) {
        executeRunnable(() -> addPermission(uuid, permission));
    }

    protected abstract void addPermission(@NotNull UUID uuid, @NotNull String permission);

    @Override
    public void remove(@NotNull UUID uuid, @NotNull String permission) {
        executeRunnable(() -> removePermission(uuid, permission));
    }

    protected abstract void removePermission(@NotNull UUID uuid, @NotNull String permission);

    @Override
    public @NotNull Set<String> getGroups(@NotNull UUID uuid) {
        return submitCallable(() -> getUserGroups(uuid))
                .orElse(Collections.emptySet());
    }

    protected abstract Set<String> getUserGroups(@NotNull UUID uuid);

    @Override
    public @NotNull Set<String> getGroups() {
        return submitCallable(this::getAllGroups)
                .orElse(Collections.emptySet());
    }

    protected abstract Set<String> getAllGroups();

    /**
     * Submits a callable in a new executor and returns the
     * callable result as an optional
     *
     * @param c   the callable to submit
     * @param <T> the callable return type
     * @return the result of the callable
     */
    private <T> Optional<T> submitCallable(Callable<T> c) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            T result = executor.submit(c).get();
            executor.shutdown();
            return Optional.of(result);
        } catch (InterruptedException | ExecutionException e) {
            executor.execute(() -> LOGGER.warning(e.getMessage()));
            executor.shutdown();
            return Optional.empty();
        }
    }

    /**
     * Executes a runnable in a new executor
     *
     * @param runnable to execute
     */
    private void executeRunnable(Runnable runnable) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(runnable);
        executor.shutdown();
    }

}
