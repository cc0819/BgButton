# BgButton
圆角按键

[![](https://jitpack.io/v/cc0819/BgButton.svg)](https://jitpack.io/#cc0819/BgButton)

Gradle

  	repositories { 
   		 maven { url "https://jitpack.io" }
	} 
  
	dependencies {
	        compile 'com.github.cc0819:BgButton:1.1.0'
	}
 


Usage

属性XML
    
XML

    <com.cheng.cc.library.BgButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="10dp"
        android:padding="10dp"
        android:text="哈哈哈哈哈"
        android:textColor="#f21"
        app:bgColor="@color/colorPrimaryDark"
        app:cornerRadius="5dp"
        app:borderWidth="2dp"
        app:cornerRadiusColor="@color/colorAccent"
        />


    <com.cheng.cc.library.BgButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="10dp"
        android:padding="10dp"
        android:text="哈哈哈哈哈"
        android:textColor="#f21"
        app:bgColor="@color/colorPrimaryDark"
        app:cornerRadius="15dp"
        app:borderWidth="1dp"
        app:cornerRadiusColor="@color/colorAccent"
        />

    <com.cheng.cc.library.BgButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="10dp"
        android:padding="10dp"
        android:text="哈哈哈哈哈"
        android:textColor="#f21"
        app:borderWidth="1dp"
        app:cornerRadiusColor="@color/colorAccent"
        app:shapeType="oval"/>



