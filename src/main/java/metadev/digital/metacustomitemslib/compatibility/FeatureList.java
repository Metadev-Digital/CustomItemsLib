package metadev.digital.metacustomitemslib.compatibility;

import java.util.HashSet;

public class FeatureList {

    private HashSet<Feature> featureSet;
    private String pluginVersion;

    public FeatureList(String pluginVersion){
        this.featureSet = new HashSet<Feature>();
        this.pluginVersion = pluginVersion;
    }

    public void addFeature(String name, String version, BoundIdentifierEnum boundingIdentifier, VersionSetIdentifierEnum classification, boolean enabled){
        VersionSet server = classification == VersionSetIdentifierEnum.SERVER ? new VersionSet(version, boundingIdentifier) : new VersionSet();
        VersionSet plugin = classification == VersionSetIdentifierEnum.PLUGIN ? new VersionSet(version, boundingIdentifier) : new VersionSet();

        register(name, server, plugin, enabled);
    }

    public void addFeature(String name, String minVer, String maxVer, VersionSetIdentifierEnum classification, boolean enabled){
        VersionSet server = classification == VersionSetIdentifierEnum.SERVER ? new VersionSet(minVer, maxVer) : new VersionSet();
        VersionSet plugin = classification == VersionSetIdentifierEnum.PLUGIN ? new VersionSet(minVer, maxVer) : new VersionSet();

        register(name, server, plugin, enabled);
    }

    public void addFeature(String name, String serverVersion, BoundIdentifierEnum boundingIdentifier, String minPluginVer, String maxPluginVer, boolean enabled){
        VersionSet server = new VersionSet(serverVersion, boundingIdentifier);
        VersionSet plugin = new VersionSet(minPluginVer, maxPluginVer);

        register(name, server, plugin, enabled);
    }

    public void addFeature(String name, String minServerVer, String maxServerVer, String pluginVersion, BoundIdentifierEnum boundingIdentifier, boolean enabled){
        VersionSet server = new VersionSet(minServerVer, maxServerVer);
        VersionSet plugin = new VersionSet(pluginVersion, boundingIdentifier);

        register(name, server, plugin, enabled);
    }

    public void addFeature(String name, String minServerVer, String maxServerVer, String minPluginVer, String maxPluginVer, boolean enabled){
        VersionSet server = new VersionSet(minServerVer, maxServerVer);
        VersionSet plugin = new VersionSet(minPluginVer, maxPluginVer);

        register(name, server, plugin, enabled);
    }

    private void register(String name, VersionSet serverSet, VersionSet pluginSet, boolean enabled) {
        boolean supported = validateSupport(serverSet, pluginSet, this.pluginVersion);
        // TODO: BUILD AND ADD FEATURE TO LIST AFTER CHECKING SUPPORT
    }

    private boolean validateSupport(VersionSet serverRestrictions, VersionSet pluginRestrictions, String currentPluginVersion) {
        // TODO: COMPARE SERVER ACTUAL AND PLUGIN ACTUAL TO RESTRICTIONS
    }

    private boolean isWithinVersionBounds(VersionSet bounds, String version){
        if (!bounds.isSet()) return true; // No version restrictions set

        if (bounds.isDoubleBound()){ // A ceiling and floor were set
            return version.compareTo(bounds.getFloor().getVersion()) >= 0 && version.compareTo(bounds.getCeiling().getVersion()) <=0;
        }

        BoundIdentifierEnum boundIdentifier = bounds.getBoundingIdentifier();
        if(boundIdentifier == null) return false; // bound was set, but failed to identify valid restrictions default false

        return boundIdentifier == BoundIdentifierEnum.FLOOR ?
                version.compareTo(bounds.getFloor().getVersion()) >= 0 :
                version.compareTo(bounds.getCeiling().getVersion()) <=0;
    }
}
