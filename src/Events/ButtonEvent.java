package Events;

public class ButtonEvent extends ApplicationEvent {
    String dataString;

    public ButtonEvent(String dataString) {
        super();
        this.dataString = dataString;
    }

    public String getDataString() {
        return dataString;
    }

}
