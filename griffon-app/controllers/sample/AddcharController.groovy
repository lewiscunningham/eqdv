package sample

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
import java.awt.*
import javax.swing.*
import javax.swing.table.*
import groovy.sql.Sql


class AddcharController {
   def model                                                          
   def view       
   def builder

   protected dialog
   Window window = null
   //extrastuff ex = null
   
    @Threading(Threading.Policy.INSIDE_UITHREAD_SYNC)
    void show(Window window) {
        window = window ?: Window.windows.find{it.focused}
        if(!dialog || dialog.owner != window) {
            app.windowManager.hide(dialog)
            dialog = builder.dialog(
                owner: window,
                title: model.title,
                resizable: model.resizable,
                modal: model.modal) {
                container(view.content)
            }
            if(model.width > 0 && model.height > 0) {
                dialog.preferredSize = [model.width, model.height]
            }
            dialog.pack()
        }
        int x = window.x + (window.width - dialog.width) / 2
        int y = window.y + (window.height - dialog.height) / 2
        dialog.setLocation(x, y)
        app.windowManager.show(dialog)
    }

  
   void mvcGroupInit(Map args) { 
      //model.loadedFile = args.filename 
      model.mvcId = args.mvcId 
      println "File: " + model.loadedFile
      def updateMasterPanel = { cls ->
           cls.delegate = view.pane
           view.pane.with {
               cls()
               revalidate()
               repaint()
           }
        }
 } 
 

   def insertChar = {
      println "insertChar"
    
      def isg = 0
      println "insertChar1: " + view.text3.selected
      if (view.text3.selected) 
      {
      println "insertChar2"
        isg = 1
      println "insertChar3"
      }
      println "insertChar4"
      withSql { dataSourceName, sql ->
      println "insertChar5"
         println model.serverName
      println "insertChar6"
         def row
         row = sql.firstRow('select server_id from servers where server_name = ?', [model.serverName])
         println view.text1.text
         sql.execute('insert into characters (character_name, server_id, character_id, guild_id, race_id, class_id) values (?, ?, next value for  char_ids, ?,1,1)',
             [view.text1.text, row.server_id, isg])
         }
     }
     
   def saveFile = { 
      insertChar()
      //doOutside {                                                      
      //   model.loadedFile.text = view.editor.text                     
      //   doLater { model.fileText = view.editor.text }                 
      //} 
      destroyMVCGroup(model.mvcId)   
   } 
 
   def closeFile = { 
      //view.tabGroup.remove(view.tab)  
      destroyMVCGroup(model.mvcId)   
   } 
   
   def parse_dir = { str ->
     str.split("\\")
    }
} 