/*
 * Copyright (C) 2021 Jiri Skoda <jiri.skoda@student.upce.cz>
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
package cz.uhk.fim.skodaji1.kpro1.semestralproject.states;

import cz.uhk.fim.skodaji1.kpro1.semestralproject.Controller;
import cz.uhk.fim.skodaji1.kpro1.semestralproject.help.Help;
import cz.uhk.fim.skodaji1.kpro1.semestralproject.screens.HTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro1.semestralproject.screens.Screen;
import java.util.Map;

/**
 * Class representing state of program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public abstract class State {
    
    /**
     * Controller of the program
     */
    protected Controller controller;
    
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
    protected Screen screen;
    
    /**
     * Array of available commands and help to them
     */
    protected Help[] helps;
    
    /**
     * Flag, whether input is strict to helps only or not
     */
    protected boolean strict = true;
    
    /**
     * Creates new state of program
     * @param controller Controller of program
     */
    public State(Controller controller)
    {
        this.controller = controller;
    }
    
    
    /**
     * Gets name of state of program
     * @return Name of state of program
     */
    public String GetName()
    {
        return this.name;
    }
    
    /**
     * Gets prefix of state in command line
     * @return Prefix of state in command line
     */
    public String GetCommandPrefix()
    {
        return this.commandPrefix;
    }
    
    /**
     * Gets screen representing state of program
     * @return Screen representing state of program
     */
    public Screen GetScreen()
    {
        return this.screen;
    }
    
    /**
     * Gets screen representing state of program
     * @param data Data which will be displayed on screen
     * @return Screen representing state of program
     */
    public Screen GetScreen(Map<String, String> data)
    {
        Screen reti = this.screen;
        if (data != null && this.screen instanceof HTMLTemplateScreen)
        {
            HTMLTemplateScreen actualScreen = (HTMLTemplateScreen)this.screen;
            actualScreen.SetContent(data);
            reti = actualScreen;
        }
        return reti;
    }
    
    /**
     * Gets array of available commands and helps to them
     * @return Array of available commands and helps to them
     */
    public Help[] GetHelps()
    {
        return this.helps;
    }
    
    /**
     * Gets flag, whether input has to be limited to helps only or not
     * @return <code>TRUE</code> if helps has to be limited to helps only, <code>FALSE</code> otherwise
     */
    public boolean GetStrict()
    {
        return this.strict;
    }
    
    /**
     * Handles input from command line
     * @param input Input from command line
     */
    public abstract void HandleInput(String input);
    
    /**
     * Loads screen
     */
    public void Load(){};
    
    /**
     * Gets control to state
     */
    public void Control(){};
    
}