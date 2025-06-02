package metadev.digital.metacustomitemslib.compatibility;

/***
 * Representation of disparate features in each compatibility so that it's enablement/support can be managed in a uniform manner
 */

public class Feature {
    private String name;
    private VersionSet serverVersion;
    private VersionSet pluginVersion;
    private boolean supported;
    private boolean enabled;

    //******************Constructors********************

    /***
     * Sets a new feature with no bounded version sets that is enabled by default
     * @param name - Name of the feature binding
     */
    public Feature(String name){
        this.name = name;
        this.serverVersion = new VersionSet();
        this.pluginVersion = new VersionSet();
        this.supported = true;
        this.enabled = true;
    }

    /***
     * Sets a new feature with no bounded version sets that has a variable enablement
     * @param name - Name of the feature binding
     * @param enabled - Boolean flag for an overarching enablement of this feature, implementation to be decided by each compat. Possibly config, programmatic, feature flag, etc
     */
    public Feature(String name, boolean enabled){
        this.name = name;
        this.serverVersion = new VersionSet();
        this.pluginVersion  = new VersionSet();
        this.supported = true;
        this.enabled = enabled;
    }

    /***
     *
     * @param name - Name of the feature binding
     * @param singlyBoundedVersionSet - The version set to represent the support criteria for this feature
     * @param classification - Whether the version set represents a bound for the server or plugin version
     * @param supported - Boolean flag for whether this feature is supported on the current versions
     * @param enabled - Boolean flag for an overarching enablement of this feature, implementation to be decided by each compat. Possibly config, programmatic, feature flag, etc
     */
    public Feature(String name, VersionSet singlyBoundedVersionSet, VersionSetIdentifierEnum classification, boolean supported, boolean enabled){
        this.name = name;
        this.serverVersion = classification == VersionSetIdentifierEnum.SERVER ? singlyBoundedVersionSet : new VersionSet();
        this.pluginVersion = classification == VersionSetIdentifierEnum.PLUGIN ? singlyBoundedVersionSet : new VersionSet();
        this.supported = supported;
        this.enabled = enabled;
    }

    /***
     *
     * @param name - Name of the feature binding
     * @param serverVersion - The earliest semantic version that this feature was supported on
     * @param pluginVersion - The latest semantic version that this feature was supported on
     * @param supported - Boolean flag for whether this feature is supported on the current versions
     * @param enabled - Boolean flag for an overarching enablement of this feature, implementation to be decided by each compat. Possibly config, programmatic, feature flag, etc
     */
    public Feature(String name, VersionSet serverVersion, VersionSet pluginVersion, boolean supported, boolean enabled){
        this.name = name;
        this.serverVersion = serverVersion;
        this.pluginVersion = pluginVersion;
        this.supported = supported;
        this.enabled = enabled;
    }

    //********************Functions*********************
    public VersionSet getServerVersionSet() {
        return serverVersion;
    }

    public VersionSet getPluginVersionSet() {
        return pluginVersion;
    }

    public boolean isActive(){
        return supported && enabled;
    }

    public void setEnabled(boolean flag) {
        this.enabled = flag;
    }
}

