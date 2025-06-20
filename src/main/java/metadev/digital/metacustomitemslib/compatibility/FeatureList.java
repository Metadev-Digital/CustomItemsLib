package metadev.digital.metacustomitemslib.compatibility;

import metadev.digital.metacustomitemslib.compatibility.enums.BoundIdentifierEnum;
import metadev.digital.metacustomitemslib.compatibility.enums.VersionSetIdentifierEnum;
import metadev.digital.metacustomitemslib.compatibility.exceptions.FeatureNotFoundException;
import metadev.digital.metacustomitemslib.server.Servers;

import java.util.HashSet;

/***
 * Hashset of Features
 */

public class FeatureList {

    private final HashSet<Feature> featureSet;
    private final String pluginVersion;

    // ****** CONSTRUCTORS ******

    /***
     * Creates a new hashset to contain Feature Objects
     * @param pluginVersion - String value used to compare semantic versions for valid version for plugin -> feature support
     */
    public FeatureList(String pluginVersion){
        this.featureSet = new HashSet<Feature>();
        this.pluginVersion = pluginVersion;
    }

    // ****** MANIPULATORS ******

    /***
     *
     * @param name - String name of the feature being searched for
     * @return - Feature object matching name value provided
     * @throws FeatureNotFoundException - Thrown when feature by given name is not found in the hashset. Handled as an error as these features
     * are manually added, and we should never be searching for a feature that does not exist.
     */
    public Feature getFeature(String name) throws FeatureNotFoundException {
        Feature compareFeature = new Feature(name);

        if(!featureSet.contains(compareFeature)) throw new FeatureNotFoundException();

        for (Feature currentFeature : featureSet) {
            if (currentFeature.equals(compareFeature))
                return currentFeature;
        }
        throw new FeatureNotFoundException();
    }

    /***
     *
     * @param updateFeature - The full feature object with updated values to replace existing feature in the hashset
     * @throws FeatureNotFoundException - Thrown when feature by given name is not found in the hashset. Handled as an error as these features
     * are manually added, and we should never be processing an update for a feature that does not exist.
     */
    public void updateFeature(Feature updateFeature) throws FeatureNotFoundException {
        Feature compareFeature = new Feature(updateFeature.getName());

        if(!featureSet.contains(compareFeature)) throw new FeatureNotFoundException();

        featureSet.remove(compareFeature);
        featureSet.add(updateFeature);
    }

    /***
     * Create and adds a feature to the hashset based off of the available information passed on call. Exists in 5 variants.
     * @param name - String name for identifying the feature
     * @param version - String version as semantic version when there is only a single bound for this feature
     * @param boundingIdentifier - BoundingIdentifierEnum value for whether the single version string provided is to be used as a ceiling or floor
     * @param classification - VersionSetIdentfierEnum value for whether the single VersionSet binding being created is to be evaluated as a server or plugin binding
     * @param enabled - Whether this feature is currently enabled based upon compat specific logic
     */
    public void addFeature(String name, String version, BoundIdentifierEnum boundingIdentifier, VersionSetIdentifierEnum classification, boolean enabled){
        VersionSet server = classification == VersionSetIdentifierEnum.SERVER ? new VersionSet(version, boundingIdentifier) : new VersionSet();
        VersionSet plugin = classification == VersionSetIdentifierEnum.PLUGIN ? new VersionSet(version, boundingIdentifier) : new VersionSet();

        register(name, server, plugin, enabled);
    }

    /***
     * Create and adds a feature to the hashset based off of the available information passed on call. Exists in 5 variants.
     * @param name - String name for identifying the feature
     * @param minVer - String version as semantic version representing the minimum version required for this binding
     * @param maxVer - String version as semantic version representing the maximum version supported for this binding
     * @param classification - VersionSetIdentfierEnum value for whether the single VersionSet binding being created is to be evaluated as a server or plugin binding
     * @param enabled - Whether this feature is currently enabled based upon compat specific logic
     */
    public void addFeature(String name, String minVer, String maxVer, VersionSetIdentifierEnum classification, boolean enabled){
        VersionSet server = classification == VersionSetIdentifierEnum.SERVER ? new VersionSet(minVer, maxVer) : new VersionSet();
        VersionSet plugin = classification == VersionSetIdentifierEnum.PLUGIN ? new VersionSet(minVer, maxVer) : new VersionSet();

        register(name, server, plugin, enabled);
    }

    /***
     * Create and adds a feature to the hashset based off of the available information passed on call. Exists in 5 variants.
     * @param name - String name for identifying the feature
     * @param serverVersion - String version as semantic version when there is only a single bound for SERVER support for this feature
     * @param boundingIdentifier - BoundingIdentifierEnum value for whether the SERVER version string provided is to be used as a ceiling or floor
     * @param minPluginVer - String version as semantic version representing the minimum version required for PLUGIN support for this binding
     * @param maxPluginVer - String version as semantic version representing the maximum version required for PLUGIN support for this binding
     * @param enabled - Whether this feature is currently enabled based upon compat specific logic
     */
    public void addFeature(String name, String serverVersion, BoundIdentifierEnum boundingIdentifier, String minPluginVer, String maxPluginVer, boolean enabled){
        VersionSet server = new VersionSet(serverVersion, boundingIdentifier);
        VersionSet plugin = new VersionSet(minPluginVer, maxPluginVer);

        register(name, server, plugin, enabled);
    }

    /***
     * Create and adds a feature to the hashset based off of the available information passed on call. Exists in 5 variants.
     * @param name - String name for identifying the feature
     * @param minServerVer - String version as semantic version representing the minimum version required for SERVER support for this binding
     * @param maxServerVer - String version as semantic version representing the maximum version required for SERVER support for this binding
     * @param pluginVersion - String version as semantic version when there is only a single bound for PLUGIN support for this feature
     * @param boundingIdentifier - BoundingIdentifierEnum value for whether the PLUGIN version string provided is to be used as a ceiling or floor
     * @param enabled - Whether this feature is currently enabled based upon compat specific logic
     */
    public void addFeature(String name, String minServerVer, String maxServerVer, String pluginVersion, BoundIdentifierEnum boundingIdentifier, boolean enabled){
        VersionSet server = new VersionSet(minServerVer, maxServerVer);
        VersionSet plugin = new VersionSet(pluginVersion, boundingIdentifier);

        register(name, server, plugin, enabled);
    }

    /***
     * Create and adds a feature to the hashset based off of the available information passed on call. Exists in 5 variants.
     * @param name - String name for identifying the feature
     * @param minServerVer - String version as semantic version representing the minimum version required for SERVER support for this binding
     * @param maxServerVer - String version as semantic version representing the maximum version required for SERVER support for this binding
     * @param minPluginVer - String version as semantic version representing the minimum version required for PLUGIN support for this binding
     * @param maxPluginVer - String version as semantic version representing the maximum version required for PLUGIN support for this binding
     * @param enabled - Whether this feature is currently enabled based upon compat specific logic
     */
    public void addFeature(String name, String minServerVer, String maxServerVer, String minPluginVer, String maxPluginVer, boolean enabled){
        VersionSet server = new VersionSet(minServerVer, maxServerVer);
        VersionSet plugin = new VersionSet(minPluginVer, maxPluginVer);

        register(name, server, plugin, enabled);
    }

    /***
     * @param name - String name for identifying the feature
     * @return Boolean value of the supported field in requested feature found by unique name String
     * @throws FeatureNotFoundException - Thrown when feature by given name is not found in the hashset. Handled as an error as these features
     * are manually added, and we should never be validating for a feature that does not exist.
     */
    public boolean isFeatureSupported(String name) throws FeatureNotFoundException  {
        Feature feature = getFeature(name);

        return feature.isSupported();
    }

    /***
     * @param name - String name for identifying the feature
     * @return Boolean value of the enabled field in requested feature found by unique name String
     * @throws FeatureNotFoundException - Thrown when feature by given name is not found in the hashset. Handled as an error as these features
     * are manually added, and we should never be validating for a feature that does not exist.
     */
    public boolean isFeatureEnabled(String name) throws FeatureNotFoundException  {
        Feature feature = getFeature(name);

        return feature.isEnabled();
    }

    /***
     * @param name - String name for identifying the feature
     * @return Boolean value of the supported AND enabled field in requested feature found by unique name String
     * @throws FeatureNotFoundException - Thrown when feature by given name is not found in the hashset. Handled as an error as these features
     * are manually added, and we should never be validating for a feature that does not exist.
     */
    public boolean isFeatureActive(String name) throws FeatureNotFoundException  {
        Feature feature = getFeature(name);

        return feature.isActive();
    }


    // ****** HELPER FUNCTIONS ******

    /***
     * Helper function to collect the individual pieces of a feature together, and actually create and add them the hashset
     * @param name - String name for identifying the feature
     * @param serverSet - VersionSet bounds for valid SERVER version support
     * @param pluginSet - VersionSet boudns for valid PLUGIN version support
     * @param enabled - Whether this feature is currently enabled based upon compat specific logic
     */
    private void register(String name, VersionSet serverSet, VersionSet pluginSet, boolean enabled) {
        boolean supported = validateSupport(serverSet, pluginSet, this.pluginVersion);

        featureSet.add(new Feature(name, serverSet, pluginSet, supported, enabled));
    }

    /***
     * Calculate whether a feature SHOULD be supported based upon the provided version bounds and the current versions of the plugin and server
     * @param serverRestrictions - VersionSet bounds for valid SERVER version support
     * @param pluginRestrictions - VersionSet bounds for valid PLUGIN version support
     * @param currentPluginVersion - String version as semantic version based off of the current FeatureList's constructor argument
     * @return - Combined boolean value of valid plugin and server support (T if 2x T, F if 1x F)
     */
    private boolean validateSupport(VersionSet serverRestrictions, VersionSet pluginRestrictions, String currentPluginVersion) {
        String currentServerVersion = Servers.getServerVersion();
        boolean isServerSupported = isWithinVersionBounds(serverRestrictions, currentServerVersion);
        boolean isPluginSupported = isWithinVersionBounds(pluginRestrictions, currentPluginVersion);

        return isPluginSupported && isServerSupported;
    }

    /***
     * Helper function to validateSupport to check if a provided semantic version exists within the ceiling and floor of the provided bounds
     * @param bounds - VersionSet bounds for valid support
     * @param version - String version as semantic version to be checked if it is within supported range of provided bounds
     * @return boolean value of whether the provided version is valid against the provided bounds
     */
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
