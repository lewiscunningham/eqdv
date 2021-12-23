package sample

optionPane(  
    id: 'pane',  
    messageType: JOptionPane.ERROR_MESSAGE,  
    optionType: JOptionPane.DEFAULT_OPTION,  
    //icon: nuvolaIcon('core', category: 'apps', size: 64),  
    message: bind {model.message}) 