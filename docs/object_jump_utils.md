对象跳转工具类
----
###### 0.使用
```text
调用RedirectUtils工具类下的方法静态方法;
具体参数说明和方法请往下看;
```
###### 1.服务
```java
/**
 * 启动服务
 *
 * @param context 上下文
 * @param cls     需要启动的服务类
 * @param action  启动服务action,接收时传回
 * @param bundle  服务启动时传入的数据bundle
 */
public static void startService(Context context, Class<?> cls, String action, Bundle bundle)

/**
 * 启动服务
 *
 * @param context 上下文
 * @param cls     需要启动的服务类
 * @param action  启动服务action,接收时传回
 */
public static void startService(Context context, Class<?> cls, String action)

/**
 * 启动服务
 *
 * @param context 上下文
 * @param cls     需要启动的服务类
 * @param bundle  服务启动时传入的数据bundle
 */
public static void startService(Context context, Class<?> cls, Bundle bundle)

/**
 * 启动服务
 *
 * @param context 上下文
 * @param cls     需要启动的服务类
 */
public static void startService(Context context, Class<?> cls)

/**
 * 停止服务
 *
 * @param context 上下文
 * @param cls     要停止的服务类
 */
public static void stopService(Context context, Class<?> cls)

/**
 * 绑定服务
 *
 * @param context 上下文
 * @param conn    服务连接器
 * @param action  启动服务action,接收时传回
 */
public static void bindService(Context context, ServiceConnection conn,
                               String action)

/**
 * 取消服务绑定
 *
 * @param context 上下文
 * @param conn    服务连接器
 */
public static void unbindService(Context context, ServiceConnection conn)
```

###### 2.Activity
```java
/**
 * 启动activity
 *
 * @param activity 提供上下文的activity
 * @param cls      要启动类对象
 * @param bundle   传入的bundle数据
 */
public static void startActivity(Activity activity, Class<?> cls, Bundle bundle)

/**
 * 启动activity
 *
 * @param activity 提供上下文的activity
 * @param cls      要启动activity的类对象
 */
public static void startActivity(Activity activity, Class<?> cls)

/**
 * 以result方式启动activity
 *
 * @param activity    提供上下文的activity
 * @param cls         要启动类对象
 * @param bundle      传入的bundle数据
 * @param requestCode 回调onActivityResult时传回的requestCode
 */
public static void startActivityForResult(Activity activity,
                                          Class<?> cls,
                                          Bundle bundle,
                                          int requestCode)

/**
 * 启动activity
 *
 * @param activity      提供上下文的activity
 * @param classFullName activity全路径类名
 * @param bundle        传入的bundle数据
 */
public static void startActivity(Activity activity, String classFullName, Bundle bundle)

/**
 * 启动activity
 *
 * @param context       上下文
 * @param classFullName activity全路径类名
 * @param bundle        传入的bundle数据
 */
public static void startActivity(Context context, String classFullName, Bundle bundle)

/**
 * 以result方式启动activity
 *
 * @param activity    提供上下文的activity
 * @param cls         activity全路径类名
 * @param bundle      传入的bundle数据
 * @param requestCode 回调onActivityResult时传回的requestCode
 */
public static void startActivityForResult(Activity activity,
                                          String classFullName,
                                          Bundle bundle,
                                          int requestCode)

/**
 * 以result方式启动activity
 *
 * @param activity    提供上下文的activity
 * @param cls         要启动activity的类对象
 * @param requestCode 回调onActivityResult中接收的requestCode参数
 */
public static void startActivityForResult(Activity activity,
                                          Class<?> cls,
                                          int requestCode)

/**
 * 启动指定包下的activity
 *
 * @param activity      提供上下文的activity
 * @param packageName   包名
 * @param classFullName 启动activity的全路径
 */
public static void startPkgActivity(Activity activity,
                                    String packageName,
                                    String classFullName)

/**
 * 启动指定包下的activity
 *
 * @param activity      提供上下文的activity
 * @param packageName   包名
 * @param classFullName 启动activity的全路径
 * @param bundle        传入的bundle数据
 */
public static void startPkgActivity(Activity activity,
                                    String packageName,
                                    String classFullName,
                                    Bundle bundle)

/**
 * 以result方式启动activity
 *
 * @param activity      提供上下文的activity
 * @param packageName   包名
 * @param classFullName 启动activity的全路径
 * @param requestCode   回调onActivityResult中接收的requestCode参数
 */
public static void startPkgActivityForResult(Activity activity,
                                             String packageName,
                                             String classFullName,
                                             int requestCode)

/**
 * 以result方式启动activity
 *
 * @param activity      提供上下文的activity
 * @param packageName   包名
 * @param classFullName 启动activity的全路径
 * @param bundle        传入的bundle数据
 * @param requestCode   回调onActivityResult中接收的requestCode参数
 */
public static void startPkgActivityForResult(Activity activity,
                                             String packageName,
                                             String classFullName,
                                             Bundle bundle,
                                             int requestCode)

/**
 * 结束当前activity
 *
 * @param activity 提供上下文的activity
 */
public static void finishActivity(Activity activity)

/**
 * 判断某个Activity是否存在
 *
 * @param context     上下文
 * @param packageName 包名
 * @param className   要判断activity的类名
 * @return true:存在;false:不存在;
 */
public boolean isActivityExist(Context context,
                               String packageName,
                               String className)
```

###### 3.广播
```java
/**
 * 发送广播
 *
 * @param context    上下文
 * @param action     接收广播时通过action来判断来源
 * @param permission 广播发送与接收权限
 * @param bundle     传入的bundle数据
 */
public static void sendBroadcast(Context context,
                                 String action,
                                 String permission,
                                 Bundle bundle)

/**
 * 发送广播
 *
 * @param context 上下文
 * @param action  接收广播时通过action来判断来源
 * @param bundle  传入的bundle数据
 */
public static void sendBroadcast(Context context,
                                 String action,
                                 Bundle bundle)

/**
 * 发送广播
 * 通过此方式发送的广播，action在框架初始化时设置
 *
 * @param context 上下文
 * @param bundle  传入的bundle数据
 */
public static void sendBroadcast(Context context, Bundle bundle)
```
###### 4.其它
```java
/**
 * 调起通话界面
 *
 * @param context     上下文
 * @param phonenumber 电话号码
 */
public static void callTel(Context context, String phonenumber)

/**
 * 启动桌面
 *
 * @param context 上下文
 */
public static void startHome(Context context)

/**
 * 打开App设置页面
 *
 * @param context 上下文
 */
public static void startAppSettings(Context context)
```