# 自定义mybatis数据权限

将用户的数据权限统一格式放入redis中
**DataScopeEnum**枚举是配置需要增加数据权限的字段和描述\
增加数据权限时在sqlapply.impl包中新增并实现PermissionStrategy，以及对应的方法逻辑即可

**使用方式直接在需要调用数据权限的地方增加注解即可** \
@DataScope(scope = {DataScopeEnum.REGION, DataScopeEnum.BUSINESS}, alias = {"cs", "cs"},columns = {"region_code", "business_type"},scopePlateId = "1")
1. **scope**：需要用使用的数据权限
2. **alias**：对应字段的表的别名
3. **columns**：需要作用的字段
4. **scopePlateId**：作用在什么系统上，比如只在运营系统使用，但是供应商系统不需要加数据权限，则可以传一个运营系统的系统id，如果指定了系统，并且系统id不等于当前登录人的系统id则数据权限不生效
   
