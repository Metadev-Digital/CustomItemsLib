package metadev.digital.metacustomitemslib.compatibility.addons;

import metadev.digital.metacustomitemslib.Core;
import metadev.digital.metacustomitemslib.compatibility.enums.SupportedPluginEntities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class ProtocolLibCompat {

	private static Plugin mPlugin;
	private static boolean supported = false;
	private final String latestSupported = "5.3.0";

	// https://www.spigotmc.org/resources/protocollib.1997/

	public ProtocolLibCompat() {
		if (!isEnabledInConfig()) {
			Bukkit.getConsoleSender()
					.sendMessage(Core.PREFIX + "Compatibility with ProtocolLib is disabled in config.yml");
		} else {
			mPlugin = Bukkit.getPluginManager().getPlugin(SupportedPluginEntities.ProtocolLib.getName());
			if (!(mPlugin.getDescription().getVersion().compareTo(latestSupported) >= 0)) {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(Core.PREFIX + ChatColor.RED + "Your current version of ProtocolLib ("
								+ mPlugin.getDescription().getVersion()
								+ ") is not supported by CustomItemsLib. Please upgrade to " + latestSupported + " or newer.");
			} else {
				Bukkit.getConsoleSender().sendMessage(Core.PREFIX + "Enabling compatibility with ProtocolLib ("
						+ mPlugin.getDescription().getVersion() + ").");
				ProtocolLibHelper.enableProtocolLib();
				supported = true;
			}
		}
	}

	// **************************************************************************
	// OTHER
	// **************************************************************************

	public Plugin getProtocoloLib() {
		return mPlugin;
	}

	public static boolean isSupported() {
		return supported;
	}

	public static boolean isEnabledInConfig() {
		return Core.getConfigManager().enableIntegrationProtocolLib;
	}

}
