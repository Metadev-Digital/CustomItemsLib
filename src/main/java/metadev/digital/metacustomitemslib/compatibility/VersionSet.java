package metadev.digital.metacustomitemslib.compatibility;

/***
 *
 */
public class VersionSet {
    private final boolean set;
    private final Version ceiling;
    private final Version floor;

    /***
     *
     */
    public VersionSet(){
        this.set = false;
        this.ceiling = new Version();
        this.floor = new Version();
    }

    /***
     *
     * @param semVer
     * @param classification
     */
    public VersionSet(String semVer, BoundIdentifierEnum classification ){
        this.set = true;
        this.ceiling = classification == BoundIdentifierEnum.CEILING ? new Version(semVer) : new Version();
        this.floor = classification == BoundIdentifierEnum.FLOOR ? new Version(semVer) : new Version();
    }

    /***
     *
     * @param ceiling
     * @param floor
     */
    public VersionSet(String floor, String ceiling){
        this.set = true;
        this.ceiling = new Version(ceiling);
        this.floor = new Version(floor);
    }

    public boolean isSet() {
        return set;
    }

    public boolean isDoubleBound() {
        return ceiling.isSet() && floor.isSet();
    }

    public BoundIdentifierEnum getBoundingIdentifier() {
        if (set && !isDoubleBound()) {
            return ceiling.isSet() ? BoundIdentifierEnum.CEILING : BoundIdentifierEnum.FLOOR;
        }
        return null;
    }

    public Version getCeiling() {
        return ceiling;
    }

    public Version getFloor() {
        return floor;
    }
}