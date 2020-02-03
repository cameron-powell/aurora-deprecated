public class LightDirective {

    private int lightID;
    private Directive directive;

    public LightDirective() {}

    public int getLightID() {
        return lightID;
    }

    public void setLightID(int lightID) {
        this.lightID = lightID;
    }

    public Directive getDirective() {
        return directive;
    }

    public void setDirective(Directive directive) {
        this.directive = directive;
    }

    @Override
    public String toString() {
        return "LightDirective{" +
                "lightID=" + lightID +
                ", directive=" + directive +
                '}';
    }

    public enum Directive {
        CURRENT_TEMPERATURE,
        CURRENT_HOUR,
        CURRENT_MINUTE,
        RANDOM_COLOR
    }
}
