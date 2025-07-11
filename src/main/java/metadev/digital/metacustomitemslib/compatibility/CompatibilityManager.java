package metadev.digital.metacustomitemslib.compatibility;

import metadev.digital.metacustomitemslib.Core;
import metadev.digital.metacustomitemslib.compatibility.enums.SupportedPluginEntities;
import metadev.digital.metacustomitemslib.compatibility.exceptions.SpinupShutdownException;
import metadev.digital.metacustomitemslib.messages.constants.Prefixes;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class CompatibilityManager implements Listener {

	private Core plugin;
	private static HashSet<Object> mCompatClasses = new HashSet<Object>();
	private static HashMap<SupportedPluginEntities, Class<?>> mWaitingCompatClasses = new HashMap<SupportedPluginEntities, Class<?>>();

	public CompatibilityManager(Core plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	public void registerPlugin(@SuppressWarnings("rawtypes") Class c, SupportedPluginEntities pluginName) {
		try {
			register(c, pluginName);
		} catch (Exception e) {
			Bukkit.getConsoleSender()
					.sendMessage(Prefixes.PREFIX_ERROR + Prefixes.PLUGIN + " could not register with [" + pluginName
							+ "] please check if [" + pluginName + "] is compatible with the server ["
							+ Bukkit.getServer().getBukkitVersion() + "]");
			if (Core.getConfigManager().debug)
				e.printStackTrace();
		}
	}

	/**
	 * Registers the compatability handler if the plugin specified is loaded
	 * 
	 * @param compatibilityHandler The class that will be created
	 * @param pluginName           The name of the plugin to check
	 */
	private void register(Class<?> compatibilityHandler, SupportedPluginEntities pluginName) {
		if (Bukkit.getPluginManager().isPluginEnabled(pluginName.getName())) {
			try {
				mCompatClasses.add(compatibilityHandler.newInstance());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else
			mWaitingCompatClasses.put(pluginName, compatibilityHandler);
	}

	/**
	 * detect if the compatibility class is loaded.
	 * 
	 * @param class1 - The Compatibility class ex. "WorldGuardCompat.class"
	 * @return true if loaded.
	 */
	public boolean isPluginLoaded(Class<?> class1) {
		Iterator<Object> i = mCompatClasses.iterator();
		while (i.hasNext()) {
			Class<?> c = i.next().getClass();
			if (c.getName().equalsIgnoreCase(class1.getName()))
				return true;
		}
		return false;
	}

	public boolean isCompatibilityLoaded(Plugin enableCheck) {
		for (Object compatClass : mCompatClasses) {
			if(compatClass.getClass().isAssignableFrom(ICompat.class) && compatClass.getClass().getName().equalsIgnoreCase(enableCheck.getClass().getName())){
				return ((ICompat) compatClass).isLoaded();
			}
		}

		return false;
	}

	public void triggerSoftShutdown() {
        for (Object compatClass : mCompatClasses) {
			if(compatClass.getClass().isAssignableFrom(ICompat.class)){
				try{
					if(((ICompat) compatClass).isLoaded()) ((ICompat) compatClass).shutdown();
				} catch (SpinupShutdownException e) {
                    throw new RuntimeException(e);
                }
            }
        }
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPluginEnabled(PluginEnableEvent event) {
		SupportedPluginEntities supportedPlugin = SupportedPluginEntities.getSupportedPlugin(event.getPlugin().getName());
		if (mWaitingCompatClasses.containsKey(supportedPlugin)) {
			registerPlugin(mWaitingCompatClasses.get(supportedPlugin), supportedPlugin);
			mWaitingCompatClasses.remove(supportedPlugin);
		}
	}

}
