package metadev.digital.metacustomitemslib.compatibility;

/***
 * Version object containing a boolean set value and a String version provided as a semantic version.
 * Boolean is always true if a value is provided, and false if not. Where false is being used in place of a nulled object
 */
public class Version {
    private final boolean set;
    private final String semVer;

    /***
     * Construct an unset version. Used in a VersionSet when a ceiling or floor is not required for the bounds. False set value used in place of a null
     */
    public Version(){
        this.set = false;
        this.semVer = "";
    }

    /***
     * Construct a set version based upon a provided semantic version as string
     * @param semVer - String version provided as semantic version
     */
    public Version(String semVer){
        this.set = true;
        this.semVer = semVer;
    }

    public boolean isSet() {
        return set;
    }

    public String getVersion(){
        return semVer;
    }
}