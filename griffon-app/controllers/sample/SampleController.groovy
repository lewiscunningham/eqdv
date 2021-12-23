package sample

import griffon.transform.Threading 
import javax.swing.JFileChooser 
import griffon.util.ApplicationHolder  
import griffon.core.GriffonApplication  
import java.awt.Window  
 
class SampleController {
   def model
   def view 

   def onShutdownStart = { app ->  
   withSql 
   { dataSourceName, sql ->
     println "Shutting Down Database"
     sql.execute('shutdown')
   }   
   }  
   
   
    @Threading(Threading.Policy.SKIP)                 
    def reset = { 
        println 'Starting debug output'
        println model.serverName
        println model.charName
        println model.serverName
        println model.persons
        println 'Ending debug output'
        model.serverName = '                                                    ' 
        model.charName = '' 
        model.itemName = '' 
        view.c1.selectedIndex = 0 // = '                                                    '
        view.chars.selectedIndex = 0 // = '                                                    '
    } 
 
    def closeFile = { 
      System.exit(0)
   } 

    def addchar = {
        withMVCGroup('addchar') { m, v, c ->
            println "Showing" 
            c.show()
        }
onStartupServer()
onStartupChar()
reset()
view.chars.invalidate()
view.chars.repaint()
    }                
   
    def deletechar = {
        withMVCGroup('deletechar') { m, v, c ->
            println "Showing" 
            c.show()
        }
model.chars.clear()          
//reset()
onStartupServer()
onStartupChar()
reset()
view.chars.revalidate()
view.chars.repaint()
view.chars.invalidate()
//model.chars.clear()          

    }                
   
    def nodeListAction = {
        withMVCGroup('filePanel') { m, v, c ->
            println "Showing" 
            c.show()
        }
    }                
   
    def openFile = {                                                    
//        def openResult = view.fileChooserWindow.showOpenDialog() 
           //Window window = null
//           if( JFileChooser.APPROVE_OPTION == openResult ) {   
//               model.file = view.fileChooserWindow.selectedFile 
//               String mvcId = model.file.path + System.currentTimeMillis()    
//               println model.file    
               nodeListAction()               
        //} 
    } 
 
    def quit = {                                                      
        app.shutdown() 
    } 

   def server 
   def chara
   def itema
   
   def printit = {
     println it.getSelectedItem().toString()
   }
   
   def onStartupEnd = { app ->
      println 'server: ' + model.serverName
      if ((model.serverName == 'DEFAULT' ) || (!model.serverName.trim()))
      {
        server = '%'
      }
      else
      {
        server = model.serverName + '%'
      }
      if ((model.charName == 'DEFAULT') || (!model.charName.trim()))
      {
        chara = '%'
      }
      else
      {
        chara = model.charName + '%'
      }
      if ((model.itemName == 'DEFAULT') || (!model.itemName.trim()))
      {
        itema = '%'
      }
      else
      {
        itema = model.itemName + '%'
      }
      model.peopleList.clear()   
      println server
      withSql { dataSourceName, sql ->
         def tmpList = []
         println server
         sql.eachRow("""
SELECT server_name, 
       CHARACTER_NAME, 
       location_pos,
       item_name || '(' || item_count || ')'  item_name
FROM public.all_data
WHERE SERVER_NAME LIKE $server
  AND character_name LIKE $chara
  AND UPPER(item_name) LIKE UPPER($itema)
order by location_pos 
""") {
            tmpList << [server: it.server_name,
                        name: it.character_name,
                        location: it.location_pos,
                        item: it.item_name]
         }
         println server
         edt { model.peopleList.addAll(tmpList) }
      }
   }



   def onStartupServer = { app ->
      withSql { dataSourceName, sql ->
         println "Start onStartupServer"
         def achar = ""
         model.persons.clear()   
         sql.eachRow("""
SELECT case when server_name = 'DEFAULT' then '                                                    ' ELSE server_name END server_name
FROM public.servers
order by case when server_name = 'DEFAULT' then '                                                    ' ELSE server_name END 
""") {
         println it.server_name
         achar = it.server_name
         //model.persons.add([it.server_name])
         //edt{model.persons.add(it.server_name)}
         //edt{model.persons.add(achar)}
         edt{model.persons.add(achar)}
   }
 }
         println "end onStartupServer"
} 

   def onStartupChar = { app ->
      withSql { dataSourceName, sql ->
         println "Start onStartupChar"
         def achar = ""
         model.chars.clear()   
         sql.eachRow("""
SELECT  case when character_name = 'DEFAULT' then '                                        ' ELSE character_name END character_name
FROM public.characters
order by character_name
""") {
         println it.character_name
         achar = it.character_name
         edt{model.chars.add(achar)}
   }
 }
         println "end onStartupChar"
} 


  def onStartupToons = 
  { app ->
      //println "generating toons"
      model.toons.clear()   
      withSql 
      { dataSourceName, sql ->
         def tmpList = []
         def achar = ""
         sql.eachRow("""
           SELECT case when character_name = 'DEFAULT' then '              ' ELSE server_name || ' - ' || CHARACTER_NAME END toon_name
             FROM public.toons
             order by case when character_name = 'DEFAULT' then '              ' ELSE server_name || ' - ' || CHARACTER_NAME END
         """) 
         {
           println it.toon_name
           achar = it.toon_name
           edt{model.toons.add(achar)}
         }
      }
     //view.c1.selectedIndex = 0 // = '                                                    '

   }
   

}
