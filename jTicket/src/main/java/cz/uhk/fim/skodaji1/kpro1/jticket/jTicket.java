package cz.uhk.fim.skodaji1.kpro1.jticket;

import cz.uhk.fim.skodaji1.kpro1.jticket.data.Configuration;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.IUserInterface;
import java.util.logging.Level;
import java.util.logging.Logger;

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

/**
 * Class representing whole program jTicket
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class jTicket
{
    /**
     * Path to file with configuration of program
     */
    private static final String configFile = "config.ini";
    
    /**
     * Main function of program
     * @param args Arguments of program
     */
    public static void main(String[] args)
    {
        Configuration config = Configuration.getInstance(configFile);
        IUserInterface ui = config.getUI();
        if (ui != null)
        {
            ui.prepare();
            ui.start();
        }
        else
        {
            Logger.getLogger(jTicket.class.getName()).log(Level.SEVERE, "Unknown UI MODE");
        }
    }
}