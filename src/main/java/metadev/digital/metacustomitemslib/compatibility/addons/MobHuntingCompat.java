package metadev.digital.metacustomitemslib.compatibility.addons;

import metadev.digital.metacustomitemslib.compatibility.enums.SupportedPluginEntities;
import metadev.digital.metacustomitemslib.messages.constants.Prefixes;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class MobHuntingCompat {

	private Plugin mPlugin;
	private static boolean supported = false;
	private final String latestSupported = "9.2.0";

	public MobHuntingCompat() {
		mPlugin = Bukkit.getPluginManager().getPlugin(SupportedPluginEntities.MobHunting.getName());

		if (mPlugin != null) {
			if (mPlugin.getDescription().getVersion().compareTo(latestSupported) >= 0) {
				Bukkit.getServer().getConsoleSender().sendMessage(Prefixes.PREFIX
						+ "Enabling compatibility with MobHunting (" + mPlugin.getDescription().getVersion() + ")");
				supported = true;
			} else {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(Prefixes.PREFIX_ERROR + "Your current version of MobHunting ("
								+ mPlugin.getDescription().getVersion()
								+ ") is not supported by " + Prefixes.PLUGIN + ". Please upgrade to " + latestSupported + " or newer.");
				Bukkit.getPluginManager().disablePlugin(mPlugin);
			}
		}
		else {
			Plugin oldMobHunt = Bukkit.getPluginManager().getPlugin(SupportedPluginEntities.OldMobHunting.getName());

			if(oldMobHunt != null) {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(Prefixes.PREFIX_ERROR + "You are running a non-Meta version of MobHunting or your current version of MobHunting ("
								+ mPlugin.getDescription().getVersion()
								+ ") is not supported by" + Prefixes.PLUGIN + ". Please upgrade to " + latestSupported + " or newer.");
				Bukkit.getPluginManager().disablePlugin(oldMobHunt);
			}
			else{
				Bukkit.getServer().getConsoleSender()
						.sendMessage(Prefixes.PREFIX + " MobHunting is not installed on this server");
			}
		}
	}

	public boolean isSupported() {
		return supported;
	}

}
