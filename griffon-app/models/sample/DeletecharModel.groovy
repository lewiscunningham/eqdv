package sample

import groovy.beans.Bindable
import ca.odell.glazedlists.EventList
import ca.odell.glazedlists.BasicEventList
import ca.odell.glazedlists.SortedList
import griffon.transform.PropertyListener 
import static griffon.util.GriffonNameUtils.isBlank
import ca.odell.glazedlists.swing.EventComboBoxModel


class DeletecharModel {
   // @Bindable String propName
   
   
  @Bindable File loadedFile 
   @Bindable String fileText
   @Bindable String title = 'Delete a Character'
   @Bindable String message = 'This is a message.'
   @Bindable boolean dirty 
   String mvcId 
   String serverName = '' 
   String charName  = '' 
   @Bindable int width = 400
   @Bindable int height = 165
   @Bindable boolean resizable = false
   @Bindable boolean modal = true   
   
   @Bindable EventList persons = new BasicEventList()
     
     
   EventList chars = new BasicEventList()
   
   
   }