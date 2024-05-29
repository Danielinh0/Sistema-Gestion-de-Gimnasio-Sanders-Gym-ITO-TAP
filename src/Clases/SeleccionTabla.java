/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 *
 * @author Daniel Eduardo y su intimo amigo Luis
 */

public class SeleccionTabla extends DefaultTableCellRenderer{
    
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value == null || value.toString().isEmpty()) {
            setBackground(Color.white);
        } else {
            setBackground(new Color(122,72,221));
        }

        return this;
    }
    
    

    
}
