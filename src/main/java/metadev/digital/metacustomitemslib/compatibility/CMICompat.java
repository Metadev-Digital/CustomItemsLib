package metadev.digital.metacustomitemslib.compatibility;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Holograms.HologramManager;
import net.Zrips.CMILib.ActionBar.CMIActionBar;
import net.Zrips.CMILib.BossBar.BossBarInfo;
import net.Zrips.CMILib.CMILib;
import metadev.digital.metacustomitemslib.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CMICompat {

	private static Plugin mPlugin, mPlugin2;
	private static boolean supported = false;

	// https://www.spigotmc.org/resources/cmi-ranks-kits-portals-essentials-mysql-sqlite-bungeecord.3742/
	// https://www.spigotmc.org/resources/cmilib.87610/

	public CMICompat() {
		if (!isEnabledInConfig()) {
			Bukkit.getConsoleSender().sendMessage(Core.PREFIX + "Compatibility with CMI is disabled in config.yml");
		} else {
			mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.CMI.getName());

			if (mPlugin != null && mPlugin.getDescription().getVersion().compareTo("9.0") >= 0) {
				Bukkit.getConsoleSender().sendMessage(Core.PREFIX + "Enabling compatibility with CMI ("
						+ mPlugin.getDescription().getVersion() + ").");

				mPlugin2 = Bukkit.getPluginManager().getPlugin(CompatPlugin.CMILib.getName());
				if (mPlugin2 != null && mPlugin2.getDescription().getVersion().compareTo("1.0") >= 0) {
					Bukkit.getConsoleSender().sendMessage(Core.PREFIX + "Enabling compatibility with CMILib ("
							+ mPlugin2.getDescription().getVersion() + ").");
					supported = true;
				} else
					Bukkit.getConsoleSender().sendMessage(Core.PREFIX_ERROR + " Your current version of CMILib is not supported by CustomItemsLib.");
			} else
				Bukkit.getConsoleSender().sendMessage(Core.PREFIX_ERROR + "Your current version of CMI is not supported by CustomItemsLib. CustomItemsLib does only support version 9.0 or newer.");

		}
	}

	// **************************************************************************
	// OTHER
	// **************************************************************************

	public static CMI getCMIPlugin() {
		return (CMI) mPlugin;
	}

	public static boolean isSupported() {
		return supported;
	}

	public static boolean isFullyLoaded() { return CMI.getInstance().isFullyLoaded(); }

	public static boolean isEnabledInConfig() {
		return Core.getConfigManager().enableIntegrationCMI;
	}

	public static HologramManager getHologramManager() {
		return getCMIPlugin().getHologramManager();
	}

	public static void sendActionBarMessage(Player player, String text) {
		CMIActionBar.send(player, text);
	}

	public static void sendBossBarMessage(Player player, String text) {
		BossBarInfo bossBar = new BossBarInfo(player, "...");
		bossBar.setSeconds(10);
		bossBar.setTitleOfBar(text);
		bossBar.setKeepForTicks(0);
		CMILib.getInstance().getBossBarManager().addBossBar(player, bossBar);
	}

}
