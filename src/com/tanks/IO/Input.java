package com.tanks.IO;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Input extends JComponent {

    private boolean[] map;

    public Input() {

        map = new boolean[256];
        for (int i = 0; i < map.length; i++) {
            final int KEY_CODE = i;

            //press key
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, false), i * 2);
            getActionMap().put(i * 2, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    map[KEY_CODE] = true;
                }
            });

            //release key
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, true), i * 2 + 1);
            getActionMap().put(i * 2 + 1, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    map[KEY_CODE] = false;
                }
            });
        }
    }

    public boolean getKey(int keyCode) {
        return map[keyCode];
    }

}