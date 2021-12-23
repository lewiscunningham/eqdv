package sample

import groovy.swing.SwingBuilder
import java.awt.FlowLayout
import java.awt.GridBagLayout
import java.awt.*
import javax.swing.*
import javax.swing.table.*
import groovy.sql.Sql


// Include core griffon scripts with the following call
//
//     includeTargets << griffonScript('_GriffonCompile)
//
// Include plugin scripts with the following call
//
//    includeTargets << griffonPluginScript('some-plugin-name', 'ScriptName')
//
class extrastuff { 

def doit = {
db = Sql.newInstance(
    'jdbc:oracle:thin:@//localhost:1521/orclpe',
    'eqdv',
    'eqdv', 
    'oracle.jdbc.OracleDriver')    

//Server = db.dataSet('SERVERS')


allData = db.dataSet('ALL_DATA')

/*
class MyTableCellRenderer extends JLabel
implements TableCellRenderer {
    public Component getTableCellRendererComponent(
                        JTable table, Object value,
                        boolean isSelected, boolean hasFocus,
                        int rowIndex, int vColIndex) {
        
        return this
    }
}
*/

Object data = new String[100000][7]
String[] columns = ["Server Name", "Character Name", "Item Name", "Location", "Bin#", "Container#", "Slot#"]


def results = db.firstRow("select server_id, server_name from servers where server_id=1")
def serverName = results.server_name
def serverID = results.server_id


def cnt = 0


cnt = 0
Object ServerNames = new String[100]
Object ServerIDs = new Integer[100]

Object Serves = new String[100]

db.eachRow("select server_id, server_name from servers") 
{ data2 ->
    println data2
    println data2[1]
    ServerIDs[cnt] = data2[0]
    ServerNames[cnt] = data2[1]
//    Servers[cnt] = data2
//    Servers[cnt][1] = data2["SERVER_NAME"]
//    println Servers
//    println ServerNames[cnt]
  cnt++

}
  
results = db.firstRow("select character_id, character_name from characters where character_id=1 and server_id = $serverID")
def characterName = results.character_name
def characterID = results.character_id

cnt = 0
//def dataresults = 

db.eachRow("select Server_Name, Character_Name, Item_Name, Location_name, to_char(nvl(Bin#,0)) bin#, to_char(nvl(Container#,0)) container#, to_char(nvl(Slot#,0)) slot# from all_data where server_id=1") 
{  row ->
    if (cnt < 100000)
    {
    //println row
    data[cnt][0] = row["SERVER_NAME"]
    data[cnt][1] = row["CHARACTER_NAME"]
    data[cnt][2] = row["ITEM_NAME"]
    data[cnt][3] = row["LOCATION_NAME"]
    data[cnt][4] = row["BIN#"]
    data[cnt][5] = row["CONTAINER#"]
    data[cnt][6] = row["SLOT#"]
    }
    cnt++
}

JTable table = new JTable(data, columns)
JScrollPane scrollPane = new JScrollPane(table)


JTextField serverField = new  JTextField(text: serverName, columns:30)
JTextField charField = new  JTextField(text: characterName, columns:30)


swing = new SwingBuilder()
gui = swing.frame(title:'EQ Data Vault', size:[700,600]) {
  panel(layout:new FlowLayout()) {
        panel(layout:new FlowLayout(),
           constraints: BorderLayout.NORTH) {
//         panel(layout:new FlowLayout()) {
//           label(text: "Server: ")
 //          }
 //        panel(layout:new FlowLayout()) {
 //              widget(serverField) 
 //          }
         panel(layout:new FlowLayout()) {
           label(text: "Server: ")
           }
        panel(layout:new FlowLayout()) {
            comboBox(items:ServerNames,
                     selectedIndex:0);
        }            
         panel(layout:new FlowLayout()) {
               widget(charField) 
           }
        }        
        panel( layout: new BorderLayout(),
           constraints: BorderLayout.CENTER ) {
               widget(scrollPane)  
        }           
        panel(layout:new BorderLayout(),
           constraints: BorderLayout.SOUTH) {
           button(action: action(name: "ok", closure: {println("duh")}))
        }
  }        
}
gui.show()    
}

}



