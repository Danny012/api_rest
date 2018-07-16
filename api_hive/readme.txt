跑通本测试用例需要您做如下三件事：

1、到需要测试的大数据集群，缓存最新票据

2、将1中所在集群的 /etc下的krb5.conf 和 /etc/security/keytabs下的hive.service.keytab放
到本项目中

3、配置jdbc url,principal,keytab,krb5

4、客户端时间必须保证和集群时间一致

5、如果以上四条 都操作了，但是还不能正确执行，请联系相关人员
