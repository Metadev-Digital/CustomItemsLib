package metadev.digital.metacustomitemslib.config;

import metadev.digital.metacustomitemslib.Core;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Migrator {
    public static void moveLegacyConfiguration(File source, File target) {
        if (source.exists()) {
            Bukkit.getServer().getConsoleSender().sendMessage(Core.PREFIX_WARNING + "Found legacy CustomItemsLib configuration files. Attempting migration....");
            try {
                Files.move(source.toPath(), target.toPath(), REPLACE_EXISTING);
                Bukkit.getServer().getConsoleSender().sendMessage(Core.PREFIX_WARNING + "Legacy CustomItemsLib configuration successfully migrated.");
                Bukkit.getServer().getConsoleSender().sendMessage(Core.PREFIX_WARNING + "================================================================");
                Bukkit.getServer().getConsoleSender().sendMessage(Core.PREFIX_WARNING + "==== Config now located in the directory MetaCustomItemsLib ====");
                Bukkit.getServer().getConsoleSender().sendMessage(Core.PREFIX_WARNING + "================================================================");
            }
            catch (SecurityException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(Core.PREFIX_ERROR + "Failed to migrate legacy CustomItemsLib configuration files due to a security restriction on your system. Config must be manually updated.");
                throw new MigratorException();
            }
            catch (NullPointerException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(Core.PREFIX_ERROR + "Failed to migrate legacy CustomItemsLib configuration files due to NPE. Check your settings manually, as you may now be unexpectedly running a fresh install.");
                throw new MigratorException();
            }
            catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(Core.PREFIX_ERROR + "Failed to migrate legacy CustomItemsLib configuration files. Check your settings manually, as you may now be unexpectedly running a fresh install.");
                throw new MigratorException();
            }
        }
        else {
            throw new MigratorException();
        }
    }
}


