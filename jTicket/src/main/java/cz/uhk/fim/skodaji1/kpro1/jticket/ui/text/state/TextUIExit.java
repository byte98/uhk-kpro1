/*
 * Copyright (C) 2021 Jiri Skoda <skodaji1@uhk.cz>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.state;

import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.ITextUIHelp;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIController;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIHelpFactory;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIScreenFactory;
import java.awt.Color;

/**
 * Class representing exit dialog of the program
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TextUIExit extends TextUIState {

    /**
     * Creates new state of program representing exit dialog
     * @param controller Controller of whole program
     */
    public TextUIExit(TextUIController controller)
    {
        super(controller);
        this.commandPrefix = "/exit?";
        this.screen = TextUIScreenFactory.createHTMLScreen("exit", "exit.html");
        this.name = "exit";
        
        this.helps = new ITextUIHelp[2];        
        this.helps[0] = TextUIHelpFactory.createSimpleHelp("no", Color.GREEN, "Zrusit");
        this.helps[1] = TextUIHelpFactory.createSimpleHelp("yes", Color.RED, "Ukoncit program");
    }

    @Override
    public void handleInput(String input)
    {
        switch (input.toLowerCase())
        {
            case "yes": this.controller.stop(); break;
            case "no": this.controller.changeToPreviousState();                
        }
    }
    
}
