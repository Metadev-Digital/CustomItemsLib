package metadev.digital.metacustomitemslib.compatibility;

/***
 * 
 */
public class Version {
    private final boolean set;
    private final String semVer;

    /***
     *
     */
    public Version(){
        this.set = false;
        this.semVer = "";
    }

    /***
     *
     * @param semVer
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