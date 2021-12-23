package sample

import groovy.beans.Bindable
import ca.odell.glazedlists.EventList
import ca.odell.glazedlists.BasicEventList
import ca.odell.glazedlists.SortedList
import griffon.transform.PropertyListener 
import static griffon.util.GriffonNameUtils.isBlank
import ca.odell.glazedlists.swing.EventComboBoxModel

import java.awt.Window  
import griffon.transform.Threading 
import org.apache.commons.io.FilenameUtils 
import groovy.swing.SwingBuilder
import java.awt.FlowLayout
import java.awt.GridBagLayout
import javax.swing.BoxLayout
import java.awt.*
import javax.swing.*
import javax.swing.table.*
import groovy.sql.Sql

actions {    
    action(id: 'saveAction', 
        enabled: bind {model.dirty}, 
        name: 'Save', 
        mnemonic: 'S', 
        accelerator: shortcut('S'), 
        closure: controller.saveFile) 
    action(id: 'closeAction', 
        name: 'Close', 
        mnemonic: 'C', 
        accelerator: shortcut('C'), 
        closure: controller.closeFile) 
}

fileChooserWindow = fileChooser() 

      app.controllers["sample"].onStartupToons()

           //controller.onStartupToons()

panel(
    id: 'content'  
) { 
    boxLayout(axis: BoxLayout.PAGE_AXIS) 
    /*
    panel() {
        boxLayout(axis: BoxLayout.PAGE_AXIS) 
        hbox() { 
        label( id: 'label1', text: 'Server Name:') 
        comboBox(id: 'c1', items: app.models['sample'].persons, preferredSize:[100,100], actionPerformed: {
                model.serverName = c1.selectedItem
            }) 
        }
    }                 
    panel() {
        boxLayout(axis: BoxLayout.PAGE_AXIS) 
        hbox() { 
            label( id: 'label2', text: 'Char Name:' )
            comboBox(id: 'chars', items: app.models['sample'].chars, preferredSize:[100,100], actionPerformed: {
                    //println c1.selectedItem
                    //println model.persons 
                    model.charName = chars.selectedItem
                }) 
        }
    }
    */
    panel() {
        boxLayout(axis: BoxLayout.PAGE_AXIS) 
        hbox(maximumSize: [350, 30]) { 
        label( id: 'label1', text: 'Toon Name:') 
        comboBox(id: 'c1', items: app.models["sample"].toons, preferredSize:[350, 30], actionPerformed: {
                app.models["sample"].toonName = c1.selectedItem
            }) 
        }
    }                 
    panel() {
        boxLayout(axis: BoxLayout.PAGE_AXIS) 
        hbox(maximumSize: [350, 30]) { 
            label( id: 'label3', text: 'File Name:')
            textField( id: 'text1',columns:20, preferredSize:[350, 30]  )
            button('V', actionPerformed: controller.openFile ) 
        }
    }
    panel() {
        boxLayout(axis: BoxLayout.LINE_AXIS) 
        hbox() { 
            button saveAction
            button closeAction
        } 
    } 
}

bean(model, dirty: bind {view.text1.text}) 
