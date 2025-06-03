package metadev.digital.metacustomitemslib.compatibility;

import metadev.digital.metacustomitemslib.compatibility.enums.VersionSetIdentifierEnum;

import java.util.Objects;

/***
 * Representation of disparate features in each compatibility so that it's enablement/support can be managed in a uniform manner
 */

public class Feature {
    private final String name;
    private final VersionSet serverVersion;
    private final VersionSet pluginVersion;
    private final boolean supported;
    private boolean enabled;

    // ****** CONSTRUCTORS ******

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
     * Sets a new feature with a single bounded version set with variable support and enablement
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
     * Sets a new feature with a double bounded version set with variable support and enablement
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

    // ****** MANIPULATORS ******

    /***
     *
     * @return - Server compatible semantic version bounds as VersionSet
     */
    public VersionSet getServerVersionSet() {
        return serverVersion;
    }

    /***
     *
     * @return - Plugin compatible semantic version bounds as VersionSet
     */
    public VersionSet getPluginVersionSet() {
        return pluginVersion;
    }

    /***
     *
     * @return - Value of enabled flag
     */
    public boolean isEnabled(){
        return enabled;
    }

    /***
     *
     * @return - Value of supported flag
     */
    public boolean isSupported(){
        return supported;
    }

    /***
     *
     * @return - True if feature is both supported and enabled
     */
    public boolean isActive(){
        return supported && enabled;
    }

    /***
     *
     * @return - Name field of feature
     */
    public String getName() {
        return name;
    }

    /***
     *
     * @param flag - The T/F value for the enablement of this feature
     */
    public void setEnabled(boolean flag) {
        this.enabled = flag;
    }

    // ****** HELPER FUNCTIONS ******

    /***
     * Compare provided object to current feature based off of an equal name value
     * @param compare - Object containing the feature we are comparing to
     * @return - The T/F value whether the provided object equals the current
     */
    @Override
    public boolean equals(Object compare) {
        if(this == compare) return true;
        if( compare == null || this.getClass() != compare.getClass() ) return false;
        return Objects.equals(this.name, ((Feature) compare).name);
    }
}

