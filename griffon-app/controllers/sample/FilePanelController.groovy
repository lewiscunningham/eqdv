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


class FilePanelController { 
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
      println "Executing MVC"
      //app.models["sample"].onStartupToons()
      def updateMasterPanel = { cls ->
           cls.delegate = view.pane
           view.pane.with {
               cls()
               revalidate()
               repaint()
           }
        }      
 } 
 

     def openFile = {                                                    
        def openResult = view.fileChooserWindow.showOpenDialog() 
           //Window window = null
           if( JFileChooser.APPROVE_OPTION == openResult ) {   
               view.text1.text = view.fileChooserWindow.selectedFile 
           } 
    } 

   def saveFile = { 
      model.loadedFile = view.text1.text
      println "File: " + model.loadedFile
      println "Before load_file"
      load_file()
      println "After load_file"
      closeFile()
      //doOutside {                                                      
      //   model.loadedFile.text = view.editor.text                     
      //   doLater { model.fileText = view.editor.text }                 
      //} 
   } 
 
   def closeFile = { 
      //view.tabGroup.remove(view.tab)  
      destroyMVCGroup(model.mvcId)   
   } 
   
  
  def load_file = 
  {
    
    println "Loading file"
    def file = new File(model.loadedFile)
    def cnt = 0
    def loc_cnt = 0
    def dummy = ""
    def locname = ""
    def Integer bin
    def Integer container
    def Integer slot
//    def bin = 0
//    def container = 0
// def slot = 0
    def itemcount = 0
    def itemid = 0
    def itemname = ""
    def slots = ""
    def locid = 0
    def v_rec = 0
    def strarr
    def namearr
    def serverrow
    def charrow
    def isg = 0

    try
    { 
      //throw new Exception() 
        withSql 
        { dataSourceName, sql ->

            println "NA:" + app.models["sample"].toonName
            namearr = app.models["sample"].toonName.split('-')
            println "NA:" + namearr
            model.serverName = namearr[0].trim()
            model.charName = namearr[1].trim()
            println "server_name: " + model.serverName
            serverrow = sql.firstRow('select server_id from servers where server_name = ?', [model.serverName])
            println "server_id: " + serverrow.server_id
            println "CharName: " +  model.charName
            charrow = sql.firstRow('select character_id, guild_id from characters where server_id = ? and character_name = ?', [serverrow.server_id, model.charName])
            sql.execute('delete from inventory_locations where server_id = ? and character_id = ?', [serverrow.server_id, charrow.character_id])
            isg = charrow.guild_id

            println "Loading file"
            file.eachLine 
           { line ->
                println "Line: " + line
                if (v_rec > 0)
                {
                    cnt = 0
                    strarr = line.split("\t")
                    strarr.each 
                    {   arrstr -> 
                        loc_cnt = 0
                        if (cnt == 0) 
                        { 
                            arrstr =  arrstr.replaceAll("Bank", "Bank-")
                            arrstr =  arrstr.replaceAll("Slot", "Slot-")
                            arrstr =  arrstr.replaceAll("General", "General-")
                            arrstr.split("-").each 
                            { loc ->
                                println "Loc=" + loc + ", loc_cnt=" + loc_cnt
                                switch(loc_cnt) 
                                {
                                    case 0: locname = loc
                                            break
                                    case 1: if (loc.isNumber()) {bin = Integer.parseInt(loc)}
                                            break
                                    case 2: if (loc.isNumber()) {bin = Integer.parseInt(loc)}
                                            break
                                    case 3: if (loc.isNumber()) {container = Integer.parseInt(loc)}
                                            break
                                    case 4: dummy = loc
                                            break
                                    case 5: if (loc.isNumber()) {slot = Integer.parseInt(loc)}
                                            break
                                    default: dummy = loc
                                } 
           
                                loc_cnt++
                            }
                        }
                        else 
                        {
                            println "Cnt=" + cnt + ", arrstr=" + arrstr
                            switch(cnt) 
                            {
                                case 1: itemname = arrstr
                                        break
                                case 2: itemid = arrstr
                                        break
                                case 3: itemcount = arrstr
                                        break
                                case 4: slots = arrstr
                                        break
                                default: dummy = ""
                            }
          
                        }

                        cnt++
                    }  

                    if (itemid != '0')
                    {

                        println "Locname = $locname, bin = $bin, container = $container, slot = $slot, count=$itemcount, slots=$slots, itemid = $itemid, item_name = $itemname" 

                        if (isg > 0)
                        {
                            slots = 0
                        }
       
                        println "CharID: " + charrow.character_id
                        sql.execute("""
                           insert into INVENTORY_LOCATIONS 
                           (server_id, character_id, location_id, location_name, bin_num, container_num, slot_num, item_count, slots, item_id, item_name)
                            values (?, ?, next value for loc_ids, ?, ?, ?, ?, ?, ?, ?, ?)
                         """,      //"
                        [serverrow.server_id, charrow.character_id, locname, bin, container, slot, itemcount, slots, itemid, itemname])
                    }
     
                }
                v_rec++ 
            }

        }   
    }    
    catch(x) 
    {
        withMVCGroup('dialog') 
        { m, v, c ->  
            m.title = 'Data Loading Error!'  
            m.message = """ 
              Oops! We got an error. The problem was caused by a 
   
              $x 
 
              Maybe you loaded an invalid file? The file you tried 
              to load  was $model.loadedFile
            """.stripIndent(12)  
            c.show()  
        }  
    } 
  }    
} 



