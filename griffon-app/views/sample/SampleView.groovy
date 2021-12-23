package sample
import ca.odell.glazedlists.BasicEventList
/*
 optionPane(
    id: 'pane',  
    messageType: JOptionPane.INFORMATION_MESSAGE,  
    optionType: JOptionPane.DEFAULT_OPTION,  
    //icon: nuvolaIcon('core', category: 'apps', size: 64),  
    message: bind {model.message})
    */
actions { 
   action(id: 'openAction', 
      name: 'Open', 
      mnemonic: 'O', 
      accelerator: shortcut('O'),
      closure: controller.openFile) 
   action(id: 'quitAction', 
      name: 'Quit', 
      mnemonic: 'Q', 
      accelerator: shortcut('Q'),
      closure: controller.quit) 
   action(id: 'addChar', 
      name: 'Add Char', 
      mnemonic: 'A', 
      accelerator: shortcut('A'),
      closure: controller.addchar) 
   action(id: 'deleteChar', 
      name: 'Delete Char', 
      mnemonic: 'D', 
      accelerator: shortcut('D'),
      closure: controller.deletechar) 
} 
 

actions {    
   action(id: 'closeAction', 
      name: 'Close', 
      mnemonic: 'C', 
      accelerator: shortcut('C'), 
      closure: controller.closeFile) 
}

actions {    
   action(id: 'submitAction', 
      name: 'Search', 
      mnemonic: 'S', 
      accelerator: shortcut('S'), 
      closure: controller.onStartupEnd) 
}

application(title: 'EQ Data Vault',
  size: [1000, 768],
  locationByPlatform: true,
  iconImage: imageIcon('/eqdv_48x48.png').image,
  iconImages: [imageIcon('/eqdv_48x48.png').image,
               imageIcon('/eqdv_32x32.png').image,
               imageIcon('/eqdv_16x16.png').image]) {

//controller.reset()
controller.onStartupServer()
controller.onStartupChar()

   menuBar { 
      menu('File') { 
         menuItem openAction                                          
         separator() 
         menuItem quitAction                                          
      } 
      menu('Edit') { 
         menuItem addChar                                          
         separator() 
         menuItem deleteChar                                          
      } 
   } 
               
borderLayout() 
  panel(
         id: 'mainPanel', constraints: NORTH,   border: emptyBorder(6)
       ) { 
    gridLayout(rows:3, columns:2, hgap:6, vgap:6) 
    label 'Server Name:' 
    comboBox(id: 'c1', items: model.persons, actionPerformed: {
                                                    //println c1.selectedItem
                                                    //println model.persons 
                                                    model.serverName = c1.selectedItem
                                                    }) 
                 
    label 'Char Name:' 
//    comboBox(id: 'chars', items: model.chars, actionPerformed: {
//                                                    model.charName = chars.selectedItem
//                                                    }) 
    comboBox(id: 'chars', model: eventComboBoxModel(source: model.chars), actionPerformed: {
                                                    //println c1.selectedItem
                                                    //println model.persons 
                                                    model.charName = chars.selectedItem
                                                    }) 
    label 'Item Name:' 
    textField columns:20,  
    text: bind(target: model, 'itemName', mutual: true)    
  }
  panel(constraints: CENTER) {  
    gridLayout(rows:1, cols: 2, hgap:1, vgap:1) 
    button('Reset', actionPerformed: controller.reset,    
           )             
    button('Search', actionPerformed: controller.onStartupEnd, 
//    button('Submit', actionPerformed: controller.onStartupServer, 
           )             
  } 
  panel(id: 'panelt', constraints: SOUTH) { 
    scrollPane {
        table(id: 'peopleTable') { 
           tableFormat = defaultTableFormat(columnNames: ['Server', 'Name', 'Location', 'Item'])
            eventTableModel(source: model.peopleList, format: tableFormat)
            installTableComparatorChooser(source: model.peopleList)
        }
    Dimension size = peopleTable.getPreferredScrollableViewportSize()
    println 'Dimensions'
    println size.width
    println size.height
    
    Double theWidth = 650.0
//    peopleTable.setPreferredScrollableViewportSize(new Dimension(Math.min(peopleTable.getPreferredSize().width, size.width), size.height))
//    peopleTable.setPreferredScrollableViewportSize(new Dimension(theWidth, size.width), size.height)
//    peopleTable.setPreferredScrollableViewportSize(new Dimension(theWidth, size.height))
    peopleTable.setPreferredScrollableViewportSize(new Dimension(950, 530))
    peopleTable.getColumnModel().getColumn(0).setPreferredWidth(100);    
    peopleTable.getColumnModel().getColumn(1).setPreferredWidth(150);    
    peopleTable.getColumnModel().getColumn(2).setPreferredWidth(250);    
    peopleTable.getColumnModel().getColumn(3).setPreferredWidth(500);    
    }
  }
}




