/*
 * Copyright (C) 2022 Jiri Skoda <skodaji1@uhk.cz>
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
package cz.uhk.fim.skodaji1.kpro1.jticket.ui.window.tables;

import cz.uhk.fim.skodaji1.kpro1.jticket.data.Station;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations;
import javax.swing.table.DefaultTableModel;

/**
 * Class representing table model for zones table
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class WindowUIZonesTableModel extends DefaultTableModel
{
    /**
     * Creates new table model for zones table
     * @param data Array with rows which will be displayed in table
     * @param columns Columns of table
     */
    public WindowUIZonesTableModel (Object[][] data, Object[] columns)
    {
        super(data, columns);
    }
    
    /**
     * Creates new table model for zones table
     */
    public WindowUIZonesTableModel()
    {
        super();
    }
    
    @Override
    public boolean isCellEditable(int row, int column)
    {
        return (column > 1);
    }
    
    /**
     * Gets prepared empty data vector
     * @return Empty data vector
     */
    public static Object[][] getEmptyDataVector()
    {
        Station[] stations = Stations.GetInstance().GetAllStations();
        Object[][] reti = new Object[stations.length][];
        for (int i = 0; i < stations.length; i++)
        {
            reti[i] = new Object[3];
            reti[i][0] = stations[i].getAbbrevation();
            reti[i][1] = stations[i].getName();
            reti[i][2] = 0;
        }
        return reti;
    }
}
