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
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Station;
import cz.uhk.fim.skodaji1.kpro1.jticket.help.Help;
import cz.uhk.fim.skodaji1.kpro1.jticket.help.HelpFactory;
import cz.uhk.fim.skodaji1.kpro1.jticket.screens.HTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro1.jticket.screens.Screen;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing state of program which displays distances menu
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class Distances extends State
{

    /**
     * Creates new state of program with distances menu
     * @param controller Controller of program
     */
    public Distances(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/distances";
        this.screen = new HTMLTemplateScreen("distances", "distances.html");
        this.name = "distances";
        this.strict = true;
        
        this.helps = new Help[4];
        this.helps[0] = HelpFactory.CreateSimpleHelp("create", Color.YELLOW, "Rezim vytvareni tabulky vzdalenosti");
        this.helps[1] = HelpFactory.CreateSimpleHelp("set", Color.YELLOW, "Rezim upravy tabulky vzdalenosti");
        this.helps[2] = HelpFactory.CreateSimpleHelp("view", Color.YELLOW, "Rezim prohlizeni tabulky vzdalenosti");
        this.helps[3] = HelpFactory.CreateSimpleHelp("back", Color.MAGENTA, "Zpet");
    }
    
    @Override
    public Screen GetScreen()
    {
        Map<String, String> data = new HashMap<>();
        data.put("stations_tr", cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations.GetInstance().GenerateTableRows());
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }

    @Override
    public void HandleInput(String input)
    {
        switch (input.toLowerCase())
        {
            case "back": this.controller.ChangeState("data"); break;
            case "create": this.controller.ChangeState("distances-create"); break;
            case "view": this.controller.ChangeState("distances-view"); break;
            case "set": this.controller.ChangeState("distances-set-from"); break;
            
        }
    }
    
}
