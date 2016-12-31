# ShowBanner
An android library to show banner
##Banner 核心类<br/>
    
###使用方法<br/>
    布局文件中添加
    <com.example.bannershowing.Banner
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="250dp">

    </com.example.bannershowing.Banner>
    
    MainActivity中
        mBanner = (Banner) findViewById(R.id.banner);
        mBanner.set...//一系列设置
        mBanner.setImages(list)要在其他设置方法之后调用;
  


###添加依赖:

    marven地址:
    
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
    
    依赖:
    
    dependencies {
	        compile 'com.github.dreamsaway:ShowBanner:v1.0'
	}
