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

import javax.swing.table.DefaultTableModel;

/**
 * Class representing table model for distances table
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class WindowUIDistancesTableModel extends DefaultTableModel
{
    /**
     * Creates new table model for distances table
     * @param data Array with rows which will be displayed in table
     * @param columns Columns of table
     */
    public WindowUIDistancesTableModel (Object[][] data, Object[] columns)
    {
        super(data, columns);
    }
    
    /**
     * Creates new table model for stations table
     */
    public WindowUIDistancesTableModel()
    {
        super();
    }
    
    @Override
    public boolean isCellEditable(int row, int column)
    {
        return (column > 0 && (column - 1) != row);
    }
    
}
