
mybaits 自定义数据权限demo
scope：需要用使用的数据权限
alias：对应字段的表的别名
columns：需要作用的字段
scopePlateId：作用在什么系统上，比如只在运营系统使用，但是供应商系统不需要加数据权限，则可以传一个运营系统的系统id，如果指定了系统，并且系统id不等于当前登录人的系统id则数据权限不生效
@DataScope(scope = {DataScopeEnum.REGION, DataScopeEnum.BUSINESS}, alias = {"cs", "cs"},columns = {"region_code", "business_type"},scopePlateId = "1")
