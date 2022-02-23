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
package cz.uhk.fim.skodaji1.kpro1.jticket.ui.text;

import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.state.TextUIState;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.state.TextUIStateFactory;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class representing controller of program, which is running with text user interface
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TextUIController
{
    /**
     * Instance of controller class
     */
    private static TextUIController instance = null;
    
    /**
     * List of all available states of program
     */
   private final List<TextUIState> states;
   
   /**
    * Main window of the program
    */
   private TextUIMainWindow mainWindow;
   
   /**
    * Window with help needed when selling tickets
    */
   private TextUIHelpWindow helpWindow;
   
   /**
    * Actual state of program
    */
   private TextUIState actualState = null;
   
   /**
    * Previous state of program
    */
   private TextUIState previousState = null;
    
    /**
     * Creates new instance of controller
     */
    private TextUIController()
    {
        this.states = new ArrayList<>();        
        this.addStates();
    }
    
    /**
     * Gets controller of program
     * @return Controller of program
     */
    public static TextUIController getController()
    {
        TextUIController reti = TextUIController.instance;
        if (TextUIController.instance == null)
        {
            TextUIController.instance = new TextUIController();
            reti = TextUIController.instance;
        }
        return reti;
    }
    
    /**
     * Adds available states of program
     */
    private void addStates()
    {
        this.states.add(TextUIStateFactory.createState(this, "welcome"));
        this.states.add(TextUIStateFactory.createState(this, "exit"));
        this.states.add(TextUIStateFactory.createState(this, "data"));
        this.states.add(TextUIStateFactory.createState(this, "stations"));
        this.states.add(TextUIStateFactory.createState(this, "stations-add"));
        this.states.add(TextUIStateFactory.createState(this, "stations-add-name"));
        this.states.add(TextUIStateFactory.createState(this, "stations-add-abbr"));
        this.states.add(TextUIStateFactory.createState(this, "stations-edit"));
        this.states.add(TextUIStateFactory.createState(this, "stations-edit-name"));
        this.states.add(TextUIStateFactory.createState(this, "stations-edit-abbr"));
        this.states.add(TextUIStateFactory.createState(this, "stations-delete"));
        this.states.add(TextUIStateFactory.createState(this, "distances"));
        this.states.add(TextUIStateFactory.createState(this, "distances-create"));
        this.states.add(TextUIStateFactory.createState(this, "distances-view"));
        this.states.add(TextUIStateFactory.createState(this, "distances-view-station"));
        this.states.add(TextUIStateFactory.createState(this, "distances-set-from"));
        this.states.add(TextUIStateFactory.createState(this, "distances-set-to"));
        this.states.add(TextUIStateFactory.createState(this, "distances-set-distance"));
        this.states.add(TextUIStateFactory.createState(this, "distances-set"));
        this.states.add(TextUIStateFactory.createState(this, "tariffs"));
        this.states.add(TextUIStateFactory.createState(this, "tariffs-zone-name"));
        this.states.add(TextUIStateFactory.createState(this, "tariffs-zone-abbr"));
        this.states.add(TextUIStateFactory.createState(this, "tariffs-zone"));
        this.states.add(TextUIStateFactory.createState(this, "tariffs-zone-zones"));
        this.states.add(TextUIStateFactory.createState(this, "tariffs-zone-prices"));
        this.states.add(TextUIStateFactory.createState(this, "tariffs-zone-view"));
        this.states.add(TextUIStateFactory.createState(this, "tariffs-zone-delete"));
        this.states.add(TextUIStateFactory.createState(this, "tariffs-dist-name"));
        this.states.add(TextUIStateFactory.createState(this, "tariffs-dist-abbr"));
        this.states.add(TextUIStateFactory.createState(this, "tariffs-dist"));
        this.states.add(TextUIStateFactory.createState(this, "tariffs-dist-prices"));
        this.states.add(TextUIStateFactory.createState(this, "tariffs-dist-view"));
        this.states.add(TextUIStateFactory.createState(this, "tariffs-dist-delete"));
        this.states.add(TextUIStateFactory.createState(this, "ticket"));
    }
    
    /**
     * Method which handles commands
     * @param command Command which will be handled
     */
    public void handleCommand(String command)
    {
        this.actualState.handleInput(command);
    }
    
    /**
     * Changes size of string to required length
     * @param input Input string which size will be changed
     * @param length Required string length
     * @return String resized to required length
     */
    public static String trimString(String input, int length)
    {
        String reti = "";
        if (input.length() > length - 3)
        {
            for (int i = 0; i < length - 3; i++)
            {
                reti += input.charAt(i);
            }
            reti += "...";
        }
        else
        {
            reti = input;
        }
        return reti;
    }
    
    /**
     * Gets state of program by its name
     * @param name Name of state
     * @return State of program or <code>NULL</code>
     */
    private TextUIState getState (String name)
    {
        TextUIState reti = null;
        Iterator<TextUIState> it = this.states.iterator();
        while (it.hasNext())
        {
            TextUIState s = it.next();
            if (s != null && s.getName().toLowerCase() == name.toLowerCase())
            {
                reti = s;
                break;
            }
        }
        return reti;
    }
    
    /**
     * Changes state of program
     * @param nextState Name of state of program
     */
    public void changeState(String nextState)
    {
        TextUIState state = this.getState(nextState);
        if (state != null)
        {
            this.previousState = this.actualState;
            this.actualState = state;
            this.actualState.load();
            this.mainWindow.showScreen(this.actualState.getScreen());
            this.mainWindow.setStrict(this.actualState.getStrict());
            this.mainWindow.setCommandMode(this.actualState.getCommandPrefix());
            this.mainWindow.showHelp(this.actualState.getHelps());
            this.actualState.control();
        }
    }
    
    /**
     * Changes state of program
     * @param nextState Name of state of program
     * @param data Data which will be displayed on screen
     */
    public void changeState(String nextState, Map<String, String> data)
    {
        TextUIState state = this.getState(nextState);
        if (state != null)
        {
            this.previousState = this.actualState;
            this.actualState = state;
            this.actualState.load();
            this.mainWindow.showScreen(this.actualState.getScreen(data));
            this.mainWindow.setStrict(this.actualState.getStrict());
            this.mainWindow.setCommandMode(this.actualState.getCommandPrefix());
            this.mainWindow.showHelp(this.actualState.getHelps());
            this.actualState.control();
        }
    }
    
    /**
     * Changes state of program to previous one
     */
    public void changeToPreviousState()
    {
        if (this.previousState != null)
        {
            TextUIState s = this.actualState;
            this.actualState = this.previousState;
            this.previousState = s;
            this.changeState(this.actualState.getName());
        }
    }
    
    /**
     * Changes state of program to previous one
     * @param data Data which will be displayed on screen
     */
    public void changeToPreviousState(Map<String, String> data)
    {
        if (this.previousState != null)
        {
            TextUIState s = this.actualState;
            this.actualState = this.previousState;
            this.previousState = s;
            this.changeState(this.actualState.getName(), data);
        }
    }
    
    /**
     * Stops program
     */
    public void stop()
    {
        this.mainWindow.dispatchEvent(new WindowEvent(this.mainWindow, WindowEvent.WINDOW_CLOSING));
    }
    
    /**
     * Shows error message in console
     * @param message Message which will be shown
     */
    public void showError(String message)
    {
        this.mainWindow.showInputError(message);
    }
    
    /**
     * Shows success message in console
     * @param message Message which will be shown
     */
    public void showSucess(String message)
    {
        this.mainWindow.showInputSuccess(message);
    }
    
    /**
     * Redraws actual screen
     */
    public void reDraw()
    {
        this.mainWindow.showScreen(this.actualState.getScreen());
        this.mainWindow.showHelp(this.actualState.getHelps());
        this.mainWindow.setCommandMode(this.actualState.getCommandPrefix());
    }
    
    /**
     * Redraws actual screen
     * @param data data which will be displayed on the screen
     */
    public void reDraw(Map<String, String> data)
    {
        this.mainWindow.showScreen(this.actualState.getScreen(data));
        this.mainWindow.showHelp(this.actualState.getHelps());
        this.mainWindow.setCommandMode(this.actualState.getCommandPrefix());
    }
    
    /**
     * Shows tariffs in help window
     */
    public void showTariffsHelp()
    {
        if (this.helpWindow.isVisible() == false)
        {
            this.helpWindow.setVisible(true);
        }
        this.helpWindow.setLocation(this.mainWindow.getX() + this.mainWindow.getWidth() + 5, this.mainWindow.getY());
        this.helpWindow.showTariffs();
        this.mainWindow.setFocusOnCommandLine();
    }
    
    
    /**
     * Shows stations in help window
     */
    public void showStationsHelp()
    {
        if (this.helpWindow.isVisible() == false)
        {
            this.helpWindow.setVisible(true);
        }
        this.helpWindow.setLocation(this.mainWindow.getX() + this.mainWindow.getWidth() + 5, this.mainWindow.getY());
        this.helpWindow.showStations();
        this.mainWindow.setFocusOnCommandLine();
    }
    
    /**
     * Hides help window
     */
    public void hideHelp()
    {
        this.helpWindow.setVisible(false);
    }
}
