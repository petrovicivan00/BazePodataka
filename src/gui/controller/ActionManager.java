package src.gui.controller;

public class ActionManager {

    private OkButton okButton;

    public ActionManager() {
        initialiseActions();
    }

    private void initialiseActions(){

        okButton = new OkButton();

    }

    public OkButton getOkButton() {
        return okButton;
    }

    public void setOkButton(OkButton okButton) {
        this.okButton = okButton;
    }

}
