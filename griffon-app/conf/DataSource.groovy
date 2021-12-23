dataSource {
    driverClassName = 'org.hsqldb.jdbcDriver'
    username = 'SA'
    password = ''
    tokenizeddl = false // set this to true if using MySQL or any other
                        // RDBMS that requires execution of DDL statements
                        // on separate calls
    pool {
        maxWait = 60000
        maxIdle = 5
        maxActive = 8
    }
}
environments {
    development {
        dataSource {
            dbCreate = 'skip' // one of ['create', 'skip']
            url = 'jdbc:hsqldb:file:data/eqdv'
            println "DEV JSBC URL: " + url
        }
    }
    test {
        dataSource {
            dbCreate = 'skip' // one of ['create', 'skip']
            url = 'jdbc:hsqldb:file:data/eqdv'
            println "Test JSBC URL: " + url
        }
    }
    production {
        dataSource {
            dbCreate = 'skip' // one of ['create', 'skip']
            url = 'jdbc:hsqldb:file:data/eqdv'
            println "Prod JSBC URL: " + url
        }
    }
}

    