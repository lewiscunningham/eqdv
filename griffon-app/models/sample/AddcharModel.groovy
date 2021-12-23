package sample

import groovy.beans.Bindable
import ca.odell.glazedlists.EventList
import ca.odell.glazedlists.BasicEventList
import ca.odell.glazedlists.SortedList
import griffon.transform.PropertyListener 
import static griffon.util.GriffonNameUtils.isBlank
import ca.odell.glazedlists.swing.EventComboBoxModel


class AddcharModel { 
   //@Bindable File loadedFile = new File('c:\\temp\\mrbackpackinv.csv') 
   @Bindable File loadedFile 
   @Bindable String fileText
   @Bindable String title = 'Add a character'
   @Bindable String message = 'This is a message.'
   @Bindable boolean dirty 
   String mvcId 
   String serverName = 'DEFAULT' 
   String charName  = '' 
   Boolean isGuild  = false
   @Bindable int width = 400
   @Bindable int height = 150
   @Bindable boolean resizable = false
   @Bindable boolean modal = true   
   
   @Bindable EventList persons = new BasicEventList()
     
     
   EventList chars = new BasicEventList()

   
} 