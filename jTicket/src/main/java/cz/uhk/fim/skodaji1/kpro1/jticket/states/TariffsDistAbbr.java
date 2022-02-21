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
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariff;
import cz.uhk.fim.skodaji1.kpro1.jticket.help.Help;
import cz.uhk.fim.skodaji1.kpro1.jticket.help.HelpFactory;
import cz.uhk.fim.skodaji1.kpro1.jticket.screens.HTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro1.jticket.screens.Screen;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing creating new distance tariff (with abbreavation selected)
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TariffsDistAbbr extends State {

    /**
     * Name of tariff
     */
    private String tariffName;
    
    /**
     * Creates new dialog for creating new distance tariff (with abbreavation selected)
     * @param controller Controller of program
     */
    public TariffsDistAbbr(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/tariffs/distance:abbr";
        this.screen = new HTMLTemplateScreen("tariffs-dist-abbr", "tariffs-dist-abbr.html");
        this.name = "tariffs-dist-abbr";
        this.strict = false;
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("<zkratka tarifu>", Color.YELLOW, "Zkratka tarifu");
        this.helps[1] = HelpFactory.CreateSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
    }

    @Override
    public Screen GetScreen()
    {
        Map<String, String> data = new HashMap<>();
        data.put("tariffs_tr", cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariffs.GetInstance().GenerateTariffsTableRows());
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
    
    @Override
    public Screen GetScreen(Map<String, String> data)
    {
        data.put("tariffs_tr", cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariffs.GetInstance().GenerateTariffsTableRows());
        this.tariffName = data.get("tariff_name");
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
    
    @Override
    public void HandleInput(String input)
    {
        if (input.toLowerCase().equals("cancel"))
        {
            this.controller.ChangeState("tariffs");
        }
        else
        {
            Tariff t = cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariffs.GetInstance().GetTariff(input);
            if (t != null)
            {
                this.controller.ShowError("Tarif '" + input + "' jiz existuje!");
            }
            else
            {
                Map<String, String> data = new HashMap<>();
                data.put("tariff_abbr", input);
                data.put("tariff_name", this.tariffName);
                this.controller.ChangeState("tariffs-dist", data);
            }
        }
    }

    
    
}
