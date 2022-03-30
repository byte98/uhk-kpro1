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

import cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

/**
 * Class representing main window of program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class WindowUIMainWindow extends JFrame
{
    /**
     * Top menu bar
     */
    private JMenuBar topMenuBar;
    
    /**
     * File menu in top menu bar
     */
    private JMenu fileMenu;
    
    /**
     * Item in menu which closes program
     */
    private JMenuItem itemClose;
    
    /**
     * Item in menu which opens settings window
     */
    private JMenuItem itemSettings;
    
    /**
     * Data menu in top menu bar
     */
    private JMenu dataMenu;
    
    /**
     * Reference to main window of program
     */
    private JFrame instance;
    
    /**
     * Item in menu which opens stations settings window
     */
    private JMenuItem itemStations;
    
    /**
     * Item in menu which opens distances settings window
     */
    private JMenuItem itemDistances;
    
    /**
     * Item in menu which opens tariffs settings window
     */
    private JMenuItem itemTariffs;
    
    /**
     * Window with stations
     */
    private WindowUIStationsWindow stationsWindow;
 
    /**
     * Creates new main window of program
     */
    public WindowUIMainWindow()
    {
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("jTicket");
        this.stationsWindow = new WindowUIStationsWindow();
        this.prepareTopMenu();
        this.instance = this;
    }
    
    /**
     * Prepares top menu of program
     */
    private void prepareTopMenu()
    {
        this.topMenuBar = new JMenuBar();
        
        // File menu
        this.fileMenu = new JMenu();
        this.fileMenu.setText("Soubor");
        this.fileMenu.setIcon(new ImageIcon(WindowUI.PATH + "/file.png"));
        
        // Settings of program
        this.itemSettings = new JMenuItem();
        this.itemSettings.setText("Nastavení");
        this.itemSettings.setIcon(new ImageIcon(WindowUI.PATH + "/settings.png"));
        this.fileMenu.add(this.itemSettings);
        
        // Close program
        this.itemClose = new JMenuItem();
        this.itemClose.setText("Zavřít");        
        this.itemClose.setIcon(new ImageIcon(WindowUI.PATH + "/close.png"));
        this.itemClose.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispatchEvent(new WindowEvent(instance, WindowEvent.WINDOW_CLOSING));
            }
        });
        this.fileMenu.add(this.itemClose);
        
        // Data menu
        this.dataMenu = new JMenu();
        this.dataMenu.setText("Data");
        this.dataMenu.setIcon(new ImageIcon(WindowUI.PATH + "/data.png"));
        
        // Stations menu item
        this.itemStations = new JMenuItem();
        this.itemStations.setText("Stanice");
        this.itemStations.setIcon(new ImageIcon(WindowUI.PATH + "/dataitem.png"));
        this.itemStations.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                stationsWindow.setData(Stations.GetInstance().GetAllStations());
                stationsWindow.setVisible(true);
            }
        });
        this.dataMenu.add(this.itemStations);
        
        // Distances menu item
        this.itemDistances = new JMenuItem();
        this.itemDistances.setText("Vzdálenosti");
        this.itemDistances.setIcon(new ImageIcon(WindowUI.PATH + "/dataitem.png"));
        this.dataMenu.add(this.itemDistances);
        
        // Tariffs menu item
        this.itemTariffs = new JMenuItem();
        this.itemTariffs.setText("Tarify");
        this.itemTariffs.setIcon(new ImageIcon(WindowUI.PATH + "/dataitem.png"));
        this.dataMenu.add(this.itemTariffs);
        
        this.topMenuBar.add(this.fileMenu);
        this.topMenuBar.add(this.dataMenu);
        this.getContentPane().add(this.topMenuBar, BorderLayout.NORTH);
    }
}
