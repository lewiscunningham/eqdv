package sample
import java.awt.Window  
import groovy.swing.SwingBuilder
//import griffon.transform.Threading.Policy
import griffon.transform.Threading 
import java.awt.FlowLayout
import java.awt.GridBagLayout
import java.awt.*
import javax.swing.*
import javax.swing.table.*
import groovy.sql.Sql

class DialogController {  
    def model  
    def view  
  
    @Threading(Threading.Policy.INSIDE_UITHREAD_SYNC)  
    def show = { Window window = null ->  
        view.pane.createDialog(  
            window ?: Window.windows.find{it.focused},  
            model.title).show()  
    }  
}  