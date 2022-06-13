/*
 * Copyright (C) 2022 Jiri Skoda <skodaji1@uhk.cz.cz>
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
package cz.uhk.fim.skodaji1.kpro1.jticket.ui.window;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing window user interface theme
 * @author Jiri Skoda <skodaji1@uhk.cz.cz>
 */
public class WindowUITheme
{
    /**
     * List with all available UI themes
     */
    public static final List<WindowUITheme> THEMES = new ArrayList<>();
    
    static
    {
        // Initialization of list of available themes
        THEMES.add(new WindowUITheme(
                "CLASSIC_WINDOWS",
                "Klasické",
                "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel",
                "resources/windowui/classic",
                new Color(4, 110, 114)));
        THEMES.add(new WindowUITheme(
                "METALIC",
                "Metalické",
                "javax.swing.plaf.metal.MetalLookAndFeel",
                "resources/windowui/metalic",
                new Color(120, 207, 250)
        ));
        THEMES.add(new WindowUITheme(
                "NIMBUS",
                "Nimbus",
                "javax.swing.plaf.nimbus.NimbusLookAndFeel",
                "resources/windowui/nimbus",
                new Color(175, 44, 222)
        ));
    }
    
    /**
     * Name of theme
     */
    private final String name;
    
    /**
     * Name of used look and feel
     */
    private final String lookAndFeel;
    
    /**
     * Path to theme resources
     */
    private final String uiPath;
    
    /**
     * Name which will be displayed to user
     */
    private final String displayName;
    
    /**
     * Colour used in tariff wizard
     */
    private final Color tariffWizardColour;
    
    /**
     * Creates new window user interface theme
     * @param n Name of theme
     * @param d Display name of theme for user
     * @param l Name of used look and feel
     * @param u Path to directory with theme resources
     * @param c Colour used in tariff wizard
     */
    public WindowUITheme(String n, String d, String l, String u, Color c)
    {
        this.name = n;
        this.displayName = d;
        this.lookAndFeel = l;
        this.uiPath = u;
        this.tariffWizardColour = c;
    }

    /**
     * Gets name of theme
     * @return Name of theme
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets name of used look and feel
     * @return Name of used look and feel
     */
    public String getLookAndFeel()
    {
        return lookAndFeel;
    }

    /**
     * Gets path to theme resources
     * @return Path to theme resources
     */
    public String getUiPath()
    {
        return uiPath;
    }
    
    /**
     * Gets display name of theme for user
     * @return Display name of theme for user
     */
    public String getDisplayName()
    {
        return this.displayName;
    }
    
    /**
     * Gets colour used in tariff wizard
     * @return Colour used in tariff wizard
     */
    public Color getTariffWizardColour()
    {
        return this.tariffWizardColour;
    }
    
    /**
     * Gets user interface theme by its name
     * @param n Name of user interface theme
     * @return User interface determined by its name 
     *         or <code>NULL</code> if no such theme has been found
     */
    public static WindowUITheme getTheme(String n)
    {
        WindowUITheme reti = null;
        for (WindowUITheme t: WindowUITheme.THEMES)
        {
            if (t.getName().trim().toLowerCase().equals(
                    n.trim().toLowerCase()
            ))
            {
                reti = t;
                break;
            }
        }
        return reti;
    }
}
