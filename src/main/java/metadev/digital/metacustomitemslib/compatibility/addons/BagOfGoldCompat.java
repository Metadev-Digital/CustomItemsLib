package metadev.digital.metacustomitemslib.compatibility.addons;

import metadev.digital.metacustomitemslib.compatibility.enums.SupportedPluginEntities;
import metadev.digital.metacustomitemslib.messages.constants.Prefixes;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BagOfGoldCompat {

	private static Plugin mPlugin;
	private static boolean supported = false;
	private final String latestSupported = "4.5.7";

	public BagOfGoldCompat() {
		mPlugin = Bukkit.getPluginManager().getPlugin(SupportedPluginEntities.BagOfGold.getName());

		if (mPlugin != null) {
			if (mPlugin.getDescription().getVersion().compareTo(latestSupported) >= 0) {
				Bukkit.getServer().getConsoleSender().sendMessage(Prefixes.PREFIX
						+ "Enabling compatibility with BagOfGold (" + mPlugin.getDescription().getVersion() + ")");
				supported = true;
			} else {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(Prefixes.PREFIX_ERROR + "Your current version of BagOfGold ("
								+ mPlugin.getDescription().getVersion()
								+ ") is not supported by CustomItemsLib. Please upgrade to " + latestSupported + " or newer.");
				Bukkit.getPluginManager().disablePlugin(mPlugin);
			}
		} else {
			Bukkit.getServer().getConsoleSender()
					.sendMessage(Prefixes.PREFIX + "BagOfGold is not installed on this server");
		}

	}

	public static boolean isSupported() {
		return supported;
	}

	public static Plugin getInstance() {
		return mPlugin;
	}

}
