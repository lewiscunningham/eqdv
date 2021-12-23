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



class DeletecharController {
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
 

   def deleteChar = {
      println "insertChar"
    
      def isg = 0
      println "insertChar1"
      withSql { dataSourceName, sql ->
         println 'select server_id from servers where server_name = ?  - ' + model.serverName
         def row
         row = sql.firstRow('select server_id from servers where server_name = ?', [model.serverName])
         println 'delete from characters where  server_id = ? and character_name = ?  - ' + row.server_id + '  ' +  model.charName
         def charrow
         charrow = sql.firstRow('select character_id from characters where server_id = ? and character_name = ?', [row.server_id, model.charName])
         sql.execute('delete from inventory_locations where  server_id = ? and character_id = ?', [row.server_id, charrow.character_id])
         sql.execute('delete from characters where  server_id = ? and character_id = ?', [row.server_id, charrow.character_id])
         }
     }
     
   def saveFile = { 
   
    def namearr = app.models["sample"].toonName.split('-')
    println "NA:" + namearr
    model.serverName = namearr[0].trim()
    model.charName = namearr[1].trim()
   
     if (!model.serverName.trim())
     {
       model.serverName = 'DEFAULT'
     }
     if (model.charName != 'DEFAULT' && model.charName.trim())
     {
        deleteChar()
        destroyMVCGroup(model.mvcId)   
     }        
   } 
 
   def closeFile = { 
      //view.tabGroup.remove(view.tab)  
      destroyMVCGroup(model.mvcId)   
   } 
   
}

