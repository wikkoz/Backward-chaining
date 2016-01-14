package Events;

import Interafaces.IApplicationEvent;

public class ButtonEvent implements IApplicationEvent {
    String dataString;

    public ButtonEvent(String dataString) {
        this.dataString = dataString;
    }

    public String getDataString() {
        return dataString;
    }
}
