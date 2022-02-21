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
import cz.uhk.fim.skodaji1.kpro1.semestralproject.data.DistanceTariff;
import cz.uhk.fim.skodaji1.kpro1.semestralproject.data.ZoneTariff;
import cz.uhk.fim.skodaji1.kpro1.semestralproject.help.Help;
import cz.uhk.fim.skodaji1.kpro1.semestralproject.help.HelpFactory;
import cz.uhk.fim.skodaji1.kpro1.semestralproject.screens.HTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro1.semestralproject.screens.Screen;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing creating new distance tariff (with confirmation dialog)
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class TariffsDist extends State {

    /**
     * Name of tariff
     */
    private String tariffName;
    
    /**
     * Abbreavation of tariff
     */
    private String tariffAbbr;
    
    /**
     * Creates new dialog for creating new distance tariff (with confirmation dialog)
     * @param controller Controller of program
     */
    public TariffsDist(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/tariffs/distance?";
        this.screen = new HTMLTemplateScreen("tariffs-dist", "tariffs-dist.html");
        this.name = "tariffs-dist";
        this.strict = true;
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("yes", Color.GREEN, "Ano, zadane udaje jsou v poradku");
        this.helps[1] = HelpFactory.CreateSimpleHelp("no", Color.RED, "Zrusit");
    }

    @Override
    public Screen GetScreen()
    {
        Map<String, String> data = new HashMap<>();
        data.put("tariffs_tr", cz.uhk.fim.skodaji1.kpro1.semestralproject.data.Tariffs.GetInstance().GenerateTariffsTableRows());
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
    
    @Override
    public Screen GetScreen(Map<String, String> data)
    {
        data.put("tariffs_tr", cz.uhk.fim.skodaji1.kpro1.semestralproject.data.Tariffs.GetInstance().GenerateTariffsTableRows());
        this.tariffName = data.get("tariff_name");
        this.tariffAbbr = data.get("tariff_abbr");
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
    
    @Override
    public void HandleInput(String input)
    {
        switch(input.toLowerCase())
        {
            case "no": this.controller.ChangeState("tariffs"); break;
            case "yes": 
                Map<String, String> data = new HashMap<>();
                data.put("tariff_name", this.tariffName);
                data.put("tariff_abbr", this.tariffAbbr);                
                System.out.format("Tariff (type: %s, name: %s, abbreavation: %s) has been created\n", "DISTANCE", this.tariffName, this.tariffAbbr);
                cz.uhk.fim.skodaji1.kpro1.semestralproject.data.Tariffs.GetInstance().AddTariff(new DistanceTariff(this.tariffName, this.tariffAbbr));
                this.controller.ChangeState("tariffs-dist-prices", data);
                break;
        }
    }
    
}
