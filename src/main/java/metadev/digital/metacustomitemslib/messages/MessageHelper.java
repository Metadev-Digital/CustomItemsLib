package metadev.digital.metacustomitemslib.messages;

import metadev.digital.metacustomitemslib.Core;
import metadev.digital.metacustomitemslib.messages.constants.Prefixes;
import org.bukkit.Bukkit;

// TODO: PULL DUPLICATES OUT OF MESSAGES AND REFACTOR

public class MessageHelper {
    /**
     * Show debug information in the Server console log
     *
     * @param message
     * @param args
     */
    public static void debug(String message, Object... args) {
        if (Core.getConfigManager().debug) {
            Bukkit.getServer().getConsoleSender().sendMessage(Prefixes.PREFIX_DEBUG + String.format(message, args));
        }
    }

    /**
     * Show console message
     *
     * @param message
     * @param args
     */
    public static void notice(String message, Object... args) {
        Bukkit.getServer().getConsoleSender().sendMessage(Prefixes.PREFIX + String.format(message, args));
    }

    /**
     * Show console warning
     *
     * @param message
     * @param args
     */
    public static void warning(String message, Object... args) {
        Bukkit.getServer().getConsoleSender().sendMessage(Prefixes.PREFIX_WARNING + String.format(message, args));
    }

    /**
     * Show console error
     *
     * @param message
     * @param args
     */
    public static void error(String message, Object... args) {
        Bukkit.getServer().getConsoleSender().sendMessage(Prefixes.PREFIX_ERROR + String.format(message, args));
    }
}
