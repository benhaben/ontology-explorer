# explorer

Provide Restful API about block,transaction,ontid information for the Explorer front-end and DApp.

## DataBase Setup

You should setup mysql cluster

sharding.jdbc.datasources.master.driver-class-name=com.mysql.jdbc.Driver
sharding.jdbc.datasources.master.url=jdbc:mysql://172.168.3.44:3306/explorer?useUnicode=true&characterEncoding=utf8&useSSL=false
sharding.jdbc.datasources.master.username=root
sharding.jdbc.datasources.master.password=explorertest

sharding.jdbc.datasources.slave0.driver-class-name=com.mysql.jdbc.Driver
sharding.jdbc.datasources.slave0.url=jdbc:mysql://172.168.3.45:3306/explorer?useUnicode=true&characterEncoding=utf8&useSSL=false
sharding.jdbc.datasources.slave0.username=root
sharding.jdbc.datasources.slave0.password=explorertest

sharding.jdbc.datasources.slave1.driver-class-name=com.mysql.jdbc.Driver
sharding.jdbc.datasources.slave1.url=jdbc:mysql://172.168.3.46:3306/explorer?useUnicode=true&characterEncoding=utf8&useSSL=false
sharding.jdbc.datasources.slave1.username=root
sharding.jdbc.datasources.slave1.password=explorertest

sharding.jdbc.masterSlaveRule.load-balance-algorithm-type=round_robin
sharding.jdbc.masterSlaveRule.name=ms
sharding.jdbc.masterSlaveRule.master-data-source-name=master
sharding.jdbc.masterSlaveRule.slave-data-source-names=slave0,slave1

sharding.jdbc.config.props.sql.show=true