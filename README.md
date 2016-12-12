# DragView
Using in unread messages in ListView. You can drag the unread message  like QQ.
## GIF
![](https://github.com/zjdyhant/DragView/blob/master/app/src/main/res/raw/dragview.gif)
## Layout
```Java
<?xml version="1.0" encoding="utf-8"?>
<com.mm.hant.dragview.widget.DragView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drag_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ListView
        android:id="@+id/list_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />


</com.mm.hant.dragview.widget.DragView>
```
###DragView的责任：
        1，当ListView的未读消息TextView被电击的时候，new一个未读消息TextView并add到界面上。
        2，绘制小红点和连接两个圆的中间部分。中间部分通过二阶贝塞儿曲线绘制。
        3，监听KeyEvent事件，当ACTION_UP的时候回调。
