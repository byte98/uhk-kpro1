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

import cz.uhk.fim.skodaji1.kpro1.jticket.data.Distances;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Station;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations;
import javax.swing.table.DefaultTableModel;

/**
 * Class representing table model for tariffs table
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class WindowUIPricesTableModel extends DefaultTableModel
{
    /**
     * Creates new table model for tariffs table
     * @param data Array with rows which will be displayed in table
     * @param columns Columns of table
     */
    public WindowUIPricesTableModel (Object[][] data, Object[] columns)
    {
        super(data, columns);
    }
    
    /**
     * Creates new table model for tariffs table
     */
    public WindowUIPricesTableModel()
    {
        super();
    }
    
    @Override
    public boolean isCellEditable(int row, int column)
    {
        return (column > 0);
    }
    
    /**
     * Gets empty data vector prepared for tariffs price table
     * @return Data vector with empty prices
     */
    public static Object[][] getEmptyDataVector()
    {
        Object[][] reti  = new Object[WindowUIPricesTableModel.getMaximalDistance() + 1][];
        for (int d = 0; d <= WindowUIPricesTableModel.getMaximalDistance(); d++)
        {
            reti[d] = new Object[2];
            reti[d][0] = String.format("%d km", d);
            reti[d][1] = 0;
        }
        return reti;
    }
    
    /**
     * Gets empty data vector prepared for tariffs price table
     * @param zones Maximal number of zones
     * @return Data vector with empty prices
     */
    public static Object[][] getEmptyDataVector(int zones)
    {
        Object[][] reti = new Object[zones + 1][];
        for (int i = 0; i <= zones; i++)
        {
            reti[i] = new Object[2];
            reti[i][0] = i;
            reti[i][1] = 0;
        }
        return reti;
    }
    
    /**
     * Gets maximal distance between stations
     * @return Maximal distance between stations in system
     */
    private static int getMaximalDistance()
    {
        int reti = Integer.MIN_VALUE;
        for (Station from: Stations.GetInstance().GetAllStations())
        {
            for (Station to: Stations.GetInstance().GetAllStations())
            {
                if (Distances.GetInstance().GetDistance(from, to) > reti)
                {
                    reti = Distances.GetInstance().GetDistance(from, to);
                }
            }
        }
        return reti;
    }
}
