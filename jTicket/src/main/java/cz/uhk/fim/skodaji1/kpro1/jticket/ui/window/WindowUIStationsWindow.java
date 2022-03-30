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
package cz.uhk.fim.skodaji1.kpro1.jticket.ui.window;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Station;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations;
import java.awt.Color;
import javax.swing.JPanel;

/**
 * Class representing window with stations
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class WindowUIStationsWindow extends JDialog
{
    
    /**
     * Viewer of data
     */
    private JTable dataView;
    
    /**
     * Container for make table scrollable
     */
    private JScrollPane scrollPane;
    
    /**
     * Content of scroll pane
     */
    private JPanel scrollContent;
 
    /**
     * Creates new window with stations
     */
    public WindowUIStationsWindow()
    {
        this.setTitle("jTicket - Stanice");
        this.setIconImage(new ImageIcon(WindowUI.PATH + "/dataitem.png").getImage());
        this.setModal(true);
        this.setLayout(new BorderLayout());
        this.scrollPane = new JScrollPane();
        this.scrollPane.setBackground(Color.red);
        this.scrollContent = new JPanel(new BorderLayout());
        this.scrollPane.add(this.scrollContent);
        this.getContentPane().add(this.scrollPane, BorderLayout.CENTER);
        this.setData(Stations.GetInstance().GetAllStations());
    }
    
    /**
     * Sets data which will be displayed in table
     * @param stations Array with stations which will be displayed
     */
    public void setData(Station[] stations)
    {
        String[] cols = {"ZKRATKA", "JMÉNO"};
        this.dataView = new JTable(this.prepareData(stations), cols);
        this.scrollContent.add(this.dataView, BorderLayout.CENTER);
    }
    
    /**
     * Prepares array of strings from data
     * @param stations Stations which will be transformed into array of strings
     * @return Array of strings created from data
     */
    private String[][] prepareData(Station[] stations)
    {
        String[][] reti = new String[stations.length][2];
        int i = 0;
        for(Station s: stations)
        {
            reti[i][0] = s.getAbbrevation();
            reti[i][1] = s.getName();
            System.out.println(s.getName());
            i++;
        }
        return reti;
    }
}
