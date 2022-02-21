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
package cz.uhk.fim.skodaji1.kpro1.jticket.states;

import cz.uhk.fim.skodaji1.kpro1.jticket.Controller;
import cz.uhk.fim.skodaji1.kpro1.jticket.help.Help;
import cz.uhk.fim.skodaji1.kpro1.jticket.help.HelpFactory;
import cz.uhk.fim.skodaji1.kpro1.jticket.screens.ScreenFactory;
import java.awt.Color;

/**
 * Class representing welcome state of program
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class Welcome extends State
{

    /**
     * Creates new welcome state of program
     * @param controller Controller of whole program
     */
    public Welcome(Controller controller)
    {
        super(controller);
        this.commandPrefix = "";
        this.screen = ScreenFactory.CreateHTMLScreen("welcome", "welcome.html");
        this.name = "welcome";
        
        this.helps = new Help[3];
        this.helps[0] = HelpFactory.CreateSimpleHelp("ticket", Color.YELLOW, "Rezim prodeje");
        this.helps[1] = HelpFactory.CreateSimpleHelp("data", Color.YELLOW, "Rezim upravy dat");
        this.helps[2] = HelpFactory.CreateSimpleHelp("exit", Color.MAGENTA, "Ukoncit program");
    }
    
    

    @Override
    public void HandleInput(String input)
    {
        switch (input.toLowerCase())
        {
            case "exit": this.controller.ChangeState("exit"); break;
            case "data": this.controller.ChangeState("data"); break;
            case "ticket":
                if (cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariffs.GetInstance().GetAllTariffs().length < 1)
                {
                    this.controller.ShowError("Nenalezen zadny tarif! Rezim prodeje nelze spustit!");
                }
                else if (cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().GetAllStations().length < 1)
                {
                    this.controller.ShowError("Nenalezeny zadne stanice! Rezim prodeje nelze spustit!");
                }
                else
                {
                    this.controller.ChangeState("ticket");
                }             
                break;
        }
    }
    
}
