package metadev.digital.metacustomitemslib.compatibility;

import metadev.digital.metacustomitemslib.compatibility.exceptions.SpinupShutdownException;
import metadev.digital.metacustomitemslib.messages.MessageHelper;
import metadev.digital.metacustomitemslib.messages.constants.Prefixes;
import org.bukkit.plugin.Plugin;

public interface ICompat {
    void start() throws SpinupShutdownException;
    void shutdown() throws SpinupShutdownException;
    boolean isEnabled();
    boolean isSupported();
    boolean isActive();
    boolean isLoaded();
    String getPluginName();
    String getPluginVersion();
    Plugin getPluginInstance();
}

