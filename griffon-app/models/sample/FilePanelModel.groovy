package sample

import groovy.beans.Bindable
import ca.odell.glazedlists.EventList
import ca.odell.glazedlists.BasicEventList
import ca.odell.glazedlists.SortedList
import griffon.transform.PropertyListener 
import static griffon.util.GriffonNameUtils.isBlank
import ca.odell.glazedlists.swing.EventComboBoxModel


class FilePanelModel { 
   //@Bindable File loadedFile = new File('c:\\temp\\mrbackpackinv.csv') 
   @Bindable String loadedFile 
   @Bindable String fileText
   @Bindable String title = 'Load an Inventory File'
   @Bindable String message = 'This is a message.'
   @Bindable boolean dirty 
   String mvcId 
   String serverName = '' 
//   String toonName = '' 
   String charName  = '' 
   @Bindable int width = 400
   @Bindable int height = 165
   @Bindable boolean resizable = false
   @Bindable boolean modal = true   
   
   //@Bindable EventList persons = new BasicEventList()
     
     
   //EventList chars = new BasicEventList()

//   @Bindable EventList toons = new BasicEventList()

   
} 