# AccountSdk Android接入帮助

  *仅以Android Studio工程作示例讲解*

### 一、AccountSdk的目录结构
针对Android Studio 或 Gradle 构建的工程，提倡以`*.aar`的方式被其它主工程导入。：

#### 1. AccountSdk主包
命名为`AvidlyAccountSDK_x.x.xx.aar`的文件即为登录模块主包，必须添加到你的主工程中。

#### 2. AccountSdk依赖库
AccountSdk本地文件中，提供了aar形式的依赖库，以备在网络不佳或其它原因时直接从本地libs中加载提供便利。即使如此，我们仍然提倡尽量在gradle中在线从两者的远程仓库更新下载合适的依赖库。

### 二、使用 Android Studio 的 Gradle 导入AccountSdk主包

根据上文的介绍，在你下载好的文件目录中找到名为 `AvidlyAccountSDK_x.x.xx.aar`的文件，并添加到项目的`libs`目录下。

> `AvidlyAccountSDK_1.0.01.aar`仅作为示例参考说明

为了确保libs目录的aar包能正确被工程引用，请检查`app`目录下的`build.gradle`文件中，是否添加以下配置参数：

    repositories {
        flatDir {
            dirs 'libs'
        }
    }

最后将aar添加到compile方法中

    dependencies {
        // your other setting
        // AvidlyAccountSDK_1.0.01替换成实际的文件名
        compile(name: 'AvidlyAccountSDK_1.0.01', ext: 'aar')
    }

至此，AccountSdk的aar包已经成功配置到你的工程中，静等gradle编译生效。但工程导入工作还未完成，还差重要的最后一步工作要做：**添加依赖库**。


### 三、添加依赖库

部分社交平台的 SDK 运行依赖一些公共的第三方库，使用 Android Studio 构建的项目可以通过下述方式来将所依赖的第三方库导入你的项目。

**原则上，我们建议将依赖的第三方库全部导入你的项目，但在某些确定条件下可以有选则性地添加某些登录平台以减少工程大小**

如果你想去除某社交平台的支持，但又不清楚如何操作，请优先与我们的技术支持人员沟通，在他们的协助下你将会正确而顺利达成目的。

####  1.添加社交平台的支持库

##### 1. 加入Facebook Login SDK
如果您的项目中希望接入Facebook登录的功能，我们需要您在的项目中加入Facebook login 的支持，可以按以下的方式添加依赖

    compile('com.facebook.android:facebook-login:[4,5)') {
        // values.xml中增加facebook_app_id，fb_login_protocol_scheme
        exclude group: 'com.android.support'//排除组织依赖
        exclude module: 'appcompat-v7'//排除模块依赖
    }

> 如果你只想通过本地添加Facebook login所依赖的aar文件，也可以在gradle文件中忽略此配置。


####  2. 添加其它外部依赖

由于一些社交平台运行时，需要其它额外的Android Api支持，因此还必须添加如下外部依赖库。

#### 1. 在Gradle中配置jcenter节点
首先在你项目的主工程 `build.gradle` 文件中 `repositories` 节点加入 `jcenter`

    buildscript {
        repositories {
            mavenCentral();
            // ******** 加入 jcenter ********
            jcenter()
            maven {
                url 'https://maven.google.com/'
                name 'Google'
            }
        }
        dependencies {
            classpath 'com.android.tools.build:gradle:2.1.3'
        }
    }

    allprojects {
        repositories {
            // ******** 加入 jcenter ********
            jcenter()
            maven {
                url 'https://maven.google.com/'
                name 'Google'
            }
        }
    }


#### 2. 加入 Android Support 支持库

**【注意】由于Facebook等社交平台依赖了recyclerview的库，AccountSDK依赖Android Support V4库，因此请务必将这些依赖库引入你的项目，否则会导致程序崩溃！**

广告的正常展示需要 `support` 库的支持，请参考以下方式将其引入到您的项目中。

可以通过以下的方式添加依赖
    
    dependencies {
        compile 'com.android.support:recyclerview-v7:26.1.0'
        compile 'com.android.support:support-v4:26.1.0'
        compile 'com.android.support:appcompat-v7:26.1.0'
        compile 'com.android.support:support-annotations:26.1.0'
        compile  'com.android.support:customtabs:26.1.0'
        compile 'com.android.support:cardview-v7:26.1.0'
    }
    
如果您不想通过上面的方式引入`support`库，我们还为您在  `android_support_library` 文件夹中准备了对应的 `xxx.aar` 文件，您只需要将文件添加到项目中并且加入`compile`命令即可。

```groovy
dependencies {
   //support libs
    compile(name: 'animated-vector-drawable-26.1.0', ext: 'aar')
    compile(name: 'appcompat-v7-26.1.0', ext: 'aar')
    compile(name: 'customtabs-26.1.0', ext: 'aar')
    compile(name: 'runtime-1.0.0', ext: 'aar')
    compile(name: 'support-compat-26.1.0', ext: 'aar')
    compile(name: 'support-core-ui-26.1.0', ext: 'aar')
    compile(name: 'support-core-utils-26.1.0', ext: 'aar')
    compile(name: 'support-fragment-26.1.0', ext: 'aar')
    compile(name: 'support-media-compat-26.1.0', ext: 'aar')
    compile(name: 'support-v4-26.1.0', ext: 'aar')
    compile(name: 'support-vector-drawable-26.1.0', ext: 'aar')
    compile(name: 'common-1.0.0', ext: 'jar')
    compile(name: 'supprot-common-1.0.0', ext: 'jar')
    compile(name: 'support-annotations-26.1.0', ext: 'jar')
    compile(name: 'recyclerview-v7-26.1.0', ext: 'aar')
}
```

### 四、Demo工程
为帮助您更好的了解广告SDK的接入以及使用，我们在这里提供了一个简单的[Demo工程](https://github.com/AvidlyGit/AvidlyAccountSdk_AndroidStudio/tree/nosdk "Demo工程")。
