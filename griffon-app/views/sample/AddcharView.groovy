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

panel(
    id: 'content'  
) { 
    boxLayout(axis: BoxLayout.PAGE_AXIS) 
    
    panel() {
        boxLayout(axis: BoxLayout.PAGE_AXIS) 
        hbox(maximumSize: [350, 30] ) { 
        label( id: 'label1', text: 'Server Name:') 
        comboBox(id: 'c1', items: app.models['sample'].persons, preferredSize:[350,30], actionPerformed: {
                model.serverName = c1.selectedItem
            }) 
            }
    }                 
    panel() {
        boxLayout(axis: BoxLayout.PAGE_AXIS) 
        hbox(maximumSize: [350, 30]) { 
            label( id: 'label2', text: 'Char Name:' )
            textField( id: 'text1', columns:20, preferredSize:[350,30]  )
            }
    }
    panel(maximumSize: [350, 30]) {
        boxLayout(axis: BoxLayout.PAGE_AXIS) 
        hbox() { 
            //label( id: 'label3', text: 'Is a guild Name:' )
            checkBox( id: 'text3', label: 'This is a guild', selected: model.isGuild, actionCommand: { println model.isGuild } )
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
