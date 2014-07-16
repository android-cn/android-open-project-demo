Android 开源项目之 pinned-section-listview
=========================

## [pinned-section-listview](https://github.com/beworker/pinned-section-listview) 简介
这是一个简单的listview控件，可以简单的使SectionItem悬停在title，直到下一个SectionItem出现。类似效果淘宝购物车功能等。

## **功能**
- 快速的滚动
- 点击SectionItem
- 下拉刷新


 这是一些简单的功能，你可以fork项目添加你自己的想法:)
 这是个简单的Library,根本不会影响你功能的大小，他甚至只是一个类。

## **使用方法**


使用方法非常简单

1.在你的`layout.xml`中使用`PinnedSectionListView`来代替系统提供的`ListView`

```xml
<com.caesar.PSL_demo.library.PinnedSectionListView
            android:id="@+id/listview"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@android:color/transparent"
            android:cacheColorHint="@android:color/transparent"
            android:divider="@android:color/transparent"
            android:footerDividersEnabled="false"
            android:headerDividersEnabled="false" />
```
            
看起来很简单

2.书写一个`MyAdatper`这个类继承于`BaseAdaper`，当然也可以继承其他Adaper，然后实现`PinnedSectionListAdapter`这个接口 添加一个简单的方法 `isItemViewTypePinned(int viewType)` 这个方法如果返回`true` 那么这个`Item`就是你要`SctionItem`.

```java 
//实现了PinnedSctionListAdapter 这个接口
public class PinnedAdapter extends BaseAdapter implements PinnedSectionListAdapter{
	
···

// 实现这个方法，返回true那么这个item就是SctionItem

     @Override
     public boolean isItemViewTypePinned(int viewType) {
         return viewType == <type to be pinned>;
     }


}
```

 以上就是`PinnedSectionListview`的使用方法,例子可以在`example`文件夹中找到.
 
 
## **代码分析** 
 
1.这是一个基于`ListView` 控件的一个扩展
 
```java 
 public class PinnedSectionListView extends ListView {
 
    ···
 } 
```
 
2.
 
 
 
 
 
 
 
## **感谢**
 
 感谢 [Sergej Shafarenka](https://github.com/beworker)期待有更好的开源项目，造福开发者。
 
 
 



 
