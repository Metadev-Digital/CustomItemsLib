package metadev.digital.metacustomitemslib.compatibility.addons;

import metadev.digital.metacustomitemslib.Core;
import metadev.digital.metacustomitemslib.compatibility.Feature;
import metadev.digital.metacustomitemslib.compatibility.FeatureList;
import metadev.digital.metacustomitemslib.compatibility.ICompat;
import metadev.digital.metacustomitemslib.compatibility.IFeatureHolder;
import metadev.digital.metacustomitemslib.compatibility.enums.BoundIdentifierEnum;
import metadev.digital.metacustomitemslib.compatibility.enums.SupportedPluginEntities;
import metadev.digital.metacustomitemslib.compatibility.enums.VersionSetIdentifierEnum;
import metadev.digital.metacustomitemslib.compatibility.exceptions.FeatureNotFoundException;
import metadev.digital.metacustomitemslib.compatibility.exceptions.SpinupShutdownException;
import metadev.digital.metacustomitemslib.messages.MessageHelper;
import metadev.digital.metacustomitemslib.messages.constants.Prefixes;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class MobHuntingCompat implements ICompat, IFeatureHolder {

	private Plugin compatPlugin;
	private static boolean enabled = false, supported = false, loaded = false;
	private static String sMin, sMax, pMin = "9.2.0", pMax;
	private static FeatureList features;

	public MobHuntingCompat() {
		compatPlugin = Bukkit.getPluginManager().getPlugin(SupportedPluginEntities.MobHunting.getName());
		Plugin oldMobHunt = Bukkit.getPluginManager().getPlugin(SupportedPluginEntities.OldMobHunting.getName());

		if(compatPlugin == null && oldMobHunt != null){
			MessageHelper.error("You are running a non-Meta version of MobHunting or your current version of MetaMobHunting" +
					" is not supported by" + Prefixes.PLUGIN + ". Please upgrade to " + pMin + " or newer.");
			Bukkit.getPluginManager().disablePlugin(oldMobHunt);
		}
		else if(compatPlugin != null) {
			try {
				start();
			} catch (SpinupShutdownException e) {
				Bukkit.getPluginManager().disablePlugin(compatPlugin);
			}
		}
	}

	// ****** ICompat ******

	@Override
	public void start() throws SpinupShutdownException {
		detectedMessage();
		registerFeatures();

		if (isActive()) {
			successfullyLoadedMessage();
			loaded = true;
		} else if (enabled && !supported) {
			Feature base = getFeature("base");
			if(base != null) unsupportedMessage(base);
			else pluginError("Plugin is enabled but not supported, and failed to understand the reasoning out of the base " +
					"feature. Likely caused by a corrupt / incorrect construction of the base feature.");
			throw new SpinupShutdownException();
		}
	}

	@Override
	public void shutdown() throws SpinupShutdownException {
		if (isActive() && loaded) {
			successfullyShutdownMessage();
			loaded = false;
		}
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean isSupported() {
		return supported;
	}

	@Override
	public boolean isActive() {
		return enabled && supported;
	}

	@Override
	public boolean isLoaded() {
		return loaded;
	}

	@Override
	public Plugin getPluginInstance() {
		return compatPlugin;
	}

	@Override
	public String getPluginName() {
		return compatPlugin.getName();
	}

	@Override
	public String getPluginVersion() {
		return compatPlugin.getDescription().getVersion();
	}

	// ****** IFeatureHolder ******

	@Override
	public void registerFeatures() {
		features = new FeatureList("");

		// Base plugin
		enabled = Core.getConfigManager().enableIntegrationMobHunting;
		features.addFeature("base", pMin, BoundIdentifierEnum.FLOOR, VersionSetIdentifierEnum.PLUGIN, enabled);
		supported = isFeatureSupported("base");

		// Other features
	}

	@Override
	public boolean isFeatureEnabled(String name) {
		boolean featureEnabled = false;
		try {
			featureEnabled = features.isFeatureEnabled(name);
		} catch (FeatureNotFoundException e) {
            MessageHelper.debug("Triggered a FeatureNotFoundException when trying to return enable flag of the feature " + name + " in the " + compatPlugin.getName() +" compat class." );
        }

        return featureEnabled;
	}

	@Override
	public boolean isFeatureSupported(String name) {
		boolean featureSupported = false;
		try {
			featureSupported = features.isFeatureEnabled(name);
		} catch (FeatureNotFoundException e) {
			MessageHelper.debug("Triggered a FeatureNotFoundException when trying to return supported flag of the feature " + name + " in the " + compatPlugin.getName() +" compat class." );
		}

		return featureSupported;
	}

	@Override
	public boolean isFeatureActive(String name) {
		boolean featureActive = false;
		try {
			featureActive = features.isFeatureActive(name);
		} catch (FeatureNotFoundException e) {
			MessageHelper.debug("Triggered a FeatureNotFoundException when trying to return active flag of the feature " + name + " in the " + compatPlugin.getName() +" compat class." );
		}

		return featureActive;
	}

	@Override
	public Feature getFeature(String name) {
		Feature feature;
		try {
			feature = features.getFeature(name);
			return feature;
		} catch (FeatureNotFoundException e) {
			MessageHelper.debug("Triggered a FeatureNotFoundException when trying to return the feature " + name + " in the " + compatPlugin.getName() +" compat class." );
		}
		return null;
	}

	// ****** Plugin Specific ******
}
