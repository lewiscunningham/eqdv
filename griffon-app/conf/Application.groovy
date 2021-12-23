application {
    title = 'Sample'
    startupGroups = ['sample']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "dialog"
    'dialog' {
        model      = 'sample.DialogModel'
        view       = 'sample.DialogView'
        controller = 'sample.DialogController'
    }

    // MVC Group for "deletechar"
    'deletechar' {
        model      = 'sample.DeletecharModel'
        view       = 'sample.DeletecharView'
        controller = 'sample.DeletecharController'
    }

    // MVC Group for "addchar"
    'addchar' {
        model      = 'sample.AddcharModel'
        view       = 'sample.AddcharView'
        controller = 'sample.AddcharController'
    }

     //MVC Group for "filePanel"
    'filePanel' {
        model      = 'sample.FilePanelModel'
        view       = 'sample.FilePanelView'
        controller = 'sample.FilePanelController'
    }

    // MVC Group for "sample"
    'sample' {
        model      = 'sample.SampleModel'
        view       = 'sample.SampleView'
        controller = 'sample.SampleController'
    }

}
