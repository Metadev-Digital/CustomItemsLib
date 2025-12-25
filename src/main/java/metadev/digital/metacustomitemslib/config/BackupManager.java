package metadev.digital.metacustomitemslib.config;

import metadev.digital.metacustomitemslib.messages.MessageHelper;
import metadev.digital.metacustomitemslib.Core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BackupManager {

    private Core plugin;

    public BackupManager(Core plugin) {
        this.plugin = plugin;
    }

    private int findNextBackupIndex(File backupDir, String baseName) {
        int clampedMaxBackups = Math.clamp(Core.getConfigManager().backup_count, 1, 1000);

        for (int i = 1; i <= clampedMaxBackups; i++) {
            File f = new File(backupDir, baseName + ".bak" + i);
            if (!f.exists()) {
                return i;
            }
        }
        return -1;
    }

    private void zipBackups(File backupDir, String baseName) throws IOException {
        File zipFile = new File(backupDir, baseName + "_backup.zip");

        // Ensure only ONE zip exists
        if (zipFile.exists()) {
            zipFile.delete();
        }

        try (ZipOutputStream zos =
                     new ZipOutputStream(new FileOutputStream(zipFile))) {

            File[] backups = backupDir.listFiles(f ->
                    f.getName().startsWith(baseName + ".bak"));

            if (backups == null || backups.length == 0) return;

            for (File backup : backups) {
                zos.putNextEntry(new ZipEntry(backup.getName()));
                Files.copy(backup.toPath(), zos);
                zos.closeEntry();

                backup.delete();
            }
        }
    }


    public void backupConfig(File mFile) {
        if (!mFile.exists()) return;

        File backupDir = new File(plugin.getDataFolder(), "backup");
        backupDir.mkdirs();

        String baseName = mFile.getName();
        int index = findNextBackupIndex(backupDir, baseName);

        try {
            // If max reached → zip and reset
            if (index == -1) {
                zipBackups(backupDir, baseName);
                index = 1;
            }

            File backupFile = new File(backupDir, baseName + ".bak" + index);

            Files.copy(
                    mFile.toPath(),
                    backupFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES
            );

            MessageHelper.notice("Config.yml backed up to " + backupFile.getPath());

        } catch (IOException e) {
            MessageHelper.error("Could not backup config.yml");
            e.printStackTrace();
        }
    }
}