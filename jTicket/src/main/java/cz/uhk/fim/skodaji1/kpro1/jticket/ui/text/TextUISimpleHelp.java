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

import java.awt.Color;

/**
 * Class representing simple help for text user interface
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class TextUISimpleHelp implements ITextUIHelp
{
    /**
     * Command to which help belongs to
     */
    private String command;
    
    /**
     * Text of help to the command
     */
    private String help;
    
    /**
     * Color of the command
     */
    private Color color;

    /**
     * Creates new simple help
     * @param command Command to which help belongs to
     * @param help Text of help to the command
     * @param color Color of the command
     */
    public TextUISimpleHelp(String command, String help, Color color)
    {
        this.command = command;
        this.help = help;
        this.color = color;
    }

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public String getHelp()
    {
        return this.help;
    }

    @Override
    public Color getColor()
    {
        return this.color;
    }
}
