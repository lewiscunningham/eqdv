package sample

import groovy.beans.Bindable
import ca.odell.glazedlists.EventList
import ca.odell.glazedlists.BasicEventList
import ca.odell.glazedlists.SortedList
import griffon.transform.PropertyListener 
import static griffon.util.GriffonNameUtils.isBlank
import ca.odell.glazedlists.swing.EventComboBoxModel


@Bindable    
@PropertyListener(enabler)                        
class SampleModel {
   EventList peopleList = new SortedList(new BasicEventList(),
     {a, b -> a.item_name <=> b.item_name} as Comparator)

     def event
     def listener = {
          event = it
          println it
        } as PropertyChangeListener


   //@Bindable String title = 'Error'
   //@Bindable String message = 'vdsvdcvdsThis is a message.'
        
/*        
    EventComboBoxModel createComboBoxStatesModel() { 
                   new EventComboBoxModel(model.persons) }        
 
    EventComboBoxModel createComboBoxStatesModel() { 
                   new EventComboBoxModel(model.chars) }        
*/
 
     //@Bindable String server1 = ""
     @Bindable EventList persons = new BasicEventList()
     
//     @Bindable ObservableList persons = ['lewis', 'robert'] as ObservableList
//     @Bindable ObservableList persons = [] //as ObservableList
     
     EventList chars = new BasicEventList()
     
   @Bindable EventList toons = new BasicEventList()
     

     //@Bindable ObservableList comboBoxValues = ['1', '2', '3'] as ObservableList 
     //def createComboBoxStatesModel() { new EventComboBoxModel(model.persons) }

 
//    EventList persons = new SortedList( new BasicEventList(),
//        {a, b -> a.server_name <=> b.server_name} as Comparator)

//    EventList chars = new SortedList( new BasicEventList(),
//        {a, b -> a.server_name <=> b.server_name} as Comparator)

        
//  persons.addPropertyChangeListener(listener)

def abcd = { model.persons.addListEventListener(listener) }

  String serverName = '' 
  String charName  = '' 
  String itemName 
  File file
  String toonName = '' 

  boolean submitEnabled              
  boolean resetEnabled    
  
  private enabler = { e ->                            
    submitEnabled = isBlank(serverName) &&  
                    isBlank(charName) 
 
    resetEnabled = !isBlank(serverName) ||  
                   !isBlank(charName) ||  
                   !isBlank(itemName)    

                  
  }   
     
}


