package metadev.digital.metacustomitemslib.compatibility.addons;

import metadev.digital.metacustomitemslib.Core;
import metadev.digital.metacustomitemslib.compatibility.Feature;
import metadev.digital.metacustomitemslib.compatibility.FeatureList;
import metadev.digital.metacustomitemslib.compatibility.IFeatureHolder;
import metadev.digital.metacustomitemslib.compatibility.enums.SupportedPluginEntities;
import metadev.digital.metacustomitemslib.compatibility.exceptions.FeatureNotFoundException;
import metadev.digital.metacustomitemslib.messages.MessageHelper;
import org.bukkit.Bukkit;

public class ActionBarHelper implements IFeatureHolder {


    private static FeatureList features;

    public ActionBarHelper() {
        registerFeatures();
    }

    // ****** IFeatureHolder ******
    @Override
    public void registerFeatures() {
        features = new FeatureList(Core.getInstance().getDescription().getVersion());

        // CMI Support
        boolean cmiEnabled = Core.getCompatibilityManager().isCompatibilityLoaded(Bukkit.getPluginManager().getPlugin(SupportedPluginEntities.CMILib.getName()));
        features.addFeature(SupportedPluginEntities.CMILib.getName(), cmiEnabled);

        // TitleManager Support
        boolean tmEnabled = Core.getCompatibilityManager().isCompatibilityLoaded(Bukkit.getPluginManager().getPlugin(SupportedPluginEntities.TitleManager.getName()));
        features.addFeature(SupportedPluginEntities.TitleManager.getName(), tmEnabled);

        // Base Action Bar Support
        boolean enabled = cmiEnabled || tmEnabled;
        features.addFeature("base",enabled);
    }

    @Override
    public boolean isFeatureEnabled(String name) {
        boolean featureEnabled = false;
        try {
            featureEnabled = features.isFeatureEnabled(name);
        } catch (FeatureNotFoundException e) {
            MessageHelper.debug("Triggered a FeatureNotFoundException when trying to return enable flag of the feature " + name + " in the ActionBarHelper." );
        }

        return featureEnabled;
    }

    @Override
    public boolean isFeatureSupported(String name) {
        boolean featureSupported = false;
        try {
            featureSupported = features.isFeatureSupported(name);
        } catch (FeatureNotFoundException e) {
            MessageHelper.debug("Triggered a FeatureNotFoundException when trying to return supported flag of the feature " + name + " in the ActionBarHelper." );
        }

        return featureSupported;
    }

    @Override
    public boolean isFeatureActive(String name) {
        boolean featureActive = false;
        try {
            featureActive = features.isFeatureActive(name);
        } catch (FeatureNotFoundException e) {
            MessageHelper.debug("Triggered a FeatureNotFoundException when trying to return active flag of the feature " + name + " in the ActionBarHelper." );
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
            MessageHelper.debug("Triggered a FeatureNotFoundException when trying to return the feature " + name + "  in the ActionBarHelper." );
        }
        return null;
    }

    // ****** ActionBarHelper Specific ******

    public boolean isActionBarActive() {
        return isFeatureActive("base");
    }

    public boolean isCMILibActive() {
        return isFeatureActive(SupportedPluginEntities.CMILib.getName());
    }

    public boolean isTitleManagerActive() {
        return isFeatureActive(SupportedPluginEntities.TitleManager.getName());
    }
}
