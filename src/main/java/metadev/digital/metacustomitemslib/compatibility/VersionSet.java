package metadev.digital.metacustomitemslib.compatibility;

import metadev.digital.metacustomitemslib.compatibility.enums.BoundIdentifierEnum;

/***
 * A pair of two versions to be used as upper and lower bounds for support of a feature.
 * Boolean is always true if a Version is provided, and false if not. Where false is being used in place of a nulled object
 */
public class VersionSet {
    private final boolean set;
    private final Version ceiling;
    private final Version floor;

    /***
     * Construct an unset VersionSet. Used in a Feature when a server or plugin version bound is not required. False set value used in place of a null
     */
    public VersionSet(){
        this.set = false;
        this.ceiling = new Version();
        this.floor = new Version();
    }

    /***
     * Construct a VersionSet containing only a single bound. Used when there is only a ceiling or floor for the bound used in a Feature.
     * @param semVer - String version provided as a semantic version
     * @param classification - BoundIdentifierEnum to represent whether the provided bound is to be used as a ceiling or floor.
     */
    public VersionSet(String semVer, BoundIdentifierEnum classification ){
        this.set = true;
        this.ceiling = classification == BoundIdentifierEnum.CEILING ? new Version(semVer) : new Version();
        this.floor = classification == BoundIdentifierEnum.FLOOR ? new Version(semVer) : new Version();
    }

    /***
     * Construct a VersionSet containing both a ceiling and floor for version support
     * @param ceiling - Version object containing the String semantic version to be used as the ceiling of version support
     * @param floor - Version object containing the String semantic version to be used as the floor of version support
     */
    public VersionSet(String floor, String ceiling){
        this.set = true;
        this.ceiling = new Version(ceiling);
        this.floor = new Version(floor);
    }

    /***
     *
     * @return - Boolean set value of the VersionSet. Would always be true if any valid semantic version is provided to this VersionSet.
     * Use case for false would be in the event that a feature has no version limitations for plugin/server support
     */
    public boolean isSet() {
        return set;
    }

    /**
     * Helper function to identify if the VersionSet binding is doubly bound. I.E. there is both a ceiling and a floor
     * @return - Boolean value representing if the VersionSet is double bound.
     */
    public boolean isDoubleBound() {
        return ceiling.isSet() && floor.isSet();
    }

    /***
     * Helper function to identify if the VersionSet binding being evaluated is to be used as a ceiling or floor
     * @return - BoundIdentifierEnum value representing whether the VersionSet is essentially a ceiling or floor OR null the VersionSet is not set or is double bound
     */
    public BoundIdentifierEnum getBoundingIdentifier() {
        if (set && !isDoubleBound()) {
            return ceiling.isSet() ? BoundIdentifierEnum.CEILING : BoundIdentifierEnum.FLOOR;
        }
        return null;
    }

    /***
     *
     * @return - Version object for the ceiling of this VersionSet
     */
    public Version getCeiling() {
        return ceiling;
    }

    /***
     *
     * @return - Version object for the floor of this VersionSet
     */
    public Version getFloor() {
        return floor;
    }
}