/*
 * Copyright (C) 2022 Jiri Skoda <jiri.skoda@student.upce.cz>
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
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.ITextUIScreen;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIHTMLTemplateScreen;
import java.util.Map;

/**
 * Class representing state of program using text user interface
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public abstract class TextUIState
{
    /**
     * Controller of the program
     */
    protected TextUIController controller;
    
    /**
     * Name of state
     */
    protected String name;
    
    /**
     * Prefix of state in command line
     */
    protected String commandPrefix;
    
    /**
     * Screen of state of program
     */
    protected ITextUIScreen screen;
    
    /**
     * Array of available commands and help to them
     */
    protected ITextUIHelp[] helps;
    
    /**
     * Flag, whether input is strict to helps only or not
     */
    protected boolean strict = true;
    
    /**
     * Creates new state of program
     * @param controller Controller of program
     */
    public TextUIState(TextUIController controller)
    {
        this.controller = controller;
    }
    
    
    /**
     * Gets name of state of program
     * @return Name of state of program
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Gets prefix of state in command line
     * @return Prefix of state in command line
     */
    public String getCommandPrefix()
    {
        return this.commandPrefix;
    }
    
    /**
     * Gets screen representing state of program
     * @return Screen representing state of program
     */
    public ITextUIScreen getScreen()
    {
        return this.screen;
    }
    
    /**
     * Gets screen representing state of program
     * @param data Data which will be displayed on screen
     * @return Screen representing state of program
     */
    public ITextUIScreen getScreen(Map<String, String> data)
    {
        ITextUIScreen reti = this.screen;
        if (data != null && this.screen instanceof TextUIHTMLTemplateScreen)
        {
            TextUIHTMLTemplateScreen actualScreen = (TextUIHTMLTemplateScreen)this.screen;
            actualScreen.setContent(data);
            reti = actualScreen;
        }
        return reti;
    }
    
    /**
     * Gets array of available commands and helps to them
     * @return Array of available commands and helps to them
     */
    public ITextUIHelp[] getHelps()
    {
        return this.helps;
    }
    
    /**
     * Gets flag, whether input has to be limited to helps only or not
     * @return <code>TRUE</code> if helps has to be limited to helps only, <code>FALSE</code> otherwise
     */
    public boolean getStrict()
    {
        return this.strict;
    }
    
    /**
     * Handles input from command line
     * @param input Input from command line
     */
    public abstract void handleInput(String input);
    
    /**
     * Loads screen
     */
    public void load(){};
    
    /**
     * Gets control to state
     */
    public void control(){};

}
