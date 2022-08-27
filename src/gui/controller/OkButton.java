package src.gui.controller;

import src.App;
import src.gui.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OkButton extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = MainFrame.getInstance().getTextArea().getText();
        System.out.println("Korsnik je uneo " + s);
        MainFrame.getInstance().setQuery(s);
        App.getGui().start();
    }

}
