package metadev.digital.metacustomitemslib.compatibility;

import metadev.digital.metacustomitemslib.compatibility.exceptions.SpinupShutdownException;
import metadev.digital.metacustomitemslib.messages.MessageHelper;
import org.bukkit.plugin.Plugin;

public interface ICompat {
    Plugin compatPlugin = null;

    void start() throws SpinupShutdownException;
    void shutdown() throws SpinupShutdownException;
    boolean isEnabled();
    boolean isSupported();
    boolean isActive();
    Plugin getPluginInstance();

    default void unsupportedMessage(Feature unsupportedFeature) {
        if(compatPlugin != null){
            String intro = unsupportedFeature.getName().equals("base") ?
                    "Your current version of " + compatPlugin.getName() + " ( " + compatPlugin.getDescription().getVersion() + " ) "
                    : "The requested feature ( " + unsupportedFeature.getName() + " ) ";

            MessageHelper.error(intro + " is not supported by your current versions of MetaCustomItemsLib and Minecraft. " +
                    "This feature is supported in version(s): " + unsupportedFeature.getConstraintsExplanationText());
        }
    }

    default void disabledMessage(Feature disabledFeature, String helpText) {
        if(compatPlugin != null){
            String intro = disabledFeature.getName().equals("base") ?
                    "Compatibility for " + compatPlugin.getName() + " "
                    : "Compatibility for the feature " + disabledFeature.getName() + " ";

            MessageHelper.error(intro + " is currently disabled." + (!helpText.isEmpty() ? " To enable: " + helpText : ""));
        }
    }
}

